package application.adapters.exception;

public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException(String id) {
        super("Cart not found with id : " + id);
    }
}
