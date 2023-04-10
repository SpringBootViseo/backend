package application.adapters.exception;

public class OrderStateAlreadyExistsException extends RuntimeException{
    public  OrderStateAlreadyExistsException(){
        super("Order State already Exist!");
    }
}
