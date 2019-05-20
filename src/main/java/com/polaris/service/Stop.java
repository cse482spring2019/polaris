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
    private TagStore tagStore;

    public Stop () {}

    public Stop(String id, String name, String direction) {
        this.id = id;
        this.name = name;
        this.direction = direction;
        this.images = new LinkedList<>();
        this.tagStore = new TagStore();
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

    public List<Image> setImages(List<Image> images) {
        return this.images = images;
    }

    public TagStore getTags() {
        return this.tagStore;
    }

    public TagStore setTagStore(TagStore tagStore) {
        return this.tagStore = tagStore;
    }

    public void updateTagCount(String tag, int count) {
        tagStore.updateTagCount(tag, count);
    }

    public void addImage(Image image) {
        images.add(image);
    }

    @Override
    public String toString() {
        return "Stop{" + id + "}";
    }
}