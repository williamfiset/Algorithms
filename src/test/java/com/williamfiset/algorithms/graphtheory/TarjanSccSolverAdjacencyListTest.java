package com.williamfiset.algorithms.graphtheory;

import static com.google.common.truth.Truth.assertThat;

import com.google.common.collect.ImmutableList;
import java.util.*;
import org.junit.*;

public class TarjanSccSolverAdjacencyListTest {

  // Initialize graph with 'n' nodes.
  public static List<List<Integer>> createGraph(int n) {
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
    return graph;
  }

  // Add directed edge to graph.
  public static void addEdge(List<List<Integer>> graph, int from, int to) {
    graph.get(from).add(to);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullGraphConstructor() {
    new TarjanSccSolverAdjacencyList(null);
  }

  @Test
  public void singletonCase() {
    int n = 1;
    List<List<Integer>> g = createGraph(n);

    TarjanSccSolverAdjacencyList solver = new TarjanSccSolverAdjacencyList(g);
    solver.solve();

    int[] actual = solver.getSccs();
    int[] expected = new int[n];
    assertThat(actual).isEqualTo(expected);
    assertThat(solver.sccCount()).isEqualTo(1);
  }

  @Test
  public void testTwoDisjointComponents() {
    int n = 5;
    List<List<Integer>> g = createGraph(n);

    addEdge(g, 0, 1);
    addEdge(g, 1, 0);

    addEdge(g, 2, 3);
    addEdge(g, 3, 4);
    addEdge(g, 4, 2);

    TarjanSccSolverAdjacencyList solver = new TarjanSccSolverAdjacencyList(g);
    solver.solve();

    List<List<Integer>> expectedSccs =
        ImmutableList.of(ImmutableList.of(0, 1), ImmutableList.of(2, 3, 4));

    assertThat(solver.sccCount()).isEqualTo(expectedSccs.size());
    assertThat(isScc(solver.getSccs(), expectedSccs)).isTrue();
  }

  @Test
  public void testButterflyCase() {
    int n = 5;
    List<List<Integer>> g = createGraph(n);

    addEdge(g, 0, 1);
    addEdge(g, 1, 2);
    addEdge(g, 2, 3);
    addEdge(g, 3, 1);
    addEdge(g, 1, 4);
    addEdge(g, 4, 0);

    TarjanSccSolverAdjacencyList solver = new TarjanSccSolverAdjacencyList(g);
    solver.solve();

    List<List<Integer>> expectedSccs = ImmutableList.of(ImmutableList.of(0, 1, 2, 3, 4));

    assertThat(solver.sccCount()).isEqualTo(expectedSccs.size());
    assertThat(isScc(solver.getSccs(), expectedSccs)).isTrue();
  }

  @Test
  public void testFirstGraphInSlides() {
    int n = 9;
    List<List<Integer>> g = createGraph(n);

    addEdge(g, 0, 1);
    addEdge(g, 1, 0);
    addEdge(g, 0, 8);
    addEdge(g, 8, 0);
    addEdge(g, 8, 7);
    addEdge(g, 7, 6);
    addEdge(g, 6, 7);
    addEdge(g, 1, 7);
    addEdge(g, 2, 1);
    addEdge(g, 2, 6);
    addEdge(g, 5, 6);
    addEdge(g, 2, 5);
    addEdge(g, 5, 3);
    addEdge(g, 3, 2);
    addEdge(g, 4, 3);
    addEdge(g, 4, 5);

    TarjanSccSolverAdjacencyList solver = new TarjanSccSolverAdjacencyList(g);
    solver.solve();

    List<List<Integer>> expectedSccs =
        ImmutableList.of(
            ImmutableList.of(0, 1, 8),
            ImmutableList.of(7, 6),
            ImmutableList.of(2, 3, 5),
            ImmutableList.of(4));

    assertThat(solver.sccCount()).isEqualTo(expectedSccs.size());
    assertThat(isScc(solver.getSccs(), expectedSccs)).isTrue();
  }

  @Test
  public void testLastGraphInSlides() {
    int n = 8;
    List<List<Integer>> g = createGraph(n);

    addEdge(g, 6, 0);
    addEdge(g, 6, 2);
    addEdge(g, 3, 4);
    addEdge(g, 6, 4);
    addEdge(g, 2, 0);
    addEdge(g, 0, 1);
    addEdge(g, 4, 5);
    addEdge(g, 5, 6);
    addEdge(g, 3, 7);
    addEdge(g, 7, 5);
    addEdge(g, 1, 2);
    addEdge(g, 7, 3);
    addEdge(g, 5, 0);

    TarjanSccSolverAdjacencyList solver = new TarjanSccSolverAdjacencyList(g);
    solver.solve();

    List<List<Integer>> expectedSccs =
        ImmutableList.of(
            ImmutableList.of(6, 5, 4), ImmutableList.of(3, 7), ImmutableList.of(0, 2, 1));

    assertThat(solver.sccCount()).isEqualTo(expectedSccs.size());
    assertThat(isScc(solver.getSccs(), expectedSccs)).isTrue();
  }

  private static boolean isScc(int[] ids, List<List<Integer>> expectedSccs) {
    Set<Integer> set = new HashSet<>();
    Set<Integer> sccComponentIds = new HashSet<>();
    for (List<Integer> indexes : expectedSccs) {
      set.clear();
      int componentId = 0;
      for (int index : indexes) {
        componentId = ids[index];
        set.add(componentId);
      }
      if (sccComponentIds.contains(componentId)) return false;
      if (set.size() != 1) return false;
      sccComponentIds.add(componentId);
    }
    return true;
  }
}
