package BMG.BookManamgnet.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterDTO {
    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    @Size(min = 6)
    private String username;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Size(min = 8)
    @Pattern(regexp = "[a-zA-Z0-9_]+")
    private String password;

    @JsonProperty
    private Boolean AdminCondition;
}
