package com.williamfiset.algorithms.datastructures.balancedtree;

import static org.junit.Assert.*;

import java.util.*;
import org.junit.Before;
import org.junit.Test;

public class RedBlackTreeTest {

  static final int MAX_RAND_NUM = +100000;
  static final int MIN_RAND_NUM = -100000;

  static final int TEST_SZ = 9000;

  private RedBlackTree<Integer> tree;

  @Before
  public void setup() {
    tree = new RedBlackTree<>();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullInsertion() {
    tree.insert(null);
  }

  @Test
  public void testTreeContainsNull() {
    assertFalse(tree.contains(null));
  }

  @Test
  public void testLeftLeftRotation() {

    tree.insert(3);
    tree.insert(2);
    tree.insert(1);

    assertEquals(2, tree.root.value.intValue());
    assertEquals(1, tree.root.left.value.intValue());
    assertEquals(3, tree.root.right.value.intValue());

    assertEquals(RedBlackTree.BLACK, tree.root.color);
    assertEquals(RedBlackTree.RED, tree.root.left.color);
    assertEquals(RedBlackTree.RED, tree.root.right.color);

    assertEquals(tree.root, tree.root.left.parent);
    assertEquals(tree.root, tree.root.right.parent);

    assertNullChildren(tree, tree.root.left, tree.root.right);
    assertCorrectParentLinks(tree, tree.root, tree.NIL);
  }

  @Test
  public void testLeftRightRotation() {

    tree.insert(3);
    tree.insert(1);
    tree.insert(2);

    assertEquals(2, tree.root.value.intValue());
    assertEquals(1, tree.root.left.value.intValue());
    assertEquals(3, tree.root.right.value.intValue());

    assertEquals(RedBlackTree.BLACK, tree.root.color);
    assertEquals(RedBlackTree.RED, tree.root.left.color);
    assertEquals(RedBlackTree.RED, tree.root.right.color);

    assertEquals(tree.root, tree.root.left.parent);
    assertEquals(tree.root, tree.root.right.parent);

    assertNullChildren(tree, tree.root.left, tree.root.right);
    assertCorrectParentLinks(tree, tree.root, tree.NIL);
  }

  @Test
  public void testRightLeftRotation() {

    tree.insert(1);
    tree.insert(3);
    tree.insert(2);

    assertEquals(2, tree.root.value.intValue());
    assertEquals(1, tree.root.left.value.intValue());
    assertEquals(3, tree.root.right.value.intValue());

    assertEquals(RedBlackTree.BLACK, tree.root.color);
    assertEquals(RedBlackTree.RED, tree.root.left.color);
    assertEquals(RedBlackTree.RED, tree.root.right.color);

    assertEquals(tree.root, tree.root.left.parent);
    assertEquals(tree.root, tree.root.right.parent);

    assertNullChildren(tree, tree.root.left, tree.root.right);
    assertCorrectParentLinks(tree, tree.root, tree.NIL);
  }

  @Test
  public void testRightRightRotation() {

    tree.insert(1);
    tree.insert(2);
    tree.insert(3);

    assertEquals(2, tree.root.value.intValue());
    assertEquals(1, tree.root.left.value.intValue());
    assertEquals(3, tree.root.right.value.intValue());

    assertEquals(RedBlackTree.BLACK, tree.root.color);
    assertEquals(RedBlackTree.RED, tree.root.left.color);
    assertEquals(RedBlackTree.RED, tree.root.right.color);

    assertEquals(tree.root, tree.root.left.parent);
    assertEquals(tree.root, tree.root.right.parent);

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

    assertEquals(2, tree.root.value.intValue());
    assertEquals(1, tree.root.left.value.intValue());
    assertEquals(3, tree.root.right.value.intValue());
    assertEquals(4, tree.root.right.right.value.intValue());

    assertEquals(RedBlackTree.BLACK, tree.root.color);
    assertEquals(RedBlackTree.BLACK, tree.root.left.color);
    assertEquals(RedBlackTree.BLACK, tree.root.right.color);
    assertEquals(RedBlackTree.RED, tree.root.right.right.color);

    assertEquals(tree.NIL, tree.root.right.left);
    assertNullChildren(tree, tree.root.left, tree.root.right.right);
    assertCorrectParentLinks(tree, tree.root, tree.NIL);

    /* Black left uncle case. */

    tree.insert(5);

    assertEquals(2, tree.root.value.intValue());
    assertEquals(1, tree.root.left.value.intValue());
    assertEquals(4, tree.root.right.value.intValue());
    assertEquals(3, tree.root.right.left.value.intValue());
    assertEquals(5, tree.root.right.right.value.intValue());

    assertEquals(RedBlackTree.BLACK, tree.root.color);
    assertEquals(RedBlackTree.BLACK, tree.root.left.color);
    assertEquals(RedBlackTree.BLACK, tree.root.right.color);
    assertEquals(RedBlackTree.RED, tree.root.right.left.color);
    assertEquals(RedBlackTree.RED, tree.root.right.right.color);
    assertCorrectParentLinks(tree, tree.root, tree.NIL);
  }

  @Test
  public void testRightUncleCase() {

    /* Red right uncle case. */

    tree.insert(2);
    tree.insert(3);
    tree.insert(4);
    tree.insert(1);

    assertEquals(3, tree.root.value.intValue());
    assertEquals(2, tree.root.left.value.intValue());
    assertEquals(4, tree.root.right.value.intValue());
    assertEquals(1, tree.root.left.left.value.intValue());

    assertEquals(RedBlackTree.BLACK, tree.root.color);
    assertEquals(RedBlackTree.BLACK, tree.root.left.color);
    assertEquals(RedBlackTree.BLACK, tree.root.right.color);
    assertEquals(RedBlackTree.RED, tree.root.left.left.color);

    assertEquals(tree.NIL, tree.root.left.right);
    assertNullChildren(tree, tree.root.right, tree.root.left.left);
    assertCorrectParentLinks(tree, tree.root, tree.NIL);

    /* Black right uncle case. */

    tree.insert(0);

    assertEquals(3, tree.root.value.intValue());
    assertEquals(1, tree.root.left.value.intValue());
    assertEquals(4, tree.root.right.value.intValue());
    assertEquals(0, tree.root.left.left.value.intValue());
    assertEquals(2, tree.root.left.right.value.intValue());

    assertEquals(RedBlackTree.BLACK, tree.root.color);
    assertEquals(RedBlackTree.BLACK, tree.root.left.color);
    assertEquals(RedBlackTree.BLACK, tree.root.right.color);
    assertEquals(RedBlackTree.RED, tree.root.left.left.color);
    assertEquals(RedBlackTree.RED, tree.root.left.right.color);
    assertCorrectParentLinks(tree, tree.root, tree.NIL);
  }

  @Test
  public void interestingCase1() {

    int[] values = {41, 44, 95, 83, 72, 66, 94, 90, 59};
    for (int v : values) tree.insert(v);

    assertEquals(44, tree.root.value.intValue());

    assertEquals(41, tree.root.left.value.intValue());
    assertEquals(83, tree.root.right.value.intValue());

    assertEquals(66, tree.root.right.left.value.intValue());
    assertEquals(94, tree.root.right.right.value.intValue());

    assertEquals(59, tree.root.right.left.left.value.intValue());
    assertEquals(72, tree.root.right.left.right.value.intValue());
    assertEquals(90, tree.root.right.right.left.value.intValue());
    assertEquals(95, tree.root.right.right.right.value.intValue());

    assertEquals(RedBlackTree.BLACK, tree.root.color);
    assertEquals(RedBlackTree.BLACK, tree.root.left.color);
    assertEquals(RedBlackTree.RED, tree.root.right.color);
    assertEquals(RedBlackTree.BLACK, tree.root.right.left.color);
    assertEquals(RedBlackTree.BLACK, tree.root.right.right.color);
    assertEquals(RedBlackTree.RED, tree.root.right.left.left.color);
    assertEquals(RedBlackTree.RED, tree.root.right.left.right.color);
    assertEquals(RedBlackTree.RED, tree.root.right.right.left.color);
    assertEquals(RedBlackTree.RED, tree.root.right.right.right.color);
  }

  @Test
  public void testRandomizedValueInsertionsAgainstTreeSet() {

    TreeSet<Integer> set = new TreeSet<>();
    for (int i = 0; i < TEST_SZ; i++) {
      int v = randValue();
      assertEquals(set.add(v), tree.insert(v));
      assertEquals(set.size(), tree.size());
      assertTrue(tree.contains(v));
      assertBinarySearchTreeInvariant(tree, tree.root);
    }
  }

  @Test
  public void testRemoval() {
    tree.insert(5);
    tree.insert(7);
    tree.insert(9);

    tree.delete(5);
    assertFalse(tree.contains(5));

    tree.delete(7);
    assertFalse(tree.contains(7));

    tree.delete(9);
    assertFalse(tree.contains(9));
  }

  @Test
  public void testNullRemoval() {
    assertFalse(tree.delete(null));
  }

  @Test
  public void testNumberDoesntExist() {
    assertFalse(tree.delete(0));
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
        assertEquals(treeSetRemove, treeRemove);
        assertFalse(tree.contains(value));
        assertEquals(i - j - 1, tree.size());
      }
      assertEquals(ts.isEmpty(), tree.isEmpty());
    }
  }

  @Test
  public void testTreeHeight() {
    for (int n = 1; n <= TEST_SZ; n++) {

      tree.insert(randValue());
      int height = tree.height();

      // RB tree height upper bound:
      // https://en.wikipedia.org/wiki/AVL_tree#Comparison_to_other_structures
      double upperBound = 2 * (Math.log(n + 1) / Math.log(2));

      assertTrue(height <= upperBound);
    }
  }

  static void assertNullChildren(RedBlackTree tree, RedBlackTree.Node... nodes) {
    for (RedBlackTree.Node node : nodes) {
      assertEquals(tree.NIL, node.left);
      assertEquals(tree.NIL, node.right);
    }
  }

  static void assertCorrectParentLinks(
      RedBlackTree tree, RedBlackTree.Node node, RedBlackTree.Node parent) {
    if (node == tree.NIL) return;
    try {
      assertEquals(node.parent, parent);
    } catch (AssertionError e) {
      System.out.println("hi");
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
