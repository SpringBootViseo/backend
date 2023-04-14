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
    @PostMapping
    public ResponseEntity<CartResponseDTO> createCart(@Validated @RequestBody CartCreateRequestDTO cartCreateRequestDTO){
        try{
            Cart cartResponse=cartUseCase.createCart(cartCreateRequestDTO.getId());
            return new ResponseEntity<>(cartMapper.cartToCartResponseDTO(cartResponse), HttpStatus.OK);
        }catch (UnexpectedTypeException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST,"Bad argument",e);
        }
        catch(CartAlreadyExistsException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Cart already exists",e);
        }

        catch (Exception e) {

            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }


    }
    @PutMapping
    public ResponseEntity<CartResponseDTO> addProduct(@Validated @RequestBody CartUpdateRequestDTO cartUpdateRequestDTO){
        try{
            Cart cartResponse=cartUseCase.addProduct(cartUpdateRequestDTO.getIdCart(),cartUpdateRequestDTO.getIdProduct());
            return new ResponseEntity<>(cartMapper.cartToCartResponseDTO(cartResponse),HttpStatus.OK);
        }
        catch (UnexpectedTypeException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad argument",e);
        }
        catch(CartNotFoundException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Cart not found",e);
        }

        catch (NoSuchElementException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found",e);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }
    @DeleteMapping
    public ResponseEntity<CartResponseDTO> deleteProduct(@Validated @RequestBody CartUpdateRequestDTO cartUpdateRequestDTO){
        try{
            Cart cartResponse=cartUseCase.deleteProduct(cartUpdateRequestDTO.getIdCart(),cartUpdateRequestDTO.getIdProduct());
            return new ResponseEntity<>(cartMapper.cartToCartResponseDTO(cartResponse),HttpStatus.OK);
        }
        catch (UnexpectedTypeException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad argument",e);
        }
        catch(CartNotFoundException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Cart not found",e);
        }

        catch (NoSuchElementException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found",e);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<CartResponseDTO> getCart(@Validated @PathVariable(name = "id") String id){
        try{
            Cart cartResponse=cartUseCase.getCart(id);
            return new ResponseEntity<>(cartMapper.cartToCartResponseDTO(cartResponse),HttpStatus.OK);
        }
        catch (UnexpectedTypeException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad argument",e);
        }
        catch(CartNotFoundException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Cart not found",e);
        }

        catch (NoSuchElementException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found",e);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }
}
