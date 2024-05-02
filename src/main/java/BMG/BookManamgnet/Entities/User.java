package BMG.BookManamgnet.Entities;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document("user")
@Data //For getters, setters
public class User {
    @Id
    @NotEmpty(message = "Email field cannot be empty")
    private String email;

    @NotEmpty(message = "Name field cannot be empty")
    private String name;

    @NotEmpty(message = "Password field cannot be empty")
    private String password;

    private List<String> roles = new ArrayList<>();

    private ArrayList<String> rentedMovies = new ArrayList<>();

    private ArrayList<String> rentedBooks = new ArrayList<>();
}
