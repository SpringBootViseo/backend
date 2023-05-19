package application.port.out;

import application.domain.Payment;

import java.util.List;
import java.util.UUID;

public interface PaymentPort {
    Payment savePayment(Payment payment);
    Payment getPayement(UUID id);
    List<Payment> listPayment();
    Boolean isPayment(UUID id);

}
