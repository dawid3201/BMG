package BMG.BookManamgnet.Services;

import BMG.BookManamgnet.Entities.Movie;
import BMG.BookManamgnet.Repository.MovieRepository;
import BMG.BookManamgnet.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@AllArgsConstructor
@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    public final List<Movie> getMovies(){return this.movieRepository.findAll();}

    public final Movie addMovie(Movie movie){
        log.info("Adding movie: {}", movie);
        return movieRepository.save(movie);
    }

}
