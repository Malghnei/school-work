import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

public class Solve {
    // Instance variables

    private UndirectedGraph graph;
    private Node entrance;
    private Node exit;
    private int maxBlastBombs;
    private int maxMeltBombs;
    private int width;
    private int length;

    // Constructor

    /**
     * Initializes a new Solve object using the input file.
     * 
     * @param inputFile the name of the input file
     * @throws LabyrinthException if file does not exist, formatted wrong, or other.
     */
    public Solve(String inputFile) throws LabyrinthException {
        try {
            // Open the input file.
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));

            // Read first 5 lines.
            int scale = Integer.parseInt(reader.readLine().trim());
            this.width = Integer.parseInt(reader.readLine().trim());
            this.length = Integer.parseInt(reader.readLine().trim());
            this.maxBlastBombs = Integer.parseInt(reader.readLine().trim());
            this.maxMeltBombs = Integer.parseInt(reader.readLine().trim());

            // Calculate total number of nodes.
            int numNodes = width * length;
            graph = new UndirectedGraph(numNodes);

            // Read the labyrinth layout.
            ArrayList<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            reader.close();

            // Parse the labyrinth and build the graph.
            parseLabyrinth(lines);

        } catch (FileNotFoundException e) {
            throw new LabyrinthException("Input file not found");
        } catch (IOException e) {
            throw new LabyrinthException("Error reading input file");
        } catch (Exception e) {
            throw new LabyrinthException("Error parsing labyrinth: " + e.getMessage());
        }
    }

    // Public methods

    /**
     * Gets the graph object.
     * 
     * @return graph object
     */
    public UndirectedGraph getGraph() {
        return graph;
    }

    /**
     * Finds the exit through Depth-First Search (DFS).
     * 
     * @return the iterator path; null otherwise
     */
    public Iterator<Node> findExit() {
        // Check if the entrance or exit is empty.
        if (entrance == null || exit == null) {
            return null;
        }

        // Use DFS with backtracking to find path.
        Stack<Node> path = new Stack<>();
        boolean found = dfs(entrance, path, 0, 0);

        if (found) {
            return path.iterator();
        }

        return null;
    }

    // Private helper methods

    /**
     * Processes the labyrinth part of the input file into an undirected graph
     * 
     * @param lines
     * @throws GraphException
     */
    private void parseLabyrinth(ArrayList<String> lines) throws GraphException {
        // Lines alternate between rooms (P) and walls (H and V).
        // Process room lines and wall lines.
        for (int row = 0; row < length; row++) {
            int roomLineIndex = row * 2;

            if (roomLineIndex >= lines.size())
                break;

            String roomLine = lines.get(roomLineIndex);

            // Process rooms in this row.
            for (int col = 0; col < width; col++) {
                int charIndex = col * 2;
                if (charIndex >= roomLine.length())
                    break;

                char roomChar = roomLine.charAt(charIndex);
                int nodeNum = row * width + col;

                // Check for entrance or exit.
                if (roomChar == 'e') {
                    entrance = graph.getNode(nodeNum);
                } else if (roomChar == 'x') {
                    exit = graph.getNode(nodeNum);
                }

                // Process horizontal edge (to the right).
                if (col < width - 1 && charIndex + 1 < roomLine.length()) {
                    char horizChar = roomLine.charAt(charIndex + 1);
                    int rightNode = row * width + (col + 1);
                    addEdgeIfValid(nodeNum, rightNode, horizChar, true);
                }
            }

            // Process vertical edges (walls between rows).
            if (row < length - 1 && roomLineIndex + 1 < lines.size()) {
                String wallLine = lines.get(roomLineIndex + 1);

                for (int col = 0; col < width; col++) {
                    int charIndex = col * 2;
                    if (charIndex >= wallLine.length())
                        break;

                    char vertChar = wallLine.charAt(charIndex);
                    int currentNode = row * width + col;
                    int belowNode = (row + 1) * width + col;
                    addEdgeIfValid(currentNode, belowNode, vertChar, false);
                }
            }
        }
    }

    /**
     * Performs a Depth-First search algorithm, based on the input file.
     * 
     * @param current        the current node
     * @param path           the path to be found
     * @param blastBombsUsed the number of blast bombs used
     * @param meltBombsUsed  the number of melt bombs used
     * @return true if a path can be deduced; false otherwise.
     */
    private boolean dfs(Node current, Stack<Node> path, int blastBombsUsed, int meltBombsUsed) {
        // Mark current node and add to path
        current.setMark(true);
        path.push(current);

        // Check if we reached the exit
        if (current.equals(exit)) {
            return true;
        }

        try {
            // Get all incident edges
            ArrayList<Edge> edges = graph.incidentEdges(current);

            if (edges != null) {
                for (Edge edge : edges) {
                    // Get the other endpoint
                    Node neighbor = edge.firstEndpoint().equals(current) ? edge.secondEndpoint() : edge.firstEndpoint();

                    // Skip if already visited
                    if (neighbor.getMark()) {
                        continue;
                    }

                    int edgeType = edge.getType();
                    int newBlastBombs = blastBombsUsed;
                    int newMeltBombs = meltBombsUsed;

                    // Check if we can traverse this edge
                    boolean canTraverse = false;

                    if (edgeType == 1) {
                        // Corridor - always can traverse
                        canTraverse = true;
                    } else if (edgeType == 2) {
                        // Brick wall - needs 1 blast bomb
                        if (blastBombsUsed < maxBlastBombs) {
                            canTraverse = true;
                            newBlastBombs++;
                        }
                    } else if (edgeType == 3) {
                        // Rock wall - needs 2 blast bombs
                        if (blastBombsUsed + 2 <= maxBlastBombs) {
                            canTraverse = true;
                            newBlastBombs += 2;
                        }
                    } else if (edgeType == 4) {
                        // Metal wall - needs 1 melt bomb
                        if (meltBombsUsed < maxMeltBombs) {
                            canTraverse = true;
                            newMeltBombs++;
                        }
                    }

                    if (canTraverse) {
                        if (dfs(neighbor, path, newBlastBombs, newMeltBombs)) {
                            return true;
                        }
                    }
                }
            }
        } catch (GraphException e) {
            // Should not happen
            e.printStackTrace();
        }

        // Backtrack - unmark and remove from path
        current.setMark(false);
        path.pop();

        return false;
    }

    /**
     * Creates an edge object based on the input file chars.
     * 
     * @param node1        first node object
     * @param node2        second node object
     * @param wallChar     character of the wall
     * @param isHorizontal bool check if horizontal
     * @throws GraphException if nodes are invalid.
     */
    private void addEdgeIfValid(int node1, int node2, char wallChar, boolean isHorizontal) throws GraphException {
        int edgeType = -1;

        // Determine edge type based on character
        if (isHorizontal) {
            switch (wallChar) {
                case '-':
                    edgeType = 1;
                    break; // corridor
                case 'b':
                    edgeType = 2;
                    break; // brick wall
                case 'r':
                    edgeType = 3;
                    break; // rock wall
                case 'm':
                    edgeType = 4;
                    break; // metal wall
                case '*':
                    return; // solid stone - no edge
            }
        } else {
            switch (wallChar) {
                case '|':
                    edgeType = 1;
                    break; // corridor
                case 'B':
                    edgeType = 2;
                    break; // brick wall
                case 'R':
                    edgeType = 3;
                    break; // rock wall
                case 'M':
                    edgeType = 4;
                    break; // metal wall
                case '*':
                    return; // solid stone - no edge
            }
        }

        if (edgeType != -1) {
            Node u = graph.getNode(node1);
            Node v = graph.getNode(node2);
            graph.insertEdge(u, v, edgeType);
        }
    }

}