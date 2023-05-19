package application.port;

import application.domain.Address;
import application.domain.Payment;
import application.domain.User;
import application.port.out.PaymentPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

class PaymentServiceTest {
    @InjectMocks
    PaymentService paymentService;
    @Mock
    PaymentPort paymentPort;
    UUID id;
    Payment payment;
    User user;
    Address address;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        id=UUID.randomUUID();
        address=new Address(id,"test","test","test");
        user=new User("test","test","test","test","test",List.of(address),0,false);
        payment=new Payment(id,10,user);
    }
    @AfterEach
    void tearDown(){
        id=null;
        address=null;
        user=null;
        payment=null;
    }
    @DisplayName("should save Payment when savePayment() if payment doesn't exist with such id")
    @Test
    void function() throws InvalidPropertiesFormatException {
        given(paymentPort.isPayment(id)).willReturn(false);
        given(paymentPort.savePayment(payment)).willReturn(payment);
        Payment result=paymentService.savePayment(payment);
        assertEquals(payment,result);
    }
    @DisplayName("should throw InvalidPropertiesFormatException when savePayement() if payment exist with such id")
    @Test
    void function1(){
        given(paymentPort.isPayment(id)).willReturn(true);
        assertThrows(InvalidPropertiesFormatException.class,()->paymentService.savePayment(payment));

    }
    @DisplayName("should return payment when id exist on DB")
    @Test
    void function2(){
        given(paymentPort.isPayment(id)).willReturn(true);
        given(paymentPort.getPayement(id)).willReturn(payment);
        Payment result=paymentService.getPayement(id);
        assertEquals(result,payment);
    }
    @DisplayName("should throw NoSuchElementException when id doesn't exist on DB")
    @Test
    void function3(){
        given(paymentPort.isPayment(id)).willReturn(false);
        assertThrows(NoSuchElementException.class,()->paymentService.getPayement(id));

    }
    @DisplayName("should return list of Payments when listPayment()")
    @Test
    void function4(){
        given(paymentPort.listPayment()).willReturn(List.of(payment));
        List<Payment> payments=paymentService.listPayment();
        assertEquals(List.of(payment),payments);
    }
    @DisplayName("should return emptyList of Payment when none Payment is in DB")
    @Test
    void function8(){
        given(paymentPort.listPayment()).willReturn(Collections.emptyList());
        List<Payment> payments=paymentService.listPayment();
        assertEquals(Collections.emptyList(),payments);
    }
    @DisplayName("should return emptyList Of Payment when non Payment is in DB and listPayment(idUser)")
    @Test
    void function9(){
        given(paymentPort.listPayment()).willReturn(Collections.emptyList());
        List<Payment> payments=paymentService.listPayment("test");
        assertEquals(Collections.emptyList(),payments);
    }
    @DisplayName("should return emptyList Of Payment when non Payment is in DB and listPaymentMontantInferieruAMontant(idUser)")
    @Test
    void function10(){
        given(paymentPort.listPayment()).willReturn(Collections.emptyList());
        List<Payment> payments=paymentService.listPaymentMontantInferieurAMontant(100);
        assertEquals(Collections.emptyList(),payments);
    }
    @DisplayName("should return emptyList Of Payment when non Payment is in DB and listPaymentMontantSuperieurAMontant(idUser)")
    @Test
    void function11(){
        given(paymentPort.listPayment()).willReturn(Collections.emptyList());
        List<Payment> payments=paymentService.listPaymentMontantSuperieuAMontant(100);
        assertEquals(Collections.emptyList(),payments);
    }
    @DisplayName("should return list of Payments of a specific User when listPayment(idUser)")
    @Test
    void function5(){
        UUID uuid=UUID.randomUUID();
        UUID uuid1=UUID.randomUUID();
        User user1=new User("test1","test","test","test","test",List.of(address),0,false);
        Payment payment1=new Payment(uuid,123,user1);
        Payment payment2=new Payment(uuid1,123,user1);
        List<Payment> payments=List.of(payment,payment1,payment2);
        List<Payment> payments1=List.of(payment1,payment2);
        given(paymentPort.listPayment()).willReturn(payments);
        List<Payment> result=paymentService.listPayment("test1");
        assertEquals(result,payments1);
    }
    @DisplayName("should return list of Payments have totalPrice more than specific price when listPaymentMontantSuperieuAMontant(montant)")
    @Test
    void function6(){
        UUID uuid=UUID.randomUUID();
        UUID uuid1=UUID.randomUUID();
        User user1=new User("test1","test","test","test","test",List.of(address),0,false);
        Payment payment1=new Payment(uuid,123,user1);
        Payment payment2=new Payment(uuid1,123,user1);
        List<Payment> payments=List.of(payment,payment1,payment2);
        List<Payment> payments1=List.of(payment1,payment2);
        given(paymentPort.listPayment()).willReturn(payments);
        List<Payment> result=paymentService.listPaymentMontantSuperieuAMontant(50);
        assertEquals(result,payments1);

    }
    @DisplayName("should return list of Payments have totalPrice less than specific price when listPaymentMontantInferieurAMontant(montant)")
    @Test
    void function7(){
        UUID uuid=UUID.randomUUID();
        UUID uuid1=UUID.randomUUID();
        User user1=new User("test1","test","test","test","test",List.of(address),0,false);
        Payment payment1=new Payment(uuid,9,user1);
        Payment payment2=new Payment(uuid1,9,user1);
        List<Payment> payments=List.of(payment,payment1,payment2);
        List<Payment> payments1=List.of(payment1,payment2);
        given(paymentPort.listPayment()).willReturn(payments);
        List<Payment> result=paymentService.listPaymentMontantInferieurAMontant(10);
        assertEquals(result,payments1);

    }
    @DisplayName("should return True when Payment id is in DB isPayment()")
    @Test
    void function12(){
        given(paymentPort.isPayment(id)).willReturn(true);
        assertTrue(paymentService.isPayment(id));
    }
    @DisplayName("should return False when Payment id isn't in DB isPayment()")
    @Test
    void function13(){
        given(paymentPort.isPayment(id)).willReturn(false);
        assertFalse(paymentService.isPayment(id));
    }
}