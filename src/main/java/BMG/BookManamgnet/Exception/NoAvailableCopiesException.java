package BMG.BookManamgnet.Exception;

public class NoAvailableCopiesException extends Exception {
    public NoAvailableCopiesException(String message){
        super(message);
    }
    public NoAvailableCopiesException(){
        super("There are no copies of this book left for rent.");
    }
}
