package application.port.out;

import application.domain.Preference;
import application.domain.Product;


public interface PreferencePort {
    Preference createPrefence(String idPreference);
    Preference addProduct(Preference preference, Product product);
    Preference deleteProductFromPreference(Preference preference,Product product);
    Preference getPreference(String idPreference);
    Boolean availablePreference(String idPreference);
}
