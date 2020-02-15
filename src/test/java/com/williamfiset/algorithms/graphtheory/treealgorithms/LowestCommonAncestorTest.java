package com.williamfiset.algorithms.graphtheory.treealgorithms;

import static com.google.common.truth.Truth.assertThat;
import static com.williamfiset.algorithms.graphtheory.treealgorithms.LowestCommonAncestor.addUndirectedEdge;
import static com.williamfiset.algorithms.graphtheory.treealgorithms.LowestCommonAncestor.createEmptyGraph;

import com.williamfiset.algorithms.graphtheory.treealgorithms.LowestCommonAncestor.TreeNode;
import java.util.*;
import org.junit.*;

public class LowestCommonAncestorTest {

  private LowestCommonAncestor solver;

  @Before
  public void setup() {
    solver = new LowestCommonAncestor();
  }

  private TreeNode createFirstTreeFromSlides() {
    int n = 17;
    List<List<Integer>> tree = createEmptyGraph(n);

    addUndirectedEdge(tree, 0, 1);
    addUndirectedEdge(tree, 0, 2);
    addUndirectedEdge(tree, 1, 3);
    addUndirectedEdge(tree, 1, 4);
    addUndirectedEdge(tree, 2, 5);
    addUndirectedEdge(tree, 2, 6);
    addUndirectedEdge(tree, 2, 7);
    addUndirectedEdge(tree, 3, 8);
    addUndirectedEdge(tree, 3, 9);
    addUndirectedEdge(tree, 5, 10);
    addUndirectedEdge(tree, 5, 11);
    addUndirectedEdge(tree, 7, 12);
    addUndirectedEdge(tree, 7, 13);
    addUndirectedEdge(tree, 11, 14);
    addUndirectedEdge(tree, 11, 15);
    addUndirectedEdge(tree, 11, 16);

    return LowestCommonAncestor.rootTree(tree, 0);
  }

  @Test
  public void testLcaTreeFromSlides1() {
    assertThat(solver).isNotNull();
    TreeNode root = createFirstTreeFromSlides();
    assertThat(solver.lca(root, 14, 13).id()).isEqualTo(2);
    assertThat(solver.lca(root, 10, 16).id()).isEqualTo(5);
    assertThat(solver.lca(root, 9, 11).id()).isEqualTo(0);
  }

  @Test
  public void testLcaTreeFromSlides2() {
    TreeNode root = createFirstTreeFromSlides();
    assertThat(solver.lca(root, 8, 9).id()).isEqualTo(3);
    assertThat(solver.lca(root, 4, 8).id()).isEqualTo(1);
    assertThat(solver.lca(root, 6, 13).id()).isEqualTo(2);
    assertThat(solver.lca(root, 7, 13).id()).isEqualTo(7);
    assertThat(solver.lca(root, 10, 5).id()).isEqualTo(5);
    assertThat(solver.lca(root, 2, 16).id()).isEqualTo(2);
  }

  @Test
  public void testLcaOfTheSameNodeIsItself() {
    int n = 17;
    TreeNode root = createFirstTreeFromSlides();
    // Try all nodes
    for (int id = 0; id < n; id++) {
      assertThat(solver.lca(root, id, id).id()).isEqualTo(id);
    }
  }
}
