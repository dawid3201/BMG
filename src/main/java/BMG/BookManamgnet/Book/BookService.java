package BMG.BookManamgnet.Book;

import BMG.BookManamgnet.Exception.BookAlreadyAddedException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class BookService {
    private final BookRepository bookRepository;


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

}
