/* 
Group 5 - Dimah Alolayan - Majd Gezan - Razan Aljuhani 
CPCS324 Project - implementation of the Ford-fulkerson method Edmonds-Karp algorithm for finding the flow augmentation paths
*/
package cpcs324edmondskarp;
import java.util.*;
public class CPCS324EdmondsKarp {

    //method to calculates the maximum flow of a graph 
    public int maxFlow(int graph[][], int source, int sink){
        //put the residual graph as total available graph initially.
        int residualGraph[][] = new int[graph.length][graph[0].length];
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph.length; j++) {
                residualGraph[i][j] = graph[i][j];
            }
        }
        
        //array for storing BFS parent
         int parent[] = new int[graph.length];

        //stores all the augmented paths
        List<List<Integer>> augmentedPaths = new ArrayList<>();

        //max flow we can get in this network
        int maxFlow = 0;

        //see if augmented path can be found from source to sink
        while(BFS(residualGraph, parent, source, sink)){
            List<Integer> augmentedPath = new ArrayList<>();
            int flow = Integer.MAX_VALUE; 
            //find minimum residual graph in augmented path and add vertices to augmented path list
            int v = sink;
            while(v != source){
                augmentedPath.add(v);
                int u = parent[v];
                if (flow > residualGraph[u][v]) {
                    flow = residualGraph[u][v];
                }
                v = u;
            }
            augmentedPath.add(source);
            Collections.reverse(augmentedPath);
            augmentedPaths.add(augmentedPath);

            //add min graph to max flow
            maxFlow += flow;
            printAugmentedPath(augmentedPath, flow, maxFlow);
            //decrease residual graph by min graph from u to v in augmented path 
            //and increase residual graph by min graph from v to u
            v = sink;
            while(v != source){
                int u = parent[v];
                residualGraph[u][v] -= flow;
                residualGraph[v][u] += flow;
                v = u;
            }
        }
        //array with initial values for the visited node are false
        boolean[] visited = {false, false, false, false, false, false};
        //find vertices reachable from the source
        MinCut(residualGraph, source, visited);
        System.out.println("\nMaximum flow of the network is " + maxFlow);
        System.out.println("----------------------------------");
        System.out.println("The min-cut is: " + maxFlow);
        System.out.println("The min-cut edges are: ");
        //print all edges that are from a reachable vertex to
        //non-reachable vertex in the original graph
        for (int i = 0; i < visited.length; i++) {
            for (int j = 0; j < visited.length; j++) {
                if (visited[i] && !visited[j] && graph[i][j] != 0) {
                    int tmpI = i+1;
                    int tmpJ = j+1;
                    System.out.println("Edge: "+tmpI + " ➜ " + tmpJ);
                }
            }
        }
        return maxFlow;
    }

    //method to find all reachable vertices from a source node
   public void MinCut(int graph[][], int source, boolean visited[]) {
        visited[source] = true;
        for (int i = 0; i < 6; i++) {
            if (graph[source][i] != 0 && !visited[i]) {
                MinCut(graph, i, visited);
            }
        }
    }

   //method to print all the augmented path of max flow
    private void printAugmentedPath(List<Integer> augmentedPath, int flow, int maxFlow) {
        for (int i = 0; i < augmentedPath.size(); i++) {
            // if the current node in a path isn't the last node, print an arrow pointing to the next node
            if (i != augmentedPath.size()-1) {
                System.out.print(augmentedPath.get(i)+1 + " ➜ ");
            } else {
                System.out.print(augmentedPath.get(i)+1);
                System.out.print(" flow: " + flow);
            }
        }
        System.out.println();
        System.out.println("Updated flow: " + maxFlow);
    }
    //method BFS to find augmented path
    boolean BFS(int residualGraph[][], int parent[], int source, int sink)
    {
        //create array and mark all vertices as not visited
        boolean visited[] = new boolean[residualGraph.length];
        for (int i = 0; i < residualGraph.length; ++i)
            visited[i] = false;
 
        //create a queue, add source vertex and mark source vertex as visited
        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(source);
        visited[source] = true;
        parent[source] = -1;

        //if we can find augmented path from source to sink
        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int v = 0; v < residualGraph.length; v++) {
                //explore the vertex only if it is not visited and its residual graph is greater than 0
                if (visited[v] == false && residualGraph[u][v] > 0) {
                    if (v == sink) {
                        //add in array parent that v got explored by u
                        parent[v] = u;
                        return true;
                    }
                    //add v to queue for BFS
                    queue.add(v);
                    parent[v] = u;
                    //mark v as visited
                    visited[v] = true;
                }
            }
        }
        return false;
    }
    public static void main(String args[]){
        CPCS324EdmondsKarp flow = new CPCS324EdmondsKarp();
        //Construct graph as an adjacency matrix
        int[][] graph = {
                //1  2  3  4  5  6
                 {0, 2, 7, 0, 0, 0}, // 1
                 {0, 0, 0, 3, 4, 0}, // 2
                 {0, 0, 0, 4, 2, 0}, // 3
                 {0, 0, 0, 0, 0, 1}, // 4
                 {0, 0, 0, 0, 0, 5}, // 5
                 {0, 0, 0, 0, 0, 0}, // 6
        };
        System.out.println("\tCPCS324 - Project(Phase three) - Edmonds-Karp Algorithm");
        System.out.println("\t--------------------------------------------------------");
        flow.maxFlow(graph, 0, 5);
    }
}
