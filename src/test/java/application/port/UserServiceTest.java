package application.port;

import application.adapters.exception.UserNotFoundException;
import application.domain.Cart;
import application.domain.User;
import application.port.out.CartPort;
import application.port.out.UserPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserPort userPort;
    @Mock
    private CartPort cartPort;
    private  String id;
    private User user;
    private Cart cart;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
       id="VQl0nhjeMgP1CAunvAt7Ff7kA2";
       user=new User(id,"Abdessamad","abdessamad@gmail.com","0612649174","Casablanca");
       cart = new Cart(id,new ArrayList<>());
    }
    @AfterEach
    public void tearDown(){
        id=null;
        user=null;
        cart=null;
    }
    @Test
    void shouldSaveUserandCreateCartWhenSaveUserWithUnavailableCart(){
        given(cartPort.availableCart(id)).willReturn(false);
        given(cartPort.createCart(id)).willReturn(cart);
        given(userPort.saveUser(user)).willReturn(user);

        User savedUser=userService.saveUser(user);
        verify(cartPort,times(1)).createCart(any());
        assertEquals(savedUser,user);
    }
    @Test
    void shouldOnlySaveUserWhenSaveUserWithAvailableCart(){
        given(cartPort.availableCart(id)).willReturn(true);
        given(userPort.saveUser(user)).willReturn(user);
        User savedUser=userService.saveUser(user);
        verify(cartPort,times(0)).createCart(any());
        assertEquals(savedUser,user);

    }
    @Test
    void shouldReturnTrueWhenisAvailableWithAvailableUser(){
        given(userPort.isAvailable(id)).willReturn(true);
        assertTrue(userService.isAvailable(id));
    }
    @Test
    void shouldReturnFalseWhenisAvailableWithUnavailableUser(){
        assertFalse(userService.isAvailable(id));
    }
    @Test
    void shouldGetUserWhenGetUserWithAvailableUser(){
        given(userPort.isAvailable(id)).willReturn(true);
        given(userPort.getUser(id)).willReturn(user);
        assertEquals(userService.getUser(id),user);
    }
    @Test
    void shouldThrowUserNotFoundExceptionWhenGetUserWithUnavailableUser(){
        given(userPort.isAvailable(id)).willReturn(false);
        assertThrows(UserNotFoundException.class,()->userService.getUser(id));
    }
    @Test
    void shouldUpdateUserWhenUpdateUserWithAvailableUser(){
        User user1= new User(id,"Abdessamad","abdessamad@gmail.com","0612649174",null);
        given(userPort.updateUser(id,user1)).willReturn(user);
        assertEquals(userService.updateUser(id,user1),user);

    }


}