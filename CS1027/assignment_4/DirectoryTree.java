/*
CS 1027 - Assignment 4
Name: Malik Alghneimin
Student Number: 
Email: malghnei@uwo.ca
Created: March 25, 2025
*/

import java.util.Iterator;

public class DirectoryTree {
    // Class Variables.
    private FileSystemObject root;

    /**
     * Creates a new DirectoryTree object.
     * @param rt The root of the tree.
     */
    public DirectoryTree(FileSystemObject rt) {
        this.root = rt;
    }

    /**
     * Getter method for returning the root variable of the DirectoryTree.
     * @return Root of the tree.
     */
    public FileSystemObject getRoot() {
        return this.root;
    }

    /**
     * Method for determining the level of the given FileSystemObject in the DirectoryTree.
     * @param fso The file/folder object.
     * @return A positive integer.
     */
    public int level(FileSystemObject fso) {
        if (fso.getParent() == null) return 0;
        else return 1 + level(fso.getParent());
    }

    /**
     * Method for determining the lowest common ancestor between two FileSystemObjects.
     * @param a A file/folder object.
     * @param b A file/folder object.
     * @return The lowest common ancestor.
     */
    public FileSystemObject lca(FileSystemObject a, FileSystemObject b) {
        return lcaHelper(a, b); 
    }

    /**
     * Helper method to assist in determining the lowest common ancestor between two FileSystemObjects.
     * @param a A file/folder object.
     * @param b A file/folder object.
     * @return the lowest common ancestor.
     */
    private FileSystemObject lcaHelper(FileSystemObject a, FileSystemObject b) {

        if (level(a) < level(b)) return lcaHelper(a, b.getParent()); // return the helper method with parent of b (Recursion call).
        if (level(b) < level(a)) return lcaHelper(a.getParent(), b); // Return the helper method with parent of a (Recursion call).

        if ((a.getParent() == root) && (b.getParent() == root)) return root; // Return the root of the tree.
        else if (a.getParent() == b.getParent()) return a.getParent(); // Return the current parent of both objects.
        else return lcaHelper(a.getParent(), b.getParent()); // Return the helper method (Recursion call).
    }

    /**
     * Method for determining the path to take from FileSystemObject a to FileSystemObject b.
     * @param a Start file/folder.
     * @param b Destination file/folder.
     * @return String of the folder path from start to end.
     */
    public String buildPath(FileSystemObject a, FileSystemObject b) {
        // Check if a and b are in the same folder.
        if (a.getParent() == b.getParent()) {
            return b.getName(); // Return the name of b as the path.
        } else {
            // Create a new String
            String pathString = "";

            // Determine the difference of level from the lca of a and b, and a.
            int levelDiff = level(a) - level(lca(a,b));

            // Add the path up levels based on the level difference.
            for (int i = 0; i < levelDiff; i++) {
                pathString += "../";
            }

            // Get the lowest common ancestor of a and b.
            FileSystemObject curr = lca(a, b);
            
            boolean running = true;
            // While loop that runs until the path is complete.
            while (running != false) {
                // Check for the path, from b to lca.
                FileSystemObject pathCheck = b;
                while (pathCheck.getParent() != curr) {
                    pathCheck = pathCheck.getParent();
                }

                // Create a new iterator to go through the children list of the current folder.
                ArrayIterator<FileSystemObject> i = (ArrayIterator<FileSystemObject>)curr.getChildren().iterator();

                // While loop to go through the iterator.
                while (i.hasNext()) {
                    // Access the next item in the iterator.
                    FileSystemObject currChild = i.next();

                    // Check if the current item aligns with the path.
                    if (currChild == pathCheck) {
                        // Update the current file/folder/
                        curr = pathCheck;

                        // Add to the string.
                        pathString += curr.getName();
                        
                        // Check if the program should still be running (current is equal to the destination).
                        if (curr == b) {
                            running = false; // Set running to false.
                        } else {
                            pathString += "/"; // Add the path segment seperation (/).
                        }
                        break; // Break for unnecessary iterations.
                    }
                }
            }
            
            // Return the String of the path.
            return pathString;
        }
    }

    /**
     * Representation method of the DirectoryTree in a form of a String.
     * @return String of the entire tree.
     */
    public String toString() {
        return toStringHelper(root); // Return a recursive helper method.
    }

    private String toStringHelper(FileSystemObject curr) {
        // Create a new String for the tree.
        String fileTree = "";

        // Check if the current object is the root.
        if (curr == root) {
            fileTree += curr.getName(); // Add the root to the String.
        } else {
            fileTree += "\n";
            
            // Add the appropriate amount of spaces to the String based off the level of the current object.
            for (int i = 1; i < level(curr); i++) {
                fileTree += "  ";
            }

            // Add the current object to the String.
            fileTree += " - " + curr.getName();    
        }
        
        // Check if the current object is not a file (ComputerFile).
        if (!curr.isFile()) {
            ArrayIterator<FileSystemObject> iterator = (ArrayIterator<FileSystemObject>)curr.getChildren().iterator();

            while (iterator.hasNext()) {
                // Get the next item from the iterator.
                FileSystemObject i = iterator.next();

                // Add the current item to the String (Recursive call).
                fileTree += toStringHelper(i);
            } 
        }

        // Return the String of the tree.
        return fileTree;
    }
        
    /**
     * Method for relocating a selected FileSystemObject to a given destination FileSystemObject.
     * @param f The selected file/folder to relocate.
     * @param dest The destination folder.
     */
    public void cutPaste(FileSystemObject f, FileSystemObject dest) {
        // Check if the destination object is a file object (ComputerFile), and if the selected object is the root.
        if (dest.isFile()) {
            // Throw an exception (DirectoryTreeException).
            // Warns the user that the destination object is a file.
            throw new DirectoryTreeException("Cannot store files/folders inside a file.");
        } else if (f == root) {
            // Throw an exception (DirectoryTreeException).
            // Warns the user that the file/folder to be cut is the root of the tree.
            throw new DirectoryTreeException("Cannot remove/cut/move the root of the whole tree.");
        } else {
            // Relocate the selected object to the destination.
            f.getParent().getChildren().remove(f); 
            f.setParent(dest); 
            dest.addChild(f); 
        }
    }

    public void copyPaste(FileSystemObject f, FileSystemObject dest) {
        // Check if the destination object is a file object (ComputerFile).
        if (dest.isFile()) {
            // Throw a new exception (DirectoryTreeException).
            // Warns the user that the destination object is a file.
            throw new DirectoryTreeException("Cannot store files/folders inside a file.");
        } else {
            // Create new copied file/folder (Recursion helper method is called).
            FileSystemObject copiedFile = copyPasteHelper(f);

            // Set the new copied file/folder to the destination folder.
            copiedFile.setParent(dest);
            dest.addChild(copiedFile);
        }
    }

    /**
     * Helper method for creating copies of FileSystemObjects using recursion.
     * @param curr The current File/Folder object.
     * @return The current copy of the File/folder object.
     */
    private FileSystemObject copyPasteHelper(FileSystemObject curr) {
        if (curr.isFile()) {
            return new ComputerFile(curr.getName(), curr.getID() + 100, curr.size()); // Return the current file copy.
        } else {
            // Create a new copy of the current folder.
            FileSystemObject currCopy = new FileSystemObject(curr.getName(), curr.getID() + 100);
            for (FileSystemObject i : curr.getChildren()) {
                // Create a copy of the child of the current folder (Recursion method call).
                FileSystemObject childCopy = copyPasteHelper(i);

                // Add the copied child file/folder to the copy of the current folder.
                currCopy.addChild(childCopy);
                childCopy.setParent(currCopy);
            }
            return currCopy; // Return the current folder copy.
        }
    }
}
