package BMG.BookManamgnet.Repository;

import BMG.BookManamgnet.Entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MovieRepository extends JpaRepository<Movie, String> {

}
