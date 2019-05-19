package com.polaris.service;

import java.util.Map;

public class DtoTags {
    private Map<String, Integer> tags;

    public DtoTags() { }

    public Map<String, Integer> getTags() {
        return tags;
    }

    public void setTags(Map<String, Integer> tags) {
        this.tags = tags;
    }
}