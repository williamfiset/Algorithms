/**
 * Implementation of finding an Eulerian Path on a n undirected graph.
 *
 * <p>This impl is still a WIP
 *
 * <p>Run: ./gradlew run -Palgorithm=graphtheory.EulerianPathUndirectedEdgesAdjacencyList
 *
 * <p>Time Complexity: O(E)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.graphtheory;

public class EulerianPathUndirectedEdgesAdjacencyList {

  /*
  private static class EulerUndirectedEdge {
    // `u` and `v` are the ids of the two nodes which connect this edge.
    int u, v;
    double cost;

    // Whether or not this edge has been included in the Eulerian path
    boolean used;

    public EulerUndirectedEdge(int u, int v, double cost) {
      this.u = u; this.v = v; this.cost = cost;
    }

    @Override
    public String toString() {
      return "(" + u + ", " + v + ", " + used + "," + cost + ")";
    }
  } // EulerUndirectedEdge

  public static List<List<Edge>> createEmptyGraph(int n) {
    List<List<Edge>> g = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      g.add(new ArrayList<>());
    }
    return g;
  }


  public static void addEulerUndirectedEdge(List<List<Edge>> g, int u, int v, double cost) {
    Edge edge = new Edge(u, v, cost);
    // Intentionally add the same edge reference
    g.get(u).add(edge);
    g.get(v).add(edge);
  }

  private final int n;
  private int edgeCount;
  private int[] degree;
  private LinkedList<EulerUndirectedEdge> path;
  private List<List<EulerUndirectedEdge>> graph;

  public EulerianPathUndirectedEdgesAdjacencyList(List<List<EulerUndirectedEdge>> graph) {
    if (graph == null) throw new IllegalArgumentException("Graph cannot be null");
    n = graph.size();
    this.graph = graph;
    path = new LinkedList<>();
  }

  // Returns a list of edgeCount + 1 node ids that give the Eulerian path or
  // null if no path exists or the graph is disconnected.
  public List<EulerUndirectedEdge> getEulerianPath() {
    setUp();

    if (!graphHasEulerianPath()) {
      System.out.println("Graph has no Eulerian path");
      return null;
    }
    dfs(findStartNode());

    // Instead of returning the 'path' as a linked list return
    // the solution as a primitive array for convenience.
    List<EulerUndirectedEdge> soln = new ArrayList<>();
    for (int i = 0; !path.isEmpty(); i++) soln.add(path.removeFirst());

    return soln;
  }

  private static void resetUsed(List<List<EulerUndirectedEdge>> g) {
    for (int from = 0; from < n; from++) {
      for (EulerUndirectedEdge e : graph.get(from)) {
        e.used = false;
      }
    }
  }

  private void setUp() {
    degree = new int[n];
    edgeCount = 0;

    for (int from = 0; from < n; from++) {
      for (EulerUndirectedEdge e : graph.get(from)) {
        if (e.used) continue;
        degree[e.u]++;
        // Because edges have duplicate refs in the graph skip the opposing edge.
        e.used = true;
        edgeCount++;
      }
    }
    resetUsed(graph);
  }

  private boolean graphHasEulerianPath() {
    int oddNodes = 0;
    for (int i = 0; i < n; i++) {
      if (degree[i] % 2 != 0) {
        oddNodes++;
      }
    }
    // Either every vertex has even degree, or exactly two vertices have an odd degree.
    return (oddNodes == 0) || (oddNodes == 2);
  }

  private int findStartNode() {
    int start = 0;
    // TODO(william): handle the case where `0` starts on an island
    for (int i = 0; i < n; i++) {
      // Start at an odd node if one exists.
      if (degree[i] % 2 != 0) return i;
    }
    return start;
  }

  // Perform DFS to find Eulerian path.
  private void dfs(int at) {
    while (out[at] != 0) {
      EulerUndirectedEdge nextEdge = graph.get(at).get(--out[at]);
      dfs(nextEdge.to);
      path.addFirst(nextEdge);
    }
  }
  */
}
