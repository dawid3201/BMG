package BMG.BookManamgnet.Security;

import BMG.BookManamgnet.Security.AccessDenied.AccessDeniedHandlerImpl;
import BMG.BookManamgnet.Security.CustomerAccount.CustomerUserService;
import BMG.BookManamgnet.Security.EmployeeAccount.EmployeeUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.crypto.spec.SecretKeySpec;

import java.util.Arrays;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    @Value("${security.jwt.secret-key}")
    private String jwtSecretKey;

    private final CustomerUserService customerUserService;

    private final EmployeeUserService employeeUserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/home").permitAll()
                        .requestMatchers("/customer_account/login").permitAll()
                        .requestMatchers("/customer_account/register").permitAll()
                        .requestMatchers("/employee_account/login").permitAll()
                        .requestMatchers("/employee_account/register").permitAll()
                        .requestMatchers("/employee_account/employee").permitAll()
                        .requestMatchers("/account/registerAdmin").permitAll()
                        .requestMatchers("/book/add").hasAuthority("SCOPE_ROLE_ADMIN")
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth -> oauth
                        .jwt(jwt -> jwt
                                .decoder(jwtDecoder())
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())))
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .build();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new AccessDeniedHandlerImpl();
    }
    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider customerProvider = new DaoAuthenticationProvider();
        customerProvider.setUserDetailsService(customerUserService);
        customerProvider.setPasswordEncoder(new BCryptPasswordEncoder());

        DaoAuthenticationProvider adminProvider = new DaoAuthenticationProvider();
        adminProvider.setUserDetailsService(employeeUserService);  // Use the AdminUserService
        adminProvider.setPasswordEncoder(new BCryptPasswordEncoder());

        return new ProviderManager(Arrays.asList(customerProvider, adminProvider));  // Support both
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        var secretKey = new SecretKeySpec(jwtSecretKey.getBytes(), "HmacSHA256");
        return NimbusJwtDecoder.withSecretKey(secretKey).macAlgorithm(MacAlgorithm.HS256).build();
    }

    @Bean
    public Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthoritiesClaimName("roles");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }
}
