package BMG.BookManamgnet.Security.EmployeeAccount;

import BMG.BookManamgnet.DTO.EmployeeDTO.EmployeeLoginDTO;
import BMG.BookManamgnet.DTO.EmployeeDTO.EmployeeRegisterDTo;
import BMG.BookManamgnet.Employee.Admin.Admin;
import BMG.BookManamgnet.Employee.Admin.AdminRepository;
import BMG.BookManamgnet.Security.BaseAccountController;
import BMG.BookManamgnet.Security.HelperUserInterface.HelperInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/employee_account")
public class EmployeeAccountController extends BaseAccountController<Admin> implements HelperInterface {

    private final AdminRepository adminRepository;

    @Autowired
    public EmployeeAccountController(AdminRepository adminRepository, AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.adminRepository = adminRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerEmployeeAdmin(@Valid @RequestBody EmployeeRegisterDTo employeeRegisterDTo,
                                                        BindingResult bindingResult) {
        checkBindingResult(bindingResult);

        Admin admin = new Admin();
        admin.setFull_name(employeeRegisterDTo.getFull_name());
        admin.setAddress(employeeRegisterDTo.getAddress());
        admin.setPhone_number(employeeRegisterDTo.getPhone_number());
        admin.setDate_of_birth(employeeRegisterDTo.getDate_of_birth());
        admin.setEmail(employeeRegisterDTo.getEmail());
        admin.setPassword(getPasswordEncoder().encode(employeeRegisterDTo.getPassword()));
        admin.setEmployeeId(randomEmployeeID());
        admin.getRoles().add("ROLE_ADMIN");

        try {
            var adminUser = adminRepository.findByEmail(employeeRegisterDTo.getEmail());
            if (adminUser != null) {
                return ResponseEntity.badRequest().body("Email already registered.");
            }
            adminRepository.save(admin);

            Map<String, Object> additionalClaims = Map.of("employee_id", admin.getEmployeeId());
            String jwtToken = createJwtToken(admin, admin.getEmail(), admin.getRoles(), additionalClaims);
            return authenticateAndGenerateToken(admin.getEmail(), employeeRegisterDTo.getPassword(), admin, jwtToken);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ResponseEntity.badRequest().body("Error");
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody EmployeeLoginDTO employeeLoginDTO,
                                        BindingResult bindingResult) {
        checkBindingResult(bindingResult);

        if (!checkIfEmployee(employeeLoginDTO.getEmployee_id())) {
            return ResponseEntity.badRequest().body("No Employee with ID: " + employeeLoginDTO.getEmployee_id() + " found.");
        }

        Admin admin = adminRepository.findByEmail(employeeLoginDTO.getEmail());
        if (admin == null) {
            return ResponseEntity.badRequest().body("Employee not found");
        }

        Map<String, Object> additionalClaims = Map.of("employee_id", admin.getEmployeeId());
        String jwtToken = createJwtToken(admin, admin.getEmail(), admin.getRoles(), additionalClaims);
        return authenticateAndGenerateToken(admin.getEmail(), employeeLoginDTO.getPassword(), admin, jwtToken);
    }

    private boolean checkIfEmployee(int employee_id) {
        Admin admin = adminRepository.findByEmployeeId(employee_id);
        return admin != null && admin.getEmployeeId() == employee_id;
    }

    private int randomEmployeeID() {
        final Random RANDOM = new Random();
        Set<Integer> uniqueDigits = new HashSet<>();
        StringBuilder result = new StringBuilder();
        while (uniqueDigits.size() < 6) {
            int digit = RANDOM.nextInt(10);
            if (uniqueDigits.add(digit)) {
                result.append(digit);
            }
        }
        return Integer.parseInt(result.toString());
    }

    @Override
    protected ResponseEntity<Object> registerUser(Admin user) {
        return null; // Not needed, handled by specific registerEmployeeAdmin method
    }
}
