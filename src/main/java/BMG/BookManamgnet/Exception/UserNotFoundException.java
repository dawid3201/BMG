package BMG.BookManamgnet.Exception;

public class UserNotFoundException extends Exception{
    public UserNotFoundException(String message){
        super(message);
    }
    public UserNotFoundException(){
        super("User was not found");
    }
}
