package BMG.BookManamgnet.Entities;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
@Data
public class Book {
    @Id
    private String ID;

    @NotEmpty(message = "Title field cannot be empty")
    private String title;

    @NotEmpty(message = "Author field cannot be empty")
    private String author;

    @NotEmpty(message = "Date of Release field cannot be empty")
    private String dateOfRelease;

    @NotEmpty(message = "Description field cannot be empty")
    private String description;

    @NotEmpty(message = "Type field cannot be empty")
    private String type;

    @NotEmpty(message = "Universe field cannot be empty")
    private String universe;

    private ArrayList<String> usersWhoRentsThisBook = new ArrayList<>();

    public final List<String> getUsersWhoRentsThisBook(){
        return this.usersWhoRentsThisBook;
    }
}
