package BMG.BookManamgnet.User;

import BMG.BookManamgnet.Exception.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserAPI {
    private UserService userService;

   @GetMapping("/byUsername")
    public ResponseEntity<AppUser> getByUsername(@RequestParam("username") String username) throws UserNotFoundException {
       return ResponseEntity.ok(this.userService.getUser(username));
   }

   @PostMapping("/rentBook")
    public ResponseEntity<?> rentBook(@RequestParam("email") String email,
                                      @RequestParam("bookTitle") String bookTitle) throws UserAlreadyRentsThisBookException,
           UserNotFoundException, BookNotFoundException {
       return ResponseEntity.ok(userService.rentBook(email, bookTitle));
   }
   @GetMapping("/getUsers")
    public ResponseEntity<List<AppUser>> getUsers(){
       return ResponseEntity.ok(userService.getUsers());
   }
   @DeleteMapping("/user")
    public ResponseEntity<String> deleteUser(@RequestParam("userId") String userId) throws UserNotFoundException, UserRentsBooksException {
       return ResponseEntity.ok(userService.deleteUser(userId));
   }

}
