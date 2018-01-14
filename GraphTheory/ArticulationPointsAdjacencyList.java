import static java.lang.Math.min;
import java.util.*;

public class ArticulationPointsAdjacencyList {

  private static final int ROOT = 0;

  private int n, id, rootNodeChildren;
  private int[] low, ids;
  private boolean[] visited, isArticulationPoint;
  private List<List<Integer>> graph;

  public ArticulationPointsAdjacencyList(List<List<Integer>> graph, int n) {
    if (graph == null || n <= 0 || graph.size() != n)
      throw new IllegalArgumentException();
    this.graph = graph;
    this.n = n;
  }

  public boolean[] findArticulationPoints() {

    id = 0;
    low = new int[n]; // Low link values
    ids = new int[n]; // Nodes ids
    visited = new boolean[n];
    isArticulationPoint = new boolean[n];

    dfs(ROOT, -1);
    isArticulationPoint[ROOT] = (rootNodeChildren > 1);

    return isArticulationPoint;
  }

  private void dfs(int at, int parent) {

    if (parent == ROOT) rootNodeChildren++;

    visited[at] = true;
    low[at] = ids[at] = id++;

    List<Integer> edges = graph.get(at);
    for (Integer to : edges) {
      if (to == parent) continue;
      if (!visited[to]) {
        dfs(to, at);
        low[at] = min(low[at], low[to]);
        if (low[to] >= ids[at]) {
          isArticulationPoint[at] = true;
        }
      } else {
        low[at] = min(low[at], ids[to]);
      }
    }

  }


  public static void main(String[] args) {
    
    int n = 12;
    List<List<Integer>> graph = createGraph(n);

    addEdge(graph, 0, 1);
    addEdge(graph, 0, 2);
    addEdge(graph, 2, 5);
    addEdge(graph, 5, 6);
    addEdge(graph, 5, 11);
    addEdge(graph, 4, 5);
    addEdge(graph, 4, 10);
    addEdge(graph, 4, 3);
    addEdge(graph, 3, 7);
    addEdge(graph, 9, 7);
    addEdge(graph, 8, 7);
    

    ArticulationPointsAdjacencyList solver = new ArticulationPointsAdjacencyList(graph, n);
    boolean[] isArticulationPoint = solver.findArticulationPoints();

    for (int i = 0; i < n; i++) {
      if (isArticulationPoint[i]) {
        System.out.printf("node %d is an articulation\n", i);
      }
    }
  }

  // Initialize graph with 'n' nodes.
  public static List<List<Integer>> createGraph(int n) {
    List<List<Integer>> graph = new ArrayList<>();
    for(int i = 0; i < n; i++) graph.add(new ArrayList<>());
    return graph;
  }

  // Add undirected edge to graph.
  public static void addEdge(List<List<Integer>> graph, int from, int to) {
    graph.get(from).add(to);
    graph.get(to).add(from);
  }

}



















