package com.iit.lab2.service;

import com.iit.lab2.persist.entity.Game;
import com.iit.lab2.persist.entity.Review;
import com.iit.lab2.persist.entity.User;
import com.iit.lab2.persist.repo.GameRepository;
import com.iit.lab2.persist.repo.ReviewRepository;
import com.iit.lab2.persist.repo.UserRepository;
import com.iit.lab2.persist.request.ReviewRequest;
import com.iit.lab2.persist.response.RestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository, GameRepository gameRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }

    public void create(ReviewRequest review) throws RestException {
        Review newReview = new Review();
        Optional<User> user = userRepository.findByUsername(review.getAuthor());
        if (!user.isPresent()) {
            throw new RestException(HttpStatus.NOT_FOUND, "Author is not exist", "author");
        }
        Optional<Game> game = gameRepository.findByName(review.getGame());
        if (!game.isPresent()) {
            throw new RestException(HttpStatus.NOT_FOUND, "Game is not exist", "game");
        }
        newReview.setGame(game.get());
        newReview.setAuthor(user.get());
        newReview.setReview(review.getReview());
        newReview.setDate(LocalDate.now());
        reviewRepository.save(newReview);
        log.info("Review was created");
    }

    public List<Review> findAll() {
        Iterable<Review> source = reviewRepository.findAll();
        List<Review> target = new ArrayList<>();
        source.forEach(target::add);
        log.info("{} addresses was found", target.size());
        return target;
    }

    public void delete(Long id) throws RestException {
        findById(id);
        reviewRepository.deleteById(id);
        log.info("The review has been removed");
    }

    public Optional<Review> findById(Long id) throws RestException {
        Optional<Review> review = reviewRepository.findById(id);
        if (review.isPresent()) {
            log.info("Review with id {} was found", id);
        } else {
            log.info("Review with id {} wasn't found", id);
            throw new RestException(HttpStatus.NOT_FOUND, "Review is not exist", "review");
        }
        return review;
    }

    public void update(ReviewRequest review) throws RestException {
        Optional<Review> row = reviewRepository.findById(review.getId());
        Review item;
        if (row.isPresent()) {
            item = row.get();
        } else {
            throw new RestException(HttpStatus.NOT_FOUND, "Review not fount", "review");
        }
        Optional<User> user = userRepository.findByUsername(review.getAuthor());
        if (!user.isPresent()) {
            throw new RestException(HttpStatus.NOT_FOUND, "Author is not exist", "author");
        }else {
            item.setAuthor(user.get());
        }
        Optional<Game> game = gameRepository.findByName(review.getGame());
        if (!game.isPresent()) {
            throw new RestException(HttpStatus.NOT_FOUND, "Game is not exist", "game");
        } else {
            item.setGame(game.get());
        }
        item.setReview(review.getReview());
        reviewRepository.save(item);
        log.info("The review has been updated");
    }
}
