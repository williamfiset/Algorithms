// To run this test in isolation from root folder:
//
// $ gradle test --tests
// com.williamfiset.algorithms.graphtheory.treealgorithms.TreeIsomorphismWithBfsTest

package com.williamfiset.algorithms.graphtheory.treealgorithms;

import static com.google.common.truth.Truth.assertThat;
import static com.williamfiset.algorithms.graphtheory.treealgorithms.TreeIsomorphism.TreeNode;
import static com.williamfiset.algorithms.graphtheory.treealgorithms.TreeIsomorphismWithBfs.addUndirectedEdge;
import static com.williamfiset.algorithms.graphtheory.treealgorithms.TreeIsomorphismWithBfs.createEmptyTree;
import static com.williamfiset.algorithms.graphtheory.treealgorithms.TreeIsomorphismWithBfs.encodeTree;
import static com.williamfiset.algorithms.graphtheory.treealgorithms.TreeIsomorphismWithBfs.treesAreIsomorphic;

import java.util.*;
import org.junit.*;

public class TreeIsomorphismWithBfsTest {

  @Test
  public void testSingleton() {
    List<List<Integer>> tree1 = createEmptyTree(1);
    List<List<Integer>> tree2 = createEmptyTree(1);
    assertThat(treesAreIsomorphic(tree1, tree2)).isEqualTo(true);
  }

  @Test
  public void testTwoNodeTree() {
    List<List<Integer>> tree1 = createEmptyTree(2);
    List<List<Integer>> tree2 = createEmptyTree(2);

    addUndirectedEdge(tree1, 0, 1);
    addUndirectedEdge(tree2, 1, 0);

    assertThat(treesAreIsomorphic(tree1, tree2)).isEqualTo(true);
  }

  @Test
  public void testSmall() {
    List<List<Integer>> tree1 = createEmptyTree(5);
    List<List<Integer>> tree2 = createEmptyTree(5);

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
    List<List<Integer>> tree1 = createEmptyTree(n);
    List<List<Integer>> tree2 = createEmptyTree(n);
    List<List<Integer>> tree3 = createEmptyTree(n);

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
  public void testSlidesExample() {
    // Setup tree structure from:
    // http://webhome.cs.uvic.ca/~wendym/courses/582/16/notes/582_12_tree_can_form.pdf
    List<List<Integer>> tree = createEmptyTree(19);

    addUndirectedEdge(tree, 6, 2);
    addUndirectedEdge(tree, 6, 7);
    addUndirectedEdge(tree, 6, 11);
    addUndirectedEdge(tree, 7, 8);
    addUndirectedEdge(tree, 7, 9);
    addUndirectedEdge(tree, 7, 10);
    addUndirectedEdge(tree, 11, 12);
    addUndirectedEdge(tree, 11, 13);
    addUndirectedEdge(tree, 11, 16);
    addUndirectedEdge(tree, 13, 14);
    addUndirectedEdge(tree, 13, 15);
    addUndirectedEdge(tree, 16, 17);
    addUndirectedEdge(tree, 16, 18);
    addUndirectedEdge(tree, 2, 0);
    addUndirectedEdge(tree, 2, 1);
    addUndirectedEdge(tree, 2, 3);
    addUndirectedEdge(tree, 2, 4);
    addUndirectedEdge(tree, 4, 5);

    String treeEncoding = encodeTree(tree);
    String expectedEncoding = "(((()())(()())())((())()()())(()()()))";
    assertThat(treeEncoding).isEqualTo(expectedEncoding);
  }

  @Test
  public void t() {
    List<List<Integer>> tree = createEmptyTree(10);

    TreeNode node0 = new TreeNode(0);
    TreeNode node1 = new TreeNode(1);
    TreeNode node2 = new TreeNode(2);
    TreeNode node3 = new TreeNode(3);
    TreeNode node4 = new TreeNode(4);
    TreeNode node5 = new TreeNode(5);
    TreeNode node6 = new TreeNode(6);
    TreeNode node7 = new TreeNode(7);
    TreeNode node8 = new TreeNode(8);
    TreeNode node9 = new TreeNode(9);

    node0.addChildren(node1, node2, node3);
    node1.addChildren(node4, node5);
    node5.addChildren(node9);
    node2.addChildren(node6, node7);
    node3.addChildren(node8);

    // TODO(william): finish this test to check for "(((())())(()())(()))" encoding
    // System.out.println(
    // com.williamfiset.algorithms.graphtheory.treealgorithms.TreeIsomorphism.encode(node0));

    // (((())())(()())(()))
    //  ((())())
    //          (()())
    //                (())
    //

    // (()())
    // (())
    // (())

    // ((()())(()))
    // ((())())
    //
    // ((()())(()))((())())

    // (((()())(()))((())()))
    //   (()())
    //         (())
    //
    //             ((())())
    //
  }
}
