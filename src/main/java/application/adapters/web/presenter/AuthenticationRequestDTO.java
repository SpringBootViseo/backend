package application.adapters.web.presenter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequestDTO {
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    String password;
}