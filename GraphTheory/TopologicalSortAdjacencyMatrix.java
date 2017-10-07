/**
 * This Topological sort takes an adjacency matrix of an acyclic graph and returns
 * an array with the indexes of the nodes in a (non unique) topological order
 * which tells you how to process the nodes in the graph. More precisely from wiki:
 * A topological ordering is a linear ordering of its vertices such that for 
 * every directed edge uv from vertex u to vertex v, u comes before v in the ordering.
 *
 * Time Complexity: O(V^2)
 *  
 * @author Micah Stairs
 **/

public class TopologicalSortAdjacencyMatrix {

  // Performs the topological sort and returns the
  // indexes of the nodes in topological order
  public static int[] topologicalSort(Double[][] adj) {

    // Setup
    int n = adj.length;
    boolean[] visited = new boolean[n];
    int[] order = new int[n];
    int index = n - 1;

    // Visit each node
    for (int u = 0; u < n; u++)
      if (!visited[u])
        index = visit(adj, visited, order, index, u);

    // Return topological sort
    return order;

  }

  private static int visit(Double[][] adj, boolean[] visited, int[] order, int index, int u) {
    
    if (visited[u]) return index;
    visited[u] = true;

    // Visit all neighbors  
    for (int v = 0; v < adj.length; v++)
      if (adj[u][v] != null && !visited[v])
        index = visit(adj, visited, order, index, v);

      // Place this node at the head of the list
    order[index--] = u;

    return index;

  }
  
  // A useful application of the topological sort is to find the shortest path 
  // between two nodes in a Directed Acyclic Graph (DAG). Given an adjacency matrix
  // with double valued edge weights this method finds the shortest path from 
  // a start node to all other nodes in the graph.
  public static double[] dagShortestPath(Double[][] adj, int start) {

    // Set up array used to maintain minimum distances from start
    int n = adj.length;
    double[] dist = new double[n];
    java.util.Arrays.fill(dist, Double.POSITIVE_INFINITY);
    dist[start] = 0.0;

    // Process nodes in a topologically sorted order
    for (int u: topologicalSort(adj))
      for (int v = 0; v < n; v++) {
        if (adj[u][v] != null) {
          double newDist = dist[u] + adj[u][v];
          if (newDist < dist[v])
            dist[v] = newDist;
        }
      }

    // Return minimum distance
    return dist;

  }

  // Example usage of topological sort
  public static void main(String[] args) {
    
    final int N = 7;
    Double[][] adjMatrix = new Double[N][N];

    adjMatrix[0][1] = 3.0;
    adjMatrix[0][2] = 2.0;
    adjMatrix[0][5] = 3.0;

    adjMatrix[1][3] = 1.0;
    adjMatrix[1][2] = 6.0;

    adjMatrix[2][3] = 1.0;
    adjMatrix[2][4] = 10.0;

    adjMatrix[3][4] = 5.0;

    adjMatrix[5][4] = 7.0;

    int[] ordering = topologicalSort(adjMatrix);

    // Prints: [6, 0, 5, 1, 2, 3, 4]
    System.out.println(java.util.Arrays.toString(ordering));

    // Find the shortest path from 0 to all other nodes
    double [] dists = dagShortestPath(adjMatrix, 0);

    // Find the distance from 0 to 4 which is 8.0 
    System.out.println(dists[4]);

    // Finds the shortest path from 0 to 6
    // prints Infinity (6 is not reachable!)
    System.out.println(dists[6]);

  }
  
}









