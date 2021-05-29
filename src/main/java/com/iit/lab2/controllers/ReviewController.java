package com.iit.lab2.controllers;

import com.iit.lab2.persist.entity.Review;
import com.iit.lab2.persist.request.ReviewRequest;
import com.iit.lab2.persist.response.RestException;
import com.iit.lab2.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/reviews")
    public List<Review> getReviews() {
        return reviewService.findAll();
    }

    @GetMapping("/reviews/{id}")
    public Review getReview(@PathVariable Long id) throws RestException {
        return reviewService.findById(id).get();
    }

    @DeleteMapping("/reviews/delete/{id}")
    public void deleteReview(@PathVariable Long id) throws RestException {
        reviewService.delete(id);
    }

    @GetMapping("/reviews/update/{id}")
    public Review update(@PathVariable Long id) throws RestException {
        return reviewService.findById(id).get();
    }

    @PostMapping("/reviews/update/{id}")
    public void update(@PathVariable Long id, @RequestBody ReviewRequest review) throws RestException {
        review.setId(id);
        reviewService.update(review);
    }

    @PostMapping("/reviews/create")
    public void createNewReview(@RequestBody ReviewRequest review) throws RestException {
        reviewService.create(review);
    }
}
