/*
CS 1027 - Assignment 2
Name: Malik Alghneimin
Student Number: 
Email: malghnei@uwo.ca
Created: Febuary 11, 2025
*/
public class Reefer extends TrainCar{
    private int temp;

    /**
     * Creates a new Reefer object.
     * @param weight the weight of the reefer
     * @param temp the temperature of the reefer
     * @param freight the assigned freight to the reefer
     */
    public Reefer(int weight, int temp, String freight) {
        super(weight, freight);
        this.temp = temp;
    }

    /**
     * Getter method for the temperature of the reefer.
     * @return the temperature of the reefer
     */
    public int getTemp() {
        return this.temp;
    }

    /**
     * Setter method for changing the temperature of the reefer.
     * @param newTemp new temperature of the reefer
     */
    public void setTemp(int newTemp) {
        this.temp = newTemp;
    }

    /**
     * Representation method of the Reefer object in a form of a string.
     * @return the freight, weight, and temperature of the reefer
     */
    public String toString() {
        return "<" + this.getFreight() + ", " + this.getWeight() + ", " + this.getTemp() + "C>";
    }

    /**
     * Method for checking if this reefer can connect to the other reefer/train car object.
     * @param other the other Reefer/TrainCar object
     * @return true or false
     */
    public boolean canConnect(TrainCar other) {
        if (!super.canConnect(other)) {
            if (other.getClass() == this.getClass()) {
                if ((((Reefer)other).getTemp() <= this.temp + 5) && (((Reefer)other).getTemp() >= this.temp - 5)) {
                    return true;
                }
            }
        } else {
            return true;
        }
        return false;
    }

    /**
     * Method for comparing other reefers/train cars to itself
     * @param other the other Reefer/TrainCar object
     * @return true or false
     */
    public boolean equals(TrainCar other) {
        if (super.equals(other)) {
            if (other.getClass() == this.getClass()) {
                if (((Reefer)other).getTemp() == this.temp) {
                    return true;
                }
            }
        }
        return false;
    }
}
