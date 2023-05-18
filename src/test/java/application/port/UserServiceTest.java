package application.port;

import application.adapters.exception.UserNotFoundException;
import application.domain.Address;
import application.domain.Cart;
import application.domain.Preference;
import application.domain.User;
import application.port.out.CartPort;
import application.port.out.PreferencePort;
import application.port.out.UserPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserPort userPort;
    @Mock
    private CartPort cartPort;
    @Mock
    private PreferencePort preferencePort;
    private  String id;
    private User user;
    private Cart cart;
    private Address address;

    private Preference preference;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
       id="VQl0nhjeMgP1CAunvAt7Ff7kA2";
       user=new User(id,"Abdessamad","abdessamad@gmail.com","0612649174",null,null,0,false);
       cart = new Cart(id,new ArrayList<>());
       preference=new Preference(id,new ArrayList<>());
    }
    @AfterEach
    public void tearDown(){
        id=null;
        user=null;
        cart=null;
        preference=null;
    }
    @Test
    void shouldSaveUserandCreateCartWhenSaveUserWithUnavailableCart(){
        given(cartPort.availableCart(id)).willReturn(false);
        given(cartPort.createCart(id)).willReturn(cart);
        given(userPort.saveUser(user)).willReturn(user);
        given((preferencePort.availablePreference(id))).willReturn(false);
        given(preferencePort.createPrefence(id)).willReturn(preference);
        User savedUser=userService.saveUser(user);
        verify(cartPort,times(1)).createCart(any());
        assertEquals(savedUser,user);
    }
    @Test
    void shouldOnlySaveUserWhenSaveUserWithAvailableCart(){
        given(cartPort.availableCart(id)).willReturn(true);
        given(userPort.saveUser(user)).willReturn(user);
        given(preferencePort.availablePreference(id)).willReturn(true);
        given(preferencePort.createPrefence(id)).willReturn(preference);
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
        User user1= new User(id,"Abdessamad","abdessamad@gmail.com","0612649174",null,null,0,false);
        given(userPort.updateUser(id,user1)).willReturn(user);
        assertEquals(userService.updateUser(id,user1),user);

    }
    @DisplayName("add 1 to avertissement when avertie if avertissement <2")
    @Test
    void fct(){
        User user = new User();
        user.setId("sampleId");
        user.setAvertissement(1);
        user.setBlackListed(false);

        // Mock the getUser and saveUser methods of the userPort
        given(userPort.getUser("sampleId")).willReturn(user);
        given(userPort.saveUser(user)).willReturn(user);

        // Invoke the updateAvertissement method
        User result = userService.avertirUser("sampleId");

        // Verify that getUser and saveUser were called with the correct arguments
        verify(userPort,times(1)).getUser("sampleId");
        verify(userPort,times(1)).saveUser(user);

        // Verify that the avertissement is incremented
       assertEquals(2, result.getAvertissement());

        // Verify that the user is not blacklisted
       assertFalse(result.isBlackListed());
    }
    @DisplayName("black list when avertie if avertissement >2")
    @Test
    void fct1(){
        User user = new User();
        user.setId("sampleId");
        user.setAvertissement(2);
        user.setBlackListed(false);

        // Mock the getUser and saveUser methods of the userPort
        given(userPort.getUser("sampleId")).willReturn(user);
        given(userPort.saveUser(user)).willReturn(user);

        // Invoke the updateAvertissement method
        User result = userService.avertirUser("sampleId");

        // Verify that getUser and saveUser were called with the correct arguments
        verify(userPort,times(1)).getUser("sampleId");
        verify(userPort,times(1)).saveUser(user);

        // Verify that the avertissement is incremented
        assertEquals(3, result.getAvertissement());

        // Verify that the user is not blacklisted
        assertTrue(result.isBlackListed());
    }
    @DisplayName("blackList user when blacklist user")
    @Test
    void fct2(){
        User user = new User();
        user.setId("sampleId");
        user.setBlackListed(false);

        // Mock the getUser and saveUser methods of the userPort
        given(userPort.getUser("sampleId")).willReturn(user);
        given(userPort.saveUser(user)).willReturn(user);

        // Invoke the blacklisterUser method
        User result = userService.blacklisterUser("sampleId");

        // Verify that getUser and saveUser were called with the correct arguments
        verify(userPort,times(1)).getUser("sampleId");
        verify(userPort,times(1)).saveUser(user);

        // Verify that the user object is blacklisted
        assertTrue(result.isBlackListed());
    }

    @Test
    public void shouldReturnTrueIfAddressIsAvailable() {
        Address address = new Address(UUID.randomUUID(),"Street 1", "City", "12345");
        List<Address> addresses = new ArrayList<>();
        addresses.add(new Address(UUID.randomUUID(),"Street 2", "City", "54321"));
        user.setAddress(addresses);

        given(userPort.getUser(id)).willReturn(user);

        boolean result = userService.addressAvailable(id, address);

        verify(userPort, times(1)).getUser(id);
        assertEquals(true, result);
    }

//    @Test
//    void shouldAddAddressWhenAvailable() {
//        // Arrange
//        List<Address> addresses = new ArrayList<>();
//        addresses.add(address);
//        user.setAddress(addresses);
//        when(userPort.getUser(eq(id))).thenReturn(user);
//        when(userPort.saveUser(user)).thenReturn(user);
//
//        // Act
//        User result = userService.addAddress(id, address);
//
//        // Assert
//        verify(userPort, times(1)).getUser(eq(id));
//        verify(userPort, times(1)).saveUser(user);
//        assertEquals(user, result);
//        assertEquals(addresses, result.getAddress());
//
//        // Verify that addressAvailable was called
//        verify(userService, times(1)).addressAvailable(eq(id), any(Address.class));
//    }












}