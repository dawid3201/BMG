package BMG.BookManamgnet.Book;

import BMG.BookManamgnet.Exception.BookAlreadyAddedException;
import BMG.BookManamgnet.Exception.NoBookTypeFoundException;
import BMG.BookManamgnet.Customer.Customer.CustomerRepository;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final CustomerRepository customerRepository;


    public final List<Book> getBooks(){
        return this.bookRepository.findAll();
    }

    public final Book addBook(Book book) throws BookAlreadyAddedException{
        List<Book> books = bookRepository.findAll();
        for(Book newBook : books){
            if(newBook.getTitle().equals(book.getTitle())){
                throw new BookAlreadyAddedException("This book is already added to the library.");
            }
        }
        return bookRepository.save(book);
    }
    public final List<Book> findByType(String type) throws NoBookTypeFoundException {
        List<Book> specificTypeBooks = bookRepository.findAll().stream()
                .filter(book -> book.getType().contains(type)).toList();

        if(specificTypeBooks.isEmpty()){
            throw new NoBookTypeFoundException("No books of type: " + type + " found.");
        }
        return specificTypeBooks;
    }

    public final List<Book> findByTitle(String title) throws NoBookTypeFoundException {
        List<Book> specificTitleBooks = bookRepository.findAll().stream()
                .filter(book -> book.getTitle().contains(title)).toList();

        if(specificTitleBooks.isEmpty()){
            throw new NoBookTypeFoundException("No books with title: " + title + " found.");
        }
        return specificTitleBooks;
    }

}
