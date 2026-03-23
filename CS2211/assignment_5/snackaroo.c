// Include other headers.
#include "snackaroo.h"
#include "snackaroo_dish.h"
#include "snackaroo_driver.h"
#include "snackaroo_relationship.h"

// Helper function that clears remaining characters from input stream.
void clear_input_buffer() {
    int c;
    while ((c = getchar()) != '\n' && c != EOF);
}

// Helper function that prints the application header.
void print_main_header() {
    printf("**********************\n");
    printf("* 2211 Snackaroo App *\n");
    printf("**********************\n\n");
}

// Helper function that prints the commands for use.
void print_help() {
    printf("\nAvailable commands:\n");
    printf("  h - Print this help message\n");
    printf("  m - Manage dishes (insert, search, update, print, erase)\n");
    printf("  a - Manage drivers (insert, search, update, print, erase)\n");
    printf("  r - Manage relationships (insert, search, print, erase, dump, restore)\n");
    printf("  q - Quit the program\n\n");
}

// Main program.
int main() {
    Dish *dish_head = NULL;           // Head of dishes linked list.
    Driver *driver_head = NULL;       // Head of drivers linked list.
    dish_driver_code *rel_head = NULL;    // Head of relationships linked list.
    char command;

    // Prints the application header and help command.
    print_main_header();
    print_help();
    
    // Main command loop.
    while (1) {
        // Reads user input.
        printf("Enter operation code: ");
        scanf(" %c", &command);
        clear_input_buffer();
        
        // Switch case for user input.
        switch (command) {
            case 'h': // Displays help commands.
                print_help();
                break;
            case 'm': // Opens the dish menu.
                dish_menu(&dish_head);
                break;
            case 'a': // Opens the driver menu.
                driver_menu(&driver_head);
                break;
            case 'r': // Opens the relationship menu.
                relationship_menu(&rel_head, dish_head, driver_head);
                break;
            case 'q': // Quits program.
                /* Free all allocated memory before exiting */
                free_all_relationships(&rel_head);
                free_all_dishes(&dish_head);
                free_all_drivers(&driver_head);
                printf("Goodbye!\n");
                return 0;
            default: // Default case.
                printf("Invalid command. Type 'h' for help.\n\n");
        }
    }
    
    return 0;
}