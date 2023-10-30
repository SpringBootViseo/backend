package application.port;

import application.adapters.exception.UserNotFoundException;
import application.domain.Address;
import application.domain.User;
import application.port.in.UserUseCase;
import application.port.out.CartPort;
import application.port.out.PreferencePort;
import application.port.out.UserPort;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@AllArgsConstructor
public class UserService implements UserUseCase {

    private final static Logger logger= LogManager.getLogger(UserService.class);
    private final UserPort userPort;
    private final CartPort cartPort;


    private final PreferencePort preferencePort;

    @Override
    public User updateUser(String id, User user) {

        logger.info("Update user with id "+id+" to user "+user.toString());
        return userPort.updateUser(id,user);
    }

    @Override
    public User saveUser(User user) {
        logger.debug("Retrieve user id");
        String id= user.getId();
        logger.debug("Instantiate avertissement with 0");
        user.setAvertissement(0);
        logger.debug("Instantiate blackListed with false");
        user.setBlackListed(false);
        logger.debug("Check if cart with user id exist");
        if(!cartPort.availableCart(id)){
            logger.debug("Create cart to the user id "+id);
            cartPort.createCart(id);
        }
        logger.debug("Check if preference List with user id exist");

        if(!preferencePort.availablePreference(id)){
            logger.debug("Create preference List to the user id "+id);

            preferencePort.createPrefence(id);
        }

        logger.info("Create user "+user.toString()+" and his cart and his preference list if they don't exist");
        return userPort.saveUser(user);
    }

    @Override
    public User addAddress(String id, Address address) {
        logger.debug("Check if address Already exist "+address.toString());
        if (addressAvailable(id, address)) {
            logger.debug("Retrieve user with id "+id);
            User user = userPort.getUser(id);
            logger.debug("Retrieve user address ");
            List<Address> addresses = user.getAddress();
            logger.debug("Check if user address is null");
            if (addresses == null) {
                logger.debug("Instantiate user address");
                addresses = new ArrayList<>();
            }
            logger.debug("add address "+address+" to user addresses");
            addresses.add(address);
            logger.debug("Attribute the new addresses list "+addresses.toString()+" to user with id "+id );
            user.setAddress(addresses);
            logger.info("Add address "+address+" to user with id "+id);
            userPort.saveUser(user);
            return user;
        } else {
            logger.error("Address Already exist");
            throw new IllegalArgumentException("L'adresse existe déjà pour l'utilisateur.");
        }
    }

    @Override
    public User deleteAddress(UUID idAddress, String id) {
        logger.debug("Retrieve user with id "+id);
        User user = userPort.getUser(id);
        logger.debug("Retrieve user address ");

        List<Address> addresses = user.getAddress();
        logger.debug("remove address "+idAddress+" from user addresses");

        addresses.removeIf(address -> address.getId().equals(idAddress));
        logger.debug("Attribute the new addresses list "+addresses.toString()+" to user with id "+id );

        user.setAddress(addresses);
        userPort.saveUser(user);
        logger.info("Remove address "+idAddress+" to user with id "+id);

        return user;
    }



    public boolean isAvailable(String id){
        logger.debug("Check if user Already exist with id "+id);

        return userPort.isAvailable(id);
    }

    public boolean addressAvailable(String id, Address newAddress) {
        logger.trace("Retrieve user with id "+id);

        User user = userPort.getUser(id);
        logger.trace("Retrieve user address ");

        List<Address> addresses = user.getAddress();
        logger.trace("Check if user address is null");

        if (addresses != null) {
            logger.trace("Check if one of address are null");

            for (Address existingAddress : addresses) {
                if (existingAddress != null && existingAddress.equals(newAddress)) {
                    return false; // Address already exists
                }
            }
        }
        logger.debug("Check if user with id "+id+" address is null");

        return true; // Address is available
    }

    @Override
    public User getUser(String id) {
        logger.debug("Check if user with id "+id+" exist");

        if(isAvailable(id)){
            logger.info("Retrieve user with id "+id);
            return userPort.getUser(id);
        }
        else{
            logger.error("User with id "+id+" doesn't exist");
            throw new UserNotFoundException(id);
        }
    }
    @Override
    public List<User> listUser() {
        logger.info("Retrieve all Users");
        return userPort.listUser();
    }


    public User loginWithGoogle(User user) throws IllegalAccessException {
        logger.debug("Check if user Already exist  ");

        if(isAvailable(user.getId())){
            logger.debug("Retrieve user with id "+user.getId());
            User user1=userPort.getUser(user.getId());
            logger.debug("Check if user is blackListed  ");
            if(user1.isBlackListed()){
                logger.error("User "+user.toString()+" is blackListed");
                throw new IllegalAccessException("Can't be connected");
            }
            else{

                logger.debug("Check if cart with user id exist");

                if(!cartPort.availableCart(user.getId()))
                {
                    logger.debug("Create cart to the user id "+user.getId());

                    cartPort.createCart(user.getId());
                }
                logger.debug("Check if preference List with user id exist");

                if(!preferencePort.availablePreference(user.getId()))
                {
                    logger.debug("Create preference List to the user id "+user.getId());

                    preferencePort.createPrefence(user.getId());
                }
                logger.info("User "+user.toString()+" and his cart and his preference list exist and he loged in");

                return user1;
            }
        }
        else{
            logger.info("User "+user.toString()+" and his cart and his preference list doesn't exist and we create them and he loged in");

            return this.saveUser(user);
        }
    }

    @Override
    public User avertirUser(String id) {
        logger.debug("Retrieve user with id "+id);

        User user = userPort.getUser(id);
        logger.debug("Retrieve user avertissements ");

        int avertissements= user.getAvertissement();
        logger.debug("Check if user avertissements between 0 and 2");

        if(avertissements>=0 && avertissements<=2){


            avertissements +=1;
            logger.debug("Attribute the new avertissemets ");
            logger.info("Add user avertissements to "+user.toString()+" now they are "+avertissements);
            user.setAvertissement(avertissements);
        }
       if(avertissements>=3){
           logger.info("User "+user.toString()+" is blocked");

           user.setBlackListed(true);
        }




        return userPort.saveUser(user);
    }

    @Override
    public User blacklisterUser(String id) {
        User user=userPort.getUser(id);
        user.setBlackListed(true);

        logger.info("User with id"+id+" is blocked");

        return userPort.saveUser(user);
    }

}
