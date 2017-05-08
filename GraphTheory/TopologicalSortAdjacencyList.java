/**
 * Topological sort takes an adjacency matrix of an acyclic graph and returns
 * an array with the indexes of the nodes in a (non unique) topological order
 * which tells you how to process the nodes in the graph. More precisely from wiki:
 * A topological ordering is a linear ordering of its vertices such that for 
 * every directed edge uv from vertex u to vertex v, u comes before v in the ordering.
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 **/

import java.util.*;

// Helper Edge class to describe Edges
class Edge {
  int from, to, weight;
  public Edge (int f, int t, int w) {
    from = f; to = t; weight = w;
  }
}

public class TopologicalSortAdjacencyList {

  // Helper method that performs a depth first search on the graph to give 
  // us the topological ordering we want. Instead of maintaining a stack 
  // of the nodes we see we simply place them inside the ordering array
  // in reverse order for simplicity.
  static int topologicalSortDFS(int i, int at, boolean[] visited, int[] ordering, Map <Integer, List<Edge>> adjList) {

    visited[at] = true;

    List <Edge> edges = adjList.get(at);

    if (edges != null)
      for (Edge edge: edges)
        if (!visited[edge.to])
          i = topologicalSortDFS(i, edge.to, visited, ordering, adjList);

    ordering[i] = at;
    return i-1;

  }

  // Finds a topological ordering of the nodes in a Directed Acyclic Graph (DAG)
  // The input to this function is an adjacency list for a graph and the number
  // of nodes in the graph. 
  //
  // NOTE: 'numNodes' is not necessarily the number of nodes currently present
  // in the adjacency list since you can have singleton nodes with no edges which
  // wouldn't be present in the adjacency list but are still part of the graph!
  //
  static int [] topologicalSort(Map <Integer, List<Edge>> adjList, int numNodes) {

    int[] ordering = new int[numNodes];
    boolean[] visited = new boolean[numNodes];
    
    for (int at = 0, i = numNodes-1; at < numNodes; at++)
      if (!visited[at])
        i = topologicalSortDFS(i, at, visited, ordering, adjList);

    return ordering;

  }

  // A useful application of the topological sort is to find the shortest path 
  // between two nodes in a Directed Acyclic Graph (DAG). Given an adjacency matrix
  // this method finds the shortest path to all nodes starting at 'start'
  //
  // NOTE: 'numNodes' is not necessarily the number of nodes currently present
  // in the adjacency list since you can have singleton nodes with no edges which
  // wouldn't be present in the adjacency list but are still part of the graph!
  // 
  public static Integer[] dagShortestPath(Map <Integer, List<Edge>> adjList, int start, int numNodes) {

    int[] topsort = topologicalSort(adjList, numNodes);
    Integer[] dist = new Integer[numNodes];
    dist[start] = 0;

    for(int i = 0; i < numNodes; i++) {

      int nodeIndex = topsort[i];
      if (dist[nodeIndex] != null) {
        List <Edge> adjacentEdges = adjList.get(nodeIndex);
        if (adjacentEdges != null) {
          for (Edge edge : adjacentEdges) {

            int newDist = dist[nodeIndex] + edge.weight;
            if (dist[edge.to] == null) dist[edge.to] = newDist;
            else dist[edge.to] = Math.min(dist[edge.to], newDist);

          }
        }
      }

    }

    return dist;

  }


  // Example usage of topological sort
  public static void main(String[] args) {
    
    final int N = 7;
    Map<Integer, List<Edge>> adjList = new HashMap<>();
    for (int i = 0; i < N; i++) adjList.put(i, new ArrayList<>());

    adjList.get(0).add(new Edge(0,1,3));
    adjList.get(0).add(new Edge(0,2,2));
    adjList.get(0).add(new Edge(0,5,3));

    adjList.get(1).add(new Edge(1,3,1));
    adjList.get(1).add(new Edge(1,2,6));

    adjList.get(2).add(new Edge(2,3,1));
    adjList.get(2).add(new Edge(2,4,10));

    adjList.get(3).add(new Edge(3,4,5));

    adjList.get(5).add(new Edge(5,4,7));

    int[] ordering = topologicalSort(adjList, N);

    // // Prints: [6, 0, 5, 1, 2, 3, 4]
    System.out.println(java.util.Arrays.toString(ordering));

    // Finds all the shortest paths starting at node 0
    Integer[] dists = dagShortestPath(adjList, 0, N);

    // Find the shortest path from 0 to 4 which is 8.0
    System.out.println(dists[4]);

    // Find the shortest path from 0 to 6 which
    // is null since 6 is not reachable!
    System.out.println(dists[6]);

  }
  
}









