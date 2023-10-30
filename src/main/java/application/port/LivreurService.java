package application.port;

import application.adapters.exception.UserAlreadyExistsException;
import application.domain.Livreur;
import application.port.in.LivreurUseCase;
import application.port.out.LivreurPort;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@AllArgsConstructor
@Service
public class LivreurService implements LivreurUseCase {
    private final LivreurPort livreurPort;
    private final static Logger logger= LogManager.getLogger(LivreurService.class);
    @Override
    public Livreur addLivreur(Livreur livreur) {
        if(livreurPort.isLivreur(livreur.getEmail())){
            logger.error("Livreur Already exist!");
            throw new UserAlreadyExistsException("Livreur Already exist!");

        }
        logger.info("create livreur "+livreur.toString());
        return livreurPort.addLivreur(livreur);
    }

    @Override
    public Livreur getLivreur(String email) {
        if(livreurPort.isLivreur(email)){
            logger.info("get livreur with email "+email);
            return livreurPort.getLivreur(email);
        }

        else{
            logger.error("Livreur with such mail doesn't exist!");
            throw new NoSuchElementException("Livreur with such mail doesn't exist!");
        }
    }

    @Override
    public Boolean isLivreur(String email) {
        logger.debug("Check if livreur with email "+email+" exist");
        return livreurPort.isLivreur(email);
    }
}
