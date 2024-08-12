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
    private int copies;


}


