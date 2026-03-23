public class Node {
    // Instance variables

    private int name;
    private boolean marked;

    // Constructor

    /**
     * Initializes a new unmarked Node object with a name.
     * 
     * @param nodeName name of the node.
     */

    public Node(int nodeName) {
        this.name = nodeName;
        this.marked = false;
    }

    // Public methods

    /**
     * Sets the mark of the Node object with the given mark.
     * 
     * @param mark the mark to set for the node.
     */
    public void setMark(boolean mark) {
        this.marked = mark;
    }

    /**
     * Returns the mark of the Node object.
     * 
     * @return the mark of the node.
     */
    public boolean getMark() {
        return this.marked;
    }

    /**
     * Returns the name of the Node object.
     * 
     * @return the name of the node.
     */
    public int getName() {
        return this.name;
    }

    /**
     * Checks if the the name of two nodes are equal.
     * 
     * @param otherNode the other node to compare with.
     * @return true if both nodes have the same name; false otherwise.
     */
    public boolean equals(Node otherNode) {
        if (otherNode == null) {
            return false;
        }
        return this.name == otherNode.name;
    }
}
