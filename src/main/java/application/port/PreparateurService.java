package application.port;

import application.adapters.exception.UserAlreadyExistsException;
import application.domain.Preparateur;
import application.port.in.PreparateurUseCase;
import application.port.out.PreparateurPort;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@AllArgsConstructor
@Service
public class PreparateurService implements PreparateurUseCase {
    private final PreparateurPort preparateurPort;
    private static final Logger logger = LoggerFactory.getLogger(PreparateurService.class);

    @Override
    public Preparateur addPreparateur(Preparateur preparateur) {
        // INFO level for a significant operation
        logger.info("Adding preparateur: {}", preparateur);

        if (preparateurPort.isPreparateur(preparateur.getEmail())) {
            logger.error("Preparateur with email {} already exists", preparateur.getEmail());
            throw new UserAlreadyExistsException("Preparateur already exists!");
        }

        return preparateurPort.addPreparateur(preparateur);
    }

    @Override
    public Preparateur getPreparateur(String email) {
        // INFO level for a significant operation
        logger.info("Getting preparateur with email: {}", email);

        if (preparateurPort.isPreparateur(email)) {
            return preparateurPort.getPreparateur(email);
        } else {
            logger.error("Preparateur with email {} doesn't exist", email);
            throw new NoSuchElementException("Preparateur with such email doesn't exist!");
        }
    }

    @Override
    public Boolean isPreparateur(String email) {
        logger.debug("Checking if preparateur with email {} exists", email);
        return preparateurPort.isPreparateur(email);
    }
}
