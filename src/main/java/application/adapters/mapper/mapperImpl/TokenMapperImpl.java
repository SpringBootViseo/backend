package application.adapters.mapper.mapperImpl;

import application.adapters.mapper.TokenMapper;
import application.adapters.persistence.entity.TokenEntity;
import application.domain.Role;
import application.domain.Token;
import application.domain.UserInfo;
import com.mongodb.client.MongoCollection;
import lombok.AllArgsConstructor;

import org.bson.Document;
import org.springframework.stereotype.Component;


import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@Component
public class TokenMapperImpl implements TokenMapper {
    @Override
    public Token tokenEntityToToken(TokenEntity tokenEntity) {
        return new Token(tokenEntity.getId(),tokenEntity.getToken(),tokenEntity.getTokenType(), tokenEntity.isRevoked(), tokenEntity.isExpired(),tokenEntity.getUserInfo());
    }

    @Override
    public TokenEntity tokenToTokenEntity(Token token) {
        return new TokenEntity(token.getId(),token.getToken(),token.getTokenType(), token.isRevoked(), token.isExpired(),token.getUserInfo());
    }

    @Override
    public List<Token> listTokenEntityToListToken(List<TokenEntity> tokenEntityList) {
        List<Token> tokenList=new ArrayList<>();
        for (TokenEntity tokenEntity:tokenEntityList
        ) {
            tokenList.add(this.tokenEntityToToken(tokenEntity));
        }
        return tokenList;
    }

    @Override
    public List<TokenEntity> listTokenToListTokenEntity(List<Token> tokenList) {
        List<TokenEntity> tokenEntityList=new ArrayList<>();
        for (Token token:tokenList
        ) {
            tokenEntityList.add(this.tokenToTokenEntity(token));

        }
        return tokenEntityList;
    }


    @Override
    public List<Token> documentTokenToListTokens(MongoCollection<Document> collection) {
        Role role=Role.USER;;
        List<Token> tokens=new ArrayList<>();
        for (Document doc:collection.find()) {
            Document userDocument= doc.get("user",Document.class);
            if(userDocument.getString("role")=="ADMIN")
                role=Role.ADMIN;
            if(userDocument.getString("role")=="PREPARATOR")
                role=Role.PREPARATOR;
            if(userDocument.getString("role")=="DELIVERY")
                role=Role.DELIVERY;
            if(userDocument.getString("role")=="CLIENT")
                role=Role.CLIENT;





            UserInfo userInfo =new UserInfo(userDocument.getInteger("_id"), userDocument.getString("firstname"), userDocument.getString("lastname"), userDocument.getString("email"), userDocument.getString("password"),userDocument.getList("tokens",Token.class), role );
            Token token=new Token(doc.getInteger("_id"),doc.getString("token"),"BEARER",doc.get("revoked",boolean.class),doc.get("expired",boolean.class),userInfo);
            tokens.add(token);
        }
        return tokens;
    }
}