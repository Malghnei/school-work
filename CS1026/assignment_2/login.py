"""
********************************************
CS 1026 - Assignment 2 - Password Strength
Code by: Malik Alghneimin
Student ID: Malghnei
File created: October 19, 2024
********************************************
This file is used to generate a random password through the random function. A random character is selected from the
character list to add on to the password until the given length is reached.
"""

# Imports
from password import *
from generate import *

# This function requires the user to type a password as an input and checks for strength, whilst comparing to the minimum
# strength.
def process_password(min_strength):
    while True:
        # Variables
        user_input = input("Type in a new password or type X for a randomly generated password: ")
        pwd = ""
        # Check if user typed x.
        # If so, a password is randomly generated.
        if user_input.upper() == "X":
            pwd = generate_password(15)
            print("Your password: ", pwd)
        # If not, the user's input is considered the password
        else:
            pwd = user_input
            print("You entered: ", pwd)
        # Calculates and outputs the password's strength.
        pwd_strength = password_strength(pwd)
        print("Your password has a strength of ", pwd_strength)
        # Checks if the password strength is lower than the minimum strength.
        if pwd_strength < min_strength:
            print("Your password is not strong enough. Please create a new password that is stronger.\n")
            # The user is given another chance to type another password.
        else:
            print("Your password strong enough! Thank you.")
            break
            # The programs ends.

