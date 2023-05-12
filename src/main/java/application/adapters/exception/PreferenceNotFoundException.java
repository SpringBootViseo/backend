package application.adapters.exception;

public class PreferenceNotFoundException extends RuntimeException {
    public PreferenceNotFoundException(String id) {
        super("Preference not found with id : " + id);
    }
}
