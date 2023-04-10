package application.port.in;

import application.domain.OrderState;

public interface OrderStateUseCase {
    OrderState getOrderState(String id);
    void deleteOrderState(String id);
    OrderState createOrderState(OrderState orderState);
}
