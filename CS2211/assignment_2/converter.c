/*
 * 2211 - Assignment 2 - Part 1
 * Name: converter.c
 * Description: This program performs unit conversions based on user input.
 * Author: Malik Alghneimin
 * Student ID: 
 * Date: 2024-10-07
*/

#include <stdio.h>

// Main function
int main() {
    // Variable declarations
    int choice = 0;
    char option;
    float conversion;
    // Main loop for user interaction
    while (choice != 5) {
        // Display menu and get user choice
        printf("Select the conversion type\n1 - Grames and Ounces\n2 - Square meters and Square Yards\n3 - Litres and Pints\n4 - Meter and Feet\n5 - Quit\n");
        scanf("%d", &choice);
        // Handle user choice
        switch (choice) {
            // Case 1 - Grams and Ounces
            case 1:
                printf("Grames and Ounces conversion selected.\n");
                // Get specific conversion direction
                printf("Convert from Grams to Ounce (G) or Ounce to Grams (O)?\n");
                scanf(" %c", (char*)&option);
                // Determine conversion factor based on user input
                if (option == 'G' || option == 'g') {
                    conversion = 0.03527;
                } else if (option == 'O' || option == 'o') {
                    conversion = 1 / 0.03527;
                } else {
                    printf("Invalid option. Defaulting to Grams to Ounces.\n");
                    conversion = 0.03527;
                }
                break;
            // Case 2 - Square meters and Square Yards
            case 2:
                printf("Square meters and Square Yards conversion selected.\n");
                // Get specific conversion direction
                printf("Convert from Square Meters to Square Yards (M) or Square Yards to Square Meters (Y)?\n");
                scanf(" %c", (char*)&option);
                // Determine conversion factor based on user input
                if (option == 'M' || option == 'm') {
                    conversion = 1.19599;
                } else if (option == 'Y' || option == 'y') {
                    conversion = 1 / 1.19599;
                } else {
                    printf("Invalid option. Defaulting to Square Meters to Square Yards.\n");
                    conversion = 1.19599;
                }
                break;
            // Case 3 - Litres and Pints
            case 3:
                printf("Litres and Pints conversion selected.\n");
                // Get specific conversion direction
                printf("Convert from Litres to Pints (L) or Pints to Litres (P)?\n");
                scanf(" %c", (char*)&option);
                // Determine conversion factor based on user input
                if (option == 'L' || option == 'l') {
                    conversion = 2.11338;
                } else if (option == 'P' || option == 'p') {
                    conversion = 1 / 2.11338;
                } else {
                    printf("Invalid option. Defaulting to Litres to Pints.\n");
                    conversion = 2.11338;
                }
                break;
            // Case 4 - Meter and Feet
            case 4:
                printf("Meter and Feet conversion selected.\n");
                // Get specific conversion direction
                printf("Convert from Meters to Feet (M) or Feet to Meters (F)?\n");
                scanf(" %c", (char*)&option);
                // Determine conversion factor based on user input
                if (option == 'M' || option == 'm') {
                    conversion = 3.28084;
                } else if (option == 'F' || option == 'f') {
                    conversion = 1 / 3.28084;
                } else {
                    printf("Invalid option. Defaulting to Meters to Feet.\n");
                    conversion = 3.28084;
                }
                break;
            // Case 5 - Quit
            case 5:
                printf("Quitting the program.\n");
                break;
            // Default case for invalid choice
            default:
                printf("Invalid choice. Please select a valid option.\n");
        }

        // Perform conversion if a valid choice was made
        if (choice >= 1 && choice <= 4) {
            float value;
            // Get the value to convert from the user
            printf("Enter the value to convert: ");
            scanf("%f", &value);
            // Calculate and display the result
            float result = value * conversion;
            printf("Your conversion is:  %.2f\n", result);
        }
    }
}