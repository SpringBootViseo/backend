package application.port;

import application.domain.OrderState;
import application.port.in.OrderStateUseCase;
import application.port.out.OrderStatePort;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.plexus.logging.LoggerManager;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class OrderStateService implements OrderStateUseCase {
    private  final OrderStatePort orderStatePort;
    private final static Logger logger= LogManager.getLogger(OrderService.class);
    @Override
    public OrderState getOrderState(String id) {
        logger.info("Retrieve orderState with id "+id);
        return orderStatePort.getOrderState(id);
    }

    @Override
    public void deleteOrderState(String id) {

        logger.info("Delete orderState with id "+id);

        orderStatePort.deleteOrderState(id);
    }

    @Override
    public OrderState createOrderState(OrderState orderState)
    {
        logger.info("create orderState "+orderState.toString());

        return orderStatePort.createOrderState(orderState);
    }
}