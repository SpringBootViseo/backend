package application.adapters.mapper;

import application.adapters.persistence.entity.UserEntity;
import application.adapters.web.presenter.UserDTO;
import application.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserEntity userToUserEntity(User user);

    User userEntityToUser(UserEntity userEntity);

    UserDTO userToUserDTO(User user);
    User userDtoToUser (UserDTO userDTO);
}
