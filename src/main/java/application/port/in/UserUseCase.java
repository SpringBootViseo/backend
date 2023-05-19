package application.port.in;

import application.domain.Address;
import application.domain.User;

import java.util.List;
import java.util.UUID;

public interface UserUseCase {
    User updateUser(String id,User user);
    boolean isAvailable(String id);
    User getUser(String id);
    User saveUser(User user);
    User addAddress(String id, Address address);
    User deleteAddress(UUID idAddress, String id);
    List<User> listUser();
    User loginWithGoogle(User user) throws IllegalAccessException;
    User avertirUser(String id);
    User blacklisterUser(String id);

}
