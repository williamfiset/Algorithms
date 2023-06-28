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

    assertThat(tree.root.value.intValue()).isEqualTo(2);
    assertThat(tree.root.left.value.intValue()).isEqualTo(1);
    assertThat(tree.root.right.value.intValue()).isEqualTo(3);

    assertThat(tree.root.color).isEqualTo(RedBlackTree.BLACK);
    assertThat(tree.root.left.color).isEqualTo(RedBlackTree.RED);
    assertThat(tree.root.right.color).isEqualTo(RedBlackTree.RED);

    assertThat(tree.root).isEqualTo(tree.root.left.parent);
    assertThat(tree.root).isEqualTo(tree.root.right.parent);

    assertNullChildren(tree, tree.root.left, tree.root.right);
    assertCorrectParentLinks(tree, tree.root, tree.NIL);
  }

  @Test
  public void testLeftRightRotation() {

    tree.insert(3);
    tree.insert(1);
    tree.insert(2);

    assertThat(tree.root.value.intValue()).isEqualTo(2);
    assertThat(tree.root.left.value.intValue()).isEqualTo(1);
    assertThat(tree.root.right.value.intValue()).isEqualTo(3);

    assertThat(tree.root.color).isEqualTo(RedBlackTree.BLACK);
    assertThat(tree.root.left.color).isEqualTo(RedBlackTree.RED);
    assertThat(tree.root.right.color).isEqualTo(RedBlackTree.RED);

    assertThat(tree.root).isEqualTo(tree.root.left.parent);
    assertThat(tree.root).isEqualTo(tree.root.right.parent);

    assertNullChildren(tree, tree.root.left, tree.root.right);
    assertCorrectParentLinks(tree, tree.root, tree.NIL);
  }

  @Test
  public void testRightLeftRotation() {

    tree.insert(1);
    tree.insert(3);
    tree.insert(2);

    assertThat(tree.root.value.intValue()).isEqualTo(2);
    assertThat(tree.root.left.value.intValue()).isEqualTo(1);
    assertThat(tree.root.right.value.intValue()).isEqualTo(3);

    assertThat(tree.root.color).isEqualTo(RedBlackTree.BLACK);
    assertThat(tree.root.left.color).isEqualTo(RedBlackTree.RED);
    assertThat(tree.root.right.color).isEqualTo(RedBlackTree.RED);

    assertThat(tree.root).isEqualTo(tree.root.left.parent);
    assertThat(tree.root).isEqualTo(tree.root.right.parent);

    assertNullChildren(tree, tree.root.left, tree.root.right);
    assertCorrectParentLinks(tree, tree.root, tree.NIL);
  }

  @Test
  public void testRightRightRotation() {

    tree.insert(1);
    tree.insert(2);
    tree.insert(3);

    assertThat(tree.root.value.intValue()).isEqualTo(2);
    assertThat(tree.root.left.value.intValue()).isEqualTo(1);
    assertThat(tree.root.right.value.intValue()).isEqualTo(3);

    assertThat(tree.root.color).isEqualTo(RedBlackTree.BLACK);
    assertThat(tree.root.left.color).isEqualTo(RedBlackTree.RED);
    assertThat(tree.root.right.color).isEqualTo(RedBlackTree.RED);

    assertThat(tree.root).isEqualTo(tree.root.left.parent);
    assertThat(tree.root).isEqualTo(tree.root.right.parent);

    assertNullChildren(tree, tree.root.left, tree.root.right);
    assertCorrectParentLinks(tree, tree.root, tree.NIL);
  }

  @Test
  public void testLeftUncleCase() {

    /* Red left uncle case. */

    tree.insert(1);
    tree.insert(2);
    tree.insert(3);
    tree.insert(4);

    assertThat(tree.root.value.intValue()).isEqualTo(2);
    assertThat(tree.root.left.value.intValue()).isEqualTo(1);
    assertThat(tree.root.right.value.intValue()).isEqualTo(3);
    assertThat(tree.root.right.right.value.intValue()).isEqualTo(4);

    assertThat(tree.root.color).isEqualTo(RedBlackTree.BLACK);
    assertThat(tree.root.left.color).isEqualTo(RedBlackTree.BLACK);
    assertThat(tree.root.right.color).isEqualTo(RedBlackTree.BLACK);
    assertThat(tree.root.right.right.color).isEqualTo(RedBlackTree.RED);

    assertThat(tree.root.right.left).isEqualTo(tree.NIL);
    assertNullChildren(tree, tree.root.left, tree.root.right.right);
    assertCorrectParentLinks(tree, tree.root, tree.NIL);

    /* Black left uncle case. */

    tree.insert(5);

    assertThat(tree.root.value.intValue()).isEqualTo(2);
    assertThat(tree.root.left.value.intValue()).isEqualTo(1);
    assertThat(tree.root.right.value.intValue()).isEqualTo(4);
    assertThat(tree.root.right.left.value.intValue()).isEqualTo(3);
    assertThat(tree.root.right.right.value.intValue()).isEqualTo(5);

    assertThat(tree.root.color).isEqualTo(RedBlackTree.BLACK);
    assertThat(tree.root.left.color).isEqualTo(RedBlackTree.BLACK);
    assertThat(tree.root.right.color).isEqualTo(RedBlackTree.BLACK);
    assertThat(tree.root.right.left.color).isEqualTo(RedBlackTree.RED);
    assertThat(tree.root.right.right.color).isEqualTo(RedBlackTree.RED);
    assertCorrectParentLinks(tree, tree.root, tree.NIL);
  }

  @Test
  public void testRightUncleCase() {

    /* Red right uncle case. */

    tree.insert(2);
    tree.insert(3);
    tree.insert(4);
    tree.insert(1);

    assertThat(tree.root.value.intValue()).isEqualTo(3);
    assertThat(tree.root.left.value.intValue()).isEqualTo(2);
    assertThat(tree.root.right.value.intValue()).isEqualTo(4);
    assertThat(tree.root.left.left.value.intValue()).isEqualTo(1);

    assertThat(tree.root.color).isEqualTo(RedBlackTree.BLACK);
    assertThat(tree.root.left.color).isEqualTo(RedBlackTree.BLACK);
    assertThat(tree.root.right.color).isEqualTo(RedBlackTree.BLACK);
    assertThat(tree.root.left.left.color).isEqualTo(RedBlackTree.RED);

    assertThat(tree.root.right.left).isEqualTo(tree.NIL);
    assertThat(tree.root.left.right).isEqualTo(tree.NIL);
    assertNullChildren(tree, tree.root.right, tree.root.left.left);
    assertCorrectParentLinks(tree, tree.root, tree.NIL);

    /* Black right uncle case. */

    tree.insert(0);

    assertThat(tree.root.value.intValue()).isEqualTo(3);
    assertThat(tree.root.left.value.intValue()).isEqualTo(1);
    assertThat(tree.root.right.value.intValue()).isEqualTo(4);
    assertThat(tree.root.left.left.value.intValue()).isEqualTo(0);
    assertThat(tree.root.left.right.value.intValue()).isEqualTo(2);

    assertThat(tree.root.color).isEqualTo(RedBlackTree.BLACK);
    assertThat(tree.root.left.color).isEqualTo(RedBlackTree.BLACK);
    assertThat(tree.root.right.color).isEqualTo(RedBlackTree.BLACK);
    assertThat(tree.root.left.left.color).isEqualTo(RedBlackTree.RED);
    assertThat(tree.root.left.right.color).isEqualTo(RedBlackTree.RED);
    assertCorrectParentLinks(tree, tree.root, tree.NIL);
  }

  @Test
  public void interestingCase1() {

    int[] values = {41, 44, 95, 83, 72, 66, 94, 90, 59};
    for (int v : values) tree.insert(v);

    assertThat(tree.root.value.intValue()).isEqualTo(44);

    assertThat(tree.root.left.value.intValue()).isEqualTo(41);
    assertThat(tree.root.right.value.intValue()).isEqualTo(83);

    assertThat(tree.root.right.left.value.intValue()).isEqualTo(66);
    assertThat(tree.root.right.right.value.intValue()).isEqualTo(94);

    assertThat(tree.root.right.left.left.value.intValue()).isEqualTo(59);
    assertThat(tree.root.right.left.right.value.intValue()).isEqualTo(72);
    assertThat(tree.root.right.right.left.value.intValue()).isEqualTo(90);
    assertThat(tree.root.right.right.right.value.intValue()).isEqualTo(95);

    assertThat(tree.root.color).isEqualTo(RedBlackTree.BLACK);
    assertThat(tree.root.left.color).isEqualTo(RedBlackTree.BLACK);
    assertThat(tree.root.right.color).isEqualTo(RedBlackTree.RED);
    assertThat(tree.root.right.left.color).isEqualTo(RedBlackTree.BLACK);
    assertThat(tree.root.right.right.color).isEqualTo(RedBlackTree.BLACK);
    assertThat(tree.root.right.left.left.color).isEqualTo(RedBlackTree.RED);
    assertThat(tree.root.right.left.right.color).isEqualTo(RedBlackTree.RED);
    assertThat(tree.root.right.right.left.color).isEqualTo(RedBlackTree.RED);
    assertThat(tree.root.right.right.right.color).isEqualTo(RedBlackTree.RED);
  }

  @Test
  public void testRandomizedValueInsertionsAgainstTreeSet() {

    TreeSet<Integer> set = new TreeSet<>();
    for (int i = 0; i < TEST_SZ; i++) {
      int v = randValue();
      assertThat(tree.insert(v)).isEqualTo(set.add(v));
      assertThat(tree.size()).isEqualTo(tree.size());
      assertThat(tree.contains(v)).isTrue();
      assertBinarySearchTreeInvariant(tree, tree.root);
    }
  }

  @Test
  public void testRemoval() {
    tree.insert(5);
    tree.insert(7);
    tree.insert(9);

    tree.delete(5);
    assertThat(tree.contains(5)).isFalse();

    tree.delete(7);
    assertThat(tree.contains(7)).isFalse();

    tree.delete(9);
    assertThat(tree.contains(9)).isFalse();
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
    for (int i = 0; i < TEST_SZ; i++) {

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
      assertThat(node.left).isEqualTo(tree.NIL);
      assertThat(node.right).isEqualTo(tree.NIL);
    }
  }

  static void assertCorrectParentLinks(
      RedBlackTree tree, RedBlackTree.Node node, RedBlackTree.Node parent) {
    if (node == tree.NIL) return;
    try {
      assertThat(node.parent).isEqualTo(parent);
    } catch (AssertionError e) {
      e.printStackTrace();
    }
    assertCorrectParentLinks(tree, node.left, node);
    assertCorrectParentLinks(tree, node.right, node);
  }

  // Make sure all left child nodes are smaller in value than their parent and
  // make sure all right child nodes are greater in value than their parent.
  // (Used only for testing)
  boolean assertBinarySearchTreeInvariant(RedBlackTree tree, RedBlackTree<Integer>.Node node) {
    if (node == tree.NIL) return true;
    boolean isValid = true;
    if (node.left != tree.NIL) isValid = node.left.value.compareTo(node.value) < 0;
    if (node.right != tree.NIL) isValid = isValid && node.right.value.compareTo(node.value) > 0;
    return isValid
        && assertBinarySearchTreeInvariant(tree, node.left)
        && assertBinarySearchTreeInvariant(tree, node.right);
  }

  // Used for testing.
  boolean validateParentLinksAreCorrect(RedBlackTree.Node node, RedBlackTree.Node parent) {
    if (node == tree.NIL) return true;
    if (node.parent != parent) return false;
    return validateParentLinksAreCorrect(node.left, node)
        && validateParentLinksAreCorrect(node.right, node);
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
