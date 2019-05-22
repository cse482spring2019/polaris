package com.polaris.service;

import org.springframework.data.annotation.Id;

import java.util.LinkedList;
import java.util.List;

public class Stop {

    @Id
    private String id;
    private String name;
    private String direction;
    private List<Image> images;
    private List<Tag> tags;

    private static final String[] TAG_NAMES = new String[]{
            "Tight Spaces",
            "Sharp Inclines",
            "Construction Nearby",
            "Low-Quality Sidewalk",
            "Overgrowing Foliage",
            "Lack of Elevator",
            "Broken Benches",
            "Insufficient Light"
    };

    public Stop () {
        this.tags = initializeTags();
    }

    public Stop(String id, String name, String direction) {
        this.id = id;
        this.name = name;
        this.direction = direction;
        this.images = new LinkedList<>();
        this.tags = initializeTags();
    }

    private List<Tag> initializeTags() {
        List<Tag> tags = new LinkedList<>();
        for (String name : TAG_NAMES) {
            tags.add(new Tag(name));
        }
        return tags;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setName(String name) { this.name = name; }

    public String getName() { return this.name; }

    public void setDirection(String direction) { this.direction = direction; }

    public String getDirection() { return this.direction; }

    public List<Image> getImages() {
        return this.images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Tag> getTags() {
        return this.tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void updateTagCount(String tagName, int count) {
        for (Tag tag : this.tags) {
            if (tag.getName().equals(tagName)) {
                tag.setCount(count);
                return;
            }
        }
        throw new IllegalArgumentException("tag name invalid");
    }

    public void addImage(Image image) {
        images.add(image);
    }

    @Override
    public String toString() {
        return "Stop{" + id + " " + name + " " + direction + "}" + images + tags;
    }
}