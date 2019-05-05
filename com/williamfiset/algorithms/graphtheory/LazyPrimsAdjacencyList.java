/**
 * An implementation of the lazy Prim's algorithm with an adjacency list
 * which upon visiting a new node adds all the edges to the min priority
 * queue and also removes already seen edges when polling.
 *
 *  Time Complexity: O(ElogE)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 **/
package com.williamfiset.algorithms.graphtheory;

import java.util.*;

public class LazyPrimsAdjacencyList {

  static class Edge implements Comparable<Edge> {
    int from, to, cost;
    public Edge(int from, int to, int cost) {
      this.from = from;
      this.to = to;
      this.cost = cost;
    }
    @Override public int compareTo(Edge other) {
      return cost - other.cost;
    }
  }

  // Graph must be undirected!
  public static Long prims(Map<Integer, List<Edge>> graph, int n) {
    
    if (graph == null) return null;
    
    long sum = 0, nodesVisited = 1;
    boolean[] visited = new boolean[n];
    PriorityQueue <Edge> pq = new PriorityQueue<>();
    
    // Add initial set of edges to the pq
    List <Edge> edges = graph.get(0);
    if (edges == null || edges.size() == 0) return null;
    for(Edge edge : edges) pq.offer(edge);
    visited[0] = true;

    // Loop while the MST is not complete
    while ( nodesVisited != n && !pq.isEmpty() ) {
      Edge edge = pq.poll();
      if (!visited[edge.to]) {
        
        // Add edges to nodes we have not yet visited
        edges = graph.get(edge.to);
        if (edges == null || edges.size() == 0) return null;
        for (Edge e : edges) 
          if (!visited[e.to])
            pq.offer(e);

        visited[edge.to] = true;
        sum += edge.cost;
        nodesVisited++;

      }
    }
    
    // Check if MST spans entire graph
    if (nodesVisited == n) return sum;
    return null;

  }

  // Example usage
  public static void main(String[] args) {
    
    // Contains tuples of (from, to, weight)
    int[][] edges = {
      
      {0, 1, 1},
      {0, 3, 4},
      {0, 4, 5},

      {1, 3, 2},
      {1, 2, 1},

      {2, 3, 5},
      {2, 5, 7},

      {3, 4, 2},
      {3, 6, 2},
      {3, 5, 11},

      {4, 7, 4},

      {5, 6, 1},
      {5, 8, 4},

      {6, 7, 4},
      {6, 8, 6},

      {7, 8, 1},
      {7, 9, 2},

      {8, 9, 0}

    };

    final int NUM_NODES = 10;

    // Setup graph as adjacency list
    Map <Integer, List<Edge>> graph = new HashMap<>();
    for (int i = 0; i < NUM_NODES; i++) graph.put(i, new ArrayList<>());
    for ( int[] tuple : edges ) {
      int from = tuple[0];
      int to = tuple[1];
      int cost = tuple[2];
      Edge edge = new Edge(from, to, cost);
      Edge revEdge = new Edge(to, from, cost);
      graph.get(from).add(edge);
      graph.get(to).add(revEdge);
    }

    Long mstCost = prims(graph, NUM_NODES);
    System.out.println("MST cost: " + mstCost);

  }

}

