package application.port.out;

import application.domain.Order;
import application.domain.OrderState;
import application.domain.User;

import java.util.List;
import java.util.UUID;

public interface OrderPort {
    Order saveOrder(Order order);
    Order getOrder(UUID id);
    List<Order> listOrder(User user);
    Order updateStateOrder(UUID id, OrderState orderState);
    List<Order> listOrder(User user,OrderState orderState);
    Boolean isAvailable(UUID id);
}
