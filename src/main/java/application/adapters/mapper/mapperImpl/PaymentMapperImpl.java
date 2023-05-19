package application.adapters.mapper.mapperImpl;

import application.adapters.mapper.PaymentMapper;
import application.adapters.persistence.entity.PaymentEntity;
import application.adapters.persistence.entity.UserEntity;
import application.adapters.web.presenter.PaymentDTO;
import application.adapters.web.presenter.UserDTO;
import application.domain.Address;
import application.domain.Payment;
import application.domain.User;
import com.mongodb.client.MongoCollection;
import lombok.AllArgsConstructor;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
public class PaymentMapperImpl implements PaymentMapper {
    private UserMapperImpl userMapper;

    @Override
    public Payment paymentEntityToPayment(PaymentEntity paymentEntity) {
        User user=userMapper.userEntityToUser(paymentEntity.getUser());
        return new Payment(paymentEntity.getId(),paymentEntity.getTotalPrice(),user);
    }

    @Override
    public PaymentEntity paymentToPaymentEntity(Payment payment) {
        UserEntity user=userMapper.userToUserEntity(payment.getUser());
        return new PaymentEntity(payment.getId(), payment.getTotalPrice(), user);
    }

    @Override
    public Payment paymentDTOToPayment(PaymentDTO paymentDTO) {
        User user=userMapper.userDtoToUser(paymentDTO.getUser());

        return new Payment(paymentDTO.getId(), paymentDTO.getTotalPrice(), user);
    }

    @Override
    public PaymentDTO paymentToPaymentDTO(Payment payment) {
        UserDTO userDTO=userMapper.userToUserDTO(payment.getUser());
        return new PaymentDTO(payment.getId(), payment.getTotalPrice(), userDTO);
    }

    @Override
    public List<Payment> listPaymentEntityToListPayment(List<PaymentEntity> paymentEntities) {
        List<Payment> payments=new ArrayList<>();
        for (PaymentEntity paymentEntity:paymentEntities
             ) {
            payments.add(this.paymentEntityToPayment(paymentEntity));
        }
        return payments;
    }

    @Override
    public List<PaymentEntity> listPaymentToListPaymentEntity(List<Payment> payments) {
        List<PaymentEntity> paymentEntities=new ArrayList<>();
        for (Payment payment: payments
             ) {
            paymentEntities.add(this.paymentToPaymentEntity(payment));

        }
        return paymentEntities;
    }

    @Override
    public List<Payment> listPaymentDTOToListPayment(List<PaymentDTO> paymentDTOS) {

        List<Payment> payments=new ArrayList<>();
        for (PaymentDTO paymentDTO:paymentDTOS
        ) {
            payments.add(this.paymentDTOToPayment(paymentDTO));
        }
        return payments;
    }

    @Override
    public List<PaymentDTO> listPaymentToListPaymentDTO(List<Payment> payments) {
        List<PaymentDTO> paymentDTOS=new ArrayList<>();
        for (Payment payment: payments
             ) {
            paymentDTOS.add(this.paymentToPaymentDTO(payment));

        }
        return paymentDTOS;
    }

    @Override
    public List<Payment> documentPaymentTolistPayment(MongoCollection<Document> collection) {
        List<Payment> payments=new ArrayList<>();
        for(Document doc: collection.find()){
            Document userDocument= doc.get("user", Document.class);
            User user=new User(userDocument.getString("_id"),userDocument.getString("fullname"),userDocument.getString("email"),userDocument.getString("numberPhone"),userDocument.getString("images"),userDocument.getList("address", Address.class),0,false);
            payments.add(new Payment(doc.get("_id", UUID.class),doc.getLong("totalPrice"),user));

        }
        return payments;
    }
}
