package application.adapters.persistence.repository;

import application.adapters.persistence.entity.CategoryEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface CategoryRepository extends MongoRepository<CategoryEntity, UUID> {

}
