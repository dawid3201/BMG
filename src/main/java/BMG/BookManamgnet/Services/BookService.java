package BMG.BookManamgnet.Services;

import BMG.BookManamgnet.Entities.Book;
import BMG.BookManamgnet.Repository.BookDAO;
import org.springframework.stereotype.Service;

import javax.management.BadAttributeValueExpException;
import java.util.ArrayList;
import java.util.List;
@Service
public class BookService {
    private final BookDAO bookDAO;

    public BookService(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }
    public final List<Book> getBooks(){
        return this.bookDAO.findAll();
    }
    public final Book addBook(Book book){
        return bookDAO.save(book);
    }
    public final Book findByTitle(String title) throws BadAttributeValueExpException {
        Book book = bookDAO.findBookByTitle(title);
        if(book != null && book.getTitle().equals(title)){
            return book;
        }else{
            throw new BadAttributeValueExpException("No book with title: " + title + " found.");
        }
    }
    public final ArrayList<Book> findByType(String type){
        ArrayList<Book> bookList = new ArrayList<>();
        for(Book book : getBooks()){
            if(book.getType().equals(type)){
                bookList.add(book);
            }
        }
        return bookList;
    }
    public final List<Book> findAllBooksByUniverse(String universe){
        List<Book> bookList = new ArrayList<>();
        for(Book book : getBooks()){
            if(book.getUniverse().equals(universe)){
                bookList.add(book);
            }
        }
        return bookList;
    }
}
