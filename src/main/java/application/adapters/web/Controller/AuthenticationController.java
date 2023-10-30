package application.adapters.web.Controller;

import application.adapters.mapper.mapperImpl.AuthenticationRequestMapperImpl;
import application.adapters.mapper.mapperImpl.AuthenticationResponseMapperImpl;
import application.adapters.mapper.mapperImpl.RegisterRequestMapperImpl;
import application.adapters.security.entity.AuthenticationRequestSec;
import application.adapters.security.entity.AuthenticationResponseSec;
import application.adapters.security.entity.RegisterRequestSec;
import application.adapters.web.presenter.AuthenticationRequestDTO;
import application.adapters.web.presenter.AuthenticationResponseDTO;
import application.adapters.web.presenter.RegisterRequestDTO;
import application.domain.AuthenticationRequest;
import application.domain.AuthenticationResponse;
import application.domain.RegisterRequest;
import application.port.in.AuthenticationUseCase;
import application.port.in.AuthenticationUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")

@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationUseCase authentificationUseCase;
    private final AuthenticationResponseMapperImpl authenticationResponseMapper;
    private final AuthenticationRequestMapperImpl authenticationRequestMapper;
    private final RegisterRequestMapperImpl registerRequestMapper;
    private final Logger logger= LogManager.getLogger(AuthenticationController.class);
    @CrossOrigin(origins = "*")
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDTO> register(
            @RequestBody RegisterRequestDTO request
    ) {
        logger.trace("Map register request DTO "+request.toString()+"to register request");
        RegisterRequest registerRequest=registerRequestMapper.registerRequestDTOToRegisterRequest(request);
        logger.trace("Call the registry function from authenfication use case");
        logger.trace("Map register request  "+registerRequest.toString()+"to register request DTO");
        logger.debug("Map the registry function authentication from the use case to the post mapper /auth/register");

        return ResponseEntity.ok(authenticationResponseMapper.authenticationResponseToAuthenticationResposneDTO( authentificationUseCase.register(registerRequest)));
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDTO> authenticate(
            @RequestBody AuthenticationRequestDTO request
    ) {
        logger.trace("Map authentication request DTO "+request.toString()+"to authentication request");
        AuthenticationRequest authenticationRequest=authenticationRequestMapper.authenticationRequestDTOToAuthenticationRequest(request);
        logger.trace("Call the authentication function from authenfication use case");
        logger.trace("Map authentication request  "+authenticationRequest.toString()+"to authentication request DTO");
        logger.debug("Map the authentication function authentication from the use case to the post mapper  /auth/authenticate");
        return ResponseEntity.ok(authenticationResponseMapper.authenticationResponseToAuthenticationResposneDTO(authentificationUseCase.authenticate(authenticationRequest)));
    }
    @CrossOrigin(origins = "*",allowedHeaders = "*")
    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponseDTO> refreshToken(@RequestHeader("Authorization") String refreshToken){
        if (refreshToken == null ||!refreshToken.startsWith("Bearer ")) {
            logger.error("Cannot find a valid token");
            throw new NoSuchElementException("Can't find a valid token");
        }
        else{
            logger.trace("Eleminate the BEARER word from the token");
            String refreshTokenWithoutHeader = refreshToken.substring(7);
            logger.trace("Call the refresh token from the authentication use case");
            AuthenticationResponse authResponse =authentificationUseCase.refreshToken(refreshTokenWithoutHeader);
            logger.trace("Map authenticationResponse "+authResponse.toString()+"to authenticationResponse DTO");
            logger.debug("Map the refreshToken function  from the use case to the post mapper /auth/refresh-token");
            return new ResponseEntity<>(authenticationResponseMapper.authenticationResponseToAuthenticationResposneDTO(authResponse), HttpStatus.OK);
        }
    }
    /*@PostMapping("/refresh-token")
    public void refreshToken(
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
        AuthenticationResponse authResponse =authentificationUseCase.refreshToken(refreshToken);
        System.out.println(".....////////////..............."+authResponse.getRefreshToken());
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);

    }*/

}