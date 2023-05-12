package application.port.out;

import application.adapters.exception.UserAlreadyExistsException;
import application.domain.User;

import java.util.List;

public interface UserPort {
    User updateUser(String id,User user);
    boolean isAvailable(String id);
    User getUser(String id);
    User saveUser(User user) throws UserAlreadyExistsException;
    User addAddress(String id,String address);
    List<User> listUser();
    }
