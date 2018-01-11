import java.util.*;

public class BridgesAdjacencyList {
  
  static int id = 0;

  public static class Edge {
    int from, to;
    public Edge(int from, int to) {
      this.from = from;
      this.to = to;
    }
    @Override
    public String toString() {
      return from + " -> " + to;
    }
  }

  public static List<Edge> findBridges(List<List<Edge>> graph, int N) {

    int[] low = new int[N]; // Low link values
    int[] ids = new int[N]; // Nodes ids
    boolean[] visited = new boolean[N];

    dfs(0, -1, ids, low, visited, graph);

    for (int i = 0; i < N; i++) {
      System.out.printf("%2d %2d %2d\n", i, ids[i], low[i]);
    }

    return null;
  }

  private static void dfs(int at, int parent, int[] ids, int[] low, boolean[] visited, List<List<Edge>> graph) {

    visited[at] = true;
    low[at] = ids[at] = id++; // <-- Better way? Use stack?

    List<Edge> edges = graph.get(at);
    if (edges != null) {
      for (Edge edge : edges) {
        int to = edge.to;
        if (to == parent) continue;
        if (!visited[to]) {
          dfs(to, at, ids, low, visited, graph);
          low[at] = Math.min(low[at], low[to]); // <-- Witchcraft?
          if (ids[at] < low[to]) {
            System.out.printf("BRDIGE: %d <-> %d\n", at, to);
          }
        } else {
          low[at] = Math.min(low[at], ids[to]);
        }
      }
    }

  }

  public static void main(String[] args) {
    int N = 10;
    List<List<Edge>> graph = new ArrayList<>();
    for(int i = 0; i < N; i++) graph.add(new ArrayList<>());
    addEdge(graph, 1-1,  2-1);
    addEdge(graph, 1-1,  3-1);
    addEdge(graph, 2-1,  3-1);
    addEdge(graph, 2-1,  4-1);
    addEdge(graph, 3-1,  4-1);
    addEdge(graph, 2-1,  5-1);
    addEdge(graph, 3-1,  8-1);
    addEdge(graph, 5-1,  7-1);
    addEdge(graph, 5-1,  6-1);
    addEdge(graph, 6-1,  7-1);
    addEdge(graph, 8-1,  9-1);
    addEdge(graph, 8-1, 10-1);

    findBridges(graph, N);

    // int N = 5;
    // List<List<Edge>> graph = new ArrayList<>();
    // for(int i = 0; i < N; i++) graph.add(new ArrayList<>());
    // addEdge(graph, 1, 2);
    // addEdge(graph, 2, 3);
    // addEdge(graph, 3, 4);
    // addEdge(graph, 4, 5);
    // addEdge(graph, 5, 3);
    // findBridges(graph, N);

  }

  static void addEdge(List<List<Edge>> graph, int from, int to) {
    List<Edge> edges = graph.get(from);
    edges.add(new Edge(from, to));
    edges = graph.get(to);
    edges.add(new Edge(to, from));
  }

}



















