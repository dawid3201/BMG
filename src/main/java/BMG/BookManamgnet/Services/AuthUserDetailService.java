package BMG.BookManamgnet.Services;

import BMG.BookManamgnet.Repository.UserDAO;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@AllArgsConstructor
@Service
public class AuthUserDetailService implements UserDetailsService {
    private final UserDAO userDAO;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<BMG.BookManamgnet.Entities.User> user = userDAO.findByEmail(username.toLowerCase());
        if(user.isEmpty()){
            throw new UsernameNotFoundException(username);
        }else{
            return User.builder()
                    .username(user.get().getEmail())
                    .password(user.get().getPassword())
                    .build();
        }
    }
}
