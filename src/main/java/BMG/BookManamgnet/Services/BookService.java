package BMG.BookManamgnet.Services;

import BMG.BookManamgnet.Entities.Book;
import BMG.BookManamgnet.Repository.BookRepository;
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
    public final Book addBook(Book book){
        return bookRepository.save(book);
    }

}
