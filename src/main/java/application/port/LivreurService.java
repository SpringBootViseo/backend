package application.port;

import application.adapters.exception.UserAlreadyExistsException;
import application.domain.Livreur;
import application.port.in.LivreurUseCase;
import application.port.out.LivreurPort;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@AllArgsConstructor
@Service
public class LivreurService implements LivreurUseCase {
    private final LivreurPort livreurPort;
    private static final Logger logger = LoggerFactory.getLogger(LivreurService.class);

    @Override
    public Livreur addLivreur(Livreur livreur) {
        if (livreurPort.isLivreur(livreur.getEmail())) {
            logger.error("Livreur with email {} already exists", livreur.getEmail());
            throw new UserAlreadyExistsException("Livreur Already exists!");
        }
        logger.info("Adding Livreur: {}", livreur);
        return livreurPort.addLivreur(livreur);
    }

    @Override
    public Livreur getLivreur(String email) {
        if (livreurPort.isLivreur(email)) {
            logger.info("Getting Livreur with email: {}", email);
            return livreurPort.getLivreur(email);
        } else {
            logger.warn("Livreur with email {} doesn't exist", email);
            throw new NoSuchElementException("Livreur with such email doesn't exist!");
        }
    }

    @Override
    public Boolean isLivreur(String email) {
        logger.debug("Checking if Livreur with email {} exists", email);
        return livreurPort.isLivreur(email);
    }
}
