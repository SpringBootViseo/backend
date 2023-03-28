package application.port;

import application.domain.User;
import application.port.in.UserUseCase;
import application.port.out.UserPort;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserUseCase {
    @Autowired
    private final UserPort userPort;

    @Override
    public User saveUser(User user) {
        return userPort.saveUser(user);
    }
}
