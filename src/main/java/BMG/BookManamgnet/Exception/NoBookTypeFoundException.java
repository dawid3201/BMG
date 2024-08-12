package BMG.BookManamgnet.Exception;

public class NoBookTypeFoundException extends Exception {
    public NoBookTypeFoundException(String message){
        super(message);
    }
    public NoBookTypeFoundException(){
        super("No such book type was found.");
    }
}
