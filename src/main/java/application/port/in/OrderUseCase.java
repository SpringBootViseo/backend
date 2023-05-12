package application.port.in;

import application.domain.Order;

import java.util.List;
import java.util.UUID;

public interface OrderUseCase {
    Order saveOrder(Order order);
    Order getOrder(UUID id);
    List<Order> listOrder(String idUser);
    Order updateStateOrder(UUID id, String idorderState);
    List<Order> listOrder(String idUser,String idorderState);
    Boolean isAvailable(UUID id);
    List <Order> listOrder();
}
