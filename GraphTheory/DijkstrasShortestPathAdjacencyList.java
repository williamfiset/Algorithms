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

class Edge {
  double cost;
  int from, to;
  public Edge(int from, int to, double cost) {
    this.from = from;
    this.to = to;
    this.cost = cost;
  }
}

public class DijkstrasShortestPathAdjacencyList {

  private static class Node implements Comparable <Node> {
    int id;
    double value;
    public Node(int nodeID, double nodeValue) {
      id = nodeID; value = nodeValue;
    }
    @Override public int compareTo(Node other) {
      return Double.compare(value, other.value);
    }
  }

  public static double dijkstra(Map<Integer,List<Edge>> graph, int start, int end, int n) {
  
    double[] dists = new double[n];
    Arrays.fill(dists, Double.POSITIVE_INFINITY);
    dists[start] = 0;

    boolean[] visited = new boolean[n];
    PriorityQueue <Node> pq = new PriorityQueue<>();
    pq.offer(new Node(start, 0.0));

    while(!pq.isEmpty()) {
      
      Node node = pq.poll();
      visited[node.id] = true;

      // Stale node, ignore it because we've already found a better path
      if (node.value > dists[node.id]) continue;

      List <Edge> edges = graph.get(node.id);
      if (edges != null) {
        for(int i = 0; i < edges.size(); i++) {
          Edge edge = edges.get(i);
          double newDist = dists[edge.from] + edge.cost;
          if (newDist < dists[edge.to]) {
            dists[edge.to] = newDist;
            if (!visited[edge.to])
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
