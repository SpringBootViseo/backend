package application.port;

import application.adapters.exception.OrderStateAlreadyExistsException;
import application.domain.OrderState;
import application.port.out.OrderStatePort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

class OrderStateServiceTest {
    @InjectMocks
    private OrderStateService orderStateService;
    @Mock
    private OrderStatePort orderStatePort;
    private OrderState orderState;

    @BeforeEach
    void setUp() {
        orderState = new OrderState("en_cours","en cours de preparation");
        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
    void tearDown() {
        orderState=null;
    }

    @Test
    void shouldGetOrderStateWhenGetOrderWithValidId() {
        given(orderStatePort.getOrderState("en_cours")).willReturn(orderState);
        assertEquals(orderStateService.getOrderState("en_cours"),orderState);
    }
    @Test
    void shouldThrowExceptionWhenGetOrderWithInvalidId() {
        given(orderStatePort.getOrderState("en_cours")).willThrow(NoSuchElementException.class);
        assertThrows(NoSuchElementException.class,()->orderStateService.getOrderState("en_cours"));
    }

    @Test
    void shouldCallOnceDeleteOrderStateWhenDeleteOrderStateWithValidId() {
        orderStatePort.deleteOrderState("en_cours");
        verify(orderStatePort,times(1)).deleteOrderState("en_cours");

    }

    @Test
    void shouldThrowExceptionWhenDeleteOrderStateWithInvalidId() {

       willThrow(NoSuchElementException.class).given(orderStatePort).deleteOrderState("en_cours");
        assertThrows(NoSuchElementException.class,()->orderStateService.deleteOrderState("en_cours"));

    }
    @Test
    void shouldCreateOrderStateWhenCreteOrderStateWithUnAvailableOrderState() {
        given(orderStatePort.createOrderState(orderState)).willReturn(orderState);
        assertEquals(orderState,orderStateService.createOrderState(orderState));
    }
    @Test
    void shouldThrowExceptionWhenCrateOrderStateWithAvailableOrderState(){
        willThrow(OrderStateAlreadyExistsException.class).given(orderStatePort).createOrderState(orderState);
        assertThrows(OrderStateAlreadyExistsException.class,()->orderStateService.createOrderState(orderState));
    }
}