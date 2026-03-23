import java.util.ArrayList;

public class Data {
    private String name;
    private ArrayList<MultimediaItem> media;

    // Constructor.

    /**
     * Initializes a new Data object with a name and an empty media list.
     * 
     * @param newName name of the Data.
     */
    public Data(String newName) {
        this.name = newName;
        this.media = new ArrayList<MultimediaItem>();
    }

    // Public Methods.

    /**
     * Adds newItem to the media list of the object.
     * 
     * @param newItem name of the item.
     */
    public void add(MultimediaItem newItem) {
        this.media.add(newItem);
    }

    /**
     * Getter method that returns the name attribute of this object.
     * 
     * @return Name of the Data object.
     */
    public String getName() {
        return this.name;
    }

    //
    /**
     * Returns the media list stored in this object.
     * 
     * @return the media list of the Data object.
     */
    public ArrayList<MultimediaItem> getMedia() {
        return this.media;
    }
}