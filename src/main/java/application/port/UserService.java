package application.port;

import application.adapters.exception.UserNotFoundException;
import application.domain.Address;
import application.domain.User;
import application.port.in.UserUseCase;
import application.port.out.CartPort;
import application.port.out.PreferencePort;
import application.port.out.UserPort;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements UserUseCase {
    private final UserPort userPort;
    private final CartPort cartPort;
    private final PreferencePort preferencePort;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Override
    public User updateUser(String id, User user) {
        // INFO level for a significant operation
        logger.info("Updating user with ID: {}", id);
        return userPort.updateUser(id, user);
    }

    @Override
    public User saveUser(User user) {
        // INFO level for a significant operation
        logger.info("Saving user: {}", user.getId());
        String id = user.getId();
        user.setAvertissement(0);
        user.setBlackListed(false);
        if (!cartPort.availableCart(id))
            cartPort.createCart(id);
        if (!preferencePort.availablePreference(id))
            preferencePort.createPrefence(id);

        return userPort.saveUser(user);
    }

    @Override
    public User addAddress(String id, Address address) {
        // INFO level for a significant operation
        logger.info("Adding address for user with ID: {}", id);
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
            // ERROR level for an error condition
            logger.error("Address already exists for user with ID: {}", id);
            throw new IllegalArgumentException("L'adresse existe déjà pour l'utilisateur.");
        }
    }

    @Override
    public User deleteAddress(UUID idAddress, String id) {
        // INFO level for a significant operation
        logger.info("Deleting address with ID: {} for user with ID: {}", idAddress, id);
        User user = userPort.getUser(id);
        List<Address> addresses = user.getAddress();

        addresses.removeIf(address -> address.getId().equals(idAddress));
        user.setAddress(addresses);
        userPort.saveUser(user);

        return user;
    }

    public boolean isAvailable(String id) {
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
        if (isAvailable(id)) {
            // INFO level for a significant operation
            logger.info("Getting user with ID: {}", id);
            return userPort.getUser(id);
        } else {
            // ERROR level for an error condition
            logger.error("User with ID {} not found", id);
            throw new UserNotFoundException(id);
        }
    }

    @Override
    public List<User> listUser() {
        // INFO level for a significant operation
        logger.info("Listing all users");
        return userPort.listUser();
    }

    public User loginWithGoogle(User user) throws IllegalAccessException {
        if (isAvailable(user.getId())) {
            User user1 = userPort.getUser(user.getId());
            if (user1.isBlackListed()) {
                // ERROR level for an error condition
                logger.error("User with ID {} is blacklisted", user.getId());
                throw new IllegalAccessException("Can't be connected");
            } else {
                if (!cartPort.availableCart(user.getId()))
                    cartPort.createCart(user.getId());
                if (!preferencePort.availablePreference(user.getId()))
                    preferencePort.createPrefence(user.getId);
                return user1;
            }
        } else {
            // INFO level for a significant operation
            logger.info("User with ID {} not found. Saving user.", user.getId());
            return this.saveUser(user);
        }
    }

    @Override
    public User avertirUser(String id) {
        // INFO level for a significant operation
        logger.info("Averting user with ID: {}", id);
        User user = userPort.getUser(id);

        int avertissements = user.getAvertissement();
        if (avertissements >= 0 && avertissements <= 2) {
            avertissements += 1;
            user.setAvertissement(avertissements);
        }
        if (avertissements >= 3) {
            user.setBlackListed(true);
        }

        return userPort.saveUser(user);
    }

    @Override
    public User blacklisterUser(String id) {
        // INFO level for a significant operation
        logger.info("Blacklisting user with ID: {}", id);
        User user = userPort.getUser(id);
        user.setBlackListed(true);
        return userPort.saveUser(user);
    }
}
