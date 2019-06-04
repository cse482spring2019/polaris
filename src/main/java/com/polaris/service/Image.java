package com.polaris.service;

/**
 * Manages the information for an image
 */
public class Image {

    private String imageUrl;
    private String altText;
    private String dateUploaded;

    /**
     * Initializes a image with no information
     */
    public Image() {
        imageUrl = altText = dateUploaded = "";
    }

    public Image(DtoImage dtoImage) {
        this.imageUrl = dtoImage.getImageUrl();
        this.altText = dtoImage.getAltText();
        this.dateUploaded = dtoImage.getDateUploaded();
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

    @Override
    public String toString() {
        return imageUrl + " " + altText + " " + dateUploaded;
    }

}