package com.williamfiset.algorithms.graphtheory;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.williamfiset.algorithms.graphtheory.Boruvkas.Edge;
import java.util.*;
import org.junit.jupiter.api.*;

public class BoruvkasTest {

  @Test
  public void testNullGraphThrowsException() {
    assertThrows(IllegalArgumentException.class, () -> new Boruvkas(5, null));
  }

  @Test
  public void testSingleNode() {
    Edge[] graph = new Edge[0];
    Boruvkas solver = new Boruvkas(1, graph);
    assertThat(solver.getMstCost()).isEqualTo(0L);
    assertThat(solver.getMst()).isEmpty();
  }

  @Test
  public void testTwoNodesConnected() {
    Edge[] graph = new Edge[] {new Edge(0, 1, 5)};
    Boruvkas solver = new Boruvkas(2, graph);
    assertThat(solver.getMstCost()).isEqualTo(5L);
    assertThat(solver.getMst()).hasSize(1);
  }

  @Test
  public void testTwoNodesDisconnected() {
    Edge[] graph = new Edge[0];
    Boruvkas solver = new Boruvkas(2, graph);
    assertThat(solver.getMstCost()).isNull();
    assertThat(solver.getMst()).isNull();
  }

  @Test
  public void testSimpleTriangle() {
    Edge[] graph =
        new Edge[] {new Edge(0, 1, 1), new Edge(1, 2, 2), new Edge(0, 2, 3)};
    Boruvkas solver = new Boruvkas(3, graph);
    assertThat(solver.getMstCost()).isEqualTo(3L);
    assertThat(solver.getMst()).hasSize(2);
  }

  @Test
  public void testDisconnectedGraph() {
    // Two separate components: {0,1} and {2,3}
    Edge[] graph = new Edge[] {new Edge(0, 1, 1), new Edge(2, 3, 2)};
    Boruvkas solver = new Boruvkas(4, graph);
    assertThat(solver.getMstCost()).isNull();
    assertThat(solver.getMst()).isNull();
  }

  @Test
  public void testExampleFromMainMethod() {
    int n = 10, m = 18, i = 0;
    Edge[] g = new Edge[m];

    g[i++] = new Edge(0, 1, 5);
    g[i++] = new Edge(0, 3, 4);
    g[i++] = new Edge(0, 4, 1);
    g[i++] = new Edge(1, 2, 4);
    g[i++] = new Edge(1, 3, 2);
    g[i++] = new Edge(2, 7, 4);
    g[i++] = new Edge(2, 8, 1);
    g[i++] = new Edge(2, 9, 2);
    g[i++] = new Edge(3, 6, 11);
    g[i++] = new Edge(3, 7, 2);
    g[i++] = new Edge(4, 3, 2);
    g[i++] = new Edge(4, 5, 1);
    g[i++] = new Edge(5, 3, 5);
    g[i++] = new Edge(5, 6, 7);
    g[i++] = new Edge(6, 7, 1);
    g[i++] = new Edge(6, 8, 4);
    g[i++] = new Edge(7, 8, 6);
    g[i++] = new Edge(9, 8, 0);

    Boruvkas solver = new Boruvkas(n, g);

    assertThat(solver.getMstCost()).isEqualTo(14L);
    assertThat(solver.getMst()).hasSize(n - 1);
  }

  @Test
  public void testLinearGraph() {
    // 0 -- 1 -- 2 -- 3 -- 4
    Edge[] graph =
        new Edge[] {
          new Edge(0, 1, 1), new Edge(1, 2, 2), new Edge(2, 3, 3), new Edge(3, 4, 4)
        };
    Boruvkas solver = new Boruvkas(5, graph);
    assertThat(solver.getMstCost()).isEqualTo(10L);
    assertThat(solver.getMst()).hasSize(4);
  }

  @Test
  public void testCompleteGraphK4() {
    // Complete graph with 4 nodes
    Edge[] graph =
        new Edge[] {
          new Edge(0, 1, 1),
          new Edge(0, 2, 4),
          new Edge(0, 3, 3),
          new Edge(1, 2, 2),
          new Edge(1, 3, 5),
          new Edge(2, 3, 6)
        };
    Boruvkas solver = new Boruvkas(4, graph);
    // MST should be: 0-1 (1), 1-2 (2), 0-3 (3) = 6
    assertThat(solver.getMstCost()).isEqualTo(6L);
    assertThat(solver.getMst()).hasSize(3);
  }

  @Test
  public void testGraphWithZeroWeightEdges() {
    Edge[] graph =
        new Edge[] {new Edge(0, 1, 0), new Edge(1, 2, 0), new Edge(2, 3, 0)};
    Boruvkas solver = new Boruvkas(4, graph);
    assertThat(solver.getMstCost()).isEqualTo(0L);
    assertThat(solver.getMst()).hasSize(3);
  }

  @Test
  public void testGraphWithNegativeWeightEdges() {
    Edge[] graph =
        new Edge[] {new Edge(0, 1, -5), new Edge(1, 2, -3), new Edge(0, 2, 10)};
    Boruvkas solver = new Boruvkas(3, graph);
    assertThat(solver.getMstCost()).isEqualTo(-8L);
    assertThat(solver.getMst()).hasSize(2);
  }

  @Test
  public void testGraphWithEqualWeightEdges() {
    // All edges have the same weight
    Edge[] graph =
        new Edge[] {
          new Edge(0, 1, 5),
          new Edge(1, 2, 5),
          new Edge(2, 3, 5),
          new Edge(3, 0, 5),
          new Edge(0, 2, 5)
        };
    Boruvkas solver = new Boruvkas(4, graph);
    assertThat(solver.getMstCost()).isEqualTo(15L);
    assertThat(solver.getMst()).hasSize(3);
  }

  @Test
  public void testStarGraph() {
    // Node 0 is connected to all other nodes
    Edge[] graph =
        new Edge[] {
          new Edge(0, 1, 1), new Edge(0, 2, 2), new Edge(0, 3, 3), new Edge(0, 4, 4)
        };
    Boruvkas solver = new Boruvkas(5, graph);
    assertThat(solver.getMstCost()).isEqualTo(10L);
    assertThat(solver.getMst()).hasSize(4);
  }

  @Test
  public void testMstIsIdempotent() {
    Edge[] graph =
        new Edge[] {new Edge(0, 1, 1), new Edge(1, 2, 2), new Edge(0, 2, 3)};
    Boruvkas solver = new Boruvkas(3, graph);

    // Call multiple times to verify idempotency
    Long cost1 = solver.getMstCost();
    Long cost2 = solver.getMstCost();
    List<Edge> mst1 = solver.getMst();
    List<Edge> mst2 = solver.getMst();

    assertThat(cost1).isEqualTo(cost2);
    assertThat(mst1).isEqualTo(mst2);
  }

  @Test
  public void testLargerGraph() {
    // A more complex graph to ensure algorithm works on larger inputs
    Edge[] graph =
        new Edge[] {
          new Edge(0, 1, 4),
          new Edge(0, 7, 8),
          new Edge(1, 2, 8),
          new Edge(1, 7, 11),
          new Edge(2, 3, 7),
          new Edge(2, 5, 4),
          new Edge(2, 8, 2),
          new Edge(3, 4, 9),
          new Edge(3, 5, 14),
          new Edge(4, 5, 10),
          new Edge(5, 6, 2),
          new Edge(6, 7, 1),
          new Edge(6, 8, 6),
          new Edge(7, 8, 7)
        };
    Boruvkas solver = new Boruvkas(9, graph);
    // Known MST cost for this classic graph
    assertThat(solver.getMstCost()).isEqualTo(37L);
    assertThat(solver.getMst()).hasSize(8);
  }

  @Test
  public void testMstEdgesFormSpanningTree() {
    Edge[] graph =
        new Edge[] {
          new Edge(0, 1, 1),
          new Edge(1, 2, 2),
          new Edge(2, 3, 3),
          new Edge(3, 0, 4),
          new Edge(0, 2, 5)
        };
    Boruvkas solver = new Boruvkas(4, graph);
    List<Edge> mst = solver.getMst();

    assertThat(mst).isNotNull();
    assertThat(mst).hasSize(3);

    // Verify MST connects all nodes (using simple connectivity check)
    Set<Integer> connected = new HashSet<>();
    connected.add(mst.get(0).u);
    connected.add(mst.get(0).v);

    boolean changed = true;
    while (changed) {
      changed = false;
      for (Edge e : mst) {
        if (connected.contains(e.u) && !connected.contains(e.v)) {
          connected.add(e.v);
          changed = true;
        } else if (connected.contains(e.v) && !connected.contains(e.u)) {
          connected.add(e.u);
          changed = true;
        }
      }
    }
    assertThat(connected).hasSize(4);
  }

  @Test
  public void testParallelEdges() {
    // Multiple edges between same nodes
    Edge[] graph =
        new Edge[] {
          new Edge(0, 1, 5),
          new Edge(0, 1, 3),
          new Edge(0, 1, 7),
          new Edge(1, 2, 2)
        };
    Boruvkas solver = new Boruvkas(3, graph);
    // Should pick the minimum weight edge between 0 and 1
    assertThat(solver.getMstCost()).isEqualTo(5L);
    assertThat(solver.getMst()).hasSize(2);
  }
}
