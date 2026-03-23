public class Edge {
    // Instance variables

    Node firstEndpoint, secondEndpoint;
    int type;

    // Constructor

    /**
     * Initializes a new Edge object with the two endpoints assigned.
     * 
     * @param u
     * @param v
     * @param edgeType
     */
    public Edge(Node u, Node v, int edgeType) {
        this.firstEndpoint = u;
        this.secondEndpoint = v;
        this.type = edgeType;
    }

    // Public methods

    /**
     * Returns the first endpoint of the edge.
     * 
     * @return a Node object.
     */
    public Node firstEndpoint() {
        return this.firstEndpoint;
    }

    /**
     * Returns the second endpoint of the edge.
     * 
     * @return a Node object.
     */
    public Node secondEndpoint() {
        return this.secondEndpoint;
    }

    /**
     * Returns the type of the edge.
     * 
     * @return the type of the edge.
     */
    public int getType() {
        return this.type;
    }

    /**
     * Sets the type of the edge with the given edge.
     * 
     * @param newType new type for the edge.
     */
    public void setType(int newType) {
        this.type = newType;
    }

    /**
     * Checks if the two endpoints of the edges are equal.
     * 
     * @param otherEdge the other edge to compare with.
     * @return true if both edges are the same; false otherwise.
     */
    public boolean equals(Edge otherEdge) {
        if (otherEdge == null) {
            return false;
        }

        return (((this.firstEndpoint.equals(otherEdge.firstEndpoint))
                && (this.secondEndpoint.equals(otherEdge.secondEndpoint)))
                ||
                ((this.firstEndpoint.equals(otherEdge.secondEndpoint)
                        && this.secondEndpoint.equals(otherEdge.firstEndpoint))));
    }
}
