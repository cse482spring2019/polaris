package com.polaris;

import org.springframework.data.annotation.Id;

import java.util.LinkedList;
import java.util.List;

public class Stop {

    @Id
    private final int id;
    private final String name;
    private List<String> imageUrls;
    private TagStore tagStore;

    public Stop(String name) {
        this.id = name.hashCode();
        this.name = name;
        this.imageUrls = new LinkedList<>();
        this.tagStore = new TagStore();
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return name;
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
        return "Stop{" +
                "name='" + name + '\'' +
                ", images=" + imageUrls +
                ", tags=" + tagStore +
                "}";
    }
}