package application.adapters.mapper.mapperImpl;
import application.adapters.persistence.entity.UserEntity;
import application.adapters.mapper.UserMapper;
import application.adapters.web.presenter.UserDTO;
import application.adapters.web.presenter.UserPhoneDTO;
import application.domain.User;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public UserEntity userToUserEntity(User user) {
        return new UserEntity(user.getId(), user.getName(), user.getEmail(), user.getPhone(), user.getPicture(),user.getAddress(), user.getAvertissement(), user.isBlackListed());
    }
    @Override
    public User userEntityToUser(UserEntity userEntity) {
        return new User(userEntity.getId(), userEntity.getFullname(), userEntity.getEmail(), userEntity.getNumberPhone(), userEntity.getPicture(), userEntity.getAddress(), userEntity.getAvertissement(), userEntity.isBlackListed());
    }
    @Override
    public UserDTO userToUserDTO(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getPhone(), user.getPicture(), user.getAddress(), user.getAvertissement(), user.isBlackListed() );
    }
    @Override
    public User userDtoToUser(UserDTO userDTO) {
        return new User(userDTO.getId(), userDTO.getName(), userDTO.getEmail(), userDTO.getPhone(), userDTO.getPicture(), userDTO.getAddress() , userDTO.getAvertissement(), userDTO.isBlackListed());

    }

    @Override
    public User userUpdateDtoToUser(UserPhoneDTO userPhoneDTO) {
        return new User(null,null,null, userPhoneDTO.getPhone(),null, null,0,false);
    }
    @Override
    public List<User> usersToDocument(MongoCollection<Document> collection) {
        List<User> userList=new ArrayList<>();
        for(Document doc:collection.find()){
            userList.add(new User(doc.getString("_id"),doc.getString("fullname"), doc.getString("email"),doc.getString("numberPhone"),doc.getString("picture"),doc.getList("address", String.class), doc.getInteger("avertissement"),doc.getBoolean("blackListed") ));
        }
        return userList;
    }

    @Override
    public List<UserDTO> listUserToListUserDTO(List<User> userList) {
        List <UserDTO> userDTOList=new ArrayList<>();
        for(User user:userList){
            userDTOList.add(this.userToUserDTO(user));
        }
        return userDTOList;
    }


}