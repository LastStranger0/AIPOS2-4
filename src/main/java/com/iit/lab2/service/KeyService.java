package com.iit.lab2.service;

import com.iit.lab2.persist.entity.Game;
import com.iit.lab2.persist.entity.Key;
import com.iit.lab2.persist.repo.GameRepository;
import com.iit.lab2.persist.repo.KeyRepository;
import com.iit.lab2.persist.request.KeyRequest;
import com.iit.lab2.persist.response.RestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class KeyService {
    private final KeyRepository keyRepository;
    private final GameRepository gameRepository;

    @Autowired
    public KeyService(KeyRepository keyRepository, GameRepository gameRepository) {
        this.keyRepository = keyRepository;
        this.gameRepository = gameRepository;
    }

    public void create(KeyRequest key) throws RestException {
        Key newKey = new Key();
        newKey.setKey(key.getKey());
        Optional<Game> game = gameRepository.findByName(key.getGame());
        if (!game.isPresent()) {
            throw new RestException(HttpStatus.NOT_FOUND, "Game is not exist", "game");
        }
        newKey.setGame(game.get());
        keyRepository.save(newKey);
        log.info("Key was created");
    }

    public List<Key> findAll() {
        Iterable<Key> source = keyRepository.findAll();
        List<Key> target = new ArrayList<>();
        source.forEach(target::add);
        log.info("{} addresses was found", target.size());
        return target;
    }

    public void delete(Long id) throws RestException {
        findById(id);
        keyRepository.deleteById(id);
        log.info("The key has been removed");
    }

    public Optional<Key> findById(Long id) throws RestException {
        Optional<Key> key = keyRepository.findById(id);
        if (key.isPresent()) {
            log.info("Key with id {} was found", id);
        } else {
            log.info("Key with id {} wasn't found", id);
            throw new RestException(HttpStatus.NOT_FOUND, "Key not found", "key");
        }
        return key;
    }

    public void update(KeyRequest key) throws RestException {
        Optional<Key> row = findById(key.getId());
        Key item = null;
        if (row.isPresent()) {
            item = row.get();
            if (!item.getKey().equals(key.getKey())) {
                if (keyRepository.findByKey(key.getKey()).isPresent()) {
                    throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, "Key is busy", "key");
                }
            }
        } else {
            throw new RestException(HttpStatus.NOT_FOUND, "Not found!", "key");
        }
        if (!"".equals(key.getKey())) {
            item.setKey(key.getKey());
        }
        Optional<Game> game = gameRepository.findByName(key.getGame());
        if (!game.isPresent()) {
            throw new RestException(HttpStatus.NOT_FOUND, "Game is not exist", "game");
        }
        item.setGame(game.get());
        keyRepository.save(item);
        log.info("The key has been updated");
    }
}