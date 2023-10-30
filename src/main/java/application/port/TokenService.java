package application.port;

import application.domain.Token;
import application.port.in.TokenUseCase;
import application.port.out.TokenPort;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class TokenService implements TokenUseCase {
    private final TokenPort tokenPort;
    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);

    @Override
    public List<Token> getAllValidTokenByUser(Integer id) {
        // INFO level for a significant operation
        logger.info("Getting all valid tokens for user with ID: {}", id);
        return tokenPort.getAllValidTokenByUser(id);
    }

    @Override
    public Token getToken(String token) {
        // INFO level for a significant operation
        logger.info("Getting token with value: {}", token);
        return tokenPort.getToken(token);
    }

    @Override
    public Token addToken(Token token) {
        // INFO level for a significant operation
        logger.info("Adding a token: {}", token);
        return tokenPort.addToken(token);
    }

    @Override
    public boolean isValid(String token) {
        // DEBUG level for a routine operation
        logger.debug("Checking if token is valid: {}", token);
        return tokenPort.isValid(token);
    }
}
