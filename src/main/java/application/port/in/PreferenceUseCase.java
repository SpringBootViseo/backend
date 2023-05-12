package application.port.in;

import application.domain.Preference;

import java.util.UUID;

public interface PreferenceUseCase {
    Preference createPrefence(String idPreference);
    Preference addProduct(String idPreference , UUID idProduct);
    Preference deleteProductFromPreference(String idPreference,UUID idProduct);
    Preference getPreference(String idPreference);
    Boolean availablePreference(String idPreference);

}
