package application.adapters.mapper.mapperImpl;
import application.adapters.persistence.entity.UserEntity;
import application.adapters.mapper.UserMapper;
import application.adapters.web.presenter.UserAddressDTO;
import application.adapters.web.presenter.UserDTO;
import application.adapters.web.presenter.UserPhoneDTO;
import application.domain.Address;
import application.domain.User;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class UserMapperImpl implements UserMapper {
    AddressMapperImpl addressMapper = new AddressMapperImpl();    @Override
    public UserEntity userToUserEntity(User user) {
        return new UserEntity(user.getId(), user.getName(), user.getEmail(), user.getPhone(), user.getPicture(),addressMapper.listAddressToAddressEntity(user.getAddress()), user.getAvertissement(), user.isBlackListed());
    }
    @Override
    public User userEntityToUser(UserEntity userEntity) {
        return new User(userEntity.getId(), userEntity.getFullname(), userEntity.getEmail(), userEntity.getNumberPhone(), userEntity.getPicture(),addressMapper.listaddressEntityToLisAddress(userEntity.getAddress()) , userEntity.getAvertissement(), userEntity.isBlackListed());
    }
    @Override
    public UserDTO userToUserDTO(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getPhone(), user.getPicture(),addressMapper.listAddressTolistUserAddressDTO(user.getAddress()) , user.getAvertissement(), user.isBlackListed() );
    }
    @Override
    public User userDtoToUser(UserDTO userDTO) {
        return new User(userDTO.getId(), userDTO.getName(), userDTO.getEmail(), userDTO.getPhone(), userDTO.getPicture(), addressMapper.listUserAddressDTOTolistAddress(userDTO.getAddress()) , userDTO.getAvertissement(), userDTO.isBlackListed());
    }

    @Override
    public User userUpdateDtoToUser(UserPhoneDTO userPhoneDTO) {
        return new User(null,null,null, userPhoneDTO.getPhone(),null, null,0,false);
    }

    @Override
        public Address userAddressDtoToAddress(UserAddressDTO userAddressDTO) {
            Address address = new Address(userAddressDTO.getId(),userAddressDTO.getStreet(), userAddressDTO.getCity(), userAddressDTO.getState());
            return address;
    }




    @Override
    public List<User> usersToDocument(MongoCollection<Document> collection) {
        List<User> userList = new ArrayList<>();
        for (Document doc : collection.find()) {
            List<Document> addressDocs = doc.getList("address", Document.class);
            List<Address> addresses = new ArrayList<>();
            for (Document addressDoc : addressDocs) {
                Address address = new Address(
                        addressDoc.get("id", UUID.class),
                        addressDoc.getString("street"),
                        addressDoc.getString("city"),
                        addressDoc.getString("state")
                );
                System.out.println("address   :"+address);
                addresses.add(address);
            }

            User user = new User(
                    doc.getString("_id"),
                    doc.getString("fullname"),
                    doc.getString("email"),
                    doc.getString("numberPhone"),
                    doc.getString("picture"),
                    addresses,
                    doc.getInteger("avertissement"),
                    doc.getBoolean("blackListed")
            );

            userList.add(user);
        }
        System.out.println("userList   :"+userList);
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

