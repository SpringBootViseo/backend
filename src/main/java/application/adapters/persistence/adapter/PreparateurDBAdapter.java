package application.adapters.persistence.adapter;

import application.adapters.exception.UserAlreadyExistsException;
import application.adapters.mapper.mapperImpl.PreparateurMapperImpl;
import application.adapters.persistence.entity.PreparateurEntity;
import application.adapters.persistence.repository.PreparateurRepository;
import application.domain.Preparateur;
import application.port.out.PreparateurPort;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@AllArgsConstructor
public class PreparateurDBAdapter implements PreparateurPort {
    private PreparateurRepository preparateurRepository;
    private PreparateurMapperImpl preparateurMapper;
    private static final Logger logger = LoggerFactory.getLogger(PreparateurDBAdapter.class);
    @Override
    public Preparateur addPreparateur(Preparateur preparateur) {
        logger.trace("Checking if prepateur with email : {} exist",preparateur.getEmail());
        if(this.isPreparateur(preparateur.getEmail())){
            logger.error("Preparateur with Email : {} already exist!",preparateur.getEmail());
            throw  new UserAlreadyExistsException("Preparateur already exist!");

        }
        PreparateurEntity preparateurEntity=preparateurMapper.preparateurToPreparateurEntity(preparateur);
        logger.debug("Adding preparateur with email: {}", preparateur.getEmail());
        PreparateurEntity savedPreparateurEntity = preparateurRepository.save(preparateurEntity);
        logger.info("Added preparateur with email: {}", preparateur.getEmail());

        return preparateurMapper.preparateurEntityToPreparateur(savedPreparateurEntity);
    }


    @Override
    public Preparateur getPreparateur(String email) {
        logger.trace("Checking if preparateur with email: {} exists", email);

        if (preparateurRepository.findById(email).isPresent()) {
            PreparateurEntity preparateurEntity = preparateurRepository.findById(email).get();
            logger.info("Retrieved preparateur with email: {}", email);
            return preparateurMapper.preparateurEntityToPreparateur(preparateurEntity);
        } else {
            logger.error("Preparateur not found with email: {}", email);
            throw new NoSuchElementException("Preparateur doesn't exist");
        }
    }

    @Override
    public Boolean isPreparateur(String email) {
        logger.trace("Checking if preparateur with email: {} exists", email);
        boolean exists = preparateurRepository.findById(email).isPresent();

        if (exists) {
            logger.info("Preparateur with email: {} exists", email);
        } else {
            logger.warn("Preparateur with email: {} does not exist", email);
        }
        return exists;
    }
}
