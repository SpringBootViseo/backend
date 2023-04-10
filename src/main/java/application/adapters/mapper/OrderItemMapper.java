package application.adapters.mapper;

import application.adapters.persistence.entity.OrderItemEntity;
import application.adapters.web.presenter.OrderItemCreateRequestDTO;
import application.adapters.web.presenter.OrderItemDTO;
import application.domain.OrderItem;

import java.util.List;

public interface OrderItemMapper {
    OrderItem orderItemEntityToOrderItem(OrderItemEntity orderItemEntity);
    OrderItemEntity orderItemToOrderItemEntity(OrderItem orderItem);
    List<OrderItem> listOrderItemEntityTolistOrderItem(List<OrderItemEntity> orderItemEntityList);
    List<OrderItemEntity> listOrderItemTolistOrderItemEntity(List<OrderItem> orderItemList);
    OrderItem orderItemDTOToOrderItem(OrderItemDTO orderItemDTO);
    OrderItemDTO orderItemToOrderItemDTO(OrderItem orderItem);
    List<OrderItem> listOrderItemDTOTolistOrderItem(List<OrderItemDTO> orderItemDTOList);
    List<OrderItemDTO> listOrderItemTolistOrderItemDTO(List<OrderItem> orderItems);
    OrderItem orderItemCreateRequestDTOToOrderItem(OrderItemCreateRequestDTO orderItemCreateRequestDTO);
    List<OrderItem> listOrderItemCreateRequestDTOTolistOrderItem(List<OrderItemCreateRequestDTO> orderItemCreateRequestDTOList);


}
