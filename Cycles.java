package digraph;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Cycles {
	
	static boolean cycle = false;
	static int lastVariable;
	static Graph graph; // global variable representing the graph

	static int[] color; // global variable storing the color
	                    // of each node during DFS: 
	                    // 0 for white, 1 for gray, 2 for black

	static int[] parent;  // global variable representing the parent 
	                      // of each node in the DFS forest
	
	public static void main(String [] args) throws FileNotFoundException {
		
		File file = new File("src/ba4/cycle-detection-sample-io/test2.in");
		Scanner sc = new Scanner(file);
		int numNodes = sc.nextInt()+1;
		int numPaths = sc.nextInt()+1;
		graph = new Graph(numNodes);
		for (int i=1; i<numPaths; i++) {
			int node1 = sc.nextInt();
			int node2 = sc.nextInt();
			graph.addEdge(node1, node2);
		}
		parent = new int[numNodes];
		color = new int[numNodes];
		
		DFS(graph, numNodes);
		if (cycle) {
            String printCycle = lastVariable + "";
            int current = graph.A[lastVariable].succ;
            while(current!=lastVariable) {
                printCycle = printCycle + " " + current;
                current = graph.A[current].succ;
            }
            System.out.println(1);
            System.out.println(printCycle);
        } else {
            System.out.println(0);
        }
	}
	static void DFS(Graph myGraph, int numnodes) {
        for(int i=1; i<numnodes; i++) {
            color[i] = 0;
        }
        for (int i=1; i<numnodes; i++) {
            if (color[i]==0 && cycle == false) {
                recDFS(i, myGraph);
            }
        }
    }
	static void recDFS(int u, Graph myGraph) {
        color[u] = 1;
        int v=0;
        Edge next=myGraph.A[u];
        while(next!=null){
            v=next.succ;
            if(color[v]==0){
                parent[v]=u;
                recDFS(v, myGraph);
            }
            //there's a cycle
            if(color[v]==1 ){
                cycle = true;
                lastVariable = v;
                break;
            }
            next=next.next;
        }
        color[u]=2;
    }

}

class Edge {
	int succ;
	Edge next;

	Edge(int succ, Edge next) {
		this.succ = succ;
		this.next = next;
	}
}

class Graph {
	Edge[] A; 
	// A[u] points to the head of a linked list;
	// p in the list corresponds to an edge u -> p.succ in the graph

	Graph(int n) {
		// initialize a graph with n vertices and no edges
		A = new Edge[n];
	}
	void addEdge(int u, int v) {
		// add an edge i -> j to the graph
		A[u] = new Edge(v, A[u]);
	}
}

