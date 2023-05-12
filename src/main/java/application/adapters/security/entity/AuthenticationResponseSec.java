package application.adapters.security.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponseSec {
    @Getter
    @Setter
    @JsonProperty("access_token")
    private String accessToken;
    @Getter
    @Setter
    @JsonProperty(value = "refresh_token")
    private String refreshToken;
}