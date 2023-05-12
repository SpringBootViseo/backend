package application.adapters.exception;

public class PreferenceAlreadyExistsException  extends RuntimeException {
    public PreferenceAlreadyExistsException(String message){
        super(message);
    }
}

