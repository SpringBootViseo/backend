package application.port.out;

import application.domain.Token;

import java.util.List;

public interface TokenPort {
    List<Token> getAllValidTokenByUser(Integer id);
    Token getToken(String token);
    Token addToken(Token token);
    boolean isValid(String token);
}