"""
********************************************
CS 1026 - Assignment 3 - Youtube Emotions
Code by: Malik Alghneimin
Student ID: Malghnei
File created: November 19, 2024
********************************************
The program identifies the most common emotions that the provided comment list contains and formats it into a report inside a 
".txt" file. This file is used to run functions created by the emotions.py and process them for user usage, aswell as checks for 
various exceptions like ValueError IOError as a way to prevent wrong inputs.
"""

import os.path
from emotions import *

VALID_COUNTRIES = ['bangladesh', 'brazil', 'canada', 'china', 'egypt',
                   'france', 'germany', 'india', 'iran', 'japan', 'mexico',
                   'nigeria', 'pakistan', 'russia', 'south korea', 'turkey',
                   'united kingdom',  'united states']

# This functions contain input prompts for the user to input appropriate values.
def ask_user_for_input():
    user_input = tuple()
    # Keyword file name input.
    user_input += (input("Input keyword file (ending in .tsv): "),)
    # Check for errors.
    # Incorrect file extension.
    if ".tsv" not in user_input[0]: 
        raise ValueError("Keyword file does not end in .tsv!")
    # File does not exist.
    elif os.path.exists(user_input[0]) == False:
        raise IOError("{0} does not exists!".format(user_input[0]))

    # Comment file name input.
    user_input += (input("Input comment file (ending in .csv): "),)
    # Check for errors.
    # Incorrect file extension.
    if ".csv" not in user_input[1]:
        raise ValueError("Comment file does not end in .csv!")
    # File does not exist.
    elif os.path.exists(user_input[1]) == False:
        raise IOError("{0} does not exists!".format(user_input[1]))
    
    # Country name input.
    user_input += (input("Input a country to analyze (or \"all\" for all countries): ").lower(),)
    # Check for errors.
    # Not valid country
    if (user_input[2] not in VALID_COUNTRIES) and (user_input[2] != "all"):
        raise ValueError("{0} is not a valid country to filter by!".format(user_input[2]))
    
    # Report file name input.
    user_input += (input("Input the name of the report file (ending in .txt): "),)
    # Check for errors.
    # Incorrect file extensions.
    if ".txt" not in user_input[3]:
        raise ValueError("Report file does not end in .txt!")
    # File does exist.
    elif os.path.exists(user_input[3]) == True:
        raise IOError("{0} exists, the report file can not already exist!".format(user_input[3]))
    
    # Return the inputs through a tuple
    return user_input

# This function is the main function
def main():
    # Infinite loop until all inputs are appropriate.
    while True:
        # Try-except blocks for error handling.
        try:
            # user input function.
            tuple_list = ask_user_for_input()
            keyword_file = make_keyword_dict(tuple_list[0])
            comment_file = make_comments_list(tuple_list[2], tuple_list[1])
            report = make_report(comment_file, keyword_file, tuple_list[3])

            # Print most common emotion according to the report file.
            print("Most common emotion is: ", report)
            break
        # IOError for existing/non-existing files.
        except IOError as e:
            print(f"Error: {e}")
        # ValueError for incorrect file extensions and incorrect values.
        except ValueError as e:
            print(f"Error: {e}")
        # RuntimeError for empty datasets.
        except RuntimeError as e:
            print(f"Error: {e}")

# Main
if __name__ == "__main__":
     main()