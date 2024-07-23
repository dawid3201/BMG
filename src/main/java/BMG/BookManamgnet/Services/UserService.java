package BMG.BookManamgnet.Services;

import BMG.BookManamgnet.Entities.Book;
import BMG.BookManamgnet.Entities.AppUser;
import BMG.BookManamgnet.Repository.BookRepository;
import BMG.BookManamgnet.Repository.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final AppUserRepository appUserRepository;
    private final BookRepository bookRepository;

    public final AppUser getUser(String username){
        AppUser user = appUserRepository.findByEmail(username);
        if(user != null){
            if(user.getEmail().equals(username)){
                return user;
            }
        }
        throw new IllegalArgumentException("User with email: " + username + " not found!");
    }
    public String rentBook( String email, String bookTitle){
        AppUser user = appUserRepository.findByEmail(email);
        if(user != null){
            Book book = bookRepository.findBookByTitle(bookTitle);
            if(book != null){
                user.getRentedBooks().add(book);
                appUserRepository.save(user);
                bookRepository.save(book);
            }
        }else{
            return "No user with email: " + email + " exists.";
        }
        return "Rental confirmed to user with email: " + user.getEmail();
    }
    public final List<AppUser> getUsers(){
        return appUserRepository.findAll();
    }

    public final String deleteUser(String userId){
        AppUser user = appUserRepository.findByEmail(userId);
        if(user != null){
            appUserRepository.delete(user);
            return "User with email: " + user.getEmail() + " has been deleted.";
        }
        throw new IllegalArgumentException("User with email: " + userId + " was not found.");
    }
}
