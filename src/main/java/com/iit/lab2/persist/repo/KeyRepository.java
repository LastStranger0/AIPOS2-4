package com.iit.lab2.persist.repo;

import com.iit.lab2.persist.entity.Game;
import com.iit.lab2.persist.entity.Key;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KeyRepository extends CrudRepository<Key, Long> {
    Optional<Key> findByKey(String key);

    void deleteByGame(Game game);

    Optional<Game> findByGame(Game game);
}
