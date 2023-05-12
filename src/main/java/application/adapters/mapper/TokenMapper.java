package application.adapters.mapper;

import application.adapters.persistence.entity.TokenEntity;
import application.domain.Token;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.List;

public interface TokenMapper {
    Token tokenEntityToToken(TokenEntity tokenEntity);
    TokenEntity tokenToTokenEntity(Token token);
    List<Token> listTokenEntityToListToken(List<TokenEntity> tokenEntityList);
    List<TokenEntity> listTokenToListTokenEntity(List<Token> tokenList);
    List<Token> documentTokenToListTokens(MongoCollection<Document> collection);
}