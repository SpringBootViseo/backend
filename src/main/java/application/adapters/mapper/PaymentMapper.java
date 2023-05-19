package application.adapters.mapper;

import application.adapters.persistence.entity.PaymentEntity;
import application.adapters.web.presenter.PaymentDTO;
import application.domain.Payment;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.List;

public interface PaymentMapper {
    Payment paymentEntityToPayment(PaymentEntity paymentEntity);
    PaymentEntity paymentToPaymentEntity(Payment payment);
    Payment paymentDTOToPayment(PaymentDTO paymentDTO);
    PaymentDTO paymentToPaymentDTO(Payment payment);
    List<Payment> listPaymentEntityToListPayment(List<PaymentEntity> paymentEntities);
    List<PaymentEntity> listPaymentToListPaymentEntity(List<Payment> payments);
    List<Payment> listPaymentDTOToListPayment(List<PaymentDTO> paymentDTOS);
    List<PaymentDTO> listPaymentToListPaymentDTO(List<Payment> payments);
    List<Payment> documentPaymentTolistPayment(MongoCollection<Document> collection);

}
