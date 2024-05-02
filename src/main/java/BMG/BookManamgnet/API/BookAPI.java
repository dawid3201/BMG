package BMG.BookManamgnet.API;

import BMG.BookManamgnet.Entities.Book;
import BMG.BookManamgnet.Services.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.management.BadAttributeValueExpException;
import java.util.List;
@RestController
public class BookAPI {
    @Autowired
    private BookService bookService;

    @PostMapping("/addBook")
    public ResponseEntity<?> addBook(@Valid @RequestBody Book book, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        return ResponseEntity.ok(this.bookService.addBook(book));
    }
    @GetMapping("/getBooks")
    public ResponseEntity<List<Book>> getBooks(){
        return ResponseEntity.ok(bookService.getBooks());
    }

    @GetMapping("/findByTitle")
    public final ResponseEntity<Book> findByTitle(@RequestParam("title") String title) throws BadAttributeValueExpException {
        return ResponseEntity.ok(this.bookService.findByTitle(title));
    }
    @GetMapping("/findByType")
    public final ResponseEntity<List<Book>> findByType(@RequestParam("type") String type)  {
        return ResponseEntity.ok(this.bookService.findByType(type));
    }
    @GetMapping("/findByUniverse")
    public final ResponseEntity<List<Book>> findByUniverse(@RequestParam("universe") String universe)  {
        return ResponseEntity.ok(this.bookService.findAllBooksByUniverse(universe));
    }
    @DeleteMapping("/deleteBook")
    public final ResponseEntity<String> deleteBook(@RequestParam("bookId") String bookId){
        return ResponseEntity.ok(this.bookService.deleteBook(bookId));
    }
}
