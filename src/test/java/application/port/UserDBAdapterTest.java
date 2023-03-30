package application.port;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.util.Optional;

import application.adapters.persistence.adapter.UserDBAdapter;
import application.adapters.web.presenter.UserDTO;
import application.port.out.UserPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import application.adapters.exception.UserAlreadyExistsException;
import application.adapters.persistence.entity.UserEntity;
import application.adapters.persistence.repository.UserRepository;
import application.adapters.mapper.MapperImpl.UserMapperImpl;
import application.domain.User;

import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserDBAdapterTest {
    @Mock
    private UserPort userPort;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDBAdapter userDBAdapter;

    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userPort);
    }
    @Test
    public void testSaveUser_Success()  {
        User user = new User("7dogOFiqeWMDCXpbgINjz0iiMSr1", "Otman Otman", "otman@example.com", "0666666666");
        when(userPort.saveUser(any(User.class))).thenReturn(user);
        User savedUser = userService.saveUser(user);
        assertEquals(user, savedUser);
    }

    @Test
    public void testSaveUser_UserAlreadyExists() {
        User user = new User("aaaaaaaaaaaaaa","John Doe", "johndoe@example.com","12222222");
        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(userEntity));

        Assertions.assertThrows(UserAlreadyExistsException.class, () -> {
            userDBAdapter.saveUser(user);
        });
    }


}


















