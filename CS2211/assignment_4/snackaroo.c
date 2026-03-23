// Include libraries and files
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// Definitions
#define MAX_DISHES 100
#define MAX_NAME_LEN 100

// Dish struct
typedef struct {
    int code;
    char dish_name[MAX_NAME_LEN];
    char restaurant_name[MAX_NAME_LEN];
    float rating;
    float price;
} Dish;

// Helper function to clear input buffer.
void clear_input_buffer() {
    int c;
    while ((c = getchar()) != '\n' && c != EOF);
}

// Function to search for dish in the dishes array with given code.
int search_dish(Dish dishes[], int *count, int code) {
    if (*count == 0) return -1;
    for (int i = 0; i < *count; i++) {
        if (dishes[i].code == code) {
            return i;
        }
    }
    return -1;
}

// Helper function to print the dishes header.
void print_dish_header() {
    // Format specifiers: https://www.upgrad.com/tutorials/software-engineering/c-tutorial/format-specifiers-in-c/
    printf("%-12s%-26s%-32s%-26s%s\n", 
            "Dish Code", 
            "Dish Name", 
            "Restaurant Name", 
            "Dish Rating", 
            "Dish Price"
        );
}

// Function to print all orders in the dishes array.
void print_dish(Dish *dish) {  
    // Format specifiers: https://www.upgrad.com/tutorials/software-engineering/c-tutorial/format-specifiers-in-c/
    printf("%-12d%-26s%-32s%-26.1f%.2f\n", 
            (*dish).code, 
            (*dish).dish_name, 
            (*dish).restaurant_name, 
            (*dish).rating, 
            (*dish).price
        );
}

void insert_dish(Dish dishes[], int *count) {
    // Check if dishes is full (MAX_DISHES)
    if (*count >= MAX_DISHES) {
        printf("Max dishes reached.\n");
        return;
    }

    Dish new_dish; // New dish

    // Ask user for dish code.
    int code;
    printf("\tEnter dish code: ");
    scanf("%d", &code);

    // If dish code is not positive.
    if (code < 0)  {
        printf("Dish code must be positive.\n");
        return;
    }

    // If dish code exists in d
    if (search_dish(dishes, count, code) != -1) {
        printf("Dish already exists.\n");
        return;
    }
    
    // Set dish code for new dish.
    new_dish.code = code;

    // Ask user for dish name.
    char dish_name[MAX_NAME_LEN];
    printf("\tEnter dish name: ");

    clear_input_buffer(); // To clear input buffer

    // Reads and checks if dish name is empty.
    if (fgets(dish_name, MAX_NAME_LEN, stdin) == NULL) {
        printf("Dish name cannot be empty.\n");
        return;
    }

    // Set dish name for new dish.
    strcpy(new_dish.dish_name, dish_name);
    // Help from claude: https://claude.ai/share/d8217116-4816-4773-9dcc-c57a88559629
    // To remove the '\n' char.
    new_dish.dish_name[strcspn(new_dish.dish_name, "\n")] = '\0'; // strcspn from 'string.h': https://www.geeksforgeeks.org/c/strcspn-in-c/
    // Check if buffer is full
    if (strlen(new_dish.dish_name) == MAX_NAME_LEN - 1 && new_dish.dish_name[MAX_NAME_LEN - 2] != '\n') {
        clear_input_buffer();  // Clear remaining input if buffer was full
    }

    // Ask user for restaurant name.
    char restaurant_name[MAX_NAME_LEN];
    printf("\tEnter restaurant name: ");
    
    // Reads and checks if restaurant name is empty.
    if (fgets(restaurant_name, MAX_NAME_LEN, stdin) == NULL) {
        printf("Restaurant name cannot be empty.\n");
        return;
    }
    
    // Set restaurant name for new dish.
    strcpy(new_dish.restaurant_name, restaurant_name);
    // To remove the '\n' char.
    new_dish.restaurant_name[strcspn(new_dish.restaurant_name, "\n")] = '\0';
    // Check if buffer is full
    if (strlen(new_dish.restaurant_name) == MAX_NAME_LEN - 1 && new_dish.restaurant_name[MAX_NAME_LEN - 2] != '\n') {
        clear_input_buffer();  // Clear remaining input if buffer was full
    }

    // Ask user for dish rating.
    float dish_rating;
    printf("\tEnter dish rating: ");
    scanf("%f", &dish_rating);
    
    // Check if dish rating is not between 0.0 and 10.0. 
    if ((dish_rating < 0.0) || (dish_rating > 10.0)) {
        printf("Dish rating must be between 0.0 and 10.0.\n");
        return;
    }

    // Set dish rating for new dish.
    new_dish.rating = dish_rating;

    // Ask user for dish price.
    float dish_price;
    printf("\tEnter dish price: ");
    scanf("%f", &dish_price);

    // Check if dish price is not positive.
    if (dish_price < 0.0) {
        printf("Dish price must be positive.\n");
        return;
    }

    // Set dish price for new dish.
    new_dish.price = dish_price;

    // Add new dish to array.
    dishes[*count] = new_dish;
    (*count)++; // Increase count.
    printf("\n");
}

void update_dish(Dish *curr_dish) {
    // Ask user for dish name.
    char dish_name[MAX_NAME_LEN];
    printf("\tEnter dish name: ");

    clear_input_buffer(); // To clear input buffer

    // Reads and checks if the dish name is empty.
    if (fgets(dish_name, MAX_NAME_LEN, stdin) == NULL) {
        printf("Dish name cannot be empty.\n");
        return;
    }

    // To remove the '\n' char.
    dish_name[strcspn(dish_name, "\n")] = '\0';
    // Check if buffer is full
    if (strlen(dish_name) == MAX_NAME_LEN - 1 && dish_name[MAX_NAME_LEN - 2] != '\n') {
        clear_input_buffer();  // Clear remaining input if buffer was full
    }

    // Ask user for restaurant name.
    char restaurant_name[MAX_NAME_LEN];
    printf("\tEnter restaurant name: ");
    
    // Reads and checks if the restaurant name is empty.
    if (fgets(restaurant_name, MAX_NAME_LEN, stdin) == NULL) {
        printf("Restaurant name cannot be empty.\n");
        return;
    }

    // To remove the '\n' char.
    restaurant_name[strcspn(restaurant_name, "\n")] = '\0';
    // Check if buffer is full
    if (strlen(restaurant_name) == MAX_NAME_LEN - 1 && restaurant_name[MAX_NAME_LEN - 2] != '\n') {
        clear_input_buffer();  // Clear remaining input if buffer was full
    }

    // Ask user for dish rating.
    float dish_rating;
    printf("\tEnter dish rating: ");
    scanf("%f", &dish_rating);
    
    // Check if dish rating is not between 0.0 and 10.0.
    if ((dish_rating < 0.0) || (dish_rating > 10.0)) {
        printf("Dish rating must be between 0.0 and 10.0.\n");
        return;
    }

    // Ask user for dish price.
    float dish_price;
    printf("\tEnter dish price: ");
    scanf("%f", &dish_price);

    if (dish_price < 0.0) {
        printf("Dish price must be positive.\n");
        return;
    }

    // Set new variables.
    strcpy((*curr_dish).dish_name, dish_name);
    strcpy((*curr_dish).restaurant_name, restaurant_name);
    (*curr_dish).rating = dish_rating;
    (*curr_dish).price = dish_price;

    printf("\n");
}

int main() {
    printf("**********************\n");
    printf("* 2211 Snackaroo App *\n");
    printf("**********************\n\n");
    Dish dishes[MAX_DISHES];
    int count = 0;
    char input;

    while (1) {
        printf("Enter operation code: ");
        scanf(" %c", &input);

        switch (input) {
            case 'i':
                insert_dish(dishes, &count);
                break;
            case 's':
                {
                    int code;
                    printf("\tEnter dish code: ");
                    scanf("%d", &code);

                    int result = search_dish(dishes, &count, code);

                    if (result != -1) {
                        print_dish(&dishes[result]);
                    } else {
                        printf("Dish does not exist.\n");
                    }
                }
                break;
            case 'u':
                {
                    int code;
                    printf("\tEnter dish code: ");
                    scanf("%d", &code);

                    int result = search_dish(dishes, &count, code);

                    if (result != -1) {
                        update_dish(&dishes[result]);
                    } else {
                        printf("Dish does not exist.\n");
                    }
                }
                break;
            case 'p':
                print_dish_header();
                for (int i = 0; i < count; i++) {
                    print_dish(&dishes[i]);
                }
                printf("\n");
                break;
            case 'q':
                return 0;
        }
    }
    return 0;
}
