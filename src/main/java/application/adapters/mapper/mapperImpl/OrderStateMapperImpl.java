package application.adapters.mapper.mapperImpl;

import application.adapters.mapper.OrderStateMapper;
import application.adapters.persistence.entity.OrderStateEntity;
import application.adapters.web.presenter.OrderStateDTO;
import application.domain.OrderState;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderStateMapperImpl implements OrderStateMapper {
    @Override
    public OrderState orderStateEntityToOrderState(OrderStateEntity orderStateEntity) {
        return new OrderState(orderStateEntity.getId(),orderStateEntity.getState());
    }

    @Override
    public OrderState orderStateDTOToOrderState(OrderStateDTO orderStateDTO) {
        return new OrderState(orderStateDTO.getId(),orderStateDTO.getState());

    }

    @Override
    public OrderStateDTO orderStateToOrderStateDTO(OrderState orderState) {
        return new OrderStateDTO(orderState.getId(),orderState.getState());
    }

    @Override
    public OrderStateEntity orderStateToOrderStateEntity(OrderState orderState) {
        return new OrderStateEntity(orderState.getId(),orderState.getState());

    }
}
