package BMG.BookManamgnet.API;

import BMG.BookManamgnet.Entities.MyUser;
import BMG.BookManamgnet.Repository.UserRepository;
import BMG.BookManamgnet.Services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.management.BadAttributeValueExpException;
import java.util.List;

@RestController
@AllArgsConstructor
public class UserAPI {
//    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

    //------------------------User Registration-----------------------
    @PostMapping("/register/user")
    public MyUser createUser(@RequestBody MyUser user) throws Exception {
        MyUser existUser = userRepository.findByEmail(user.getEmail());
        if(existUser != null){
            throw new BadAttributeValueExpException("User with email: " + user.getEmail() + " already exists.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    //For now, Users can be registerd on Postman. Login WORKS and redirect based on ROLES
    //TODO: make register page register users and redirect them to proper page.

    @PostMapping("/rentBook")
    public final ResponseEntity<String> rentBook(@RequestParam("email") String email, @RequestParam("bookTitle") String bookTitle){
        return ResponseEntity.ok(this.userService.rentBook(email,bookTitle));
    }

    @GetMapping("/getUsers")
    public final ResponseEntity<List<MyUser>> getUsers(){
        return ResponseEntity.ok(this.userService.getUsers());
    }
    @GetMapping("/getUser")
    public final ResponseEntity<MyUser> getUser(@RequestParam("userId") String userId){
        return ResponseEntity.ok(this.userService.getUser(userId));
    }
    @DeleteMapping("/deleteUser")
    public final ResponseEntity<String> deleteUser(@RequestParam("userId") String userId){
        return ResponseEntity.ok(this.userService.deleteUser(userId));
    }
}
