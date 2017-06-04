/**
 * An implementation of Tarjan's SCC algorithm for a directed graph.
 * Time Complexity: O(V + E)
 * @author Micah Stairs, William Fiset
 **/
import java.util.*;

class TarjanAdjacencyList {
  public static void main(String[] args) {
    
    // As an example we create a graph with four strongly connected components

    final int NUM_NODES = 10;

    // Initialize graph
    Map<Integer, List<Edge>> adjacencyList = new HashMap<>();
    for(int i = 0; i < NUM_NODES; i++) adjacencyList.put(i, new ArrayList<Edge>());

    // SCC 1 with nodes 0,1,2
    adjacencyList.get(0).add(new Edge(0, 1));
    adjacencyList.get(1).add(new Edge(1, 2));
    adjacencyList.get(2).add(new Edge(2, 0));

    // SCC 2 with nodes 3,4,5,6
    adjacencyList.get(5).add(new Edge(5, 4));
    adjacencyList.get(5).add(new Edge(5, 6));
    adjacencyList.get(3).add(new Edge(3, 5));
    adjacencyList.get(4).add(new Edge(4, 3));
    adjacencyList.get(4).add(new Edge(4, 5));
    adjacencyList.get(6).add(new Edge(6, 4));

    // SCC 3 with nodes 7,8
    adjacencyList.get(7).add(new Edge(7, 8));
    adjacencyList.get(8).add(new Edge(8, 7));

    // SCC 4 is node 9 all alone by itself
    // Add a few more edges to make things interesting

    adjacencyList.get(1).add(new Edge(1, 5));
    adjacencyList.get(1).add(new Edge(1, 7));
    adjacencyList.get(2).add(new Edge(2, 7));
    adjacencyList.get(6).add(new Edge(6, 8));
    adjacencyList.get(9).add(new Edge(9, 8));
    adjacencyList.get(9).add(new Edge(9, 4));

    Tarjan sccs = new Tarjan(adjacencyList, NUM_NODES);

    System.out.println("Strong connected component count: " + sccs.getStronglyConnectedComponentsCount());
    System.out.println("Strong connected components:\n" + Arrays.toString(sccs.getStronglyConnectedComponents()) );

    // Output:
    // Strong connected component count: 4
    // Strong connected components:
    // [2, 2, 2, 1, 1, 1, 1, 0, 0, 3]

  }
}

// Defines a directed edge which goes from node 'from' to the node 'to'
class Edge {
  int from, to;
  public Edge(int from, int to) {
    this.from = from;
    this.to = to;
  }
}

class Tarjan {

  private int n, pre, count = 0;
  private int[] low, id;
  private boolean[] marked;
  private Map <Integer, List<Edge>> graph;
  private Stack <Integer> stack = new Stack <Integer>();

  /** 
   * Runs Tarjan's strongly connected component algorithm on a directed graph, O(E+V)
   * @param graph - An adjacency list containing directed edges
   * @param N     - The number of nodes in the graph
   **/
  public Tarjan(Map <Integer, List<Edge>> graph, int n) {

    this.n = n;
    this.graph = graph;
    marked = new boolean[n];
    id = new int[n];
    low = new int[n];

    for (int from = 0; from < n; from++)
      if (!marked[from])
        dfs(from);

  }

  // Depth first search
  private void dfs(int from) {

    marked[from] = true;
    low[from] = pre++;
    int min = low[from];
    stack.push(from);

    List<Edge> edges = graph.get(from);

    for (Edge edge: edges) {
      if (!marked[edge.to]) dfs(edge.to);
      if (low[edge.to] < min) min = low[edge.to];
    }

    if (min < low[from]) {
      low[from] = min;
      return;
    }
    int to;
    do {
      to = stack.pop();
      id[to] = count;
      low[to] = n;
    } while (to != from);
    count++;
  }

  // Returns the number of strongly connected components in this graph
  public int getStronglyConnectedComponentsCount() {
    return count;
  }


  // Returns the id array with the strongly connected components.
  // If id[i] == id[j] then nodes i and j are part of the same strongly connected component.
  public int[] getStronglyConnectedComponents() {
    return id.clone();
  }

}

