package application.adapters.web.Controller;

import application.adapters.mapper.mapperImpl.OrderMapperImpl;
import application.adapters.web.presenter.OrderCreateRequestDTO;
import application.adapters.web.presenter.OrderDTO;
import application.adapters.web.presenter.OrderUpdateStateRequestDTO;
import application.domain.Order;
import application.port.in.OrderUseCase;
import jakarta.validation.UnexpectedTypeException;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private final static Logger logger= LogManager.getLogger(OrderController.class);
    @PostMapping
    ResponseEntity<OrderDTO> createOrder(@Validated @RequestBody OrderCreateRequestDTO orderDTO){

        try{
            logger.trace("Map orderCreateRequestDTO "+orderDTO.toString()+"to Order");
            logger.trace("Call saveOrder from Order use case ");
            Order savedOrder=orderUseCase.saveOrder(orderMapper.orderCreateRequestDTOToOrder(orderDTO));
            logger.debug("Map save Order from Order use case with Post Mapper /orders");
            return new ResponseEntity<>(orderMapper.orderToOrderDTO(savedOrder), HttpStatus.OK);
        }catch (UnexpectedTypeException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad argument",e);
        }

        catch (Exception e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }

    }
    @GetMapping("/user/{id}")
    ResponseEntity<List<OrderDTO>> listOrders(@PathVariable(name = "id") String id){
        try {
            logger.trace("Call list Order with user id : "+id);
            List<Order> orderList=orderUseCase.listOrder(id);
            logger.trace("Map Order list "+orderList.toString()+"to Order List DTO");
            logger.debug("Map listOrder of user with id "+id+" to Get Mapper /orders/user/"+id);
            return new ResponseEntity<>(orderMapper.listOrderTolistOrderDTO(orderList),HttpStatus.OK);
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);

        }
    }

    @GetMapping("")
    ResponseEntity<List<OrderDTO>> listOrder(){
        try {
            logger.trace("Call list Order");
            List<Order> listOrder=orderUseCase.listOrder();
            logger.trace("Map Order list "+listOrder.toString()+"to Order List DTO");
            logger.debug("Map listOrder  to Get Mapper /orders");

            return  new ResponseEntity<>(orderMapper.listOrderTolistOrderDTO(listOrder),HttpStatus.OK);
        }
        catch (Exception e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);

        }
    }
    @GetMapping("/{id}")
    ResponseEntity<OrderDTO> getOrder(@Validated @PathVariable(name = "id")UUID id){
        try{
            logger.trace("Call get Order with id : "+id+" from use case ");
            Order order= orderUseCase.getOrder(id);
            logger.trace("Map order to order  "+order.toString()+"to order DTO");
            logger.debug("Map getOrder from Order use case to Get Mapper /order/"+id);
            return new ResponseEntity<>(orderMapper.orderToOrderDTO(order),HttpStatus.OK);
        }
        catch (UnexpectedTypeException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad argument",e);
        }

        catch (NoSuchElementException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Order not found",e);
        }


        catch (Exception e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }
    @PutMapping("/{id}")
    ResponseEntity<OrderDTO> updateOrderStateOrder(@Validated @PathVariable(name = "id") UUID id, @Validated @RequestBody OrderUpdateStateRequestDTO idState){
        try{
            logger.trace("Retrieve order state id "+idState.getIdState());
            Order order=orderUseCase.updateStateOrder(id,idState.getIdState());
            logger.trace("Call update state from use case with order id "+id+" and with order state "+idState.getIdState());
            logger.debug("Map update state from use case with order id "+id+" and with order state "+idState.getIdState()+" to the Put Mapper orders/id");

            return new ResponseEntity<>(orderMapper.orderToOrderDTO(order),HttpStatus.OK);
        }
        catch (UnexpectedTypeException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad argument",e);
        }

        catch (NoSuchElementException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Order or State not found",e);
        }

        catch (Exception e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }
    @PatchMapping("/pay/{id}/email/{email}")
    ResponseEntity<OrderDTO> payOrder(@Validated @PathVariable(name = "id") UUID id,@Validated @PathVariable(name = "email")String email){
        try{
            logger.trace("Call pay order from use case with order id "+id+" and with mail "+email);
            Order order=orderUseCase.payerOrder(id,email);
            logger.trace("Map order "+order.toString()+" to order DTO");
            logger.debug("Map pay order from use case to patch mapper with id "+id+" and with mail "+email);
            return new ResponseEntity<>(orderMapper.orderToOrderDTO(order),HttpStatus.OK);
        }
        catch (IllegalAccessException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        catch (UnexpectedTypeException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad argument",e);
        }

        catch (NoSuchElementException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Order or State not found",e);
        }

        catch (Exception e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }
    @PatchMapping("/prepare/{id}")
    ResponseEntity<OrderDTO> prepareOrder(@Validated @PathVariable(name = "id") UUID id){
        try{
            logger.trace("Call prepare order from use case with order id "+id);

            Order order=orderUseCase.preparerOrder(id);
            logger.trace("Map order "+order+" to order DTO");
            logger.debug("Map prepare order from use case to patch mapper with id /orders/prepare/"+id);

            return new ResponseEntity<>(orderMapper.orderToOrderDTO(order),HttpStatus.OK);
        }
        catch (IllegalAccessException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        catch (UnexpectedTypeException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad argument",e);
        }

        catch (NoSuchElementException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Order or State not found",e);
        }

        catch (Exception e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }
    @PatchMapping("/readyToGo/{id}")
    ResponseEntity<OrderDTO> readyToGoOrder(@Validated @PathVariable(name = "id") UUID id){
        try{
            logger.trace("Call readyToGo order from use case with order id "+id);


            Order order=orderUseCase.readyForDeliveryOrder(id);
            logger.trace("Map order "+order+" to order DTO");            logger.debug("Map prepare order from use case to patch mapper with id /orders/prepare/"+id);
            logger.debug("Map reatoGoOrder from use case to patch mapper with id /orders/readyToGo/"+id);

            return new ResponseEntity<>(orderMapper.orderToOrderDTO(order),HttpStatus.OK);
        }
        catch (IllegalAccessException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        catch (UnexpectedTypeException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad argument",e);
        }

        catch (NoSuchElementException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Order or State not found",e);
        }

        catch (Exception e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }
    @PatchMapping("/{idOrder}/Preparateur/{emailPreparateur}")
    ResponseEntity<OrderDTO> attribuerOrderPreparateur(@PathVariable(name = "idOrder") UUID idOrder, @PathVariable(name = "emailPreparateur")String idPreparateur){
        try{
            logger.trace("call attribuerOrerPreparateur from use case  with Order id : "+idOrder+" and preparator id :"+idPreparateur);
            Order response=orderUseCase.attribuerOrder(idOrder,idPreparateur);
            logger.trace("Map order "+response+" to order DTO");
            logger.debug("Map attribuerOrder  from use case to patch mapper with id /orders/"+idOrder+"preparateur/"+idPreparateur);

            return new ResponseEntity<>(orderMapper.orderToOrderDTO(response),HttpStatus.OK);
        }
        catch (Exception e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }
    @GetMapping("/Preparateur/{email}")
    ResponseEntity<List<OrderDTO>> listOrderPreparedByEmailPrepa(@PathVariable(name = "email") String email){
        try {
            logger.trace("Call listOrerPreparateur from use case  with email  : "+email);

            List<Order> orders=orderUseCase.listOrderPreparateur(email);
            logger.trace("Map orders "+orders.toString()+"to orders DTO");
            logger.debug("Map listOrderPreparateur to get Mapper /orders/Preparateur/"+email);
            return new ResponseEntity<>(orderMapper.listOrderTolistOrderDTO(orders),HttpStatus.OK);
        }
        catch (Exception e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }
}
