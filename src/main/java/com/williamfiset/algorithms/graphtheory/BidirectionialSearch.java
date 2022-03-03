package com.williamfiset.algorithms.graphtheory;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;



public class BidirectionialSearch {
    boolean[] srcVisited;
    boolean[] destVisited;
    Deque<Integer> srcQueue;
    Deque<Integer> destQueue;
    List<List<Edge>> graph;
    List<Integer> srcParent;
    List<Integer> destParent;

    

//*********************************ALGORITHM_IMPLEMENTATION***********************************/

    public BidirectionialSearch(List<List<Edge>> graph, int n){
        srcVisited = new boolean[n];
        destVisited = new boolean[n];
        srcParent = new ArrayList<Integer>(n);
        destParent = new ArrayList<Integer>(n);
        this.graph = graph;
    }

    public void bfs(String direction) {
        if(direction == "forward"){
            int currentNode = srcQueue.poll();
            List<Edge> edges = graph.get(currentNode);
            for (Edge e : edges) {
                if(!srcVisited[e.to]){
                    srcQueue.offer(e.to);
                    srcVisited[e.to] = true;
                    srcParent.set(e.to, currentNode);
                }
            }
        } else {
            int currentNode = destQueue.poll();
            List<Edge> edges = graph.get(currentNode);
            for (Edge e : edges) {
                if(!destVisited[e.to]){
                    destQueue.offer(e.to);
                    destVisited[e.to] = true;
                    destParent.set(e.to, currentNode);
                }
            }
        }
    }

    // Find intersecting node between the two BFS's
    public int isIntersecting() {

        return -1;
    }
    // Returns the path from source to destination
    private List<Integer> getPath(int src, int dest, int intersectingNode) {
        List<Integer> path = new ArrayList<Integer>();
        return path;
    }

    public List<Integer> bidirectionalSearch(int src, int dest){
        // Add source to its queue and mark as visited
        // Parent is -1
        srcQueue.offer(src);
        srcVisited[src] = true;
        srcParent.set(0, -1);

        // Add destination to its queue and mark as visited
        // Parent is -1
        destQueue.offer(src);
        destVisited[src] = true;
        destParent.set(0, -1);

        while(!srcQueue.isEmpty() && !destQueue.isEmpty()){
            // BFS in forward direction from src
            bfs("forward");
            // BFS in backward direction from dest
            bfs("backward");
            int intersectingNode = isIntersecting();
            if(intersectingNode != -1){
                return getPath(src, dest, intersectingNode);
            }
        }
        return null;
    }



    //*********************************GRAPH_CREATION***********************************/

    public static class Edge {
        int from, to;
            public Edge(int from, int to) {
              this.from = from;
              this.to = to;
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
    graph.get(u).add(new Edge(u, v));
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
    public static void main(String[] args) {
            // BFS example #1 from slides.
        final int n = 13;
        List<List<Edge>> graph = createEmptyGraph(n);

        addUnweightedUndirectedEdge(graph, 0, 7);
        addUnweightedUndirectedEdge(graph, 0, 9);
        addUnweightedUndirectedEdge(graph, 0, 11);
        addUnweightedUndirectedEdge(graph, 7, 11);
        addUnweightedUndirectedEdge(graph, 7, 6);
        addUnweightedUndirectedEdge(graph, 7, 3);
        addUnweightedUndirectedEdge(graph, 6, 5);
        addUnweightedUndirectedEdge(graph, 3, 4);
        addUnweightedUndirectedEdge(graph, 2, 3);
        addUnweightedUndirectedEdge(graph, 2, 12);
        addUnweightedUndirectedEdge(graph, 12, 8);
        addUnweightedUndirectedEdge(graph, 8, 1);
        addUnweightedUndirectedEdge(graph, 1, 10);
        addUnweightedUndirectedEdge(graph, 10, 9);
        addUnweightedUndirectedEdge(graph, 9, 8);

        BidirectionialSearch solver = new BidirectionialSearch(graph, n);
        int src = 0;
        int dest = 10;
        List<Integer> path = solver.bidirectionalSearch(src, dest);
        if (path == null) {
            System.out.println("No path between " + src + " and " + dest);
        } else {
            System.out.println("Solution:");
            for (Integer node : path) {
                System.out.print(node + " ");
            }
        }
    }
}
