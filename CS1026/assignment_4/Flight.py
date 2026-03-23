"""
********************************************
CS 1026 - Assignment 4 - Air Travel
Code by: Malik Alghneimin
Student Number: 251430453
File created: November 26, 2024
********************************************
The program is used to create the "Flight" class, which is used for creating a object foundation that can contain information
about the given flight like the flight's number, origin, destination, and duration. 
"""
from Airport import *

# Creating a new class called "Flight".
class Flight:
    # Initialization function of the class.
    def __init__(self, flight_no, origin, dest, dur):
        # Check whether the given origin and/or the destination are airport objects.
        if isinstance(origin, Airport) and isinstance(dest, Airport):
            # The given values are assigned to the class object.
            self._flight_no = flight_no
            self._origin = origin
            self._destination = dest
            self._duration = float(dur)
        else:
            # Otherwise, an error is raised if not true
            raise TypeError("The origin and destination must be Airport objects")
    
    # Function for controlling what to return in a form of a string.
    def __str__(self):
        # Check if the flight is domestic or international.
        if self.is_domestic(): type = "domestic" 
        else: type = "international"
        # Return a string in the proper format.
        return "{0} to {1} ({2}h) [{3}]".format(self._origin.get_city(), self._destination.get_city(), round(self._duration), type)
    
    # Function for matching the current object with the other object.
    def __eq__(self, other):
        # Check whether the other object being compared to is the same object as the current object (in this case, "Flight")
        if isinstance(other, Flight):
            # Returns if the objects are equal or not.
            if (self._origin == other._origin) and (self._destination == other._destination):
                return True
            else:
                return False
        else:
            return False

    # Function for addition of the current object and the other object.
    def __add__(self, conn_flight):
        if not isinstance(conn_flight, Flight):
            raise TypeError("The connecting_flight must be a Flight object")
        else:
            if self._destination != conn_flight._origin:
                raise ValueError("These flights cannot be combined")
            else:
                return Flight(self._flight_no, self._origin, conn_flight._destination, self._duration + conn_flight._duration)
    
    # Getter method for returning the flight_no.
    def get_flight_no(self):
        return self._flight_no
    
    # Getter method for returning the origin.
    def get_origin(self):
        return self._origin
    
    # Getter method for returning the destination.
    def get_destination(self):
        return self._destination
    
    # Getter method for returning the duration.
    def get_duration(self):
        return self._duration
    
    # Method for checking if the current object is domestic (if the destination and origin are in the same country).
    def is_domestic(self):
        return self._origin.get_country() == self._destination.get_country()
    
    # Setter method for changing the origin of the object.
    def set_origin(self, origin):
        self._origin = origin
    
    # Setter method for changing the destination of the object.
    def set_destination(self, destination):
        self._destination = destination