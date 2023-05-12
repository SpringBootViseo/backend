package application.adapters.persistence.repository;

import application.adapters.persistence.entity.UserInfoEntity;
import io.swagger.models.auth.In;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserInfoRepository extends MongoRepository<UserInfoEntity, Integer> {
    Optional<UserInfoEntity> findByEmail(String email);
}