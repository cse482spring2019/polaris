package com.polaris.service;

/**
 * Manages the information for a tag
 */
public class Tag {

    private String label;
    private Integer count;

    /**
     * Initializes a image with no information
     */
    public Tag() {
        this("");
    }

    public Tag(String label) {
        this.label = label;
        this.count = 0;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return label + "=" + count;
    }

}