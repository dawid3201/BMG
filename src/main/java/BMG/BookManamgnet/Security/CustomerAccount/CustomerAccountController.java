package BMG.BookManamgnet.Security.CustomerAccount;

import BMG.BookManamgnet.DTO.CustomerDTO.CustomerLoginDTO;
import BMG.BookManamgnet.DTO.CustomerDTO.CustomerRegisterDTO;
import BMG.BookManamgnet.Customer.Customer.Customer;
import BMG.BookManamgnet.Customer.Customer.CustomerRepository;
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
public class CustomerAccountController implements HelperInterface{
    @Value("${security.jwt.secret-key}")
    private String jwtSecretKey;

    @Value("${security.jwt.issuer}")
    private String jwtIssuer;

    private final CustomerRepository customerRepository;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public CustomerAccountController(CustomerRepository customerRepository,
                                     AuthenticationManager authenticationManager) {
        this.customerRepository = customerRepository;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody CustomerRegisterDTO customerRegisterDTO,
                                               BindingResult bindingResult){
        checkBindingResult(bindingResult);

        var bCryptEncoder = new BCryptPasswordEncoder();
        Customer customer = new Customer();
        customer.getRoles().add("ROLE_USER");
        customer.setFirstname(customerRegisterDTO.getFirstName());
        customer.setLastname(customerRegisterDTO.getLastName());
        customer.setUsername(customerRegisterDTO.getUsername());
        customer.setEmail(customerRegisterDTO.getEmail());
        customer.setPassword(bCryptEncoder.encode(customerRegisterDTO.getPassword()));

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

            String jwtToken = createJwtToken(customer);
            var response = new HashMap<String, Object>();
            response.put("token", jwtToken);
            response.put("user", customer);

            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            System.out.println("There is an exception: ");
            ex.printStackTrace();
        }
        return ResponseEntity.badRequest().body("Error");
    }

    private String createJwtToken(Customer customer){
        Instant now = Instant.now();

        List<String> roleList = customer.getRoles();
        List<String> grantedAuthorities = roleList.stream().toList();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(jwtIssuer)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(25 * 3600))
                .subject(customer.getEmail())
                .claim("roles", grantedAuthorities) //storing roles as ROLE_USER in JWT token
                .build();

        var encoder = new NimbusJwtEncoder(
                new ImmutableSecret<>(jwtSecretKey.getBytes()));
        var params = JwtEncoderParameters.from(JwsHeader
                .with(MacAlgorithm.HS256).build(), claims);
        //create a jwt token
        return encoder.encode(params).getTokenValue();
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody CustomerLoginDTO customerLoginDTO, BindingResult bindingResult){
        checkBindingResult(bindingResult);
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                        customerLoginDTO.getUsername(),
                        customerLoginDTO.getPassword()
                    )
            );
            Customer customer = customerRepository.findByUsername(customerLoginDTO.getUsername());
            String jwtToken = createJwtToken(customer);

            var response = new HashMap<String, Object>();
            response.put("token", jwtToken);
            response.put("user", customer);

            return ResponseEntity.ok(response);

        }catch (Exception exception){
            System.out.println("There is an Exception: ");
            exception.printStackTrace();
        }
        System.out.println(customerLoginDTO.getUsername());
        System.out.println(customerLoginDTO.getPassword());
        return ResponseEntity.badRequest().body("Bad username or password.");
    }
}
