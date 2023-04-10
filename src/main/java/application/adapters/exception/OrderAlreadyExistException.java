package application.adapters.exception;

public class OrderAlreadyExistException extends  RuntimeException{
    public OrderAlreadyExistException(){
        super("Order Already Exists!");
    }
}
