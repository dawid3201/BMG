package BMG.BookManamgnet.Repository;

import BMG.BookManamgnet.Entities.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<MyUser, Long> {
    Optional<MyUser> findUserByName(String name);

    MyUser findByEmail(String email);

}
