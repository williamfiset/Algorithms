/**
 * This file contains an implementation of Dijkstra's shortest path algorithm from a 
 * start node to a specific ending node. Dijkstra can also be modified
 * to find the shortest path between a starting node and all other nodes
 * in the graph. However, in this implementation since we're only going from
 * a starting node to an ending node we can employ an optimization to stop
 * early once we've visited all the neighbors of the ending node.
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 **/

import java.util.*;

public class DijkstrasShortestPathAdjacencyList {

  // An edge class to represent a directed edge 
  // between two nodes with a certain non-negative cost. 
  static class Edge {
    double cost;
    int from, to;
    public Edge(int from, int to, double cost) {
      if (cost < 0) throw new IllegalArgumentException("Dijkstra's algorithm does not permit negative edge weights");
      this.from = from;
      this.to = to;
      this.cost = cost;
    }
  }

  // Node class to track the nodes to visit while running Dijkstra's
  private static class Node implements Comparable <Node> {
    int id;
    double value;
    private static final double EPS = 1e-7;
    public Node(int nodeID, double nodeValue) {
      id = nodeID; value = nodeValue;
    }
    @Override public int compareTo(Node other) {
      if (Math.abs(value-other.value) < EPS) return 0;
      return (value - other.value) > 0 ? +1 : -1;
    }
  }

  // Run Dijkstra's algorithm on a directed graph to find the shortest path
  // from a starting node to an ending node. If there is no path between the
  // starting node and the destination node the returned value is set to be Double.POSITIVE_INFINITY.
  public static double dijkstra(Map <Integer,List<Edge>> graph, int start, int end, int n) {

    // Maintain an array of the minimum distance to each node
    double[] dists = new double[n];
    Arrays.fill(dists, Double.POSITIVE_INFINITY);
    dists[start] = 0;

    // Keep a priority queue of the next most promising node to visit
    PriorityQueue <Node> pq = new PriorityQueue<>();
    pq.offer(new Node(start, 0.0));

    // Track which nodes have already been visited
    boolean[] visited = new boolean[n];

    // In the event that you wish to rebuild the shortest path
    // you can do so using the prev array and starting at some node 'end'
    // and finding the previous node using prev[end] and the previous node
    // after that prev[prev[end]] etc... working all the way back until 
    // the index of start is found. Simply uncomment where the prev array is used.
    // int[] prev = new int[n];

    while(!pq.isEmpty()) {
      
      Node node = pq.poll();
      visited[node.id] = true;

      // We already found a better path before we got to 
      // processing this node so we can ignore it.
      if (node.value > dists[node.id]) continue;

      List <Edge> edges = graph.get(node.id);
      if (edges != null) {
        for(int i = 0; i < edges.size(); i++) {
          Edge edge = edges.get(i);

          // You cannot get a shorter path by revisiting
          // a node you have already visited before
          if (visited[edge.to]) continue;

          // Update minimum cost if applicable
          double newDist = dists[edge.from] + edge.cost;
          if (newDist < dists[edge.to]) {
            // prev[edge.to] = edge.from;
            dists[edge.to] = newDist;
            pq.offer(new Node(edge.to, dists[edge.to]));
          }

        }
      }

      // Once we've visited all the nodes spanning from the end 
      // node we know we can return the minimum distance value to 
      // the end node because it cannot get any better after this point.
      if (node.id == end) return dists[end];      
      
    }

    // End node is unreachable
    return Double.POSITIVE_INFINITY;

  }

}
