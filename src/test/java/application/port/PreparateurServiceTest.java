package application.port;

import application.adapters.exception.UserAlreadyExistsException;
import application.domain.Preparateur;
import application.port.out.PreparateurPort;
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

class PreparateurServiceTest {
    @InjectMocks
    PreparateurService preparateurService;
    @Mock
    PreparateurPort preparateurPort;
    Preparateur preparateur;
    String email;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        email="abdessamad@mail.com";
        preparateur=new Preparateur("abdessmad","badrane",email);
    }
    @AfterEach
    void tearDown(){
        email=null;
        preparateur=null;
    }
    @DisplayName("should add preparateur when add preparateur")
    @Test
    void fonction(){
        given(preparateurPort.addPreparateur(preparateur)).willReturn(preparateur);
        given(preparateurPort.isPreparateur(email)).willReturn(false);
        Preparateur result=preparateurService.addPreparateur(preparateur);
        assertEquals(result,preparateur);
    }
    @DisplayName("should Throw UserAlreadyExistException when add preparateur with elready existing email")
    @Test
    void fonction1(){
        given(preparateurPort.isPreparateur(email)).willReturn(true);
        assertThrows(UserAlreadyExistsException.class,()->preparateurService.addPreparateur(preparateur));
    }
    @DisplayName("should getPreparateur when getPreparateur with Preparateur existing email")
    @Test
    void fonction2(){
        given(preparateurPort.isPreparateur(email)).willReturn(true);
        given(preparateurPort.getPreparateur(email)).willReturn(preparateur);
        Preparateur result=preparateurService.getPreparateur(email);
        assertEquals(result,preparateur);
    }
    @DisplayName("should throw NoSuchElementException when getPreparateur with an inexistant email")
    @Test
    void fonction3(){
        given(preparateurPort.isPreparateur(email)).willReturn(false);
        assertThrows(NoSuchElementException.class,()->preparateurService.getPreparateur(email));
    }
    @DisplayName("should return True if exist preparateur with email")
    @Test
    void fonction4(){
        given(preparateurPort.isPreparateur(email)).willReturn(true);
        assertTrue(preparateurService.isPreparateur(email));
    }
    @DisplayName("should return False if no prepareteur with such email exist")
    @Test
    void fonction5(){
        given(preparateurPort.isPreparateur(email)).willReturn(false);
        assertFalse(preparateurService.isPreparateur(email));
    }

}