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
    private TagStore tags;
    private Integer yesAccessible;
    private Integer noAccessible;

    public Stop () {}

    public Stop(String id, String name, String direction) {
        this.id = id;
        this.name = name;
        this.direction = direction;
        this.images = new LinkedList<>();
        this.tags = new TagStore();
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

    public TagStore getTags() {
        return this.tags;
    }

    public void setTags(TagStore tags) {
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

    public void updateTagCount(String tag, int count) {
        tags.updateTagCount(tag, count);
    }

    public void addImage(Image image) {
        images.add(image);
    }

    @Override
    public String toString() {
        return "Stop{" + id + "}";
    }
}
