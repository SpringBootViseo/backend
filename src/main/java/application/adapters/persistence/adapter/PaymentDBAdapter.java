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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Logger logger = LogManager.getLogger(PaymentDBAdapter.class);

    @Override
    public Payment savePayment(Payment payment) {
        logger.debug("Saving payment with ID: {}", payment.getId());
        if (paymentRepository.findById(payment.getId()).isPresent()) {
            logger.error("Payment with ID {} already exists. Skipping save.", payment.getId());
            throw new UserAlreadyExistsException("Payment Already Exist!");
        }
        PaymentEntity paymentEntity = paymentMapper.paymentToPaymentEntity(payment);
        logger.info("Payment with ID {} has been successfully saved.", paymentEntity.getId());

        return paymentMapper.paymentEntityToPayment(paymentRepository.save(paymentEntity));
    }

    @Override
    public Payment getPayement(UUID id) {
        logger.info("Fetching payment with ID: {}", id);
        if (paymentRepository.findById(id).isPresent()) {
            logger.info("Payment with ID {} has been successfully fetched", id);
            return paymentMapper.paymentEntityToPayment(paymentRepository.findById(id).get());
        } else {
            logger.error("Payment with ID {} not found", id);
            throw new NoSuchElementException("Payment Doesn't exist!");
        }
    }

    @Override
    public List<Payment> listPayment() {
        logger.info("Listing all payments");
        MongoCollection<Document> collection = mongoConfig.getAllDocuments("Payments");
        List<Payment> payments = paymentMapper.documentPaymentTolistPayment(collection);
        logger.debug("Found {} payments", payments.size());
        return paymentMapper.documentPaymentTolistPayment(collection);
    }

    @Override
    public Boolean isPayment(UUID id) {
        logger.debug("Checking if payment with ID {} exists", id);
        return paymentRepository.findById(id).isPresent();
    }

}