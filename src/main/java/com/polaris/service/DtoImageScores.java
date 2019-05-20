package com.polaris.service;

public class DtoImageScores {
    private String url;
    private Integer up;
    private Integer down;

    public DtoImageScores() {
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUp(Integer up) {
        this.up = up;
    }

    public Integer getUp() {
        return this.up;
    }

    public void setDown(Integer down) {
        this.down = down;
    }

    public Integer getDown() {
        return this.down;
    }
}