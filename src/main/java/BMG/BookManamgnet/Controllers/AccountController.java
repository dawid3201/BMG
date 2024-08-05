package BMG.BookManamgnet.Controllers;

import BMG.BookManamgnet.DTO.LoginDTO;
import BMG.BookManamgnet.DTO.RegisterDTO;
import BMG.BookManamgnet.Role.Role;
import BMG.BookManamgnet.Role.RoleRepository;
import BMG.BookManamgnet.Security.SecretKeyGenerator;
import BMG.BookManamgnet.User.AppUser;
import BMG.BookManamgnet.User.AppUserRepository;
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
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Value("${security.jwt.secret-key}")
    private String jwtSecretKey;

    private SecretKeyGenerator secretKeyGenerator;

    @Value("${security.jwt.issuer}")
    private String jwtIssuer;


    private final AppUserRepository appUserRepository;
    private final AuthenticationManager authenticationManager;

    private final RoleRepository roleRepository;

    @Autowired
    public AccountController(AppUserRepository appUserRepository,
                             AuthenticationManager authenticationManager,
                             RoleRepository roleRepository) {
        this.appUserRepository = appUserRepository;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
    }
    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterDTO registerDTO,
                                           BindingResult bindingResult){
        checkBindingResult(bindingResult);

        var bCryptEncoder = new BCryptPasswordEncoder();

        AppUser appUser = new AppUser();
        appUser.setFirstname(registerDTO.getFirstName());
        appUser.setLastname(registerDTO.getLastName());
        appUser.setUsername(registerDTO.getUsername());
        appUser.setEmail(registerDTO.getEmail());
        CheckAdminCondition(registerDTO, appUser);
        appUser.setPassword(bCryptEncoder.encode(registerDTO.getPassword()));
        //save user to database
        try{
            var otherUser = appUserRepository.findByUsername(registerDTO.getUsername());
            if(otherUser != null){
                return ResponseEntity.badRequest().body("Username already used");
            }

            otherUser = appUserRepository.findByEmail(registerDTO.getEmail());
            if(otherUser != null){
                return ResponseEntity.badRequest().body("Email already used.");
            }

            appUserRepository.save(appUser);
            String jwtToken = createJwtToken(appUser);
            var response = new HashMap<String, Object>();
            response.put("token", jwtToken);
            response.put("user", appUser);

            return ResponseEntity.ok(response);
        }catch (Exception ex){
            System.out.println("There is an exception: ");
            ex.printStackTrace();
        }
        //in case of other exceptions
        return ResponseEntity.badRequest().body("Error");
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginDTO loginDTO, BindingResult bindingResult){
        checkBindingResult(bindingResult);
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsername(),
                        loginDTO.getPassword()
                    )
            );
            AppUser appUser = appUserRepository.findByUsername(loginDTO.getUsername());
            String jwtToken = createJwtToken(appUser);

            var response = new HashMap<String, Object>();
            response.put("token", jwtToken);
            response.put("user", appUser);


            return ResponseEntity.ok(response);

        }catch (Exception exception){
            System.out.println("There is an Exception: ");
            exception.printStackTrace();
        }
        System.out.println(appUserRepository);
        return ResponseEntity.badRequest().body("Bad username or password.");
    }
    //-----------------HELPER-METHODS-------------------
    private void checkBindingResult(BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            var errorList = bindingResult.getAllErrors();
            var errorMap = new HashMap<String, String>();

            for (org.springframework.validation.ObjectError objectError : errorList) {
                var error = (FieldError) objectError;
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            ResponseEntity.badRequest().body(errorMap);
        }
    }
    private String createJwtToken(AppUser appUser){
        Instant now = Instant.now();

        List<String> roleList = appUser.getRoles();
        List<String> grantedAuthorities = roleList.stream().toList();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(jwtIssuer)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(25 * 3600))
                .subject(appUser.getEmail())
                .claim("roles", grantedAuthorities) //storing roles as ROLE_USER in JWT token
                .build();

        var encoder = new NimbusJwtEncoder(
                new ImmutableSecret<>(jwtSecretKey.getBytes()));
        var params = JwtEncoderParameters.from(JwsHeader
                .with(MacAlgorithm.HS256).build(), claims);
        //create a jwt token
        return encoder.encode(params).getTokenValue();
    }
    private void CheckAdminCondition(RegisterDTO registerDTO, AppUser appUser) {
        if (registerDTO.getAdminCondition()) {
            appUser.getRoles().add("ROLE_USER");
            appUser.getRoles().add("ROLE_ADMIN");
        } else {
            appUser.getRoles().add("ROLE_USER");
        }
    }
}
