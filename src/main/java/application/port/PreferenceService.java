package application.port;

import application.domain.Preference;
import application.domain.Product;
import application.port.in.PreferenceUseCase;
import application.port.out.PreferencePort;
import application.port.out.ProductPort;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PreferenceService implements PreferenceUseCase {
    private final PreferencePort preferencePort;
    private final ProductPort productPort;
    private static final Logger logger = LoggerFactory.getLogger(PreferenceService.class);

    @Override
    public Preference createPrefence(String idPreference) {
        // INFO level for a significant operation
        logger.info("Creating preference with ID: {}", idPreference);
        return preferencePort.createPrefence(idPreference);
    }

    @Override
    public Preference addProduct(String idPreference, UUID idProduct) {
        // INFO level for a significant operation
        logger.info("Adding product with ID {} to preference with ID: {}", idProduct, idPreference);

        Product product = productPort.getProduct(idProduct);
        Preference preference = this.getPreference(idPreference);
        return preferencePort.addProduct(preference, product);
    }

    @Override
    public Preference deleteProductFromPreference(String idPreference, UUID idProduct) {
        // INFO level for a significant operation
        logger.info("Deleting product with ID {} from preference with ID: {}", idProduct, idPreference);

        Product product = productPort.getProduct(idProduct);
        Preference preference = this.getPreference(idPreference);
        return preferencePort.deleteProductFromPreference(preference, product);
    }

    @Override
    public Preference getPreference(String idPreference) {
        logger.info("Getting preference with ID: {}", idPreference);

        if (this.availablePreference(idPreference)) {
            return preferencePort.getPreference(idPreference);
        } else {
            logger.error("Preference with ID {} doesn't exist", idPreference);
            throw new NoSuchElementException("This preference doesn't exist!");
        }
    }

    @Override
    public Boolean availablePreference(String idPreference) {
        // DEBUG level for a routine operation
        logger.debug("Checking if preference with ID {} is available", idPreference);
        return preferencePort.availablePreference(idPreference);
    }
}
