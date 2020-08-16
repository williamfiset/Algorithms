package com.williamfiset.algorithms.graphtheory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

class BidirectionalDijkstrasAlgorithm {

  private Graph reverseGraph;
  private Graph graph;
  private DijkstraExplorer startExplorer;
  private DijkstraExplorer endExplorer;

  public BidirectionalDijkstrasAlgorithm(Graph graph, boolean b) {
    this.graph = graph;
    this.reverseGraph = b ? graph.reverseGraph() : graph;
  }

  public BidirectionalDijkstrasAlgorithm(Graph graph) {
    this.graph = graph;
    this.reverseGraph = graph;
  }

  public Graph getGraph() {
    return graph;
  }

  Res bidirectionalDijkstra(String start, String end) {

    startExplorer = new DijkstraExplorer(start, graph);
    endExplorer = new DijkstraExplorer(end, reverseGraph);
    Set<String> commonSet = new HashSet<>(graph.size());
    Res res = new Res(new LinkedList<>(), Double.POSITIVE_INFINITY);
    while (true) {
      String exploringNode = startExplorer.exploring(endExplorer, commonSet);
      if (exploringNode == null || endExplorer.exploredSet.contains(exploringNode)) {
        break;
      }
      exploringNode = endExplorer.exploring(startExplorer, commonSet);
      if (exploringNode == null || startExplorer.exploredSet.contains(exploringNode)) {
        break;
      }
    }
    if (commonSet.size() > 0) {
      extractTheRes(commonSet, res);
    }
    return res;
  }

  private void extractTheRes(Set<String> commonSet, Res res) {
    double shortPath = Double.MAX_VALUE;
    String realMeetingNode = null;
    for (String node : commonSet) {
      double temp = endExplorer.distanceMap.get(node) + startExplorer.distanceMap.get(node);
      if (temp < shortPath) {
        shortPath = temp;
        realMeetingNode = node;
      }
    }
    res.shortestDistance = shortPath;
    extractPath(realMeetingNode, res.thePath);
  }

  private void extractPath(String realMeetingNode, LinkedList<String> thePath) {
    String cur = realMeetingNode;
    // add from the meeting node to start.
    while (cur != null) {
      thePath.addFirst(cur);
      cur = startExplorer.visitedMap.get(cur);
    }

    // poll the meeting node, otherwise it will add twice to the path list
    thePath.pollLast();
    cur = realMeetingNode;

    // add from the meeting node to end
    while (cur != null) {
      thePath.addLast(cur);
      cur = endExplorer.visitedMap.get(cur);
    }
  }

  static class DijkstraExplorer {
    // visited, means we visited this node during exploring a node, we haven't fully explored this
    // node yet.
    Map<String, String> visitedMap;

    // explored, means we have fully explored this node, we have been to all its neighbors nodes.
    private Set<String> exploredSet;

    // the shortest distance of a node to the init node.
    private Map<String, Double> distanceMap;
    private PriorityQueue<Edge> pq;
    private Graph graph;

    public DijkstraExplorer(String initNode, Graph graph) {
      int n = graph.size();
      this.graph = graph;
      this.exploredSet = new HashSet<>(n);
      this.visitedMap = new HashMap<>(n);
      this.distanceMap = new HashMap<>(n);
      this.pq = new PriorityQueue<>((a, b) -> Double.compare(a.distance, b.distance));
      this.distanceMap.put(initNode, 0.0);
      visitedMap.put(initNode, null);
      this.pq.offer(new Edge(initNode, 0.0));
    }

    public String exploring(DijkstraExplorer waitingExplorer, Set<String> commonSet) {
      String exploringNode = null;
      if (pq.size() > 0) {
        Edge poll = pq.poll();
        exploringNode = poll.to;
        if (exploredSet.add(exploringNode)) {
          List<Edge> edgeList = graph.getEdges(exploringNode);
          if (edgeList != null) {
            for (Edge edge : edgeList) {
              String neighbor = edge.to;
              if (!exploredSet.contains(neighbor)) {
                double newCostToNeighbor = edge.distance + distanceMap.get(exploringNode);
                if (newCostToNeighbor < distanceMap.getOrDefault(neighbor, Double.MAX_VALUE)) {
                  distanceMap.put(neighbor, newCostToNeighbor);
                  visitedMap.put(neighbor, exploringNode);
                  pq.add(new Edge(neighbor, newCostToNeighbor));
                  if (waitingExplorer.distanceMap.containsKey(neighbor)) {
                    commonSet.add(neighbor);
                  }
                }
              }
            }
          }
        }
      }
      return exploringNode;
    }
  }

  static class Res {
    LinkedList<String> thePath;
    double shortestDistance;

    public Res(LinkedList<String> thePath, double shortestDistance) {
      this.thePath = thePath;
      this.shortestDistance = shortestDistance;
    }
  }

  static class Graph {

    private Map<String, List<Edge>> graphMap;

    Graph() {
      graphMap = new HashMap<>();
    }

    Graph(int n) {
      graphMap = new HashMap<>(n);
    }

    List<Edge> getEdges(String node) {
      return graphMap.get(node);
    }

    void addDirectedEdge(String from, String to, double distance) {
      graphMap.computeIfAbsent(from, x -> new LinkedList<>()).add(new Edge(to, distance));
    }

    void addUndirectedEdge(String from, String to, double distance) {
      addDirectedEdge(from, to, distance);
      addDirectedEdge(to, from, distance);
    }

    public Graph reverseGraph() {
      Graph reverseGraph = new Graph(graphMap.size());
      for (String from : graphMap.keySet()) {
        for (Edge edge : graphMap.get(from)) {
          reverseGraph.addDirectedEdge(edge.to, from, edge.distance);
        }
      }
      return reverseGraph;
    }

    public int size() {
      return graphMap.size();
    }

    public Set<String> getNodeSet() {
      return graphMap.keySet();
    }
  }

  static class Edge {
    String to;
    double distance;

    public Edge(String to, double distance) {
      this.to = to;
      this.distance = distance;
    }
  }
}
