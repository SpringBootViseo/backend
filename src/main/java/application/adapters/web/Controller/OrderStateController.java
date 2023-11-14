package application.adapters.web.Controller;

import application.adapters.exception.OrderStateAlreadyExistsException;
import application.adapters.mapper.mapperImpl.OrderStateMapperImpl;
import application.adapters.web.presenter.OrderStateDTO;
import application.domain.OrderState;
import application.port.in.OrderStateUseCase;
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

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/orderStates")
public class OrderStateController {
    private final Logger logger= LoggerFactory.getLogger(OrderStateController.class);
    private OrderStateUseCase orderStateUseCase;
    private OrderStateMapperImpl orderStateMapper;
    @GetMapping("/{id}")
    ResponseEntity<OrderStateDTO> getOrderState(@Validated @PathVariable(name = "id") String id){
        try{
            logger.trace("Call get order state  from use case with id "+id);
            OrderState orderState=orderStateUseCase.getOrderState(id);
            logger.trace("Map Order State "+ orderState.toString()+" to order state DTO");
            logger.debug("Map the get Order State from use case with Get Mapper /orderSates/"+id);
            return new ResponseEntity<>(orderStateMapper.orderStateToOrderStateDTO(orderState), HttpStatus.OK);
        }
        catch (UnexpectedTypeException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad argument",e);
        }

        catch (NoSuchElementException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"OrderState not found",e);
        }


        catch (Exception e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }
    @DeleteMapping("/{id}")
    ResponseEntity<OrderStateDTO> deleteOrderState(@Validated @PathVariable(name = "id")String id){
        try{
            logger.trace("Call delete order state  from use case with id "+id);

            orderStateUseCase.deleteOrderState(id);
            logger.debug("Map the delete Order State from use case with delete Mapper /orderSates/"+id);

            return  new ResponseEntity<>(HttpStatus.OK);
        }
        catch (UnexpectedTypeException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad argument",e);
        }

        catch (NoSuchElementException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"OrderState not found",e);
        }


        catch (Exception e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }
    @PostMapping
    ResponseEntity<OrderStateDTO> addOrderState(@Validated @RequestBody OrderState orderState){
        try{
            logger.trace("Call create order state  from use case with orderState "+orderState.toString());

            OrderState savedOrderState=orderStateUseCase.createOrderState(orderState);
            logger.trace("Map Order State "+ savedOrderState.toString()+" to order state DTO");
            logger.debug("Map the get Order State from use case with Post Mapper /orderSates with body "+orderState.toString());

            return new ResponseEntity<>(orderStateMapper.orderStateToOrderStateDTO(savedOrderState),HttpStatus.OK);
        }
        catch (UnexpectedTypeException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad argument",e);
        }

        catch (OrderStateAlreadyExistsException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT,"OrderState already exist",e);
        }


        catch (Exception e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }

    }
}