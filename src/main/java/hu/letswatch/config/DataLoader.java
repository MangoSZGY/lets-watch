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
            repository.deleteAll();
            System.out.println("--- Adatbázis üres, készen áll a saját filmjeidre! ---");
        };
    }
}