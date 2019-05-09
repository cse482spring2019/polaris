package com.polaris;

import org.springframework.data.annotation.Id;

import java.util.LinkedList;
import java.util.List;

public class Stop {
    @Id
    private final long id;
    private final List<Review> reviews;

    public Stop(long id) {
        this.id = id;
        this.reviews = new LinkedList<>();
    }

    public long getId() {
        return id;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    @Override
    public String toString() {
        return "Stop={" +
                    "id=" + id + "," +
                    "reviews=" + reviews + "," +
                "}";
    }
}
