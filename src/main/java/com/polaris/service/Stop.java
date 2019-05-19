package com.polaris.service;

import org.springframework.data.annotation.Id;

import java.util.LinkedList;
import java.util.List;

public class Stop {

    @Id
    private String id;
    private String name;
    private List<Image> images;
    private TagStore tagStore;

    public Stop () {}

    public Stop(String id) {
        this.id = id;
        this.images = new LinkedList<>();
        this.tagStore = new TagStore();
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public String setName(String name) {
        return this.name = name;
    }

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