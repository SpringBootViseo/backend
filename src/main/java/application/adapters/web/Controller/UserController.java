package application.adapters.web.Controller;

import application.adapters.exception.UserAlreadyExistsException;
import application.adapters.exception.UserNotFoundException;
import application.adapters.mapper.UserMapper;
import application.adapters.web.presenter.UserAddressDTO;
import application.adapters.web.presenter.UserDTO;
import application.adapters.web.presenter.UserPhoneDTO;
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
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private final UserUseCase userUseCase;

    private final UserMapper userMapper;
    @PostMapping("")
    public ResponseEntity<User> createUser(@Validated @RequestBody UserDTO userDTO) {
        try {
            User savedUser = userUseCase.saveUser(userMapper.userDtoToUser(userDTO));
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        }catch (UnexpectedTypeException e){
            System.out.println(e.getMessage());
             throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad argument",e);
        } catch (UserAlreadyExistsException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User  exists", e);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }
    @PostMapping("/{id}")
    public ResponseEntity<User> addAdressToUser(@Validated @PathVariable(name = "id")String id, @Validated @RequestBody UserAddressDTO userAddressDTO){
        try{
            User response= userUseCase.addAddress(id,userAddressDTO.getAddress());
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (UnexpectedTypeException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad argument",e);
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User  doesn't exist", e);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }
    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUser(@Validated @RequestBody UserPhoneDTO userPhoneDTO, @Validated @PathVariable(name = "id") String id){
        try{
            User response=userUseCase.updateUser(id,userMapper.userUpdateDtoToUser(userPhoneDTO));
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (UnexpectedTypeException e){
            System.out.println(e.getMessage());
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad argument",e);
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User  doesn't exist", e);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@Validated @PathVariable(name = "id")String id){
        try {
            User response=userUseCase.getUser(id);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (UnexpectedTypeException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad argument",e);
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User doesn't exist", e);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }
}
