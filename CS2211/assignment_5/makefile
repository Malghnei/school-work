# All sampled from topic 18 of the course and the official manual: https://www.gnu.org/software/make/manual/make.html.
# Compiler and flags
CC = gcc
CFLAGS = -Wall -std=c99

# Excecutable name
TARGET = snackaroo

# Object files
OBJS = snackaroo.o snackaroo_dish.o snackaroo_driver.o snackaroo_relationship.o

# Default target
all: $(TARGET)

# Link objects to create executable
$(TARGET): $(OBJS) 
	$(CC) $(CFLAGS) -o $(TARGET) $(OBJS)

# Main program
snackaroo.o: snackaroo.c snackaroo.h snackaroo_dish.h snackaroo_driver.h snackaroo_relationship.h 
	$(CC) $(CFLAGS) -c snackaroo.c

# Dish module
snackaroo_dish.o: snackaroo_dish.c snackaroo_dish.h snackaroo.h	
	$(CC) $(CFLAGS) -c snackaroo_dish.c

# Drivers module
snackaroo_driver.o: snackaroo_driver.c snackaroo_driver.h snackaroo.h 
	$(CC) $(CFLAGS) -c snackaroo_driver.c

# Relationship module
snackaroo_relationship.o: snackaroo_relationship.c snackaroo_relationship.h snackaroo_dish.h snackaroo_driver.h snackaroo.h
	$(CC) $(CFLAGS) -c snackaroo_relationship.c

# Clean up compiled files
clean:
rm -f $(OBJS) $(TARGET)




