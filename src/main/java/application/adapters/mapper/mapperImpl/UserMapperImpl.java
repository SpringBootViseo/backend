package application.adapters.mapper.mapperImpl;
import application.adapters.persistence.entity.UserEntity;
import application.adapters.mapper.UserMapper;
import application.adapters.web.presenter.UserDTO;
import application.adapters.web.presenter.UserPhoneDTO;
import application.domain.User;
import org.springframework.stereotype.Component;
@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public UserEntity userToUserEntity(User user) {
        return new UserEntity(user.getId(), user.getName(), user.getEmail(), user.getPhone(), user.getPicture(),user.getAddress() );
    }
    @Override
    public User userEntityToUser(UserEntity userEntity) {
        return new User(userEntity.getId(), userEntity.getFullname(), userEntity.getEmail(), userEntity.getNumberPhone(), userEntity.getPicture(), userEntity.getAddress());
    }
    @Override
    public UserDTO userToUserDTO(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getPhone(), user.getPicture(), user.getAddress() );
    }
    @Override
    public User userDtoToUser(UserDTO userDTO) {
        return new User(userDTO.getId(), userDTO.getName(), userDTO.getEmail(), userDTO.getPhone(), userDTO.getPicture(), userDTO.getAddress() );

    }

    @Override
    public User userUpdateDtoToUser(UserPhoneDTO userPhoneDTO) {
        return new User(null,null,null, userPhoneDTO.getPhone(),null, null);
    }


}