package com.williamfiset.algorithms.datastructures.balancedtree;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.*;
import org.junit.jupiter.api.*;

public class RedBlackTreeTest {

  static final int MAX_RAND_NUM = +100000;
  static final int MIN_RAND_NUM = -100000;

  static final int TEST_SZ = 9000;

  private RedBlackTree<Integer> tree;

  @BeforeEach
  public void setup() {
    tree = new RedBlackTree<>();
  }

  @Test
  public void testNullInsertion() {
    assertThrows(IllegalArgumentException.class, () -> tree.insert(null));
  }

  @Test
  public void testTreeContainsNull() {
    assertThat(tree.contains(null)).isFalse();
  }

  @Test
  public void testLeftLeftRotation() {

    tree.insert(3);
    tree.insert(2);
    tree.insert(1);

    assertThat(tree.root.getValue().intValue()).isEqualTo(2);
    assertThat(tree.root.getLeft().getValue().intValue()).isEqualTo(1);
    assertThat(tree.root.getRight().getValue().intValue()).isEqualTo(3);

    assertThat(tree.root.getColor()).isEqualTo(RedBlackTree.BLACK);
    assertThat(tree.root.getLeft().getColor()).isEqualTo(RedBlackTree.RED);
    assertThat(tree.root.getRight().getColor()).isEqualTo(RedBlackTree.RED);

    assertThat(tree.root).isEqualTo(tree.root.getLeft().getParent());
    assertThat(tree.root).isEqualTo(tree.root.getRight().getParent());

    assertNullChildren(tree, tree.root.getLeft(), tree.root.getRight());
    assertCorrectParentLinks(tree, tree.root, tree.NIL);
    verifyProperties(tree);
  }

  @Test
  public void testLeftRightRotation() {

    tree.insert(3);
    tree.insert(1);
    tree.insert(2);

    assertThat(tree.root.getValue().intValue()).isEqualTo(2);
    assertThat(tree.root.getLeft().getValue().intValue()).isEqualTo(1);
    assertThat(tree.root.getRight().getValue().intValue()).isEqualTo(3);

    assertThat(tree.root.getColor()).isEqualTo(RedBlackTree.BLACK);
    assertThat(tree.root.getLeft().getColor()).isEqualTo(RedBlackTree.RED);
    assertThat(tree.root.getRight().getColor()).isEqualTo(RedBlackTree.RED);

    assertThat(tree.root).isEqualTo(tree.root.getLeft().getParent());
    assertThat(tree.root).isEqualTo(tree.root.getRight().getParent());

    assertNullChildren(tree, tree.root.getLeft(), tree.root.getRight());
    assertCorrectParentLinks(tree, tree.root, tree.NIL);
    verifyProperties(tree);
  }

  @Test
  public void testRightLeftRotation() {

    tree.insert(1);
    tree.insert(3);
    tree.insert(2);

    assertThat(tree.root.getValue().intValue()).isEqualTo(2);
    assertThat(tree.root.getLeft().getValue().intValue()).isEqualTo(1);
    assertThat(tree.root.getRight().getValue().intValue()).isEqualTo(3);

    assertThat(tree.root.getColor()).isEqualTo(RedBlackTree.BLACK);
    assertThat(tree.root.getLeft().getColor()).isEqualTo(RedBlackTree.RED);
    assertThat(tree.root.getRight().getColor()).isEqualTo(RedBlackTree.RED);

    assertThat(tree.root).isEqualTo(tree.root.getLeft().getParent());
    assertThat(tree.root).isEqualTo(tree.root.getRight().getParent());

    assertNullChildren(tree, tree.root.getLeft(), tree.root.getRight());
    assertCorrectParentLinks(tree, tree.root, tree.NIL);
    verifyProperties(tree);
  }

  @Test
  public void testRightRightRotation() {

    tree.insert(1);
    tree.insert(2);
    tree.insert(3);

    assertThat(tree.root.getValue().intValue()).isEqualTo(2);
    assertThat(tree.root.getLeft().getValue().intValue()).isEqualTo(1);
    assertThat(tree.root.getRight().getValue().intValue()).isEqualTo(3);

    assertThat(tree.root.getColor()).isEqualTo(RedBlackTree.BLACK);
    assertThat(tree.root.getLeft().getColor()).isEqualTo(RedBlackTree.RED);
    assertThat(tree.root.getRight().getColor()).isEqualTo(RedBlackTree.RED);

    assertThat(tree.root).isEqualTo(tree.root.getLeft().getParent());
    assertThat(tree.root).isEqualTo(tree.root.getRight().getParent());

    assertNullChildren(tree, tree.root.getLeft(), tree.root.getRight());
    assertCorrectParentLinks(tree, tree.root, tree.NIL);
    verifyProperties(tree);
  }

  @Test
  public void testLeftUncleCase() {

    /* Red left uncle case. */

    tree.insert(1);
    tree.insert(2);
    tree.insert(3);
    tree.insert(4);

    assertThat(tree.root.getValue().intValue()).isEqualTo(2);
    assertThat(tree.root.getLeft().getValue().intValue()).isEqualTo(1);
    assertThat(tree.root.getRight().getValue().intValue()).isEqualTo(3);
    assertThat(tree.root.getRight().getRight().getValue().intValue()).isEqualTo(4);

    assertThat(tree.root.getColor()).isEqualTo(RedBlackTree.BLACK);
    assertThat(tree.root.getLeft().getColor()).isEqualTo(RedBlackTree.BLACK);
    assertThat(tree.root.getRight().getColor()).isEqualTo(RedBlackTree.BLACK);
    assertThat(tree.root.getRight().getRight().getColor()).isEqualTo(RedBlackTree.RED);

    assertThat(tree.root.getRight().getLeft()).isEqualTo(tree.NIL);
    assertNullChildren(tree, tree.root.getLeft(), tree.root.getRight().getRight());
    assertCorrectParentLinks(tree, tree.root, tree.NIL);
    verifyProperties(tree);

    /* Black left uncle case. */

    tree.insert(5);

    assertThat(tree.root.getValue().intValue()).isEqualTo(2);
    assertThat(tree.root.getLeft().getValue().intValue()).isEqualTo(1);
    assertThat(tree.root.getRight().getValue().intValue()).isEqualTo(4);
    assertThat(tree.root.getRight().getLeft().getValue().intValue()).isEqualTo(3);
    assertThat(tree.root.getRight().getRight().getValue().intValue()).isEqualTo(5);

    assertThat(tree.root.getColor()).isEqualTo(RedBlackTree.BLACK);
    assertThat(tree.root.getLeft().getColor()).isEqualTo(RedBlackTree.BLACK);
    assertThat(tree.root.getRight().getColor()).isEqualTo(RedBlackTree.BLACK);
    assertThat(tree.root.getRight().getLeft().getColor()).isEqualTo(RedBlackTree.RED);
    assertThat(tree.root.getRight().getRight().getColor()).isEqualTo(RedBlackTree.RED);
    assertCorrectParentLinks(tree, tree.root, tree.NIL);
    verifyProperties(tree);
  }

  @Test
  public void testRightUncleCase() {

    /* Red right uncle case. */

    tree.insert(2);
    tree.insert(3);
    tree.insert(4);
    tree.insert(1);

    assertThat(tree.root.getValue().intValue()).isEqualTo(3);
    assertThat(tree.root.getLeft().getValue().intValue()).isEqualTo(2);
    assertThat(tree.root.getRight().getValue().intValue()).isEqualTo(4);
    assertThat(tree.root.getLeft().getLeft().getValue().intValue()).isEqualTo(1);

    assertThat(tree.root.getColor()).isEqualTo(RedBlackTree.BLACK);
    assertThat(tree.root.getLeft().getColor()).isEqualTo(RedBlackTree.BLACK);
    assertThat(tree.root.getRight().getColor()).isEqualTo(RedBlackTree.BLACK);
    assertThat(tree.root.getLeft().getLeft().getColor()).isEqualTo(RedBlackTree.RED);

    assertThat(tree.root.getRight().getLeft()).isEqualTo(tree.NIL);
    assertThat(tree.root.getLeft().getRight()).isEqualTo(tree.NIL);
    assertNullChildren(tree, tree.root.getRight(), tree.root.getLeft().getLeft());
    assertCorrectParentLinks(tree, tree.root, tree.NIL);
    verifyProperties(tree);

    /* Black right uncle case. */

    tree.insert(0);

    assertThat(tree.root.getValue().intValue()).isEqualTo(3);
    assertThat(tree.root.getLeft().getValue().intValue()).isEqualTo(1);
    assertThat(tree.root.getRight().getValue().intValue()).isEqualTo(4);
    assertThat(tree.root.getLeft().getLeft().getValue().intValue()).isEqualTo(0);
    assertThat(tree.root.getLeft().getRight().getValue().intValue()).isEqualTo(2);

    assertThat(tree.root.getColor()).isEqualTo(RedBlackTree.BLACK);
    assertThat(tree.root.getLeft().getColor()).isEqualTo(RedBlackTree.BLACK);
    assertThat(tree.root.getRight().getColor()).isEqualTo(RedBlackTree.BLACK);
    assertThat(tree.root.getLeft().getLeft().getColor()).isEqualTo(RedBlackTree.RED);
    assertThat(tree.root.getLeft().getRight().getColor()).isEqualTo(RedBlackTree.RED);
    assertCorrectParentLinks(tree, tree.root, tree.NIL);
    verifyProperties(tree);
  }

  @Test
  public void interestingCase1() {

    int[] values = {41, 44, 95, 83, 72, 66, 94, 90, 59};
    for (int v : values) tree.insert(v);

    assertThat(tree.root.getValue().intValue()).isEqualTo(44);

    assertThat(tree.root.getLeft().getValue().intValue()).isEqualTo(41);
    assertThat(tree.root.getRight().getValue().intValue()).isEqualTo(83);

    assertThat(tree.root.getRight().getLeft().getValue().intValue()).isEqualTo(66);
    assertThat(tree.root.getRight().getRight().getValue().intValue()).isEqualTo(94);

    assertThat(tree.root.getRight().getLeft().getLeft().getValue().intValue()).isEqualTo(59);
    assertThat(tree.root.getRight().getLeft().getRight().getValue().intValue()).isEqualTo(72);
    assertThat(tree.root.getRight().getRight().getLeft().getValue().intValue()).isEqualTo(90);
    assertThat(tree.root.getRight().getRight().getRight().getValue().intValue()).isEqualTo(95);

    assertThat(tree.root.getColor()).isEqualTo(RedBlackTree.BLACK);
    assertThat(tree.root.getLeft().getColor()).isEqualTo(RedBlackTree.BLACK);
    assertThat(tree.root.getRight().getColor()).isEqualTo(RedBlackTree.RED);
    assertThat(tree.root.getRight().getLeft().getColor()).isEqualTo(RedBlackTree.BLACK);
    assertThat(tree.root.getRight().getRight().getColor()).isEqualTo(RedBlackTree.BLACK);
    assertThat(tree.root.getRight().getLeft().getLeft().getColor()).isEqualTo(RedBlackTree.RED);
    assertThat(tree.root.getRight().getLeft().getRight().getColor()).isEqualTo(RedBlackTree.RED);
    assertThat(tree.root.getRight().getRight().getLeft().getColor()).isEqualTo(RedBlackTree.RED);
    assertThat(tree.root.getRight().getRight().getRight().getColor()).isEqualTo(RedBlackTree.RED);
    verifyProperties(tree);
  }

  @Test
  public void testRandomizedValueInsertionsAgainstTreeSet() {

    TreeSet<Integer> set = new TreeSet<>();
    for (int i = 0; i < TEST_SZ; i++) {
      int v = randValue();
      assertThat(tree.insert(v)).isEqualTo(set.add(v));
      assertThat(tree.size()).isEqualTo(set.size());
      assertThat(tree.contains(v)).isTrue();
      assertBinarySearchTreeInvariant(tree, tree.root);
      if (i % 100 == 0) verifyProperties(tree);
    }
    verifyProperties(tree);
  }

  @Test
  public void testRemoval() {
    tree.insert(5);
    tree.insert(7);
    tree.insert(9);

    tree.delete(5);
    assertThat(tree.contains(5)).isFalse();
    verifyProperties(tree);

    tree.delete(7);
    assertThat(tree.contains(7)).isFalse();
    verifyProperties(tree);

    tree.delete(9);
    assertThat(tree.contains(9)).isFalse();
    verifyProperties(tree);
  }

  @Test
  public void testNullRemoval() {
    assertThat(tree.delete(null)).isFalse();
  }

  @Test
  public void testNumberDoesntExist() {
    assertThat(tree.delete(0)).isFalse();
  }

  @Test
  public void randomRemoveTests() {
    TreeSet<Integer> ts = new TreeSet<>();
    for (int i = 0; i < 100; i++) { // Reduced TEST_SZ for faster property verification

      List<Integer> lst = genRandList(i);
      for (Integer value : lst) {
        tree.insert(value);
        ts.add(value);
      }
      Collections.shuffle(lst);

      for (int j = 0; j < i; j++) {

        Integer value = lst.get(j);
        boolean treeSetRemove = ts.remove(value);
        boolean treeRemove = tree.delete(value);
        assertThat(treeSetRemove).isEqualTo(treeRemove);
        assertThat(tree.contains(value)).isFalse();
        assertThat(tree.size()).isEqualTo(i - j - 1);
        verifyProperties(tree);
      }
      assertThat(ts.isEmpty()).isEqualTo(tree.isEmpty());
    }
  }

  @Test
  public void testTreeHeight() {
    for (int n = 1; n <= TEST_SZ; n++) {

      tree.insert(randValue());
      double height = tree.height();

      // RB tree height upper bound:
      // https://en.wikipedia.org/wiki/AVL_tree#Comparison_to_other_structures
      double upperBound = 2 * (Math.log(n + 1) / Math.log(2));

      assertThat(height).isAtMost(upperBound);
    }
  }

  static void assertNullChildren(RedBlackTree tree, RedBlackTree.Node... nodes) {
    for (RedBlackTree.Node node : nodes) {
      assertThat(node.getLeft()).isEqualTo(tree.NIL);
      assertThat(node.getRight()).isEqualTo(tree.NIL);
    }
  }

  static void assertCorrectParentLinks(
      RedBlackTree tree, RedBlackTree.Node node, RedBlackTree.Node parent) {
    if (node == tree.NIL) return;
    try {
      assertThat(node.getParent()).isEqualTo(parent);
    } catch (AssertionError e) {
      e.printStackTrace();
    }
    assertCorrectParentLinks(tree, node.getLeft(), node);
    assertCorrectParentLinks(tree, node.getRight(), node);
  }

  // Make sure all left child nodes are smaller in value than their parent and
  // make sure all right child nodes are greater in value than their parent.
  // (Used only for testing)
  boolean assertBinarySearchTreeInvariant(RedBlackTree tree, RedBlackTree<Integer>.Node node) {
    if (node == tree.NIL) return true;
    boolean isValid = true;
    if (node.getLeft() != tree.NIL) isValid = node.getLeft().getValue().compareTo(node.getValue()) < 0;
    if (node.getRight() != tree.NIL)
      isValid = isValid && node.getRight().getValue().compareTo(node.getValue()) > 0;
    return isValid
        && assertBinarySearchTreeInvariant(tree, node.getLeft())
        && assertBinarySearchTreeInvariant(tree, node.getRight());
  }

  // Used for testing.
  boolean validateParentLinksAreCorrect(RedBlackTree.Node node, RedBlackTree.Node parent) {
    if (node == tree.NIL) return true;
    if (node.getParent() != parent) return false;
    return validateParentLinksAreCorrect(node.getLeft(), node)
        && validateParentLinksAreCorrect(node.getRight(), node);
  }

  // Verify RB tree properties
  private void verifyProperties(RedBlackTree<Integer> tree) {
    if (tree.root == tree.NIL) return;

    // 1. Every node is either red or black (Implicit)

    // 2. The root is black
    assertThat(tree.root.getColor()).isEqualTo(RedBlackTree.BLACK);

    // 3. Every leaf (NIL) is black
    assertThat(tree.NIL.getColor()).isEqualTo(RedBlackTree.BLACK);

    verifyRedNodeProperty(tree, tree.root);
    verifyBlackHeightProperty(tree, tree.root);
  }

  private void verifyRedNodeProperty(RedBlackTree<Integer> tree, RedBlackTree<Integer>.Node node) {
    if (node == tree.NIL) return;
    if (node.getColor() == RedBlackTree.RED) {
      assertThat(node.getLeft().getColor()).isEqualTo(RedBlackTree.BLACK);
      assertThat(node.getRight().getColor()).isEqualTo(RedBlackTree.BLACK);
    }
    verifyRedNodeProperty(tree, node.getLeft());
    verifyRedNodeProperty(tree, node.getRight());
  }

  private int verifyBlackHeightProperty(
      RedBlackTree<Integer> tree, RedBlackTree<Integer>.Node node) {
    if (node == tree.NIL) return 1;

    int leftHeight = verifyBlackHeightProperty(tree, node.getLeft());
    int rightHeight = verifyBlackHeightProperty(tree, node.getRight());

    assertThat(leftHeight).isEqualTo(rightHeight);

    return leftHeight + (node.getColor() == RedBlackTree.BLACK ? 1 : 0);
  }

  static List<Integer> genRandList(int sz) {
    List<Integer> lst = new ArrayList<>(sz);
    for (int i = 0; i < sz; i++) lst.add(i); // unique values.
    Collections.shuffle(lst);
    return lst;
  }

  public static int randValue() {
    return (int) (Math.random() * MAX_RAND_NUM * 2) + MIN_RAND_NUM;
  }
}
