"""
********************************************
CS 1026 - Assignment 2 - Password Strength
Code by: Malik Alghneimin
Student ID: Malghnei
File created: October 8, 2024
********************************************
This file is used to verify the strength of the password based off the numbers of characters and the number of character
groups it contains. The strength is an integer that ranges from 0 to 5, in which 5 is the strongest. The final function
calculates the strength and returns the value.
"""

# Character lists
LETTERS = "abcdefghijklmnopqrstuvwxyz"
DIGITS = "0123456789"
SYMBOLS = "!@#$%^&*/?-+=,.|~"

# This function is responsible for checking the number of groups in the password.
def count_groups(pwd):
    # Variable
    groups = "0000"

    # For loop goes through the letters of the password.
    for i in pwd:
        # For loop goes through the provided alphabet list.
        for j in LETTERS:
            # If statement to check for lowercase letters.
            if i == j:
                groups = "1" + groups[1:4]
            # Else if statement to check for uppercase letters.
            elif i == j.upper():
                groups = groups[0] + "1" + groups[2:4]
        # For loop goes through the provided number list.
        for j in DIGITS:
            # If statement to check for digits.
            if i == j:
                groups = groups[0:2] + "1" + groups[3]
        # For loop goes through the provided symbol list.
        for j in SYMBOLS:
            # If statement to check for symbols.
            if i == j:
                groups = groups[0:3] + "1"

    # Returns the final result
    return int(groups[0]) + int(groups[1]) + int(groups[2]) + int(groups[3])


# This function is responsible for checking the strength of the password based off:
# the number of groups used and the number of characters in the password.
def password_strength(pwd):
    # Variables
    pwd_length = len(pwd)
    num_groups = count_groups(pwd)
    strength = 0

    # Check the strength based on the number of groups in the password.
    if num_groups == 0 or num_groups == 1:
        strength = 2
    elif num_groups == 2 or num_groups == 3:
        strength = 3
    else:
        strength = 4

    # Final check for the strength based on the number of characters in the password.
    if (" " not in pwd) and ("\n" not in pwd) and ("\t" not in pwd):
        if pwd_length < 5:
            return 0
        elif (pwd_length >= 5) and (pwd_length <= 8):
            return strength - 1
        elif (pwd_length >= 9) and (pwd_length <= 12):
            return strength
        else:
            return strength + 1
    # Password strength is automatically 0 when the following is found in the password:
    # whitespace (" "), tab ("\t"), and newline ("\n").
    else:
        return 0

