"""
********************************************
CS 1026 - Assignment 4 - Air Travel
Code by: Malik Alghneimin
Student Number: 
File created: November 26, 2024
********************************************
The program is used to create the "Airport" class, which is used for creating a object foundation that can contain information
about the given airport like airport code, city, and country.
"""

# Creating a new class called "Airport".
class Airport:
    # Initialization function of the class
    def __init__(self, code, city, country):
        # The given values are assigned to the class object.
        self._code = code
        self._city = city
        self._country = country

    # Function for controlling what to return in a form of a string.
    def __str__(self):
        return "{0} ({1}, {2})".format(self._code, self._city, self._country)

    # Function for matching the current object with the other object.
    def __eq__(self, other):
        # Check whether the other object being compared to is the same object as the current object (in this case, "Airport")
        if isinstance(other, Airport):
            # Returns if the objects are equal or not.
            return other.get_code() == self.get_code()
        else:
            return False
        
    # Getter method for returning the airport code.
    def get_code(self):
        return self._code

    # Getter method for returning the airport's city.
    def get_city(self):
        return self._city

    # Getter method for returning the airport's country.
    def get_country(self):
        return self._country

    # Setter method for changing the object's city.
    def set_city(self, city):
        self._city = city
    
    # Setter method for changing the object's country.
    def set_country(self, country):
        self._country = country
