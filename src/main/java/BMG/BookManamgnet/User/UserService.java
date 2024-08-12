package BMG.BookManamgnet.User;

import BMG.BookManamgnet.Book.Book;
import BMG.BookManamgnet.Book.BookRepository;
import BMG.BookManamgnet.Book.HandelingBook.RentedBook;
import BMG.BookManamgnet.Exception.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    public final Boolean rentBook(String email, String bookTitle)
            throws UserAlreadyRentsThisBookException, UserNotFoundException,
            BookNotFoundException, NoAvailableCopiesException {

        AppUser user = appUserRepository.findByEmail(email);
        if (user != null) {
            Book book = bookRepository.findBookByTitle(bookTitle);
            if (book != null) {
                checkIfUserRentsThisBook(user, book);
                checkIfCopiesAreLeftForThisBook(book);

                RentedBook rentedBook = new RentedBook();
                rentedBook.setUserId(user);
                rentedBook.setBookId(book);
                rentedBook.setRental_date(LocalDate.now());

                user.getRentedBooks().add(rentedBook);

                appUserRepository.save(user);
                bookRepository.save(book);
                return true;
            } else {
                throw new BookNotFoundException();
            }
        } else {
            throw new UserNotFoundException();
        }
    }
    private void checkIfUserRentsThisBook(AppUser user, Book book) throws UserAlreadyRentsThisBookException {
        for (RentedBook rentedBook : user.getRentedBooks()) {
            if (rentedBook.getBookId().equals(book)) {
                throw new UserAlreadyRentsThisBookException("User is already renting this book");
            }
        }
    }
    private void checkIfCopiesAreLeftForThisBook(Book book) throws NoAvailableCopiesException {
        if(book.getCopies() == 0){
            throw new NoAvailableCopiesException("There are no available copies left for this book.");
        }else{
            book.setCopies(book.getCopies()-1);
        }
    }
    public final Boolean returnBook(String title, String userEmail)
            throws UserNotFoundException, BookNotFoundException {

        AppUser user = appUserRepository.findByEmail(userEmail);
        if (user != null) {
            RentedBook rentedBookToReturn = null;

            for (RentedBook rentedBook : user.getRentedBooks()) {
                if (rentedBook.getBookId().getTitle().equals(title)) {
                    rentedBookToReturn = rentedBook;
                    break;
                }
            }
            if (rentedBookToReturn != null) {
                Book book = rentedBookToReturn.getBookId();
                book.setCopies(book.getCopies()+1);

                user.getRentedBooks().remove(rentedBookToReturn);

                appUserRepository.save(user);
                bookRepository.save(book);

                return true;
            } else {
                throw new BookNotFoundException("Book with title: " + title + " was not found on user rent list.");
            }
        } else {
            throw new UserNotFoundException("User with email: " + userEmail + " was not found.");
        }
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
