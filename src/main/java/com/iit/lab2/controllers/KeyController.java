package com.iit.lab2.controllers;

import com.iit.lab2.persist.entity.Key;
import com.iit.lab2.persist.request.KeyRequest;
import com.iit.lab2.persist.response.RestException;
import com.iit.lab2.service.KeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class KeyController {

    private final KeyService keyService;

    @Autowired
    public KeyController(KeyService keyService) {
        this.keyService = keyService;
    }

    @GetMapping("/keys")
    public List<Key> getKeys() {
        return keyService.findAll();
    }

    @GetMapping("/keys/{id}")
    public Key getKey(@PathVariable Long id) throws RestException {
        return keyService.findById(id).get();
    }

    @DeleteMapping("/keys/delete/{id}")
    public void deleteKey(@PathVariable Long id) throws RestException {
        keyService.delete(id);
    }

    @GetMapping("/keys/update/{id}")
    public Key updateKey(@PathVariable Long id) throws RestException {
        return keyService.findById(id).get();
    }

    @PostMapping("/keys/update/{id}")
    public void update(@PathVariable Long id, @RequestBody KeyRequest key) throws RestException {
        key.setId(id);
        keyService.update(key);
    }

    @PostMapping("/keys/create")
    public void createNewKey(@RequestBody KeyRequest key) throws RestException {
        keyService.create(key);
    }
}
