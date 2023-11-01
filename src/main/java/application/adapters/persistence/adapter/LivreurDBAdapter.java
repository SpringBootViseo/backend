package application.adapters.persistence.adapter;

import application.adapters.exception.UserAlreadyExistsException;
import application.adapters.mapper.mapperImpl.LivreurMapperImpl;
import application.adapters.persistence.entity.LivreurEntity;
import application.adapters.persistence.repository.LivreurRepository;
import application.domain.Livreur;
import application.port.out.LivreurPort;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@AllArgsConstructor
public class LivreurDBAdapter implements LivreurPort {
    private LivreurRepository livreurRepository;
    private LivreurMapperImpl livreurMapper;
    private static final Logger logger = LogManager.getLogger(LivreurDBAdapter.class);

    @Override
    public Livreur addLivreur(Livreur livreur) {
        logger.info("Adding Livreur with email: {}", livreur.getEmail());
        if (this.isLivreur(livreur.getEmail())) {
            logger.error("Livreur with email {} already exists", livreur.getEmail());
            throw new UserAlreadyExistsException("Livreur already exists!");
        }
        LivreurEntity livreurEntity = livreurMapper.livreurToLivreurEntity(livreur);
        return livreurMapper.livreurEntityToLivreur(livreurRepository.save(livreurEntity));
    }

    @Override
    public Livreur getLivreur(String email) {
        logger.info("Fetching Livreur with email: {}", email);
        if (livreurRepository.findById(email).isPresent()) {
            return livreurMapper.livreurEntityToLivreur(livreurRepository.findById(email).get());
        } else {
            logger.error("Livreur with email {} not found", email);
            throw new NoSuchElementException("Livreur doesn't exist");
        }
    }

    @Override
    public Boolean isLivreur(String email) {
        logger.info("Checking if Livreur with email {} exists", email);
        return livreurRepository.findById(email).isPresent();
    }
}