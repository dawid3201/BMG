package BMG.BookManamgnet.Book;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private String dateOfRelease;
    private String description;
    private String type;
    private String universe;

    //TODO: add availability, if book is rented then show book is unavailable
    //private boolean available;
    //private double price;
}


