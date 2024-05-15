package BMG.BookManamgnet.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String dateOfRelease;
    private String description;
    private String type;
    private String universe;

    @ManyToMany(mappedBy = "rentedMovies")
    private List<MyUser> usersWhoRentThisMovie = new ArrayList<>();
}
