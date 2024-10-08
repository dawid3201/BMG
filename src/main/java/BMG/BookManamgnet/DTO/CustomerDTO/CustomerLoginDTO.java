package BMG.BookManamgnet.DTO.CustomerDTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CustomerLoginDTO {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
