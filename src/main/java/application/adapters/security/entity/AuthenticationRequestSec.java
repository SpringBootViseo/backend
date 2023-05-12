package application.adapters.security.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor

public class AuthenticationRequestSec {
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    String password;
}