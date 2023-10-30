package application.port;

import application.domain.Payment;
import application.port.in.PaymentUseCase;
import application.port.out.PaymentPort;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@AllArgsConstructor
@Service
public class PaymentService implements PaymentUseCase {
    private final PaymentPort paymentPort;
    private final static Logger logger= LogManager.getLogger(PaymentService.class);
    @Override
    public Payment savePayment(Payment payment) throws InvalidPropertiesFormatException {
        logger.debug("check if payment with id "+payment.getId()+"exist");
        if(paymentPort.isPayment(payment.getId())){
            logger.error("Payment with id"+payment.getId()+" Already exist!");
            throw new InvalidPropertiesFormatException("Payment with id Already exist!");
        }
        else {
            logger.info("Save payment "+payment.toString());
            return paymentPort.savePayment(payment);
        }
    }

    @Override
    public Payment getPayement(UUID id) {
        if(paymentPort.isPayment(id)){
            logger.info("Retrieve Payement with id"+id);
            return paymentPort.getPayement(id);
        }

        else{
            logger.error("Payment doesn't exist with such id "+id);
            throw new NoSuchElementException("Payment doesn't exist!");
        }
    }

    @Override
    public List<Payment> listPayment() {
        logger.info("Get all Payements ");

        return paymentPort.listPayment();
    }

    @Override
    public List<Payment> listPayment(String idUser) {
        logger.debug("Get All Payments");
        List<Payment> payments=paymentPort.listPayment();
        logger.debug("omit the payments with null user");
        List<Payment> paymentsHasUserNotNull=payments.stream().filter(
                payment -> payment.getUser()!=null
        ).toList();
        logger.info("Retrieve all payment done by user with id "+idUser);
        List<Payment> result=paymentsHasUserNotNull.stream().filter(
                payment -> payment.getUser().getId().equals(idUser)
        ).toList();
        return result;
    }

    @Override
    public List<Payment> listPaymentMontantSuperieuAMontant(long montant) {
        logger.debug("Get All Payments");

        List<Payment> payments=paymentPort.listPayment();
        logger.debug("omit the payments with 0 in total");

        List<Payment> paymentsHasTotalNotNull=payments.stream().filter(
                payment -> payment.getTotalPrice()!=0
        ).toList();
        logger.info("Retrieve all payment with total superior than "+montant);
        List<Payment> result=paymentsHasTotalNotNull.stream().filter(
                payment -> payment.getTotalPrice()>montant
        ).toList();
        return result;
    }

    @Override
    public List<Payment> listPaymentMontantInferieurAMontant(long montant) {
        logger.debug("Get All Payments");
        List<Payment> payments=paymentPort.listPayment();
        logger.debug("omit the payments with 0 in total");

        List<Payment> paymentsHasTotalNotNull=payments.stream().filter(
                payment -> payment.getTotalPrice()!=0
        ).toList();
        logger.info("Retrieve all payment with total inferior than "+montant);

        List<Payment> result=paymentsHasTotalNotNull.stream().filter(
                payment -> payment.getTotalPrice()<montant
        ).toList();
        return result;
    }

    @Override
    public Boolean isPayment(UUID id) {
        logger.debug("Retrieve payment with id "+id);
        return paymentPort.isPayment(id);
    }
}
