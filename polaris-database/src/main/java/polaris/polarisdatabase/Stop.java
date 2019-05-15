package polaris.polarisdatabase;

import org.springframework.data.annotation.Id;

import java.util.LinkedList;
import java.util.List;

public class Stop {

    @Id
    private int id;
    private String name;
    private List<String> imageUrls;
    private TagStore tagStore;

    public Stop(int id) {
        this.id = id;
        this.imageUrls = new LinkedList<>();
        this.tagStore = new TagStore();
    }

    public void setId(int id) {
        this.id = id;
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