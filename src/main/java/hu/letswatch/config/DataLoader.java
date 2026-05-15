package hu.letswatch.config;

import hu.letswatch.model.Movie;
import hu.letswatch.repository.MovieRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner initDatabase(MovieRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                Movie joker = new Movie();
                joker.setTitle("Joker");
                joker.setDirector("Todd Phillips");
                joker.setReleaseYear(2019);
                joker.setGenre("Drama");
                joker.setPosterUrl("https://images.weserv.nl/?url=https://m.media-amazon.com/images/M/MV5BNGVjN3RkN2UtZWEyZi00NzE3LWE3NjctNWVmYTNjZWNhNDUyXkEyXkFqcGdeQXVyMjUxOTkzMzI@._V1_SX300.jpg");
                joker.setStatus("WATCHLIST");
                joker.setPlot("In Gotham City, mentally troubled comedian Arthur Fleck is disregarded and mistreated by society.");
                repository.save(joker);
                System.out.println("--- SIKER: A Joker bekerült az adatbázisba! ---");
            }
        };
    }
}