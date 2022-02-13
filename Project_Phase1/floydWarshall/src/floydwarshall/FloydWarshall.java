//Razan Aljuhani - Dimah Alolayan - Majd Gezan
// Group 5
// CPCS324 Project Floyd-Warshall Algorithm
//This is a java program to find the distances (All pairs shortest paths) of the digraph using Floyd's algorithm .
package floydwarshall;

public class FloydWarshall {

    final static int INFINITY = 99; //∞ , if there is no direct edge between pairs .
    final static int vertices = 10; //Number of vertices in graph .

    //Main 
    public static void main(String[] args) {

        // Wigted matrix is representation of digraph .
        int[][] Wigted_matrix = {
            {0, 10, INFINITY, INFINITY, INFINITY, 5, INFINITY, INFINITY, INFINITY, INFINITY},
            {INFINITY, 0, 3, INFINITY, 3, INFINITY, INFINITY, INFINITY, INFINITY, INFINITY},
            {INFINITY, INFINITY, 0, 4, INFINITY, INFINITY, INFINITY, 5, INFINITY, INFINITY},
            {INFINITY, INFINITY, INFINITY, 0, INFINITY, INFINITY, INFINITY, INFINITY, 4, INFINITY},
            {INFINITY, INFINITY, 4, INFINITY, 0, INFINITY, 2, INFINITY, INFINITY, INFINITY},
            {INFINITY, 3, INFINITY, INFINITY, INFINITY, 0, INFINITY, INFINITY, INFINITY, 2},
            {INFINITY, INFINITY, INFINITY, 7, INFINITY, INFINITY, 0, INFINITY, INFINITY, INFINITY},
            {INFINITY, INFINITY, INFINITY, 4, INFINITY, INFINITY, INFINITY, 0, 3, INFINITY},
            {INFINITY, INFINITY, INFINITY, INFINITY, INFINITY, INFINITY, INFINITY, INFINITY, 0, INFINITY},
            {INFINITY, 6, INFINITY, INFINITY, INFINITY, INFINITY, 8, INFINITY, INFINITY, 0}
        };
        System.out.println("\tCPCS324 - Project(Phase one) - Floyd-Warshall Algorithm\n");
        FloydWarshall sol = new FloydWarshall(); //object of FloydWarshall class
        sol.floyd(Wigted_matrix);  // Print the solution

    }

    //Method1 : Floyd's Algorithm to find All-Pairs Shortest paths  . 
    public void floyd(int[][] wigted_matrix) {
        int i, j, k;
        int distanceMatrix[][] = new int[vertices][vertices]; // to record all shortest path (Solution matrix)
        //Initialize the distance matrix same as input wighted matrix.
        for (i = 0; i < vertices; i++) {
            for (j = 0; j < vertices; j++) {
                distanceMatrix[i][j] = wigted_matrix[i][j];
            }
        }
        System.out.println(">>> The Given Weight Matrix:");
        System.out.println("D(0):");
        display(distanceMatrix); //print Wigted Matrix

        System.out.println("\n>>> The All Pairs Shortest path by Floyd's Algorithm: ");
        for (k = 0; k < vertices; k++) {
            // Pick all vertices as source one by one
            for (i = 0; i < vertices; i++) {
                // Pick all vertices as destination for the above picked source
                for (j = 0; j < vertices; j++) {
                    // If vertex k is on the shortest path from i to j, then update the value of distanceMatrix[i][j]
                    if ((distanceMatrix[i][k] + distanceMatrix[k][j]) < distanceMatrix[i][j]) {
                        distanceMatrix[i][j] = distanceMatrix[i][k] + distanceMatrix[k][j];
                    }
                }
            }
            //print matrices after each itreation
            System.out.println("\nD(" + (k+1) + "):");
            display(distanceMatrix);
            System.out.println();
        }
    }

    //Method2 : for print the matrices 
    public static void display(int[][] distanceMatrix) {
        //Loop of all elements of the same row.
        for (int i = 0; i < vertices; ++i) {
            //Loop of all elements of the same column.
            for (int j = 0; j < vertices; ++j) {
                if (distanceMatrix[i][j] == INFINITY) {
                    System.out.print("∞\t");
                } else {
                    System.out.print(distanceMatrix[i][j] + "\t");
                }
            }
            System.out.println();
        }
    }
}
