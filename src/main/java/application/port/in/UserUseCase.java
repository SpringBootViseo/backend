package application.port.in;

import application.domain.Address;
import application.domain.User;

import java.util.List;

public interface UserUseCase {
    User updateUser(String id,User user);
    boolean isAvailable(String id);
    User getUser(String id);
    User saveUser(User user);
    User addAddress(String id, Address address);
    List<User> listUser();
    User loginWithGoogle(User user);

}
