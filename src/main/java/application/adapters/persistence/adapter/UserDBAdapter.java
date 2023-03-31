package application.adapters.persistence.adapter;

import application.adapters.exception.UserAlreadyExistsException;
import application.adapters.mapper.mapperImpl.UserMapperImpl;
import application.adapters.persistence.entity.UserEntity;
import application.adapters.persistence.repository.UserRepository;
import application.domain.User;
import application.port.out.UserPort;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserDBAdapter implements UserPort {
    @Autowired
    private UserRepository userRepository;
    private UserMapperImpl userMapperImpl;
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
        userEntity.setNumberPhone(user.getPhone());
        return userMapperImpl.userEntityToUser (userRepository.save(userEntity));
    }

}
