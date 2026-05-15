package hu.letswatch;

import hu.letswatch.controller.MovieController;
import hu.letswatch.model.Director;
import hu.letswatch.model.Movie;
import hu.letswatch.repository.DirectorRepository;
import hu.letswatch.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class MovieControllerTest {

    @Autowired
    private MovieController movieController;

    @MockBean
    private MovieRepository repository;

    @MockBean
    private DirectorRepository directorRepository;

    @Test
    void testGetAllMovies() {
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie());
        when(repository.findAll()).thenReturn(movies);

        List<Movie> result = movieController.getAllMovies();
        assertEquals(1, result.size());
    }

    @Test
    void testSaveNewMovieWithExistingDirector() {
        Director existingDirector = new Director("Christopher Nolan");
        Movie movie = new Movie();
        movie.setTitle("Inception");
        movie.setDirector(new Director("Christopher Nolan"));

        when(directorRepository.findByName("Christopher Nolan")).thenReturn(Optional.of(existingDirector));
        when(repository.findAll()).thenReturn(new ArrayList<>());
        when(repository.save(any(Movie.class))).thenReturn(movie);

        Movie result = movieController.saveOrUpdateMovie(movie);

        assertNotNull(result);
        assertEquals("Inception", result.getTitle());
    }

    @Test
    void testUpdateExistingMovie() {
        Movie existingMovie = new Movie();
        existingMovie.setId(1L);
        existingMovie.setTitle("Inception");
        existingMovie.setStatus("WATCHLIST");

        Movie updateInfo = new Movie();
        updateInfo.setTitle("Inception");
        updateInfo.setStatus("WATCHED");
        updateInfo.setRating(5);

        List<Movie> currentMovies = new ArrayList<>();
        currentMovies.add(existingMovie);

        when(repository.findAll()).thenReturn(currentMovies);
        when(repository.save(any(Movie.class))).thenReturn(existingMovie);

        Movie result = movieController.saveOrUpdateMovie(updateInfo);

        assertEquals("WATCHED", result.getStatus());
        assertEquals(5, result.getRating());
    }

    @Test
    void testDeleteMovie() {
        movieController.deleteMovie(1L);
        verify(repository, times(1)).deleteById(1L);
    }
}