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
        var token = new TokenEntity(randomNumber,jwtToken,"BEARER",false,false,user);
        tokenPort.addToken(tokenMapper.tokenEntityToToken(token));
    }
    public void revokeAllUserTokens(UserInfo user) {
        var validUserTokens = tokenPort.getAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        for (Token  validtoken: validUserTokens
        ) {
            tokenPort.addToken(validtoken);
        }

    }




    public AuthenticationResponse register(RegisterRequest request) {
        Random rand = new Random();
        Integer
                randomNumber = Integer.valueOf(rand.nextInt());
        if(userInfoPort.isUser(request.getEmail())){
            throw  new UserAlreadyExistsException("user with email "+request.getEmail()+" already exists!");
        }
        UserInfo userInfo=new UserInfo(randomNumber,request.getFirstname(),request.getLastname(),request.getEmail(),passwordEncoder.encode(request.getPassword()),new ArrayList<>(), request.getRole());
        userInfoPort.addUserInfo(userInfo);
        String jwtToken= jwtService.generateToken(userInfoMapper.userInfoToUserInfoSec(userInfo));
        String refreshToken=jwtService.generateRefreshToken(userInfoMapper.userInfoToUserInfoSec(userInfo));
        saveUserToken(userInfo,jwtToken);
        AuthenticationResponseSec authenticationResponseSec=new AuthenticationResponseSec(jwtToken,refreshToken);
        return authenticationResponseMapper.authenticationResponseSecToAuthenticationResponse(authenticationResponseSec);
    }
    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );


        UserInfo userInfoDetails = userInfoPort.getUserByEmail(request.getEmail());

        UserInfoSec user=userInfoMapper.userInfoToUserInfoSec(userInfoDetails);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(userInfoDetails);
        saveUserToken(userInfoMapper.userInfoSecToUserInfo(user), jwtToken);
        AuthenticationResponseSec authenticationResponseSec=new AuthenticationResponseSec(jwtToken,refreshToken);
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
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = userInfoPort.getUserByEmail(userEmail);
            UserInfoSec userInfoSec= userInfoMapper.userInfoToUserInfoSec(user);
            if (jwtService.isTokenValid(refreshToken,userInfoSec)) {
                var accessToken = jwtService.generateToken(userInfoSec);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                AuthenticationResponseSec authResponse = new AuthenticationResponseSec(accessToken,refreshToken);
                return authenticationResponseMapper.authenticationResponseSecToAuthenticationResponse(authResponse);
            }
        }
        throw  new UserAlreadyExistsException("jjd");
    }
}