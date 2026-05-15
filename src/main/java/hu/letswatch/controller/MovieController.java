package hu.letswatch.controller;

import hu.letswatch.model.Movie;
import hu.letswatch.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
@CrossOrigin(origins = "*")
public class MovieController {

    @Autowired
    private MovieRepository repository;

    @GetMapping
    public List<Movie> getAllMovies() {
        return repository.findAll();
    }

    @PostMapping
    public Movie saveOrUpdateMovie(@RequestBody Movie movie) {
        // Duplikáció ellenőrzése cím alapján
        return repository.findAll().stream()
                .filter(m -> m.getTitle().equalsIgnoreCase(movie.getTitle()))
                .findFirst()
                .map(existingMovie -> {
                    // Ha létezik, csak frissítjük (pl. Watchlist -> Watched)
                    existingMovie.setStatus(movie.getStatus());
                    existingMovie.setRating(movie.getRating());
                    return repository.save(existingMovie);
                })
                .orElseGet(() -> repository.save(movie));
    }

    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable Long id) {
        repository.deleteById(id);
    }
}