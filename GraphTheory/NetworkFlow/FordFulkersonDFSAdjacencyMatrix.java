

public class FordFulkersonDFSAdjacencyMatrix {

  static int visitedToken = 1;

  public static int fordFulkerson(int[][] caps, int source, int sink) {

    int n = caps.length;
    int [] visited = new int[n];
    boolean [] minCut = new boolean[n];

    for(int maxFlow = 0;;) {

      // Try to find an augmenting path from source to sink
      int flow = dfs(caps, visited, source, sink, Integer.MAX_VALUE);
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

  private static int dfs(int[][] caps, int[] visited, int node, int sink, int flow) {

    // Found sink node, return flow thus far
    if (node == sink) return flow;

    int[] cap = caps[node];
    visited[node] = visitedToken;
    
    for (int i = 0; i < cap.length; i++) {
      if (visited[i] != visitedToken && cap[i] > 0) {
        
        if (cap[i] < flow) flow = cap[i];
        int dfsFlow = dfs(caps, visited, i, sink, flow);

        if (dfsFlow > 0) {
          caps[node][i] -= dfsFlow;
          caps[i][node] += dfsFlow;
          return dfsFlow;
        }

      }
    }
    
    return 0;

  }

}


