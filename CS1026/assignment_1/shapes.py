"""
********************************************
CS 1026 - Assignment 1 - Shapes and Sizes
Code by: Malik Alghneimin
Student ID: malghnei
File Created: September 24, 2024
********************************************
This file is used to calculate the perimeter and area of various valid shapes.
These values are calculated through various fitting equations with if statements and variables.
The final results are printed after all inputs are fulfilled.
Invalid inputs such as negative numbers and incorrect shapes are to be processed and informed to the user.
"""

import math

# Variables
PI = math.pi
perimeter = 0
area = 0

# Ask the user to enter the name of the valid shape:
shape = input("Enter a 2D shape: ")
# Shape check
if shape.lower() == "rectangle":
    # Ask the user for the following variables
    length = float(input("Enter the length of the rectangle: "))
    width = float(input("Enter the width of the rectangle: "))
    # Equations for the perimeter and area of a rectangle
    perimeter = (2 * width) + (2 * length)
    area = width * length
    # Check if values are negative
    if (length <= 0) or (width <= 0):
        print("Invalid value entered")
        quit()
elif shape.lower() == "triangle":
    # Ask the user for the following variables
    side_b = float(input("Enter the base length of the triangle: "))
    side_a = float(input("Enter the 2nd side length of the triangle: "))
    side_c = float(input("Enter the 3rd side length of the triangle: "))
    height = float(input("Enter the height of the triangle: "))
    # Equations for the perimeter and area of a triangle
    perimeter = side_a + side_b + side_c
    area = (side_b * height) / 2
    # Check if values are negative
    if (side_b <= 0) or (side_a <= 0) or (side_c <= 0) or (height <= 0):
        print("Invalid value entered")
        quit()
elif shape.lower() == "circle":
    # Ask the user for the following variables
    radius = float(input("Enter the radius of the circle: "))
    # Equations for the perimeter and area of a circle
    perimeter = 2 * PI * radius
    area = PI * radius ** 2
    # Check if values are negative
    if radius <= 0:
        print("Invalid value entered")
        quit()
elif shape.lower() == "trapezoid":
    # Ask the user for the following variables
    top_side = float(input("Enter the top side length of the trapezoid:"))
    bottom_side = float(input("Enter the bottom side length of the trapezoid: "))
    left_side = float(input("Enter the left side length of the trapezoid: "))
    right_side = float(input("Enter the right side length of the trapezoid: "))
    height = float(input("Enter the height of the trapezoid: "))
    # Equations for the perimeter and area of a trapezoid
    perimeter = top_side + bottom_side + left_side + right_side
    area = ((top_side + bottom_side) / 2) * height
    # Check if values are negative
    if (top_side <= 0) or (bottom_side <= 0) or (left_side <= 0) or (right_side <= 0) or (height <= 0):
        print("Invalid value entered")
        quit()

elif shape.lower() == "hexagon":
    # Ask the user for the following variables
    side = float(input("Enter the side length of the hexagon: "))
    # Equations for the perimeter and area of a hexagon
    perimeter = 6 * side
    area = (3 * math.sqrt(3)) / 2 * side ** 2
    # Check if values are negative
    if side <= 0:
        print("Invalid value entered")
        quit()
else:
    # Output that shape is invalid.
    print("Invalid shape entered")
    quit()

# Output results
print("Shape: ", shape)
print("Perimeter: %.2f" % perimeter)
print("Area: %.2f" % area)
