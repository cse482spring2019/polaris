package com.polaris.service;

public class DtoAddImage {
    private String url;
    private String altText;
    private String date;

    public DtoAddImage() {
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }

    public String getAltText() {
        return this.altText;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return this.date;
    }
}