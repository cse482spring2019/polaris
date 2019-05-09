package com.polaris;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Review {

    public static final String NO_IMAGE = "NO_IMAGE";

    private final String date;
    private final String imageUrl;
    private final String text;

    public Review(String imageUrl, String text) {
        this.date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
        this.imageUrl = imageUrl == null ? NO_IMAGE : imageUrl;
        this.text = text;
    }

    @Override
    public String toString() {
        return "Review={" +
                    "date=" + date + "," +
                    "imageUrl=" + imageUrl + "," +
                    "text-" + text +
                "}";
    }
}
