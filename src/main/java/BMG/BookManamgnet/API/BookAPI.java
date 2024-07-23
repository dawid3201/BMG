package BMG.BookManamgnet.API;

import BMG.BookManamgnet.Entities.Book;
import BMG.BookManamgnet.Services.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Book> addBook(@RequestBody Book book){
        return ResponseEntity.ok(bookService.addBook(book));
    }

}
