package application.port;

import application.adapters.exception.OrderAlreadyExistException;
import application.adapters.exception.ProductNotAvailableException;
import application.adapters.exception.UserNotFoundException;
import application.domain.*;
import application.port.out.OrderPort;
import application.port.out.OrderStatePort;
import application.port.out.ProductPort;
import application.port.out.UserPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

class OrderServiceTest {
    @InjectMocks
    OrderService orderService;
    @Mock
    OrderPort orderPort;
    @Mock
    UserPort userPort;
    @Mock
    ProductPort productPort;
    @Mock
    OrderStatePort orderStatePort;
    Order order;
    User user;
    Category category;
    Product product1;
    Product product;
    OrderState readyState;
    UUID id;
    UUID uuid;
    OrderState deliveredState;
    List<OrderItem> orderItemList;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        id=UUID.randomUUID();
        uuid=UUID.randomUUID();
        user=new User("VQl0nhjeMgP1CAunvAt7Ff7kA2","Abdessamad","abdessamad@gmail.com","0612649174","Casablanca",null,0,false);
        category=new Category(id,"test","test","test");

        product = new Product(id,"test","test","test","test", 20,10, List.of(new String[]{"test", "test"}),"test",0.0,0.0,100.0,category);
        product1 = new Product(uuid,"test1","test1","test1","test1", 20,10, List.of(new String[]{"test1", "test1"}),"test1",0.0,0.0,10.0,category);
        readyState=new OrderState("ready","ready to deliver");
        deliveredState=new OrderState("delivered","delivered to the Client");
        orderItemList=new ArrayList<>();
        orderItemList.add(new OrderItem(product,1));
        orderItemList.add(new OrderItem(product1,3));
        order=new Order(id,user,readyState,orderItemList,0L,"15-12-2020");
    }

    @AfterEach
    void tearDown() {
        id=null;
        uuid=null;
        user=null;
        category=null;
        product = null;
        product1 = null;
        readyState=null;
        deliveredState=null;
        orderItemList=null;
        order=null;

    }

    @Test
    void shouldThrowOrderAlreadyExistExceptionWhenSaveOrderWithAvailableId() {
        given(orderService.isAvailable(id)).willReturn(true);
        assertThrows(OrderAlreadyExistException.class,()->orderService.saveOrder(order));
    }
    @Test
    void shouldThrowProductNotAvailableExceptionWhenSaveOrderWithUnAvailableProduct() {
        given(orderService.isAvailable(id)).willReturn(false);
        given(productPort.orderProduct(id,1)).willThrow(ProductNotAvailableException.class);
        assertThrows(NoSuchElementException.class,()->orderService.saveOrder(order));
    }
    @Test
    void shouldSaveOrderUpdateProductWhenSaveOrderWithAvailableProduct(){
        Product updatedProduct = new Product(id,"test","test","test","test", 20,11, List.of(new String[]{"test", "test"}),"test",0.0,0.0,100.0,category);
        Product updatedProduct1=new Product(uuid,"test1","test1","test1","test1", 20,13, List.of(new String[]{"test1", "test1"}),"test1",0.0,0.0,10.0,category);
        List<OrderItem> orderItemList1=new ArrayList<>();
        orderItemList1.add(new OrderItem(updatedProduct,1));
        orderItemList1.add(new OrderItem(updatedProduct1,3));
        Order savedOrder=new Order(id,user,readyState,orderItemList1,130L,"15-12-2020");

        given(orderService.isAvailable(id)).willReturn(false);
        given(productPort.orderProduct(id,1)).willReturn(updatedProduct);
        given(productPort.orderProduct(uuid,3)).willReturn(updatedProduct1);
        given(orderPort.saveOrder(savedOrder)).willReturn(savedOrder);
        given(orderPort.saveOrder(order)).willReturn(savedOrder);
        assertEquals(orderService.saveOrder(order),savedOrder);


    }

    @Test
    void shouldThrowNoSuchElementExceptionWhenGetOrderWithUnavailableId() {
        given(orderService.isAvailable(id)).willReturn(false);
        assertThrows(NoSuchElementException.class,()->orderService.getOrder(id));
    }
    @Test
    void shouldReturnOrderWhenGetOrderWithUnavailableId() {
        given(orderService.isAvailable(id)).willReturn(true);
        given(orderPort.getOrder(id)).willReturn(order);
        assertEquals(order,orderService.getOrder(id));
    }

    @Test
    void shouldThrowUserNotFoundWhenlistOrderWithUnavailableUser() {
        given(userPort.getUser("test")).willThrow(UserNotFoundException.class);
        assertThrows(UserNotFoundException.class,()->orderService.listOrder("test"));
    }
    @Test
    void shouldReturnListOfOrderWhenListOrderWithAvailableUser(){
        given(userPort.getUser("VQl0nhjeMgP1CAunvAt7Ff7kA2")).willReturn(user);
        given(orderPort.listOrder(user)).willReturn(List.of(order));
        assertEquals(orderService.listOrder("VQl0nhjeMgP1CAunvAt7Ff7kA2"),List.of(order));
    }
    @Test
    void shouldReturnEmptyListWhenListOrderWithAvailableUserWithNoOrder(){
        given(userPort.getUser("VQl0nhjeMgP1CAunvAt7Ff7kA2")).willReturn(user);
        given(orderPort.listOrder(user)).willReturn(Collections.emptyList());
        assertTrue(orderService.listOrder("VQl0nhjeMgP1CAunvAt7Ff7kA2").isEmpty());
    }

    @Test
    void shouldupdateStateOrderWhenUpdateStateWithAvailableOrderAndState() {
        Order resultedOrder= new Order(id,user,deliveredState,orderItemList,0L,"15-12-2020");
        given(orderStatePort.getOrderState("delivered")).willReturn(deliveredState);
        given(orderPort.updateStateOrder(id,deliveredState)).willReturn(resultedOrder);
        assertEquals(orderService.updateStateOrder(id,"delivered"),resultedOrder);

    }
    @Test
    void shouldThrowNoSuchElementWhenUpdateStateWithUnavailableState(){
        given(orderStatePort.getOrderState("test")).willThrow(NoSuchElementException.class);
        assertThrows(NoSuchElementException.class,()->orderService.updateStateOrder(id,"test"));
    }

    @Test
    void shouldThrowUserNotFoundWhenlistOrderWithUnavailableUserAndAvailableState() {
        given(userPort.getUser("test")).willThrow(UserNotFoundException.class);
        assertThrows(UserNotFoundException.class,()->orderService.listOrder("test","ready"));
    }
    @Test
    void shouldReturnListOfOrderWhenListOrderWithAvailableUserAndUnavailableState(){
        given(userPort.getUser("VQl0nhjeMgP1CAunvAt7Ff7kA2")).willReturn(user);
        given(orderStatePort.getOrderState("test")).willThrow(NoSuchElementException.class);
        assertThrows(NoSuchElementException.class,()->orderService.listOrder("VQl0nhjeMgP1CAunvAt7Ff7kA2","test"));
    }
    @Test
    void shouldReturnEmptyListWhenListOrderWithAvailableUserAvailableStateWithNoOrder(){
        given(userPort.getUser("VQl0nhjeMgP1CAunvAt7Ff7kA2")).willReturn(user);
        given(orderStatePort.getOrderState("ready")).willReturn(readyState);
        given(orderPort.listOrder(user,readyState)).willReturn(Collections.emptyList());
        assertTrue(orderService.listOrder("VQl0nhjeMgP1CAunvAt7Ff7kA2","ready").isEmpty());
    }
    @Test
    void shouldReturnOrderListWhenListOrderWithAvailableUserAvailableStateWithNoOrder(){
        given(userPort.getUser("VQl0nhjeMgP1CAunvAt7Ff7kA2")).willReturn(user);
        given(orderStatePort.getOrderState("ready")).willReturn(readyState);
        given(orderPort.listOrder(user,readyState)).willReturn(List.of(order));
        assertEquals(orderService.listOrder("VQl0nhjeMgP1CAunvAt7Ff7kA2","ready"),List.of(order));
    }

    @Test
    void shouldReturnTrueWhenisAvailableWithAvailableOrder() {
        given(orderPort.isAvailable(id)).willReturn(true);
        assertTrue(orderService.isAvailable(id));
    }
    @Test
    void shouldReturnFalseWhenisAvailableWithUnavailableOrder(){
        given(orderPort.isAvailable(id)).willReturn(false);
        assertFalse(orderService.isAvailable(id));
    }
    @DisplayName("should pay order if order is prête à livré")
    @Test
    void function() throws IllegalAccessException {
        OrderState payed=new OrderState("payé","payé");
        Order order1=new Order(id,user,new OrderState("prête à livré","prête à livré"),orderItemList,0L,"15-12-2020");
        Order order2=new Order(id,user,payed,orderItemList,0L,"15-12-2020");
        given(orderPort.getOrder(id)).willReturn(order1);
        given(orderPort.updateStateOrder(id,payed)).willReturn(order2);
        given(orderStatePort.getOrderState("payé")).willReturn(payed);
        Order result=orderService.payerOrder(id);
        assertEquals(result,order2);
    }
    @DisplayName("should prepare order if order is commandé")
    @Test
    void function1() throws IllegalAccessException {
        OrderState ordered1=new OrderState("en préparation","en préparation");
        Order order1=new Order(id,user,new OrderState("commandé","commandé"),orderItemList,0L,"15-12-2020");
        Order order2=new Order(id,user,ordered1,orderItemList,0L,"15-12-2020");
        given(orderPort.getOrder(id)).willReturn(order1);
        given(orderPort.updateStateOrder(id,ordered1)).willReturn(order2);
        given(orderStatePort.getOrderState("en préparation")).willReturn(ordered1);
        Order result=orderService.preparerOrder(id);
        assertEquals(result,order2);
    }
    @DisplayName("should end prepare order if order is en preparartion")
    @Test
    void function2() throws IllegalAccessException {
        OrderState ordered1=new OrderState("prête à livré","prête à livré");
        Order order1=new Order(id,user,new OrderState("en préparation","en préparation"),orderItemList,0L,"15-12-2020");
        Order order2=new Order(id,user,ordered1,orderItemList,0L,"15-12-2020");
        given(orderPort.getOrder(id)).willReturn(order1);
        given(orderPort.updateStateOrder(id,ordered1)).willReturn(order2);
        given(orderStatePort.getOrderState("prête à livré")).willReturn(ordered1);
        Order result=orderService.readyForDeliveryOrder(id);
        assertEquals(result,order2);
    }
    @DisplayName("should throw illegalAccessException if order is not en preparation and wanna end prepare")
    @Test
    void function3(){
        OrderState ordered1=new OrderState("prête à livré","prête à livré");

        Order order1=new Order(id,user,ordered1,orderItemList,0L,"15-12-2020");
        given(orderPort.getOrder(id)).willReturn(order1);
        assertThrows(IllegalAccessException.class,()->orderService.readyForDeliveryOrder(id));
    }
    @DisplayName("should throw illegalAccessException if order is not command and wanna prepare it")
    @Test
    void function4(){
        OrderState ordered1=new OrderState("prête à livré","prête à livré");

        Order order1=new Order(id,user,ordered1,orderItemList,0L,"15-12-2020");
        given(orderPort.getOrder(id)).willReturn(order1);
        assertThrows(IllegalAccessException.class,()->orderService.preparerOrder(id));
    }
    @DisplayName("should throw illegalAccessException if order is not prête à livré and wanna pay it")
    @Test
    void function5(){
        OrderState ordered1=new OrderState("commandé","commandé");

        Order order1=new Order(id,user,ordered1,orderItemList,0L,"15-12-2020");
        given(orderPort.getOrder(id)).willReturn(order1);
        assertThrows(IllegalAccessException.class,()->orderService.payerOrder(id));
    }
}
