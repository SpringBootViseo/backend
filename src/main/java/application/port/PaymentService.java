package application.port;

import application.domain.Payment;
import application.port.in.PaymentUseCase;
import application.port.out.PaymentPort;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@AllArgsConstructor
@Service
public class PaymentService implements PaymentUseCase {
    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);
    private final PaymentPort paymentPort;

    @Override
    public Payment savePayment(Payment payment) throws InvalidPropertiesFormatException {
        logger.info("Saving payment: {}", payment);

        if (paymentPort.isPayment(payment.getId())) {
            logger.error("Payment with ID {} already exists", payment.getId());
            throw new InvalidPropertiesFormatException("Payment with id already exists");
        } else {
            return paymentPort.savePayment(payment);
        }
    }

    @Override
    public Payment getPayement(UUID id) {
        logger.info("Getting payment with ID: {}", id);

        if (paymentPort.isPayment(id)) {
            return paymentPort.getPayement(id);
        } else {
            logger.error("Payment with ID {} doesn't exist", id);
            throw new NoSuchElementException("Payment doesn't exist");
        }
    }

    @Override
    public List<Payment> listPayment() {
        logger.info("Listing all payments");
        return paymentPort.listPayment();
    }

    @Override
    public List<Payment> listPayment(String idUser) {
        logger.info("Listing payments for user with ID: {}", idUser);

        List<Payment> payments = paymentPort.listPayment();
        List<Payment> paymentsHasUserNotNull = payments.stream()
                .filter(payment -> payment.getUser() != null)
                .toList();

        List<Payment> result = paymentsHasUserNotNull.stream()
                .filter(payment -> payment.getUser().getId().equals(idUser))
                .toList();

        return result;
    }




    @Override
    public List<Payment> listPaymentMontantSuperieuAMontant(long montant) {
        logger.info("Listing payments with a total price greater than: {}", montant);
        List<Payment> payments = paymentPort.listPayment();
        List<Payment> paymentsHasTotalNotNull = payments.stream()
                .filter(payment -> payment.getTotalPrice() != 0)
                .toList();

        List<Payment> result = paymentsHasTotalNotNull.stream()
                .filter(payment -> payment.getTotalPrice() > montant)
                .toList();

        return result;
    }

    @Override
    public List<Payment> listPaymentMontantInferieurAMontant(long montant) {
        logger.info("Listing payments with a total price less than: {}", montant);

        List<Payment> payments = paymentPort.listPayment();
        List<Payment> paymentsHasTotalNotNull = payments.stream()
                .filter(payment -> payment.getTotalPrice() != 0)
                .toList();

        List<Payment> result = paymentsHasTotalNotNull.stream()
                .filter(payment -> payment.getTotalPrice() < montant)
                .toList();

        return result;
    }

    @Override
    public Boolean isPayment(UUID id) {
        logger.debug("Checking if payment with ID {} exists", id);
        return paymentPort.isPayment(id);
    }
}
