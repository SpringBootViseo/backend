package application.adapters.persistence.adapter;

import application.adapters.exception.OrderStateAlreadyExistsException;
import application.adapters.mapper.mapperImpl.OrderStateMapperImpl;
import application.adapters.persistence.entity.OrderStateEntity;
import application.adapters.persistence.repository.OrderStateRepository;
import application.domain.OrderState;
import application.port.out.OrderStatePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@AllArgsConstructor
public class OrderStateDBAdapter implements OrderStatePort {
    private  final OrderStateRepository orderStateRepository;
    private final OrderStateMapperImpl orderStateMapper;
    @Override
    public OrderState getOrderState(String id) {
        if(orderStateRepository.findById(id).isPresent()){
            OrderStateEntity orderStateEntity=orderStateRepository.findById(id).get();
            return orderStateMapper.orderStateEntityToOrderState(orderStateEntity);
        }
        else throw new NoSuchElementException("Can't find this state");
    }

    @Override
    public void deleteOrderState(String id) {
        if(orderStateRepository.findById(id).isPresent()){
            orderStateRepository.deleteById(id);
        }
        else throw new NoSuchElementException("Can't find this state");

    }

    @Override
    public OrderState createOrderState(OrderState orderState)  {
        if( orderStateRepository.findById(orderState.getId()).isEmpty()){
            OrderStateEntity savedOrderState=orderStateRepository.save(orderStateMapper.orderStateToOrderStateEntity(orderState));
            return orderStateMapper.orderStateEntityToOrderState(savedOrderState);
        }
        else throw new OrderStateAlreadyExistsException();
    }
}
