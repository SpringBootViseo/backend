package application.port;

import application.domain.OrderState;
import application.port.in.OrderStateUseCase;
import application.port.out.OrderStatePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class OrderStateService implements OrderStateUseCase {
    private  final OrderStatePort orderStatePort;
    @Override
    public OrderState getOrderState(String id) {
        return orderStatePort.getOrderState(id);
    }

    @Override
    public void deleteOrderState(String id) {
        orderStatePort.deleteOrderState(id);
    }

    @Override
    public OrderState createOrderState(OrderState orderState) {
        return orderStatePort.createOrderState(orderState);
    }
}
