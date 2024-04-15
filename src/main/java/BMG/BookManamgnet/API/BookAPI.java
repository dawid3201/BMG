package BMG.BookManamgnet.API;

import BMG.BookManamgnet.Entities.Book;
import BMG.BookManamgnet.Services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.management.BadAttributeValueExpException;
import java.util.List;
@RestController
public class BookAPI {
    @Autowired
    private BookService bookService;

    @PostMapping("/addBook")
    public ResponseEntity<Book> addBook(@RequestParam("title")String title,
                        @RequestParam("author") String author,
                        @RequestParam("dateOfRelease") String dateOfRelease,
                        @RequestParam("description") String description,
                                        @RequestParam("type") String type){
        return ResponseEntity.ok(this.bookService.addBook(title,author,dateOfRelease,description, type));
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
}
