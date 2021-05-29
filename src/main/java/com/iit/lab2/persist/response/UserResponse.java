package com.iit.lab2.persist.response;

import com.iit.lab2.persist.entity.User;

import java.time.LocalDate;

public class UserResponse {
    private Long id;
    private String email;
    private String username;
    private String password;
    private LocalDate date;
    private String linkImage;

    public UserResponse() {
    }

    public UserResponse(User user) {
        id = user.getId();
        email = user.getEmail();
        username = user.getUsername();
        password = user.getPassword();
        date = user.getDate();
        final int PORT = 8082;
        linkImage = String.format("http://localhost:%d/user/%d/mainImage", PORT, id);
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

    public String getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }
}
