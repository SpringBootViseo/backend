package application.port;

import application.domain.*;
import application.port.out.AuthenticationPort;
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
import static org.mockito.Mockito.*;

class AuthenticationServiceTest {
    @InjectMocks
    AuthenticationService authenticationService;
    @Mock
    AuthenticationPort authenticationPort;
    UserInfo userInfo;
    RegisterRequest registerRequest;
    AuthenticationRequest authenticationRequest;
    AuthenticationResponse authenticationResponse;

    String token;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);

        userInfo = new UserInfo(1,"Steve","Jobs","S.jobs@apple.com","test",new ArrayList<>(), Role.USER);
        registerRequest=new RegisterRequest("Steve","Jobs","S.jobs@apple.com","test",Role.USER);
        authenticationRequest=new AuthenticationRequest("S.jobs@apple.com","test");
        authenticationResponse=new AuthenticationResponse();
        token="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhQGFiZC5jb20iLCJpYXQiOjE2ODM5MTUxMDAsImV4cCI6MTY4NDAwMTUwMH0.RAX3_CLqiUzNDirS4QNXEgqM_tDIqBkBbXFMgNer-0Y";
        authenticationResponse=new AuthenticationResponse(token,token);

    }
    @AfterEach
    void tearDown(){
        userInfo=null;
        registerRequest=null;
        authenticationResponse=null;
        authenticationRequest=null;
        token=null;
        authenticationResponse=null;
    }
    @DisplayName("should save user and token when saveUserToken and call it once")
    @Test
    void test(){
        doNothing().when(authenticationPort).saveUserToken(userInfo,token);
        authenticationService.saveUserToken(userInfo,token);
        verify(authenticationPort,times(1)).saveUserToken(userInfo,token);
    }
    @DisplayName("should revoke all token of user when revokeAllUserTokens and call it once")
    @Test
    void test1(){
        doNothing().when(authenticationPort).revokeAllUserTokens(userInfo);
        authenticationService.revokeAllUserTokens(userInfo);
        verify(authenticationPort,times(1)).revokeAllUserTokens(userInfo);
    }
    @DisplayName("should return authenticationResponse when registration")
    @Test
    void test2(){
        given(authenticationPort.register(registerRequest)).willReturn(authenticationResponse);
        AuthenticationResponse response=authenticationService.register(registerRequest);
        assertEquals(response,authenticationResponse);
    }
    @DisplayName("should return authenticationResponse when authenticate ")
    @Test
    void test3(){
        given(authenticationPort.authenticate(authenticationRequest)).willReturn(authenticationResponse);
        AuthenticationResponse response=authenticationService.authenticate(authenticationRequest);
        assertEquals(response,authenticationResponse);
    }
    @DisplayName("should return authenticationResponse when refreshToken")
    @Test
    void test4(){
        given(authenticationPort.refreshToken(token)).willReturn(authenticationResponse);
        AuthenticationResponse response=authenticationService.refreshToken(token);
        assertEquals(response,authenticationResponse);
    }

}