package BMG.BookManamgnet.API;

import BMG.BookManamgnet.Entities.AppUser;
import BMG.BookManamgnet.Services.UserService;
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
    public ResponseEntity<AppUser> getByUsername(@RequestParam("username") String username){
       return ResponseEntity.ok(this.userService.getUser(username));
   }

   @PostMapping("/rentBook")
    public ResponseEntity<?> rentBook(@RequestParam("email") String email,
                                      @RequestParam("bookTitle") String bookTitle){
       return ResponseEntity.ok(userService.rentBook(email, bookTitle));
   }
   @GetMapping("/getUsers")
    public ResponseEntity<List<AppUser>> getUsers(){
       return ResponseEntity.ok(userService.getUsers());
   }
   @DeleteMapping("/user")
    public ResponseEntity<String> deleteUser(@RequestParam("userId") String userId){
       return ResponseEntity.ok(userService.deleteUser(userId));
   }

}
