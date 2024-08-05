package BMG.BookManamgnet.Security;

import BMG.BookManamgnet.User.AppUser;
import BMG.BookManamgnet.User.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppUserService implements UserDetailsService {

    @Autowired
    private AppUserRepository appUserRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username);
        if(appUser != null){

            List<GrantedAuthority> authorities = appUser.getRoles().stream()
                    .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

            System.out.println(authorities);
            System.out.println(appUser.getRoles());

            return User.withUsername(appUser.getUsername())
                    .password(appUser.getPassword())
                    .authorities(authorities)
                    .build();

        }
        return null;
    }
}
