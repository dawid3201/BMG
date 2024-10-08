package BMG.BookManamgnet.Customer;

import BMG.BookManamgnet.Exception.*;
import BMG.BookManamgnet.Customer.Customer.Customer;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
@RequestMapping("/api/user")
public class CustomerAPI {
    private CustomerService customerService;

   @GetMapping("/byEmail")
    public ResponseEntity<Customer> getByUsername(@RequestParam("email") String email) throws UserNotFoundException {
       return ResponseEntity.ok(this.customerService.getUser(email));
   }
   @PostMapping("/rentBook")
    public ResponseEntity<?> rentBook(@RequestParam("email") String email,
                                      @RequestParam("bookTitle") String bookTitle) throws UserAlreadyRentsThisBookException,
           UserNotFoundException, BookNotFoundException, NoAvailableCopiesException {
       return ResponseEntity.ok(customerService.rentBook(email, bookTitle));
   }
   @PostMapping("/returnBook")
   public ResponseEntity<Boolean> returnBook(@RequestParam("title") String title,
                                       @RequestParam("userEmail") String userEmail) throws UserNotFoundException, BookNotFoundException {
       return ResponseEntity.ok(customerService.returnBook(title, userEmail));
   }
   @GetMapping("/getUsers")
    public ResponseEntity<List<Customer>> getUsers(){
       return ResponseEntity.ok(customerService.getUsers());
   }
   @DeleteMapping("/user")
    public ResponseEntity<String> deleteUser(@RequestParam("userId") String userId) throws UserNotFoundException, UserRentsBooksException {
       return ResponseEntity.ok(customerService.deleteUser(userId));
   }

}
