// To run this test in isolation from root folder:
//
// $ gradle test --tests
// com.williamfiset.algorithms.graphtheory.treealgorithms.TreeCenterLongestPathImplTest

package com.williamfiset.algorithms.graphtheory.treealgorithms;

import static com.google.common.truth.Truth.assertThat;
import static com.williamfiset.algorithms.graphtheory.treealgorithms.TreeCenterLongestPathImpl.addUndirectedEdge;
import static com.williamfiset.algorithms.graphtheory.treealgorithms.TreeCenterLongestPathImpl.createEmptyTree;
import static com.williamfiset.algorithms.graphtheory.treealgorithms.TreeCenterLongestPathImpl.findTreeCenters;

import java.util.*;
import org.junit.*;

public class TreeCenterLongestPathImplTest {

  @Test
  public void simpleTest1() {
    List<List<Integer>> graph = createEmptyTree(9);
    addUndirectedEdge(graph, 0, 1);
    addUndirectedEdge(graph, 2, 1);
    addUndirectedEdge(graph, 2, 3);
    addUndirectedEdge(graph, 3, 4);
    addUndirectedEdge(graph, 5, 3);
    addUndirectedEdge(graph, 2, 6);
    addUndirectedEdge(graph, 6, 7);
    addUndirectedEdge(graph, 6, 8);
    assertThat(findTreeCenters(graph)).containsExactly(2);
  }

  @Test
  public void singleton() {
    assertThat(findTreeCenters(createEmptyTree(1))).containsExactly(0);
  }

  @Test
  public void twoNodeTree() {
    List<List<Integer>> graph = createEmptyTree(2);
    addUndirectedEdge(graph, 0, 1);
    assertThat(findTreeCenters(graph)).containsExactly(0, 1);
  }

  @Test
  public void simpleTest2() {
    List<List<Integer>> graph = createEmptyTree(3);
    addUndirectedEdge(graph, 0, 1);
    addUndirectedEdge(graph, 1, 2);
    assertThat(findTreeCenters(graph)).containsExactly(1);
  }

  @Test
  public void simpleTest3() {
    List<List<Integer>> graph = createEmptyTree(4);
    addUndirectedEdge(graph, 0, 1);
    addUndirectedEdge(graph, 1, 2);
    addUndirectedEdge(graph, 2, 3);
    assertThat(findTreeCenters(graph)).containsExactly(1, 2);
  }

  @Test
  public void simpleTest4() {
    List<List<Integer>> graph = createEmptyTree(7);
    addUndirectedEdge(graph, 0, 1);
    addUndirectedEdge(graph, 1, 2);
    addUndirectedEdge(graph, 2, 3);
    addUndirectedEdge(graph, 3, 4);
    addUndirectedEdge(graph, 4, 5);
    addUndirectedEdge(graph, 4, 6);
    assertThat(findTreeCenters(graph)).containsExactly(2, 3);
  }
}
