package application.port;

import application.domain.Preference;
import application.domain.Product;
import application.port.in.PreferenceUseCase;
import application.port.out.PreferencePort;
import application.port.out.ProductPort;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;
@Service
@AllArgsConstructor
public class PreferenceService implements PreferenceUseCase {
    private final static Logger logger= LogManager.getLogger(PreferenceService.class);

    PreferencePort preferencePort;
    ProductPort productPort;
    @Override
    public Preference createPrefence(String idPreference) {
        logger.info("Create preference list for user with id "+idPreference);
        return preferencePort.createPrefence(idPreference);
    }

    @Override
    public Preference addProduct(String idPreference, UUID idProduct) {
        logger.debug("Get Product with id "+idProduct);
        Product product = productPort.getProduct(idProduct);
        logger.debug("Get preference list of user with id "+idPreference);
        Preference preference = this.getPreference(idPreference);
        logger.info("Add product with id "+idProduct+" to Preference list of user with id "+idPreference);
        return preferencePort.addProduct(preference,product);
    }

    @Override
    public Preference deleteProductFromPreference(String idPreference, UUID idProduct) {
        logger.debug("Get Product with id "+idProduct);
        Product product = productPort.getProduct(idProduct);
        logger.debug("Get preference list of user with id "+idPreference);

        Preference preference = this.getPreference(idPreference);
        logger.info("Delete product with id "+idProduct+" to Preference list of user with id "+idPreference);

        return preferencePort.deleteProductFromPreference(preference,product);
    }

    @Override
    public Preference getPreference(String idPreference) {
        logger.debug("Check if preference list exist of user with id "+idPreference);
        if(this.availablePreference(idPreference)){
            logger.info("Get list of Preference of user with id :"+idPreference);
            return preferencePort.getPreference(idPreference);
        }
        else{
            logger.error("This preference doesn't exist!");
            throw new NoSuchElementException("This preference doesn't exist!");
        }
    }

    @Override
    public Boolean availablePreference(String idPreference) {
        logger.debug("Check if preference list exist of user with id "+idPreference);

        return preferencePort.availablePreference(idPreference);
    }
}