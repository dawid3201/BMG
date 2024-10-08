package BMG.BookManamgnet.Book;

import BMG.BookManamgnet.Exception.BookAlreadyAddedException;
import BMG.BookManamgnet.Exception.NoBookTypeFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@AllArgsConstructor
@RequestMapping("/api/book")
@CrossOrigin(origins = "http://localhost:3000")
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
    @GetMapping("/getByType")
    public ResponseEntity<List<Book>> getBooksByType(@RequestParam("type") String type) throws NoBookTypeFoundException {
        return ResponseEntity.ok(bookService.findByType(type));
    }
    @GetMapping("/getByTitle")
    public ResponseEntity<List<Book>> getBooksByTitle(@RequestParam("title") String title) throws NoBookTypeFoundException {
        return ResponseEntity.ok(bookService.findByTitle(title));
    }
}
