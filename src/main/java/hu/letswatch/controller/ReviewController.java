package hu.letswatch.controller;

import hu.letswatch.model.Review;
import hu.letswatch.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    // Összes értékelés lekérése
    @GetMapping
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    // Új értékelés mentése
    @PostMapping
    public Review createReview(@RequestBody Review review) {
        return reviewRepository.save(review);
    }
}