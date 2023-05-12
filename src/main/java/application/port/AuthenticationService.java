package application.port;


import application.domain.AuthenticationRequest;
import application.domain.AuthenticationResponse;
import application.domain.RegisterRequest;
import application.domain.UserInfo;
import application.port.in.AuthenticationUseCase;
import application.port.out.AuthenticationPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService implements AuthenticationUseCase {
    AuthenticationPort authentificationPort;
    @Override
    public void saveUserToken(UserInfo user, String jwtToken) {
        authentificationPort.saveUserToken(user,jwtToken);
    }

    @Override
    public void revokeAllUserTokens(UserInfo user) {
        authentificationPort.revokeAllUserTokens(user);

    }

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        return authentificationPort.register(request);
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        return authentificationPort.authenticate(request);
    }

    @Override
    public AuthenticationResponse refreshToken(String refreshToken) {
        return authentificationPort.refreshToken(refreshToken);
    }
}