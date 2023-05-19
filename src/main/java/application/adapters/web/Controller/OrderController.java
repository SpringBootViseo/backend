package application.adapters.web.Controller;

import application.adapters.mapper.mapperImpl.OrderMapperImpl;
import application.adapters.web.presenter.OrderCreateRequestDTO;
import application.adapters.web.presenter.OrderDTO;
import application.adapters.web.presenter.OrderUpdateStateRequestDTO;
import application.domain.Order;
import application.port.in.OrderUseCase;
import jakarta.validation.UnexpectedTypeException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")

@RequestMapping("/orders")
public class OrderController {
    private OrderUseCase orderUseCase;
    private OrderMapperImpl orderMapper;
    @PostMapping
    ResponseEntity<OrderDTO> createOrder(@Validated @RequestBody OrderCreateRequestDTO orderDTO){

        try{
            Order savedOrder=orderUseCase.saveOrder(orderMapper.orderCreateRequestDTOToOrder(orderDTO));
            return new ResponseEntity<>(orderMapper.orderToOrderDTO(savedOrder), HttpStatus.OK);
        }catch (UnexpectedTypeException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad argument",e);
        }

        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }

    }
    @GetMapping("/user/{id}")
    ResponseEntity<List<OrderDTO>> listOrders(@PathVariable(name = "id") String id){
        try {
            List<Order> orderList=orderUseCase.listOrder(id);
            return new ResponseEntity<>(orderMapper.listOrderTolistOrderDTO(orderList),HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);

        }
    }

    @GetMapping("")
    ResponseEntity<List<OrderDTO>> listOrder(){
        try {
            List<Order> listOrder=orderUseCase.listOrder();
            return  new ResponseEntity<>(orderMapper.listOrderTolistOrderDTO(listOrder),HttpStatus.OK);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);

        }
    }
    @GetMapping("/{id}")
    ResponseEntity<OrderDTO> getOrder(@Validated @PathVariable(name = "id")UUID id){
        try{
            Order order= orderUseCase.getOrder(id);
            return new ResponseEntity<>(orderMapper.orderToOrderDTO(order),HttpStatus.OK);
        }
        catch (UnexpectedTypeException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad argument",e);
        }

        catch (NoSuchElementException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Order not found",e);
        }


        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }
    @PutMapping("/{id}")
    ResponseEntity<OrderDTO> updateOrderStateOrder(@Validated @PathVariable(name = "id") UUID id, @Validated @RequestBody OrderUpdateStateRequestDTO idState){
        try{
            Order order=orderUseCase.updateStateOrder(id,idState.getIdState());
            return new ResponseEntity<>(orderMapper.orderToOrderDTO(order),HttpStatus.OK);
        }
        catch (UnexpectedTypeException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad argument",e);
        }

        catch (NoSuchElementException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Order or State not found",e);
        }

        catch (Exception e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }
    @PatchMapping("/pay/{id}")
    ResponseEntity<OrderDTO> payOrder(@Validated @PathVariable(name = "id") UUID id){
        try{
            Order order=orderUseCase.payerOrder(id);
            return new ResponseEntity<>(orderMapper.orderToOrderDTO(order),HttpStatus.OK);
        }
        catch (IllegalAccessException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        catch (UnexpectedTypeException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad argument",e);
        }

        catch (NoSuchElementException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Order or State not found",e);
        }

        catch (Exception e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }
    @PatchMapping("/prepare/{id}")
    ResponseEntity<OrderDTO> prepareOrder(@Validated @PathVariable(name = "id") UUID id){
        try{
            Order order=orderUseCase.preparerOrder(id);
            return new ResponseEntity<>(orderMapper.orderToOrderDTO(order),HttpStatus.OK);
        }
        catch (IllegalAccessException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        catch (UnexpectedTypeException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad argument",e);
        }

        catch (NoSuchElementException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Order or State not found",e);
        }

        catch (Exception e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }
    @PatchMapping("/readyToGo/{id}")
    ResponseEntity<OrderDTO> readyToGoOrder(@Validated @PathVariable(name = "id") UUID id){
        try{
            Order order=orderUseCase.readyForDeliveryOrder(id);
            return new ResponseEntity<>(orderMapper.orderToOrderDTO(order),HttpStatus.OK);
        }
        catch (IllegalAccessException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        catch (UnexpectedTypeException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad argument",e);
        }

        catch (NoSuchElementException e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Order or State not found",e);
        }

        catch (Exception e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }
    @PatchMapping("/{idOrder}/Preparateur/{emailPreparateur}")
    ResponseEntity<OrderDTO> attribuerOrderPreparateur(@PathVariable(name = "idOrder") UUID idOrder, @PathVariable(name = "emailPreparateur")String idPreparateur){
        try{
            Order response=orderUseCase.attribuerOrder(idOrder,idPreparateur);
            return new ResponseEntity<>(orderMapper.orderToOrderDTO(response),HttpStatus.OK);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }
    @GetMapping("/Preparateur/{email}")
    ResponseEntity<List<OrderDTO>> listOrderPreparedByEmailPrepa(@PathVariable(name = "email") String email){
        try {
            List<Order> orders=orderUseCase.listOrderPreparateur(email);
            return new ResponseEntity<>(orderMapper.listOrderTolistOrderDTO(orders),HttpStatus.OK);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }
}
