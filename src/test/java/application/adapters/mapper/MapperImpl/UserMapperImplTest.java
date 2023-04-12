package application.adapters.mapper.MapperImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import application.adapters.mapper.mapperImpl.UserMapperImpl;
import org.junit.jupiter.api.Test;

import application.adapters.persistence.entity.UserEntity;
import application.adapters.web.presenter.UserDTO;
import application.domain.User;

public class UserMapperImplTest {

    private final UserMapperImpl userMapper = new UserMapperImpl();
    private final User testUser = new User("1L", "John Doe", "john.doe@example.com", "1234567890",null,null);

    @Test
    public void testUserToUserEntity() {
        UserEntity userEntity = userMapper.userToUserEntity(testUser);
        assertEquals(testUser.getId(), userEntity.getId());
        assertEquals(testUser.getName(), userEntity.getFullname());
        assertEquals(testUser.getEmail(), userEntity.getEmail());
        assertEquals(testUser.getPhone(), userEntity.getNumberPhone());
    }

    @Test
    public void testUserEntityToUser() {
        UserEntity userEntity = new UserEntity("1L", "John Doe", "john.doe@example.com", "1234567890",null,null);
        User user = userMapper.userEntityToUser(userEntity);
        assertEquals(userEntity.getId(), user.getId());
        assertEquals(userEntity.getFullname(), user.getName());
        assertEquals(userEntity.getEmail(), user.getEmail());
        assertEquals(userEntity.getNumberPhone(), user.getPhone());
    }

    @Test
    public void testUserToUserDTO() {
        UserDTO userDTO = userMapper.userToUserDTO(testUser);
        assertEquals(testUser.getId(), userDTO.getId());
        assertEquals(testUser.getName(), userDTO.getName());
        assertEquals(testUser.getEmail(), userDTO.getEmail());
        assertEquals(testUser.getPhone(), userDTO.getPhone());
    }

    @Test
    public void testUserDtoToUser() {
        UserDTO userDTO = new UserDTO("1L", "John Doe", "john.doe@example.com", "1234567890",null,null);
        User user = userMapper.userDtoToUser(userDTO);
        assertEquals(userDTO.getId(), user.getId());
        assertEquals(userDTO.getName(), user.getName());
        assertEquals(userDTO.getEmail(), user.getEmail());
        assertEquals(userDTO.getPhone(), user.getPhone());
    }
}
