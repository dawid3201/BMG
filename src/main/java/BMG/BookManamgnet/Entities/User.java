package BMG.BookManamgnet.Entities;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document
@Data //For getters, setters
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @NotEmpty(message = "Email field cannot be empty")
    private String email;

    @NotEmpty(message = "Name field cannot be empty")
    private String name;

    private ArrayList<Movie> rentedMovies = new ArrayList<>();

    private ArrayList<String> rentedBooks = new ArrayList<>();

}
