package application.port;

import application.domain.Preference;
import application.domain.Product;
import application.port.in.PreferenceUseCase;
import application.port.out.PreferencePort;
import application.port.out.ProductPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;
@Service
@AllArgsConstructor
public class PreferenceService implements PreferenceUseCase {

    PreferencePort preferencePort;
    ProductPort productPort;
    @Override
    public Preference createPrefence(String idPreference) {
        return preferencePort.createPrefence(idPreference);
    }

    @Override
    public Preference addProduct(String idPreference, UUID idProduct) {
        Product product = productPort.getProduct(idProduct);
        Preference preference = this.getPreference(idPreference);
        return preferencePort.addProduct(preference,product);
    }

    @Override
    public Preference deleteProductFromPreference(String idPreference, UUID idProduct) {
        Product product = productPort.getProduct(idProduct);
        Preference preference = this.getPreference(idPreference);
        return preferencePort.deleteProductFromPreference(preference,product);
    }

    @Override
    public Preference getPreference(String idPreference) {
        if(this.availablePreference(idPreference))
            return preferencePort.getPreference(idPreference);
        else throw new NoSuchElementException("This preference doesn't exist!");
    }

    @Override
    public Boolean availablePreference(String idPreference) {
        return preferencePort.availablePreference(idPreference);
    }
}
