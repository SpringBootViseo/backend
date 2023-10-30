package application.adapters.security;

import application.adapters.exception.UserAlreadyExistsException;
import application.adapters.mapper.mapperImpl.*;
import application.adapters.persistence.entity.TokenEntity;
import application.adapters.security.config.JwtService;
import application.adapters.security.entity.AuthenticationResponseSec;
import application.adapters.security.entity.UserInfoSec;
import application.domain.*;
import application.port.out.AuthenticationPort;
import application.port.out.TokenPort;
import application.port.out.UserInfoPort;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class AuthenticationAdapter implements AuthenticationPort {
    //private final UserInfoRepository repository;
    private final UserInfoPort userInfoPort;
    private final static Logger logger = LogManager.getLogger(AuthenticationAdapter.class);
    private final TokenPort tokenPort;
    private final PasswordEncoder passwordEncoder;
    private final UserInfoMapperImpl userInfoMapper;
    private final TokenMapperImpl tokenMapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RegisterRequestMapperImpl registerRequestMapper;
    private final AuthenticationResponseMapperImpl authenticationResponseMapper;
    private final AuthenticationRequestMapperImpl authenticationRequestMapper;

    public void saveUserToken(UserInfo user, String jwtToken) {

        Random rand = new Random();
        Integer randomNumber = Integer.valueOf(rand.nextInt());
        logger.trace("Create a Token of type Berear");
        var token = new TokenEntity(randomNumber,jwtToken,"BEARER",false,false,user);
        tokenPort.addToken(tokenMapper.tokenEntityToToken(token));
    }
    public void revokeAllUserTokens(UserInfo user) {
        logger.trace("Get all Valid Token of user "+user.getId());
        var validUserTokens = tokenPort.getAllValidTokenByUser(user.getId());
        logger.trace("Check if there's a Valid Token of user "+user.getId());
        if (validUserTokens.isEmpty())
            return;
        logger.trace("Change status of all retrieved token to invalid");
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        logger.trace("Save tokens with new status");
        for (Token  validtoken: validUserTokens
        ) {
            tokenPort.addToken(validtoken);
        }


    }




    public AuthenticationResponse register(RegisterRequest request) {
        Random rand = new Random();
        Integer
                randomNumber = Integer.valueOf(rand.nextInt());
        logger.trace("Check if a user had the same email"+request.getEmail());
        if(userInfoPort.isUser(request.getEmail())){
            logger.error("user with email "+request.getEmail()+" already exists!");
            throw  new UserAlreadyExistsException("user with email "+request.getEmail()+" already exists!");
        }
        UserInfo userInfo=new UserInfo(randomNumber,request.getFirstname(),request.getLastname(),request.getEmail(),passwordEncoder.encode(request.getPassword()),new ArrayList<>(), request.getRole());
        userInfoPort.addUserInfo(userInfo);
        logger.debug(userInfo.toString() + " created");
        logger.trace("generate Access Token");
        String jwtToken= jwtService.generateToken(userInfoMapper.userInfoToUserInfoSec(userInfo));
        logger.trace("generate Refresh Token");
        String refreshToken=jwtService.generateRefreshToken(userInfoMapper.userInfoToUserInfoSec(userInfo));
        logger.trace("Save access Token"+jwtToken);
        saveUserToken(userInfo,jwtToken);
        logger.trace("Generate response");
        AuthenticationResponseSec authenticationResponseSec=new AuthenticationResponseSec(jwtToken,refreshToken);
        logger.debug("Register user with email "+request.getEmail());
        return authenticationResponseMapper.authenticationResponseSecToAuthenticationResponse(authenticationResponseSec);
    }
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        logger.trace("Call authenticationManager to authenticate");
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        logger.trace("Retrieve User data which have mail"+request.getEmail());

        UserInfo userInfoDetails = userInfoPort.getUserByEmail(request.getEmail());
        logger.trace("Map userInfo to UserInfoSec");
        UserInfoSec user=userInfoMapper.userInfoToUserInfoSec(userInfoDetails);
        logger.trace("Generate Access Token");
        var jwtToken = jwtService.generateToken(user);
        logger.trace("Generate Refresh Token");

        var refreshToken = jwtService.generateRefreshToken(user);
        logger.trace("Revoke all token");

        revokeAllUserTokens(userInfoDetails);
        logger.trace("Save the new Access Token "+jwtToken);
        saveUserToken(userInfoMapper.userInfoSecToUserInfo(user), jwtToken);
        logger.trace("Generate response");

        AuthenticationResponseSec authenticationResponseSec=new AuthenticationResponseSec(jwtToken,refreshToken);
        logger.debug("Authenticate with email "+request.getEmail());
        return authenticationResponseMapper.authenticationResponseSecToAuthenticationResponse(authenticationResponseSec);
    }
    /*    public void refreshToken(
                HttpServletRequest request,
                HttpServletResponse response
        ) throws IOException {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            final String refreshToken;
            final String userEmail;
            if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
                return;
            }
            refreshToken = authHeader.substring(7);
            userEmail = jwtService.extractUsername(refreshToken);
            if (userEmail != null) {
                var user = userInfoPort.getUserByEmail(userEmail);
                UserInfoSec userInfoSec= userInfoMapper.userInfoToUserInfoSec(user);
                if (jwtService.isTokenValid(refreshToken,userInfoSec)) {
                    var accessToken = jwtService.generateToken(userInfoSec);
                    revokeAllUserTokens(user);
                    saveUserToken(user, accessToken);
                    AuthenticationResponse authResponse = new AuthenticationResponse(accessToken,refreshToken);
                    new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
                }
            }
        }*/
    public AuthenticationResponse refreshToken(String refreshToken){
        final String userEmail;
        logger.trace("Extract email from refresh token"+refreshToken);
        userEmail = jwtService.extractUsername(refreshToken);
        logger.trace("Check if email is null");
        if (userEmail != null) {
            logger.trace("Retrieve user with mail "+userEmail);
            var user = userInfoPort.getUserByEmail(userEmail);
            logger.trace("Map userInfo to UserInfoSec");
            UserInfoSec userInfoSec= userInfoMapper.userInfoToUserInfoSec(user);
            logger.trace("Check if token is Valid");
            if (jwtService.isTokenValid(refreshToken,userInfoSec)) {
                logger.trace("Generate access Token");
                var accessToken = jwtService.generateToken(userInfoSec);
                logger.trace("Revoke all tokens");
                revokeAllUserTokens(user);
                logger.trace("Save the acces token");
                saveUserToken(user, accessToken);
                logger.trace("Generate response");
                AuthenticationResponseSec authResponse = new AuthenticationResponseSec(accessToken,refreshToken);
                logger.debug("Generate access Token from "+refreshToken);
                return authenticationResponseMapper.authenticationResponseSecToAuthenticationResponse(authResponse);
            }
        }
        logger.error("User doesn't exist");
        throw  new UserAlreadyExistsException("jjd");
    }
}