package BMG.BookManamgnet.Entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

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
    private String role; // ADMIN, USER


    //Store all books that user rents
    @ManyToMany
    @JoinTable(
            name = "rented_books",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> rentedBooks = new ArrayList<>();
}
