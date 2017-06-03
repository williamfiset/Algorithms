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
    
    // Each breadth first search layer gets separated by a DEPTH_TOKEN
    // to easily augment this method for additional functionality
    final int DEPTH_TOKEN = -1;

    // Start by visiting the starting node and adding it to the queue.
    queue.offer(start);
    queue.offer(DEPTH_TOKEN);
    visited[start] = true;
    
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

      } else {
        
        count++;

        List <Edge> edges = graph.get(node);
        if (edges != null) {

          // Loop through all edges attached to this node. Mark nodes as 
          // visited once they're in the queue. This will prevent having
          // duplicate nodes in the queue and speedup the BFS.
          for(Edge edge : edges) {
            if (!visited[edge.to]) {
              visited[edge.to] = true;
              queue.offer(edge.to);
            }
          }

        }

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
    System.out.println("BFS node count starting at node 0: " + nodeCount);
    if (nodeCount != 4) System.err.println("Error with BFS");

    nodeCount = bfs(graph, 4, numNodes);
    System.out.println("BFS node count starting at node 4: " + nodeCount);
    if (nodeCount != 1) System.err.println("Error with BFS");

    // Complete graph with self loops
    graph.clear();
    numNodes = 100;
    for (int i = 0; i < numNodes; i++)
      for (int j = 0; j < numNodes; j++)
        addDirectedEdge(graph,i,j,1);

    nodeCount = bfs(graph, 6, numNodes);
    System.out.println("BFS node count starting at node 6: " + nodeCount);
    if (nodeCount != 100) System.err.println("Error with BFS");    

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










