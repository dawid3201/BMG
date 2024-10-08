package BMG.BookManamgnet.Security.CustomerAccount;

import BMG.BookManamgnet.DTO.CustomerDTO.CustomerLoginDTO;
import BMG.BookManamgnet.DTO.CustomerDTO.CustomerRegisterDTO;
import BMG.BookManamgnet.Customer.Customer.Customer;
import BMG.BookManamgnet.Customer.Customer.CustomerRepository;
import BMG.BookManamgnet.Security.BaseAccountController;
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

import java.util.*;

import java.time.Instant;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/customer_account")
public class CustomerAccountController extends BaseAccountController<Customer> implements HelperInterface {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerAccountController(CustomerRepository customerRepository, AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.customerRepository = customerRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody CustomerRegisterDTO customerRegisterDTO,
                                               BindingResult bindingResult) {
        checkBindingResult(bindingResult);

        Customer customer = new Customer();
        customer.getRoles().add("ROLE_USER");
        customer.setFirstname(customerRegisterDTO.getFirstName());
        customer.setLastname(customerRegisterDTO.getLastName());
        customer.setUsername(customerRegisterDTO.getUsername());
        customer.setEmail(customerRegisterDTO.getEmail());
        customer.setPassword(getPasswordEncoder().encode(customerRegisterDTO.getPassword()));

        try {
            var otherUser = customerRepository.findByUsername(customerRegisterDTO.getUsername());
            if (otherUser != null) {
                return ResponseEntity.badRequest().body("Username already used");
            }

            otherUser = customerRepository.findByEmail(customerRegisterDTO.getEmail());
            if (otherUser != null) {
                return ResponseEntity.badRequest().body("Email already used.");
            }
            customerRepository.save(customer);

            String jwtToken = createJwtToken(customer, customer.getEmail(), customer.getRoles(), new HashMap<>());
            return authenticateAndGenerateToken(customer.getUsername(), customerRegisterDTO.getPassword(), customer, jwtToken);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ResponseEntity.badRequest().body("Error");
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody CustomerLoginDTO customerLoginDTO, BindingResult bindingResult) {
        checkBindingResult(bindingResult);
        Customer customer = customerRepository.findByUsername(customerLoginDTO.getUsername());

        if (customer == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        String jwtToken = createJwtToken(customer, customer.getEmail(), customer.getRoles(), new HashMap<>());
        return authenticateAndGenerateToken(customer.getUsername(), customerLoginDTO.getPassword(), customer, jwtToken);
    }

    @Override
    protected ResponseEntity<Object> registerUser(Customer user) {
        return null; // Not needed, handled by specific registerUser method above
    }
}

