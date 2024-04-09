package BMG.BookManamgnet.API;

import BMG.BookManamgnet.Entities.User;
import BMG.BookManamgnet.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserAPI {
    @Autowired
    private UserService userService;

    @PostMapping("/rentBook")
    public final ResponseEntity<String> rentBook(@RequestParam("email") String email, @RequestParam("bookId") String bookTitle){
        return ResponseEntity.ok(this.userService.rentBook(email,bookTitle));
    }
    @PostMapping("/addUser")
    public final ResponseEntity<User> addUser(@RequestParam("name")String name, @RequestParam("email") String email){
        return ResponseEntity.status(201).body(this.userService.addUser(name, email));
    }
    @GetMapping("/getUsers")
    public final ResponseEntity<List<User>> getUsers(){
        return ResponseEntity.ok(this.userService.getUsers());
    }




}
