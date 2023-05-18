package application.port;

import application.adapters.exception.UserNotFoundException;
import application.domain.Address;
import application.domain.User;
import application.port.in.UserUseCase;
import application.port.out.CartPort;
import application.port.out.PreferencePort;
import application.port.out.UserPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class UserService implements UserUseCase {
    private final UserPort userPort;
    private final CartPort cartPort;

    private final PreferencePort preferencePort;

    @Override
    public User updateUser(String id, User user) {
        return userPort.updateUser(id,user);
    }

    @Override
    public User saveUser(User user) {
        String id= user.getId();
        if(!cartPort.availableCart(id))
            cartPort.createCart(id);
        if(!preferencePort.availablePreference(id))
            preferencePort.createPrefence(id);
        return userPort.saveUser(user);
    }

    @Override
    public User addAddress(String id, Address address) {
        if (addressAvailable(id, address)) {
            User user = userPort.getUser(id);
            List<Address> addresses = user.getAddress();

            if (addresses == null) {
                addresses = new ArrayList<>();
            }

            addresses.add(address);
            user.setAddress(addresses);

            userPort.saveUser(user);
            return user;
        } else {
            throw new IllegalArgumentException("L'adresse existe déjà pour l'utilisateur.");
        }
    }


    public boolean isAvailable(String id){
        return userPort.isAvailable(id);
    }

    public boolean addressAvailable(String id, Address newAddress) {
        User user = userPort.getUser(id);
        List<Address> addresses = user.getAddress();

        if (addresses != null) {
            for (Address existingAddress : addresses) {
                if (existingAddress != null && existingAddress.equals(newAddress)) {
                    return false; // Address already exists
                }
            }
        }

        return true; // Address is available
    }

    @Override
    public User getUser(String id) {
        if(isAvailable(id)){
            return userPort.getUser(id);
        }
        else throw new UserNotFoundException(id);
    }
    @Override
    public List<User> listUser() {
        return userPort.listUser();
    }


    public User loginWithGoogle(User user) {
        if(isAvailable(user.getId())){
            return userPort.getUser(user.getId());
        }
        else{
            return userPort.saveUser(user);
        }
    }

}
