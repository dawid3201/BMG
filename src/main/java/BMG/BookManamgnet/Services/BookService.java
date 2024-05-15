package BMG.BookManamgnet.Services;

import BMG.BookManamgnet.Entities.Book;
import BMG.BookManamgnet.Repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.management.BadAttributeValueExpException;
import java.util.ArrayList;
import java.util.List;
@Service
@AllArgsConstructor
public class BookService {
    private final BookRepository bookRepository;


    public final List<Book> getBooks(){
        return this.bookRepository.findAll();
    }
    public final Book addBook(Book book){
        return bookRepository.save(book);
    }

    public final Book findByTitle(String title) throws BadAttributeValueExpException {
        Book book = bookRepository.findBookByTitle(title);
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
    public final String deleteBook(Long bookId){
        Book book = bookRepository.findBookById(bookId);
        if(book != null){
            bookRepository.delete(book);
            return "Book with ID: " + bookId + " has been deleted.";
        }else{
            return "Book with ID: " + bookId + " was not found.";
        }
    }
}
