package BMG.BookManamgnet.Services;

import BMG.BookManamgnet.Entities.Book;
import BMG.BookManamgnet.Entities.User;
import BMG.BookManamgnet.Repository.BookDAO;
import BMG.BookManamgnet.Repository.UserDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserDAO userDAO;
    private final BookDAO bookDAO;

    public UserService(UserDAO userDAO, BookDAO bookDAO) {
        this.userDAO = userDAO;
        this.bookDAO = bookDAO;
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
    public final User addUser(User user){
        return userDAO.save(user);

    }
    public final List<User> getUsers(){
        return userDAO.findAll();
    }
}
