package BMG.BookManamgnet.API;

import BMG.BookManamgnet.Entities.User;
import BMG.BookManamgnet.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserAPI {
    @Autowired
    private UserService userService;

    @PostMapping("/rentBook")
    public final ResponseEntity<String> rentBook(@RequestParam("email") String email, @RequestParam("bookTitle") String bookTitle){
        return ResponseEntity.ok(this.userService.rentBook(email,bookTitle));
    }
    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@Valid @RequestBody User user, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        return ResponseEntity.ok(this.userService.addUser(user));
    }

    @GetMapping("/getUsers")
    public final ResponseEntity<List<User>> getUsers(){
        return ResponseEntity.ok(this.userService.getUsers());
    }
}
