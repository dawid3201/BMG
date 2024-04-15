package BMG.BookManamgnet.Entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
public class Book {

    private String title;
    private String author;
    private String dateOfRelease;
    private String description;
    private boolean status;
    private String type;
    private String universe;

    private List<User> usersWhoRentsThisBook;

    public boolean getStatus(){
        return status;
    }
    public final List<User> getUsersWhoRentsThisBook(){
        return usersWhoRentsThisBook;
    }
}
