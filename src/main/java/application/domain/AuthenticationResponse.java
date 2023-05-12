package application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    @Getter
    @Setter
    private String accessToken;
    @Getter
    @Setter
    private String refreshToken;
}
