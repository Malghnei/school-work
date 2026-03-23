/*
 * 2211 - Assignment 2 - Part 2
 * Name: intToEnglish.c
 * Description: This program converts integers (1-999) to their English word representation.
 * Author: Malik Alghneimin
 * Student ID: 
 * Date: 2024-10-07
*/

#include <stdio.h>

// Arrays for number words
const char *ones[] = {"", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
const char *teens[] = {"ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};
const char *tens[] = {"", "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"};

// Main function
int main() {
    // Variable declaration
    int input = -1;
    // Loop until user decides to quit
    while (input != 0) {
        // Prompt user for input
        printf("Please enter a value (1-999, 0 to quit): ");
        scanf("%d", &input);
        // Handle quitting and invalid input
        if (input == 0) {
            break;
        }
        // Validate input range (1-999)
        if (input < 0 || input > 999) {
            printf("Number out of range. Please try again.\n");
            continue; // Skip to next iteration
        }

        // Convert number to English words
        printf("You entered the number ");
        // Hundreds place
        if (input >= 100) {
            printf("%s hundred", ones[input / 100]);
            // If there are tens or ones, add "and"
            input %= 100;
            if (input) printf(" and ");
        }
        // Tens and ones place
        if (input >= 20) {
            printf("%s", tens[input / 10]);
            // If there's a ones place, add a hyphen and the ones word
            if (input % 10) printf("-%s", ones[input % 10]); 
        } else if (input >= 10) {
            // -teens (10-19)
            printf("%s", teens[input - 10]);
        } else if (input > 0) {
            // Ones place (1-9)
            printf("%s", ones[input]);
        }
        printf("\n");

        input = -1; // Reset input for next iteration
    }
}