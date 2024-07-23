package BMG.BookManamgnet.Repository;

import BMG.BookManamgnet.Entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String username);

    AppUser findByEmail(String email);

}
