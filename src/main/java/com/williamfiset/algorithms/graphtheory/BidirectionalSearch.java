/**
 * An implementation of bidirectional search using BFS forward and backward
 *
 *
 * @authors Johan Ekberg & David Östling
 */
package com.williamfiset.algorithms.graphtheory;

import com.williamfiset.algorithms.datastructures.graph.Graph;
import com.williamfiset.algorithms.datastructures.graph.Graph.Edge;
import com.williamfiset.algorithms.datastructures.graph.Graph.Node;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;

public class BidirectionalSearch {
    boolean[] srcVisited;
    boolean[] destVisited;
    Deque<Integer> srcQueue;
    Deque<Integer> destQueue;
    Graph graph;
    int[] srcParent;
    int[] destParent;
    
    /**
     * 
     * @param graph The graph to be searched
     * @param n Number of nodes in the graph
     */
    public BidirectionalSearch(Graph graph, int n){
        srcVisited = new boolean[n];
        destVisited = new boolean[n];
        srcParent = new int[n];
        destParent = new int[n];
        srcQueue = new ArrayDeque<>(n);
        destQueue = new ArrayDeque<>(n);
        this.graph = graph;
    }

    /**
     * Performs one step of BFS in the desired direction 
     * @param direction Direction of the bfs - forward or backward
     */
    public void bfs(String direction) {
        if(direction == "forward"){
            int currentNode = srcQueue.poll();
            List<Edge> edges = graph.getNode(currentNode).getEdges();
            for (Edge e : edges) {
                if(!srcVisited[e.getTo()]){
                    srcQueue.offer(e.getTo());
                    srcVisited[e.getTo()] = true;
                    srcParent[e.getTo()] = currentNode;
                }
            }
        } else {
            int currentNode = destQueue.poll();
            List<Edge> edges = graph.getNode(currentNode).getEdges();
            for (Edge e : edges) {
                if(!destVisited[e.getTo()]){
                    destQueue.offer(e.getTo());
                    destVisited[e.getTo()] = true;
                    destParent[e.getTo()] = currentNode;
                }
            }
        }
    }

    /**
     * Find intersecting node between the two BFS's
     * @return the number of nodes in the graph or -1
     */
    public int isIntersecting() {
        HashMap<Integer, Node> g = graph.getGraph();
        for (Integer n : g.keySet()) {
            if(srcVisited[n] && destVisited[n])
                return n;
        }
        return -1;
    }

    /**
     * Returns the path from source to destination
     * @param src the source node
     * @param dest the destination node
     * @param intersectingNode the node where both directions of the bfs intersects
     * @return the smallest path 
     */
    private List<Integer> getPath(int src, int dest, int intersectingNode) {
        List<Integer> path = new ArrayList<Integer>();
        path.add(intersectingNode);
        int temp = intersectingNode;

        while (temp != src){
            path.add(srcParent[temp]);
            temp = srcParent[temp];
        }
        Collections.reverse(path);
        temp = intersectingNode;

        while (temp != dest){
            path.add(destParent[temp]);
            temp = destParent[temp];
        }
        return path;
    }

    /**
     * 
     * @param src the source node
     * @param dest the destination node
     * @return
     */
    public List<Integer> bidirectionalSearch(int src, int dest){
        // Add source to its queue and mark as visited
        // Parent is -1
        srcQueue.offer(src);
        srcVisited[src] = true;
        srcParent[src] = -1;

        // Add destination to its queue and mark as visited
        // Parent is -1
        destQueue.offer(dest);
        destVisited[dest] = true;
        destParent[dest] = -1;

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



    public static void main(String[] args) {
        // Graph example
        final int n = 15;
        Graph g = new Graph();
        for (int i = 0; i < n; i++) {
            g.addNode(i);
        }
        g.addEdge(0, 4);
        g.addEdge(1, 4);
        g.addEdge(2, 5);
        g.addEdge(3, 5);
        g.addEdge(4, 6);
        g.addEdge(5, 6);
        g.addEdge(6, 7);
        g.addEdge(7, 8);
        g.addEdge(8, 9);
        g.addEdge(9, 11);
        g.addEdge(9, 12);
        g.addEdge(8, 10);
        g.addEdge(10, 13);
        g.addEdge(10, 14);

        BidirectionalSearch solver = new BidirectionalSearch(g, n);
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
