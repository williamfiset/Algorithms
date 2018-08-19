import java.awt.geom.*;
import java.util.*;

public class MiceAndOwls {

  static class Hole {
    int capacity;
    Point2D point;
    public Hole(int x, int y, int cap) {
      point = new Point2D.Double(x, y);
      capacity = cap;
    }
  }

  public static void main(String[] args) {
    Point2D[] mice = new Point2D[]{
      new Point2D.Double(1, 0),
      new Point2D.Double(0, 1),
      new Point2D.Double(8, 1),
      new Point2D.Double(12, 0),
      new Point2D.Double(12, 4),
      new Point2D.Double(15, 5)
    };
    Hole[] holes = new Hole[]{
      new Hole(1, 1, 1),
      new Hole(10, 2, 2),
      new Hole(14, 5, 1)
    };
    solve(mice, holes, /* radius= */ 3);
  }

  static void solve(Point2D[] mice, Hole[] holes, int radius) {
    final int M = mice.length;
    final int H = holes.length;
    
    final int N = M + H + 2
    final int S = N - 1;
    final int T = N - 2;
    
    NetworkFlowSolverBase solver;
    solver = new FordFulkersonDfsSolverAdjacencyList(N, S, T);

    // Source to mice
    for (int i = 0; i < M; i++) {
      solver.addEdge(S, i, 1);
    }

    // Hook up mice with holes
    for (int i = 0; i < M; i++) {
      Point2D mouse = mice[i];
      for (int j = 0; j < H; j++) {
        Point2D hole = holes[j].point;
        if (mouse.distance(hole) <= radius) {
          solver.addEdge(i, M+j, 1);
        }
      }
    }

    // Holes to sink
    for (int i = 0; i < H; i++) {
      solver.addEdge(M+i, T, holes[i].capacity);
    }

    System.out.println("Number of safe mice: " + solver.getMaxFlow());

  }

}

class Edge {
  public int from, to;
  public Edge residual;
  public long flow, capacity;
  final public long originalCapacity;
  
  public Edge(int from, int to, long capacity) {
    this.from = from;
    this.to = to;
    this.originalCapacity = this.capacity = capacity;
  }

  public String toString(int s, int t) {
    String u = (from == s) ? "s" : ((from == t) ? "t" : String.valueOf(from));
    String v = (to == s) ? "s" : ((to == t) ? "t" : String.valueOf(to));
    return String.format("Edge %s -> %s = %d/%d", u, v, flow, originalCapacity);
  }
}

abstract class NetworkFlowSolverBase {

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

  // Indicates whether the network flow algorithm has ran. We should not need to
  // run the solver multiple times, because it always yields the same result.
  protected boolean solved;

  protected long maxFlow;

  protected List<Edge>[] graph;

  /**
   * Creates an instance of a flow network solver. Use the {@link #addEdge(int, int, int)}
   * method to add edges to the graph.
   *
   * @param n - The number of nodes in the graph including source and sink nodes.
   * @param s - The index of the source node, 0 <= s < n
   * @param t - The index of the sink node, 0 <= t < n
   */
  public NetworkFlowSolverBase(int n, int s, int t) {
    this.n = n; this.s = s; this.t = t; 
    initializeGraph();
    visited = new int[n];
  }

  // Construct an empty graph with n nodes including the source and sink nodes.
  private void initializeGraph() {
    graph = new List[n];
    for (int i = 0; i < n; i++)
      graph[i] = new ArrayList<Edge>();
  }

  /**
   * Adds a directed edge (and residual edge) to the flow graph.
   *
   * @param from     - The index of the node the directed edge starts at.
   * @param to       - The index of the node the directed edge end at.
   * @param capacity - The capacity of the edge.
   */
  public void addEdge(int from, int to, long capacity) {
    Edge e1 = new Edge(from, to, capacity);
    Edge e2 = new Edge(to, from, 0);
    e1.residual = e2;
    e2.residual = e1;
    graph[from].add(e1);
    graph[to].add(e2);
  }

  /**
   * Returns the graph after the solver has been executed. This allow you to
   * inspect the {@link Edge#flow} compared to the {@link Edge#capacity} in 
   * each edge. This is useful if you want to figure out which edges were 
   * used during the max flow.
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

class FordFulkersonDfsSolverAdjacencyList extends NetworkFlowSolverBase {

  /**
   * Creates an instance of a flow network solver. Use the {@link #addEdge(int, int, int)}
   * method to add edges to the graph.
   *
   * @param n - The number of nodes in the graph including source and sink nodes.
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
        long bottleNeck = dfs(edge.to, Math.min(flow, edge.capacity));

        // Augment flow with bottle neck value
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