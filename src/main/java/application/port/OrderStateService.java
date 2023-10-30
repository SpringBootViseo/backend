package application.port;

import application.domain.OrderState;
import application.port.in.OrderStateUseCase;
import application.port.out.OrderStatePort;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class OrderStateService implements OrderStateUseCase {
    private final OrderStatePort orderStatePort;
    private static final Logger logger = LoggerFactory.getLogger(OrderStateService.class);

    @Override
    public OrderState getOrderState(String id) {
        // INFO level for a significant operation
        logger.info("Getting order state with ID: {}", id);
        return orderStatePort.getOrderState(id);
    }

    @Override
    public void deleteOrderState(String id) {
        // INFO level for a significant operation
        logger.info("Deleting order state with ID: {}", id);
        orderStatePort.deleteOrderState(id);
    }

    @Override
    public OrderState createOrderState(OrderState orderState) {
        // INFO level for a significant operation
        logger.info("Creating order state: {}", orderState);
        return orderStatePort.createOrderState(orderState);
    }
}
