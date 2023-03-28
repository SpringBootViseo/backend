package application.adapters.persistence.inventory;

import application.adapters.persistence.inventory.entity.CategoryEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface CategoryRepository extends MongoRepository<CategoryEntity, UUID> {

}
