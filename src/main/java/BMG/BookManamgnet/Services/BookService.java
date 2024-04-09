package BMG.BookManamgnet.Services;

import BMG.BookManamgnet.Entities.Book;
import BMG.BookManamgnet.Repository.BookDAO;
import BMG.BookManamgnet.Repository.UserDAO;
import org.springframework.stereotype.Service;

import javax.management.BadAttributeValueExpException;
import java.util.ArrayList;
import java.util.List;
@Service
public class BookService {
    private final UserDAO userDAO;
    private final BookDAO bookDAO;

    public BookService(UserDAO userDAO, BookDAO bookDAO) {
        this.userDAO = userDAO;
        this.bookDAO = bookDAO;
    }
    public final List<Book> getBooks(){
        return this.bookDAO.findAll();
    }
    public final Book addBook(String title, String author, //Works
                              String dateOfRelease, String description, String type){
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setDateOfRelease(dateOfRelease);
        book.setDescription(description);
        book.setType(type);
        return bookDAO.save(book);

    }
    public final Book findByTitle(String title) throws BadAttributeValueExpException {
        Book book = bookDAO.findBookByTitle(title);
        if(book != null){
            return book;
        }else{
            throw new BadAttributeValueExpException("No book with title: " + title + " found.");
        }
    }
    public final List<Book> findByType(String type){
        List<Book> bookList = new ArrayList<>();
        for(Book book : getBooks()){
            if(book.getType().equals(type)){
                bookList.add(book);
            }
        }
        return bookList;
    }

    //method that returns books if they include title: Harry Potter should return all harry potters books

}
