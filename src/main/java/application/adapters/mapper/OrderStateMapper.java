package application.adapters.mapper;

import application.adapters.persistence.entity.OrderStateEntity;
import application.adapters.web.presenter.OrderStateDTO;
import application.domain.OrderState;

public interface OrderStateMapper {
    OrderState orderStateEntityToOrderState(OrderStateEntity orderStateEntity);
    OrderState orderStateDTOToOrderState(OrderStateDTO orderStateDTO);
    OrderStateDTO orderStateToOrderStateDTO(OrderState orderState);
    OrderStateEntity orderStateToOrderStateEntity(OrderState orderState);

}
