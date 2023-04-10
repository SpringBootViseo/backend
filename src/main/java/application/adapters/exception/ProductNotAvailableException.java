package application.adapters.exception;

public class ProductNotAvailableException extends RuntimeException{
    public ProductNotAvailableException(){
        super("Can't order this quantity");
    }
}
