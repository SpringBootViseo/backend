package application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Getter
    @Setter
    private String id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private String phone;


}
