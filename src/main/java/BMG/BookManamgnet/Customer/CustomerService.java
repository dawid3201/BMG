package BMG.BookManamgnet.Customer;

import BMG.BookManamgnet.Book.Book;
import BMG.BookManamgnet.Book.BookRepository;
import BMG.BookManamgnet.Book.HandelingBook.RentedBook;
import BMG.BookManamgnet.Exception.*;
import BMG.BookManamgnet.Customer.Customer.Customer;
import BMG.BookManamgnet.Customer.Customer.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final BookRepository bookRepository;

    public final Customer getUser(String email) throws UserNotFoundException {
        Customer customer = customerRepository.findByEmail(email);
        if(customer != null){
            if(customer.getEmail().equals(email)){
                return customer;
            }
        }
        throw new UserNotFoundException("User with email: " + email + " not found!");
    }
    public final Boolean rentBook(String email, String bookTitle)
            throws UserAlreadyRentsThisBookException, UserNotFoundException,
            BookNotFoundException, NoAvailableCopiesException {

        Customer user = customerRepository.findByEmail(email);
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

                customerRepository.save(user);
                bookRepository.save(book);
                return true;
            } else {
                throw new BookNotFoundException();
            }
        } else {
            throw new UserNotFoundException();
        }
    }
    private void checkIfUserRentsThisBook(Customer user, Book book) throws UserAlreadyRentsThisBookException {
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

        Customer user = customerRepository.findByEmail(userEmail);
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

                customerRepository.save(user);
                bookRepository.save(book);

                return true;
            } else {
                throw new BookNotFoundException("Book with title: " + title + " was not found on user rent list.");
            }
        } else {
            throw new UserNotFoundException("User with email: " + userEmail + " was not found.");
        }
    }

    public final List<Customer> getUsers(){
        return customerRepository.findAll();
    }

    public final String deleteUser(String userId) throws UserRentsBooksException, UserNotFoundException {
        Customer user = customerRepository.findByEmail(userId);
        if(user == null){
            throw new UserNotFoundException();
        }
        if(user.getRentedBooks() != null){
            throw new UserRentsBooksException();
        }
        customerRepository.delete(user);
        return "User with email: "+ user.getEmail() + "deleted.";
    }
}
