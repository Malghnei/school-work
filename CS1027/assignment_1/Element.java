/*
CS 1027 - Assignment 1 
Name: Malik Alghneimin
Student Number: 
Email: malghnei@uwo.ca
Created: Febuary 4, 2025
*/
public class Element {
    private int atomicNum;
    private float atomicWeight;
    private String symbol;
    private String name;
    private String state;
    private String type;

    /**
     * Creates a new Element object
     * @param num the atomic number of the element
     * @param wt the atomic weight of the element
     * @param sym the symbol of the element
     * @param nm the name of the element
     * @param ty the type of the element
     */
    public Element(int num, float wt, String sym, String nm, String st, String ty) {
        this.atomicNum = num;
        this.atomicWeight = wt;
        this.symbol = sym;
        this.name = nm;
        this.state = st;
        this.type = ty;
    }
    /**
     * Getter method for the atomic number of the element
     * @return the element's atomic number
     */
    public int getAtomicNo() {
        return this.atomicNum;
    }
    
    /**
     * Getter method for the atomic weight of the element
     * @return the element's atomic weight
     */
    public float getAtomicWeight() {
        return this.atomicWeight;
    }

    /**
     * Getter method for the symbol of the element
     * @return the element's symbol
     */
    public String getSymbol() {
        return this.symbol;
    }

    /**
     * Getter method for the name of the element
     * @return the element's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter method for the state of the element
     * @return the element's state
     */
    public String getState() {
        return this.state;
    }

    /**
     * Getter method for the type of the element
     * @return the element's type
     */
    public String getType() {
        return this.type;
    }

    /**
     * Setter method for adjusting the current element's name
     * @param newName new name for the element
     */
    public void setName(String newName) {
        this.name = newName;
    }

    /**
     * Setter method for adjusting the current element's state
     * @param newState new state for the element
     */
    public void setState(String newState) {
        this.state = newState;
    }

    /**
     * Setter method for adjusting the current element's type
     * @param newType new type for the element
     */
    public void setType(String newType) {
        this.type = newType;
    }
    
    /**
     * Representation method of the element object in a form of a string
     * @return the name and the symbol of the element
     */
    public String toString() {
        return String.format("%s (%s)", this.symbol, this.name);
    }
    
    /**
     * Method for comparing other elements to itself
     * @param other the other element object
     * @return true or false
     */
    public boolean equals(Element other) {
        if (this.getAtomicNo() == other.getAtomicNo()) return true;
        else return false;
    }
}