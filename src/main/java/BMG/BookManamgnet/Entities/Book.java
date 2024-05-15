package BMG.BookManamgnet.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany(mappedBy = "rentedBooks")
    private List<MyUser> usersWhoRentThisBook = new ArrayList<>();
}


