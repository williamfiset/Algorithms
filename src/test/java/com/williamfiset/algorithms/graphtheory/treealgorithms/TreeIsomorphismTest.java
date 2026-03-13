package com.williamfiset.algorithms.graphtheory.treealgorithms;

import static com.google.common.truth.Truth.assertThat;
import static com.williamfiset.algorithms.graphtheory.treealgorithms.TreeIsomorphism.addUndirectedEdge;
import static com.williamfiset.algorithms.graphtheory.treealgorithms.TreeIsomorphism.createEmptyGraph;
import static com.williamfiset.algorithms.graphtheory.treealgorithms.TreeIsomorphism.encode;
import static com.williamfiset.algorithms.graphtheory.treealgorithms.TreeIsomorphism.treesAreIsomorphic;
import static org.junit.Assert.assertThrows;

import com.williamfiset.algorithms.graphtheory.treealgorithms.TreeIsomorphism.TreeNode;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

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

  // ==================== Encoding tests ====================

  @Test
  public void testEncodeNullNode() {
    assertThat(encode(null)).isEqualTo("");
  }

  @Test
  public void testEncodeLeafNode() {
    TreeNode leaf = new TreeNode(0);
    assertThat(encode(leaf)).isEqualTo("()");
  }

  @Test
  public void testEncodeLinearTree() {
    // 0 -> 1 -> 2
    TreeNode root = new TreeNode(0);
    TreeNode child = new TreeNode(1, root);
    TreeNode grandchild = new TreeNode(2, child);
    root.addChildren(child);
    child.addChildren(grandchild);

    assertThat(encode(root)).isEqualTo("((()))");
  }

  @Test
  public void testEncodeStarTree() {
    // 0 with children 1, 2, 3
    TreeNode root = new TreeNode(0);
    root.addChildren(new TreeNode(1, root), new TreeNode(2, root), new TreeNode(3, root));

    assertThat(encode(root)).isEqualTo("(()()())");
  }

  @Test
  public void testEncodeFromSlides() {
    //           0
    //        /  |  \
    //       2   1   3
    //      / \ / \   \
    //     6  7 4  5   8
    //             |
    //             9
    List<List<Integer>> tree = createEmptyGraph(10);
    addUndirectedEdge(tree, 0, 2);
    addUndirectedEdge(tree, 0, 1);
    addUndirectedEdge(tree, 0, 3);
    addUndirectedEdge(tree, 2, 6);
    addUndirectedEdge(tree, 2, 7);
    addUndirectedEdge(tree, 1, 4);
    addUndirectedEdge(tree, 1, 5);
    addUndirectedEdge(tree, 5, 9);
    addUndirectedEdge(tree, 3, 8);

    // Root at node 0 and use treesAreIsomorphic's internal rootTree via encode
    // We build manually to test encode directly
    TreeNode n0 = new TreeNode(0);
    TreeNode n1 = new TreeNode(1, n0);
    TreeNode n2 = new TreeNode(2, n0);
    TreeNode n3 = new TreeNode(3, n0);
    TreeNode n4 = new TreeNode(4, n1);
    TreeNode n5 = new TreeNode(5, n1);
    TreeNode n6 = new TreeNode(6, n2);
    TreeNode n7 = new TreeNode(7, n2);
    TreeNode n8 = new TreeNode(8, n3);
    TreeNode n9 = new TreeNode(9, n5);

    n0.addChildren(n2, n1, n3);
    n2.addChildren(n6, n7);
    n1.addChildren(n4, n5);
    n5.addChildren(n9);
    n3.addChildren(n8);

    assertThat(encode(n0)).isEqualTo("(((())())(()())(()))");
  }

  @Test
  public void testIsomorphicEncodingsMatch() {
    // Two isomorphic subtrees with different labels should produce the same encoding.
    // Tree A: root -> (child1, child2 -> grandchild)
    TreeNode rootA = new TreeNode(0);
    TreeNode a1 = new TreeNode(1, rootA);
    TreeNode a2 = new TreeNode(2, rootA);
    TreeNode a3 = new TreeNode(3, a2);
    rootA.addChildren(a1, a2);
    a2.addChildren(a3);

    // Tree B: root -> (child5 -> grandchild, child6)
    TreeNode rootB = new TreeNode(10);
    TreeNode b1 = new TreeNode(5, rootB);
    TreeNode b2 = new TreeNode(6, rootB);
    TreeNode b3 = new TreeNode(7, b1);
    rootB.addChildren(b1, b2);
    b1.addChildren(b3);

    assertThat(encode(rootA)).isEqualTo(encode(rootB));
  }

  // ==================== TreeNode tests ====================

  @Test
  public void testTreeNodeParent() {
    TreeNode root = new TreeNode(0);
    TreeNode child = new TreeNode(1, root);
    assertThat(root.parent()).isNull();
    assertThat(child.parent()).isEqualTo(root);
  }

  @Test
  public void testTreeNodeChildren() {
    TreeNode root = new TreeNode(0);
    TreeNode c1 = new TreeNode(1, root);
    TreeNode c2 = new TreeNode(2, root);
    root.addChildren(c1, c2);
    assertThat(root.children()).containsExactly(c1, c2).inOrder();
  }

  @Test
  public void testTreeNodeToString() {
    assertThat(new TreeNode(42).toString()).isEqualTo("42");
  }

  // ==================== Helpers ====================

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
