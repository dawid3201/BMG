package BMG.BookManamgnet.Employee.Admin;

import BMG.BookManamgnet.Employee.Employee;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "my_employee")
public class Admin implements Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String full_name;
    private String address;
    private Long phone_number;
    private Date date_of_birth;

    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    @Column(name = "employee_id")
    private int employeeId;


    @ElementCollection
    @CollectionTable(name = "employee_roles", joinColumns = @JoinColumn(name = "employee_id"))
    @Column(name = "role_name")
    private List<String> roles = new ArrayList<>();
}
