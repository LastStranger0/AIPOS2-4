package com.iit.lab2.persist.request;


public class KeyRequest {
    private Long id;
    private String key;
    private String game;

    public String getKey() {
        return key;
    }

    public String getGame() {
        return game;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
