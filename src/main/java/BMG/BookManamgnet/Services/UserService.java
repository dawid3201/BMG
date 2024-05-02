package BMG.BookManamgnet.Services;

import BMG.BookManamgnet.Entities.Book;
import BMG.BookManamgnet.Entities.User;
import BMG.BookManamgnet.Repository.BookDAO;
import BMG.BookManamgnet.Repository.UserDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserDAO userDAO;
    private final BookDAO bookDAO;

    public final User getUser(String userId){
        User user = userDAO.getUserByEmail(userId);
        if(user != null){
            if(user.getEmail().equals(userId)){
                return user;
            }
        }
        throw new IllegalArgumentException("User with email: " + userId + " not found!");
    }
    public String rentBook( String email, String bookTitle){
        User user = userDAO.getUserByEmail(email);
        if(user != null){
            Book book = bookDAO.findBookByTitle(bookTitle);
            if(book != null){
                user.getRentedBooks().add(book.getTitle());
                userDAO.save(user);

                book.getUsersWhoRentsThisBook().add(user.getEmail());
                bookDAO.save(book);
            }
        }else{
            return "No user with email: " + email + " exists.";
        }
        return "Rental confirmed to user with email: " + user.getEmail();
    }
    public final List<User> getUsers(){
        return userDAO.findAll();
    }

    public final String deleteUser(String userId){
        User user = userDAO.getUserByEmail(userId);
        if(user != null){
            userDAO.delete(user);
            return "User with email: " + user.getEmail() + " has been deleted.";
        }
        throw new IllegalArgumentException("User with email: " + userId + " was not found.");
    }
}
