package application.adapters.persistence.repository;

import application.adapters.persistence.entity.PaymentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PaymentRepository extends MongoRepository<PaymentEntity, UUID> {
}
