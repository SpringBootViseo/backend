package application.port;

import application.domain.Token;
import application.domain.UserInfo;
import application.port.out.TokenPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

class TokenServiceTest {
    @InjectMocks
    TokenService tokenService;
    @Mock
    TokenPort tokenPort;
    String jwtToken;
    Token validToken;
    Token inValidToken;
    Integer id;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        jwtToken="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhQGFiZC5jb20iLCJpYXQiOjE2ODM5MTUxMDAsImV4cCI6MTY4NDAwMTUwMH0.RAX3_CLqiUzNDirS4QNXEgqM_tDIqBkBbXFMgNer-0Y";
        validToken=new Token(1,jwtToken,"Bearer",false,false,new UserInfo());
        inValidToken=new Token(2,jwtToken,"Bearer",true,true,new UserInfo());
        id= 1;


    }
    Token token;
    @AfterEach
    void tearDown(){
        validToken=null;
        jwtToken=null;
        inValidToken=null;
        id=null;
    }
    @DisplayName("return all valid token when given user id")
    @Test
    void test(){
        given(tokenPort.getAllValidTokenByUser(id)).willReturn(List.of(validToken));
        List<Token> result=tokenService.getAllValidTokenByUser(id);
        assertEquals(result,List.of(validToken));
    }
    @DisplayName("return token when given token string")
    @Test
    void test1(){
        given(tokenPort.getToken(jwtToken)).willReturn(validToken);
        Token result=tokenService.getToken(jwtToken);
        assertEquals(result,validToken);
    }
    @DisplayName("return  valid token when add token")
    @Test
    void test2(){
        given(tokenPort.addToken(validToken)).willReturn(validToken);
        Token result=tokenService.addToken(validToken);
        assertEquals(result,validToken);
    }
    @DisplayName("return true when given string of valid token")
    @Test
    void test3(){
        given(tokenPort.isValid(jwtToken)).willReturn(true);
        boolean result=tokenService.isValid(jwtToken);
        assertTrue(result);
    }
    @DisplayName("return false when given string of invalid token")
    @Test
    void test4(){
        given(tokenPort.isValid(jwtToken)).willReturn(false);
        boolean result=tokenService.isValid(jwtToken);
        assertFalse(result);
    }




}