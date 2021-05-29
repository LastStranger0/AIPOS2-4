package com.iit.lab2.persist.repo;

import com.iit.lab2.persist.entity.Game;
import com.iit.lab2.persist.entity.Review;
import com.iit.lab2.persist.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {
    void deleteByGame(Game game);

    void deleteByAuthor(User author);

    Optional<Review> findByAuthor(User author);

    Optional<Review> findByGame(Game game);
}
