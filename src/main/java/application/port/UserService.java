package application.port;

import application.adapters.exception.UserNotFoundException;
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
    public User updateUser(String id, User user) {
        return userPort.updateUser(id,user);
    }

    @Override
    public User saveUser(User user) {
        String id= user.getId();
        if(!cartPort.availableCart(id))
            cartPort.createCart(id);
        return userPort.saveUser(user);
    }

    @Override
    public User addAddress(String id, String address) {

        return userPort.addAddress(id,address);
    }

    public boolean isAvailable(String id){
        return userPort.isAvailable(id);
    }

    @Override
    public User getUser(String id) {
        if(isAvailable(id)){
            return userPort.getUser(id);
        }
        else throw new UserNotFoundException(id);
    }
}
