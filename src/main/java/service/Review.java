import java.text.SimpleDateFormat;
import java.util.Date;

public class Review {

    private final String date;          // date the review was posted
    private final String imageUrl;    // url of the image (null if no image)
    private final String text;        // text of the review

    /**
     * Initializes a new Review with the given imageUrl and text,
     * using the current date to timestamp of the Review.
     *
     * @param imageUrl url of the image (null for no image)
     * @param text text of the review body
     */
    public Review(String imageUrl, String text) {
        this.imageUrl = imageUrl;
        this.text = text;
        this.date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
    }

    /**
     * Returns the date the Review was created
     *
     * @return date of the Review's creation
     */
    public String getDate() {
        return this.date;
    }

    /**
     * Returns the url to the Review's image
     *
     * @return returns the url of the image or null for no image
     */
    public String getImageUrl() {
        return this.imageUrl;
    }

    /**
     * Returns the text body of the Review
     *
     * @return text body of the Review
     */
    public String getText() {
        return this.text;
    }
}
