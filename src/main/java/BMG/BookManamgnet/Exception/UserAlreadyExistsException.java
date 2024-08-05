package BMG.BookManamgnet.Exception;

public class UserAlreadyExistsException extends Exception{
    public UserAlreadyExistsException(String message){
        super(message);
    }
    public UserAlreadyExistsException(){
        super("User with this email already exists.");
    }
}
