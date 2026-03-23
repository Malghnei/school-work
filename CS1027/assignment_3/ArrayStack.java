/*
CS 1027 - Assignment 3
Name: Malik Alghneimin
Student Number: 
Email: malghnei@uwo.ca
Created: March 8, 2025
*/
public class ArrayStack<T> implements StackADT<T> {
    // Class variables
    private T[] array;
    private int top;

    /**
     * Initializes a new ArrayStack object with a set initial size of array.
     */
    public ArrayStack() {
        array = (T[])new Object[10]; // Creates a new array with a size of 10.
        top = array.length - 1; // Set top to size of the array - 1.
    }

    /**
     * Initializes a new ArrayStack object with a given initial size of array.
     * @param initCapacity
     */
    public ArrayStack(int initCapacity) {
        array = (T[])new Object[initCapacity]; // Creates a new array with a given size.
        top = initCapacity - 1; // Set top to size of the array - 1.
    }

    /**
     * This method adds the given object to the stack.
     * @param element The object to be added to the stack.
     */
    public void push(T element) {
        // Check if the stack is full.
        if (top == -1) { 
            expandCapacity(); // Calls a method for increasing the array size.
        }

        array[top] = element; // Adds the object to the array.
        top--; // Decrement top.
    }

    /**
     * This method removes the top element and returns it.
     * @return The object at the top of the stack.
     * @throws CollectionException Occurs when the stack is empty.
     */
    public T pop() throws CollectionException {
        if (isEmpty()) {
            // Throws an exception when the stack is empty.
            throw new CollectionException("Stack is empty");
        } else {
            T temp; // Temporary variable for storage.
            top++; // Increment top.
            temp = array[top]; // Decrements the top value and assignes the current value to a variable.
            array[top] = null; // Removes value from stack.
            return temp; // Returns the removed value.
        }
    }

    /**
     * This method returns the element at the top of the stack without removing it.
     * @return The object at the top of the stack.
     * @throws CollectionException Occurs when the stack is empty.
     */
    public T peek() throws CollectionException {
        if (isEmpty()) {
            // Throws an exception when the stack is empty.
            throw new CollectionException("Stack is empty");
        } else {
            // Returns the top of the stack value.
            return array[top + 1];
        }
    }

    /**
     * This method checks whether the stack is currently empty or not.
     * @return True or False.
     */
    public boolean isEmpty() {
        if (top + 1 == array.length) return true; // if top is at the beginning, stack is empty.
        else return false; // if not, stack is not empty.
    }

    /**
     * This method returns the amount of elements in the stack.
     * @return The size of the stack.
     */
    public int size() {
        int numElements = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                numElements++;
            }
        }
        return numElements;
    }


    /**
     * This method returns the size of the array itself.
     * @return The size of the array.
     */
    public int getCapacity() {
        return array.length;
    }

    /**
     * This method returns the top index of the stack in the array.
     * @return Top index in the array.
     */
    public int getTop() {
        return top;
    }

    /**
     * This method returns the stack in a form of a string.
     * @return String of the elements inside the stack.
     */
    public String toString() {
        // Check if the stack is empty
        if (isEmpty()) {
            return "Empty stack."; // Return if the stack is empty.
        } else {
            // Create a String variable to return.
            String toReturn = "";
            // For loop to add elements to the String.
            for (int i = top + 1; i < array.length; i++) {
                // Add current element to the string.
                toReturn += array[i];
                // Check if the current is last or not.
                if (i + 1 != array.length) toReturn += ", "; // Return "," if the element is not last.
                else toReturn += "."; // Return "." if the element is last.
            }
            return toReturn; // Return the String.
        }
    }

    /**
     * This method increases the size of the array of the stack.
     */
    private void expandCapacity() {
        // Temporary array to create.
        T[] tempArray;
        // Check the size of the current array.
        if (array.length <= 15) {
            // If equal or less than 15, size of temporary array is double the size of current array.
            tempArray = (T[])new Object[array.length * 2];
            top = array.length - 1;
        } else {
            // If greater than 15 size of temporary array is the size of current array plus 10;
            tempArray = (T[])new Object[array.length + 10];
            top += 10;
        }

        // Reassigning values to the temporary array.
        for (int i = 1; i < array.length + 1; i++) {
            tempArray[tempArray.length - i] = array[array.length - i];
        } 
        // Set the temporary array as the current one.
        array = tempArray;
    }
}