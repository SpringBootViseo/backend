package application.adapters.persistence.inventory;

import application.adapters.persistence.inventory.entity.ManufacturerEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ManufacturerRepository extends MongoRepository<ManufacturerEntity, String> {

}
