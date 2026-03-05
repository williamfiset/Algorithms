package com.williamfiset.algorithms.graphtheory;

import static com.google.common.truth.Truth.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

public class BellmanFordAdjacencyListTest {

  // -------------------------------------------------------------------------
  // Unreachable node relaxation
  // -------------------------------------------------------------------------

  /**
   * An unreachable intermediate node must not corrupt the distance of a node
   * that IS reachable via a separate path.
   *
   * Graph (start = 0):
   *   0 --5--> 2
   *   1 --(-100)--> 2    (node 1 is unreachable from 0)
   *
   * The edge 1→2 has a cheaper cost, but because dist[1] = +Inf the
   * relaxation dist[1] + (-100) = +Inf must not update dist[2].
   * Expected: dist[2] = 5, not -100 or NaN.
   */
  @Test
  public void unreachableNodeDoesNotPolluteCostOfReachableNeighbor() {
    int V = 4;
    List<BellmanFordAdjacencyList.Edge>[] g = BellmanFordAdjacencyList.createGraph(V);
    BellmanFordAdjacencyList.addEdge(g, 0, 2, 5);
    BellmanFordAdjacencyList.addEdge(g, 1, 2, -100); // 1 unreachable

    double[] dist = BellmanFordAdjacencyList.bellmanFord(g, V, 0);

    assertThat(dist[0]).isEqualTo(0.0);
    assertThat(dist[1]).isPositiveInfinity();
    assertThat(dist[2]).isEqualTo(5.0);
    assertThat(dist[3]).isPositiveInfinity();
  }

  /**
   * A node reachable only through an unreachable intermediary must itself
   * remain unreachable (+Inf).
   *
   * Graph (start = 0):
   *   1 --5--> 2    (node 1 is unreachable from 0)
   *
   * Expected: dist[1] = +Inf, dist[2] = +Inf.
   */
  @Test
  public void nodeReachableOnlyThroughUnreachableIntermediaryStaysUnreachable() {
    int V = 3;
    List<BellmanFordAdjacencyList.Edge>[] g = BellmanFordAdjacencyList.createGraph(V);
    BellmanFordAdjacencyList.addEdge(g, 1, 2, 5); // 1 unreachable

    double[] dist = BellmanFordAdjacencyList.bellmanFord(g, V, 0);

    assertThat(dist[0]).isEqualTo(0.0);
    assertThat(dist[1]).isPositiveInfinity();
    assertThat(dist[2]).isPositiveInfinity();
  }

  /**
   * An unreachable node involved in a negative cycle must not mark reachable
   * nodes as -Inf during the cycle-detection pass.
   *
   * Graph (start = 0):
   *   0 --10--> 3
   *   1 --(-1)--> 2    (negative cycle: 1→2→1, but both unreachable)
   *   2 --(-1)--> 1
   *   2 --5--> 3
   *
   * Nodes 1 and 2 form a negative cycle but are unreachable from 0.
   * Node 3 is reachable with cost 10 and must NOT be marked -Inf.
   */
  @Test
  public void unreachableNegativeCycleDoesNotTaintReachableNode() {
    int V = 4;
    List<BellmanFordAdjacencyList.Edge>[] g = BellmanFordAdjacencyList.createGraph(V);
    BellmanFordAdjacencyList.addEdge(g, 0, 3, 10);
    BellmanFordAdjacencyList.addEdge(g, 1, 2, -1); // unreachable negative cycle
    BellmanFordAdjacencyList.addEdge(g, 2, 1, -1);
    BellmanFordAdjacencyList.addEdge(g, 2, 3, 5);  // path from cycle to node 3

    double[] dist = BellmanFordAdjacencyList.bellmanFord(g, V, 0);

    assertThat(dist[0]).isEqualTo(0.0);
    assertThat(dist[1]).isPositiveInfinity();
    assertThat(dist[2]).isPositiveInfinity();
    assertThat(dist[3]).isEqualTo(10.0); // must NOT be -Inf
  }

  // -------------------------------------------------------------------------
  // General cases
  // -------------------------------------------------------------------------

  @Test
  public void singleNode() {
    int V = 1;
    List<BellmanFordAdjacencyList.Edge>[] g = BellmanFordAdjacencyList.createGraph(V);

    double[] dist = BellmanFordAdjacencyList.bellmanFord(g, V, 0);

    assertThat(dist[0]).isEqualTo(0.0);
  }

  @Test
  public void twoNodesDirectEdge() {
    int V = 2;
    List<BellmanFordAdjacencyList.Edge>[] g = BellmanFordAdjacencyList.createGraph(V);
    BellmanFordAdjacencyList.addEdge(g, 0, 1, 7);

    double[] dist = BellmanFordAdjacencyList.bellmanFord(g, V, 0);

    assertThat(dist[0]).isEqualTo(0.0);
    assertThat(dist[1]).isEqualTo(7.0);
  }

  @Test
  public void shortestPathChosenOverLonger() {
    // Two paths from 0 to 2: 0→2 (cost 10) and 0→1→2 (cost 3+4=7)
    int V = 3;
    List<BellmanFordAdjacencyList.Edge>[] g = BellmanFordAdjacencyList.createGraph(V);
    BellmanFordAdjacencyList.addEdge(g, 0, 2, 10);
    BellmanFordAdjacencyList.addEdge(g, 0, 1, 3);
    BellmanFordAdjacencyList.addEdge(g, 1, 2, 4);

    double[] dist = BellmanFordAdjacencyList.bellmanFord(g, V, 0);

    assertThat(dist[2]).isEqualTo(7.0);
  }

  @Test
  public void negativeEdgeWeightWithoutCycle() {
    // 0 --1--> 1 --(-2)--> 2; shortest path to 2 is -1
    int V = 3;
    List<BellmanFordAdjacencyList.Edge>[] g = BellmanFordAdjacencyList.createGraph(V);
    BellmanFordAdjacencyList.addEdge(g, 0, 1, 1);
    BellmanFordAdjacencyList.addEdge(g, 1, 2, -2);

    double[] dist = BellmanFordAdjacencyList.bellmanFord(g, V, 0);

    assertThat(dist[0]).isEqualTo(0.0);
    assertThat(dist[1]).isEqualTo(1.0);
    assertThat(dist[2]).isEqualTo(-1.0);
  }

  @Test
  public void reachableNegativeCycleMarkedNegativeInfinity() {
    // 0 --1--> 1 --1--> 2 --(-3)--> 1 (negative cycle: 1→2→1)
    int V = 3;
    List<BellmanFordAdjacencyList.Edge>[] g = BellmanFordAdjacencyList.createGraph(V);
    BellmanFordAdjacencyList.addEdge(g, 0, 1, 1);
    BellmanFordAdjacencyList.addEdge(g, 1, 2, 1);
    BellmanFordAdjacencyList.addEdge(g, 2, 1, -3);

    double[] dist = BellmanFordAdjacencyList.bellmanFord(g, V, 0);

    assertThat(dist[0]).isEqualTo(0.0);
    assertThat(dist[1]).isNegativeInfinity();
    assertThat(dist[2]).isNegativeInfinity();
  }

  @Test
  public void nodeDownstreamOfNegativeCycleMarkedNegativeInfinity() {
    // Negative cycle 1→2→1, with 2→3 leading out of the cycle.
    // Node 3 is downstream and must also be -Inf.
    int V = 4;
    List<BellmanFordAdjacencyList.Edge>[] g = BellmanFordAdjacencyList.createGraph(V);
    BellmanFordAdjacencyList.addEdge(g, 0, 1, 1);
    BellmanFordAdjacencyList.addEdge(g, 1, 2, 1);
    BellmanFordAdjacencyList.addEdge(g, 2, 1, -3);
    BellmanFordAdjacencyList.addEdge(g, 2, 3, 5);

    double[] dist = BellmanFordAdjacencyList.bellmanFord(g, V, 0);

    assertThat(dist[3]).isNegativeInfinity();
  }

  @Test
  public void disconnectedGraph() {
    // Nodes 2 and 3 have no path from node 0.
    int V = 4;
    List<BellmanFordAdjacencyList.Edge>[] g = BellmanFordAdjacencyList.createGraph(V);
    BellmanFordAdjacencyList.addEdge(g, 0, 1, 3);
    BellmanFordAdjacencyList.addEdge(g, 2, 3, 1); // separate component

    double[] dist = BellmanFordAdjacencyList.bellmanFord(g, V, 0);

    assertThat(dist[0]).isEqualTo(0.0);
    assertThat(dist[1]).isEqualTo(3.0);
    assertThat(dist[2]).isPositiveInfinity();
    assertThat(dist[3]).isPositiveInfinity();
  }

  @Test
  public void exampleFromMain() {
    // Reproduces the graph and expected output from the main() method.
    int V = 9;
    List<BellmanFordAdjacencyList.Edge>[] g = BellmanFordAdjacencyList.createGraph(V);
    BellmanFordAdjacencyList.addEdge(g, 0, 1, 1);
    BellmanFordAdjacencyList.addEdge(g, 1, 2, 1);
    BellmanFordAdjacencyList.addEdge(g, 2, 4, 1);
    BellmanFordAdjacencyList.addEdge(g, 4, 3, -3);
    BellmanFordAdjacencyList.addEdge(g, 3, 2, 1);
    BellmanFordAdjacencyList.addEdge(g, 1, 5, 4);
    BellmanFordAdjacencyList.addEdge(g, 1, 6, 4);
    BellmanFordAdjacencyList.addEdge(g, 5, 6, 5);
    BellmanFordAdjacencyList.addEdge(g, 6, 7, 4);
    BellmanFordAdjacencyList.addEdge(g, 5, 7, 3);

    double[] dist = BellmanFordAdjacencyList.bellmanFord(g, V, 0);

    assertThat(dist[0]).isEqualTo(0.0);
    assertThat(dist[1]).isEqualTo(1.0);
    assertThat(dist[2]).isNegativeInfinity();
    assertThat(dist[3]).isNegativeInfinity();
    assertThat(dist[4]).isNegativeInfinity();
    assertThat(dist[5]).isEqualTo(5.0);
    assertThat(dist[6]).isEqualTo(5.0);
    assertThat(dist[7]).isEqualTo(8.0);
    assertThat(dist[8]).isPositiveInfinity();
  }
}
