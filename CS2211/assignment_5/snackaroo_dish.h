// Check if SNACKAROO_DISH_H exists; prevents repetition.
#ifndef SNACKAROO_DISH_H
#define SNACKAROO_DISH_H

// Defining macros.
#define MAX_DISH_NAME 100
#define MAX_RESTAURANT_NAME 100

// Initializing Dish struct.
typedef struct Dish {
    int code;
    char dish_name[MAX_DISH_NAME];
    char restaurant_name[MAX_RESTAURANT_NAME];
    float rating;
    float price;
    struct Dish *next;
} Dish;

// Functions.
void dish_menu(Dish **head);
void insert_dish(Dish **head);
void search_dish(Dish *head);
void update_dish(Dish *head);
void print_all_dishes(Dish *head);
void erase_dish(Dish **head);
void free_all_dishes(Dish **head);
void dump_dishes(Dish *head);
void restore_dishes(Dish **head);

// Helper Functions.
Dish* find_dish_by_code(Dish *head, int code);

#endif