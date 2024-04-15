package BMG.BookManamgnet.Repository;

import BMG.BookManamgnet.Entities.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieDAO extends MongoRepository<Movie, String> {

}
