package application.port.in;

import application.domain.Payment;

import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.UUID;

public interface PaymentUseCase {
    Payment savePayment(Payment payment) throws InvalidPropertiesFormatException;
    Payment getPayement(UUID id);
    List<Payment> listPayment();
    List<Payment> listPayment(String idUser);
    List<Payment> listPaymentMontantSuperieuAMontant(long montant);
    List<Payment> listPaymentMontantInferieurAMontant(long montant);
    Boolean isPayment(UUID id);
}
