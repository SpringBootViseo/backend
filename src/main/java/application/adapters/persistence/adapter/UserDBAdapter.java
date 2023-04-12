package application.adapters.persistence.adapter;

import application.adapters.exception.UserAlreadyExistsException;
import application.adapters.exception.UserNotFoundException;
import application.adapters.mapper.mapperImpl.UserMapperImpl;
import application.adapters.persistence.entity.UserEntity;
import application.adapters.persistence.repository.UserRepository;
import application.domain.User;
import application.port.out.UserPort;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class UserDBAdapter implements UserPort {
    @Autowired
    private UserRepository userRepository;
    private UserMapperImpl userMapperImpl;

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
        UserEntity userEntity = userRepository.findById(user.getId()).orElse(null);
        if (userEntity != null) {
            throw new UserAlreadyExistsException("");
        }
        // save the user
        userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setFullname(user.getName());
        userEntity.setEmail(user.getEmail());
        userEntity.setPicture(user.getPicture());
        userEntity.setNumberPhone(user.getPhone());
        return userMapperImpl.userEntityToUser (userRepository.save(userEntity));
    }

    @Override
    public User addAddress(String id, String address) {
        User user=this.getUser(id);
        if(user.getAddress() ==null )
            user.setAddress(List.of(address));
        else {
            List<String> addresses=user.getAddress();
            addresses.add(address);
            user.setAddress(addresses);
        }
        userRepository.save(userMapperImpl.userToUserEntity(user));
        return user;
    }

}
