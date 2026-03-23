/*
CS 1027 - Assignment 2
Name: Malik Alghneimin
Student Number: 251430453
Email: malghnei@uwo.ca
Created: Febuary 11, 2025
*/
public class TrainCar {
    private int weight;
    private String freight;

    /**
     * Creates a new TrainCar object.
     * @param weight the weight of the train car
     * @param freight the assigned freight to the train car
     */
    public TrainCar(int weight, String freight) {
        this.weight = weight;
        this.freight = freight;
    }

    /**
     * Getter method for the weight of the train car.
     * @return weight of the train car
     */
    public int getWeight() {
        return this.weight;
    }

    /**
     * Getter method for the freight of the train car.
     * @return freight of the train car
     */
    public String getFreight() {
        return this.freight;
    }

    /**
     * Setter method for changing the weight of the train car.
     * @param newWeight new weight of the train car
     */
    public void setWeight(int newWeight) {
        this.weight = newWeight;
    }

    /**
     * Setter method for changing the freight of the train car.
     * @param newFreight new freight assigned to the train car
     */
    public void setFreight(String newFreight) {
        this.freight = newFreight;
    }

    /**
     * Representation method of the TrainCar object in a form of a string.
     * @return the freight and weight of the train car
     */
    public String toString() {
        return "<" + this.freight + ", " + this.weight + ">";
    }

    /**
     * Method for checking whether this train car can or cannot connect to another train car
     * @param other the other train car
     * @return true or false
     */
    public boolean canConnect(TrainCar other) {
        if ((other.getWeight() == this.weight) || (other.getFreight().equals(this.freight))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method for comparing other train cars to itself
     * @param other the other train car object
     * @return true or false
     */
    public boolean equals(TrainCar other) {
        if ((other.getWeight() == this.weight) && (other.getFreight().equals(this.freight))) {
            if (other.getClass() == this.getClass()) {
                return true;
            }
        }
        return false;
    }
}
