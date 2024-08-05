//package BMG.BookManamgnet.Role;
//
//import jakarta.annotation.PostConstruct;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@AllArgsConstructor
//public class RoleService {
//    private final RoleRepository roleRepository;
//
//    @PostConstruct
//    public void initializeRoles() {
//        List<String> defaultRoles = Arrays.asList("USER", "ADMIN");
//        for (String roleName : defaultRoles) {
//            Optional<Role> existingRole = roleRepository.findByName(roleName);
//            if (existingRole.isEmpty()) {
//                Role role = new Role(roleName);
//                roleRepository.save(role);
//            }
//        }
//    }
//}
