package BMG.BookManamgnet.Exception;

public class UserRentsBooksException extends Exception{
    public UserRentsBooksException(String message){
        super(message);
    }
    public UserRentsBooksException(){
        super("User cannot be deleted because they rent books.");
    }
}
