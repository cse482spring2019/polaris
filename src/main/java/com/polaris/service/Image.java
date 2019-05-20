package com.polaris.service;

/**
 * Manages the information for an image
 */
public class Image {

    private String url;
    private String altText;
    private String date;
    private int up;
    private int down;

    /**
     * Initializes a image with no information
     */
    public Image() {
        url = altText = date = "";
        up = down = 0;
    }

    public Image(DtoAddImage dtoImage) {
        this.url = dtoImage.getUrl();
        this.altText = dtoImage.getAltText();
        this.date = dtoImage.getDate();
        this.up = 0;
        this.down = 0;
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

    public void setUp(int up) {
        this.up = up;
    }

    public int getUp() {
        return this.up;
    }

    public void setDown(int down) {
        this.down = down;
    }

    public int getDown() {
        return this.down;
    }

    @Override
    public String toString() {
        return url + " " + altText + " " + date + " " + up + " " + down;
    }

}