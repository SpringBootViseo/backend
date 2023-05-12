package application.port.in;

import application.domain.Token;

import java.util.List;

public interface TokenUseCase {
    List<Token> getAllValidTokenByUser(Integer id);
    Token getToken(String token);
    Token addToken(Token token);
    boolean isValid(String token);
}
