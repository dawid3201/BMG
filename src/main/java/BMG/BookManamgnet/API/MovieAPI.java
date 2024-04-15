package BMG.BookManamgnet.API;

import BMG.BookManamgnet.Entities.Movie;
import BMG.BookManamgnet.Services.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
@RestController
public class MovieAPI {
    @Autowired
    private MovieService movieService;

    @PostMapping("/addMovie")
    public final ResponseEntity<?> addMovie(@Valid @RequestBody Movie newMovie, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            // Return validation errors
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        return ResponseEntity.ok(this.movieService.addMovie(newMovie));
    }
}
