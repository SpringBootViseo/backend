package application.port;

import application.domain.Token;
import application.port.in.TokenUseCase;
import application.port.out.TokenPort;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class TokenService implements TokenUseCase {
    private final TokenPort tokenPort;
    private final static Logger logger= LogManager.getLogger(TokenService.class);

    @Override
    public List<Token> getAllValidTokenByUser(Integer id) {
        logger.info("Retrieve all valid token of user with id "+ id);
        return tokenPort.getAllValidTokenByUser(id);
    }

    @Override
    public Token getToken(String token) {

        logger.info("Get token "+token);
        return tokenPort.getToken(token);
    }

    @Override
    public Token addToken(Token token)
    {
        logger.info("Add token "+token.toString());
        return tokenPort.addToken(token);
    }

    @Override
    public boolean isValid(String token) {
        logger.info("Check validity of  token "+token);

        return tokenPort.isValid(token);
    }


}