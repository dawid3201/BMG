package BMG.BookManamgnet.Security.EmployeeAccount;

import BMG.BookManamgnet.Employee.Admin.Admin;
import BMG.BookManamgnet.Employee.Admin.AdminRepository;
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
public class EmployeeUserService implements UserDetailsService {
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByEmail(email);

        if (admin == null) {
            throw new UsernameNotFoundException("Admin not found with email: " + email);
        }

        // Convert roles to GrantedAuthority for Spring Security
        List<GrantedAuthority> authorities = admin.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // Build and return UserDetails object
        return User.withUsername(admin.getEmail())
                .password(admin.getPassword())
                .authorities(authorities)
                .build();
    }
}
