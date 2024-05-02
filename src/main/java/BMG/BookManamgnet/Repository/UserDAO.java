package BMG.BookManamgnet.Repository;

import BMG.BookManamgnet.Entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserDAO extends MongoRepository<User, String> {
    User getUserByEmail(String email);

    Optional<User> findByEmail(String username);
    Boolean existsByEmail(String email);
}
