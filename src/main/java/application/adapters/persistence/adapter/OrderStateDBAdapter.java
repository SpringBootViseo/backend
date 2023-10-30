package application.adapters.persistence.adapter;

import application.adapters.exception.OrderStateAlreadyExistsException;
import application.adapters.mapper.mapperImpl.OrderStateMapperImpl;
import application.adapters.persistence.entity.OrderStateEntity;
import application.adapters.persistence.repository.OrderStateRepository;
import application.domain.OrderState;
import application.port.out.OrderStatePort;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@AllArgsConstructor
public class OrderStateDBAdapter implements OrderStatePort {
    private final OrderStateRepository orderStateRepository;
    private final OrderStateMapperImpl orderStateMapper;
    private static final Logger logger = LoggerFactory.getLogger(OrderStateDBAdapter.class);

    @Override
    public OrderState getOrderState(String id) {
        logger.trace("checking if OrdrState with id : {} exist",id);
        if (orderStateRepository.findById(id).isPresent()) {
            logger.debug("Fetching OrderState with ID: {}", id);
            OrderStateEntity orderStateEntity = orderStateRepository.findById(id).get();
            logger.info("Fetched OrderState with ID: {}", id);
            return orderStateMapper.orderStateEntityToOrderState(orderStateEntity);
        } else {
            logger.error("OrderState with ID {} not found", id);
            throw new NoSuchElementException("Can't find this state");
        }
    }

    @Override
    public void deleteOrderState(String id) {
        logger.trace("Checking if Order with id : {} exist",id);
        if (orderStateRepository.findById(id).isPresent()) {
            logger.debug("Deleting OrderState with ID: {}", id);
            orderStateRepository.deleteById(id);
        } else {
            logger.error("OrderState with ID {} not found", id);
            throw new NoSuchElementException("Can't find this state");
        }
    }

    @Override
    public OrderState createOrderState(OrderState orderState) {
        logger.trace("Checking for the existence of OrderState with ID: {}", orderState.getId());

        if (orderStateRepository.findById(orderState.getId()).isEmpty()) {
            logger.debug("Creating OrderState with ID: {}", orderState.getId());

            OrderStateEntity savedOrderState = orderStateRepository.save(orderStateMapper.orderStateToOrderStateEntity(orderState));

            logger.info("OrderState with ID {} has been created successfully", savedOrderState.getId());

            return orderStateMapper.orderStateEntityToOrderState(savedOrderState);
        } else {
            logger.error("OrderState with ID {} already exists", orderState.getId());

            throw new OrderStateAlreadyExistsException();
        }
    }

}
