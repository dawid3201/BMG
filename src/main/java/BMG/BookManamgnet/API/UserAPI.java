package BMG.BookManamgnet.API;

import BMG.BookManamgnet.Entities.Book;
import BMG.BookManamgnet.Entities.Movie;
import BMG.BookManamgnet.Entities.User;
import BMG.BookManamgnet.Repository.UserDAO;
import BMG.BookManamgnet.Services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.management.BadAttributeValueExpException;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class UserAPI {
    private UserService userService;
    private UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;
    //------------------------User Registration-----------------------
    @PostMapping("/registerNewUser")
    public ResponseEntity<User> getUserDetails(@RequestBody User user) throws Exception{
        if(userDAO.existsByEmail(user.getEmail())){
            throw new BadAttributeValueExpException("User with email: " + user.getEmail() + " already exists!");
        }
        user.setName(user.getName());
        user.setEmail(user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add("USER");

        user.setRentedMovies(new ArrayList<>());
        user.setRentedBooks(new ArrayList<>());

        userDAO.save(user);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/rentBook")
    public final ResponseEntity<String> rentBook(@RequestParam("email") String email, @RequestParam("bookTitle") String bookTitle){
        return ResponseEntity.ok(this.userService.rentBook(email,bookTitle));
    }

    @GetMapping("/getUsers")
    public final ResponseEntity<List<User>> getUsers(){
        return ResponseEntity.ok(this.userService.getUsers());
    }
    @GetMapping("/getUser")
    public final ResponseEntity<User> getUser(@RequestParam("userId") String userId){
        return ResponseEntity.ok(this.userService.getUser(userId));
    }
    @DeleteMapping("/deleteUser")
    public final ResponseEntity<String> deleteUser(@RequestParam("userId") String userId){
        return ResponseEntity.ok(this.userService.deleteUser(userId));
    }
}
