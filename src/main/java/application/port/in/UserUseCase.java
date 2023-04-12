package application.port.in;

import application.domain.User;

public interface UserUseCase {
    User updateUser(String id,User user);
    boolean isAvailable(String id);
    User getUser(String id);
    User saveUser(User user);
    User addAddress(String id,String address);

}
