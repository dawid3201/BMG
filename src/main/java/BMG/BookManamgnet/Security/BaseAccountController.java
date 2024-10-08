package BMG.BookManamgnet.Security;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
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

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseAccountController<T> {

    @Value("${security.jwt.secret-key}")
    private String jwtSecretKey;

    @Value("${security.jwt.issuer}")
    private String jwtIssuer;

    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public BaseAccountController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    protected String createJwtToken(T user, String email, List<String> roles, Map<String, Object> additionalClaims) {
        Instant now = Instant.now();

        JwtClaimsSet.Builder claimsBuilder = JwtClaimsSet.builder()
                .issuer(jwtIssuer)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(25 * 3600))
                .subject(email)
                .claim("roles", roles);

        additionalClaims.forEach(claimsBuilder::claim);

        JwtClaimsSet claims = claimsBuilder.build();

        var encoder = new NimbusJwtEncoder(new ImmutableSecret<>(jwtSecretKey.getBytes()));
        var params = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims);

        return encoder.encode(params).getTokenValue();
    }

    protected BCryptPasswordEncoder getPasswordEncoder() {
        return this.passwordEncoder;
    }

    protected ResponseEntity<Object> authenticateAndGenerateToken(String username, String password, T user, String jwtToken) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            var response = new HashMap<String, Object>();
            response.put("token", jwtToken);
            response.put("user", user);

            return ResponseEntity.ok(response);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.badRequest().body("Bad username or password.");
        }
    }

    protected abstract ResponseEntity<Object> registerUser(T user);
}
