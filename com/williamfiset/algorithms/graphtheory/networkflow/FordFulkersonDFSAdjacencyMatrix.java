/**
 * An implementation of the Ford-Fulkerson (FF) method with a DFS as a method of finding augmenting
 * paths. FF allows you to find the max flow through a directed graph as well as the min cut as a
 * byproduct.
 *
 * <p>Time Complexity: O(fV^2), where f is the max flow
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.graphtheory.networkflow;

public class FordFulkersonDFSAdjacencyMatrix {

  static int visitedToken = 1;

  public static int fordFulkerson(int[][] caps, int source, int sink) {

    int n = caps.length;
    int[] visited = new int[n];
    boolean[] minCut = new boolean[n];

    for (int maxFlow = 0; ; ) {

      // Try to find an augmenting path from source to sink
      int flow = dfs(caps, visited, source, sink, Integer.MAX_VALUE);
      visitedToken++;

      maxFlow += flow;
      if (flow == 0) {

        return maxFlow;

        // Uncomment to return the min-cut. The nodes on the "LEFT SIDE" of the
        // cut are marked as true and those on the "RIGHT SIDE" of the cut
        // are marked as false. This finds all the nodes which participated in the
        // last (failed) attempt to find an augmenting path from the source to
        // the sink in the DFS phase.
        // for(int i = 0; i < n; i++)
        //   if (visited[i] == visitedToken-1)
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
