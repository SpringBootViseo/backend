package application.domain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class Token {
    @Getter
    @Setter
    public Integer id;
    @Getter
    @Setter
    public String token;
    @Getter
    @Setter
    public String tokenType="BEARER";
    @Getter
    @Setter
    public boolean revoked;
    @Getter
    @Setter
    public boolean expired;
    @Getter
    @Setter
    public UserInfo userInfo;
}
