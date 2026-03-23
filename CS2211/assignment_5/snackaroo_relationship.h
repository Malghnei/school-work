// Check if SNACKAROO_RELATIONSHIP_H exists; prevents repetition.
#ifndef SNACKAROO_RELATIONSHIP_H
#define SNACKAROO_RELATIONSHIP_H

// Include libraries and files.
#include "snackaroo_dish.h"
#include "snackaroo_driver.h"

// Initializing dish_driver_code struct.
typedef struct dish_driver_code {
    int relationship_id;
    int dish_code;
    int driver_code;
    struct dish_driver_code *next;
} dish_driver_code;

// Functions.
void relationship_menu(dish_driver_code **head, Dish *dish_head, Driver *driver_head);
void insert_relationship(dish_driver_code **head, Dish *dish_head, Driver *driver_head);
void search_relationship(dish_driver_code *head);
void print_all_relationships(dish_driver_code *head);
void print_dishes_for_driver(dish_driver_code *head, Dish *dish_head);
void print_drivers_for_dish(dish_driver_code *head, Driver *driver_head);
void erase_relationship(dish_driver_code **head);
void free_all_relationships(dish_driver_code **head);
void dump_relationships(dish_driver_code *head);
void restore_relationships(dish_driver_code **head, Dish *dish_head, Driver *driver_head);

// Helper Functions.
dish_driver_code* find_relationship_by_id(dish_driver_code *head, int id);
int get_next_relationship_id(dish_driver_code *head);

#endif