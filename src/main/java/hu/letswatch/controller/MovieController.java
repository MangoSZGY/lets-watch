package hu.letswatch.controller;

import hu.letswatch.model.Movie;
import hu.letswatch.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/movies")
@CrossOrigin(origins = "*")
public class MovieController {

    @Autowired
    private MovieRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    // AZ ÚJ KULCSOD
    private final String API_KEY = "d96764e4";

    @GetMapping
    public List<Movie> getAllMovies() {
        return repository.findAll();
    }

    @GetMapping("/search-online")
    public List<Movie> searchOnline(@RequestParam String query) {
        String url = "http://www.omdbapi.com/?s=" + query + "&apikey=" + API_KEY;

        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response == null || !"True".equals(response.get("Response"))) {
                return Collections.emptyList();
            }

            List<Map<String, String>> searchResults = (List<Map<String, String>>) response.get("Search");

            return searchResults.stream().map(m -> {
                Movie movie = new Movie();
                movie.setTitle(m.get("Title"));

                try {
                    String year = m.get("Year").replaceAll("[^0-9]", "");
                    movie.setReleaseYear(year.length() >= 4 ? Integer.parseInt(year.substring(0, 4)) : 0);
                } catch (Exception e) {
                    movie.setReleaseYear(0);
                }

                movie.setPosterUrl(m.get("Poster"));
                movie.setGenre("Movie");
                movie.setPlot("Kattints a részletekért!");
                return movie;
            }).collect(Collectors.toList());

        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @PostMapping
    public Movie saveOrUpdateMovie(@RequestBody Movie movie) {
        return repository.findAll().stream()
                .filter(m -> m.getTitle().equalsIgnoreCase(movie.getTitle()))
                .findFirst()
                .map(existingMovie -> {
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