package application.port;

import application.adapters.exception.UserAlreadyExistsException;
import application.domain.Preparateur;
import application.port.in.PreparateurUseCase;
import application.port.out.PreparateurPort;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@AllArgsConstructor
@Service
public class PreparateurService implements PreparateurUseCase {
    private final PreparateurPort preparateurPort;
    private final static Logger logger= LogManager.getLogger(PreparateurService.class);
    @Override
    public Preparateur addPreparateur(Preparateur preparateur) {
        logger.debug("Check if preparateur"+preparateur.toString()+" Already exist");
        if(preparateurPort.isPreparateur(preparateur.getEmail())){
            logger.error("preparateur alreadyExist!");
            throw new UserAlreadyExistsException("preparateur alreadyExist!");
        }
        logger.info("Create new Preparateur "+preparateur.toString());

        return preparateurPort.addPreparateur(preparateur);
    }

    @Override
    public Preparateur getPreparateur(String email)
    {
        logger.debug("Check if preparateur with email "+email+" Already exist");

        if(preparateurPort.isPreparateur(email)){
            logger.info("Get preparateur info with email :"+email);
            return preparateurPort.getPreparateur(email);
        }

        else {
            logger.error("Preparateur with such mail doesn't exist!");
            throw new NoSuchElementException("Preparateur with such mail doesn't exist!");
        }
    }

    @Override
    public Boolean isPreparateur(String email) {
        logger.debug("Check if preparateur with email "+email+" Already exist");

        return preparateurPort.isPreparateur(email);
    }

}