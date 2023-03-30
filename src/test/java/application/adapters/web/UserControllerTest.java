package application.adapters.web;

import application.adapters.mapper.MapperImpl.UserMapperImpl;
import application.adapters.mapper.UserMapper;
import application.adapters.persistence.adapter.UserDBAdapter;
import application.adapters.persistence.repository.UserRepository;
import application.adapters.web.presenter.UserDTO;
import application.domain.User;
import application.port.UserService;
import application.port.in.UserUseCase;
import application.port.out.UserPort;
import jakarta.validation.UnexpectedTypeException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;
import static com.mongodb.internal.connection.tlschannel.util.Util.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
public class UserControllerTest {
    @InjectMocks
    private UserController userController;
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserUseCase userUseCase;
    @Mock
    private UserMapperImpl userMapperImpl;
    private User user;
    private UserDTO userDTO;
    private String id;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        id="123465879";
        userDTO=new UserDTO("test","test","test","test");
        user=new User("test","test","test","test");
    }

    @AfterEach
    void tearDown() {
        id=null;
        user=null;
        userDTO=null;
    }


    private UserService userService;
    @DisplayName("Unit test for create User with invalid user")
    @Test
    void shoulThrowUnexpectedTypeExceptionWhenCreateUserWithInvalidUser(){

        userDTO.setName(null);
        user.setName(null);
        given(userMapperImpl.userToUserDTO(user)).willReturn(userDTO);
        given(userMapperImpl.userDtoToUser(userDTO)).willReturn(user);
        when(userUseCase.saveUser(user)).thenThrow(new UnexpectedTypeException("test exception"));
        try {
            userController.createUser(userDTO);
        } catch (ResponseStatusException e) {
            assertEquals(400, e.getBody().getStatus());
            assertEquals("Bad argument", e.getReason());
            assertTrue(e.getCause() instanceof UnexpectedTypeException);
    }

}
}



