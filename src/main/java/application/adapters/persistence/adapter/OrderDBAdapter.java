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

    @Override
    public Order saveOrder(Order order) {

        OrderEntity orderEntity=orderMapper.orderToOrderEntity(order);
        OrderEntity savedOrder=orderRepository.save(orderEntity);
        return orderMapper.orderEntityToOrder(savedOrder);
    }

    @Override
    public Order getOrder(UUID id) {
        if(this.isAvailable(id)){
            OrderEntity orderEntity=orderRepository.findById(id).get();
            return orderMapper.orderEntityToOrder(orderEntity);
        }
        else throw new NoSuchElementException("Can't find Order");
    }
    public List<Order> listOrder(){
        MongoCollection<Document> collection= mongoConfig.getAllDocuments("Orders");
        List<Order> orderList=orderMapper.documentOrderTolistOrder(collection);
return orderList;
    }

    @Override
    public List<Order> listOrder(User user) {
        List<Order> orderList=this.listOrder();
        List<Order> orderHasUser=orderList.stream().filter(
                order -> order.getUser().getId().equals(user.getId())
        ).collect(Collectors.toList());
        return orderHasUser;
    }

    @Override
    public Order updateStateOrder(UUID id, OrderState orderState) {
        Order order=this.getOrder(id);
        order.setOrderState(orderState);
        OrderEntity orderEntity=orderMapper.orderToOrderEntity(order);
        return orderMapper.orderEntityToOrder(orderRepository.save(orderEntity));
    }

    @Override
    public List<Order> listOrder(User user, OrderState orderState) {
        List<Order> orderhasUser=this.listOrder(user);
        List<Order> orderHasUserAndState=orderhasUser.stream().filter(
                order -> order.getOrderState().getId().equals(orderState.getId())
        ).collect(Collectors.toList());
        return orderHasUserAndState;
    }

    @Override
    public Boolean isAvailable(UUID id) {

        return orderRepository.findById(id).isPresent();
    }
}
