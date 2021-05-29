package com.iit.lab2.persist.response;

import com.iit.lab2.persist.entity.Game;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GameResponse {
    private Long id;
    private String name;
    private Double price;
    private String description;
    private LocalDate date;
    private String linkMainImage;
    private List<String> linksImages;

    public GameResponse(Game game) {
        id = game.getId();
        name = game.getName();
        price = game.getPrice();
        description = game.getDescription();
        date = game.getDate();
        int PORT = 8082;
        linkMainImage = String.format("http://localhost:%d/games/%d/mainImage", PORT, id);
        linksImages = new ArrayList<>();
        for (int i = 0; i < game.getLinksImages().size(); i++) {
            linksImages.add(String.format("http://localhost:%d/games/%d/screenshot/%d", PORT, id, i));
        }
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void addScreenshot(String screenshot) {
        linksImages.add(screenshot);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getLinkMainImage() {
        return linkMainImage;
    }

    public void setLinkMainImage(String linkMainImage) {
        this.linkMainImage = linkMainImage;
    }

    public List<String> getLinksImages() {
        return linksImages;
    }

    public void setLinksImages(List<String> linksImages) {
        this.linksImages = linksImages;
    }
}
