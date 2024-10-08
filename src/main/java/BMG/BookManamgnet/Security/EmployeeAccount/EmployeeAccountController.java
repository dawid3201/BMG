package BMG.BookManamgnet.Security.EmployeeAccount;

import BMG.BookManamgnet.DTO.EmployeeDTO.EmployeeLoginDTO;
import BMG.BookManamgnet.DTO.EmployeeDTO.EmployeeRegisterDTo;
import BMG.BookManamgnet.Employee.Admin.Admin;
import BMG.BookManamgnet.Employee.Admin.AdminRepository;
import BMG.BookManamgnet.Security.HelperUserInterface.HelperInterface;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.management.BadAttributeValueExpException;
import java.time.Instant;
import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/employee_account")
public class EmployeeAccountController implements HelperInterface {
    @Value("${security.jwt.secret-key}")
    private String jwtSecretKey;

    @Value("${security.jwt.issuer}")
    private String jwtIssuer;

    private final AdminRepository adminRepository;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public EmployeeAccountController(AdminRepository adminRepository,
                                     AuthenticationManager authenticationManager) {
        this.adminRepository = adminRepository;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerEmployeeAdmin(@Valid @RequestBody EmployeeRegisterDTo employeeRegisterDTo,
                                               BindingResult bindingResult){
        checkBindingResult(bindingResult);

        var bCryptEncoder = new BCryptPasswordEncoder();
        Admin admin = new Admin();
        admin.setFull_name(employeeRegisterDTo.getFull_name());
        admin.setAddress(employeeRegisterDTo.getAddress());
        admin.setPhone_number(employeeRegisterDTo.getPhone_number());
        admin.setDate_of_birth(employeeRegisterDTo.getDate_of_birth());
        admin.setEmail(employeeRegisterDTo.getEmail());
        admin.setPassword(bCryptEncoder.encode(employeeRegisterDTo.getPassword()));
        admin.setEmployeeId(randomEmployeeID());
        admin.getRoles().add("ROLE_ADMIN");



        try {
            var adminUser = adminRepository.findByEmail(employeeRegisterDTo.getEmail());
            if (adminUser != null) {
                return ResponseEntity.badRequest().body("Email already registered.");
            }
            adminRepository.save(admin);

            String jwtToken = createJwtToken(admin);
            var response = new HashMap<String, Object>();
            response.put("token", jwtToken);
            response.put("user", admin);

            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            System.out.println("There is an exception: ");
            ex.printStackTrace();
        }
        return ResponseEntity.badRequest().body("Error");
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
    private String createJwtToken(Admin admin){
        Instant now = Instant.now();

        List<String> roleList = admin.getRoles();
        List<String> grantedAuthorities = roleList.stream().toList();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(jwtIssuer)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(25 * 3600))
                .subject(admin.getEmail())
                .claim("roles", grantedAuthorities) //storing roles as ROLE_USER in JWT token
                .claim("employee_id", admin.getEmployeeId())
                .build();

        var encoder = new NimbusJwtEncoder(
                new ImmutableSecret<>(jwtSecretKey.getBytes()));
        var params = JwtEncoderParameters.from(JwsHeader
                .with(MacAlgorithm.HS256).build(), claims);
        //create a jwt token
        return encoder.encode(params).getTokenValue();
    }
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody EmployeeLoginDTO employeeLoginDTO,
                                        BindingResult bindingResult){
        checkBindingResult(bindingResult);
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            employeeLoginDTO.getEmail(),
                            employeeLoginDTO.getPassword()
                    )
            );
            if(!checkIfEmployee(employeeLoginDTO.getEmployee_id())){
                throw new BadAttributeValueExpException("No Employee with ID: " + employeeLoginDTO.getEmployee_id() + " found.");
            }
            Admin admin = adminRepository.findByEmail(employeeLoginDTO.getEmail());
            String jwtToken = createJwtToken(admin);

            var response = new HashMap<String, Object>();
            response.put("token", jwtToken);
            response.put("user", admin);

            return ResponseEntity.ok(response);

        }catch (Exception exception){
            System.out.println("There is an Exception: ");
            exception.printStackTrace();
        }
        System.out.println(adminRepository);
        return ResponseEntity.badRequest().body("Bad username or password.");
    }
    private boolean checkIfEmployee(int employee_id){
        Admin admin = adminRepository.findByEmployeeId(employee_id);
        if(admin != null){
            return admin.getEmployeeId() == employee_id;
        }
        return false;
    }
}
