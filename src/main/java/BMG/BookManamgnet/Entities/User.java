package BMG.BookManamgnet.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data //For getters, setters
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String email;

    private String name;
    private int booksRented;
    private boolean isRentingABook;
    private int numberOfRentedBooks;
    private List<User> rentedBooks;

}
