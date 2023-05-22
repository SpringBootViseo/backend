package application.adapters.persistence.repository;

import application.adapters.persistence.entity.LivreurEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivreurRepository extends MongoRepository<LivreurEntity,String> {
}
