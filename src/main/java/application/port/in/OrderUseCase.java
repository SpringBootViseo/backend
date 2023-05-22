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
    Order payerOrder(UUID id,String emailLivreur) throws IllegalAccessException;
    Order preparerOrder(UUID id) throws IllegalAccessException;
    Order readyForDeliveryOrder(UUID id) throws IllegalAccessException;
    Order attribuerOrder(UUID idOrder,String emailPreparateur) throws IllegalAccessException;
    List<Order> listOrderPreparateur(String email);
}
