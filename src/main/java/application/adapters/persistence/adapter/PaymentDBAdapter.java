package application.adapters.persistence.adapter;

import application.adapters.exception.UserAlreadyExistsException;
import application.adapters.mapper.mapperImpl.PaymentMapperImpl;
import application.adapters.persistence.MongoConfig;
import application.adapters.persistence.entity.PaymentEntity;
import application.adapters.persistence.repository.PaymentRepository;
import application.domain.Payment;
import application.port.out.PaymentPort;
import com.mongodb.client.MongoCollection;
import lombok.AllArgsConstructor;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Component
@AllArgsConstructor
public class PaymentDBAdapter implements PaymentPort {
    private final PaymentRepository paymentRepository;
    private final PaymentMapperImpl paymentMapper;
    private final MongoConfig mongoConfig;
    @Override
    public Payment savePayment(Payment payment) {
        if(paymentRepository.findById(payment.getId()).isPresent()){
            throw new UserAlreadyExistsException("Payment Already Exist!");
        }
        PaymentEntity paymentEntity=paymentMapper.paymentToPaymentEntity(payment);
        return paymentMapper.paymentEntityToPayment(paymentRepository.save(paymentEntity));
    }

    @Override
    public Payment getPayement(UUID id) {
        if(paymentRepository.findById(id).isPresent()){
            return paymentMapper.paymentEntityToPayment(paymentRepository.findById(id).get());
        }
        else throw new NoSuchElementException("Payment Doesn't exist!");
    }

    @Override
    public List<Payment> listPayment() {
        MongoCollection<Document> collection=mongoConfig.getAllDocuments("Payments");

        return paymentMapper.documentPaymentTolistPayment(collection);
    }

    @Override
    public Boolean isPayment(UUID id) {
        return paymentRepository.findById(id).isPresent();
    }
}
