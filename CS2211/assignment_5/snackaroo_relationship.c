// Include libraries and files.
#include "snackaroo.h"
#include "snackaroo_relationship.h"

// Helper function to generate unique ID for new relationship.
int get_next_relationship_id(dish_driver_code *head) {
    int max_id = 0;
    dish_driver_code *current = head;

    while(current != NULL) {
        if (current->relationship_id > max_id) {
            max_id = current->relationship_id;
        }
        current = current->next;
    }

    return max_id + 1;
}

// Helper function to search for relationship by given ID.
dish_driver_code* find_relationship_by_id(dish_driver_code *head, int id) {
    dish_driver_code *current = head;
    
    while (current != NULL) {
        if (current->relationship_id == id) {
            return current;
        }
        current = current->next;
    }

    return NULL;
}

void insert_relationship(dish_driver_code **head, Dish *dish_head, Driver *driver_head) {
    int dish_code, driver_code;

    // Ask user for dish code.
    printf("\tEnter dish code: ");
    scanf("%d", &dish_code);
    clear_input_buffer();

    // Check if dish code exists.
    if (find_dish_by_code(dish_head, dish_code) == NULL) {
        printf("Dish code does not exist.\n\n");
        return;
    }

    // Ask user for driver code.
    printf("\tEnter driver code: ");
    scanf("%d", &driver_code);
    clear_input_buffer();

    // Check if driver code exists.
    if (find_driver_by_code(driver_head, driver_code) == NULL) {
        printf("Driver code does not exist.\n\n");
        return;
    }

    // Allocate memory for new relationship.
    dish_driver_code *new_rel = (dish_driver_code*)malloc(sizeof(dish_driver_code));
    if (new_rel == NULL) {
        printf("Failed.\n\n");
        return;
    }

    // Assign the values to the struct members.
    new_rel->relationship_id = get_next_relationship_id(*head);
    new_rel->dish_code = dish_code;
    new_rel->driver_code = driver_code;

    new_rel->next = *head;
    *head = new_rel;

    // Inform user that the relationship is created successfully.
    printf("Relationship created successfully. ID: %d\n\n", new_rel->relationship_id);
}

// Function to search for relationship with given ID and prints the infromation.
void search_relationship(dish_driver_code *head) {
    int id;

    // Ask user for relationship ID.
    printf("\tEnter relationship ID: ");
    scanf("%d", &id);
    clear_input_buffer();

    // Find the relationship with the ID given.
    dish_driver_code *rel = find_relationship_by_id(head, id);
    // Check if relationship exists.
    if (rel == NULL) {
        printf("Relationship not found.\n\n");
        return;
    }

    // Prints the information about the relationship.
    printf("%-15s%-15s%-15s\n", "Relationship ID", "Dish Code", "Driver Code");
    printf("%-15d%-15d%-15d\n", rel->relationship_id, rel->dish_code, rel->driver_code);
    printf("\n");
}

// Function that prints all relationships.
void print_all_relationships(dish_driver_code *head) {
    // Check if the given relationship is empty.
    if (head == NULL) {
        printf("No relationship found.\n\n");
        return;
    }

    // Prints the header for the relationships.
    printf("%-15s%-15s%-15s\n", "Relationship ID", "Dish Code", "Driver Code");

    // Print the rest of the relationships.
    dish_driver_code *current = head;
    while (current != NULL) {
        printf("%-15d%-15d%-15d\n", current->relationship_id, current->dish_code, current->driver_code);
        current = current->next;
    }
    printf("\n");
}

// Function that prints all the dishes delivered by a specific driver.
void print_dishes_for_driver(dish_driver_code *head, Dish *dish_head) {
    int driver_code;
    int found = 0;

    // Ask user for driver code.
    printf("\tEnter driver code: ");
    scanf("%d", &driver_code);
    clear_input_buffer();

    // Prints the header for the dish(es).
    printf("%-12s%-26s%-32s%-26s%-15s\n", "Dish Code", "Dish Name", "Restaurant Name", "Dish Rating", "Dish Price");

    // Go through the relationship list.
    dish_driver_code *current = head;
    while (current != NULL) {
        if (current->driver_code == driver_code) {
            Dish *dish = find_dish_by_code(dish_head, current->dish_code);
            // Check if dish exists.
            if (dish != NULL) {
                printf("%-12d%-26s%-32s%-26.1f$%.2f\n",
                       dish->code, dish->dish_name, dish->restaurant_name, 
                       dish->rating, dish->price);
                found = 1;
            }
        }
        current = current->next;
    }

    // Check for if there is no dishes.
    if (!found) {
        printf("No dishes found for this driver.\n");
    }
    printf("\n");
}

// Function that prints all the drivers delivered a specific dish.
void print_drivers_for_dish(dish_driver_code *head, Driver *driver_head) {
    int dish_code;
    int found = 0;

    printf("\tEnter dish code: ");
    scanf("%d", &dish_code);
    clear_input_buffer();

    // Prints the header for the driver(s).
    printf("%-12s%-26s%-20s%-20s\n", "Driver Code", "Driver Name", "Vehicle Colour", "Licence Plate");
    
    // Go through the relationship list.
    dish_driver_code *current = head;
    while (current != NULL) {
        if (current->dish_code == dish_code) {
            Driver *driver = find_driver_by_code(driver_head, current->driver_code);
            // Check if driver exists.
            if (driver != NULL) {
                printf("%-12d%-26s%-20s%-20s\n",driver->code, driver->name, get_colour_name(driver->vehicle_colour), driver->licence_plate);
                found = 1;
            }
        }
        current = current->next;
    }

    // Check for if there is no dishes.
    if (!found) {
        printf("No drivers found for this dish.\n");
    }
    printf("\n");
}

// Function that removes a relationship.
void erase_relationship(dish_driver_code **head) {
    int id;
    
    // Ask user for the relationship ID.
    printf("\tEnter relationship ID: ");
    scanf("%d", &id);
    clear_input_buffer();
    
    // Check if the linked list is empty.
    if (*head == NULL) {
        printf("Relationship not found.\n\n");
        return;
    }
    
    dish_driver_code *current = *head;
    dish_driver_code *previous = NULL;
    
    // Go through the linked list until relationship with given ID.
    while (current != NULL && current->relationship_id != id) {
        previous = current;
        current = current->next;
    }
    
    // Check if the relationship exists.
    if (current == NULL) {
        printf("Relationship not found.\n\n");
        return;
    }
    
    // Check if there exists a relationship after the current one.
    if (previous == NULL) {
        *head = current->next;
    } else {
        previous->next = current->next;
    }
    
    // Free memory allocated towards the relationship.
    free(current);
    printf("Relationship deleted successfully.\n\n");
}

// Function that frees all memory allocated for all relationships.
void free_all_relationships(dish_driver_code **head) {
    dish_driver_code *current = *head;
    dish_driver_code *next;
    
    // Go through all relatioships.
    while (current != NULL) {
        next = current->next;
        free(current);
        current = next;
    }

    *head = NULL;
}

// Function that saves all relationships to a binary file.
void dump_relationships(dish_driver_code *head) {
    char filename[100];
    
    // Ask user for output file name.
    printf("\tEnter output filename: ");
    fgets(filename, sizeof(filename), stdin);
    filename[strcspn(filename, "\n")] = '\0';
    
    // Opens and check if file exists.
    FILE *file = fopen(filename, "wb");
    if (file == NULL) {
        printf("Error opening file for writing.\n\n");
        return;
    }
    
    int count = 0;
    dish_driver_code *current = head;
    
    // Write each relationship to file
    while (current != NULL) {
        fwrite(current, sizeof(dish_driver_code), 1, file);
        count++;
        current = current->next;
    }
    
    // Close the output file.
    fclose(file);
    printf("Dumped %d relationships to %s\n\n", count, filename);
}

// Function that loads relationships from a binary file.
void restore_relationships(dish_driver_code **head, Dish *dish_head, Driver *driver_head) {
    char filename[100];
    
    // Ask user for input file name.
    printf("\tEnter input filename: ");
    fgets(filename, sizeof(filename), stdin);
    filename[strcspn(filename, "\n")] = '\0';
    
    // Check if file exists.
    FILE *file = fopen(filename, "rb");
    if (file == NULL) {
        printf("Error opening file for reading.\n\n");
        return;
    }
    
    dish_driver_code temp;
    int count = 0;
    int skipped = 0;
    
    // Read relationships from file
    while (fread(&temp, sizeof(dish_driver_code), 1, file) == 1) {
        // Validate referential integrity
        if (find_dish_by_code(dish_head, temp.dish_code) == NULL) {
            printf("Warning: Skipping relationship %d - dish %d does not exist.\n", temp.relationship_id, temp.dish_code);
            skipped++;
            continue;
        }
        
        if (find_driver_by_code(driver_head, temp.driver_code) == NULL) {
            printf("Warning: Skipping relationship %d - driver %d does not exist.\n", temp.relationship_id, temp.driver_code);
            skipped++;
            continue;
        }
        
        // Allocate and insert new relationship
        dish_driver_code *new_rel = (dish_driver_code*)malloc(sizeof(dish_driver_code));
        if (new_rel == NULL) {
            printf("Memory allocation failed.\n");
            break;
        }
        
        *new_rel = temp;
        new_rel->next = *head;
        *head = new_rel;
        count++;
    }
    
    // Close the input file
    fclose(file);
    printf("Restored %d relationships from %s", count, filename);
    if (skipped > 0) {
        printf(" (%d skipped due to integrity violations)", skipped);
    }
    printf("\n\n");
}

// Function that handles the relationship menu.
void relationship_menu(dish_driver_code **rel_head, Dish *dish_head, Driver *driver_head) {
    char command;
    
    // Output the options to the user.
    printf("\nRelationship Menu - Enter command (i/s/p/f/g/e/d/r): ");
    printf("\n  i - Insert relationship");
    printf("\n  s - Search relationship");
    printf("\n  p - Print all relationships");
    printf("\n  f - Print dishes For a driver");
    printf("\n  g - Print drivers for a dish (G for dish)");
    printf("\n  e - Erase relationship");
    printf("\n  d - Dump to file");
    printf("\n  r - Restore from file");
    printf("\nChoice: ");

    // Read user input
    scanf(" %c", &command);
    clear_input_buffer();
    
    // Switch case for user input.
    switch (command) {
        case 'i': // Insert new relationship.
            insert_relationship(rel_head, dish_head, driver_head);
            break;
        case 's': // Search for relationship.
            search_relationship(*rel_head);
            break;
        case 'p': // Prints all relationships
            print_all_relationships(*rel_head);
            break;
        case 'f': // Prints all dishes for driver.
            print_dishes_for_driver(*rel_head, dish_head);
            break;
        case 'g': // Prints all drivers for dish.
            print_drivers_for_dish(*rel_head, driver_head);
            break;
        case 'e': // Removes a relationship.
            erase_relationship(rel_head);
            break;
        case 'd': // Dumps the entire database to a file.
            dump_relationships(*rel_head);
            break;
        case 'r': // Restores the entire database from a file.
            restore_relationships(rel_head, dish_head, driver_head);
            break;
        default: // Default case.
            printf("Invalid command.\n\n");
    }
}




