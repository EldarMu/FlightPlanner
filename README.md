# FlightPlanner
Flightplanner is a programming puzzle for students to plug their own shortest path algorithm into

It's a swing java app with a very simple ui, it draws a bunch of white squares representing cities
in a green field and shows graph edges with yellow lines. The students are encouraged to implement either 
A* or Djikstra's algorithm to solve the problem. The solver function is provided the start point and endpoint
as indices in an array of "cities" as well as the array of cities and the connections as an adjacency matrix.

The current implementation (SolverEEM) is a brute-force depth-first search that ends up going through every path
and thus can be quite slow. It is also used in order to generate all the possible solutions.

Please don't judge me for the ui, I am not a frontend person.

Also, MIT license.
