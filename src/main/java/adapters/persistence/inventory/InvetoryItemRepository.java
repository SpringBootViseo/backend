package adapters.persistence.inventory;

import adapters.persistence.inventory.entity.InventoryItemEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvetoryItemRepository extends MongoRepository<InventoryItemEntity, String> {

}
