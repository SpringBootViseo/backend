package application.adapters.web;

import application.domain.User;
import application.port.in.UserUseCase;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    @Autowired
    private final UserUseCase userUseCase;
    @PostMapping("")
    public User saveUser(@RequestBody User user){
        return userUseCase.saveUser(user);
    }
}
