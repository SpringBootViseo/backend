package application.adapters.web;

import application.adapters.exception.UserAlreadyExistsException;
import application.adapters.mapper.UserMapper;
import application.adapters.web.presenter.UserDTO;
import application.domain.User;
import application.port.in.UserUseCase;
import jakarta.validation.UnexpectedTypeException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/users")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    @Autowired
    private final UserUseCase userUseCase;

    private final UserMapper userMapper;
    @PostMapping("")
    public ResponseEntity<User> createUser(@Validated @RequestBody UserDTO userDTO) {
        try {
            User savedUser = userUseCase.saveUser(userMapper.userDtoToUser(userDTO));
            return new ResponseEntity<User>(savedUser, HttpStatus.CREATED);
        }catch (UnexpectedTypeException e){
             throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad argument",e);
        } catch (UserAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User  exists", e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }
}
