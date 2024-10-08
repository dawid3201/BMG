package BMG.BookManamgnet.DTO.EmployeeDTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class EmployeeLoginDTO {
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
    @NotEmpty
    private int employee_id;

}
