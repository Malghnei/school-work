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
import random
# Character list
ALL_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*/?-+,.|~"

# This function creates a password randomly based off characters from character groups at the given length.
def generate_password(length):
    # Password variable
    pwd = ""

    # Randomly generates the password in the given length
    for i in range(0,length,1):
        pwd += random.choice(ALL_CHARS)

    # Return the generated password
    return pwd

