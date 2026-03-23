// Include libraries and files.
#include "snackaroo.h"
#include "snackaroo_driver.h"
#include <ctype.h>

// Helper function that converts colour code to string.
const char* get_colour_name(int colour_code) {
    static const char *colours[] = {
        "red", "green", "blue", "grey", "white", "black", "other"
    };
    
    if (colour_code >= 0 && colour_code <= 6) {
        return colours[colour_code];
    }
    return "unknown";
}

// Helper function that checks if licence plate is valid.
int validate_licence_plate(const char *plate) {
    int len = strlen(plate);
    
    // Check length constraints (2-8 characters).
    if (len < 2 || len > 8) {
        return 0;
    }
    
    // Check each character.
    for (int i = 0; i < len; i++) {
        if (!isalnum(plate[i]) && plate[i] != ' ') {
            return 0;
        }
    }
    
    return 1;
}

// Helper function that finds a driver by driver code.
Driver* find_driver_by_code(Driver *head, int code) {
    Driver *current = head;
    while (current != NULL) {
        if (current->code == code) {
            return current;
        }
        current = current->next;
    }
    return NULL;
}

// Function that inserts a new driver.
void insert_driver(Driver **head) {
    int code, colour;
    
    // Ask user for driver code.
    printf("\tEnter driver code: ");
    scanf("%d", &code);
    clear_input_buffer();
    
    // Check for errors.
    // Check if code is a negative value.
    if (code < 0) {
        printf("Driver code must be positive.\n\n");
        return;
    }
    
    // Check if code already exists.
    if (find_driver_by_code(*head, code) != NULL) {
        printf("Driver already exists.\n\n");
        return;
    }
    
    // Allocate memory for new driver
    Driver *new_driver = (Driver*)malloc(sizeof(Driver));
    if (new_driver == NULL) {
        printf("Memory allocation failed.\n\n");
        return;
    }
    
    new_driver->code = code;
    
    // Ask user for driver name.
    printf("\tEnter driver name: ");
    fgets(new_driver->name, MAX_DRIVER_NAME, stdin);
    new_driver->name[strcspn(new_driver->name, "\n")] = '\0';
    if (strlen(new_driver->name) == MAX_DRIVER_NAME - 1) {
        clear_input_buffer();
    }
    
    // Ask user for vehicle colour.
    printf("\tEnter vehicle colour (0=red, 1=green, 2=blue, 3=grey, 4=white, 5=black, 6=other): ");
    scanf("%d", &colour);
    clear_input_buffer();
    
    // Check if colour option is valid.
    if (colour < 0 || colour > 6) {
        printf("Invalid colour code. Must be 0-6.\n\n");
        free(new_driver);
        return;
    }
    new_driver->vehicle_colour = colour;
    
    // Ask user for licence plate.
    printf("\tEnter licence plate: ");
    fgets(new_driver->licence_plate, MAX_LICENCE_PLATE, stdin);
    new_driver->licence_plate[strcspn(new_driver->licence_plate, "\n")] = '\0';
    if (strlen(new_driver->licence_plate) == MAX_LICENCE_PLATE - 1) {
        clear_input_buffer();
    }
    
    // Check if licence plate is valid.
    if (!validate_licence_plate(new_driver->licence_plate)) {
        printf("Invalid licence plate. Must be 2-8 alphanumeric characters or spaces.\n\n");
        free(new_driver);
        return;
    }
    
    // Insert at beginning of list
    new_driver->next = *head;
    *head = new_driver;
    
    printf("Driver added successfully.\n\n");
}

// Function that searches for a driver with given driver code.
void search_driver(Driver *head) {
    int code;
    
    // Ask user for driver code.
    printf("\tEnter driver code: ");
    scanf("%d", &code);
    clear_input_buffer();
    
    // Find and check if driver exists.
    Driver *driver = find_driver_by_code(head, code);
    if (driver == NULL) {
        printf("Driver not found.\n\n");
        return;
    }
    
    // Print driver header and driver.
    printf("%-12s%-26s%-20s%-20s\n", "Driver Code", "Driver Name", "Vehicle Colour", "Licence Plate");
    printf("%-12d%-26s%-20s%-20s\n",driver->code, driver->name, get_colour_name(driver->vehicle_colour), driver->licence_plate);
    printf("\n");
}

// Function that updates the driver with the given dish code.
void update_driver(Driver *head) {
    int code, colour;
    
    // Ask user for driver code.
    printf("\tEnter driver code: ");
    scanf("%d", &code);
    clear_input_buffer();
    
    // Find and check if driver exists.
    Driver *driver = find_driver_by_code(head, code);
    if (driver == NULL) {
        printf("Driver not found.\n\n");
        return;
    }
    
    // Update all fields.
    // Driver name.
    printf("\tEnter driver name: ");
    fgets(driver->name, MAX_DRIVER_NAME, stdin);
    driver->name[strcspn(driver->name, "\n")] = '\0';
    if (strlen(driver->name) == MAX_DRIVER_NAME - 1) {
        clear_input_buffer();
    }
    
    // Vehicle colour.
    printf("\tEnter vehicle colour (0=red, 1=green, 2=blue, 3=grey, 4=white, 5=black, 6=other): ");
    scanf("%d", &colour);
    clear_input_buffer();
    
    if (colour < 0 || colour > 6) {
        printf("Invalid colour code. Must be 0-6.\n\n");
        return;
    }
    driver->vehicle_colour = colour;
    
    // Licence plate.
    printf("\tEnter licence plate: ");
    fgets(driver->licence_plate, MAX_LICENCE_PLATE, stdin);
    driver->licence_plate[strcspn(driver->licence_plate, "\n")] = '\0';
    if (strlen(driver->licence_plate) == MAX_LICENCE_PLATE - 1) {
        clear_input_buffer();
    }
    
    if (!validate_licence_plate(driver->licence_plate)) {
        printf("Invalid licence plate. Must be 2-8 alphanumeric characters or spaces.\n\n");
        return;
    }
    
    printf("Driver updated successfully.\n\n");
}

// Function that prints all the drivers.
void print_all_drivers(Driver *head) {
    // Prints header for drivers.
    printf("%-12s%-26s%-20s%-20s\n", "Driver Code", "Driver Name", "Vehicle Colour", "Licence Plate");
    
    // Prints all the drivers.
    Driver *current = head;
    while (current != NULL) {
        printf("%-12d%-26s%-20s%-20s\n", current->code, current->name, get_colour_name(current->vehicle_colour), current->licence_plate);
        current = current->next;
    }
    printf("\n");
}

// Function that removes the driver with the given driver code.
void erase_driver(Driver **head) {
    int code;
    
    // Ask user for driver code.
    printf("\tEnter driver code: ");
    scanf("%d", &code);
    clear_input_buffer();
    
    // Handle empty list.
    if (*head == NULL) {
        printf("Driver not found.\n\n");
        return;
    }
    
    Driver *current = *head;
    Driver *previous = NULL;
    
    // Search for driver to delete.
    while (current != NULL && current->code != code) {
        previous = current;
        current = current->next;
    }
    
    // Check if driver exists.
    if (current == NULL) {
        printf("Driver not found.\n\n");
        return;
    }

    // Delete head node.
    if (previous == NULL) {
        *head = current->next;
    } else {
        // Delete middle or end node.
        previous->next = current->next;
    }
    
    // Free the allocated memory for the driver.
    free(current);
    printf("Driver deleted successfully.\n\n");
}

// Function that frees all the memory allocated for drivers.
void free_all_drivers(Driver **head) {
    Driver *current = *head;
    Driver *next;
    
    while (current != NULL) {
        next = current->next;
        free(current);
        current = next;
    }
    
    *head = NULL;
}

// Function that handles the driver menu.
void driver_menu(Driver **head) {
    char command;
    
    // Read user input.
    printf("\nDriver Menu - Enter command (i/s/u/p/e): ");
    scanf(" %c", &command);
    clear_input_buffer();
    
    // Switch case for user input.
    switch (command) {
        case 'i': // Insert new driver.
            insert_driver(head);
            break;
        case 's': // Search for driver.
            search_driver(*head);
            break;
        case 'u': // Update existing driver.
            update_driver(*head);
            break;
        case 'p': // Print all drivers.
            print_all_drivers(*head);
            break;
        case 'e': // Remove a driver.
            erase_driver(head);
            break;
        default: // Default case.
            printf("Invalid command.\n\n");
    }
}
