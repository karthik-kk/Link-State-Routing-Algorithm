# Link-State-Routing-Algorithm
#### Overview:
**_Link-State Routing protocol_** is a main class of routing protocols. It is performed by every switching node/router in the network. The basic concept of link-state routing is that every node constructs a map of the connectivity to the network, in the form of a Graph, showing which nodes are connected to which other nodes. Each node then independently calculates the next best logical path from it to every possible destination in the network. The collection of best paths will then form the node's routing table.

#### Description:
This is a Simulation of **_Link-State Routing Protocol_** implemented in JAVA

It involves: 
- -->  Simulating the process of generating connection table for each router in a given network
- --> Computing the optimal path with least cost between any two specific routers
- --> Handling the situation to build a revised connection table and computing an alternative shortest path when a node fails or when the router is down

The application provides a Console Interface for the user to interact with the simulator.

#### Simulation:
Open up the Terminal or Command prompt:

```sh
$ cd src
$ javac Dijkstra.java
$ java Dijkstra
```
-OR-

```sh
$ cd Executable
$ java -jar DijkstraExecutable.jar
```
This Runs the Simulator and the user can select the files 'input.txt' or '8_routers.txt' or any other Graph_Input files and continue with the simulation.

For Detailed Description, refer to Link State Routing Documentation and Operations Manual.

---
**Copyright &copy; 2015,&nbsp;Karthik Krishnamurthy**
