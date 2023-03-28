package application.adapters.persistence.adapter;

import application.adapters.persistence.entity.UserEntity;
import application.adapters.persistence.mapper.MapperImpl.UserMapperImpl;
import application.adapters.persistence.mapper.UserMapper;
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
    public User saveUser(User user) {
        UserEntity userEntity = userMapperImpl.userToUserEntity (user);
        UserEntity userEntitySaved = userRepository.save(userEntity);
        return userMapperImpl.userEntityToUser (userEntitySaved);
    }
}
