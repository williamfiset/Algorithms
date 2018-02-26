/**
 * An implementation of the Bellman-Ford algorithm. The algorithm finds
 * the shortest path between a starting node and all other nodes in the graph. 
 * The algorithm also detects negative cycles.
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 **/

import java.util.*;

public class BellmanFordAdjacencyMatrix {

  private int n, start;
  private boolean solved;
  private double[] dist;
  private Integer[] prev;
  private double[][] matrix;

  /**
   * An implementation of the Bellman-Ford algorithm. The algorithm finds
   * the shortest path between a starting node and all other nodes in the graph. 
   * The algorithm also detects negative cycles. If a node is part of a negative 
   * cycle then the minimum cost for that node is set to Double.NEGATIVE_INFINITY.
   *
   * @param graph - An adjacency matrix containing directed edges forming the graph
   * @param start - The id of the starting node
   **/
  public BellmanFordAdjacencyMatrix(int start, double[][] matrix) {
    this.n = matrix.length;
    this.start = start;
    this.matrix = new double[n][n];

    // Copy input adjacency matrix.
    for (int i = 0; i < n; i++)
      this.matrix[i] = matrix[i].clone();
  }

  public double[] getShortestPaths() {
    if (!solved) solve();
    return dist;
  }

  public List<Integer> reconstructShortestPath(int end) {
    if (!solved) solve();
    List<Integer> path = new ArrayList<>();
    if (dist[end] == Double.POSITIVE_INFINITY) return path;
    for(int at = start; prev[at] != null; at = prev[at])
      path.add(at);
    path.add(end);
    return path;
  }

  public void solve() {
    if (solved) return;

    // Initialize the distance to all nodes to be infinity
    // except for the start node which is zero.
    dist = new double[n];
    java.util.Arrays.fill(dist, Double.POSITIVE_INFINITY);
    dist[start] = 0;

    // Initialize prev array which will allows for shortest path
    // reconstruction after the algorithm has terminated.
    prev = new Integer[n];

    // For each vertex, apply relaxation for all the edges
    for (int k = 0; k < n-1; k++)
      for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
          if (dist[i] + matrix[i][j] < dist[j]) {
            dist[j] = dist[i] + matrix[i][j];
            prev[j] = i;
          }


    // Run algorithm a second time to detect which nodes are part
    // of a negative cycle. A negative cycle has occurred if we 
    // can find a better path beyond the optimal solution.
    for (int k = 0; k < n-1; k++)
      for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
          if (dist[i] + matrix[i][j] < dist[j])
            dist[j] = Double.NEGATIVE_INFINITY;

    solved = true;
  }

  public static void main(String[] args) {
    
    int n = 9;
    double[][] graph = new double[n][n];

    // Setup completely disconnected graph with the distance
    // from a node to itself to be zero.
    for (int i = 0; i < n; i++) {
      java.util.Arrays.fill(graph[i], Double.POSITIVE_INFINITY);
      graph[i][i] = 0;
    }

    graph[0][1] = 1;
    graph[1][2] = 1;
    graph[2][4] = 1;
    graph[4][3] = -3;
    graph[3][2] = 1;
    graph[1][5] = 4;
    graph[1][6] = 4;
    graph[5][6] = 5;
    graph[6][7] = 4;
    graph[5][7] = 3;

    int start = 0;
    double[] d = new BellmanFordAdjacencyMatrix(start, graph).getShortestPaths();

    for (int i = 0; i < n; i++)
      System.out.printf("The cost to get from node %d to %d is %.2f\n", start, i, d[i] );

    // Output:
    // The cost to get from node 0 to 0 is 0.00
    // The cost to get from node 0 to 1 is 1.00
    // The cost to get from node 0 to 2 is -Infinity
    // The cost to get from node 0 to 3 is -Infinity
    // The cost to get from node 0 to 4 is -Infinity
    // The cost to get from node 0 to 5 is 5.00
    // The cost to get from node 0 to 6 is 5.00
    // The cost to get from node 0 to 7 is 8.00
    // The cost to get from node 0 to 8 is Infinity

  }

}










