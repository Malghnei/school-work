/*
CS 1027 - Assignment 3
Name: Malik Alghneimin
Student Number: 
Email: malghnei@uwo.ca
Created: March 8, 2025
*/
public class CampusWalk {
    // Class variables
    private Map map;

    /**
     * Initializes a new CampusWalk object and opens a new map using the given file name.
     * @param filename The name of the map file.
     * @param showMap Check whether to show the map.
     */
    public CampusWalk(String filename, boolean showMap) {
        // Try-Catch statement for catching errors.
        try {
            // Create a new map object.
            map = new Map(filename);
            // Check if to show map.
            if (showMap == true) {
                map.showGUI();
            }
        } catch (Exception e) {
            // Print when error occurrred (Usually invalid file name).
            System.out.println("Error occurred");
        }
    }

    /**
     * A method for checking and counting adjacent cells of the current cell that are Goose cells.
     * @param cell The current cell.
     * @return The number of Goose cells adjacent to the current cell.
     */
    public int neighbourGooseCount(Hexagon cell) {
        // Counter variable.
        int gooseCells = 0;
        // For loop to go through all the adjacent cells.
        for (int i = 0; i < 6; i++) {
            // Variable to hold the current adjacent cell.
            Hexagon currNeighbour = cell.getNeighbour(i);
            // Check if adjacent cell is not null.
            if (currNeighbour != null) {
                // Check if adjacent cell is a Goose cell.
                if (currNeighbour.isGooseCell()) {
                    gooseCells++; // Increase the counter by one.
                }
            }
        }
        return gooseCells; // Return the number of adjacent Goose cells.
    }

    /**
     * A method for finding the best cell to choose using an algoritms with conditions.
     * @param cell The current cell.
     * @return The best cell adjacent to the current cell.
     */
    public Hexagon findBest(Hexagon cell) {
        // Local Variables.
        Hexagon[] priorityList = new Hexagon[3]; // Array used for determining the order of the cells.
        int lowest = 3; // To keep track of the number of Goose cells.

        // For loop to check all the adjacent cells.
        for (int i = 0; i < 6; i++) {
            // Variable to hold the current adjacent cell.
            Hexagon currCell = cell.getNeighbour(i);
            // Check if current adjacent cell is not null.
            if (currCell != null) {
                // Check if current adjacent cell is not marked.
                if (!currCell.isMarked()){
                    // Check if current adjacent cell is the End cell.
                    if (currCell.isEnd()) return currCell; // Return current adjacent cell if true.
                    // Check if current adjacent cell is not a Goose cell or has less than 3 neighbouring Goose cells.
                    if ((!currCell.isGooseCell()) && neighbourGooseCount(currCell) < 3) {
                        // Check what type of cell it is (Book, Grass, Snow) and if its corresponding array is empty.
                        if ((currCell.isBookCell()) && (priorityList[0] == null)) {
                            priorityList[0] = currCell; // Assign the current Book cell to the corresponding array.
                        } else if ((currCell.isGrassCell()) && (neighbourGooseCount(currCell) < lowest)) {
                            priorityList[1] = currCell; // Assign the current Grass cell to the corresponding array.
                            lowest = neighbourGooseCount(currCell); // Update the tracker variable.
                        } else if ((currCell.isSnowCell()) && (priorityList[2] == null)) {
                            priorityList[2] = currCell; // Assign the current Snow cell to the corresponding array.
                        }
                    }
                }
            } 
        }
        // Check through the array for a cell to be returned, according to the conditions.
        for (Hexagon i: priorityList) {
            // Check if the current cell is not empty.
            if (i != null) {
                return i; // Return the current cell.
            }
        }
        return null; // Return null if no cells are found.
    }

    /**
     * A method for finding a path through the given map using an algoritm and return the path taken in a form of a string.
     * @return A string with the IDs of the cells in the path (From start to end).
     */
    public String findPath() {
        // Local variables.
        ArrayStack<Hexagon> stack = new ArrayStack<>(); // Stack variable for holding the path.
        boolean running = true; // Check variable if the path is still running.
        String path = ""; // String variable to return.

        stack.push(map.getStart()); // Start cell is pushed into the stack.
        stack.peek().markInStack(); // Marked the starting cell as in-stack.

        // While loop that runs if stack is not empty and the path is still running.
        while ((!stack.isEmpty()) && (running == true)) {
            Hexagon currCell = stack.peek(); // Variable to hold the current cell. 
            path += currCell.getID() + " "; // The ID of the current cell is added to the string.
            
            // Check if the current cell is the end cell.
            if (currCell.isEnd()) {
                running = false; // Set running to false.
                break; // Breaks the while loop.
            }
            
            // Variable to hold the next appropriate cell.
            Hexagon nextCell = findBest(currCell);

            // Check if the next cell is valid or not.
            if (nextCell == null) {
                stack.pop(); // Remove the current cell off the stack.
                currCell.markOutStack(); // Mark the cell as out-stack.
            } else {
                stack.push(nextCell); // Push the next cell on the stack.
                nextCell.markInStack(); // Mark the next cell in-stack.
            }

        }
        // Check if the path was still running.
        if (running == false) {
            return path; // Return the string of the path.
        } else {
            return "No path found"; // Return if not running.
        }
    }

    /**
     * A method for exiting the map's GUI.
     */
    public void exit() {
        map.exit(); // Closes the screen GUI of the map.
    }
}
