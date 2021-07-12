/**
 * This file is still a WIP
 *
 * <p>Still need to: - Implemented undirected edge eulerain path algo
 *
 * <p>./gradlew run -Palgorithm=graphtheory.ChinesePostmanProblem
 */
package com.williamfiset.algorithms.graphtheory;

import java.util.*;

public class ChinesePostmanProblem {

  // An edge class to represent a directed edge
  // between two nodes with a certain cost.
  public static class Edge {
    double cost;
    int from, to;

    public Edge(int from, int to, double cost) {
      this.from = from;
      this.to = to;
      this.cost = cost;
    }

    @Override
    public String toString() {
      return "(" + from + ", " + to + ", " + cost + ")";
    }
  }

  private int n;
  private int[] inDegree;

  public ChinesePostmanProblem(List<List<Edge>> g) {
    this.n = g.size();
    // TODO(william): Make a copy of this graph.

    // TODO(william): check if graph has an Eulerian path

    inDegree = new int[n];

    for (List<Edge> edges : g) {
      for (Edge edge : edges) {
        inDegree[edge.from]++;
      }
    }

    Map<Integer, Integer> mapping = new HashMap<>();
    Map<Integer, Integer> invMapping = new HashMap<>();

    int id = 0;
    int oddDegreeNodeCount = 0;
    for (int i = 0; i < n; i++) {
      if (inDegree[i] % 2 != 0) { // odd degree node
        oddDegreeNodeCount++;

        System.out.printf("%d -> %d\n", i, id);

        // node `i` maps to node with `id` in the sub graph
        mapping.put(i, id);
        invMapping.put(id, i);

        id++;
      }
    }
    System.out.println(mapping);
    // Odd node cost matrix.
    Double[][] matrix = new Double[oddDegreeNodeCount][oddDegreeNodeCount];

    for (int i = 0; i < n; i++) {

      // Skip even degree nodes
      if (inDegree[i] % 2 == 0) {
        continue;
      }

      int fromNodeId = mapping.get(i);
      for (Edge edge : g.get(i)) {
        // Skip nodes which are not odd. Only connect odd node to odd node
        if (inDegree[edge.to] % 2 == 0) {
          continue;
        }

        // System.out.printf("edge.to = %d, inDegree[edge.to] = %d\n", edge.to, inDegree[edge.to]);
        int toNodeId = mapping.get(edge.to);
        // System.out.printf("%d -> %d | %d -> %d\n", edge.from, edge.to, fromNodeId, toNodeId);
        matrix[fromNodeId][toNodeId] = edge.cost;
      }
    }

    WeightedMaximumCardinalityMatchingRecursive wmcm =
        new WeightedMaximumCardinalityMatchingRecursive(matrix);
    int[] matching = wmcm.getMatching();
    for (int i = 0; i < matching.length / 2; i++) {
      int node1 = matching[2 * i];
      int node2 = matching[2 * i + 1];

      if (matrix[node1][node2] != null) { // edge exists
        // Add a new edge in the original graph with the same value as this edge

        // Map node1 and node2 back to the original graph nodes
        int from = invMapping.get(node1);
        int to = invMapping.get(node2);

        // Seek time can be made into a lookup, but these graphs are generally quite small.
        Edge edge = findEdge(g, from, to);
        System.out.printf("%d -> %d | %d -> %d | cost = %f\n", node1, node2, from, to, edge.cost);

        Edge e1 = new Edge(from, to, edge.cost);
        Edge e2 = new Edge(to, from, edge.cost);

        // // Augment existing graph with new edges
        g.get(from).add(e1);
        g.get(to).add(e2);
      }
    }

    // Print augmented graph
    for (int i = 0; i < n; i++) {
      System.out.printf("%d -> [", i);
      for (Edge e : g.get(i)) {
        System.out.print(e + ", ");
      }
      System.out.print("]\n");
    }

    EulerianPathDirectedEdgesAdjacencyList eulerPathSolver =
        new EulerianPathDirectedEdgesAdjacencyList(g);
    List<Edge> cppTour = eulerPathSolver.getEulerianPath();

    double tourTotal = 0;
    for (Edge edge : cppTour) {
      System.out.printf("%d -> %d with cost: %f\n", edge.from, edge.to, edge.cost);
      tourTotal += edge.cost;
    }
    System.out.println(tourTotal);
    System.out.println(tourTotal / 2.0);
  }

  private static Edge findEdge(List<List<Edge>> g, int to, int from) {
    for (Edge e : g.get(from)) {
      if (e.to == to) {
        return e;
      }
    }
    return null;
  }

  public List<Integer> getTour() {
    return null;
  }

  private static class WeightedMaximumCardinalityMatchingRecursive {

    // A `MatchingCost` object captures the cost of a matching. Because we allow matching nodes
    // which
    // do not have edges between them we define `impossibleEdgeMatches` to track the number of times
    // this happens. When comparing two MatchingCost objects, the one with the fewest number of
    // impossible edges matches is ranked higher and ties broken based on cost, see the
    // `isBetterMatchingCost` method below.
    private static class MatchingCost {
      double cost = 0;
      int impossibleEdgeMatches = 0;

      public MatchingCost() {}

      public MatchingCost(double cost, int iem) {
        this.cost = cost;
        this.impossibleEdgeMatches = iem;
      }

      public MatchingCost(MatchingCost mc) {
        this.cost = mc.cost;
        this.impossibleEdgeMatches = mc.impossibleEdgeMatches;
      }

      public static MatchingCost createInfiniteValueMatchingCost() {
        return new MatchingCost(Double.MAX_VALUE, Integer.MAX_VALUE / 2);
      }

      // Updates the MatchingCost with the value of a particular edge. If the specified edge value
      // is
      // `null`, then the edge doesn't actually exist in the graph.
      public void updateMatchingCost(Double edgeCost) {
        if (edgeCost == null) {
          impossibleEdgeMatches++;
        } else {
          cost += edgeCost;
        }
      }

      // Checks if the current matching cost is better than `mc`
      public boolean isBetterMatchingCost(MatchingCost mc) {
        if (impossibleEdgeMatches < mc.impossibleEdgeMatches) {
          return true;
        }
        if (impossibleEdgeMatches == mc.impossibleEdgeMatches) {
          return cost < mc.cost;
        }
        return false;
      }

      @Override
      public String toString() {
        return cost + " " + impossibleEdgeMatches;
      }
    }

    // Inputs
    private int n;
    private Double[][] cost;

    // Internal
    private final int FULL_STATE;
    private int artificialNodeId = -1;
    private boolean isOdd;
    private boolean solved;

    // Outputs
    private double minWeightCost;
    private int[] matching;

    // The cost matrix should be a symmetric (i.e cost[i][j] = cost[j][i]) and have a cost of `null`
    // between nodes i and j if no edge exists between those two nodes.
    public WeightedMaximumCardinalityMatchingRecursive(Double[][] cost) {
      if (cost == null) throw new IllegalArgumentException("Input cannot be null");
      n = cost.length;
      if (n <= 1) throw new IllegalArgumentException("Invalid matrix size: " + n);
      setCostMatrix(cost);
      FULL_STATE = (1 << n) - 1;
    }

    // Sets the cost matrix. If the number of nodes in the graph is odd, add an artificial
    // node that connects to every other node with a cost of infinity. This will make it easy
    // to find a perfect matching and remove in the artificial node in the end.
    private void setCostMatrix(Double[][] inputMatrix) {
      isOdd = (n % 2 == 0) ? false : true;
      Double[][] newCostMatrix = null;

      if (isOdd) {
        newCostMatrix = new Double[n + 1][n + 1];
      } else {
        newCostMatrix = new Double[n][n];
      }

      for (int i = 0; i < n; ++i) {
        for (int j = 0; j < n; ++j) {
          newCostMatrix[i][j] = inputMatrix[i][j];
        }
      }

      if (isOdd) {
        for (int i = 0; i < n; i++) {
          newCostMatrix[n][i] = null;
          newCostMatrix[i][n] = null;
        }
        newCostMatrix[n][n] = 0.0;
        artificialNodeId = n;
        n++;
      }

      this.cost = newCostMatrix;
    }

    public double getMinWeightCost() {
      solve();
      return minWeightCost;
    }

    /**
     * Get the minimum weight cost matching. The matching is returned as an array where the nodes at
     * index 2*i and 2*i+1 form a matched pair. For example, nodes at indexes (0, 1) are a pair, (2,
     * 3) are another pair, etc...
     *
     * <p>How to iterate over the pairs:
     *
     * <pre>{@code
     * WeightedMaximumCardinalityMatchingRecursive mwpm = ...
     * int[] matching = mwpm.getMatching();
     * for (int i = 0; i < matching.length / 2; i++) {
     *   int node1 = matching[2*i];
     *   int node2 = matching[2*i+1];
     *   // Do something with the matched pair (node1, node2)
     * }
     * }</pre>
     */
    public int[] getMatching() {
      solve();
      return matching;
    }

    private void solve() {
      if (solved) return;
      MatchingCost[] dp = new MatchingCost[1 << n];
      int[] history = new int[1 << n];

      MatchingCost matchingCost = f(FULL_STATE, dp, history);
      minWeightCost = matchingCost.cost;

      reconstructMatching(history);
      solved = true;
    }

    private MatchingCost f(int state, MatchingCost[] dp, int[] history) {
      if (dp[state] != null) {
        return dp[state];
      }
      if (state == 0) {
        return new MatchingCost();
      }
      int p1, p2;
      // Seek to find active bit position (p1)
      for (p1 = 0; p1 < n; p1++) {
        if ((state & (1 << p1)) > 0) {
          break;
        }
      }

      int bestState = -1;
      MatchingCost bestMatchingCost = MatchingCost.createInfiniteValueMatchingCost();

      for (p2 = p1 + 1; p2 < n; p2++) {
        // Position `p2` is on. Try matching the pair (p1, p2) together.
        if ((state & (1 << p2)) > 0) {
          int reducedState = state ^ (1 << p1) ^ (1 << p2);

          MatchingCost matchCost = new MatchingCost(f(reducedState, dp, history));
          matchCost.updateMatchingCost(cost[p1][p2]);

          if (matchCost.isBetterMatchingCost(bestMatchingCost)) {
            bestMatchingCost = matchCost;
            bestState = reducedState;
          }
        }
      }

      history[state] = bestState;
      return dp[state] = bestMatchingCost;
    }

    // Populates the `matching` array with a sorted deterministic matching sorted by lowest node
    // index. For example, if the perfect matching consists of the pairs (3, 4), (1, 5), (0, 2).
    // The matching is sorted such that the pairs appear in the ordering: (0, 2), (1, 5), (3, 4).
    // Furthermore, it is guaranteed that for any pair (a, b) that a < b.
    private void reconstructMatching(int[] history) {
      // A map between pairs of nodes that were matched together.
      int[] map = new int[n];
      int[] leftNodes = new int[n / 2];

      int matchingSize = 0;

      // Reconstruct the matching of pairs of nodes working backwards through computed states.
      for (int i = 0, state = FULL_STATE; state != 0; state = history[state]) {
        // Isolate the pair used by xoring the state with the state used to generate it.
        int pairUsed = state ^ history[state];

        int leftNode = getBitPosition(Integer.lowestOneBit(pairUsed));
        int rightNode = getBitPosition(Integer.highestOneBit(pairUsed));

        leftNodes[i++] = leftNode;
        map[leftNode] = rightNode;

        if (cost[leftNode][rightNode] != null) {
          matchingSize++;
        }
      }

      // Sort the left nodes in ascending order.
      java.util.Arrays.sort(leftNodes);

      matchingSize = matchingSize * 2;
      matching = new int[matchingSize];

      for (int i = 0, j = 0; i < n / 2; i++) {
        int leftNode = leftNodes[i];
        int rightNode = map[leftNodes[i]];
        // Ignore the artificial node when there is an odd number of nodes.
        if (isOdd && (leftNode == artificialNodeId || rightNode == artificialNodeId)) {
          continue;
        }
        // Only match edges which actually exist
        if (cost[leftNode][rightNode] != null) {
          matching[2 * j] = leftNode;
          matching[2 * j + 1] = rightNode;
          j++;
        }
      }
    }

    // Gets the zero base index position of the 1 bit in `k`. `k` must be a power of 2, so there is
    // only ever 1 bit in the binary representation of k.
    private int getBitPosition(int k) {
      int count = -1;
      while (k > 0) {
        count++;
        k >>= 1;
      }
      return count;
    }
  } // WeightedMaximumCardinalityMatchingRecursive

  // TODO(william): implement tests for this modified Eulerian path algo. It should be able to
  // return
  // the correct edges from a multi graph. The other impl in this repo bans multigraphs.
  private static class EulerianPathDirectedEdgesAdjacencyList {

    private final int n;
    private int edgeCount;
    private int[] in, out;
    private LinkedList<Edge> path;
    private List<List<Edge>> graph;

    public EulerianPathDirectedEdgesAdjacencyList(List<List<Edge>> graph) {
      if (graph == null) throw new IllegalArgumentException("Graph cannot be null");
      n = graph.size();
      this.graph = graph;
      path = new LinkedList<>();
    }

    // Returns a list of edgeCount + 1 node ids that give the Eulerian path or
    // null if no path exists or the graph is disconnected.
    public List<Edge> getEulerianPath() {
      setUp();

      if (!graphHasEulerianPath()) {
        System.out.println("Graph has no Eulerian path");
        return null;
      }
      dfs(findStartNode());

      // Instead of returning the 'path' as a linked list return
      // the solution as a primitive array for convenience.
      List<Edge> soln = new ArrayList<>();
      for (int i = 0; !path.isEmpty(); i++) soln.add(path.removeFirst());

      return soln;
    }

    private void setUp() {
      // Arrays that track the in degree and out degree of each node.
      in = new int[n];
      out = new int[n];

      edgeCount = 0;

      // Compute in and out node degrees.
      for (int from = 0; from < n; from++) {
        for (Edge e : graph.get(from)) {
          in[e.to]++;
          out[e.from]++;
          edgeCount++;
        }
      }
    }

    private boolean graphHasEulerianPath() {
      if (edgeCount == 0) return false;
      int startNodes = 0, endNodes = 0;
      for (int i = 0; i < n; i++) {
        if (out[i] - in[i] > 1 || in[i] - out[i] > 1) return false;
        else if (out[i] - in[i] == 1) startNodes++;
        else if (in[i] - out[i] == 1) endNodes++;
      }
      return (endNodes == 0 && startNodes == 0) || (endNodes == 1 && startNodes == 1);
    }

    private int findStartNode() {
      int start = 0;
      for (int i = 0; i < n; i++) {
        // Unique starting node.
        if (out[i] - in[i] == 1) return i;
        // Start at a node with an outgoing edge.
        if (out[i] > 0) start = i;
      }
      return start;
    }

    // Perform DFS to find Eulerian path.
    private void dfs(int at) {
      while (out[at] != 0) {
        Edge nextEdge = graph.get(at).get(--out[at]);
        dfs(nextEdge.to);
        path.addFirst(nextEdge);
      }
    }
  } // EulerianPathDirectedEdgesAdjacencyList

  public static List<List<Edge>> createEmptyGraph(int n) {
    List<List<Edge>> g = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      g.add(new ArrayList<>());
    }
    return g;
  }

  public static void addDirectedEdge(List<List<Edge>> g, int from, int to, double cost) {
    g.get(from).add(new Edge(from, to, cost));
  }

  public static void addUndirectedEdge(List<List<Edge>> g, int from, int to, double cost) {
    addDirectedEdge(g, from, to, cost);
    addDirectedEdge(g, to, from, cost);
  }

  public static void main(String[] args) {
    cppTest1();
    // eulerTest1();
  }

  private static void eulerTest1() {
    int n = 2;
    List<List<Edge>> g = createEmptyGraph(n);
    addDirectedEdge(g, 0, 0, 0);
    addDirectedEdge(g, 0, 1, 1);
    addDirectedEdge(g, 0, 1, 2);
    addDirectedEdge(g, 1, 0, 3);
    addDirectedEdge(g, 1, 0, 4);
    addDirectedEdge(g, 1, 1, 5);
    EulerianPathDirectedEdgesAdjacencyList solver = new EulerianPathDirectedEdgesAdjacencyList(g);
    List<Edge> path = solver.getEulerianPath();
    for (Edge edge : path) {
      System.out.printf("%d -> %d with cost: %f\n", edge.from, edge.to, edge.cost);
    }
  }

  private static void cppTest1() {
    int n = 6;
    List<List<Edge>> g = createEmptyGraph(n);
    addUndirectedEdge(g, 0, 1, 5);
    addUndirectedEdge(g, 0, 2, 3);
    addUndirectedEdge(g, 0, 3, 2);
    addUndirectedEdge(g, 1, 4, 3);
    addUndirectedEdge(g, 1, 5, 6);
    addUndirectedEdge(g, 2, 3, 1);
    addUndirectedEdge(g, 3, 4, 1);
    addUndirectedEdge(g, 4, 5, 8);

    ChinesePostmanProblem cpp = new ChinesePostmanProblem(g);
  }
}
