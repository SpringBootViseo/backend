package application.adapters.persistence.inventory;

import application.adapters.persistence.inventory.entity.ProductEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends MongoRepository<ProductEntity, UUID> {
}
