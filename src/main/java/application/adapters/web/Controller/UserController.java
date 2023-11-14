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
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger logger= LoggerFactory.getLogger(UserController.class);
    private final UserMapper userMapper;
    @PostMapping("")
    public ResponseEntity<User> createUser(@Validated @RequestBody UserDTO userDTO) {
        try {
            logger.trace("Call createUser from USerUseCase ");
            logger.trace("Map userDTO"+ userDTO.toString()+" to  user");

            User savedUser = userUseCase.saveUser(userMapper.userDtoToUser(userDTO));
            logger.debug("Map saveUSer from UserUSerCase to Post Mapper /user with body :"+userDTO.toString());
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        }catch (UnexpectedTypeException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad argument",e);
        } catch (UserAlreadyExistsException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User  exists", e);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }
    @PostMapping("/{id}")
    public ResponseEntity<User> addAdressToUser(@Validated @PathVariable(name = "id")String id, @Validated @RequestBody UserAddressDTO userAddressDTO){
        try{
            logger.trace("Call addAddress from USerUseCase ");
            logger.trace("Map userAddressDTO"+ userAddressDTO.toString()+" to  user");

            User response= userUseCase.addAddress(id,userMapper.userAddressDtoToAddress(userAddressDTO));
            logger.debug("Map addAddress from UserUSerCase to Post Mapper /user/"+id+" with body :"+userAddressDTO.toString());

            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (UnexpectedTypeException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad argument",e);
        } catch (UserNotFoundException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User  doesn't exist", e);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }

    @DeleteMapping("/{id}/idAddress/{idAddress}")
    public ResponseEntity<UserDTO> deleteAddressFromUser(@Validated @PathVariable(name = "idAddress") UUID idAddress, @PathVariable (name = "id") String id){
        try{
            logger.trace("Call deleteAddress from USerUseCase with idAddress: "+idAddress+" idUser: "+id);

            User userResponse=userUseCase.deleteAddress(idAddress,id);
            logger.trace("Map user "+ userResponse.toString()+" to  userDTO");
            logger.debug("Map deleteAddressFromUser from UserUSerCase to Delete Mapper /user/"+id+"/idAddress"+idAddress);

            return new ResponseEntity<>(userMapper.userToUserDTO(userResponse),HttpStatus.OK);
        }
        catch (UnexpectedTypeException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad argument",e);
        }
        catch(AddressNotFound e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Address not found",e);
        }

        catch (Exception e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }

    }
    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUser(@Validated @RequestBody UserPhoneDTO userPhoneDTO, @Validated @PathVariable(name = "id") String id){
        try{
            logger.trace("Call update from USerUseCase ");

            User response=userUseCase.updateUser(id,userMapper.userUpdateDtoToUser(userPhoneDTO));
            logger.debug("Map updateUSer from UserUSerCase to Patch Mapper /user/"+id+" with body :"+userPhoneDTO.toString());

            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (UnexpectedTypeException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad argument",e);
        } catch (UserNotFoundException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User  doesn't exist", e);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@Validated @PathVariable(name = "id")String id){
        try {
            logger.trace("Call getUSer from USerUseCase with id :"+id);

            User response=userUseCase.getUser(id);
            logger.debug("Map getUser from UserUSerCase to Get Mapper /user/"+id);

            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (UnexpectedTypeException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad argument",e);
        } catch (UserNotFoundException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User doesn't exist", e);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }
    @PutMapping("/avertir/{id}")
    public  ResponseEntity<UserDTO> avertirUser(@Validated @PathVariable(name = "id")String id){
        try{
            logger.trace("Call avertirUser from USerUseCase with id: "+id);

            UserDTO response=userMapper.userToUserDTO(userUseCase.avertirUser(id));
            logger.debug("Map avertirUser from UserUSerCase to Put Mapper /user/avertir/"+id);

            return new ResponseEntity<>(response,HttpStatus.OK);

        }catch (UnexpectedTypeException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad argument",e);
        } catch (UserNotFoundException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User doesn't exist", e);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }
    @PutMapping("/blackLister/{id}")
    ResponseEntity<UserDTO> blackListerUser(@Validated @PathVariable(name = "id")String id){
        try {
            logger.trace("Call blackListerUser from USerUseCase with id: "+id);

            UserDTO userDTO=userMapper.userToUserDTO(userUseCase.blacklisterUser(id));
            logger.debug("Map blackListerUser from UserUSerCase to Put Mapper /user/blackLister/"+id);

            return new ResponseEntity<>(userDTO,HttpStatus.OK);

        }catch (UnexpectedTypeException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad argument",e);
        } catch (UserNotFoundException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User doesn't exist", e);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<UserDTO> loginWithGoogle(@Validated @RequestBody UserDTO userDTO){
        try{
            logger.trace("Call loginWithGoogle from USerUseCase ");
            UserDTO response=userMapper.userToUserDTO(userUseCase.loginWithGoogle(userMapper.userDtoToUser(userDTO)));
            logger.debug("Map loginWithGoogle from UserUSerCase to Post Mapper /users/login/ with body"+userDTO.toString());

            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch (IllegalAccessException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
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
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }
}