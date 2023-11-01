package application.adapters.persistence.adapter;

import application.adapters.mapper.mapperImpl.OrderMapperImpl;
import application.adapters.persistence.MongoConfig;
import application.adapters.persistence.entity.OrderEntity;
import application.adapters.persistence.repository.OrderRepository;
import application.domain.Order;
import application.domain.OrderState;
import application.domain.User;
import application.port.out.OrderPort;
import com.mongodb.client.MongoCollection;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class OrderDBAdapter implements OrderPort {
    private final OrderRepository orderRepository;
    private final OrderMapperImpl orderMapper;
    private final MongoConfig mongoConfig;
    private static final Logger logger = LogManager.getLogger(OrderDBAdapter.class);

    @Override
    public Order saveOrder(Order order) {
        logger.info("Saving Order with ID: {}", order.getId());

        OrderEntity orderEntity = orderMapper.orderToOrderEntity(order);

        OrderEntity savedOrder = orderRepository.save(orderEntity);

        logger.info("Order with ID {} has been saved successfully", savedOrder.getId());

        return orderMapper.orderEntityToOrder(savedOrder);
    }

    @Override
    public Order getOrder(UUID id) {
        logger.trace("Chiking if Order with ID: {} exist", id);
        if (this.isAvailable(id)) {
            OrderEntity orderEntity = orderRepository.findById(id).get();
            logger.info("Fetching Order with ID: {}", id);
            return orderMapper.orderEntityToOrder(orderEntity);
        } else {
            logger.error("Order with ID {} not found", id);
            throw new NoSuchElementException("Can't find Order");
        }
    }

    public List<Order> listOrder() {
        logger.info("Listing all orders");
        MongoCollection<Document> collection = mongoConfig.getAllDocuments("Orders");
        List<Order> orderList = orderMapper.documentOrderTolistOrder(collection);
        return orderList;
    }

    @Override
    public List<Order> listOrder(User user) {
        logger.info("Listing orders for User with ID: {}", user.getId());
        List<Order> orderList = this.listOrder();
        List<Order> orderHasUser = orderList.stream().filter(
                order -> order.getUser().getId().equals(user.getId())
        ).collect(Collectors.toList());
        logger.info("Found {} orders for User with ID: {}", orderHasUser.size(), user.getId());

        return orderHasUser;
    }

    @Override
    public Order updateStateOrder(UUID id, OrderState orderState) {
        logger.info("Updating state for Order with ID: {}", id);
        Order order = this.getOrder(id);
        order.setOrderState(orderState);
        OrderEntity orderEntity = orderMapper.orderToOrderEntity(order);
        logger.debug("Order state updated for Order with ID: {} to {}", id, orderState);
        return orderMapper.orderEntityToOrder(orderRepository.save(orderEntity));
    }

    @Override
    public List<Order> listOrder(User user, OrderState orderState) {
        logger.info("Listing orders for User with ID: {} and OrderState: {}", user.getId(), orderState.getId());

        List<Order> ordersForUser = this.listOrder(user);

        logger.debug("Found {} orders for User with ID: {}", ordersForUser.size(), user.getId());

        List<Order> ordersForUserAndState = ordersForUser.stream()
                .filter(order -> order.getOrderState().getId().equals(orderState.getId()))
                .collect(Collectors.toList());

        logger.debug("Found {} orders for User with ID: {} and OrderState: {}",
                ordersForUserAndState.size(), user.getId(), orderState.getId());

        logger.info("Completed listing orders for User with ID: {} and OrderState: {}", user.getId(), orderState.getId());

        return ordersForUserAndState;
    }


    @Override
    public Boolean isAvailable(UUID id) {
        logger.info("Checking if Order with ID {} exists", id);
        return orderRepository.findById(id).isPresent();
    }
}