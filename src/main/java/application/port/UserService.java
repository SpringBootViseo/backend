package application.port;

import application.adapters.exception.UserNotFoundException;
import application.domain.User;
import application.port.in.UserUseCase;
import application.port.out.CartPort;
import application.port.out.PreferencePort;
import application.port.out.UserPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
        user.setAvertissement(0);
        user.setBlackListed(false);
        if(!cartPort.availableCart(id))
            cartPort.createCart(id);
        if(!preferencePort.availablePreference(id))
            preferencePort.createPrefence(id);


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
    @Override
    public List<User> listUser() {
        return userPort.listUser();
    }


    public User loginWithGoogle(User user) {
        System.out.println("Availability in DB:"+isAvailable(user.getId()));
        if(isAvailable(user.getId())){
            return userPort.getUser(user.getId());
        }
        else{
            return this.saveUser(user);
        }
    }

    @Override
    public User avertirUser(String id) {
        User user = userPort.getUser(id);

        int avertissements= user.getAvertissement();
        if(avertissements>=0 && avertissements<=2){
            avertissements +=1;
            user.setAvertissement(avertissements);
        }
       if(avertissements>=3){

           user.setBlackListed(true);
        }




        return userPort.saveUser(user);
    }

    @Override
    public User blacklisterUser(String id) {
        User user=userPort.getUser(id);
        user.setBlackListed(true);


        return userPort.saveUser(user);
    }

}
