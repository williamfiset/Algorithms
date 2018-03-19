/**
 * An implementation of BFS with an adjacency list.
 * 
 * Time Complexity: O(V + E)
 * 
 * @author William Fiset, william.alexandre.fiset@gmail.com
 **/

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

public class BreadthFirstSearchAdjacencyListIterative {
  
  public static class Edge {
    int from, to, cost;
    public Edge(int from, int to, int cost) {
      this.from = from;
      this.to = to;
      this.cost = cost;
    }
  }

  private int n;
  private int maxDepth;
  private Integer[] prev;
  private boolean pathExists;
  private List<List<Edge>> graph;

  // Each breadth first search frontier layer gets separated by this DEPTH_TOKEN.
  // This is useful if you want to track the maximum depth of the BFS.
  private static final int DEPTH_TOKEN = -1;

  // Useful constant to indicate that we don't care about specifying an end node when doing a BFS.
  private static final int NO_END_DESTINATION = -2;

  public BreadthFirstSearchAdjacencyListIterative(List<List<Edge>> graph) {
    if (graph == null) throw new IllegalArgumentException("Graph can not be null");
    n = graph.size();
    this.graph = graph;
  }

  public int getMaximumDepth(int start) {
    bfs(start, NO_END_DESTINATION);
    return maxDepth;
  }

  /**
   * Reconstructs the path (of nodes) from 'start' to 'end' inclusive. If the edges are 
   * unweighted then this method returns the shortest path from 'start' to 'end'
   *
   * @return An array of nodes indexes of the shortest path from 'start' to 'end'.
   * If 'start' and 'end' are not connected then an empty array is returned.
   */
  public List<Integer> reconstructPath(int start, int end) {
    bfs(start, end);
    List<Integer> path = new ArrayList<>();
    if (!pathExists) return path;
    for(Integer at = end; at != null; at = prev[at])
      path.add(at);
    Collections.reverse(path);
    return path;
  }

  // Perform a breadth first search on a graph a starting node 'start'.
  private void bfs(int start, int end) {
    maxDepth = 0;
    prev = new Integer[n];
    boolean[] visited = new boolean[n];
    Deque<Integer> queue = new ArrayDeque<>(n);

    // Start by visiting the 'start' node and add it to the queue.
    queue.offer(start);
    queue.offer(DEPTH_TOKEN);
    visited[start] = true;
    
    // Continue until the BFS is done.
    while(true) {
      int node = queue.poll();
      if (node == end) pathExists = true;
      
      // If we encounter a DEPTH_TOKEN this means that we have finished the current frontier
      // and are about to start a new layer of nodes (all of which are already in the queue), or 
      // we have visited all the nodes and can terminate.
      if (node == DEPTH_TOKEN) {
        // We have visited all the nodes.
        if (queue.isEmpty()) break;

        // Add another DEPTH_TOKEN separator marker.
        queue.offer(DEPTH_TOKEN);
      } else {

        maxDepth++;
        List<Edge> edges = graph.get(node);

        // Loop through all edges attached to this node. Mark nodes as visited once they're 
        // in the queue. This will prevent having duplicate nodes in the queue and speedup the BFS.
        for(Edge edge : edges) {
          if (!visited[edge.to]) {
            visited[edge.to] = true;
            prev[edge.to] = node;
            queue.offer(edge.to);
          }
        }
      }
    }
  }

  // Initialize an empty adjacency list that can hold up to n nodes.
  public static List<List<Edge>> createEmptyGraph(int n) {
    List<List<Edge>> graph = new ArrayList<>(n);
    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
    return graph;
  }

  // Add a directed edge from node 'u' to node 'v' with cost 'cost'.
  public static void addDirectedEdge(List<List<Edge>> graph, int u, int v, int cost) {
    graph.get(u).add(new Edge(u, v, cost));
  }

  // Add an undirected edge between nodes 'u' and 'v'.
  public static void addUndirectedEdge(List<List<Edge>> graph, int u, int v, int cost) {
    addDirectedEdge(graph, u, v, cost);
    addDirectedEdge(graph, v, u, cost);
  }
  
  // Add an undirected unweighted edge between nodes 'u' and 'v'. The edge added
  // will have a weight of 1 since its intended to be unweighted.
  public static void addUnweightedUndirectedEdge(List<List<Edge>> graph, int u, int v) {
    addUndirectedEdge(graph, u, v, 1);
  }

    /* BFS example. */

  public static void main(String[] args) {
    // BFS example #1 from slides.
    final int n = 13;
    List<List<Edge>> graph = createEmptyGraph(n);

    addUnweightedUndirectedEdge(graph, 0,  7);
    addUnweightedUndirectedEdge(graph, 0,  9);
    addUnweightedUndirectedEdge(graph, 0,  11);
    addUnweightedUndirectedEdge(graph, 7,  11);
    addUnweightedUndirectedEdge(graph, 7,  6);
    addUnweightedUndirectedEdge(graph, 7,  3);
    addUnweightedUndirectedEdge(graph, 6,  5);
    addUnweightedUndirectedEdge(graph, 3,  4);
    addUnweightedUndirectedEdge(graph, 2,  3);
    addUnweightedUndirectedEdge(graph, 2,  12);
    addUnweightedUndirectedEdge(graph, 12, 8);
    addUnweightedUndirectedEdge(graph, 8,  1);
    addUnweightedUndirectedEdge(graph, 1,  10);
    addUnweightedUndirectedEdge(graph, 10, 9);
    addUnweightedUndirectedEdge(graph, 9,  8);

    BreadthFirstSearchAdjacencyListIterative solver;
    solver = new BreadthFirstSearchAdjacencyListIterative(graph);

    int start = 10, end = 5;
    List<Integer> path = solver.reconstructPath(10, 5);
    System.out.printf("The shortest path from %d to %d is: [%s]\n", start, end, formatPath(path));
    // Prints:
    // The shortest path from 10 to 5 is: [10 -> 9 -> 0 -> 7 -> 6 -> 5]
    
  }

  private static String formatPath(List<Integer> path) {
    return String.join(" -> ", path.stream()
                                   .map(Object::toString)
                                   .collect(java.util.stream.Collectors.toList()));
  }

}










