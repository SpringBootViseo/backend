package application.adapters.persistence.mapper;

import application.adapters.persistence.entity.UserEntity;
import application.domain.User;
import org.mapstruct.Mapper;
@Mapper
public interface UserMapper {
    UserEntity userToUserEntity (User user);
    User userEntityToUser(UserEntity userEntity);

}
