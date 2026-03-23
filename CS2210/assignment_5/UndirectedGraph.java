import java.util.ArrayList;

public class UndirectedGraph {
    // Instance variables
    private int numNodes;
    private Node[] nodes;
    private ArrayList<Edge>[] adjacentList;

    // Constructor

    /**
     * Initializes a new UndirectedGraph object with the number of nodes.
     * 
     * @param n the number of nodes present
     */
    public UndirectedGraph(int n) {
        this.numNodes = n;
        this.nodes = new Node[n];
        this.adjacentList = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            this.nodes[i] = new Node(i);
            adjacentList[i] = new ArrayList<Edge>();
        }
    }

    // Public methods

    /**
     * Creates an edge object and inserts it inside the adjacency list.
     * 
     * @param u        first node object
     * @param v        second node object
     * @param edgeType type of edge
     * @throws GraphException if nodes are invalid (empty or exists in graph).
     */
    public void insertEdge(Node u, Node v, int edgeType) throws GraphException {
        // Check if both nodes are empty.
        if (u == null || v == null) {
            throw new GraphException("Node does not exist.");
        }

        // Get the name of the nodes.
        int uName = u.getName();
        int vName = v.getName();

        // Check if edge name is invalid.
        if (uName < 0 || uName >= numNodes || vName < 0 || vName >= numNodes) {
            throw new GraphException("Node does not exist");
        }

        // Check if edge already exists.
        for (Edge edge : this.adjacentList[uName]) {
            Node first = edge.firstEndpoint();
            Node second = edge.secondEndpoint();

            if ((first.equals(u) && second.equals(v)) ||
                    (first.equals(v) && second.equals(u))) {
                throw new GraphException("Edge already exists");
            }
        }

        // Creates the edge object.
        Edge newEdge = new Edge(u, v, edgeType);

        // Add edge to both adjacency lists.
        this.adjacentList[uName].add(newEdge);
        this.adjacentList[vName].add(newEdge);
    }

    /**
     * Gets the nodes that matches the given node.
     * 
     * @param u the given node
     * @return the node if exists.
     * @throws GraphException if the node does not exist.
     */
    public Node getNode(int u) throws GraphException {
        if (u < 0 || u >= numNodes) {
            throw new GraphException("Node does not exist");
        }
        return this.nodes[u];
    }

    /**
     * Gets the list storing all the edges incident on the given node.
     * 
     * @param u the given node
     * @return the list of edges incident to given node; null otherwise.
     * @throws GraphException if node is empty or does not exists
     */
    public ArrayList<Edge> incidentEdges(Node u) throws GraphException {
        if (u == null) {
            throw new GraphException("Node does not exist");
        }

        int uName = u.getName();

        if (uName < 0 || uName >= numNodes) {
            throw new GraphException("Node does not exist");
        }

        ArrayList<Edge> edges = adjacentList[uName];

        if (edges.isEmpty()) {
            return null;
        }

        return edges;
    }

    /**
     * Gets the edge connecting the given nodes.
     * 
     * @param u first node object
     * @param v second node object
     * @return the edge object connecting the nodes
     * @throws GraphException if nodes are empty, does not exist, or edge does not
     *                        exist.
     */
    public Edge getEdge(Node u, Node v) throws GraphException {
        if (u == null || v == null) {
            throw new GraphException("Node does not exist");
        }

        int uName = u.getName();
        int vName = v.getName();

        if (uName < 0 || uName >= numNodes || vName < 0 || vName >= numNodes) {
            throw new GraphException("Node does not exist");
        }

        // Search for edge in u's adjacency list
        for (Edge edge : this.adjacentList[uName]) {
            Node first = edge.firstEndpoint();
            Node second = edge.secondEndpoint();

            if ((first.equals(u) && second.equals(v)) ||
                    (first.equals(v) && second.equals(u))) {
                return edge;
            }
        }

        throw new GraphException("Edge does not exist");
    }

    /**
     * Checks if the given nodes are adjacent (have edges incident to each other).
     * 
     * @param u fisrt node object
     * @param v second node object
     * @return true if the nodes are adjacent; false otherwise.
     * @throws GraphException if the nodes do not exist.
     */
    public boolean areAdjacent(Node u, Node v) throws GraphException {
        if (u == null || v == null) {
            throw new GraphException("Node does not exist");
        }

        int uName = u.getName();
        int vName = v.getName();

        if (uName < 0 || uName >= numNodes || vName < 0 || vName >= numNodes) {
            throw new GraphException("Node does not exist");
        }

        // Check if there's an edge between u and v
        for (Edge edge : this.adjacentList[uName]) {
            Node first = edge.firstEndpoint();
            Node second = edge.secondEndpoint();

            if ((first.equals(u) && second.equals(v)) ||
                    (first.equals(v) && second.equals(u))) {
                return true;
            }
        }

        return false;
    }
}
