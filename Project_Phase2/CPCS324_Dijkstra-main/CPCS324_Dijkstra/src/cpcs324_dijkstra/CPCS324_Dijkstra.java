package cpcs324_dijkstra;
// Group 5 - Dimah Alolayan - Majd Geddan - Razan Aljuhani 
// CPCS324 Project - implementation of Dijkstra's Algorithm Using Priority Queue
import java.util.*;

public class CPCS324_Dijkstra {

    private int dist[]; //Distance array
    private Set<Integer> settled; // Hash set
    private PriorityQueue<Node> pq;
    private int V; // Number of vertices
    List<List<Node>> adj; //adjacent nodes list
    String[] cities = {"Jeddah", "Makkah", "Madinah", "Riyadh", "Dammam", "Taif", "Abha", "Tabuk", "Qasim", "Hail", "Jizan", "Najran"};

    // Constructor uses V (Verticies) as parameter
    public CPCS324_Dijkstra(int V) {
        this.V = V;
        dist = new int[V];
        settled = new HashSet<Integer>();
        pq = new PriorityQueue<Node>(V, new Node());

    }
    //End of constructor

    // Function for Dijkstra's Algorithm
    public void dijkstra(List<List<Node>> adj, int src) {
        this.adj = adj;

        //Holding the max value in java as an initial number for destiniations, will be changed later.
        for (int i = 0; i < V; i++) {
            dist[i] = Integer.MAX_VALUE;
        }

        //Adding the source node to the priority queue
        pq.add(new Node(src, 0));

        //Distance to the source is 0, example: Jeddah --> 0
        dist[src] = 0;

        while (settled.size() != V) {
            //remove the least distance node from the priority queue
            int u = pq.remove().node;

            //adding the node whose distance is already checked and removed from the priority queue
            settled.add(u);
            adjacency(u);
        }
    }
    //End of Dijkstra's Algorithm

    //Function to find the least distance of all adjacent nodes of the node
    private void adjacency(int u) {
        int edgeDistance = -1; //Assumed initail value 
        int newDistance = -1; //Assumed initail value 

        //Iterate on all adjacent nodes of node v
        for (int i = 0; i < adj.get(u).size(); i++) {
            Node v = adj.get(u).get(i);
            //If current node is not added to the hash set already, then it calculates the distance
            if (!settled.contains(v.node)) {
                edgeDistance = v.cost;
                newDistance = dist[u] + edgeDistance;
                //This condition is to check if the new distance is less in cost and assign the right value
                if (newDistance < dist[v.node]) {
                    dist[v.node] = newDistance;
                }

                //Add node by each iteration to the priority queue
                pq.add(new Node(v.node, dist[v.node]));

            }
        }
    }
    //End of Adjacent nodes function

    public static void display(CPCS324_Dijkstra dpq, int source) {
        List<String> visited = new ArrayList<>(); //Visited tree verticies list (for printing)
        List<Distance> distances = new ArrayList<>(); //destinations list (for printing)

        for (int i = 0; i < dpq.dist.length; i++) {
            Distance dist = new Distance(); // Initialize new distance object 

            dist.dest = dpq.cities[i];
            dist.source = dpq.cities[source];
            dist.distance = dpq.dist[i];

            distances.add(dist); //Adding each city's distance, source and destination
        }

        // Distance comparing (Used for printing)
        Comparator<Distance> compareByDistance = new Comparator<Distance>() {
            @Override
            public int compare(Distance distance1, Distance distance2) {
                return distance1.distance.compareTo(distance2.distance);
            }
        };

        //Sort distances for printing 
        Collections.sort(distances, compareByDistance);
        String output;
        //Final print for each Iteration 
        for (int i = 0; i < distances.size(); i++) {
            if (!distances.get(i).dest.equals("Jeddah")) {
                output = distances.get(i).dest + '(' + distances.get(i).source + ',' + distances.get(i).distance + ')';
                visited.add(output);
            }
            if (distances.get(i).dest.equals("Jeddah")) {
                output = distances.get(i).dest + '(' + '-' + ',' + distances.get(i).distance + ')';
                visited.add(output);
            }
            System.out.println("Tree Vertices VT");

            for (String line : visited) {
                System.out.println(" ▸ " + line);
            }

            System.out.println("\nRemaining Vertices V-VT");

            for (Integer j = visited.size(); j < distances.size(); j++) {
                System.out.println(" ▹ " + distances.get(j).dest
                        + '(' + distances.get(j).source + ','
                        + distances.get(j).distance + ')');

            }

            System.out.println("--------------------------");
        }
        System.out.println("");
    }

    public static void main(String arg[]) {
        int V = 12; //Number of Verticies 
        int source = 0; //Source is assigned to 0

        //Adjacent nodes list 
        List<List<Node>> adj = new ArrayList<List<Node>>();

        //As an initialization step we need to make nodes with no edges
        for (int i = 0; i < V; i++) {
            List<Node> item = new ArrayList<Node>();
            adj.add(item);
        }

        //Filling the list to make a graph represintaion, adding each adjacent node with its distance      
        adj.get(0).add(new Node(0, 0));          adj.get(1).add(new Node(0, 79));
        adj.get(0).add(new Node(1, 79));         adj.get(1).add(new Node(1, 0));
        adj.get(0).add(new Node(2, 420));        adj.get(1).add(new Node(2, 358));
        adj.get(0).add(new Node(3, 949));        adj.get(1).add(new Node(3, 870));
        adj.get(0).add(new Node(4, 1343));       adj.get(1).add(new Node(4, 1265));
        adj.get(0).add(new Node(5, 167));        adj.get(1).add(new Node(5, 88));
        adj.get(0).add(new Node(6, 625));        adj.get(1).add(new Node(6, 627));
        adj.get(0).add(new Node(7, 1024));       adj.get(1).add(new Node(7, 1037));
        adj.get(0).add(new Node(8, 863));        adj.get(1).add(new Node(8, 876));
        adj.get(0).add(new Node(9, 777));        adj.get(1).add(new Node(9, 790));
        adj.get(0).add(new Node(10, 710));       adj.get(1).add(new Node(10, 680));
        adj.get(0).add(new Node(11, 905));       adj.get(1).add(new Node(11, 912));

        adj.get(2).add(new Node(0, 420));        adj.get(3).add(new Node(0, 949));
        adj.get(2).add(new Node(1, 358));        adj.get(3).add(new Node(1, 870));
        adj.get(2).add(new Node(2, 0));          adj.get(3).add(new Node(2, 848));
        adj.get(2).add(new Node(3, 848));        adj.get(3).add(new Node(3, 0));
        adj.get(2).add(new Node(4, 1343));       adj.get(3).add(new Node(4, 395));
        adj.get(2).add(new Node(5, 446));        adj.get(3).add(new Node(5, 782));
        adj.get(2).add(new Node(6, 985));        adj.get(3).add(new Node(6, 1064));
        adj.get(2).add(new Node(7, 679));        adj.get(3).add(new Node(7, 1304));
        adj.get(2).add(new Node(8, 518));        adj.get(3).add(new Node(8, 330));
        adj.get(2).add(new Node(9, 432));        adj.get(3).add(new Node(9, 640));
        adj.get(2).add(new Node(10, 1043));      adj.get(3).add(new Node(10, 1272));
        adj.get(2).add(new Node(11, 1270));      adj.get(3).add(new Node(11, 950));

        adj.get(4).add(new Node(0, 1343));       adj.get(5).add(new Node(0, 167));
        adj.get(4).add(new Node(1, 1265));       adj.get(5).add(new Node(1, 88));
        adj.get(4).add(new Node(2, 1343));       adj.get(5).add(new Node(2, 446));
        adj.get(4).add(new Node(3, 395));        adj.get(5).add(new Node(3, 782));
        adj.get(4).add(new Node(4, 0));          adj.get(5).add(new Node(4, 1177));
        adj.get(4).add(new Node(5, 1177));       adj.get(5).add(new Node(5, 0));
        adj.get(4).add(new Node(6, 1495));       adj.get(5).add(new Node(6, 561));
        adj.get(4).add(new Node(7, 1729));       adj.get(5).add(new Node(7, 1204));
        adj.get(4).add(new Node(8, 725));        adj.get(5).add(new Node(8, 936));
        adj.get(4).add(new Node(9, 1035));       adj.get(5).add(new Node(9, 957));
        adj.get(4).add(new Node(10, 1667));      adj.get(5).add(new Node(10, 763));
        adj.get(4).add(new Node(11, 1345));      adj.get(5).add(new Node(11, 864));

        adj.get(6).add(new Node(0, 625));        adj.get(7).add(new Node(0, 1024));
        adj.get(6).add(new Node(1, 627));        adj.get(7).add(new Node(1, 1037));
        adj.get(6).add(new Node(2, 985));        adj.get(7).add(new Node(2, 679));
        adj.get(6).add(new Node(3, 1064));       adj.get(7).add(new Node(3, 1304));
        adj.get(6).add(new Node(4, 1459));       adj.get(7).add(new Node(4, 1729));
        adj.get(6).add(new Node(5, 561));        adj.get(7).add(new Node(5, 1204));
        adj.get(6).add(new Node(6, 0));          adj.get(7).add(new Node(6, 1649));
        adj.get(6).add(new Node(7, 1649));       adj.get(7).add(new Node(7, 0));
        adj.get(6).add(new Node(8, 1488));       adj.get(7).add(new Node(8, 974));
        adj.get(6).add(new Node(9, 1402));       adj.get(7).add(new Node(9, 664));
        adj.get(6).add(new Node(10, 202));       adj.get(7).add(new Node(10, 1744));
        adj.get(6).add(new Node(11, 280));       adj.get(7).add(new Node(11, 1929));

        adj.get(8).add(new Node(0, 863));        adj.get(9).add(new Node(0, 777));
        adj.get(8).add(new Node(1, 876));        adj.get(9).add(new Node(1, 790));
        adj.get(8).add(new Node(2, 518));        adj.get(9).add(new Node(2, 432));
        adj.get(8).add(new Node(3, 330));        adj.get(9).add(new Node(3, 640));
        adj.get(8).add(new Node(4, 725));        adj.get(9).add(new Node(4, 1035));
        adj.get(8).add(new Node(5, 936));        adj.get(9).add(new Node(5, 957));
        adj.get(8).add(new Node(6, 1488));       adj.get(9).add(new Node(6, 1402));
        adj.get(8).add(new Node(7, 974));        adj.get(9).add(new Node(7, 664));
        adj.get(8).add(new Node(8, 0));          adj.get(9).add(new Node(8, 974));
        adj.get(8).add(new Node(9, 310));        adj.get(9).add(new Node(9, 0));
        adj.get(8).add(new Node(10, 1561));      adj.get(9).add(new Node(10, 1475));
        adj.get(8).add(new Node(11, 1280));      adj.get(9).add(new Node(11, 1590));

        adj.get(10).add(new Node(0, 710));       adj.get(11).add(new Node(0, 905));
        adj.get(10).add(new Node(1, 685));       adj.get(11).add(new Node(1, 912));
        adj.get(10).add(new Node(2, 1043));      adj.get(11).add(new Node(2, 1270));
        adj.get(10).add(new Node(3, 1272));      adj.get(11).add(new Node(3, 950));
        adj.get(10).add(new Node(4, 1667));      adj.get(11).add(new Node(4, 1345));
        adj.get(10).add(new Node(5, 763));       adj.get(11).add(new Node(5, 864));
        adj.get(10).add(new Node(6, 202));       adj.get(11).add(new Node(6, 280));
        adj.get(10).add(new Node(7, 1722));      adj.get(11).add(new Node(7, 1929));
        adj.get(10).add(new Node(8, 1561));      adj.get(11).add(new Node(8, 1280));
        adj.get(10).add(new Node(9, 1475));      adj.get(11).add(new Node(9, 1590));
        adj.get(10).add(new Node(10, 0));        adj.get(11).add(new Node(10, 482));
        adj.get(10).add(new Node(11, 482));      adj.get(11).add(new Node(11, 0));

        CPCS324_Dijkstra dpq = new CPCS324_Dijkstra(V); // Initialize new object with the vertices number
        dpq.dijkstra(adj, source); //check the single source shortest path

        display(dpq, source); //Dispaly final output for each iteration 
    }
}

class Distance {

    public String dest;
    public String source;
    public Integer distance;
}

//Represent a node in the graph
class Node implements Comparator<Node> {

    public int node;
    public int cost;

    public Node() {
    }

    public Node(int node, int cost) {
        this.node = node;
        this.cost = cost;
    }

    @Override
    public int compare(Node node1, Node node2) {
        if (node1.cost < node2.cost) {
            return -1;
        }
        if (node1.cost > node2.cost) {
            return 1;
        }
        return 0;
    }
}

