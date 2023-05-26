package application.adapters.web.Controller;

import application.adapters.exception.AddressNotFound;
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

import java.util.List;
import java.util.UUID;


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
            User response= userUseCase.addAddress(id,userMapper.userAddressDtoToAddress(userAddressDTO));
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

    @DeleteMapping("/{id}/idAddress/{idAddress}")
    public ResponseEntity<UserDTO> deleteAddressFromUser(@Validated @PathVariable(name = "idAddress") UUID idAddress, @PathVariable (name = "id") String id){
        try{
            System.out.println("delete");
            User userResponse=userUseCase.deleteAddress(idAddress,id);
            return new ResponseEntity<>(userMapper.userToUserDTO(userResponse),HttpStatus.OK);
        }
        catch (UnexpectedTypeException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad argument",e);
        }
        catch(AddressNotFound e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Address not found",e);
        }

        catch (Exception e) {
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
    @PutMapping("/avertir/{id}")
    public  ResponseEntity<UserDTO> avertirUser(@Validated @PathVariable(name = "id")String id){
        try{
            UserDTO response=userMapper.userToUserDTO(userUseCase.avertirUser(id));
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
    @PutMapping("/blackLister/{id}")
    ResponseEntity<UserDTO> blackListerUser(@Validated @PathVariable(name = "id")String id){
        try {
            UserDTO userDTO=userMapper.userToUserDTO(userUseCase.blacklisterUser(id));
            return new ResponseEntity<>(userDTO,HttpStatus.OK);

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
    @PostMapping("/login")
    public ResponseEntity<UserDTO> loginWithGoogle(@Validated @RequestBody UserDTO userDTO){
        try{

            UserDTO response=userMapper.userToUserDTO(userUseCase.loginWithGoogle(userMapper.userDtoToUser(userDTO)));
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch (IllegalAccessException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }

    }
    @GetMapping()
    public ResponseEntity<List<UserDTO>> listUsers(){
        try{
            List<UserDTO> response=userMapper.listUserToListUserDTO(userUseCase.listUser());
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }
}
