public class MultimediaItem {
    // Instance Variables.
    private String content;
    private int type;

    // Constructor.

    /**
     * Initializes a new MultimediaItem object.
     * 
     * @param newContent Name of the content.
     * @param newType    Type of the content.
     */
    public MultimediaItem(String newContent, int newType) {
        this.content = newContent;
        this.type = newType;
    }

    // Public Methods.

    /**
     * Returns the content of this node.
     * 
     * @return the name of the content.
     */
    public String getContent() {
        return this.content;
    }

    /**
     * Returns the type of this node.
     * 
     * @return the type of the content.
     */
    public int getType() {
        return this.type;
    }
}