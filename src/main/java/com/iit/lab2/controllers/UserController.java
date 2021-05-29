package com.iit.lab2.controllers;

import com.iit.lab2.persist.request.UserRequest;
import com.iit.lab2.persist.response.RestException;
import com.iit.lab2.persist.response.UserResponse;
import com.iit.lab2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin("*")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<UserResponse> getUsers() {
        return userService.findAll();
    }

    @GetMapping("/users/{id}")
    public UserResponse getUser(@PathVariable Long id) throws RestException {
        return new UserResponse(userService.findById(id).get());
    }

    @PostMapping("/users/create")
    public void registerNewUser(@RequestBody UserRequest user) throws RestException {
        userService.create(user);
    }


    @DeleteMapping("/users/delete/{id}")
    public void deleteUser(@PathVariable Long id) throws RestException {
        userService.delete(id);
    }

    @GetMapping("/users/update/{id}")
    public UserResponse update(@PathVariable Long id) throws RestException {
        return new UserResponse(userService.findById(id).get());
    }

    @PostMapping("/users/update/{id}")
    public void updateUser(@PathVariable Long id, @RequestBody UserRequest user) throws RestException {
        user.setId(id);
        userService.update(user);
    }

    @GetMapping("/user/{id}/mainImage")
    public byte[] getMainImage(@PathVariable Long id) throws RestException {
        return userService.downloadMainImage(id);
    }

    @PostMapping(
            path = "/user/{id}/mainImage/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void uploadMainImage(@PathVariable Long id,
                                @RequestParam("file") MultipartFile file){
        userService.uploadMainImage(id, file);
    }
}
