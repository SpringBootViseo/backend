package application.port.out;

import application.adapters.exception.UserAlreadyExistsException;
import application.domain.Address;
import application.domain.User;

import java.util.List;

public interface UserPort {
    User updateUser(String id,User user);
    boolean isAvailable(String id);
    boolean addressAvailable(String id , Address address);
    User getUser(String id);
    User saveUser(User user) throws UserAlreadyExistsException;
    User addAddress(String id, Address address);
    List<User> listUser();

}
