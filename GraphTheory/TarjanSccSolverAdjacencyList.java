/**
 * An implementation of Tarjan's Strongly Connected Components algorithm
 * using an adjacency list.
 *
 * Time complexity: O(V+E)
 * @author William Fiset, william.alexandre.fiset@gmail.com
 **/

import static java.lang.Math.min;
import java.util.*;

public class TarjanSccSolverAdjacencyList {

  private int n;
  private List<List<Integer>> graph;

  private boolean solved;
  private int sccCount, id;
  private boolean[] onStack;
  private int[] ids, low;
  private Deque<Integer> stack;

  private static final int UNVISITED = -1;

  public TarjanSccSolverAdjacencyList(List<List<Integer>> graph) {
    if (graph == null) throw new IllegalArgumentException("Graph cannot be null.");
    n = graph.size();
    this.graph = graph;
  }
  
  // Returns the number of strongly connected components in the graph.
  public int sccCount() {
    if (!solved) solve();
    return sccCount;
  }
  
  // Get the connected components of this graph. If two indexes
  // have the same value then they're in the same SCC.
  public int[] getSccs() {
    if (!solved) solve();
    return low;
  }

  public void solve() {
    if (solved) return;
    
    ids = new int[n];
    low = new int[n];
    onStack = new boolean[n];
    stack = new ArrayDeque<>();
    Arrays.fill(ids, UNVISITED);

    for(int i = 0; i < n; i++)
      if (ids[i] == UNVISITED)
        dfs(i);

    solved = true;
  }
  
  private void dfs(int at) {
    
    System.out.printf("AT: %d - %s\n", at, Arrays.toString(low));
    stack.push(at);
    onStack[at] = true;
    ids[at] = low[at] = id++;

    for(int to : graph.get(at)) {
      if (ids[to] == UNVISITED) dfs(to);
      if (onStack[to]) low[at] = min(low[at], low[to]);  
    }

    System.out.printf("CALLBACK: %d - %s - %s\n", at, Arrays.toString(low), stack);

    // On recursive callback, if we're at the root node (start of SCC) 
    // empty the seen stack until back to root.
    if (low[at] == ids[at]) {
      for(int node = stack.pop();;node = stack.pop()) {
        onStack[node] = false;
        low[node] = sccCount;
        System.out.println(node);
        if (node == at) break;
      }
      System.out.println();
      sccCount++;
    }

  }

  // Sets up adjacency list with n nodes. 
  public static List<List<Integer>> createGraph(int n) {
    List<List<Integer>> graph = new ArrayList<>();
    for(int i = 0; i < n; i++) graph.add(new ArrayList<>());
    return graph;
  }

  // Adds a directed edge from node 'from' to node 'to'
  public static void addEdge(List<List<Integer>> graph, int from, int to) {
    graph.get(from).add(to);
  }

  public static void main(String[] arg) {
    butterflyStrangeOrdering();
  }

  public static void grid3x3() {
    int n = 9;
    List<List<Integer>> graph = createGraph(n);
    addEdge(graph, 0, 1);
    addEdge(graph, 1, 2);
    addEdge(graph, 0, 3);
    addEdge(graph, 4, 0);
    addEdge(graph, 5, 1);
    addEdge(graph, 2, 5);
    addEdge(graph, 3, 4);
    addEdge(graph, 4, 5);
    addEdge(graph, 4, 6);
    addEdge(graph, 7, 4);
    addEdge(graph, 4, 8);
    addEdge(graph, 5, 8);
    addEdge(graph, 6, 7);
    addEdge(graph, 7, 8);
    addEdge(graph, 8, 8);
    TarjanSccSolverAdjacencyList solver = new TarjanSccSolverAdjacencyList(graph);
    solver.solve();
    System.out.println(Arrays.toString(solver.getSccs()));
    System.out.println("SCCs: " + solver.sccCount());
  }

  public static void butterflyStrangeOrdering() {
    int n = 5;
    List<List<Integer>> g = createGraph(n);
    addEdge(g, 0 , 1);
    addEdge(g, 1 , 2);
    addEdge(g, 2 , 3);
    addEdge(g, 3 , 1);
    addEdge(g, 1 , 4);
    addEdge(g, 4 , 0);
    TarjanSccSolverAdjacencyList solver = new TarjanSccSolverAdjacencyList(g);
    solver.solve();
    System.out.println(Arrays.toString(solver.getSccs()));
    System.out.println("SCCs: " + solver.sccCount());
  }

}
