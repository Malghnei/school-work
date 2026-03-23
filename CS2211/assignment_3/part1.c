#include <stdio.h>
#include <string.h>


// Part Function
void part1(int arr[], int size) {
    printf("Part 1:\n\tYour array is: ");
    
    for (int i = 0; i < size; i++) {
        printf("[%d] = %d", i, arr[i]);
        if (i < size - 1) {
            printf(", ");
        }
    }
}

void part2(int arr[], int size) {
    printf("\nPart 2:\n\tYour array in reverse is: ");
    
    for (int i = size - 1; i >= 0; i--) {
        printf("[%d] = %d", i, arr[i]);
        if (i <= size) {
            printf(", ");
        }
    }
}

void part3(int arr[], int size) {
    printf("\nPart 3:\n\tThe even elements in the array is: ");
    
    for (int i = 0; i < size; i++) {
        if (arr[i] % 2 == 0) {
            printf("[%d] = %d", i, arr[i]);
            if (i < size - 1) {
                printf(", ");
            }
        }
    }
}

void part4(int arr[], int size) {
    printf("\nPart 4:\n\tThe sum of all values in your array is: ");
    
    int sum = 0;
    for (int i = 1; i < size; i++) {
        sum += arr[i];
    }
    printf("%d\n", sum);
}

void part5(int arr[], int size) {
    printf("Part 5\n\tYour array in sorted order is: ");
    int sortedArr[size];
    for (int i = 0; i < size; i++) {
        sortedArr[i] = arr[i];
    }

    // Simple bubble sort
    for (int i = 0; i < size - 1; i++) {
        for (int j = 0; j < size - i - 1; j++) {
            if (sortedArr[j] > sortedArr[j + 1]) {
                int temp = sortedArr[j];
                sortedArr[j] = sortedArr[j + 1];
                sortedArr[j + 1] = temp;
            }
        }
    }

    for (int i = 0; i < size; i++) {
        printf("[%d] = %d", i, sortedArr[i]);
        if (i < size - 1) {
            printf(", ");
        }
    }
}

void part6(int arr[], int size) {
    printf("\nPart 6\n\tYour array with first and last element switched is: ");

    if (size >= 1) {
        printf("[%d] = %d, ", size - 1, arr[size - 1]);
    }

    for (int i = 1; i < size - 1; i++) {
        printf("[%d] = %d", i, arr[i]);
        if (i < size) {
            printf(", ");
        }
    }

    if (size >= 1) {
        printf("[%d] = %d\n", 0, arr[0]);
    }
}

int main() {
    int size;
    char input[100];    

    printf("How many integers will you provide? (5 to 12): ");
    scanf(" %d", &size);
    
    int numbers[size];
    printf("Number of integers: %d, Size of array (in bytes): ", size, sizeof(numbers));
    printf("Please enter %d integers: ", size);
    
    // getchar: https://www.tutorialspoint.com/c_standard_library/c_function_getchar.htm
    while (getchar() != '\n') {} // Clear input buffer


    if (fgets(input, sizeof(input), stdin) != NULL) {
        // strtok: https://www.tutorialspoint.com/c_standard_library/c_function_strtok.htm
        char *token = strtok(input, " ");
        int count = 0;

        while (token != NULL) {
            numbers[count] = atoi(token);
            token = strtok(NULL, " ");
            count++;
        }

        if (count != size) {
            printf("Error: Expected %d integers but got %d.\n", size, count);
        }
    }
    
    // Call parts
    part1(numbers, size);
    part2(numbers, size);
    part3(numbers, size);
    part4(numbers, size);
    part5(numbers, size);
    part6(numbers, size);

    return 0;
}