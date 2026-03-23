/*
CS 1027 - Assignment 4
Name: Malik Alghneimin
Student Number: 
Email: malghnei@uwo.ca
Created: March 25, 2025
*/

import java.util.Iterator;

public class FileSystemObject implements Comparable<FileSystemObject> {
    // Local variables.
    private String name;
    private OrderedListADT<FileSystemObject> children;
    private FileSystemObject parent;
    private int id;

    /**
     * Creates a new FileSystemObject. 
     * @param name The name of the file/folder.
     * @param id The ID of the file/folder.
     */
    public FileSystemObject(String name, int id) {
        // Object variables.
        this.name = name;
        this.id = id;

        // Check if the object is not a file (ComputerFile).
        if (!isFile()) {
            // Create a children list.
            children = new ArrayOrderedList<FileSystemObject>();
        }
    }

    /**
     * Getter Method to return the name variable of the FileSystemObject.
     * @return Name of the file/folder.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter method to return the id variable of the FileSystemObject.
     * @return ID of the file/folder.
     */
    public int getID() {
        return this.id;
    }

    /**
     * Getter method to return the parent variable of the FileSystemObject.
     * @return Parent of the file/folder.
     */
    public FileSystemObject getParent() {
        return parent;
    }

    /**
     * Getter method to return the children list variable of the FileSystemObject (ComputerFile objects do not contain children).
     * @return Children list of the folder.
     */
    public OrderedListADT<FileSystemObject> getChildren() {
        return children;
    }

    /**
     * Setter method to change the name variable of the FileSystemObject.
     * @param newName The new name of the file/folder.
     */
    public void setName(String newName) {
        this.name = newName;
    }

    /**
     * Setter method to change the parent variable of the FileSystemObject.
     * @param newName The new parent of the file/folder.
     */
    public void setParent(FileSystemObject newParent) {
        this.parent = newParent;
    }

    /**
     * Method for checking whether the current object is a ComputerFile or not.
     * @return True or False.
     */
    public boolean isFile() {
        return this instanceof ComputerFile;
    }

    /**
     * Method for adding a node to the current FileSystemObject as a child.
     * @param node
     */
    public void addChild(FileSystemObject node) {
        // Check if the current object is a file object (ComputerFile) or not.
        if (isFile()) {
            // Throws an exception (DirectoryTreeException).
            // Warns the user that files/folders objects cannot be stored in a file object (ComputerFile).
            throw new DirectoryTreeException("Cannot store a file/folder in a file.");
        } else {
            // A helper method is called to check for any duplicate nodes in the folder.
            if (checkDupe(node, children)) {
                // Throws an exception (DirectoryTreeException).
                // Warns the user that two files/folders cannot have the same name in the same folder.
                throw new DirectoryTreeException("Cannot have two files/folders with the same name stored in the same folder.");
            }

            // Add the node to the folder.
            children.add(node);
            node.setParent(this);
        }
    }

    /**
     * Helper method to check for any duplicate nodes in the given children list.
     * @param toCheck The node to check.
     * @param children The list of children.
     * @return True or false.
     */
    private boolean checkDupe(FileSystemObject toCheck, OrderedListADT<FileSystemObject> children) {
        // Create a new iterator for the children list.
        ArrayIterator<FileSystemObject> iterator = (ArrayIterator<FileSystemObject>) children.iterator();

        // While loop to go through the whole iterator.
        while (iterator.hasNext()) {
            // Get the item from the iterator.
            FileSystemObject currFile = iterator.next();

            // Check if the node to check has the same name as the current item.
            if (toCheck.getName().equals(currFile.getName())) {
                return true; // Return true.
            }
        }
        return false; // Return false.
    }

    /**
     * Representation method of the FileSystemObject in a form of a string.
     * @return The name of the file/folder.
     */
    public String toString() {
        return this.name;
    }

    /**
     * Method for determining the size of the FileSystemObject.
     * <pre> 
     * If the current object is a file (ComputerFile), the size variable is returned.
     * If the current object is a folder (FileSystemObject), recursion is used to determine the size of all the children of the object.
     * </pre>
     * @return the total size of the file/folder.
     */
    public int size() {
        // Check whether the current object is a file (ComputerFile) or not.
        if (isFile()) {
            return ((ComputerFile)this).size(); // Return the size of the file using type casting.
        } else {
            return sizeHelper(this); // Return the recursive helper method.
        }
    }

    private int sizeHelper(FileSystemObject currFolder) {
        // Create a new iterator for the children list.
        ArrayIterator<FileSystemObject> iterator = (ArrayIterator<FileSystemObject>)currFolder.getChildren().iterator();
        
        // Variable to keep count of the size.
        int total = 0;

        // While loop to go through the whole iterator.
        while (iterator.hasNext()) {
            // Get the item from the iterator.
            FileSystemObject currChild = iterator.next();

            // Check if the item is a file (ComputerFile) or not.
            if (currChild.isFile()) {
                // Add the size of the file to the total variable.
                total += ((ComputerFile)currChild).size();
            } else {
                // Add the size of the folder to the total variable (Recursion call).
                total += sizeHelper(currChild);
            }
        }

        // Return the total size of the file/folder.
        return total;
    }

    /**
     * Method to compare the current FileSystemObject with another object (ComputerFile or FileSystemObject).
     * @return number
     */
    public int compareTo(FileSystemObject other) {
        // Check the current and other object do not have the same class.
        if (other.getClass() != this.getClass()) {
            if (isFile()) return 1; // Return positive number.  
            else return -1; // Return negative number
        }

        // Return the comparison of the name variables of both objects.
        return this.getName().compareToIgnoreCase(other.getName());
    }
}