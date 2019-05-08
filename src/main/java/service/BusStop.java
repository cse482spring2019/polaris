import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class BusStop {

    private final long id;
    private final List<Review> reviews;

    /**
     * Initializes a new BusStop with no reviews
     */
    @Entity
    public BusStop(long id) {
        this.id = id;
        this.reviews = new LinkedList<>();
    }

    /**
     * Returns the unique id of this stop
     *
     * @return the id of this stop
     */
    public long getId() {
        return id;
    }

    /**
     * Returns a List of all Reviews made for this stop
     *
     * @return list of this stop's reviews
     */
    public List<Review> getReviews() {
        return reviews;
    }

    /**
     * Adds a new Review with the given imageUrl and text to this BusStop
     *
     * @param imageUrl url of the review's image (null for no image)
     * @param text text body of the new review
     */
    public void addReview(String imageUrl, String text) {
        reviews.add(new Review(imageUrl, text));
    }
}
