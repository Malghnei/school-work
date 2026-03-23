public class Compound {
    private Element[] elements;
    private int[] elementCount;

    /**
     * Creates a new Compound object
     * @param table the periodic table
     * @param compoundArray the 2D array of the elements in the compound
     */
    public Compound(PeriodicTable table, String[][] compoundArray) {
        // Initialize the arrays using the compoundArray's size
        elements = new Element[compoundArray.length];
        elementCount = new int[compoundArray.length];
        
        // For loop to add all elements and number of atoms in the corresponding lists
        for (int i = 0; i < compoundArray.length; ++i) {
            elements[i] = table.getElement(compoundArray[i][0]);

            // Check if there are atom values in the compoundArray
            if (compoundArray[i].length == 1) { elementCount[i] = 1; } 
            else { elementCount[i] = Integer.parseInt(compoundArray[i][1]); }
        }
    }

    /**
     * Determines the type of bond of the compound
     * @return ionic, covalent, or null
     */
    public String getBondType() {
        // Check if there are only two elements in the compoundArray
        if (elements.length == 2) {
            // Counters for the types of elements
            int metalCounter = 0;
            int nonMetalCounter = 0;
            int metalloidCounter = 0;

            // For loop to check through all the elements
            for (Element i: elements) {
                // Check the type of the element
                if (i.getType().contains("Metalloid")) { ++metalloidCounter; }
                else if (i.getType().contains("Metal")) { ++metalCounter; }
                else if (i.getType().contains("Nonmetal")) { ++nonMetalCounter; }
            }

            // Check what type of bond using the data taken
            if ((metalCounter == 1) && (nonMetalCounter == 1)) { return "ionic"; } 
            else if ((metalCounter == 1) && (metalloidCounter == 1)) { return "covalent"; } 
            else if (nonMetalCounter == 2) { return "covalent"; } 
            else { return null; }
        }
        // Return null there not only two elements
        return null;
    }

    /**
     * Representation method of the compound object in the form of a string
     * @return list of the elements in the compound
     */
    public String toString() {
        String text = "";

        // For loop to list all elements in the compound
        for (int i = 0; i < elements.length; ++i) {
            text += elements[i].getName() + ": " + elementCount[i] + "\n";         
        }
        
        // Return text in the form of a string
        return text;
    }
}
