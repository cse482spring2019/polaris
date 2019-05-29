package com.polaris.service;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Stop {

    @Id
    private String id;
    private String name;
    private String direction;
    private List<Image> images;
    private List<Tag> tags;
    private Integer yesAccessible;
    private Integer noAccessible;

    private final String[] TAG_LABELS = new String[]{
            "Tight Spaces",
            "Sharp Inclines",
            "Construction Nearby",
            "Low-Quality Sidewalk",
            "Overgrowing Foliage",
            "Lack of Elevator",
            "Broken Benches",
            "Insufficient Light"
    };

    public Stop () {}

    public Stop(String id, String name, String direction) {
        this.id = id;
        this.name = name;
        this.direction = direction;
        this.images = new LinkedList<>();
        this.tags = initializeTags();
        this.yesAccessible = 0;
        this.noAccessible = 0;
    }

    private List<Tag> initializeTags() {
        List<Tag> tags = new ArrayList<>(TAG_LABELS.length);
        for (String label : TAG_LABELS) {
            tags.add(new Tag(label));
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

    public Integer getYesAccessible() {
        return this.yesAccessible;
    }

    public void setYesAccessible(Integer yesAccessible) {
        this.yesAccessible = yesAccessible;
    }

    public Integer getNoAccessible() {
        return this.noAccessible;
    }

    public void setNoAccessible(Integer noAccessible) {
        this.noAccessible = noAccessible;
    }

    public void updateTagCount(String label, int count) {
        for (Tag tag : tags) {
            if (tag.getLabel().equals(label)) {
                tag.setCount(count);
                return;
            }
        }
        throw new IllegalArgumentException(String.format("Tag %s not found", label));
    }

    public void addImage(Image image) {
        images.add(image);
    }

    @Override
    public String toString() {
        return "Stop{" + id + "," + name + "," + direction + "}"
                + images + tags + "{" + yesAccessible + "," + noAccessible + "}";
    }
}
