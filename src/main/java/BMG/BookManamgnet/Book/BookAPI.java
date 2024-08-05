package BMG.BookManamgnet.Book;

import BMG.BookManamgnet.Exception.BookAlreadyAddedException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@AllArgsConstructor
@RequestMapping("/book")
public class BookAPI {
    private final BookService bookService;

    @GetMapping("/getBooks")
    public ResponseEntity<List<Book>> getBooks(){
        return ResponseEntity.ok(bookService.getBooks());
    }
    @PostMapping("/add")
    public ResponseEntity<Book> addBook(@RequestBody Book book) throws BookAlreadyAddedException {
        return ResponseEntity.ok(bookService.addBook(book));
    }

}
