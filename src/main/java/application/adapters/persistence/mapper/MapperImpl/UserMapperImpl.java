package application.adapters.persistence.mapper.MapperImpl;

import application.adapters.persistence.entity.UserEntity;
import application.adapters.persistence.mapper.UserMapper;
import application.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public UserEntity userToUserEntity(User user) {
        return new UserEntity(user.getId(), user.getName(), user.getEmail(), user.getPhone() );
    }

    @Override
    public User userEntityToUser(UserEntity userEntity) {
        return new User(userEntity.getId(), userEntity.getFullname(), userEntity.getEmail(), userEntity.getNumberPhone());
    }
}
