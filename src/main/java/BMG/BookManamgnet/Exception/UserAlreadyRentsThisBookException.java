package BMG.BookManamgnet.Exception;

public class UserAlreadyRentsThisBookException extends Exception{
    public UserAlreadyRentsThisBookException(String message){
        super(message);
    }
    public UserAlreadyRentsThisBookException(){
        super("This Book is already rented by this or another user.");
    }
}
