
import java.util.*;

public class MaxMatchingBipartiteGraphAugmentingPath {


  // This function performs Maximum Cardinality Bipartite
  // Matching (MCBM) on a bipartite graph where the nodes 
  // [0,n) are in the left set and [n, n+m) in right set
  public static int mcbm(List<List<Integer>> graph, int n, int m) {
    
    int N = n + m;
    int matches = 0;
    int[] matched = new int[N];
    boolean[] visited = new boolean[N];
    Arrays.fill(matched, -1);

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) visited[j] = false;
      matches += augment(graph, visited, matched, i);
    }

    return matches;

  }

  private static int augment(List<List<Integer>> graph, boolean[] visited, int[] matched, int at) {

    // Node already matched
    if (visited[at]) return 0;
    visited[at] = true;

    List <Integer> edges = graph.get(at);
    if (edges != null) {
      for (int i = 0; i < edges.size(); i++) {
        int to = edges.get(i);
        if (matched[to] == -1 || augment(graph, visited, matched, matched[to]) != 0) {
          matched[to] = at;
          return 1;
        }
      }
    }

    // No path found
    return 0;

  } 

}