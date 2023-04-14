package application.adapters.web.Controller;

import application.adapters.exception.OrderStateAlreadyExistsException;
import application.adapters.mapper.mapperImpl.OrderStateMapperImpl;
import application.adapters.web.presenter.OrderStateDTO;
import application.domain.OrderState;
import application.port.in.OrderStateUseCase;
import jakarta.validation.UnexpectedTypeException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/orderStates")
public class OrderStateController {
    private OrderStateUseCase orderStateUseCase;
    private OrderStateMapperImpl orderStateMapper;
    @GetMapping("/{id}")
    ResponseEntity<OrderStateDTO> getOrderState(@Validated @PathVariable(name = "id") String id){
        try{
            OrderState orderState=orderStateUseCase.getOrderState(id);
            return new ResponseEntity<>(orderStateMapper.orderStateToOrderStateDTO(orderState), HttpStatus.OK);
        }
        catch (UnexpectedTypeException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad argument",e);
        }

        catch (NoSuchElementException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"OrderState not found",e);
        }


        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }
    @DeleteMapping("/{id}")
    ResponseEntity<OrderStateDTO> deleteOrderState(@Validated @PathVariable(name = "id")String id){
        try{
            orderStateUseCase.deleteOrderState(id);
            return  new ResponseEntity<>(HttpStatus.OK);
        }
        catch (UnexpectedTypeException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad argument",e);
        }

        catch (NoSuchElementException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"OrderState not found",e);
        }


        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }
    @PostMapping
    ResponseEntity<OrderStateDTO> addOrderState(@Validated @RequestBody OrderState orderState){
        try{
            OrderState savedOrderState=orderStateUseCase.createOrderState(orderState);
            return new ResponseEntity<>(orderStateMapper.orderStateToOrderStateDTO(savedOrderState),HttpStatus.OK);
        }
        catch (UnexpectedTypeException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad argument",e);
        }

        catch (OrderStateAlreadyExistsException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT,"OrderState already exist",e);
        }


        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }

    }
}
