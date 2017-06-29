
import java.util.*;

class Edge {
  Edge residualEdge;
  int to, capacity, i;
  public Edge(int t, int c, int ii) {
    to = t; capacity = c; i = ii;
  }
}

public class FordFulkersonDFSAdjacencyList {

  static int visitedToken = 1;

  // Construct an empty graph
  public static List<Edge>[] createGraph(int n) {
    List<Edge>[] graph = new ArrayList[n];
    for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
    return graph;
  }

  public static void addEdge(List<Edge>[] graph, int from, int to, int cap) {
    Edge edge = new Edge(to, cap);
    Edge rEdge = new Edge(from, 0);
    edge.residualEdge = rEdge;
    rEdge.residualEdge = edge;
    graph[from].add(edge);
    graph[to].add(rEdge);
  }

  // Performs the Ford-Fulkerson method applying a depth first search as 
  // a means of finding an augmenting path. The input consists of a directed graph
  // with specified capacities on the edges.
  public static int fordFulkerson(List<Edge>[] graph, int source, int sink) {

    int n = graph.length;
    int [] visited = new int[n];
    boolean [] minCut = new boolean[n];

    for(int maxFlow = 0;;) {

      // Try to find an augmenting path from source to sink
      int flow = dfs(graph, visited, source, sink, Integer.MAX_VALUE);
      visitedToken++;
      
      maxFlow += flow;
      if (flow == 0) {

        return maxFlow;

        // Uncomment for min-cut. This finds all the edges found in
        // the last augmenting path in the DFS phase.
        // for(int i = 0; i < n; i++)
        //   if (visited[i] == visitedToken)
        //     minCut[i] = true;
        // return minCut;

      }

    }
  }

  private static int dfs(List<Edge>[] graph, int[] visited, int node, int sink, int flow) {

    // Found sink node, return augmented path flow
    if (node == sink) return flow;

    List <Edge> edges = graph[node];
    visited[node] = visitedToken;
    
    if (edges != null) {
      for (int i = 0; i < edges.size(); i++) {
        Edge edge = edges.get(i);
        if (visited[i] != visitedToken && edge.capacity > 0) {
          
          if (edge.capacity < flow) flow = edge.capacity;
          int dfsFlow = dfs(graph, visited, i, sink, flow);

          if (dfsFlow > 0) {
            Edge residualEdge = edge.residualEdge;
            edge.capacity -= dfsFlow;
            residualEdge.capacity += dfsFlow;
            return dfsFlow;
          }

        }
      }
    }
    
    return 0;

  }

}


