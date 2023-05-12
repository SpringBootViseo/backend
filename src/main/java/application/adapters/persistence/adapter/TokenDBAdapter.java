
package application.adapters.persistence.adapter;

import application.adapters.mapper.mapperImpl.TokenMapperImpl;
import application.adapters.persistence.MongoConfig;
import application.adapters.persistence.entity.TokenEntity;
import application.adapters.persistence.repository.TokenRepository;
import application.domain.Token;
import application.port.out.TokenPort;
import com.mongodb.client.MongoCollection;
import lombok.AllArgsConstructor;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class TokenDBAdapter implements TokenPort {
    private final TokenRepository tokenRepository;
    private final TokenMapperImpl tokenMapper;
    private final MongoConfig mongoConfig;
    @Override
    public List<Token> getAllValidTokenByUser(Integer id) {
        MongoCollection<Document> collection= mongoConfig.getAllDocuments("Tokens");

        List<Token> tokenList=tokenMapper.documentTokenToListTokens(collection);
        List<Token> tokenHasUser=tokenList.stream().filter(
                token ->
                        token.getUserInfo().getId().equals(id)


        ).collect(Collectors.toList());
        List<Token> tokenHasRevokedFalse= tokenHasUser.stream().filter(
                token -> !token.expired || !token.revoked
        ).collect(Collectors.toList());
        return tokenHasRevokedFalse;

    }

    @Override
    public Token getToken(String token) {
        if(tokenRepository.findByToken(token).isPresent()){
            TokenEntity tokenEntity=tokenRepository.findByToken(token).get();
            return tokenMapper.tokenEntityToToken(tokenEntity);}
        else return null;
    }

    @Override
    public Token addToken(Token token) {
        TokenEntity tokenEntity=tokenMapper.tokenToTokenEntity(token);
        return tokenMapper.tokenEntityToToken(tokenRepository.save(tokenEntity));
    }

    @Override
    public boolean isValid(String token) {
        return tokenRepository.findByToken(token)
                .map(t -> !t.isExpired() && !t.isRevoked())
                .orElse(false);
    }
}
