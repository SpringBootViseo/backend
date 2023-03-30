//import application.adapters.mapper.UserMapper;
//import application.adapters.web.UserController;
//import application.domain.User;
//import application.port.in.UserUseCase;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static net.bytebuddy.matcher.ElementMatchers.is;
//import static org.mockito.BDDMockito.given;
//import static org.springframework.http.RequestEntity.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(UserController.class)
//public class UserControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private UserUseCase userUseCase;
//
//    @Autowired
//    private UserMapper userMapper;
//
//    @Test
//    public void testCreateUser() throws Exception {
//        User user = new User();
//        user.setName("testuser");
//        user.setEmail("email@gmail.com");
//        user.setId("1345679879qdgzqg978qz");
//        user.setPhone("0611111111");
//
//        given(userUseCase.saveUser(user)).willReturn(user);
//
//        mockMvc.perform(post("/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(JsonUtil.toJson(user)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.username", is(user.getName())))
//                .andExpect(jsonPath("$.email", is(user.getEmail())));
//    }
//}
