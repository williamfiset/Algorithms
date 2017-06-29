/**
 * This is an implementation of the augmenting path algorithm to find the 
 * Maximum Cardinality Bipartite Matching (MCBM) on a bipartite graph.
 * To summarize, this means that given a bipartite graph we are able
 * to find the matching between two groups which yields the most 
 * number of edges used.
 *
 * Code tested against: https://open.kattis.com/problems/gopher2
 *
 * Time Complexity: O(VE)
 * 
 * @author William Fiset, william.alexandre.fiset@gmail.com
 **/

import java.util.*;

public class MaximumCardinalityBipartiteMatchingAugmentingPathAdjacencyList {

  static final int FREE = -1;
  static int visitToken = 1;

  /** 
   * This function performs Maximum Cardinality Bipartite
   * Matching (MCBM) on a bipartite graph where the nodes 
   * [0,n) are in the left set and [n, n+m) in right set
   * @param graph - An adjacency list representing an undirected bipartite graph.
   **/
  public static int mcbm(List<List<Integer>> graph, int n, int m) {
    
    int N = n + m, matches = 0;
    
    int[] visited = new int[n];
    int[] next = new int[N];
    Arrays.fill(next, FREE);

    for (int i = 0; i < n; i++) {
      matches += augment(graph, visited, next, i);
      visitToken++;
    }

    return matches;

  }

  private static int augment(List<List<Integer>> graph, int[] visited, int[] next, int at) {

    // Node already visited in this augmenting path
    if (visited[at] == visitToken) return 0;
    visited[at] = visitToken;

    List <Integer> edges = graph.get(at);
    if (edges != null) {
      for (int i = 0; i < edges.size(); i++) {
        
        int rightNode = edges.get(i);

        // If the value of node is FREE then the node on the right
        // side has not yet been matched, otherwise the value of 
        // node refers to the index of the left on the left set we
        // want to visit to finding an alternating path
        int node = next[rightNode];

        // If the node on the right side has not been matched
        if (node == FREE) {
          
          // Record which node we came from and return
          // 1 to indicate a path was found
          next[rightNode] = at;
          return 1;

        // We were able to find an alternating path
        } else if(augment(graph, visited, next, node) != 0) {
          
          // Record which node we came from and return
          // 1 to indicate a path was found
          next[rightNode] = at;
          return 1;

        }

      }
    }

    // No path found :/
    return 0;

  }

  public static void main(String[] args) {
    
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < 8; i++) graph.add(new ArrayList<>());
    
    // Left set includes {0,1,2,3} and right set {4,5,6,7}
    addEdge(graph, 0, 4);
    addEdge(graph, 1, 5);
    addEdge(graph, 2, 7);
    addEdge(graph, 3, 6);
    addEdge(graph, 4, 1);
    addEdge(graph, 5, 1);
    addEdge(graph, 6, 1);

    System.out.println(mcbm(graph,4,4));

  }

  private static void addEdge(List<List<Integer>> graph, int from, int to) {
    graph.get(from).add(to);
  }

}



















