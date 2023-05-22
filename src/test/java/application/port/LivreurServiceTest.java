package application.port;

import application.adapters.exception.UserAlreadyExistsException;
import application.domain.Livreur;
import application.port.out.LivreurPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

class LivreurServiceTest {
    @InjectMocks
    LivreurService livreurService;
    @Mock
    LivreurPort livreurPort;
    Livreur livreur;
    String email;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        email="a@a.com";
        livreur=new Livreur("a","a",email);
    }
    @AfterEach
    void tearDown(){
        email=null;
        livreur=null;
    }
    @DisplayName("given livreur with Unavailable livreur email in DB to addLivreur will add Livreur")
    @Test
    void func(){
        given(livreurPort.isLivreur(email)).willReturn(false);
        given(livreurPort.addLivreur(livreur)).willReturn(livreur);
        Livreur result=livreurService.addLivreur(livreur);
        assertEquals(livreur,result);
    }
    @DisplayName("given livreur with available livreur email in DB to addLivreur will Throw UserAlreadyExist Exception")
    @Test
    void func1(){
        given(livreurPort.isLivreur(email)).willReturn(true);

        assertThrows(UserAlreadyExistsException.class,()->livreurService.addLivreur(livreur));
    }
    @DisplayName("given email with available livreur email in DB to getLivreur will return livreur")
    @Test
    void func2(){
        given(livreurPort.isLivreur(email)).willReturn(true);
        given(livreurPort.getLivreur(email)).willReturn(livreur);
        Livreur livreur1=livreurService.getLivreur(email);
        assertEquals(livreur1,livreur);
    }
    @DisplayName("given email with unavailable livreur email in DB to getLivreur will Throw NoSuchElementException")
    @Test
    void func3(){
        given(livreurPort.isLivreur(email)).willReturn(false);
        assertThrows(NoSuchElementException.class,()->livreurService.getLivreur(email));

    }
    @DisplayName("given email with unavailable livreur email in DB to isLivreur will return False")
    @Test
    void func4(){
        given(livreurPort.isLivreur(email)).willReturn(false);
        assertFalse(livreurService.isLivreur(email));
    }
    @DisplayName("given email with available livreur email in DB to isLivreur will return True")
    @Test
    void func5(){
        given(livreurPort.isLivreur(email)).willReturn(true);
        assertTrue(livreurService.isLivreur(email));
    }

}