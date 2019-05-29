package com.polaris.service;

public class DtoImage {
    private String imageUrl;
    private String altText;
    private String dateUploaded;

    public DtoImage() {
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }

    public String getAltText() {
        return this.altText;
    }

    public void setDateUploaded(String dateUploaded) {
        this.dateUploaded = dateUploaded;
    }

    public String getDateUploaded() {
        return this.dateUploaded;
    }
}