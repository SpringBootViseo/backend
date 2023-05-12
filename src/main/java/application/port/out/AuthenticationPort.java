package application.port.out;
import application.domain.AuthenticationRequest;
import application.domain.AuthenticationResponse;
import application.domain.RegisterRequest;
import application.domain.UserInfo;

public interface AuthenticationPort {
    void saveUserToken(UserInfo user, String jwtToken);
    void revokeAllUserTokens(UserInfo user);
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
    AuthenticationResponse refreshToken(String refreshToken);
}
