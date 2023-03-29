package application.adapters.persistence.repository;

import application.adapters.persistence.entity.ManufacturerEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManufacturerRepository extends MongoRepository<ManufacturerEntity, String> {

}
