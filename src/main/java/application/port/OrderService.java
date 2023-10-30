package application.port;

import application.adapters.exception.OrderAlreadyExistException;
import application.adapters.exception.ProductNotAvailableException;
import application.domain.*;
import application.port.in.OrderUseCase;
import application.port.out.*;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final OrderPort orderPort;
    private final UserPort userPort;
    private final ProductPort productPort;
    private final PaymentPort paymentPort;
    private final OrderStatePort orderStatePort;
    private final PreparateurPort preparateurPort;
    private final LivreurPort livreurPort;

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Override
    public Order saveOrder(Order order) {
        // INFO level for a significant operation
        logger.info("Saving order: {}", order);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a");
        order.setDateCommande(now.format(formatter));
        order.setTotalPrice(0.0);
        order.setPreparateur(new Preparateur());

        if (this.isAvailable(order.getId())) {
            logger.error("Order with ID {} already exists", order.getId());
            throw new OrderAlreadyExistException();
        }
        if (order.getOrderItems().isEmpty()) {
            logger.error("No order items in the order");
            throw new ProductNotAvailableException();
        } else {
            List<OrderItem> orderItemList = order.getOrderItems();
            for (OrderItem orderItem : orderItemList) {
                try {
                    Product orderedProduct = productPort.orderProduct(orderItem.getProduct().getId(), orderItem.getQuantity());
                    order.setTotalPrice(order.getTotalPrice() + orderedProduct.getCurrentPrice() * orderItem.getQuantity());
                    orderItem.setProduct(orderedProduct);
                } catch (ProductNotAvailableException e) {
                    logger.error("Can't purchase this order");
                    throw new NoSuchElementException("Can't Purchase this Order");
                }
            }
            OrderState orderState = orderStatePort.getOrderState("commandé");
            order.setOrderState(orderState);
            return orderPort.saveOrder(order);
        }
    }

    @Override
    public Order getOrder(UUID id) {
        if (this.isAvailable(id)) {
            logger.info("Getting order with ID: {}", id);
            return orderPort.getOrder(id);
        } else {
            logger.error("Can't find Order with ID: {}", id);
            throw new NoSuchElementException("Can't found Order with " + id);
        }
    }

    @Override
    public List<Order> listOrder(String idUser) {
        // INFO level for a significant operation
        logger.info("Listing orders for user with ID: {}", idUser);

        User user = userPort.getUser(idUser);
        return orderPort.listOrder(user);
    }

    @Override
    public Order updateStateOrder(UUID id, String idorderState) {
        // INFO level for a significant operation
        logger.info("Updating order state for order with ID: {} to state: {}", id, idorderState);

        OrderState orderState = orderStatePort.getOrderState(idorderState);
        return orderPort.updateStateOrder(id, orderState);
    }

    @Override
    public List<Order> listOrder(String idUser, String idorderState) {
        // INFO level for a significant operation
        logger.info("Listing orders for user with ID: {} in state: {}", idUser, idorderState);

        User user = userPort.getUser(idUser);
        OrderState orderState = orderStatePort.getOrderState(idorderState);
        return orderPort.listOrder(user, orderState);
    }

    @Override
    public Boolean isAvailable(UUID id) {
        // DEBUG level for a routine operation
        logger.debug("Checking if order with ID {} is available", id);
        return orderPort.isAvailable(id);
    }

    @Override
    public List<Order> listOrder() {
        // INFO level for a significant operation
        logger.info("Listing all orders");
        return orderPort.listOrder();
    }

    @Override
    public Order payerOrder(UUID id, String emailLivreur) throws IllegalAccessException {
        if (orderPort.getOrder(id).getOrderState().getState().equals("prête à livré")) {
            // INFO level for a significant operation
            logger.info("Paying order with ID: {}", id);

            OrderState payedState = orderStatePort.getOrderState("payé");
            Order order = orderPort.updateStateOrder(id, payedState);
            Livreur livreur = livreurPort.getLivreur(emailLivreur);
            Payment payment = new Payment(id, order.getTotalPrice(), order.getUser(), livreur);
            paymentPort.savePayment(payment);
            return order;
        } else {
            logger.warn("Can't pay this order");
            throw new IllegalAccessException("Impossible de payer cette commande ");
        }
    }

    @Override
    public Order preparerOrder(UUID id) throws IllegalAccessException {
        if (orderPort.getOrder(id).getOrderState().getState().equals("commandé")) {
            logger.info("Preparing order with ID: {}", id);

            OrderState onPrepareState = orderStatePort.getOrderState("en préparation");
            return orderPort.updateStateOrder(id, onPrepareState);
        } else {
            logger.warn("Can't prepare this order");
            throw new IllegalAccessException("Impossible de préparer cette commande ");
        }
    }

    @Override
    public Order readyForDeliveryOrder(UUID id) throws IllegalAccessException {
        if (orderPort.getOrder(id).getOrderState().getState().equals("en préparation")) {
            // INFO level for a significant operation
            logger.info("Order is ready for delivery with ID: {}", id);

            OrderState readyForDeliveryState = orderStatePort.getOrderState("prête à livré");
            return orderPort.updateStateOrder(id, readyForDeliveryState);
        } else {
            logger.warn("Can't mark this order as ready for delivery");
            throw new IllegalAccessException("Impossible de marquer cette commande comme prête à livrer ");
        }
    }


    @Override
    public Order attribuerOrder(UUID idOrder, String emailPreparateur) throws IllegalAccessException {
        // INFO level for a significant operation
        logger.info("Attributing order with ID: {} to preparateur with email: {}", idOrder, emailPreparateur);

        if (preparateurPort.isPreparateur(emailPreparateur)) {
            Preparateur preparateur = preparateurPort.getPreparateur(emailPreparateur);

            if (orderPort.getOrder(idOrder).getOrderState().getState().equals("commandé")) {
                OrderState onPrepareState = orderStatePort.getOrderState("en préparation");
                Order order = orderPort.updateStateOrder(idOrder, onPrepareState);
                order.setPreparateur(preparateur);
                return orderPort.saveOrder(order);
            } else {
                logger.warn("Can't attribute this order to the preparateur");
                throw new IllegalAccessException("Impossible de préparer cette commande ");
            }
        } else {
            logger.warn("No preparateur found with email: {}", emailPreparateur);
            throw new NoSuchElementException("No such Preparator with such email!");
        }
    }

    @Override
    public List<Order> listOrderPreparateur(String email) {
        // INFO level for a significant operation
        logger.info("Listing orders for preparateur with email: {}", email);

        List<Order> orderList = orderPort.listOrder();
        List<Order> ordersHasPreparateurNotNull = orderList.stream().filter(
                order -> order.getPreparateur() != null
        ).collect(Collectors.toList());

        List<Order> ordersHasPreparateurEmail = ordersHasPreparateurNotNull.stream().filter(
                order -> order.getPreparateur().getEmail() != null
        ).collect(Collectors.toList());

        List<Order> result = ordersHasPreparateurEmail.stream().filter(
                order -> order.getPreparateur().getEmail().equals(email)
        ).collect(Collectors.toList());

        return result;
    }
}

