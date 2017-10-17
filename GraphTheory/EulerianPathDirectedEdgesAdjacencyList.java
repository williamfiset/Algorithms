/**
 * 
 * 
 */
import java.util.*;


// A directed graph has an Eulerian trail if and only if at most one vertex has
// (out-degree) − (in-degree) = 1, at most one vertex has (in-degree) − 
// (out-degree) = 1, every other vertex has equal in-degree and out-degree, 
// and all of its vertices with nonzero degree belong to a single connected 
// component of the underlying undirected graph.

public class EulerianPathDirectedEdgesAdjacencyList {
  
    static List<Integer> ans = new ArrayList<>();

    static void dfs(int at, int[] edgesLeft, List<List<Integer>> graph) {
      
      if (edgesLeft[at] == 0) {
        return;
      }

      List<Integer> edges = graph.get(at);
      if (edges != null) {
        for (int i = 0; i < edges.size(); i++) {
          int from = at, to = edges.get(i);
          edgesLeft[at]--;
          dfs(to, edgesLeft, graph);
          ans.add(to);
        }
      }

    }

    // Assume that all belong to on connected component.
    static int[] eulerianPath(List<List<Integer>> graph) {

      if (graph == null) throw new IllegalArgumentException();
      
      final int N = graph.size();

      // Tracks in degrees and out degrees for each node.
      int[] in  = new int[N];
      int[] out = new int[N];
      
      // Edge count.
      int E = 0;

      for (int from = 0; from < N; from++) {
        List<Integer> edges = graph.get(from);
        for (int i = 0; i < edges.size(); i++) {
          int to = edges.get(i);
          out[from]++;
          in[to]++;
          E++;
        }
      }
      
      int start = 0, end = 0, inOut = 0, outIn = 0;
      for (int i = 0; i < N; i++) {
        if (in[i] - out[i] == 1) {
          end = i;
          inOut++;
        } else if (out[i] - in[i] == 1) {
          start = i;
          outIn++;
        } else if (in[i] != out[i]) return null;
      }
      
      // Assert that an eulerian path exists on this graph.
      if (inOut > 1 || outIn > 1) return null;
      
      // Main algorithm here.
      Stack<Integer> stack = new Stack<>();
      stack.push(start);
      
      int[] ordering = new int[E];
      int[] edgesLeft = out;

      dfs(start, edgesLeft, graph);

      // Add start node?
      System.out.println(ans);
      
      return null;
    }
  
    public static void main(String[] args) {

      int N = 5;
      List<List<Integer>> graph = new ArrayList<>(N);
      for (int i = 0; i < N; i++) graph.add(new ArrayList<>());

      graph.get(0).add(1);

      graph.get(1).add(3);
      graph.get(1).add(2);
      graph.get(1).add(4);

      graph.get(2).add(1);

      graph.get(4).add(1);

      eulerianPath(graph);

    }

}












