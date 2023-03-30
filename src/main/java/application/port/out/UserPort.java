package application.port.out;

import application.adapters.exception.UserAlreadyExistsException;
import application.domain.User;

public interface UserPort {
    User saveUser(User user) throws UserAlreadyExistsException;
    }
