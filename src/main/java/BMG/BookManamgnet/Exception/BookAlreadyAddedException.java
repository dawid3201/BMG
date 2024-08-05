package BMG.BookManamgnet.Exception;

public class BookAlreadyAddedException extends Exception{
    public BookAlreadyAddedException(String message){
        super(message);
    }
    public BookAlreadyAddedException(){
        super("Book with this title already exists on database.");
    }
}
