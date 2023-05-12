package application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    String password;
}
