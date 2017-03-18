
import java.util.*;

class Edge {
  int from, to;
  public Edge(int from, int to) {
    this.from = from;
    this.to = to;
  }
}

class Tarjan {

  int n, pre, count = 0;
  boolean[] marked;
  int[] id, low;
  Map < Integer, List<Edge>> graph;
  Stack<Integer> stack = new Stack<Integer>();

  // graph - An adjacency list containing directed edges
  // N     - the number of nodes in the graph
  public Tarjan(Map < Integer, List<Edge>> graph, int N) {

    n = N;
    this.graph = graph;
    marked = new boolean[n];
    id = new int[n];
    low = new int[n];

    for (int from = 0; from < n; from++)
      if (!marked[from])
        dfs(from);

  }

  void dfs(int from) {

    marked[from] = true;
    low[from] = pre++;
    int min = low[from];
    stack.push(from);

    List<Edge> edges = graph.get(from);

    for (Edge edge: edges) {
      if (!marked[edge.to]) dfs(edge.to);
      if (low[edge.to] < min) min = low[edge.to];
    }

    if (min < low[from]) {
      low[from] = min;
      return;
    }
    int to;
    do {
      to = stack.pop();
      id[to] = count;
      low[to] = n;
    } while (to != from);
    count++;
  }

}