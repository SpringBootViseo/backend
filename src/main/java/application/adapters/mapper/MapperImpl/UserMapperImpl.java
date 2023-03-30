package application.adapters.mapper.MapperImpl;

import application.adapters.persistence.entity.UserEntity;
import application.adapters.mapper.UserMapper;
import application.adapters.web.presenter.UserDTO;
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

    @Override
    public UserDTO userToUserDTO(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getPhone() );
    }

    @Override
    public User userDtoToUser(UserDTO userDTO) {
        return new User(userDTO.getId(), userDTO.getName(), userDTO.getEmail(), userDTO.getPhone() );

    }

}
