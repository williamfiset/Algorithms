/**
 *
 * 
 */

// A directed graph has an Eulerian trail if and only if at most one vertex has
// (out-degree) − (in-degree) = 1, at most one vertex has (in-degree) − 
// (out-degree) = 1, every other vertex has equal in-degree and out-degree, 
// and all of its vertices with nonzero degree belong to a single connected 
// component of the underlying undirected graph.

public class EulerianPathDirectedEdgesEdgeList {
  
    static class Edge {
      int from, to;
      public Edge(int from, int to) {
        this.from = from;
        this.to = to;
      }
    }
    
    // Assume that all belong to on connected component.
    static int[] eulerianPath(Edge[] edges, final int N) {
      if (edges == null || N < 0) throw IllegalArgumentException();
      
      int[] in  = new int[N];
      int[] out = new int[N];
      
      // Count in degrees and out degrees
      for (Edge edge : edges) {
        in[edge.to]++;
        out[edge.from]++;
      }
      
      int inOut = 0, outIn = 0;
      for (int i = 0; i < N; i++) {
        if (in[i] - out[i] == 1) inOut++;
        else if (out[i] - in[i] == 1) outIn++;
        else if (in[i] != out[i]) return null;
      }
      
      // Assert that an eulerian path exists on this graph.
      if (inOut > 1 || outIn > 1) return null;
      
      // Main algorithm here.
      
      return null;
    }
  
}












