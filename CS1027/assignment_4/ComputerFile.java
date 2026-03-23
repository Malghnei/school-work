/*
CS 1027 - Assignment 4
Name: Malik Alghneimin
Student Number: 
Email: malghnei@uwo.ca
Created: March 25, 2025
*/

public class ComputerFile extends FileSystemObject {
    // Class variable.
    private int size;

    /**
     * Creates a new ComputerFile object. 
     * @param name Name of the file.
     * @param id ID of the file.
     * @param size Size of the file.
     */
    public ComputerFile(String name, int id, int size) {
        // Initialize the parent component of the class (FileSystemObject).
        super(name, id);
        // Object variable.
        this.size = size;
    }

    @Override
    /**
     * Getter method for returning the size of the ComputerFile.
     * @return size of the file.
     */
    public int size() {
        return this.size;
    }
}
