// To run this test in isolation from root folder:
//
// $ gradle test --tests
// com.williamfiset.algorithms.graphtheory.treealgorithms.TreeIsomorphismTest

package com.williamfiset.algorithms.graphtheory.treealgorithms;

import static com.google.common.truth.Truth.assertThat;
import static com.williamfiset.algorithms.graphtheory.treealgorithms.TreeIsomorphism.addUndirectedEdge;
import static com.williamfiset.algorithms.graphtheory.treealgorithms.TreeIsomorphism.createEmptyGraph;
import static com.williamfiset.algorithms.graphtheory.treealgorithms.TreeIsomorphism.treesAreIsomorphic;
import static org.junit.Assert.assertThrows;

import java.util.*;
import org.junit.jupiter.api.*;

public class TreeIsomorphismTest {

  @Test
  public void emptyTreeThrowsException() {
    assertThrows(
        IllegalArgumentException.class,
        () -> treesAreIsomorphic(createEmptyGraph(0), createEmptyGraph(1)));
  }

  @Test
  public void singletonTreesAreIsomorphic() {
    assertThat(treesAreIsomorphic(createEmptyGraph(1), createEmptyGraph(1))).isEqualTo(true);
  }

  @Test
  public void testTwoNodeTree() {
    List<List<Integer>> tree1 = createEmptyGraph(2);
    List<List<Integer>> tree2 = createEmptyGraph(2);
    addUndirectedEdge(tree1, 0, 1);
    addUndirectedEdge(tree2, 1, 0);
    assertThat(treesAreIsomorphic(tree1, tree2)).isEqualTo(true);
  }

  @Test
  public void testSmall() {
    List<List<Integer>> tree1 = createEmptyGraph(5);
    List<List<Integer>> tree2 = createEmptyGraph(5);

    addUndirectedEdge(tree1, 2, 0);
    addUndirectedEdge(tree1, 2, 1);
    addUndirectedEdge(tree1, 2, 3);
    addUndirectedEdge(tree1, 3, 4);

    addUndirectedEdge(tree2, 1, 3);
    addUndirectedEdge(tree2, 1, 0);
    addUndirectedEdge(tree2, 1, 2);
    addUndirectedEdge(tree2, 2, 4);

    assertThat(treesAreIsomorphic(tree1, tree2)).isEqualTo(true);
  }

  @Test
  public void testSimilarChains() {
    // Trees 1 and 3 are equal
    int n = 10;
    List<List<Integer>> tree1 = createEmptyGraph(n);
    List<List<Integer>> tree2 = createEmptyGraph(n);
    List<List<Integer>> tree3 = createEmptyGraph(n);

    addUndirectedEdge(tree1, 0, 1);
    addUndirectedEdge(tree1, 1, 3);
    addUndirectedEdge(tree1, 3, 5);
    addUndirectedEdge(tree1, 5, 7);
    addUndirectedEdge(tree1, 7, 8);
    addUndirectedEdge(tree1, 8, 9);
    addUndirectedEdge(tree1, 2, 1);
    addUndirectedEdge(tree1, 4, 3);
    addUndirectedEdge(tree1, 6, 5);

    addUndirectedEdge(tree2, 0, 1);
    addUndirectedEdge(tree2, 1, 3);
    addUndirectedEdge(tree2, 3, 5);
    addUndirectedEdge(tree2, 5, 6);
    addUndirectedEdge(tree2, 6, 8);
    addUndirectedEdge(tree2, 8, 9);
    addUndirectedEdge(tree2, 6, 7);
    addUndirectedEdge(tree2, 4, 3);
    addUndirectedEdge(tree2, 2, 1);

    addUndirectedEdge(tree3, 0, 1);
    addUndirectedEdge(tree3, 1, 8);
    addUndirectedEdge(tree3, 1, 6);
    addUndirectedEdge(tree3, 6, 4);
    addUndirectedEdge(tree3, 6, 5);
    addUndirectedEdge(tree3, 5, 3);
    addUndirectedEdge(tree3, 5, 7);
    addUndirectedEdge(tree3, 7, 2);
    addUndirectedEdge(tree3, 2, 9);

    assertThat(treesAreIsomorphic(tree1, tree2)).isEqualTo(false);
    assertThat(treesAreIsomorphic(tree1, tree3)).isEqualTo(true);
    assertThat(treesAreIsomorphic(tree2, tree3)).isEqualTo(false);
  }

  @Test
  public void simpleTest() {
    List<List<Integer>> tree1 = createEmptyGraph(5);
    List<List<Integer>> tree2 = createEmptyGraph(5);

    addUndirectedEdge(tree1, 2, 0);
    addUndirectedEdge(tree1, 3, 4);
    addUndirectedEdge(tree1, 2, 1);
    addUndirectedEdge(tree1, 2, 3);

    addUndirectedEdge(tree2, 1, 0);
    addUndirectedEdge(tree2, 2, 4);
    addUndirectedEdge(tree2, 1, 3);
    addUndirectedEdge(tree2, 1, 2);

    assertThat(treesAreIsomorphic(tree1, tree2)).isEqualTo(true);
  }

  @Test
  public void differentNumberOfNodes() {
    List<List<Integer>> tree1 = createEmptyGraph(2);
    List<List<Integer>> tree2 = createEmptyGraph(3);

    addUndirectedEdge(tree1, 0, 1);

    addUndirectedEdge(tree2, 0, 1);
    addUndirectedEdge(tree2, 1, 2);

    assertThat(treesAreIsomorphic(tree1, tree2)).isEqualTo(false);
  }

  @Test
  public void testIsomorphismEquivilanceAgainstOtherImpl() {
    for (int n = 1; n < 50; n++) {
      for (int loops = 0; loops < 1000; loops++) {
        List<List<Integer>> tree1 = generateRandomTree(n);
        List<List<Integer>> tree2 = generateRandomTree(n);

        boolean impl1 = treesAreIsomorphic(tree1, tree2);
        boolean impl2 =
            com.williamfiset.algorithms.graphtheory.treealgorithms.TreeIsomorphismWithBfs
                .treesAreIsomorphic(tree1, tree2);
        if (impl1 != impl2) {
          System.err.println("TreeIsomorphism algorithms disagree!");
          System.err.println(tree1);
          System.err.println(tree2);
        }
        assertThat(impl1).isEqualTo(impl2);
      }
    }
  }

  public static List<List<Integer>> generateRandomTree(int n) {
    List<Integer> nodes = new ArrayList<>();
    nodes.add(0);

    List<List<Integer>> g = createEmptyGraph(n);
    for (int nextNode = 1; nodes.size() != n; nextNode++) {
      int randomNode = nodes.get((int) (Math.random() * nodes.size()));
      addUndirectedEdge(g, randomNode, nextNode);
      nodes.add(nextNode);
    }
    return g;
  }
}
