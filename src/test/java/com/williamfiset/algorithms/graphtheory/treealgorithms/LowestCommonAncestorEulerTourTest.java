package com.williamfiset.algorithms.graphtheory.treealgorithms;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.*;
import org.junit.jupiter.api.*;

public class LowestCommonAncestorEulerTourTest {

  private LowestCommonAncestorEulerTour.TreeNode createFirstTreeFromSlides() {
    int n = 17;
    List<List<Integer>> tree = LowestCommonAncestorEulerTour.createEmptyGraph(n);

    LowestCommonAncestorEulerTour.addUndirectedEdge(tree, 0, 1);
    LowestCommonAncestorEulerTour.addUndirectedEdge(tree, 0, 2);
    LowestCommonAncestorEulerTour.addUndirectedEdge(tree, 1, 3);
    LowestCommonAncestorEulerTour.addUndirectedEdge(tree, 1, 4);
    LowestCommonAncestorEulerTour.addUndirectedEdge(tree, 2, 5);
    LowestCommonAncestorEulerTour.addUndirectedEdge(tree, 2, 6);
    LowestCommonAncestorEulerTour.addUndirectedEdge(tree, 2, 7);
    LowestCommonAncestorEulerTour.addUndirectedEdge(tree, 3, 8);
    LowestCommonAncestorEulerTour.addUndirectedEdge(tree, 3, 9);
    LowestCommonAncestorEulerTour.addUndirectedEdge(tree, 5, 10);
    LowestCommonAncestorEulerTour.addUndirectedEdge(tree, 5, 11);
    LowestCommonAncestorEulerTour.addUndirectedEdge(tree, 7, 12);
    LowestCommonAncestorEulerTour.addUndirectedEdge(tree, 7, 13);
    LowestCommonAncestorEulerTour.addUndirectedEdge(tree, 11, 14);
    LowestCommonAncestorEulerTour.addUndirectedEdge(tree, 11, 15);
    LowestCommonAncestorEulerTour.addUndirectedEdge(tree, 11, 16);

    return LowestCommonAncestorEulerTour.TreeNode.rootTree(tree, 0);
  }

  @Test
  public void testLcaTreeFromSlides1() {
    LowestCommonAncestorEulerTour.TreeNode root = createFirstTreeFromSlides();
    LowestCommonAncestorEulerTour fastSolver = new LowestCommonAncestorEulerTour(root);
    assertThat(fastSolver.lca(14, 13).index()).isEqualTo(2);
    assertThat(fastSolver.lca(10, 16).index()).isEqualTo(5);
    assertThat(fastSolver.lca(9, 11).index()).isEqualTo(0);
  }

  @Test
  public void testLcaTreeFromSlides2() {
    LowestCommonAncestorEulerTour.TreeNode root = createFirstTreeFromSlides();
    LowestCommonAncestorEulerTour fastSolver = new LowestCommonAncestorEulerTour(root);
    assertThat(fastSolver.lca(8, 9).index()).isEqualTo(3);
    assertThat(fastSolver.lca(4, 8).index()).isEqualTo(1);
    assertThat(fastSolver.lca(6, 13).index()).isEqualTo(2);
    assertThat(fastSolver.lca(7, 13).index()).isEqualTo(7);
    assertThat(fastSolver.lca(10, 5).index()).isEqualTo(5);
    assertThat(fastSolver.lca(2, 16).index()).isEqualTo(2);
  }

  @Test
  public void testLcaOfTheSameNodeIsItself() {
    LowestCommonAncestorEulerTour.TreeNode root = createFirstTreeFromSlides();
    LowestCommonAncestorEulerTour fastSolver = new LowestCommonAncestorEulerTour(root);

    // Try all nodes
    for (int id = 0; id < root.size(); id++) {
      assertThat(fastSolver.lca(id, id).index()).isEqualTo(id);
    }
  }

  @Test
  public void testLcaIsSymmetric() {
    LowestCommonAncestorEulerTour.TreeNode root = createFirstTreeFromSlides();
    LowestCommonAncestorEulerTour solver = new LowestCommonAncestorEulerTour(root);

    // LCA(a, b) should equal LCA(b, a) for all pairs
    for (int i = 0; i < root.size(); i++) {
      for (int j = i + 1; j < root.size(); j++) {
        assertThat(solver.lca(i, j).index()).isEqualTo(solver.lca(j, i).index());
      }
    }
  }

  @Test
  public void testLcaRootIsAlwaysAncestor() {
    LowestCommonAncestorEulerTour.TreeNode root = createFirstTreeFromSlides();
    LowestCommonAncestorEulerTour solver = new LowestCommonAncestorEulerTour(root);

    // LCA of root with any node should be root
    for (int i = 0; i < root.size(); i++) {
      assertThat(solver.lca(0, i).index()).isEqualTo(0);
    }
  }

  @Test
  public void testLcaParentChild() {
    LowestCommonAncestorEulerTour.TreeNode root = createFirstTreeFromSlides();
    LowestCommonAncestorEulerTour solver = new LowestCommonAncestorEulerTour(root);

    // LCA of a parent and its direct child is the parent
    // Node 1's children are 3 and 4
    assertThat(solver.lca(1, 3).index()).isEqualTo(1);
    assertThat(solver.lca(1, 4).index()).isEqualTo(1);
    // Node 2's children are 5, 6, 7
    assertThat(solver.lca(2, 5).index()).isEqualTo(2);
    assertThat(solver.lca(2, 7).index()).isEqualTo(2);
    // Node 11's children are 14, 15, 16
    assertThat(solver.lca(11, 14).index()).isEqualTo(11);
  }

  @Test
  public void testSingleNodeTree() {
    List<List<Integer>> tree = LowestCommonAncestorEulerTour.createEmptyGraph(1);
    LowestCommonAncestorEulerTour.TreeNode root =
        LowestCommonAncestorEulerTour.TreeNode.rootTree(tree, 0);

    LowestCommonAncestorEulerTour solver = new LowestCommonAncestorEulerTour(root);
    assertThat(solver.lca(0, 0).index()).isEqualTo(0);
  }

  @Test
  public void testTwoNodeTree() {
    List<List<Integer>> tree = LowestCommonAncestorEulerTour.createEmptyGraph(2);
    LowestCommonAncestorEulerTour.addUndirectedEdge(tree, 0, 1);
    LowestCommonAncestorEulerTour.TreeNode root =
        LowestCommonAncestorEulerTour.TreeNode.rootTree(tree, 0);

    LowestCommonAncestorEulerTour solver = new LowestCommonAncestorEulerTour(root);
    assertThat(solver.lca(0, 1).index()).isEqualTo(0);
    assertThat(solver.lca(1, 0).index()).isEqualTo(0);
    assertThat(solver.lca(1, 1).index()).isEqualTo(1);
  }

  @Test
  public void testLinearChainTree() {
    // Build a chain: 0 - 1 - 2 - 3 - 4
    int n = 5;
    List<List<Integer>> tree = LowestCommonAncestorEulerTour.createEmptyGraph(n);
    for (int i = 0; i < n - 1; i++) {
      LowestCommonAncestorEulerTour.addUndirectedEdge(tree, i, i + 1);
    }
    LowestCommonAncestorEulerTour.TreeNode root =
        LowestCommonAncestorEulerTour.TreeNode.rootTree(tree, 0);

    LowestCommonAncestorEulerTour solver = new LowestCommonAncestorEulerTour(root);

    // In a chain rooted at 0, LCA of any two nodes is the one closer to root
    assertThat(solver.lca(0, 4).index()).isEqualTo(0);
    assertThat(solver.lca(1, 4).index()).isEqualTo(1);
    assertThat(solver.lca(2, 4).index()).isEqualTo(2);
    assertThat(solver.lca(3, 4).index()).isEqualTo(3);
    assertThat(solver.lca(1, 3).index()).isEqualTo(1);
  }

  @Test
  public void testTreeNodeProperties() {
    LowestCommonAncestorEulerTour.TreeNode root = createFirstTreeFromSlides();

    assertThat(root.index()).isEqualTo(0);
    assertThat(root.parent()).isNull();
    assertThat(root.size()).isEqualTo(17);
    assertThat(root.children()).hasSize(2);
    assertThat(root.toString()).isEqualTo("0");
  }

  @Test
  public void testTreeNodeChildProperties() {
    LowestCommonAncestorEulerTour.TreeNode root = createFirstTreeFromSlides();

    // Node 1 (first child of root)
    LowestCommonAncestorEulerTour.TreeNode node1 = root.children().get(0);
    assertThat(node1.index()).isEqualTo(1);
    assertThat(node1.parent().index()).isEqualTo(0);
    assertThat(node1.children()).hasSize(2); // children: 3, 4
  }

  @Test
  public void testMalformedGraphThrows() {
    // Graph with 3 nodes but only 1 edge (disconnected)
    List<List<Integer>> tree = LowestCommonAncestorEulerTour.createEmptyGraph(3);
    LowestCommonAncestorEulerTour.addUndirectedEdge(tree, 0, 1);

    assertThrows(
        IllegalArgumentException.class,
        () -> LowestCommonAncestorEulerTour.TreeNode.rootTree(tree, 0));
  }

  public static List<List<Integer>> generateRandomTree(int n) {
    List<Integer> nodes = new ArrayList<>();
    nodes.add(0);

    List<List<Integer>> g = LowestCommonAncestorEulerTour.createEmptyGraph(n);
    for (int nextNode = 1; nodes.size() != n; nextNode++) {
      int randomNode = nodes.get((int) (Math.random() * nodes.size()));
      LowestCommonAncestorEulerTour.addUndirectedEdge(g, randomNode, nextNode);
      nodes.add(nextNode);
    }
    return g;
  }
}
