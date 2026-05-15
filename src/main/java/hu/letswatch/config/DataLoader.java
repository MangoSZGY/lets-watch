package hu.letswatch.config;

import hu.letswatch.repository.MovieRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner initDatabase(MovieRepository repository) {
        return args -> {
            // Itt most nem adunk hozzá semmit automatikusan
            System.out.println("--- Adatbázis kész, várja a filmeket! ---");
        };
    }
}