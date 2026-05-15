package hu.letswatch;

import hu.letswatch.model.Movie;
import hu.letswatch.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void testSaveAndFindMovie() {
        // 1. Előkészítés (Given)
        Movie movie = new Movie(null, "Interstellar", "Christopher Nolan", 2014, null);

        // 2. Végrehajtás (When)
        Movie savedMovie = movieRepository.save(movie);

        // 3. Ellenőrzés (Then)
        assertThat(savedMovie.getId()).isNotNull();
        assertThat(savedMovie.getTitle()).isEqualTo("Interstellar");
    }
}