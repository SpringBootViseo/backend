package application.adapters.mapper;

import application.adapters.persistence.entity.OrderEntity;
import application.adapters.web.presenter.OrderCreateRequestDTO;
import application.adapters.web.presenter.OrderDTO;
import application.domain.Order;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.List;

public interface OrderMapper {
    Order orderEntityToOrder(OrderEntity orderEntity);
    Order orderDTOToOrder(OrderDTO orderDTO);
    OrderDTO orderToOrderDTO(Order order);
    OrderEntity orderToOrderEntity(Order order);
    List<Order> documentOrderTolistOrder(MongoCollection<Document> collection);
    List<Order> listOrderEntityTolistOrder(List<OrderEntity> orderEntityList);
    List<Order> listOrderDTOTolistOrder(List<OrderDTO> orderDTOList);
    List<OrderDTO> listOrderTolistOrderDTO(List<Order> orderList);
    List<OrderEntity> listOrderTolistOrderEntity(List<Order> orderList);
    Order orderCreateRequestDTOToOrder(OrderCreateRequestDTO orderCreateRequestDTO );
    List<Order> listOrderCreateRequestDTOTolistOrder(List<OrderCreateRequestDTO> orderCreateRequestDTOList);
}
