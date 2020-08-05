# RMI-Room-Reservation

RMI-Room-Reservation is a Project I did during Summer Semester 2020 while I was studying Computer Engineering in the University of West Attica in Athens, Greece. The Project was assigned to students that attended the subject Distributed Systems.

## About the Project
In this project a Server-Client application using Java's RMI is developed that works as a room reservation service. Thus, a server could serve multiple amount of clients simultaneously.

A client have the following options:

* Get help regarding which options he has
* Get a list with available rooms
* Make room reservations
* Cancel Reservations
* Get a list of every client and his reservations in the Hotel

## Requirements
Java Development Kit: for instance OpenJDK 8

## Usage/Example
First, if you are using Windows 10 you have to start the rmi registry. (On linux usually is running already, so no action needed.

In order to run RMI registry on Windows, type the following:
```bash
start rmiregistry
```
Compile or .java files
```bash
javac *.java
```
Then, rmic generates stub, skeleton, and tie classes for remote objects using either the JRMP or IIOP protocols.
```bash
rmic HRImpl
```
```bash
rmic HRServer
```
After RMI registry is exectuted, then at least two terminals need to be opened, one for the Server side application and at least one for a client.

Then type the following:

  Start the Server
  ```bash
  java HRServer
  ```
  ### Example 1: Get available Options

  ```bash
  java HRClient
  Please run the program in one of the following ways:
  java HRClient list <hostname>
  java HRClient book <hostname> <type> <number> <name>
  java HRClient guests <hostname>
  java HRClient cancel <hostname> <type> <number> <name>
  ```
  ### Example 2: Make a Reservation
  ```bash
  java HRClient book localhost B 3 Koutanis
  Booking was succesful!The total cost of this booking is: 210.0
  ```
 ## Copyrights
 This project was upload strictly to show my work and what I've learned during my studies. Thus, copying it or use it in   any way is not allowed.
