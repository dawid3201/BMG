package BMG.BookManamgnet.Repository;

import BMG.BookManamgnet.Entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserDAO extends MongoRepository<User, Integer> {
    User getUserByEmail(String emial);
}
