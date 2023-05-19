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

    @Override
    public User updateUser(String id, User user) {
        if(userRepository.findById(id).isPresent()){
            UserEntity savedUser=userRepository.findById(id).get();
            savedUser.setNumberPhone(user.getPhone());
            UserEntity result=userRepository.save(savedUser);
            return userMapperImpl.userEntityToUser(result);
        }
        else throw new UserNotFoundException(id);

    }

    @Override
    public boolean isAvailable(String id) {
        return userRepository.findById(id).isPresent();
    }


    @Override
    public User getUser(String id) {
        if(isAvailable(id))
             return userMapperImpl.userEntityToUser(userRepository.findById(id).get());
        else throw new UserNotFoundException(id);
    }

    @Override
    public User saveUser(User user) throws UserAlreadyExistsException {
            UserEntity userEntity = new UserEntity(user.getId(), user.getName(), user.getEmail(), user.getPhone(), user.getPicture(), addressMapper.listAddressToAddressEntity(user.getAddress()), user.getAvertissement(), user.isBlackListed());
            return userMapperImpl.userEntityToUser (userRepository.save(userEntity));



    }

    @Override
    public List<User> listUser() {
        MongoCollection<Document> collection=mongoConfig.getAllDocuments("Users");
        return userMapperImpl.usersToDocument(collection);
    }

}
