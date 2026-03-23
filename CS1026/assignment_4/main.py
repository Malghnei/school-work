"""
********************************************
CS 1026 - Assignment 4 - Air Travel
Code by: Malik Alghneimin
Student Number: 
File created: November 26, 2024
********************************************
The program uses the Flight and Airport classes, created from the imports, in functions that obtain specific values/objects.
"""


# You must create the Flight.py and Airport.py files.
from Flight import *
from Airport import *

# Define your collections for all_airports and all_flights here.
all_airports = []
all_flights = {}

# Function for loading the flights and airports file and adding the information in the containers.
def load_data(airport_file, flight_file):
    # Try-except function for catching errors occuring in the instructions.
    try:
        # Opening and assigning data inside the files. 
        airport_data = open(airport_file, "r").readlines()
        flight_data = open(flight_file, "r").readlines()

        # Cleaning and adding values into the all_airports container.
        for i in airport_data:
            # Cleaning information.
            line = i.split("-")
            for j in range(0,len(line)):
                line[j] = line[j].strip()
            # Creating and adding the Airport object into the container.
            all_airports.append(Airport(line[0], line[2], line[1]))

        # Cleaning and adding the values into the all_flights container.
        for i in flight_data:
            # Cleaning information.
            line = i.split("-")
            for j in range(0, len(line)):
                line[j] = line[j].strip()
            # Getting the appropriate Airport object.
            for j in all_airports:
                if line[2] == j.get_code():
                    origin = j
                    origin_code = j.get_code()
                if line[3] == j.get_code():
                    destination = j
            # Creating and adding the Flight object into the container.
            if origin_code in all_flights:
                all_flights[origin_code].append(Flight(line[0] + "-" + line[1], origin, destination, line[4]))
            else:
                all_flights[origin_code] = [Flight(line[0] + "-" + line[1], origin, destination, line[4])]  

        # Returns true if no errors occur during the loading of data. 
        return True
    except:
        # Return False if an error occurs.
        return False


# Function for finding and returning an Airport object based off the given airport code.
def get_airport_by_code(code):
    # For loop to check through all the Airport objects in the all_airports container.
    for i in all_airports:
        if i.get_code() == code:
            # Returns the Airport object that has the same airport code as the given code.
            return i
    # A ValueError is raised if no Airport object is found.
    raise ValueError("No airport with the given code: {0}".format(code))


# Function for finding and returning all Flight objects associated with the given city.
def find_all_city_flights(city):
    # Declaring variables
    list = []
    city_airport = ""
    # Find the Airport code associated with the given city.
    for i in all_airports:
        if i.get_city() == city:
            # The Airport object associated with the given city.
            city_airport = i
    # Finding all flights with the selected airport as the destination or origin
    if city_airport != "":
        # For loop to go through every Airport object.
        for i in all_airports:
            if i.get_code() in all_flights:
                # For loop to go through every Flight object. 
                for j in all_flights[i.get_code()]:
                    # Check if the flight's origin and destination are in the given city.
                    if (j.get_destination() == city_airport) or (j.get_origin() == city_airport):
                        # The Flight object is added to the list to be returned.
                        list.append(j)
    # Return the list of flights
    return list


# Function for finding and returning all Flight objects that are associated with the given country.
def find_all_country_flights(country):
    # Declaring variables
    list = []
    airport_list = []
    # Find the airport code associated with the given city.
    for i in all_airports:
        if i.get_country() == country:
            # The Airport object associated with the given city is added to a list.
            airport_list.append(i)
    # Finding all flights with the selected airport as the destination or origin
    if airport_list != []:
        # For loop to check through all Airport objects.
        for i in all_airports:
            if i.get_code() in all_flights:
                # For loop to check through all Flight objects with the origin as the current airport.
                for j in all_flights[i.get_code()]:
                    # Check if the flight's origin and destination are in the given country.
                    if (j.get_destination() in airport_list) or (j.get_origin() in airport_list):
                        # The Flight object is added to the list to be returned.
                        list.append(j)
    # Return the list of flights
    return list


# Function for finding and returning Flight objects that have the given origin and destination.
# Single-hop connections (addition of two Flight objects) are also searched.
def find_flight_between(orig_airport, dest_airport):
    # Declaring variables.
    orig_airport_code = orig_airport.get_code()
    dest_airport_code = dest_airport.get_code()
    single_hop_airport = set()

    # For loop to check all Flight objects with the given origin.
    for i in all_flights[orig_airport_code]:
        # Check if the flight's destination is the same as the given destination.
        if i.get_destination() == dest_airport:
            return "Direct Flight: {0} to {1}".format(orig_airport_code, dest_airport_code)
        # If not, all the possible single-hop connections are to be checked.
        else:
            if i.get_destination().get_code() in all_flights:
                # For loop to check all flight connections with the origin as the destination of the first flight.
                for j in all_flights[i.get_destination().get_code()]:
                    # Checik if the connection's destination is the same as the given destination.
                    if j.get_destination() == dest_airport:
                        # Add the origin of the connections in a list to be returned.
                        single_hop_airport.add(j.get_origin().get_code())
                
    # Check if the list of single-hop origins is not empty.
    if len(single_hop_airport) != 0:
        return single_hop_airport
    
    # Raise a ValueError if nothing is returned.
    raise ValueError("There are no direct or single-hop connecting flights from {0} to {1}".format(orig_airport_code, dest_airport_code))


# Function for finding and returning a Flight object with the shortest duration.
def shortest_flight_from(orig_airport):
    # Declaring variables.
    orig_airport_code = orig_airport.get_code()
    shortest_flight = ['', 0]
    # Check for all flights with the same origin.
    if orig_airport_code in all_flights:
        for i in all_flights[orig_airport_code]:
            # Compare the current duration to the shortest duration.
            if shortest_flight[1] == 0 or float(i.get_duration()) < shortest_flight[1]:
                # The new shorter duration is set.
                shortest_flight[1] = float(i.get_duration())
                shortest_flight[0] = i
        
    # Return the Flight object with the shortest duration.
    return shortest_flight[0]   


# Function for finding and returning a Flight object with the opposite origin and destination as the given flight.
def find_return_flight(first_flight):
    # Declaring variables.
    return_flight_origin = first_flight.get_destination()
    return_flight_dest = first_flight.get_origin()
    origin_airport_code = return_flight_origin.get_code()
    dest_airport_code = return_flight_dest.get_code()

    # Check if there is a flight with the opposite origin and destination.
    if origin_airport_code in all_flights:
        for i in all_flights[origin_airport_code]:
            if i.get_destination() == return_flight_dest:
                # The Flight object is returns.
                return i

    # Raise a ValueError if there is no returning flight.
    raise ValueError("There is no flight from {0} to {1}".format(origin_airport_code, dest_airport_code))


# Main
if __name__ == "__main__":
    # Add any of your own tests on your functions here.
    # Make sure you don't have any testing or debugging code outside of this block!
    data = load_data("airports.txt", "flights.txt")
    print(data, len(all_airports), len(all_flights))

    print(get_airport_by_code("ORD"))

    # print(get_airport_by_code("ABC"))


    res = find_all_city_flights("Dallas") 
    for r in res: 
        print(r)

    res = find_all_country_flights("China") 
    for r in res: 
        print(r)

    pearson = get_airport_by_code("YYZ") 
    ohare = get_airport_by_code("ORD") 
    edm = get_airport_by_code("YEG") 
    print(find_flight_between(edm, ohare))

    jfk = get_airport_by_code("JFK")
    print(shortest_flight_from(jfk))

    print(find_flight_between(edm, pearson))

    # print(find_flight_between(pearson, ohare))

    sf_to_sp = all_flights["SFO"][0] 
    print(find_return_flight(sf_to_sp))

    f1 = all_flights["YEG"][0] 
    f2 = all_flights["ORD"][0] 
    print(f1 + f2)

    # print(f2 + f1)