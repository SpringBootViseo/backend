package application.adapters.persistence.repository;

import application.adapters.persistence.entity.OrderStateEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStateRepository extends MongoRepository<OrderStateEntity,String> {
}
