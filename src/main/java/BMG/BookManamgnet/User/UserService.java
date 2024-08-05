package BMG.BookManamgnet.User;

import BMG.BookManamgnet.Book.Book;
import BMG.BookManamgnet.Book.BookRepository;
import BMG.BookManamgnet.Exception.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final AppUserRepository appUserRepository;
    private final BookRepository bookRepository;

    public final AppUser getUser(String username) throws UserNotFoundException {
        AppUser user = appUserRepository.findByEmail(username);
        if(user != null){
            if(user.getEmail().equals(username)){
                return user;
            }
        }
        throw new UserNotFoundException("User with email: " + username + " not found!");
    }
    public String rentBook( String email, String bookTitle) throws UserAlreadyRentsThisBookException,
            UserNotFoundException, BookNotFoundException {
        AppUser user = appUserRepository.findByEmail(email);
        if(user != null){
            Book book = bookRepository.findBookByTitle(bookTitle);
            if(book != null){
                if(checkUserBooks(user,book)){
                    user.getRentedBooks().add(book);
                    appUserRepository.save(user);
                    bookRepository.save(book);
                    return "Rental confirmed to user with email: " + user.getEmail();
                }else{
                    throw new UserAlreadyRentsThisBookException();
                }
            }else{
                throw new BookNotFoundException();
            }
        }else{
            throw new UserNotFoundException();
        }
    }
    private boolean checkUserBooks(AppUser user, Book book){
        return !user.getRentedBooks().contains(book);
    }
    public final List<AppUser> getUsers(){
        return appUserRepository.findAll();
    }

    public final String deleteUser(String userId) throws UserRentsBooksException, UserNotFoundException {
        AppUser user = appUserRepository.findByEmail(userId);
        if(user == null){
            throw new UserNotFoundException();
        }
        if(user.getRentedBooks() != null){
            throw new UserRentsBooksException();
        }
        appUserRepository.delete(user);
        return "User with email: "+ user.getEmail() + "deleted.";
    }
}
