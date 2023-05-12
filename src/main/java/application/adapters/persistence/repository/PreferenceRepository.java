package application.adapters.persistence.repository;

import application.adapters.persistence.entity.PreferenceEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface PreferenceRepository extends MongoRepository<PreferenceEntity, String> {
}
