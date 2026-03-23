"""
********************************************
CS 1026 - Assignment 3 - Youtube Emotions
Code by: Malik Alghneimin
Student ID: Malghnei
File created: November 19, 2024
********************************************
The program is used to setup functions used in "main.py" for analyzing emotions in a comment list through 
the usage of keywords associated with emotions and so on. The creation of a report file is also done in this program.
"""
EMOTIONS = ['anger', 'joy', 'fear', 'trust', 'sadness', 'anticipation']
ALPHABETS = ['a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z']

# This function is used to clean the given comment by removing unnesseccary spacings and symbols.
def clean_text(comment):
    # Remove unnessecary spaces.
    comment = comment.strip()
    # New variable for output.
    newcomment = ""
    # For loop to check the characters in the comment.
    for i in range(0,len(comment)):
        # Check if the character is in the alphabet.
        if comment[i].lower() not in ALPHABETS:
            newcomment += " "
        else:   
            newcomment += comment[i].lower()
         
    # Return the clean text.
    return newcomment

# This function is used to create a keyword dictionary by the given keyword file.
def make_keyword_dict(keyword_file_name):
    # Opening the keyword tsv file.
    file = open(keyword_file_name, "r")
    # Declaring a dictionary variable for output and reading the file.
    dict = {}
    lines = file.readlines()
    # For loop to setting up the dictionary.
    for i in range(0,len(lines)):
        # Cleaning and splitting the lists.
        newline = lines[i].strip()
        newline = newline.split("\t")
        dict[newline[0]] = {}
        # Adding new dictionary element.
        for j in range(0,len(EMOTIONS)):
            dict[newline[0]][EMOTIONS[j]] = int(newline[j + 1])
        
    # Return the finalized keyword dictionary.
    return dict

# This function is used to check the comments words and find the most emotions found inside the comments through keywords.
def classify_comment_emotion(comment, keywords):
    # Declaring variables.
    highest = ["anger", 0]
    total_emotions = {}
    # Declaring and cleaning variable for the comment.
    cleaned_comment = clean_text(comment)
    cleaned_comment = cleaned_comment.split(" ")
    # Checking every word in the comment for words inside the keyword dictionary.
    # If the word in the comment is in the dictionary, the emotions associated with the word is added to the total emotions.
    for i in cleaned_comment:
        if i in keywords:
            for j in EMOTIONS:
                if j not in total_emotions:
                    total_emotions[j] = keywords[i][j]
                else:
                    total_emotions[j] += keywords[i][j]


    # Tracks the highest value inside the total emotions.
    for i in total_emotions:
        if total_emotions[i] > highest[1]:
            highest[1] = total_emotions[i]
            highest[0] = i
    # Returns the highest emotions found in the comment.
    return highest[0]
    

# This function is used to create a comment list according to the country filter chosen by the user.
def make_comments_list(filter_country, comments_file_name):
    # Declaring variables
    file = open(comments_file_name, "r")
    lines = file.readlines()
    list = []

    # Checks every comments inside the comments list and filters them according to the country.
    # Or all the comments are considered if the user chooses to do all countries.
    for i in range(0,len(lines)):
        newline = lines[i].strip()
        newline = newline.split(',')
        # User's input check (valid country or "all")
        if (filter_country.lower() == newline[2]) or (filter_country.lower() == "all"):
            list.append({'comment_id': int(newline[0]), 'username': newline[1], 'country': newline[2], 'text': newline[3].strip()})
    
    # Return the filtered list according to the user
    return list

# This function creates a ".txt" file with a report on the most common emotions in the comment list.
def make_report(comment_list, keywords, report_filename):
    # Error checking if list is empty.
    if len(comment_list) == 0:
        raise RuntimeError("No comments in dataset!")
    # Declaring variables
    total_emotions = {"anger": 0, "joy": 0, "fear": 0, "trust": 0, "sadness": 0, "anticipation": 0}
    highest = ["none", 0]
    report = open(report_filename, "x")
    sum_size = 0

    # Goes through every comment in the comment list and checks for the most common emotion.
    for i in range(0, len(comment_list)):
        # Most commmon emotion of the comment.
        comment_emotion = classify_comment_emotion(comment_list[i]["text"], keywords)
        # Most common emotion is added to the dictionary of total emotions.
        if comment_emotion != " ":
            total_emotions[comment_emotion] += 1
    
    # Check for the most common emotion compared to the rest in the appropriate order.
    for i in range(0,len(EMOTIONS)):
        sum_size += total_emotions[EMOTIONS[i]]
        if total_emotions[EMOTIONS[i]] > highest[1]:
            highest[0] = EMOTIONS[i]
            highest[1] = total_emotions[EMOTIONS[i]]
    
    # Formats text in the report file.
    report.write("Most common emotion: {0}\n\nEmotion Totals".format(highest[0]))
    for i in total_emotions:
        report.write("\n{0}: {1} ({2}%)".format(i, total_emotions[i], round(float('{:.4}'.format((total_emotions[i] / sum_size) * 100)), 2)))
    
    # Returns the most common emotion in the provided comment list.
    return highest[0]

