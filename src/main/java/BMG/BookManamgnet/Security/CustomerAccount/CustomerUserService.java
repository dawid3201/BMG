package BMG.BookManamgnet.Security.CustomerAccount;

import BMG.BookManamgnet.Customer.Customer.Customer;
import BMG.BookManamgnet.Customer.Customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerUserService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByUsername(username);

        if (customer == null) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        // Convert roles to GrantedAuthority for Spring Security
        List<GrantedAuthority> authorities = customer.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // Build and return UserDetails object
        return User.withUsername(customer.getUsername())
                .password(customer.getPassword())
                .authorities(authorities)
                .build();
    }
}
