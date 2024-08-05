package BMG.BookManamgnet.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginDTO {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
