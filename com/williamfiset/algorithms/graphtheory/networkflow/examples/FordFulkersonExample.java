/**
 * An implementation of the Ford-Fulkerson (FF) method with a DFS
 * as a method of finding augmenting paths.
 *
 * Time Complexity: O(fE), where f is the max flow and E is the number of edges
 **/

package com.williamfiset.algorithms.graphtheory.networkflow.examples;

import static java.lang.Math.min;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class FordFulkersonExample {

  private static class Edge {
    public int from, to;
    public Edge residual;
    public long flow, capacity;
    public final long originalCapacity;
    
    public Edge(int from, int to, long capacity) {
      this.from = from;
      this.to = to;
      this.originalCapacity = this.capacity = capacity;
    }

    public String toString(int s, int t) {
      String u = (from == s) ? "s" : ((from == t) ? "t" : String.valueOf(from));
      String v = (to == s) ? "s" : ((to == t) ? "t" : String.valueOf(to));
      return String.format("Edge %s -> %s, flow = %d, capacity = %d, original capacity = %d", u, v, 
        flow, capacity, originalCapacity);
    }
  }


  private static abstract class NetworkFlowSolverBase {

    // To avoid overflow, set infinity to a value less than Long.MAX_VALUE;
    protected static final long INF = Long.MAX_VALUE / 2;

    // Inputs: n = number of nodes, s = source, t = sink
    protected final int n, s, t;

    // 'visited' and 'visitedToken' are variables used in graph sub-routines to 
    // track whether a node has been visited or not. In particular, node 'i' was 
    // recently visited if visited[i] == visitedToken is true. This is handy 
    // because to mark all nodes as unvisited simply increment the visitedToken.
    protected int visitedToken = 1;
    protected int[] visited;

    // Indicates whether the network flow algorithm has ran. The solver only 
    // needs to run once because it always yields the same result.
    protected boolean solved;

    protected long maxFlow;
    protected List<Edge>[] graph;

    /**
     * Creates an instance of a flow network solver. Use the {@link #addEdge}
     * method to add edges to the graph.
     *
     * @param n - The number of nodes in the graph including s and t.
     * @param s - The index of the source node, 0 <= s < n
     * @param t - The index of the sink node, 0 <= t < n
     */
    public NetworkFlowSolverBase(int n, int s, int t) {
      this.n = n; 
      this.s = s; 
      this.t = t; 
      initializeGraph();
      visited = new int[n];
    }

    // Constructs an empty graph with n nodes including s and t.
    private void initializeGraph() {
      graph = new List[n];
      for (int i = 0; i < n; i++)
        graph[i] = new ArrayList<Edge>();
    }

    /**
     * Adds a directed edge (and its residual edge) to the flow graph.
     *
     * @param from     - The index of the node the directed edge starts at.
     * @param to       - The index of the node the directed edge ends at.
     * @param capacity - The capacity of the edge
     */
    public void addEdge(int from, int to, long capacity) {
      if (capacity < 0) throw new IllegalArgumentException("Capacity < 0");
      Edge e1 = new Edge(from, to, capacity);
      Edge e2 = new Edge(to, from, 0);
      e1.residual = e2;
      e2.residual = e1;
      graph[from].add(e1);
      graph[to].add(e2);
    }

    /**
     * Returns the residual graph after the solver has been executed. This 
     * allows you to inspect the {@link Edge#flow}, {@link Edge#capacity},
     * and {@link Edge#originalCapacity} values of each edge. This is useful
     * if you are debugging or want to figure out which edges were used
     * during the max flow.
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
      solve();
      solved = true;
    }

    // Method to implement which solves the network flow problem.
    public abstract void solve();

  }


  private static class FordFulkersonDfsSolverAdjacencyList extends NetworkFlowSolverBase {

    /**
     * Creates an instance of a flow network solver. Use the {@link #addEdge}
     * method to add edges to the graph.
     *
     * @param n - The number of nodes in the graph including s and t.
     * @param s - The index of the source node, 0 <= s < n
     * @param t - The index of the sink node, 0 <= t < n
     */
    public FordFulkersonDfsSolverAdjacencyList(int n, int s, int t) {
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

      List<Edge> edges = graph[node];
      visited[node] = visitedToken;

      for (Edge edge : edges) {
        if (edge.capacity > 0 && visited[edge.to] != visitedToken) {
          long bottleNeck = dfs(edge.to, min(flow, edge.capacity));

          // If we made it from s -> t (a.k.a bottleNeck > 0) then
          // augment flow with bottle neck value and recurse.
          if (bottleNeck > 0) {
            Edge res = edge.residual;
            edge.flow += bottleNeck;
            edge.capacity -= bottleNeck;
            res.capacity += bottleNeck;
            return bottleNeck;
          }

        }
      }
      return 0;
    }
  }

    /* EXAMPLE */


  public static void main(String[] args) {
    exampleFromSlides();
  }

  private static void exampleFromSlides() {
    int n = 12;
    int s = n-2;
    int t = n-1;

    FordFulkersonDfsSolverAdjacencyList solver;
    solver = new FordFulkersonDfsSolverAdjacencyList(n, s, t);

    solver.addEdge(s, 1, 2);
    solver.addEdge(s, 2, 1);
    solver.addEdge(s, 0, 7);

    solver.addEdge(0, 3, 2);
    solver.addEdge(0, 4, 4);

    solver.addEdge(1, 4, 5);
    solver.addEdge(1, 5, 6);

    solver.addEdge(2, 3, 4);
    solver.addEdge(2, 7, 8);

    solver.addEdge(3, 6, 7);
    solver.addEdge(3, 7, 1);

    solver.addEdge(4, 5, 8);
    solver.addEdge(4, 8, 3);

    solver.addEdge(5, 8, 3);

    solver.addEdge(6, t, 1);
    solver.addEdge(7, t, 3);
    solver.addEdge(8, t, 4);

    // Prints:
    // Maximum Flow is: 7
    System.out.printf("Maximum Flow is: %d\n", solver.getMaxFlow());

    List<Edge>[] resultGraph = solver.getGraph();
    
    // Prints the flow and capacity value of each edge.
    for (List<Edge> edges : resultGraph)
      for (Edge e : edges)
        System.out.println(e.toString(s, t));

  }

}

