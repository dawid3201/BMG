package BMG.BookManamgnet.Services;

import BMG.BookManamgnet.Entities.Book;
import BMG.BookManamgnet.Entities.MyUser;
import BMG.BookManamgnet.Repository.BookRepository;
import BMG.BookManamgnet.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BookRepository bookDAO;

    public final MyUser getUser(String userId){
        MyUser user = userRepository.findByEmail(userId);
        if(user != null){
            if(user.getEmail().equals(userId)){
                return user;
            }
        }
        throw new IllegalArgumentException("User with email: " + userId + " not found!");
    }
    public String rentBook( String email, String bookTitle){
        MyUser user = userRepository.findByEmail(email);
        if(user != null){
            Book book = bookDAO.findBookByTitle(bookTitle);
            if(book != null){
                user.getRentedBooks().add(book);
                userRepository.save(user);

                book.getUsersWhoRentThisBook().add(user);
                bookDAO.save(book);
            }
        }else{
            return "No user with email: " + email + " exists.";
        }
        return "Rental confirmed to user with email: " + user.getEmail();
    }
    public final List<MyUser> getUsers(){
        return userRepository.findAll();
    }

    public final String deleteUser(String userId){
        MyUser user = userRepository.findByEmail(userId);
        if(user != null){
            userRepository.delete(user);
            return "User with email: " + user.getEmail() + " has been deleted.";
        }
        throw new IllegalArgumentException("User with email: " + userId + " was not found.");
    }
}
