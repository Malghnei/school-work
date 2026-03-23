// Include libraries and files.
#include "snackaroo.h"
#include "snackaroo_dish.h"

// Helper function that searches for a dish by code.
Dish* find_dish_by_code(Dish *head, int code) {
    Dish *current = head;

    while (current != NULL) {
        if (current->code == code) {
            return current;
        }
        current = current->next;
    }

    return NULL;
}

// Function that adds new dish to the linked list.
void insert_dish(Dish **head) {
    int code;
    float rating, price;

    // Asks user for dish code.
    printf("\tEnter dish code: ");
    scanf("%d", &code);
    clear_input_buffer();


    // Check for errors.
    // Check if code is a negative value.
    if (code < 0) {
        printf("Dish code must be positive.\n");
        return;
    }

    // Check if code is in list.
    if (find_dish_by_code(*head, code) != NULL) {
        printf("Dish already exists.\n");
        return;
    }

    // Allocates memory for new dish.
    Dish *new_dish = (Dish*)malloc(sizeof(Dish));
    if (new_dish == NULL) {
        printf("Error");
        return;
    }

    new_dish->code = code;

    // Read dish name with fgets for proper string handling
    printf("\tEnter dish name: ");
    fgets(new_dish->dish_name, MAX_DISH_NAME, stdin);
    new_dish->dish_name[strcspn(new_dish->dish_name, "\n")] = '\0';
    if (strlen(new_dish->dish_name) == MAX_DISH_NAME - 1) {
        clear_input_buffer();
    }
    
    // Read restaurant name
    printf("\tEnter restaurant name: ");
    fgets(new_dish->restaurant_name, MAX_RESTAURANT_NAME, stdin);
    new_dish->restaurant_name[strcspn(new_dish->restaurant_name, "\n")] = '\0';
    if (strlen(new_dish->restaurant_name) == MAX_RESTAURANT_NAME - 1) {
        clear_input_buffer();
    }
    
    // Read and validate rating
    printf("\tEnter dish rating: ");
    scanf("%f", &rating);
    clear_input_buffer();
    
    if (rating < 0.0 || rating > 10.0) {
        printf("Rating must be between 0.0 and 10.0.\n\n");
        free(new_dish);
        return;
    }
    new_dish->rating = rating;
    
    // Read and validate price
    printf("\tEnter dish price: ");
    scanf("%f", &price);
    clear_input_buffer();
    
    if (price < 0.0) {
        printf("Price must be positive.\n\n");
        free(new_dish);
        return;
    }
    new_dish->price = price;
    
    // Insert at beginning of list
    new_dish->next = *head;
    *head = new_dish;
    
    printf("Dish added successfully.\n\n");
}

// Function that searches for a dish with given code.
void search_dish(Dish *head) {
    int code;
    
    // Ask user for dish code.
    printf("\tEnter dish code: ");
    scanf("%d", &code);
    clear_input_buffer();
    
    // Find and check if dish exists.
    Dish *dish = find_dish_by_code(head, code);
    if (dish == NULL) {
        printf("Dish not found.\n\n");
        return;
    }
    
    // Print dish header and dish.
    printf("%-12s%-26s%-32s%-26s%-15s\n", "Dish Code", "Dish Name", "Restaurant Name", "Dish Rating", "Dish Price");
    printf("%-12d%-26s%-32s%-26.1f$%.2f\n", dish->code, dish->dish_name, dish->restaurant_name, dish->rating, dish->price);
    printf("\n");
}

// Function that updates the dish with the given dish code.
void update_dish(Dish *head) {
    int code;
    float rating, price;
    
    // Ask user for dish code.
    printf("\tEnter dish code: ");
    scanf("%d", &code);
    clear_input_buffer();
    
    // Find and check if dish exists.
    Dish *dish = find_dish_by_code(head, code);
    if (dish == NULL) {
        printf("Dish not found.\n\n");
        return;
    }
    
    // Update all fields.
    // Dish name.
    printf("\tEnter dish name: ");
    fgets(dish->dish_name, MAX_DISH_NAME, stdin);
    dish->dish_name[strcspn(dish->dish_name, "\n")] = '\0';
    if (strlen(dish->dish_name) == MAX_DISH_NAME - 1) {
        clear_input_buffer();
    }
    
    // Restaurant name.
    printf("\tEnter restaurant name: ");
    fgets(dish->restaurant_name, MAX_RESTAURANT_NAME, stdin);
    dish->restaurant_name[strcspn(dish->restaurant_name, "\n")] = '\0';
    if (strlen(dish->restaurant_name) == MAX_RESTAURANT_NAME - 1) {
        clear_input_buffer();
    }
    
    // Dish rating.
    printf("\tEnter dish rating: ");
    scanf("%f", &rating);
    clear_input_buffer();
    
    if (rating < 0.0 || rating > 10.0) {
        printf("Rating must be between 0.0 and 10.0.\n\n");
        return;
    }
    dish->rating = rating;
    
    // Dish price.
    printf("\tEnter dish price: ");
    scanf("%f", &price);
    clear_input_buffer();
    
    if (price < 0.0) {
        printf("Price must be positive.\n\n");
        return;
    }
    dish->price = price;
    
    printf("Dish updated successfully.\n\n");
}

// Function that prints all the dishes.
void print_all_dishes(Dish *head) {
    // Prints header for dishes.
    printf("%-12s%-26s%-32s%-26s%-15s\n", "Dish Code", "Dish Name", "Restaurant Name", "Dish Rating", "Dish Price");
    
    // Prints all the dishes.
    Dish *current = head;
    while (current != NULL) {
        printf("%-12d%-26s%-32s%-26.1f$%.2f\n",current->code, current->dish_name, current->restaurant_name, current->rating, current->price);
        current = current->next;
    }
    printf("\n");
}

// Function that removes the dish with the given dish code.
void erase_dish(Dish **head) {
    int code;
    
    // Ask user for dish code.
    printf("\tEnter dish code: ");
    scanf("%d", &code);
    clear_input_buffer();
    
    // Handle empty list.
    if (*head == NULL) {
        printf("Dish not found.\n\n");
        return;
    }
    
    Dish *current = *head;
    Dish *previous = NULL;
    
    // Search for dish to delete.
    while (current != NULL && current->code != code) {
        previous = current;
        current = current->next;
    }
    
    // Check if dish exists.
    if (current == NULL) {
        printf("Dish not found.\n\n");
        return;
    }
    
    // Delete head node.
    if (previous == NULL) {
        *head = current->next;
    } else {
        // Delete middle or end node.
        previous->next = current->next;
    }
    
    // Free the allocated memory for the dish.
    free(current);
    printf("Dish deleted successfully.\n\n");
}

// Function that frees all the memory allocated for dishes.
void free_all_dishes(Dish **head) {
    Dish *current = *head;
    Dish *next;
    
    while (current != NULL) {
        next = current->next;
        free(current);
        current = next;
    }
    
    *head = NULL;
}

// Function that handles the dish menu.
void dish_menu(Dish **head) {
    char command;
    
    // Read user input.
    printf("\nDish Menu - Enter command (i/s/u/p/e): ");
    scanf(" %c", &command);
    clear_input_buffer();
    
    // Switch case for user input.
    switch (command) {
        case 'i': // Insert new dish.
            insert_dish(head);
            break;
        case 's': // Search for dish.
            search_dish(*head);
            break;
        case 'u': // Update existing dish.
            update_dish(*head);
            break;
        case 'p': // Print all dishes.
            print_all_dishes(*head);
            break;
        case 'e': // Remove a dish.
            erase_dish(head);
            break;
        default: // Default case.
            printf("Invalid command.\n\n");
    }
}



