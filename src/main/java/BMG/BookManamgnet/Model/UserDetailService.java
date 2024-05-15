package BMG.BookManamgnet.Model;


import BMG.BookManamgnet.Entities.MyUser;
import BMG.BookManamgnet.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class UserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Optional<MyUser> user = userRepository.findUserByName(name);
        if(user.isPresent()){
            var userObj = user.get();
            return User.builder()
                    .username(userObj.getName())
                    .password(userObj.getPassword())
                    .roles(getRoles(userObj))
                    .build();
        }else{
            throw new UsernameNotFoundException(name);
        }
    }

    private String[] getRoles(MyUser user){
        if(user.getRole() == null){//if empty return USER
            return new String[]{"USER"};
        }
        return user.getRole().split(",");// if not return content split by comma
    }
}
