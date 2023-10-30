package application.port;


import application.domain.*;
import application.port.in.AuthenticationUseCase;
import application.port.out.AuthenticationPort;
import application.port.out.LivreurPort;
import application.port.out.PreparateurPort;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.codehaus.plexus.logging.LoggerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor

public class AuthenticationService implements AuthenticationUseCase {

    private final static Logger logger= LoggerFactory.getLogger(AuthenticationService.class);
    AuthenticationPort authentificationPort;
    PreparateurPort preparateurPort;
    LivreurPort livreurPort;
    @Override
    public void saveUserToken(UserInfo user, String jwtToken) {

        logger.debug(String.format(user.getFirstname()+" "+user.getLastname()+" generate and save  the token : "+jwtToken));
        authentificationPort.saveUserToken(user,jwtToken);
    }

    @Override
    public void revokeAllUserTokens(UserInfo user) {
        logger.debug(String.format(user.getFirstname()+" "+user.getLastname()+ " revoke all his tokens"));
        authentificationPort.revokeAllUserTokens(user);

    }

    @Override
    public AuthenticationResponse register(RegisterRequest request) {

        if(request.getRole()== Role.PREPARATOR){
            logger.info(String.format(request.getFirstname()+" "+request.getLastname()+" send registry request as order preparator with mail "+request.getEmail()));
            preparateurPort.addPreparateur(new Preparateur(request.getFirstname(),request.getLastname(),request.getEmail()));
        }
        if(request.getRole()==Role.DELIVERY){
            logger.info(String.format(request.getFirstname()+" "+request.getLastname()+" send registry request as delivery guy with mail "+request.getEmail()));
            livreurPort.addLivreur(new Livreur(request.getFirstname(),request.getLastname(),request.getEmail()));
        }
        logger.info(String.format(request.getFirstname()+" "+request.getLastname()+" send registry request  with mail "+request.getEmail()));
        return authentificationPort.register(request);
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        logger.info(String.format("Authentication request is sent with mail "+request.getEmail()));
        return authentificationPort.authenticate(request);
    }

    @Override
    public AuthenticationResponse refreshToken(String refreshToken) {
        logger.info(String.format("Refresh Token request is sent "+refreshToken));
        return authentificationPort.refreshToken(refreshToken);
    }
}