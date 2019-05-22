package com.polaris.service;

public class Tag {
    private String name;
    private int count;

    public Tag() {
        this("");
    }

    public Tag(String name) {
        this.name = name;
        this.count = 0;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return name + "=" + count;
    }
}
