// Check if SNACKAROO_DRIVER_H exists; prevents repetition.
#ifndef SNACKAROO_DRIVER_H
#define SNACKAROO_DRIVER_H

// Defining macros.
#define MAX_DRIVER_NAME 50
#define MAX_LICENCE_PLATE 9

// Initializing Driver struct.
typedef struct Driver{
    int code;
    char name[MAX_DRIVER_NAME];
    int vehicle_colour;
    char licence_plate[MAX_LICENCE_PLATE];
    struct Driver *next;
} Driver;

// Functions.
void driver_menu(Driver **head);
void insert_driver(Driver **head);
void search_driver(Driver *head);
void update_driver(Driver *head);
void print_all_drivers(Driver *head);
void erase_driver(Driver **head);
void free_all_drivers(Driver **head);
void dump_driver(Driver *head);
void restore_drivers(Driver **head);

// Helper functions.
Driver* find_driver_by_code(Driver *head, int code);
const char* get_colour_name(int colour_code);
int validate_license_plate(const char *plate);

#endif


