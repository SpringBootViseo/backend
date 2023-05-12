package application.port;

import application.domain.Token;
import application.port.in.TokenUseCase;
import application.port.out.TokenPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class TokenService implements TokenUseCase {
    private final TokenPort tokenPort;
    @Override
    public List<Token> getAllValidTokenByUser(Integer id) {
        return tokenPort.getAllValidTokenByUser(id);
    }

    @Override
    public Token getToken(String token) {
        return tokenPort.getToken(token);
    }

    @Override
    public Token addToken(Token token) {
        return tokenPort.addToken(token);
    }

    @Override
    public boolean isValid(String token) {
        return tokenPort.isValid(token);
    }


}