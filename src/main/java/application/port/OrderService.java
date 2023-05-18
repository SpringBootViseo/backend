package application.port;

import application.adapters.exception.OrderAlreadyExistException;
import application.adapters.exception.ProductNotAvailableException;
import application.domain.*;
import application.port.in.OrderUseCase;
import application.port.out.OrderPort;
import application.port.out.OrderStatePort;
import application.port.out.ProductPort;
import application.port.out.UserPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@AllArgsConstructor
@Service
public class OrderService implements OrderUseCase {
    private final OrderPort orderPort;
    private final UserPort userPort;
    private final ProductPort productPort;
    private final OrderStatePort orderStatePort;
    @Override
    public Order saveOrder(Order order) {
        order.setTotalPrice(0L);
        if(this.isAvailable(order.getId())){
            throw new OrderAlreadyExistException();
        }
        if(order.getOrderItems().isEmpty()){
            throw new ProductNotAvailableException();
        }
        else{
            List<OrderItem> orderItemList=order.getOrderItems();
            for (OrderItem orderItem: orderItemList){
                try {
                    Product orderedProduct=productPort.orderProduct(orderItem.getProduct().getId(), orderItem.getQuantity());
                    order.setTotalPrice((long) (order.getTotalPrice()+orderItem.getProduct().getCurrentPrice()*orderItem.getQuantity()));
                    orderItem.setProduct(orderedProduct);
                }
                catch (ProductNotAvailableException e){
                    throw new NoSuchElementException("Can't Purchase this Order");
                }
            }
            OrderState orderState=orderStatePort.getOrderState("commandé");
            order.setOrderState(orderState);
            return orderPort.saveOrder(order);
        }

    }

    @Override
    public Order getOrder(UUID id) {
        if(this.isAvailable(id))
            return orderPort.getOrder(id) ;
        else throw new NoSuchElementException("Can't found Order with "+id);
    }

    @Override
    public List<Order> listOrder(String idUser) {
        User user=userPort.getUser(idUser);
        return orderPort.listOrder(user);
    }

    @Override
    public Order updateStateOrder(UUID id, String idorderState) {
        OrderState orderState=orderStatePort.getOrderState(idorderState);
        return orderPort.updateStateOrder(id,orderState);
    }

    @Override
    public List<Order> listOrder(String idUser, String idorderState) {
        User user=userPort.getUser(idUser);
        OrderState orderState=orderStatePort.getOrderState(idorderState);
        return orderPort.listOrder(user,orderState);
    }


    @Override
    public Boolean isAvailable(UUID id) {
        return orderPort.isAvailable(id);
    }

    @Override
    public List<Order> listOrder() {
        return orderPort.listOrder();
    }

    @Override
    public Order payerOrder(UUID id) throws IllegalAccessException {
        if(orderPort.getOrder(id).getOrderState().getState().equals("prête à livré")){
            OrderState payedState=orderStatePort.getOrderState("payé");
            return orderPort.updateStateOrder(id,payedState);
        }
       else throw new IllegalAccessException("Impossible de payé cette commande ");
    }


    @Override
    public Order preparerOrder(UUID id) throws IllegalAccessException {
        if(orderPort.getOrder(id).getOrderState().getState().equals("commandé")) {
            OrderState onPrepareState = orderStatePort.getOrderState("en préparation");
            return orderPort.updateStateOrder(id, onPrepareState);
        }
        else throw new IllegalAccessException("Impossible de préparer cette commande ");

    }

    @Override
    public Order readyForDeliveryOrder(UUID id) throws IllegalAccessException {
        if(orderPort.getOrder(id).getOrderState().getState().equals("en préparation")) {

        OrderState readyForDeliveryState = orderStatePort.getOrderState("prête à livré");

        return orderPort.updateStateOrder(id, readyForDeliveryState);
    }else throw new IllegalAccessException("Impossible de payé cette commande ");

    }

}