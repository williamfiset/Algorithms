/**
 * An implementation of a iterative BFS with an adjacency list
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 **/

import java.util.*;

class Edge {
  int from, to, cost;
  public Edge(int from, int to, int cost) {
    this.from = from;
    this.to = to;
    this.cost = cost;
  }
}

public class BreadthFirstSearchAdjacencyListIterative {
  
  // Perform a breadth first search on a graph with n nodes 
  // from a starting point to count the number of nodes
  // in a given component.
  static int bfs(Map <Integer, List<Edge>> graph, int start, int n) {

    int count = 0;
    boolean[] visited = new boolean[n];
    ArrayDeque <Integer> queue = new ArrayDeque<>();
    
    // For each breadth first search layer gets separated by a DEPTH_TOKEN
    // to easily augment this method for additional functionality
    int DEPTH_TOKEN = -1;

    // Start by visiting the starting node
    queue.offer(start);
    queue.offer(DEPTH_TOKEN);
    
    // Continue until the BFS is done
    while(true) {
      
      Integer node = queue.poll();
      
      // If we encounter a depth token this means that we 
      // have finished the current frontier and are about 
      // to start the new layer (some of which may already 
      // be in the queue) or have reached the end.
      if (node == DEPTH_TOKEN) {
        
        // No more nodes to process
        if (queue.isEmpty()) break;

        // Add another DEPTH_TOKEN
        queue.offer(DEPTH_TOKEN);

      } else if (!visited[node]) {
        
        count++;
        visited[node] = true;

        List <Edge> edges = graph.get(node);
        if (edges != null)
          for(Edge edge : edges)
            if (!visited[edge.to])
              queue.offer(edge.to);

      }

    }
    
    return count;

  }
  
  // Example usage of DFS
  public static void main(String[] args) {
    
    // Create a fully connected graph
    int numNodes = 5;
    Map <Integer, List<Edge>> graph = new HashMap<>();
    addDirectedEdge(graph, 0, 1, 4);
    addDirectedEdge(graph, 0, 2, 5);
    addDirectedEdge(graph, 1, 2, -2);
    addDirectedEdge(graph, 1, 3, 6);
    addDirectedEdge(graph, 2, 3, 1);
    addDirectedEdge(graph, 2, 2, 10); // Self loop

    long nodeCount = bfs(graph, 0, numNodes);
    System.out.println("DFS node count starting at node 0: " + nodeCount);
    if (nodeCount != 4) System.err.println("Error with DFS");

    nodeCount = bfs(graph, 4, numNodes);
    System.out.println("DFS node count starting at node 4: " + nodeCount);
    if (nodeCount != 1) System.err.println("Error with DFS");

  }

  // Helper method to setup graph
  private static void addDirectedEdge( Map <Integer, List <Edge>> graph, int from, int to, int cost) {
    List <Edge> list = graph.get(from);
    if (list == null) {
      list = new ArrayList <Edge>();
      graph.put(from, list);
    }
    list.add(new Edge(from, to, cost));
  }

}










