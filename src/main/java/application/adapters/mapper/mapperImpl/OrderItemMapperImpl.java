package application.adapters.mapper.mapperImpl;

import application.adapters.mapper.OrderItemMapper;
import application.adapters.persistence.entity.OrderItemEntity;
import application.adapters.web.presenter.OrderItemCreateRequestDTO;
import application.adapters.web.presenter.OrderItemDTO;
import application.domain.OrderItem;
import application.domain.Product;
import application.port.out.ProductPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
@AllArgsConstructor

public class OrderItemMapperImpl implements OrderItemMapper {
    ProductMapperImpl productMapper;
    ProductPort productPort;
    @Override
    public OrderItem orderItemEntityToOrderItem(OrderItemEntity orderItemEntity) {
        return new OrderItem(orderItemEntity.getProduct(), orderItemEntity.getQuantity());
    }

    @Override
    public OrderItemEntity orderItemToOrderItemEntity(OrderItem orderItem) {
        return new OrderItemEntity(orderItem.getProduct(), orderItem.getQuantity());
    }

    @Override
    public List<OrderItem> listOrderItemEntityTolistOrderItem(List<OrderItemEntity> orderItemEntityList) {
        List<OrderItem> orderItemList =new ArrayList<>();
        for (OrderItemEntity orderItemEntity : orderItemEntityList
             ) {
            orderItemList.add(this.orderItemEntityToOrderItem(orderItemEntity));
        }
        return orderItemList;
    }

    @Override
    public List<OrderItemEntity> listOrderItemTolistOrderItemEntity(List<OrderItem> orderItemList) {
        List<OrderItemEntity> orderItemEntityList =new ArrayList<>();
        for (OrderItem orderItem : orderItemList
             ) {
            orderItemEntityList.add(this.orderItemToOrderItemEntity(orderItem));
        }
        return orderItemEntityList;
    }

    @Override
    public OrderItem orderItemDTOToOrderItem(OrderItemDTO orderItemDTO) {
        return new OrderItem(productMapper.productToProductDto(orderItemDTO.getProduct()), orderItemDTO.getQuantity());
    }

    @Override
    public OrderItemDTO orderItemToOrderItemDTO(OrderItem orderItem) {
        return new OrderItemDTO(productMapper.productDtoToProduct(orderItem.getProduct()), orderItem.getQuantity());
    }

    @Override
    public List<OrderItem> listOrderItemDTOTolistOrderItem(List<OrderItemDTO> orderItemDTOList) {
        List<OrderItem> orderItemList =new ArrayList<>();
        for (OrderItemDTO orderItemDTO :
                orderItemDTOList) {
            orderItemList.add(this.orderItemDTOToOrderItem(orderItemDTO));

        }
        return orderItemList;
    }

    @Override
    public List<OrderItemDTO> listOrderItemTolistOrderItemDTO(List<OrderItem> orderItems) {
        List<OrderItemDTO> orderItemDTOList =new ArrayList<>();
        for(OrderItem orderItem : orderItems){
            orderItemDTOList.add(this.orderItemToOrderItemDTO(orderItem));
        }
        return orderItemDTOList;
    }

    @Override
    public OrderItem orderItemCreateRequestDTOToOrderItem(OrderItemCreateRequestDTO orderItemCreateRequestDTO) {
        Product product= productPort.getProduct(orderItemCreateRequestDTO.getId());
        return new OrderItem(product,orderItemCreateRequestDTO.getQuantity());
    }

    @Override
    public List<OrderItem> listOrderItemCreateRequestDTOTolistOrderItem(List<OrderItemCreateRequestDTO> orderItemCreateRequestDTOList) {
        List <OrderItem> orderItemList=new ArrayList<>();
        for (OrderItemCreateRequestDTO orderItemCreateRequestDTO:orderItemCreateRequestDTOList){
            orderItemList.add(this.orderItemCreateRequestDTOToOrderItem(orderItemCreateRequestDTO));
        }
        return orderItemList;
    }
}
