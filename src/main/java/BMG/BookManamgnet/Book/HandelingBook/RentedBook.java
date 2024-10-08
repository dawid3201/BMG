package BMG.BookManamgnet.Book.HandelingBook;

import BMG.BookManamgnet.Book.Book;
import BMG.BookManamgnet.Customer.Customer.Customer;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "rented_books")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class RentedBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Customer userId;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book bookId;

    @Column(name = "rental_date")
    private LocalDate rental_date;
}

