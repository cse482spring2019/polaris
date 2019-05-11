package com.polaris;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages the tags for a single bus stop
 */
public class TagStore {

    private final Map<String, Integer> tagStore;

    public static final String SPACE =        "Tight Spaces";
    public static final String INCLINE =      "Sharp Inclines";
    public static final String CONSTRUCTION = "Construction Nearby";
    public static final String SIDEWALK =     "Low-Quality Sidewalk";
    public static final String SHRUBS =       "Overgrowing Foliage";
    public static final String ELEVATOR =     "Lack of Elevator";
    public static final String BENCHES =      "Broken Benches";
    public static final String DARKNESS =     "Insufficient Light";

    /**
     * Initializes a TagStore with no selected tags
     */
    public TagStore() {
        this.tagStore = new HashMap<>();
        for (String tag : new String[] {SPACE, INCLINE, CONSTRUCTION, SIDEWALK,
                                        SHRUBS, ELEVATOR, BENCHES, DARKNESS}) {
            tagStore.put(tag, 0);
        }
    }

    /**
     * Increases the value of the given tag by one
     *
     * @param tag tag to be incremented
     */
    public void incrementTag(String tag) {
        if (!tagStore.containsKey(tag)) {
            throw new IllegalArgumentException("invalid tag supplied");
        }
        tagStore.put(tag, tagStore.get(tag) + 1);
    }

    /**
     * Decreases the value of the given tag by one
     *
     * @param tag tag to be decremented
     */
    public void decrementTag(String tag) {
        if (!tagStore.containsKey(tag)) {
            throw new IllegalArgumentException("invalid tag supplied");
        }
        tagStore.put(tag, Math.max(tagStore.get(tag) - 1, 0));
    }

    @Override
    public String toString() {
        return tagStore.toString();
    }

}
