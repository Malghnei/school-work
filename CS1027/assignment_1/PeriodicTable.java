public class PeriodicTable {
    private Element[][] mainTable;
    private Element[] lanthanides;
    private Element[] actinides;

    /**
     * Creates a new periodicTable object
     * @param filename the name of the file
     */
    public PeriodicTable(String filename) {
        // Initializes the list sizes
        mainTable = new Element[8][19];
        lanthanides = new Element[15];
        actinides = new Element[15];
        // Loads the data into the lists
        loadData(filename);
    }

    /**
     * Reads data from the given file and loads it into lists.
     * @param filename the name of the file
     */
    public void loadData(String filename) {
        TextFileReader file = new TextFileReader(filename);
        int lanthanidesCounter = 0;
        int actinidesCounter = 0;
        file.readString();

        // Checks through the whole file
        while (!file.endOfFile()) {
            // Extracts a line from the file
            String line = file.readString();
            // Cleaning the line and forming it into a list
            String[] list = line.split(",");
            String[] newList = new String[29];
            int j = 0;

            // For loop to clean empty strings
            for (int i = 0; i<list.length; ++i) {
                if ((list[i].length() != 0) && (list[i] != " ")) {
                    newList[j] = list[i];
                    ++j;
                }
            }

            // Creates a new element object based on the data
            Element element = new Element(Integer.parseInt(newList[0]), Float.parseFloat(newList[3]), newList[2], newList[1], newList[9], newList[12]);
            
            // Check where the element belongs in the lists
            if ((Integer.parseInt(newList[7]) == 6) && (((newList[8]).contains("i")))) {
                lanthanides[lanthanidesCounter] = element;
                ++lanthanidesCounter;
            } else if ((Integer.parseInt(newList[7]) == 7) && (((newList[8]).contains("i")))){
                actinides[actinidesCounter] = element;
                ++actinidesCounter;
            } else {
                mainTable[Integer.parseInt(newList[7])][Integer.parseInt(newList[8])] = element;
            }    
        }

    }

    /**
     * Representation method of the periodicTable object in the form of a string
     * @return the whole periodic table
     */
    public String toString() {
        String table = "";

        // For loops to go through the whole mainTable list
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 19; ++j) {
                // Add the element's symbol to the string
                if (j != 0) { table += "  "; } 
                if (mainTable[i][j] != null) {
                    if (mainTable[i][j].getSymbol().length() == 1) {
                        // Elements with one letter symbols
                        table += " " + mainTable[i][j].getSymbol();
                    } else {
                        // Elements with two letter symbols
                        table += mainTable[i][j].getSymbol();
                    }
                } else {
                    // No elements to be found
                    if (j != 0) { table += "  "; }
                }
            }
            table += "\n";
        }

        table += "\n";

        // For loops to go through the lanthanides and actinides lists
        for (int i = 0; i<2; ++i) {
            Element[] list;
            if (i == 0) {
                list = lanthanides;
            } else {
                list = actinides;
            }

            for (int j = 0; j<18; ++j) {
                table += "  ";
                if (j<3) {
                    // Empty space
                    table += "  ";
                } else {
                    // Add the element's symbol to the string
                    if (list[j-3].getSymbol().length() == 1) {
                        // Elements with one letter symbols
                        table += " " + list[j-3].getSymbol();
                    } else {
                        // Elements with two letter symbols
                        table += list[j-3].getSymbol();
                    }
                }
            }
            table += "\n";
        }

        // Table is returned in the form of a string
        return table;
    }

    /**
     * Getter method for finding an element
     * @param sym symbol of the element to be found
     * @return the element object
     */
    public Element getElement(String sym) {
        // For loops to check through the mainTable list
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 19; ++j) {
                if (mainTable[i][j] != null) {
                    if (mainTable[i][j].getSymbol().equals(sym)) {
                        // Return the element
                        return mainTable[i][j];
                    }
                }
            }
        }

        // For loops to check through the lanthinides and actinides lists
        for (int i = 0; i < 2; ++i) {
            // List selector
            Element[] list;
            if (i == 0) {
                list = lanthanides;
            } else {
                list = actinides;
            }
            
            for (int j = 0; j < 15; ++j) {
                if (list[j].getSymbol().equals(sym)) {
                    // Return the element
                    return list[j];
                }
            }
        }
        // Return nothing if element is not found
        return null;
    }

    /**
     * Getter method for finding the list of elements in the period
     * @param per the period of the table
     * @return list of elements
     */
    public Element[] getPeriod(int per) {
        int arrayCounter = 0;
        // Check if the period is valid in the mainTable list
        if ((per > 0) && (per < 8)) {
            // Counting the amount of elements in the period
            for (int i = 0; i < 19; ++i) {
                if (mainTable[per][i] != null) {    
                    ++arrayCounter;
                }
            }

            // Create a new list based off the size
            Element[] list = new Element[arrayCounter];

            arrayCounter = 0;
            // For loop to get all the elements in the period
            for (int i = 0; i < 19; ++i) {
                if (mainTable[per][i] != null) {
                    list[arrayCounter] = mainTable[per][i];
                    ++arrayCounter;
                }
            }
            // Return the list of elements
            return list;
        }
        // Return nothing if period is not valid
        return null;
    }

    /**
     * Getter method for finding the list of elements in the group of the table
     * @param grp the group of the table
     * @return list of elements
     */
    public Element[] getGroup(int grp) {
        int arrayCounter = 0;
        // Check if the group is valid in the mainTable list
        if ((grp > 0) && (grp < 19)) {
            // Counting the amount of elements in the period
            for (int i = 0; i < 8; ++i) {
                if (mainTable[i][grp] != null) {    
                    ++arrayCounter;
                }
            }

            // Create a new list based off the size
            Element[] list = new Element[arrayCounter];
            arrayCounter = 0;

            // For loop to get all the elements in the group
            for (int i = 0; i < 8; ++i) {
                if (mainTable[i][grp] != null) {
                    list[arrayCounter] = mainTable[i][grp];
                    ++arrayCounter;
                }
            }
            // Return the list of elements
            return list;
        }
        // Return nothing if group is not valid
        return null;
    }

    /**
     * Getter method for returning the list of lanthanides
     * @return list of elements
     */
    public Element[] getLanthanides() {
        return lanthanides;
    }

    /**
     * Getter method for returning the list of actinides
     * @return list of elements
     */
    public Element[] getActinides() {
        return actinides;
    }
}
