package com.williamfiset.algorithms.graphtheory;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.williamfiset.algorithms.utils.graphutils.GraphGenerator;
import com.williamfiset.algorithms.utils.graphutils.Utils;
import java.util.*;
import org.junit.jupiter.api.*;

public class KahnsTest {

  private static boolean find(List<List<Integer>> g, boolean[] vis, int at, int target) {
    List<Integer> edges = g.get(at);
    if (edges.size() == 0 || vis[at]) {
      return false;
    }
    if (at == target) {
      return true;
    }
    vis[at] = true;
    for (int next : edges) {
      if (find(g, vis, next, target)) {
        return true;
      }
    }
    return false;
  }

  // Verifies that the topological ordering is not violated, O(n^2)
  private static boolean isTopsortOrdering(List<List<Integer>> g, int[] order) {
    int n = g.size();
    for (int i = 0; i < n; i++) {
      for (int j = i + 1; j < n; j++) {
        // Check that `node j` appears before `node i`
        if (find(g, new boolean[n], order[j], order[i])) {
          return false;
        }
      }
    }
    return true;
  }

  @Test
  public void cycleInGraph() {
    List<List<Integer>> g = Utils.createEmptyAdjacencyList(4);
    Utils.addDirectedEdge(g, 0, 1);
    Utils.addDirectedEdge(g, 1, 2);
    Utils.addDirectedEdge(g, 2, 3);
    Utils.addDirectedEdge(g, 3, 0);
    Kahns solver = new Kahns();
    assertThrows(IllegalArgumentException.class, () -> solver.kahns(g));
  }

  @Test
  public void verifyIsTopsortOrdering() {
    List<List<Integer>> g = Utils.createEmptyAdjacencyList(3);
    Utils.addDirectedEdge(g, 0, 1);
    Utils.addDirectedEdge(g, 1, 2);
    int[] order = {2, 1, 0};
    assertThat(isTopsortOrdering(g, order)).isEqualTo(false);
  }

  @Test
  public void randomTest() {
    GraphGenerator.DagGenerator dagGen = new GraphGenerator.DagGenerator(10, 10, 5, 5, 0.86);
    List<List<Integer>> g = dagGen.createDag();
    Kahns solver = new Kahns();
    int[] order = solver.kahns(g);
    assertThat(isTopsortOrdering(g, order)).isEqualTo(true);
  }

  @Test
  public void randomTests() {
    for (double p = 0.7; p <= 1.0; p += 0.02) {
      GraphGenerator.DagGenerator dagGen = new GraphGenerator.DagGenerator(2, 20, 4, 15, p);
      List<List<Integer>> g = dagGen.createDag();
      Kahns solver = new Kahns();
      int[] order = solver.kahns(g);
      assertThat(isTopsortOrdering(g, order)).isEqualTo(true);
    }
  }
}
