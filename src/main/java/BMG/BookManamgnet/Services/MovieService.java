package BMG.BookManamgnet.Services;

import BMG.BookManamgnet.Entities.Book;
import BMG.BookManamgnet.Entities.Movie;
import BMG.BookManamgnet.Repository.MovieDAO;
import BMG.BookManamgnet.Repository.UserDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class MovieService {
    private final MovieDAO movieDAO;
    private final UserDAO userDAO;
    public MovieService(MovieDAO movieDAO, UserDAO userDAO){
        this.movieDAO = movieDAO;
        this.userDAO = userDAO;
        log.info("MovieService has been created!");
    }

    public final List<Movie> getMovies(){return this.movieDAO.findAll();}

    public final Movie addMovie(Movie movie){
        log.info("Adding movie: {}", movie);
        return movieDAO.save(movie);
    }

}
