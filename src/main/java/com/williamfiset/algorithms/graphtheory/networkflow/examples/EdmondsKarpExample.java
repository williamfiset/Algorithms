/**
 * An implementation of the Edmonds-Karp algorithm to find the maximum flow.
 *
 * <p>Time Complexity: O(VE^2), where v is the number of vertices and E is the number of edges.
 *
 * <p>Download the code: $ git clone https://github.com/williamfiset/Algorithms
 *
 * <p>Change directory to the root of the Algorithms directory: $ cd Algorithms
 *
 * <p>Build: $ javac -d src/main/java
 * src/main/java/com/williamfiset/algorithms/graphtheory/networkflow/examples/EdmondsKarpExample.java
 *
 * <p>Run: $ java -cp src/main/java
 * com/williamfiset/algorithms/graphtheory/networkflow/examples/EdmondsKarpExample
 */
package com.williamfiset.algorithms.graphtheory.networkflow.examples;

import static java.lang.Math.min;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class EdmondsKarpExample {

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
    private int visitedToken = 1;
    private int[] visited;

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

    // Marks node 'i' as visited.
    public void visit(int i) {
      visited[i] = visitedToken;
    }

    // Returns true/false depending on whether node 'i' has been visited or not.
    public boolean visited(int i) {
      return visited[i] == visitedToken;
    }

    // Resets all nodes as unvisited. This is especially useful to do
    // between iterations finding augmenting paths, O(1)
    public void markAllNodesAsUnvisited() {
      visitedToken++;
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

  private static class EdmondsKarpSolver extends NetworkFlowSolverBase {

    /**
     * Creates an instance of a flow network solver. Use the {@link #addEdge(int, int, int)} method
     * to add edges to the graph.
     *
     * @param n - The number of nodes in the graph including source and sink nodes.
     * @param s - The index of the source node, 0 <= s < n
     * @param t - The index of the sink node, 0 <= t < n, t != s
     */
    public EdmondsKarpSolver(int n, int s, int t) {
      super(n, s, t);
    }

    // Run Edmonds-Karp and compute the max flow from the source to the sink node.
    @Override
    public void solve() {
      long flow;
      do {
        markAllNodesAsUnvisited();
        flow = bfs();
        maxFlow += flow;
      } while (flow != 0);
    }

    private long bfs() {
      // Initialize BFS queue and add starting source node.
      Queue<Integer> q = new ArrayDeque<>(n);
      visit(s);
      q.offer(s);

      // Perform BFS from source to sink
      Edge[] prev = new Edge[n];
      while (!q.isEmpty()) {
        int node = q.poll();
        if (node == t) break;

        for (Edge edge : graph[node]) {
          long cap = edge.remainingCapacity();
          if (cap > 0 && !visited(edge.to)) {
            visit(edge.to);
            prev[edge.to] = edge;
            q.offer(edge.to);
          }
        }
      }

      // Sink not reachable!
      if (prev[t] == null) return 0;

      // Find augmented path and bottle neck
      long bottleNeck = Long.MAX_VALUE;
      for (Edge edge = prev[t]; edge != null; edge = prev[edge.from])
        bottleNeck = min(bottleNeck, edge.remainingCapacity());

      // Retrace augmented path and update flow values.
      for (Edge edge = prev[t]; edge != null; edge = prev[edge.from]) edge.augment(bottleNeck);

      // Return bottleneck flow
      return bottleNeck;
    }
  }

  /* EXAMPLE */

  public static void main(String[] args) {
    // n is the number of nodes including the source and the sink.
    int n = 11;

    int s = n - 2;
    int t = n - 1;

    NetworkFlowSolverBase solver = new EdmondsKarpSolver(n, s, t);

    // Edges from source
    solver.addEdge(s, 0, 5);
    solver.addEdge(s, 1, 10);
    solver.addEdge(s, 2, 5);

    // Middle edges
    solver.addEdge(0, 3, 10);
    solver.addEdge(1, 0, 15);
    solver.addEdge(1, 4, 20);
    solver.addEdge(2, 5, 10);
    solver.addEdge(3, 4, 25);
    solver.addEdge(3, 6, 10);
    solver.addEdge(4, 2, 5);
    solver.addEdge(4, 7, 30);
    solver.addEdge(5, 7, 5);
    solver.addEdge(5, 8, 10);
    solver.addEdge(7, 3, 15);
    solver.addEdge(7, 8, 5);

    // Edges to sink
    solver.addEdge(6, t, 5);
    solver.addEdge(7, t, 15);
    solver.addEdge(8, t, 10);

    // Prints:
    // Maximum Flow is: 20
    System.out.printf("Maximum Flow is: %d\n", solver.getMaxFlow());

    List<Edge>[] resultGraph = solver.getGraph();

    // Displays all edges part of the resulting residual graph.
    for (List<Edge> edges : resultGraph) for (Edge e : edges) System.out.println(e.toString(s, t));
  }
}
