package com.polaris.service;

import org.springframework.data.annotation.Id;

import java.util.LinkedList;
import java.util.List;

public class Stop {

    @Id
    private String id;
    private String name;
    private List<String> imageUrls;
    private TagStore tagStore;

    public Stop () {}

    public Stop(String id) {
        this.id = id;
        this.imageUrls = new LinkedList<>();
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

    public List<String> getImageUrls() {
        return this.imageUrls;
    }

    public List<String> setImageUrls(List<String> imageUrls) {
        return this.imageUrls = imageUrls;
    }

    public TagStore getTags() {
        return this.tagStore;
    }

    public TagStore setTagStore(TagStore tagStore) {
        return this.tagStore = tagStore;
    }

    public void incrementTag(String tag) {
        tagStore.incrementTag(tag);
    }

    public void decrementTag(String tag) {
        tagStore.decrementTag(tag);
    }

    public void addImage(String imageUrl) {
        imageUrls.add(imageUrl);
    }

    @Override
    public String toString() {
        return "Stop{" + id + "}";
    }
}