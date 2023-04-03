package application.port;

import application.domain.User;
import application.port.in.UserUseCase;
import application.port.out.CartPort;
import application.port.out.UserPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserUseCase {
    private final UserPort userPort;
    private final CartPort cartPort;

    @Override
    public User saveUser(User user) {
        String id= user.getId();
        if(!cartPort.availableCart(id))
            cartPort.createCart(id);
        return userPort.saveUser(user);
    }
}
