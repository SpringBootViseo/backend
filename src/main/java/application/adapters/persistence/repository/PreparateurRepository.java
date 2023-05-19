package application.adapters.persistence.repository;

import application.adapters.persistence.entity.PreparateurEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreparateurRepository extends MongoRepository<PreparateurEntity,String> {
}
