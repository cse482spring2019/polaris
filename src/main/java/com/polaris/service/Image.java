package com.polaris.service;

/**
 * Manages the information for an image
 */
public class Image {

    private String imageUrl;
    private String altText;
    private String dateUploaded;
    // Higher values means the image was more helpful
    private int score;
    // Higher values means the image is more likely to be outdated
    private int outdatedScore;

    /**
     * Initializes a image with no information
     */
    public Image() {
        imageUrl = altText = dateUploaded = "";
        score = outdatedScore = 0;
    }

    public Image(DtoImage dtoImage) {
        this.imageUrl = dtoImage.getUrl();
        this.altText = dtoImage.getAltText();
        this.dateUploaded = dtoImage.getDate();
        this.score = 0;
        this.outdatedScore = 0;
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

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return this.score;
    }

    public void setOutdatedScore(int outdatedScore) {
        this.outdatedScore = outdatedScore;
    }

    public int getOutdatedScore() {
        return this.outdatedScore;
    }

    @Override
    public String toString() {
        return imageUrl + " " + altText + " " + dateUploaded + " " + score + " " + outdatedScore;
    }

}