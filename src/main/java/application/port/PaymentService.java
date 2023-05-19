package application.port;

import application.domain.Payment;
import application.port.in.PaymentUseCase;
import application.port.out.PaymentPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@AllArgsConstructor
@Service
public class PaymentService implements PaymentUseCase {
    private final PaymentPort paymentPort;
    @Override
    public Payment savePayment(Payment payment) throws InvalidPropertiesFormatException {
        if(paymentPort.isPayment(payment.getId())){
            throw new InvalidPropertiesFormatException("Payment with id Already exist!");
        }
        else return paymentPort.savePayment(payment);
    }

    @Override
    public Payment getPayement(UUID id) {
        if(paymentPort.isPayment(id))
        return paymentPort.getPayement(id);
        else throw new NoSuchElementException("Payment doesn't exist!");
    }

    @Override
    public List<Payment> listPayment() {
        return paymentPort.listPayment();
    }

    @Override
    public List<Payment> listPayment(String idUser) {
        List<Payment> payments=paymentPort.listPayment();
        List<Payment> paymentsHasUserNotNull=payments.stream().filter(
                payment -> payment.getUser()!=null
        ).toList();
        List<Payment> result=paymentsHasUserNotNull.stream().filter(
                payment -> payment.getUser().getId().equals(idUser)
        ).toList();
        return result;
    }

    @Override
    public List<Payment> listPaymentMontantSuperieuAMontant(long montant) {
        List<Payment> payments=paymentPort.listPayment();
        List<Payment> paymentsHasTotalNotNull=payments.stream().filter(
                payment -> payment.getTotalPrice()!=0
        ).toList();
        List<Payment> result=paymentsHasTotalNotNull.stream().filter(
                payment -> payment.getTotalPrice()>montant
        ).toList();
        return result;
    }

    @Override
    public List<Payment> listPaymentMontantInferieurAMontant(long montant) {
        List<Payment> payments=paymentPort.listPayment();
        List<Payment> paymentsHasTotalNotNull=payments.stream().filter(
                payment -> payment.getTotalPrice()!=0
        ).toList();
        List<Payment> result=paymentsHasTotalNotNull.stream().filter(
                payment -> payment.getTotalPrice()<montant
        ).toList();
        return result;
    }

    @Override
    public Boolean isPayment(UUID id) {
        return paymentPort.isPayment(id);
    }
}
