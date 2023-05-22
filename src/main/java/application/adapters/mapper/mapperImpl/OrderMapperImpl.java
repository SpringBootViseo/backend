package application.adapters.mapper.mapperImpl;

import application.adapters.mapper.OrderMapper;
import application.adapters.persistence.entity.*;
import application.adapters.web.presenter.*;
import application.domain.*;
import application.port.out.OrderStatePort;
import application.port.out.UserPort;
import com.mongodb.client.MongoCollection;
import lombok.AllArgsConstructor;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
public class OrderMapperImpl implements OrderMapper {
    private OrderStateMapperImpl orderStateMapper;
    private OrderItemMapperImpl orderItemMapper;
    private UserMapperImpl userMapper;
    private UserPort userPort;
    private OrderStatePort orderStatePort;
    private PreparateurMapperImpl preparateurMapper;
    @Override
    public Order orderEntityToOrder(OrderEntity orderEntity) {
        List<OrderItem> orderItemList=orderItemMapper.listOrderItemEntityTolistOrderItem(orderEntity.getOrderItems());
        OrderState orderState=orderStateMapper.orderStateEntityToOrderState(orderEntity.getOrderState());
        User user=userMapper.userEntityToUser(orderEntity.getUser());
        Preparateur preparateur=preparateurMapper.preparateurEntityToPreparateur(orderEntity.getPreparateur());
        return new Order(orderEntity.getId(),user,orderState,orderItemList,orderEntity.getTotalPrice(),orderEntity.getDateCommande(),preparateur);
    }


    @Override
    public Order orderDTOToOrder(OrderDTO orderDTO) {
        List<OrderItem> orderItemList=orderItemMapper.listOrderItemDTOTolistOrderItem(orderDTO.getOrderItems());
        OrderState orderState=orderStateMapper.orderStateDTOToOrderState(orderDTO.getOrderState());
        User user=userMapper.userDtoToUser(orderDTO.getUser());
        Preparateur preparateur=preparateurMapper.preparateurDTOToPreparateur(orderDTO.getPreparateurDTO());
        return new Order(orderDTO.getId(),user,orderState,orderItemList,orderDTO.getTotalPrice(),orderDTO.getDateCommande(),preparateur);    }

    @Override
    public OrderDTO orderToOrderDTO(Order order) {
        List<OrderItemDTO> orderItemList=orderItemMapper.listOrderItemTolistOrderItemDTO(order.getOrderItems());
        OrderStateDTO orderState=orderStateMapper.orderStateToOrderStateDTO(order.getOrderState());
        UserDTO user=userMapper.userToUserDTO(order.getUser());
        PreparateurDTO preparateurDTO=preparateurMapper.preparateurToPreparateurDTO(order.getPreparateur());
        return new OrderDTO(order.getId(),user,orderState,orderItemList,order.getTotalPrice(),order.getDateCommande(),preparateurDTO);
    }

    @Override
    public OrderEntity orderToOrderEntity(Order order) {
        List<OrderItemEntity> orderItemList=orderItemMapper.listOrderItemTolistOrderItemEntity(order.getOrderItems());
        OrderStateEntity orderState=orderStateMapper.orderStateToOrderStateEntity(order.getOrderState());
        UserEntity user=userMapper.userToUserEntity(order.getUser());
        PreparateurEntity preparateur=preparateurMapper.preparateurToPreparateurEntity(order.getPreparateur());
        return new OrderEntity(order.getId(),user,orderState,orderItemList,order.getTotalPrice(),order.getDateCommande(),preparateur);
    }

    @Override
    public List<Order> documentOrderTolistOrder(MongoCollection<Document> collection) {

        List<Order> orderList=new ArrayList<>();
        for(Document doc:collection.find()){
            List<OrderItem> orderItemList=new ArrayList<>();
            Document userDocument= doc.get("user", Document.class);
            List<Document> listAddressDocument=userDocument.getList("address",Document.class);
            List<Address> addresses=new ArrayList<>();
            for (Document addressDocument:listAddressDocument
                 ) {
                addresses.add(new Address(addressDocument.get("_id", UUID.class),addressDocument.getString("street"),addressDocument.getString("city"),addressDocument.getString("state")));
            }

            User user=new User(userDocument.getString("_id"),userDocument.getString("fullname"),userDocument.getString("email"),userDocument.getString("numberPhone"),userDocument.getString("picture"),addresses,userDocument.getInteger("avertissement"),userDocument.getBoolean("blackListed"));
            Document preparateurDocument=doc.get("preparateur", Document.class);
            Preparateur preparateur=new Preparateur(preparateurDocument.getString("firstname"),preparateurDocument.getString("lastname"),preparateurDocument.getString("_id"));
            Document orderStateDocument=doc.get("orderState",Document.class);
            OrderState orderState=new OrderState(orderStateDocument.getString("_id"),orderStateDocument.getString("state"));

            List<Document> orderItemsDocumentList=doc.getList("orderItems", Document.class);
            for (Document orderItemsDocument:
                    orderItemsDocumentList
                 ) {
                Document productDocument=orderItemsDocument.get("product",Document.class);
                Document categorydocument=productDocument.get("category", Document.class);
                Category category =new Category(categorydocument.get("_id", UUID.class),categorydocument.getString("name"),categorydocument.getString("linkImg"),categorydocument.getString("linkImgBanner"));
                Product product=new Product(productDocument.get("_id", UUID.class),productDocument.getString("name"),productDocument.getString("marque"),productDocument.getString("linkImg"),productDocument.getString("description"),productDocument.getInteger("storedQuantity"),productDocument.getInteger("orderedQuantity"),productDocument.getList("Images", String.class),productDocument.getString("unitQuantity"),productDocument.getDouble("reductionPercentage"),productDocument.getDouble("previousPrice") , productDocument.getDouble("currentPrice"),category);

                orderItemList.add(new OrderItem(product,orderItemsDocument.getInteger("quantity")));
            }
            orderList.add(new Order(doc.get("_id", UUID.class),user,orderState,orderItemList,doc.getLong("totalPrice"),doc.getString("dateCommande"),preparateur));

        }
        return orderList;


    }

    @Override
    public List<Order> listOrderEntityTolistOrder(List<OrderEntity> orderEntityList) {
        List<Order> orderList=new ArrayList<>();
        for (OrderEntity orderEntity:orderEntityList
             ) {
            orderList.add(this.orderEntityToOrder(orderEntity));
        }
        return orderList;
    }

    @Override
    public List<Order> listOrderDTOTolistOrder(List<OrderDTO> orderDTOList) {
        List<Order> orderList=new ArrayList<>();
        for (OrderDTO orderDTO:orderDTOList
        ) {
            orderList.add(this.orderDTOToOrder(orderDTO));
        }
        return orderList;
    }

    @Override
    public List<OrderDTO> listOrderTolistOrderDTO(List<Order> orderList) {
        List<OrderDTO> orderDTOList=new ArrayList<>();
        for (Order order:orderList
        ) {
            orderDTOList.add(this.orderToOrderDTO(order));
        }
        return orderDTOList;
    }

    @Override
    public List<OrderEntity> listOrderTolistOrderEntity(List<Order> orderList) {
        List<OrderEntity> orderEntityList=new ArrayList<>();
        for (Order order:orderList
        ) {
            orderEntityList.add(this.orderToOrderEntity(order));
        }
        return orderEntityList;
    }

    @Override
    public Order orderCreateRequestDTOToOrder(OrderCreateRequestDTO orderCreateRequestDTO) {
        User user=userPort.getUser(orderCreateRequestDTO.getIdUser());
        OrderState orderState=orderStatePort.getOrderState(orderCreateRequestDTO.getIdState());
        List<OrderItem> orderItemList=orderItemMapper.listOrderItemCreateRequestDTOTolistOrderItem(orderCreateRequestDTO.getOrderItems());

        return new Order(orderCreateRequestDTO.getId(),user,orderState,orderItemList, orderCreateRequestDTO.getTotalPrice(), orderCreateRequestDTO.getDateCommande(),null);
    }

    @Override
    public List<Order> listOrderCreateRequestDTOTolistOrder(List<OrderCreateRequestDTO> orderCreateRequestDTOList) {
        List<Order> orderList=new ArrayList<>();
        for (OrderCreateRequestDTO orderCreateRequestDTO:orderCreateRequestDTOList
             ) {
            orderList.add(this.orderCreateRequestDTOToOrder(orderCreateRequestDTO));

        }
        return orderList;
    }
}
