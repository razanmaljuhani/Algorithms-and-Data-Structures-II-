package cpcs324_phase2;

/* Group 5 - Razan Aljuhani - Dimah Alolayan - Majd Gezan
   CPCS324 Project Phase2
   This is a java program to find the Minimum Spanning Tree of the graph 
   using prim's algorithms by min-heap and priority queue and Kruskal algorithm
   Changes that were made to the code:
    - printing statements
    - change matrix to adjacency list to make it faster 
 */
import java.util.*; // for using Scanner, LinkedList, PriorityQueue and Comparator 
import javafx.util.Pair; // for using Pair

public class CPCS324_Phase2 {

    public static Graph graph; //initilaze graph Object from Graph class
    public static final String BLUE = "\033[0;34m";  // BLUE Colour for printing 

    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in); //to read input from user 
        System.out.println("\tCPCS324 - Project(Phase two) ");
        displayAvalibleCases();//call of displayAvalibleCases function to display avalible cases for make_graph
        int selection = input.nextInt();// to read TH chosen (case number) from user .
        System.out.println("");
        make_graph(selection);

        //calculate the minimum spanning tree for the same generated graph 
        graph.primUsingPriorityQueue();
        graph.primUsingMinHeap();
        graph.kruskalMST();

    }//end the main method

    //function for display avalible cases to make a graph randomly based on the user choies .
    public static void displayAvalibleCases() {
        System.out.println("ــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــ");
        System.out.println("         The 10 Cases for Making a graph randomly  : ");
        System.out.println("ــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــ");
        System.out.println(BLUE + "\tNodes \t Edges");
        System.out.println("1-\t1000 \t 10000");
        System.out.println("2-\t1000 \t 15000");
        System.out.println("3-\t1000 \t 25000");
        System.out.println("4-\t5000 \t 15000");
        System.out.println("5-\t5000 \t 25000");
        System.out.println("6-\t10000 \t 15000");
        System.out.println("7-\t10000 \t 25000");
        System.out.println("8-\t20000 \t 200000");
        System.out.println("9-\t20000 \t 300000");
        System.out.println("10-\t50000 \t 1000000");
        System.out.print("\n▹▹▹ Enter the case number you choice: ");
    }//end displayAvalibleCases method

    //--------------------------------------make_graph Function--------------------------------------
    //for generate random number of nodes and edges based on the selection of the user .
    public static void make_graph(int choice) {
        int numbVertices = 0, numEdges = 0;
        //Switch statement to go through the user's input
        switch (choice) {
            case 1:
                numbVertices = 1000;
                numEdges = 10000;
                break;
            case 2:
                numbVertices = 1000;
                numEdges = 15000;
                break;
            case 3:
                numbVertices = 1000;
                numEdges = 25000;
                break;
            case 4:
                numbVertices = 5000;
                numEdges = 15000;
                break;
            case 5:
                numbVertices = 5000;
                numEdges = 25000;
                break;
            case 6:
                numbVertices = 10000;
                numEdges = 15000;
                break;
            case 7:
                numbVertices = 10000;
                numEdges = 25000;
                break;
            case 8:
                numbVertices = 20000;
                numEdges = 200000;
                break;
            case 9:
                numbVertices = 20000;
                numEdges = 300000;
                break;
            case 10:
                numbVertices = 50000;
                numEdges = 1000000;
                break;
        }
        graph = new Graph(numbVertices, numEdges); // Object from Graph class
    }//end make_graph method

    //_______________Edge class_______________
    //used to create edges of graph
    public static class EdgeOF {

        int source; //The frist node of edge 
        int destination; //The second node of edge 
        int weight; // The cost of edge

        //Constructor of Edge class
        public EdgeOF(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    } //end Edge class

    //_______________ResultSet class_______________
    //used to creat ResultSet of graph by prim's 
    public static class ResultSet {

        int parent;// the result parent node
        int weight;// the cost of edge .
    }//end ResultSet class

    //_______________HeapNode class_______________
    //used to create Heap Nodes of graph
    public static class HeapNode {

        int vertex; //the node of heap tree
        int key; //key of node 

    }//end HeapNode class

    //_______________MinHeap class_______________
    //used for find MST by MinHeap .
    public static class MinHeap {

        int capacity; //the maximum number of node in heap
        int currentSize; //the curent size of heap
        HeapNode[] mH; //mH array to store heap nodes 
        int[] indexes; //indexes array will be used to decrease the key

        //Constructor of MinHeap class
        public MinHeap(int capacity) {
            this.capacity = capacity;
            mH = new HeapNode[capacity + 1];
            indexes = new int[capacity];
            mH[0] = new HeapNode();
            mH[0].key = Integer.MIN_VALUE;
            mH[0].vertex = -1;
            currentSize = 0;
        }

        //******* insert method *******  
        //this method for insert nodes to the heap
        public void insert(HeapNode x) {
            currentSize++; //increase the size of heap 
            int idx = currentSize; //idx, store the new currentsize of heap.
            mH[idx] = x; // insert new HeapNode x to mH array .
            indexes[x.vertex] = idx; //insert to indexes array the idx (key) of new node
            bubbleUp(idx); // call of bubbleUp method 
        }//end insert method.

        //******* bubbleUp method *******  
        public void bubbleUp(int idx) {
            int parentIdx = idx / 2; //the key of parentIdx = currentSize of heap divide by 2 .
            int currentIdx = idx; //the key of currentIdx = currentSize of heap divide by 2 .
            //while currentIdx > 0 and key of parentIdx > key of currentIdx do 
            while (currentIdx > 0 && mH[parentIdx].key > mH[currentIdx].key) {
                HeapNode currentNode = mH[currentIdx];//insert currentNode to mH array 
                HeapNode parentNode = mH[parentIdx];//insert parentNode to mH array 

                //swap the positions
                indexes[currentNode.vertex] = parentIdx;
                indexes[parentNode.vertex] = currentIdx;
                swap(currentIdx, parentIdx);
                currentIdx = parentIdx;
                parentIdx = parentIdx / 2; //Divide the parentIdx (key) by 2 
            }
        }//end bubbleUp method.

        //******* extractMin method ******* 
        public HeapNode extractMin() {
            HeapNode min = mH[1]; //store min node at index 1 in mH
            HeapNode lastNode = mH[currentSize]; // store lastNode at index currentSize in  mH
            indexes[lastNode.vertex] = 1;  //update the indexes[]
            //move the last node to the top 
            mH[1] = lastNode;
            mH[currentSize] = null;
            sinkDown(1); //call sinkDown method
            currentSize--; //decrease currentSize by one 
            return min; //return the min 

        }//end extractMin method.

        //******* sinkDown method *******  
        public void sinkDown(int k) {
            int smallest = k; //smallest, store the key of the parent node .
            int leftChildIdx = 2 * k; //leftChildIdx, (2*k) this is a key of leftChild node.
            int rightChildIdx = 2 * k + 1;//rightChildIdx, (2 * k + 1) this is a key of rightChild node .

            //if leftChild < smallest (set smallest to the leftChild)
            if (leftChildIdx < heapSize() && mH[smallest].key > mH[leftChildIdx].key) {
                smallest = leftChildIdx;
            }
            //if rightChild < smallest (set smallest to the rightChild)
            if (rightChildIdx < heapSize() && mH[smallest].key > mH[rightChildIdx].key) {
                smallest = rightChildIdx;
            }
            //if smallest value change , store smallestNode in mH array with new smallest key 
            if (smallest != k) {
                HeapNode smallestNode = mH[smallest];//store smallestNode in mH array with new smallest key value
                HeapNode kNode = mH[k];//store kNode in mH array with K value  
                //swap the positions
                indexes[smallestNode.vertex] = k;
                indexes[kNode.vertex] = smallest;
                swap(k, smallest); //call swap() method
                sinkDown(smallest); //recursive call of sinkDown method 
            }
        }//end sinkDown method.

        //******* swap method *******  
        public void swap(int a, int b) {
            HeapNode temp = mH[a];
            mH[a] = mH[b];
            mH[b] = temp;
        }//end swap method.

        //******* isEmpty method ******* 
        public boolean isEmpty() {
            //check if heap is embty or not ? 
            return currentSize == 0;
        }//end isEmpty method.

        //******* heapSize method *******  
        public int heapSize() {
            //this method return the currentSize of the heap .
            return currentSize;
        }//end heapSize method.

    }//end MinHeap class

    //_____________________________________________Graph class__________________________________________
    public static class Graph {

        //To calculate the runtime :
        long startTime; //Start time of program
        long endTime; //End Time of program, 
        Random random = new Random(); //object from random class

        int vertices; //Number of nodes in a graph .
        int edges; //Number Of edges in a graph

        LinkedList<EdgeOF>[] adjacencylist; // adjacencylist is representation of random graph .

        //Constructor of Graph class
        Graph(int vertices, int edges) {
            this.vertices = vertices;
            this.edges = edges;
            adjacencylist = new LinkedList[vertices];
            Random random = new Random();
            //initialize adjacency lists for all the vertices
            for (int i = 0; i < vertices; i++) {
                adjacencylist[i] = new LinkedList<>();
            }
            for (int i = 0; i < vertices - 1; i++) {
                int rand = random.nextInt(10) + 1;
                addEgde(i, i + 1, rand);
            }
            // A for loop to randomly generate edges
            for (int i = 0; i < edges; i++) {
                // Randomly select two vertices to create an edge between them
                int source = random.nextInt(vertices);
                int destination = random.nextInt(vertices);

                // Check if there is already an edge between v and u
                if ((source == destination)) {
                    i = i - 1;  // Reduce the value of i
                    continue; //for undirected graph 
                }
                int weight = (int) (1 + (Math.random() * 10));  // generate the weight randomly from 1 to 10
                addEgde(source, destination, weight); // Add an edge between them if not previously created
            }
            //printGraph(); //call printGraph() method 
        }//End the constructor

        //******* addEgde method ******* 
        //for add edges between given vertices
        public void addEgde(int source, int destination, int weight) {
            EdgeOF edge = new EdgeOF(source, destination, weight); //object from edge class
            adjacencylist[source].addFirst(edge); //make edge from source node to destination node
            edge = new EdgeOF(destination, source, weight);
            adjacencylist[destination].addFirst(edge); //for undirected graph, make edge from destination node to source node
        } //end addEdge method.

        ///******* printGraph method ******* 
        //for Print the graph that generated randomly according to the number of cases 
        public void printGraph() {
            System.out.println("The generated random graph : ");
            for (int i = 0; i < vertices; i++) {
                LinkedList<EdgeOF> list = adjacencylist[i];
                for (int j = 0; j < list.size(); j++) {
                    System.out.println("vertex- " + i + "\tis connected to " + list.get(j).destination + "\twith weight " + list.get(j).weight);
                }
            }
        }

        //--------------------------------------primUsingPriorityQueue Function--------------------------------------
        //used for apply primUsingPriorityQueue on the graph to find minimum spanning tree 
        public void primUsingPriorityQueue() {

            startTime = System.currentTimeMillis(); //start calculation the time of the algorithm

            boolean[] mst = new boolean[vertices]; //to cheack if node in mst(MinimumSpanningTree) or not 
            ResultSet[] resultSet = new ResultSet[vertices]; //array of objects from ResultSet class, used for store result set of mst
            int[] key = new int[vertices];  //array of key, used to store the key to know whether priority queue update is required or not ?

            //Initialize all the keys to infinity and
            //initialize resultSet for all the vertices
            for (int i = 0; i < vertices; i++) {
                key[i] = Integer.MAX_VALUE;
                resultSet[i] = new ResultSet();
            }

            //Initialize priority queue
            PriorityQueue<Pair<Integer, Integer>> pq = new PriorityQueue<>(vertices, (Pair<Integer, Integer> p1, Pair<Integer, Integer> p2) -> {
                //sort using key values
                int key1 = p1.getKey();
                int key2 = p2.getKey();
                return key1 - key2;
            } //override the comparator to do the sorting based keys
            );

            //create the pair for for the first index, 0 key 0 index
            key[0] = 0;
            Pair<Integer, Integer> p0 = new Pair<>(key[0], 0);
            //add it to pq
            pq.offer(p0);

            resultSet[0] = new ResultSet(); //create the resultSet for the first index 0
            resultSet[0].parent = -1; //set the parent of resultSet at indx 0 to -1 

            //while priority queue is not empty
            while (!pq.isEmpty()) {
                Pair<Integer, Integer> extractedPair = pq.poll();//extract the min
                int extractedVertex = extractedPair.getValue();//extracted vertex
                mst[extractedVertex] = true; //set true for mst at extractedVertex.

                //iterate through all the adjacent vertices and update the keys
                LinkedList<EdgeOF> list = adjacencylist[extractedVertex];
                for (int i = 0; i < list.size(); i++) {
                    EdgeOF edge = list.get(i);
                    //only if edge destination is not present in mst
                    if (mst[edge.destination] == false) {
                        int destination = edge.destination;
                        int newKey = edge.weight;
                        //check if updated key < existing key, if yes, update if
                        if (key[destination] > newKey) {
                            //add it to the priority queue
                            Pair<Integer, Integer> p = new Pair<>(newKey, destination);
                            pq.offer(p);
                            //update the resultSet for destination vertex
                            resultSet[destination].parent = extractedVertex;
                            resultSet[destination].weight = newKey;
                            //update the key[]
                            key[destination] = newKey;
                        }
                    }
                }
                //printPrimSol(resultSet);  //Call printPrimSol function to print minimum spannin tree
            }
            System.out.println("ــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــ");
            System.out.println("▹▹▹ By Prim's using (Priority Queue) Algorithm");
            printPrimSol(resultSet);  //Call printPrimSol function to print minimum spannin tree
            endTime = System.currentTimeMillis(); //End calculation the time of the algorithm
            long totalTime = endTime - startTime; //Calculate the runtime of the algorithm
            System.out.println("  ▹ The Runtime of the Prim's (Priority Queue) Algorithm in Second: " + (totalTime / 1000.0) + "\n");
        }//end primUsingPrioritQueue method .

        //******* decreaseKey method *******  
        //used for update the key if needed 
        public void decreaseKey(MinHeap minHeap, int newKey, int vertex) {

            //get the index which key's needs a decrease;
            int index = minHeap.indexes[vertex];

            //get the node and update its value
            HeapNode node = minHeap.mH[index];
            node.key = newKey;
            minHeap.bubbleUp(index); //call bubbleUp() method of minHeap class
        }//end decreaseKey method .

        //--------------------------------------primUsingMinHeap Function--------------------------------------
        //used for apply primUsingMinHeap on the graph to find minimum spanning tree .
        public void primUsingMinHeap() {
            startTime = System.currentTimeMillis(); //start calculation the time of the algorithm

            boolean[] inHeap = new boolean[vertices]; //to cheack if node in heap or not ?
            ResultSet[] resultSet = new ResultSet[vertices]; //array of objects from ResultSet class, used for store result set of mst
            int[] key = new int[vertices]; //key array used to store the keys to decide whether min-heap update is required or not?
            HeapNode[] heapNodes = new HeapNode[vertices];//Object of HeapNode class

            //Create heapNode for all the vertices and
            //initialize resultSet for all the vertices
            for (int i = 0; i < vertices; i++) {
                heapNodes[i] = new HeapNode();
                heapNodes[i].vertex = i;
                heapNodes[i].key = Integer.MAX_VALUE;
                resultSet[i] = new ResultSet();
                resultSet[i].parent = -1;
                inHeap[i] = true;
                key[i] = Integer.MAX_VALUE;
            }

            heapNodes[0].key = 0;//decrease the key for the first index

            MinHeap minHeap = new MinHeap(vertices);//Object from MinHeap class , to add all the vertices to the MinHeap

            //Add all the vertices to priority queue
            for (int i = 0; i < vertices; i++) {
                minHeap.insert(heapNodes[i]);
            }

            //while minHeap is not empty
            while (!minHeap.isEmpty()) {
                //extract the min
                HeapNode extractedNode = minHeap.extractMin();

                //extracted vertex
                int extractedVertex = extractedNode.vertex;
                inHeap[extractedVertex] = false;

                //iterate through all the adjacent vertices
                LinkedList<EdgeOF> list = adjacencylist[extractedVertex];
                for (int i = 0; i < list.size(); i++) {
                    EdgeOF edge = list.get(i);
                    //only if edge destination is present in heap
                    if (inHeap[edge.destination]) {
                        int destination = edge.destination;
                        int newKey = edge.weight;
                        //check if updated key les than existing key, if yes, update it .
                        if (key[destination] > newKey) {
                            decreaseKey(minHeap, newKey, destination);
                            //update the parent node for destination
                            resultSet[destination].parent = extractedVertex;
                            resultSet[destination].weight = newKey;
                            key[destination] = newKey;
                        }
                    }
                }
                //printPrimSol(resultSet);
            }
            System.out.println("ــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــ");
            System.out.println("▹▹▹ By Prim's using (Min-heap) Algorithm");
            printPrimSol(resultSet);  //Call printPrimSol function to print minimum spannin tree
            endTime = System.currentTimeMillis(); //End calculation the time of the algorithm
            long totalTime = endTime - startTime; //Calculate the runtime of the algorithm
            System.out.println("  ▹ The Runtime of the Prim's (Min-heap) Algorithm in Second: " + (totalTime / 1000.0) + "\n");
        }//end primUsingMinHeap method 

        //******* printPrimSol method *******  
        //used for print the solution ( minimum spanning tree ) of the graph 
        public void printPrimSol(ResultSet[] resultSet) {
            int total_min_weight = 0; //create int varible total_min_weight to calculate total cost of MST 
            //loop to print all edges of resultSet and calculate the total MST cost
            for (int k = 0; k < resultSet.length; ++k) {
                total_min_weight += resultSet[k].weight; //total cost of MST is the sum of costs of all edges in resultSet
                //print tree vertices after each itreation
               // System.out.println("Edge: " + k + " - " + resultSet[k].parent + "\tCost: " + resultSet[k].weight);
            }
            System.out.println("  ▹ Total minimum spanning tree cost: " + total_min_weight); //print total MST cost
     
        }//end printPrimSol method

        //******* makeSet method *******  
        public void makeSet(int[] parent) {
            for (int i = 0; i < vertices; i++) {
                parent[i] = i; //set parent pointing to itself
            }
        }//end makeSet method 

        //******* find method *******  
        public int find(int[] parent, int vertex) {
            //chain of parent pointers from x upwards through the tree
            // until an element is reached whose parent is itself
            if (parent[vertex] != vertex) {
                return find(parent, parent[vertex]);
            };
            return vertex;
        } //end find method 

        //******* union method *******  
        public void union(int[] parent, int x, int y) {
            int x_set_parent = find(parent, x);
            int y_set_parent = find(parent, y);
            parent[y_set_parent] = x_set_parent; //make x the parent of y
        }//end union method .

        //---------------------------------------------------- Kruskal Function----------------------------------------------
        public void kruskalMST() {

            startTime = System.currentTimeMillis(); //start time to help us analyze the efficiency
            LinkedList<EdgeOF>[] allEdges = adjacencylist;

            //Priority Queue initialization
            PriorityQueue<EdgeOF> pq = new PriorityQueue<>(adjacencylist.length, Comparator.comparingInt(o -> o.weight));

            //Add Edges to the priority queue
            for (int i = 0; i < allEdges.length; i++) {
                for (int j = 0; j < allEdges[i].size(); j++) {
                    pq.add(allEdges[i].get(j));
                }
            }
            int ii = 0;
            int[] parent = new int[vertices]; //create a parent []
            makeSet(parent); //function explaind on it's section 

            ArrayList<EdgeOF> mst = new ArrayList<>();

            int index = 0;
            while (index < vertices - 1 && !pq.isEmpty()) {
                EdgeOF edge = pq.remove();
                //check if adding the current edge will create a cycle
                int x_set = find(parent, edge.source);
                int y_set = find(parent, edge.destination);

                if (x_set == y_set) {
                    //skip beacause it'll create a cycle
                } else {
                    mst.add(edge); //no cycle therefore, add it to our mst 
                    index++;
                    union(parent, x_set, y_set); //union to the two subsets 
                }
            }
            int MST = 0;
            for (int i = 0; i < mst.size(); i++) {
                MST += mst.get(i).weight; //calculate the total weight for the Minimum Spanning Tree
            }
            System.out.println("ــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــ");
            System.out.println("▹▹▹ By kruskal's Algorithm:");
            //printKruskalSol(mst); //call of printKruskalSol
            System.out.println("  ▹ Total minimum spanning tree cost: " + MST);  //print the Minimum Spanning Tree 
            endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime;
            System.out.println("  ▹ The total Runtime in Seconds for Kurskal's Algorithm: " + (totalTime / 1000.0) + "\n");
        } //end kruskalMST method 

        //******* printKruskalSol method ***   
        //used for print the solution ( minimum spanning tree ) of the graph 
        public static void printKruskalSol(ArrayList<EdgeOF> edgeList) {
            //System.out.println("\nMinimum Spanning Tree: ");
            for (int i = 0; i < edgeList.size(); i++) {
                EdgeOF edge = edgeList.get(i);
                System.out.println("Edge: " + i + "-" + edge.destination + " cost: " + edge.weight);
            }
        } //end printKruskalSol method 

    } //end Graph class

}
