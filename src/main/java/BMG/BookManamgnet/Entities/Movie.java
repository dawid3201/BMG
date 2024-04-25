package BMG.BookManamgnet.Entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
@Document
@Data
public class Movie {
    @Id
    @NotBlank(message = "Title field cannot be empty.")
    private String title;

    @NotBlank(message = "Date of Release field cannot be empty.")
    @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$", message = "Date of release must be in dd/mm/yyyy format")
    private String dateOfRelease;

   @NotBlank(message = "Description field cannot be empty.")
    private String description;

    @NotBlank(message = "Type field cannot be empty.")
    private String type;

    @NotBlank(message = "Universe field cannot be empty.")
    private String universe;

    @NotEmpty(message = "Actors field cannot be empty.")
    private ArrayList<String> actors;
}
