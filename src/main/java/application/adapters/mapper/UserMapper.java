package application.adapters.mapper;


import application.adapters.persistence.entity.UserEntity;
import application.adapters.web.presenter.UserDTO;
import application.adapters.web.presenter.UserPhoneDTO;
import application.domain.User;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper
public interface UserMapper {
    UserEntity userToUserEntity (User user);
    User userEntityToUser(UserEntity userEntity);

    UserDTO userToUserDTO(User user);
    User userDtoToUser (UserDTO userDTO);
    User userUpdateDtoToUser(UserPhoneDTO userPhoneDTO);

    List<User> usersToDocument(MongoCollection<Document> collection);
    List<UserDTO> listUserToListUserDTO(List<User> userList);
}
