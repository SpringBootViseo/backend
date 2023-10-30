package application.adapters.persistence.adapter;

import application.adapters.exception.UserAlreadyExistsException;
import application.adapters.exception.UserNotFoundException;
import application.adapters.mapper.mapperImpl.AddressMapperImpl;
import application.adapters.mapper.mapperImpl.UserMapperImpl;
import application.adapters.persistence.MongoConfig;
import application.adapters.persistence.entity.UserEntity;
import application.adapters.persistence.repository.UserRepository;
import application.domain.User;
import application.port.out.UserPort;
import org.bson.Document;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.mongodb.client.MongoCollection;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class UserDBAdapter implements UserPort {
    @Autowired
    private UserRepository userRepository;
    private UserMapperImpl userMapperImpl;
    private AddressMapperImpl addressMapper;
    private final MongoConfig mongoConfig;
    private static final Logger logger = LoggerFactory.getLogger(UserDBAdapter.class);

    @Override
    public User updateUser(String id, User user) {
        logger.info("Updating user with ID: {}", id);
        logger.trace("Checking if user with ID: {} already exists", id);

        if (userRepository.findById(id).isPresent()) {
            UserEntity savedUser = userRepository.findById(id).get();
            logger.debug("Getting user with ID: {}", id);
            savedUser.setNumberPhone(user.getPhone());
            logger.debug("Setting user's phone number : {}",user.getPhone());
            UserEntity result = userRepository.save(savedUser);
            logger.debug("Saving updated user");
            logger.info("User with ID: {} updated successfully", id);
            return userMapperImpl.userEntityToUser(result);
        } else {
            logger.info("User with ID: {} not found", id);
            throw new UserNotFoundException(id);
        }
    }

    @Override
    public boolean isAvailable(String id) {
        logger.info("Checking if user with ID: {} is available", id);
        boolean available = userRepository.findById(id).isPresent();
        logger.debug("User with ID: {} is {}available", id, available ? "" : "not ");
        return available;
    }

    @Override
    public User getUser(String id) {
        logger.info("Getting user with ID: {}", id);

        if (isAvailable(id)) {
            UserEntity userEntity = userRepository.findById(id).get();
            logger.debug("User with ID: {} found", id);
            return userMapperImpl.userEntityToUser(userEntity);
        } else {
            logger.info("User with ID: {} not found", id);
            throw new UserNotFoundException(id);
        }
    }


    @Override
    public User saveUser(User user) throws UserAlreadyExistsException {
        logger.info("Saving user with ID: {}", user.getId());
        logger.trace("Checking if user with id : {} already exist ",user.getId());
        if (!userRepository.findById(user.getId()).isPresent()) {
            UserEntity userEntity = new UserEntity(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getPhone(),
                    user.getPicture(),
                    addressMapper.listAddressToAddressEntity(user.getAddress()),
                    user.getAvertissement(),
                    user.isBlackListed()
            );
            logger.debug("Creating UserEntity with id : {}",user.getId());
            UserEntity savedUserEntity = userRepository.save(userEntity);
            logger.debug("Saving UserEntity with id : {}",user.getId());
            User savedUser = userMapperImpl.userEntityToUser(savedUserEntity);
            logger.debug("Converting UserEntity to User");
            logger.info("User with ID: {} saved successfully", user.getId());

            return savedUser;
        } else {
            logger.error("User with ID: {} already exists", user.getId());
            throw new UserAlreadyExistsException("User already exists!");
        }
    }

    @Override
    public List<User> listUser() {
        logger.info("Listing all users");

        logger.debug("Retrieving user documents from the database");
        MongoCollection<Document> collection = mongoConfig.getAllDocuments("Users");

        List<User> users = userMapperImpl.usersToDocument(collection);

        logger.debug("Converting user documents to User objects");

        logger.info("Listed all users successfully");

        return users;
    }



}
