package BMG.BookManamgnet.DTO.EmployeeDTO;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Date;

@Data
public class EmployeeRegisterDTo {

    @NotEmpty
    private String full_name;

    @NotEmpty
    private String address;

    @NotNull
    private Long phone_number;

    @NotNull
    private Date date_of_birth;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Size(min = 8)
    @Pattern(regexp = "[a-zA-Z0-9_]+")
    private String password;
}
