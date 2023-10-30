package application.port;


import application.domain.*;
import application.port.in.AuthenticationUseCase;
import application.port.out.AuthenticationPort;
import application.port.out.LivreurPort;
import application.port.out.PreparateurPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class AuthenticationService implements AuthenticationUseCase {

    AuthenticationPort authentificationPort;
    PreparateurPort preparateurPort;
    LivreurPort livreurPort;
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

        if(request.getRole()== Role.PREPARATOR){

            preparateurPort.addPreparateur(new Preparateur(request.getFirstname(),request.getLastname(),request.getEmail()));
        }
        if(request.getRole()==Role.DELIVERY){
            livreurPort.addLivreur(new Livreur(request.getFirstname(),request.getLastname(),request.getEmail()));
        }
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