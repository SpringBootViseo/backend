package application.adapters.persistence.entity;

import application.domain.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Token")
public class TokenEntity {
    @Id
    @Getter
    @Setter
    private Integer id;
    @Getter
    @Setter
    private String token;
    @Getter
    @Setter
    private String tokenType="BEARER";
    @Getter
    @Setter
    private boolean revoked;
    @Getter
    @Setter
    private boolean expired;
    @Getter
    @Setter
    private UserInfo userInfo;
}