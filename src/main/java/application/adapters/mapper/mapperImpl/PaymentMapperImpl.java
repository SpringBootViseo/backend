package application.adapters.mapper.mapperImpl;

import application.adapters.mapper.PaymentMapper;
import application.adapters.persistence.entity.LivreurEntity;
import application.adapters.persistence.entity.PaymentEntity;
import application.adapters.persistence.entity.UserEntity;
import application.adapters.web.presenter.LivreurDTO;
import application.adapters.web.presenter.PaymentDTO;
import application.adapters.web.presenter.UserDTO;
import application.domain.Address;
import application.domain.Livreur;
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
    private LivreurMapperImpl livreurMapper;

    @Override
    public Payment paymentEntityToPayment(PaymentEntity paymentEntity) {
        User user=userMapper.userEntityToUser(paymentEntity.getUser());
        Livreur livreur=livreurMapper.livreurEntityToLivreur(paymentEntity.getLivreur());
        return new Payment(paymentEntity.getId(),paymentEntity.getTotalPrice(),user,livreur);
    }

    @Override
    public PaymentEntity paymentToPaymentEntity(Payment payment) {
        UserEntity user=userMapper.userToUserEntity(payment.getUser());
        LivreurEntity livreurEntity=livreurMapper.livreurToLivreurEntity(payment.getLivreur());
        return new PaymentEntity(payment.getId(), payment.getTotalPrice(), user,livreurEntity);
    }

    @Override
    public Payment paymentDTOToPayment(PaymentDTO paymentDTO) {
        User user=userMapper.userDtoToUser(paymentDTO.getUser());
        Livreur livreur=livreurMapper.livreurDTOToLivreur(paymentDTO.getLivreur());
        return new Payment(paymentDTO.getId(), paymentDTO.getTotalPrice(), user,livreur);
    }

    @Override
    public PaymentDTO paymentToPaymentDTO(Payment payment) {
        UserDTO userDTO=userMapper.userToUserDTO(payment.getUser());
        LivreurDTO livreurDTO=livreurMapper.livreurToLivreurDTO(payment.getLivreur());
        return new PaymentDTO(payment.getId(), payment.getTotalPrice(), userDTO,livreurDTO);
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
            Document livreurDocument=doc.get("livreur",Document.class);
            Livreur livreur=new Livreur(livreurDocument.getString("firstname"),livreurDocument.getString("lastname"),livreurDocument.getString("_id"));
            List<Document> listAddressDocument=userDocument.getList("address",Document.class);
            List<Address> addresses=new ArrayList<>();
            for (Document addressDocument:listAddressDocument
            ) {
                addresses.add(new Address(addressDocument.get("_id", UUID.class),addressDocument.getString("street"),addressDocument.getString("city"),addressDocument.getString("state")));
            }
            User user=new User(userDocument.getString("_id"),userDocument.getString("fullname"),userDocument.getString("email"),userDocument.getString("numberPhone"),userDocument.getString("images"),addresses,userDocument.getInteger("avertissement"),userDocument.getBoolean("blackListed"));
            payments.add(new Payment(doc.get("_id", UUID.class),doc.getLong("totalPrice"),user,livreur));

        }
        return payments;
    }
}
