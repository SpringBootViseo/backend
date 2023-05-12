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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")

@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationUseCase authentificationUseCase;
    private final AuthenticationResponseMapperImpl authenticationResponseMapper;
    private final AuthenticationRequestMapperImpl authenticationRequestMapper;
    private final RegisterRequestMapperImpl registerRequestMapper;
    @CrossOrigin(origins = "*")
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDTO> register(
            @RequestBody RegisterRequestDTO request
    ) {
        RegisterRequest registerRequest=registerRequestMapper.registerRequestDTOToRegisterRequest(request);
        return ResponseEntity.ok(authenticationResponseMapper.authenticationResponseToAuthenticationResposneDTO( authentificationUseCase.register(registerRequest)));
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDTO> authenticate(
            @RequestBody AuthenticationRequestDTO request
    ) {
        AuthenticationRequest authenticationRequest=authenticationRequestMapper.authenticationRequestDTOToAuthenticationRequest(request);
        return ResponseEntity.ok(authenticationResponseMapper.authenticationResponseToAuthenticationResposneDTO(authentificationUseCase.authenticate(authenticationRequest)));
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/refresh-token")
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
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);

    }

}