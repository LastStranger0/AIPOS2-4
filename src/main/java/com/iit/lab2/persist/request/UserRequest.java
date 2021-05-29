package com.iit.lab2.persist.request;

import com.iit.lab2.persist.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public class UserRequest {
    private Long id;
    private String email;
    private String username;
    private String password;
    private LocalDate date;
    private MultipartFile linkImage;

    public UserRequest() {
    }

    public UserRequest(User user) {
        id = user.getId();
        email = user.getEmail();
        username = user.getUsername();
        password = user.getPassword();
        date = user.getDate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public MultipartFile getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(MultipartFile linkImage) {
        this.linkImage = linkImage;
    }
}
