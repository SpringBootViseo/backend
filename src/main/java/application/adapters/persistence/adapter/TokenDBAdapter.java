
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class TokenDBAdapter implements TokenPort {
    private final TokenRepository tokenRepository;
    private final TokenMapperImpl tokenMapper;
    private final MongoConfig mongoConfig;
    private static final Logger logger = LoggerFactory.getLogger(TokenDBAdapter.class);
    @Override
    public List<Token> getAllValidTokenByUser(Integer id) {
        logger.info("Getting all valid tokens for User with ID: {}", id);

        MongoCollection<Document> collection = mongoConfig.getAllDocuments("Tokens");

        List<Token> tokenList = tokenMapper.documentTokenToListTokens(collection);

        logger.debug("Found {} tokens in the collection", tokenList.size());

        List<Token> tokenHasUser = tokenList.stream()
                .filter(token -> token.getUserInfo().getId().equals(id))
                .collect(Collectors.toList());

        logger.debug("Found {} tokens belonging to User with ID: {}", tokenHasUser.size(), id);

        List<Token> tokenHasRevokedFalse = tokenHasUser.stream()
                .filter(token -> !token.expired || !token.revoked)
                .collect(Collectors.toList());

        logger.info("Found {} valid tokens for User with ID: {}", tokenHasRevokedFalse.size(), id);

        return tokenHasRevokedFalse;
    }


    @Override
    public Token getToken(String token) {
        logger.info("Getting token by token value: {}", token);

        if (tokenRepository.findByToken(token).isPresent()) {
            TokenEntity tokenEntity = tokenRepository.findByToken(token).get();
            logger.debug("Token found by token value: {}", token);
            return tokenMapper.tokenEntityToToken(tokenEntity);
        } else {
            logger.info("Token with value {} not found", token);
            return null;
        }
    }

    @Override
    public Token addToken(Token token) {
        logger.info("Adding a new token");

        TokenEntity tokenEntity = tokenMapper.tokenToTokenEntity(token);
        logger.debug("Converting token to tokenEntity");
        TokenEntity savedTokenEntity = tokenRepository.save(tokenEntity);
        logger.debug("Saving tokenEntity: {}", tokenEntity);
        Token savedToken = tokenMapper.tokenEntityToToken(savedTokenEntity);
        logger.debug("Converting tokenEntity to token");

        logger.info("Token added successfully");

        return savedToken;
    }


    @Override
    public boolean isValid(String token) {
        logger.info("Checking if token is valid");

        boolean valid = tokenRepository.findByToken(token)
                .map(t -> !t.isExpired() && !t.isRevoked())
                .orElse(false);

        logger.info("Token is {}valid", valid ? "" : "not ");

        return valid;
    }

}
