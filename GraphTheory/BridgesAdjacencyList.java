import static java.lang.Math.min;
import java.util.*;

public class BridgesAdjacencyList {
  
  private int n, id;
  private int[] low, ids;
  private boolean[] visited;
  private List<List<Integer>> graph;

  public BridgesAdjacencyList(List<List<Integer>> graph, int n) {
    if (graph == null || graph.size() != n) 
      throw new IllegalArgumentException();
    this.graph = graph;
    this.n = n;
  }

  // Returns a list of pair of points indicating which nodes form bridges.
  // The returned list is always of even length and indexes (0, 1) form a pair,
  // indexes (2, 3) form another pair, and indexes (2*i, 2*i+1) form a pair etc.
  public List<Integer> findBridges(List<List<Integer>> graph, int N) {

    id = 0;
    low = new int[N]; // Low link values
    ids = new int[N]; // Nodes ids
    visited = new boolean[N];

    List<Integer> bridges = new ArrayList<>();
    dfs(0, -1, bridges);

    return bridges;
  }

  private void dfs(int at, int parent, List<Integer> bridges) {

    visited[at] = true;
    low[at] = ids[at] = id++;

    List<Integer> edges = graph.get(at);
    for (Integer to : edges) {
      if (to == parent) continue;
      if (!visited[to]) {
        dfs(to, at, bridges);
        low[at] = min(low[at], low[to]);
        if (ids[at] < low[to]) {
          bridges.add(at);
          bridges.add(to);
        }
      } else {
        low[at] = min(low[at], ids[to]);
      }
    }

  }


  /* Example usage: */


  public static void main(String[] args) {
    
    int n = 10;
    List<List<Integer>> graph = createGraph(n);

    addEdge(graph, 0, 1);
    addEdge(graph, 0, 2);
    addEdge(graph, 1, 2);
    addEdge(graph, 1, 3);
    addEdge(graph, 2, 3);
    addEdge(graph, 1, 4);
    addEdge(graph, 2, 7);
    addEdge(graph, 4, 6);
    addEdge(graph, 4, 5);
    addEdge(graph, 5, 6);
    addEdge(graph, 7, 8);
    addEdge(graph, 7, 9);

    BridgesAdjacencyList solver = new BridgesAdjacencyList(graph, n);
    List<Integer> bridges = solver.findBridges(graph, n);
    for (int i = 0; i < bridges.size() / 2; i++) {
      int node1 = bridges.get(2*i);
      int node2 = bridges.get(2*i+1);
      System.out.printf("BRDIGE between nodes: %d and %d\n", node1, node2);
    }

  }

  // Initialize graph with 'n' nodes.
  public static List<List<Integer>> createGraph(int n) {
    List<List<Integer>> graph = new ArrayList<>();
    for(int i = 0; i < n; i++) graph.add(new ArrayList<>());
    return graph;
  }

  // Add undirected edge to graph.
  public static void addEdge(List<List<Integer>> graph, int from, int to) {
    graph.get(from).add(to);
    graph.get(to).add(from);
  }

}



















