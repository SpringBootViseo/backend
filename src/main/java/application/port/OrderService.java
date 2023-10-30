package application.port;

import application.adapters.exception.OrderAlreadyExistException;
import application.adapters.exception.ProductNotAvailableException;
import application.domain.*;
import application.port.in.OrderUseCase;
import application.port.out.*;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class OrderService implements OrderUseCase {
    private final static Logger logger= LogManager.getLogger(OrderService.class);
    private final OrderPort orderPort;
    private final UserPort userPort;
    private final ProductPort productPort;
    private  final PaymentPort paymentPort;
    private final OrderStatePort orderStatePort;
    private  final PreparateurPort preparateurPort;
    private final LivreurPort livreurPort;
    @Override
    public Order saveOrder(Order order) {
        logger.debug("Retrieve date of now");
        LocalDateTime now = LocalDateTime.now();
        logger.debug("Format the date of now "+now.toString());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a");
        logger.debug("Attribute the date to date Commande"+now.format(formatter));
        order.setDateCommande(now.format(formatter));
        logger.debug("Set the total price to 0");
        order.setTotalPrice(0.0);
        logger.debug("Instantiate Preparateur ");
        order.setPreparateur(new Preparateur());

        if(this.isAvailable(order.getId())){
            logger.error("Order id Already exist");
            throw new OrderAlreadyExistException();
        }
        if(order.getOrderItems().isEmpty()){
            logger.error("No orderItem exist");
            throw new ProductNotAvailableException();
        }
        else{
            logger.debug("Get orderItems");
            List<OrderItem> orderItemList=order.getOrderItems();
            logger.debug("Calculate total of order");
            for (OrderItem orderItem: orderItemList){
                try {
                    Product orderedProduct=productPort.orderProduct(orderItem.getProduct().getId(), orderItem.getQuantity());
                    logger.trace("Retrieve price of product : "+orderedProduct.toString());
                    order.setTotalPrice( (Double) (order.getTotalPrice()+orderItem.getProduct().getCurrentPrice()*orderItem.getQuantity()));
                    logger.trace("Set total price of order "+order.getTotalPrice());
                    orderItem.setProduct(orderedProduct);

                }
                catch (ProductNotAvailableException e){
                    logger.error("Can't Purchase this Order");
                    throw new NoSuchElementException("Can't Purchase this Order");
                }
            }
            logger.debug("Put order state of order to commandé");
            OrderState orderState=orderStatePort.getOrderState("commandé");
            order.setOrderState(orderState);
            logger.info("Create order "+ order.toString());
            return orderPort.saveOrder(order);
        }

    }

    @Override
    public Order getOrder(UUID id) {
        if(this.isAvailable(id)){
            logger.info("Retrieve order of id "+id);
            return orderPort.getOrder(id) ;
        }

        else{
            logger.error("Can't found Order with "+id);
            throw new NoSuchElementException("Can't found Order with "+id);
        }
    }

    @Override
    public List<Order> listOrder(String idUser) {
        logger.debug("Retrieve user of id "+idUser);
        User user=userPort.getUser(idUser);
        logger.info("Retrieve orders ordered by user"+user.toString());
        return orderPort.listOrder(user);
    }

    @Override
    public Order updateStateOrder(UUID id, String idorderState) {
        logger.debug("Retrieve Order state of id "+idorderState);
        OrderState orderState=orderStatePort.getOrderState(idorderState);
        logger.info("Update order state of order with id "+id+" to "+orderState.toString());
        return orderPort.updateStateOrder(id,orderState);
    }

    @Override
    public List<Order> listOrder(String idUser, String idorderState) {
        logger.debug("Retrieve User of id "+idUser);
        User user=userPort.getUser(idUser);
        logger.debug("Retrieve orderState of id"+idorderState);
        OrderState orderState=orderStatePort.getOrderState(idorderState);
        logger.info("Get Orders of user "+user.toString()+" that have state "+orderState.toString());
        return orderPort.listOrder(user,orderState);
    }


    @Override
    public Boolean isAvailable(UUID id) {
        logger.debug("Check if order with id "+ id+ " exist");
        return orderPort.isAvailable(id);
    }

    @Override
    public List<Order> listOrder() {
        logger.info("Get all Orders");
        return orderPort.listOrder();
    }

    @Override
    public Order payerOrder(UUID id,String emailLivreur) throws IllegalAccessException {
        logger.debug("prête à livré");
        if(orderPort.getOrder(id).getOrderState().getState().equals("prête à livré")){
            logger.trace("Retrieve order state of payé");
            OrderState payedState=orderStatePort.getOrderState("payé");
            logger.trace("Update order state of order of  id "+id+"to payé");
            Order order =orderPort.updateStateOrder(id,payedState);
            logger.trace("Retrieve Livreur with email"+emailLivreur);
            Livreur livreur=livreurPort.getLivreur(emailLivreur);
            logger.trace("Create Payement");
            Payment payment=new Payment(id,order.getTotalPrice(),order.getUser(),livreur);
            logger.info("Pay the payment of id "+id+" to livreur "+livreur.toString());
            paymentPort.savePayment(payment);
            return order;
        }
       else{
           logger.error("Impossible de payer cette commande ");
           throw new IllegalAccessException("Impossible de payer cette commande ");
        }
    }


    @Override
    public Order preparerOrder(UUID id) throws IllegalAccessException {
        logger.debug("check if order has state commandé");
        if(orderPort.getOrder(id).getOrderState().getState().equals("commandé")) {
            logger.trace("Retrieve order state of en préparation");

            OrderState onPrepareState = orderStatePort.getOrderState("en préparation");
            logger.info("¨Prepare commande of id "+id);

            return orderPort.updateStateOrder(id, onPrepareState);
        }
        else {
            logger.error("Impossible de préparer cette commande ");
            throw new IllegalAccessException("Impossible de préparer cette commande ");
        }

    }

    @Override
    public Order readyForDeliveryOrder(UUID id) throws IllegalAccessException {
        logger.debug("check if order has state prête à livré");

        if(orderPort.getOrder(id).getOrderState().getState().equals("en préparation")) {
            logger.trace("Retrieve order state of prête à livré");

        OrderState readyForDeliveryState = orderStatePort.getOrderState("prête à livré");
            logger.trace("Update order state of order of  id "+id+"to prête à livré");
            logger.info("order of id "+id+" is ready to go");

        return orderPort.updateStateOrder(id, readyForDeliveryState);
    }else{
            logger.error("Impossible de payé cette commande ");
            throw new IllegalAccessException("Impossible de payé cette commande ");
        }

    }

    @Override
    public Order attribuerOrder(UUID idOrder, String emailPreparateur) throws IllegalAccessException {
        logger.debug("check if a preparateur has mail "+emailPreparateur);

        if(preparateurPort.isPreparateur(emailPreparateur)){
            logger.trace("Retrieve preparateur with mail "+emailPreparateur);
            Preparateur preparateur=preparateurPort.getPreparateur(emailPreparateur);
            logger.trace("check if order has state commandé");

            if(orderPort.getOrder(idOrder).getOrderState().getState().equals("commandé")) {
                logger.trace("Retrieve order state of commandé");

                OrderState onPrepareState = orderStatePort.getOrderState("en préparation");
                logger.trace("Update order state of order of  id "+idOrder+"to en préparation");

                Order order= orderPort.updateStateOrder(idOrder, onPrepareState);
                logger.info("Order of id"+idOrder+" prepared by"+preparateur.toString());
                order.setPreparateur(preparateur);
                return orderPort.saveOrder(order);
            }
            else{
                logger.error("Impossible de préparer cette commande ");
                throw new IllegalAccessException("Impossible de préparer cette commande ");
            }


        }
        else{
            logger.error("No such Preparator with such email "+emailPreparateur);
            throw new NoSuchElementException("No such Preparator with such email!");

        }
    }

    @Override
    public List<Order> listOrderPreparateur(String email) {
        logger.debug("list all orders");
        List<Order> orderList=orderPort.listOrder();
        logger.debug("Filter list of orders and ommit orders without preparateur");
        List<Order> ordersHasPreparateurNotNull=orderList.stream().filter(

                   order ->order.getPreparateur()!=null


        ).collect(Collectors.toList());
        logger.debug("Filter list of orders and ommit orders with preparateur without email");

        List<Order> ordersHasPreparateurEmail=ordersHasPreparateurNotNull.stream().filter(
                order ->order.getPreparateur().getEmail()!=null

        ).collect(Collectors.toList());
        logger.info("Filter list of orders with preparateur has email "+email);
        List<Order> result=ordersHasPreparateurEmail.stream().filter(
                order ->

                     order.getPreparateur().getEmail().equals(email)

        ).collect(Collectors.toList());


        return result;
    }

}