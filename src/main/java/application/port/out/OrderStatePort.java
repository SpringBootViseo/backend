package application.port.out;

import application.domain.OrderState;


public interface OrderStatePort {
    OrderState getOrderState(String id);
    void deleteOrderState(String id);
    OrderState createOrderState(OrderState orderState) ;
}
