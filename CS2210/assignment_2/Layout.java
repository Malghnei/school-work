public class Layout {
    // Instance variables
    private String boardLayout;
    private int score;

    /**
     * Constructor for Layout class
     * @param boardLayout The string representation of the board
     * @param score Integer score (0, 1, 2, or 3)
     */
    public Layout(String boardLayout, int score) {
        this.boardLayout = boardLayout;
        this.score = score;
    }

    /**
     * Getter for board layout string
     * @return The string representation of the board
     */
    public String getBoardLayout() {
        return this.boardLayout;
    }

    /**
     * Getter for score
     * @return The integer score
     */
    public int getScore() {
        return this.score;
    }
}
