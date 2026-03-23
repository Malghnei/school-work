/*
CS 1027 - Assignment 2
Name: Malik Alghneimin
Student Number: 251430453
Email: malghnei@uwo.ca
Created: Febuary 11, 2025
*/
public class Train {
    private DoubleNode<TrainCar> locomotive;
    private DoubleNode<TrainCar> caboose;

    /**
     * Creates a new Train object. Locomotive and caboose are automatically null upon initialization.
     */
    public Train() {
        locomotive = null;
        caboose = null;
    }

    /**
     * Getter method for the locomotive of the train.
     * @return the locomotive of the train
     */
    public DoubleNode<TrainCar> getLocomotive() {
        return locomotive;
    }

    /**
     * Getter method for the caboose of the train.
     * @return the caboose of the train
     */
    public DoubleNode<TrainCar> getCaboose() {
        return caboose;
    }

    /**
     * Method for performing addition of another Reefer/TrainCar object to the train.
     * @param car the Reefer/TrainCar object to be added
     */
    public void addCar(TrainCar car) {
        // Initializing variables
        DoubleNode<TrainCar> curr, next = null;
        curr = new DoubleNode<TrainCar>(car);

        if ((locomotive == null) && (caboose == null)) { // Check if the train is empty (No locomotive and caboose).
            // Sets both the locomotive and caboose to the car being added.
            locomotive = curr;
            caboose = curr;
            return; // End the method.
        } else { 
            if (caboose.getElement().canConnect(car)) { // Check if the car to be added can connect to the caboose.
                // Adds the current car at the end of the train.
                caboose.setNext(curr);
                curr.setPrevious(caboose);
                // Change the caboose according to the train.
                caboose = curr;
                return; // End the method.
            } else {
                // Set the current to the next car.
                curr = caboose.getPrevious();
                // While loop to check for the whole train.
                while  ((curr != null)) {
                    // Check if the current car and the car behind it can connect to the new car.
                    if ((curr.getElement().canConnect(car)) && (curr.getNext().getElement().canConnect(car))) {
                        next = curr.getNext();
                        // Rearranges the connections of the cars according to the addition.
                        curr.setNext(new DoubleNode<TrainCar>(car));
                        curr.getNext().setPrevious(curr);
                        curr.getNext().setNext(next);
                        next.setPrevious(curr.getNext());
                        return; // End the method.
                    } else { // If the new car cannot be connected.
                        // Set the current to the next car.
                        curr = curr.getPrevious();
                    }
                } 
            }
        } 
        // Throw an exception if the attempt at adding fails.
        throw new TrainException("Car could not be added");
    }

    /**
     * Method for checking if the addCar() method can be ran.
     * @param car the Reefer/TrainCar object to be added
     * @return true or false
     */
    public boolean tryAddCar(TrainCar car) {
        // Try-Catch statement to handle any exceptions thrown from the method called.
        try {
            // The addCar() method is ran.
            addCar(car);
            return true; // Return true if the method is ran successfully.
        } catch (Exception e) {
            return false; // Return false if the method is ran unsuccessfully.
        }
    }

    /**
     * Method for performing removal of a Reefer/TrainCar object in the train.
     * @param Car the Reefer/TrainCar object to be removed
     */
    public void removeCar(TrainCar Car) {
        // Initializing variables.
        DoubleNode<TrainCar> curr = caboose;
        // While loop to go through the whole train.
        while ((curr != null)) {
            if (curr.getElement().equals(Car)) { // Check if the current car is equal to the car to be removed.
                if ((curr == caboose)) { // Check if the current car is the caboose.
                    // Reassignes the caboose.
                    caboose = curr.getPrevious();
                    if (caboose != null) { // Check if the new caboose is not equals to null.
                        // Cuts connections with the car to be removed.
                        curr.getPrevious().setNext(null);
                        curr.setPrevious(null);
                    }
                    return; // End the method.
                } else {
                    // Initialize new variables.
                    DoubleNode<TrainCar> next = curr.getNext();
                    DoubleNode<TrainCar> prev = curr.getPrevious();
                    if (prev == null) { // Check if the front (locomotive) is the car to be removed.
                        // Cuts connections from the car to be removed.
                        next.setPrevious(null);
                        curr.setNext(null);
                        // Reassignes the locomotive.
                        locomotive = next;
                        return; // End the method.
                    } else if (next.getElement().canConnect(prev.getElement())) { // Check if removing the car is possible.
                        // Cuts connections from the car to be removed.
                        curr.setPrevious(null);
                        curr.setNext(null);
                        next.setPrevious(prev);
                        prev.setNext(next);
                        return; // End the method.
                    }
                }
            }
            // Set the current to the next car.
            curr = curr.getPrevious();
        }
        // Throw an exception if the removal has failed.
        throw new TrainException("Car could not be removed");
    }

    /**
     * Method for checking if the removeCar() method can be ran.
     * @param car the Reefer/TrainCar object to be removed
     * @return true or false.
     */
    public boolean tryRemoveCar(TrainCar car) {
        try {
            // The removeCar() method is ran.
            removeCar(car);
            return true; // Return true if the method is ran successfully.
        } catch (Exception e) {
            return false; // Return false if the method is ran unsuccessfully.
        }
    }

    /**
     * Representation method of the Train object in a form of a string.
     * @return the list of Reefers/TrainCar objects in the train
     */
    public String toString() {
        // Initializing variables.
        DoubleNode<TrainCar> curr = locomotive;
        String trainString = "";

        if ((locomotive == null) && (caboose == null)) { // Check if the train is empty.
            return "The train is empty"; // Returns the following string if the train is empty.
        } else {
            // While loop to check through the whole train.
            while (curr != null) {
                // Add the string of the car object to the string to be returned.
                trainString += curr.getElement();
                // Check if not last car.
                if (curr.getNext() != null) {
                    // Add commas between car objects in string.
                    trainString += ", ";
                }
                // Set the current to the next car.
                curr = curr.getNext();
            }
            // Return the string object.
            return trainString;
        }
    }
}
