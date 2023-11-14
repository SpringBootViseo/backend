package application.adapters.web.Controller;

import application.adapters.exception.CartAlreadyExistsException;
import application.adapters.exception.CartNotFoundException;
import application.adapters.mapper.mapperImpl.CartMapperImpl;
import application.adapters.web.presenter.CartCreateRequestDTO;
import application.adapters.web.presenter.CartResponseDTO;
import application.adapters.web.presenter.CartUpdateRequestDTO;
import application.domain.Cart;
import application.port.in.CartUseCase;
import jakarta.validation.UnexpectedTypeException;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/carts")
public class CartController {
    private CartUseCase cartUseCase;
    private CartMapperImpl cartMapper;
    private final Logger logger= LoggerFactory.getLogger(CartController.class);
    @PostMapping
    public ResponseEntity<CartResponseDTO> createCart(@Validated @RequestBody CartCreateRequestDTO cartCreateRequestDTO){

        try{
            logger.trace("Call create Cart from cart use case");
            Cart cartResponse=cartUseCase.createCart(cartCreateRequestDTO.getId());
            logger.trace("Map cart "+cartResponse.toString()+" to cart DTO");
            logger.debug("Map the createCart function  from the use case to the post mapper /carts");
            return new ResponseEntity<>(cartMapper.cartToCartResponseDTO(cartResponse), HttpStatus.OK);
        }catch (UnexpectedTypeException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST,"Bad argument",e);
        }
        catch(CartAlreadyExistsException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Cart already exists",e);
        }

        catch (Exception e) {

            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }


    }
    @PutMapping
    public ResponseEntity<CartResponseDTO> addProduct(@Validated @RequestBody CartUpdateRequestDTO cartUpdateRequestDTO){
        try{
            logger.trace("Retrieve idCart "+cartUpdateRequestDTO.getIdCart()+" and  idProduct "+cartUpdateRequestDTO.getIdProduct());
            logger.trace("Call addProduct from cart use case");
            Cart cartResponse=cartUseCase.addProduct(cartUpdateRequestDTO.getIdCart(),cartUpdateRequestDTO.getIdProduct());
            logger.trace("Map cart "+cartResponse.toString()+" to cartDTO ");
            logger.debug("Map the addProduct function from use case to put mapper /carts ");
            return new ResponseEntity<>(cartMapper.cartToCartResponseDTO(cartResponse),HttpStatus.OK);
        }
        catch (UnexpectedTypeException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad argument",e);
        }
        catch(CartNotFoundException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Cart not found",e);
        }

        catch (NoSuchElementException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found",e);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }
    @DeleteMapping
    public ResponseEntity<CartResponseDTO> deleteProduct(@Validated @RequestBody CartUpdateRequestDTO cartUpdateRequestDTO){
        try{
            logger.trace("Retrieve idCart "+cartUpdateRequestDTO.getIdCart()+" and  idProduct "+cartUpdateRequestDTO.getIdProduct());
            logger.trace("Call deleteProduct from cart use case");
            Cart cartResponse=cartUseCase.deleteProduct(cartUpdateRequestDTO.getIdCart(),cartUpdateRequestDTO.getIdProduct());
            logger.trace("Map cart "+cartResponse.toString()+" to cartDTO ");
            logger.debug("Map the deleteProduct function from use case to delete mapper /carts ");
            return new ResponseEntity<>(cartMapper.cartToCartResponseDTO(cartResponse),HttpStatus.OK);
        }
        catch (UnexpectedTypeException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad argument",e);
        }
        catch(CartNotFoundException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Cart not found",e);
        }

        catch (NoSuchElementException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found",e);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }
    @DeleteMapping("/deleteProductsCart/{id}")
    public void deleteUserCart(@Validated @PathVariable(name = "id") String id){
        try{
            logger.trace("Call deleteUserCart from cart use case");
            cartUseCase.deleteCart(id);
            logger.debug("Map the deleteUserCart function from use case to delete mapper /deleteProductsCart/"+id);
        }
        catch (UnexpectedTypeException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad argument",e);
        }
        catch(CartNotFoundException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Cart not found",e);
        }

        catch (NoSuchElementException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found",e);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<CartResponseDTO> getCart(@Validated @PathVariable(name = "id") String id){
        try{
            logger.trace("Call deleteUserCart from cart use case");
            Cart cartResponse=cartUseCase.getCart(id);
            logger.trace("Map cart "+cartResponse.toString()+" to cartDTO ");
            logger.debug("Map the getCart function from use case to get mapper /crts/"+id);

            return new ResponseEntity<>(cartMapper.cartToCartResponseDTO(cartResponse),HttpStatus.OK);
        }
        catch (UnexpectedTypeException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad argument",e);
        }
        catch(CartNotFoundException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Cart not found",e);
        }

        catch (NoSuchElementException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found",e);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }
}