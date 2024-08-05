package BMG.BookManamgnet.User;

import BMG.BookManamgnet.Book.Book;
import BMG.BookManamgnet.Role.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "my_user")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String lastname;
    private String username;

    @Column(unique = true, nullable = false)
    private String email;
    private String password;

    //TODO: keep user record. What was rented and returned

    @ElementCollection
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role_name")
    private List<String> roles = new ArrayList<>();

    //Store all books that user rents
    @ManyToMany
    @JoinTable(
            name = "rented_books",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> rentedBooks = new ArrayList<>();
}
