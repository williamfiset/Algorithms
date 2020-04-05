/**
 * An implementation of the Ford-Fulkerson (FF) method with a DFS as a method of finding augmenting
 * paths.
 *
 * <p>Time Complexity: O(fE), where f is the max flow and E is the number of edges
 *
 * <p>Download the code: $ git clone https://github.com/williamfiset/Algorithms
 *
 * <p>Change directory to the root of the Algorithms directory: $ cd Algorithms
 *
 * <p>Build: $ javac -d src/main/java
 * src/main/java/com/williamfiset/algorithms/graphtheory/networkflow/examples/FordFulkersonExample.java
 *
 * <p>Run: $ java -cp src/main/java
 * com/williamfiset/algorithms/graphtheory/networkflow/examples/FordFulkersonExample
 */
package com.williamfiset.algorithms.graphtheory.networkflow.examples;

import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.List;

public class FordFulkersonExample {

  private static class Edge {
    public int from, to;
    public Edge residual;
    public long flow;
    public final long capacity;

    public Edge(int from, int to, long capacity) {
      this.from = from;
      this.to = to;
      this.capacity = capacity;
    }

    public boolean isResidual() {
      return capacity == 0;
    }

    public long remainingCapacity() {
      return capacity - flow;
    }

    public void augment(long bottleNeck) {
      flow += bottleNeck;
      residual.flow -= bottleNeck;
    }

    public String toString(int s, int t) {
      String u = (from == s) ? "s" : ((from == t) ? "t" : String.valueOf(from));
      String v = (to == s) ? "s" : ((to == t) ? "t" : String.valueOf(to));
      return String.format(
          "Edge %s -> %s | flow = %3d | capacity = %3d | is residual: %s",
          u, v, flow, capacity, isResidual());
    }
  }

  private abstract static class NetworkFlowSolverBase {

    // To avoid overflow, set infinity to a value less than Long.MAX_VALUE;
    static final long INF = Long.MAX_VALUE / 2;

    // Inputs: n = number of nodes, s = source, t = sink
    final int n, s, t;

    // 'visited' and 'visitedToken' are variables used in graph sub-routines to
    // track whether a node has been visited or not. In particular, node 'i' was
    // recently visited if visited[i] == visitedToken is true. This is handy
    // because to mark all nodes as unvisited simply increment the visitedToken.
    protected int visitedToken = 1;
    protected int[] visited;

    // Indicates whether the network flow algorithm has ran. The solver only
    // needs to run once because it always yields the same result.
    protected boolean solved;

    // The maximum flow. Calculated by calling the {@link #solve} method.
    protected long maxFlow;

    // The adjacency list representing the flow graph.
    protected List<Edge>[] graph;

    /**
     * Creates an instance of a flow network solver. Use the {@link #addEdge} method to add edges to
     * the graph.
     *
     * @param n - The number of nodes in the graph including s and t.
     * @param s - The index of the source node, 0 <= s < n
     * @param t - The index of the sink node, 0 <= t < n and t != s
     */
    public NetworkFlowSolverBase(int n, int s, int t) {
      this.n = n;
      this.s = s;
      this.t = t;
      initializeEmptyFlowGraph();
      visited = new int[n];
    }

    // Constructs an empty graph with n nodes including s and t.
    @SuppressWarnings("unchecked")
    private void initializeEmptyFlowGraph() {
      graph = new List[n];
      for (int i = 0; i < n; i++) graph[i] = new ArrayList<Edge>();
    }

    /**
     * Adds a directed edge (and its residual edge) to the flow graph.
     *
     * @param from - The index of the node the directed edge starts at.
     * @param to - The index of the node the directed edge ends at.
     * @param capacity - The capacity of the edge
     */
    public void addEdge(int from, int to, long capacity) {
      if (capacity <= 0) throw new IllegalArgumentException("Forward edge capacity <= 0");
      Edge e1 = new Edge(from, to, capacity);
      Edge e2 = new Edge(to, from, 0);
      e1.residual = e2;
      e2.residual = e1;
      graph[from].add(e1);
      graph[to].add(e2);
    }

    /**
     * Returns the residual graph after the solver has been executed. This allows you to inspect the
     * {@link Edge#flow} and {@link Edge#capacity} values of each edge. This is useful if you are
     * debugging or want to figure out which edges were used during the max flow.
     */
    public List<Edge>[] getGraph() {
      execute();
      return graph;
    }

    // Returns the maximum flow from the source to the sink.
    public long getMaxFlow() {
      execute();
      return maxFlow;
    }

    // Wrapper method that ensures we only call solve() once
    private void execute() {
      if (solved) return;
      solved = true;
      solve();
    }

    // Method to implement which solves the network flow problem.
    public abstract void solve();
  }

  private static class FordFulkersonDfsSolver extends NetworkFlowSolverBase {

    /**
     * Creates an instance of a flow network solver. Use the {@link #addEdge} method to add edges to
     * the graph.
     *
     * @param n - The number of nodes in the graph including s and t.
     * @param s - The index of the source node, 0 <= s < n
     * @param t - The index of the sink node, 0 <= t < n and t != s
     */
    public FordFulkersonDfsSolver(int n, int s, int t) {
      super(n, s, t);
    }

    // Performs the Ford-Fulkerson method applying a depth first search as
    // a means of finding an augmenting path.
    @Override
    public void solve() {
      // Find max flow by adding all augmenting path flows.
      for (long f = dfs(s, INF); f != 0; f = dfs(s, INF)) {
        visitedToken++;
        maxFlow += f;
      }
    }

    private long dfs(int node, long flow) {
      // At sink node, return augmented path flow.
      if (node == t) return flow;

      // Mark the current node as visited.
      visited[node] = visitedToken;

      List<Edge> edges = graph[node];
      for (Edge edge : edges) {
        if (edge.remainingCapacity() > 0 && visited[edge.to] != visitedToken) {
          long bottleNeck = dfs(edge.to, min(flow, edge.remainingCapacity()));

          // If we made it from s -> t (a.k.a bottleNeck > 0) then
          // augment flow with bottleneck value.
          if (bottleNeck > 0) {
            edge.augment(bottleNeck);
            return bottleNeck;
          }
        }
      }
      return 0;
    }
  }

  /* EXAMPLE */

  public static void main(String[] args) {
    // n is the number of nodes including the source and the sink.
    int n = 12;

    int s = n - 2;
    int t = n - 1;

    NetworkFlowSolverBase solver = new FordFulkersonDfsSolver(n, s, t);

    // Edges from source
    solver.addEdge(s, 0, 10);
    solver.addEdge(s, 1, 5);
    solver.addEdge(s, 2, 10);

    // Middle edges
    solver.addEdge(0, 3, 10);
    solver.addEdge(1, 2, 10);
    solver.addEdge(2, 5, 15);
    solver.addEdge(3, 1, 2);
    solver.addEdge(3, 6, 15);
    solver.addEdge(4, 1, 15);
    solver.addEdge(4, 3, 3);
    solver.addEdge(5, 4, 4);
    solver.addEdge(5, 8, 10);
    solver.addEdge(6, 7, 10);
    solver.addEdge(7, 4, 10);
    solver.addEdge(7, 5, 7);

    // Edges to sink
    solver.addEdge(6, t, 15);
    solver.addEdge(8, t, 10);

    // Prints:
    // Maximum Flow is: 23
    System.out.printf("Maximum Flow is: %d\n", solver.getMaxFlow());

    List<Edge>[] resultGraph = solver.getGraph();

    // Displays all edges part of the resulting residual graph.
    for (List<Edge> edges : resultGraph) for (Edge e : edges) System.out.println(e.toString(s, t));
  }
}
