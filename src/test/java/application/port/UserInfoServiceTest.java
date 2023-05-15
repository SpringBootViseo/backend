package application.port;

import application.domain.Role;
import application.domain.UserInfo;
import application.port.out.UserInfoPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

class UserInfoServiceTest {
    @InjectMocks
    UserInfoService userInfoService;
    @Mock
    UserInfoPort userInfoPort;
    String email;
    UserInfo userInfo;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        email="a.a@mail.com";
        userInfo=new UserInfo(1,"a","a",email,"a",new ArrayList<>(), Role.USER);
    }
    @AfterEach
    void tearDown(){
        email=null;
        userInfo=null;
    }
    @DisplayName("return user when getUserEmail")
    @Test
    void test(){
        given(userInfoPort.getUserByEmail(email)).willReturn(userInfo);
        UserInfo result=userInfoService.getUserByEmail(email);
        assertEquals(result,userInfo);
    }
    @DisplayName("return user when addUser")
    @Test
    void test1(){
        given(userInfoPort.addUserInfo(userInfo)).willReturn(userInfo);
        UserInfo result=userInfoService.addUserInfo(userInfo);
        assertEquals(result,userInfo);

    }
    @DisplayName("return true when isUser")
    @Test
    void test2(){
        given(userInfoPort.isUser(email)).willReturn(true);
        assertTrue(userInfoService.isUser(email));
    }
    @DisplayName("return false when isUser with inexistant email")
    @Test
    void test3(){
        given(userInfoPort.isUser(email)).willReturn(false);
        assertFalse(userInfoService.isUser(email));
    }

}