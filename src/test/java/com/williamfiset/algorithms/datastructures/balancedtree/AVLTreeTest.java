package com.williamfiset.algorithms.datastructures.balancedtree;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;
import org.junit.Before;
import org.junit.Test;

public class AVLTreeTest {

  static final int MAX_RAND_NUM = +100000;
  static final int MIN_RAND_NUM = -100000;

  static final int TEST_SZ = 2500;

  private AVLTreeRecursive<Integer> tree;

  @Before
  public void setup() {
    tree = new AVLTreeRecursive<>();
  }

  @Test
  public void testNullInsertion() {
    assertFalse(tree.insert(null));
  }

  @Test
  public void testNullRemoval() {
    assertFalse(tree.remove(null));
  }

  @Test
  public void testTreeContainsNull() {
    assertFalse(tree.contains(null));
  }

  @Test
  public void testLeftLeftCase() {

    tree.insert(3);
    tree.insert(2);
    tree.insert(1);

    assertEquals(2, tree.root.value.intValue());
    assertEquals(1, tree.root.left.value.intValue());
    assertEquals(3, tree.root.right.value.intValue());

    assertNull(tree.root.left.left);
    assertNull(tree.root.left.right);
    assertNull(tree.root.right.left);
    assertNull(tree.root.right.right);
  }

  @Test
  public void testLeftRightCase() {

    tree.insert(3);
    tree.insert(1);
    tree.insert(2);

    assertEquals(2, tree.root.value.intValue());
    assertEquals(1, tree.root.left.value.intValue());
    assertEquals(3, tree.root.right.value.intValue());

    assertNull(tree.root.left.left);
    assertNull(tree.root.left.right);
    assertNull(tree.root.right.left);
    assertNull(tree.root.right.right);
  }

  @Test
  public void testRightRightCase() {

    tree.insert(1);
    tree.insert(2);
    tree.insert(3);

    assertEquals(2, tree.root.value.intValue());
    assertEquals(1, tree.root.left.value.intValue());
    assertEquals(3, tree.root.right.value.intValue());

    assertNull(tree.root.left.left);
    assertNull(tree.root.left.right);
    assertNull(tree.root.right.left);
    assertNull(tree.root.right.right);
  }

  @Test
  public void testRightLeftCase() {

    tree.insert(1);
    tree.insert(3);
    tree.insert(2);

    assertEquals(2, tree.root.value.intValue());
    assertEquals(1, tree.root.left.value.intValue());
    assertEquals(3, tree.root.right.value.intValue());

    assertNull(tree.root.left.left);
    assertNull(tree.root.left.right);
    assertNull(tree.root.right.left);
    assertNull(tree.root.right.right);
  }

  @Test
  public void testRandomizedBalanceFactorTest() {
    for (int i = 0; i < TEST_SZ; i++) {
      tree.insert(randValue());
      assertTrue(validateBalanceFactorValues(tree.root));
    }
  }

  // Make sure all balance factor values are either -1, 0 or +1
  static boolean validateBalanceFactorValues(AVLTreeRecursive<Integer>.Node node) {
    if (node == null) return true;
    if (node.bf > +1 || node.bf < -1) return false;
    return validateBalanceFactorValues(node.left) && validateBalanceFactorValues(node.right);
  }

  @Test
  public void testRandomizedValueInsertionsAgainstTreeSet() {

    TreeSet<Integer> set = new TreeSet<>();
    for (int i = 0; i < TEST_SZ; i++) {
      int v = randValue();
      assertEquals(set.add(v), tree.insert(v));
      assertEquals(set.size(), tree.size());
      assertTrue(tree.validateBSTInvarient(tree.root));
    }
  }

  @Test
  public void testTreeHeight() {
    for (int n = 1; n <= TEST_SZ; n++) {

      tree.insert(randValue());
      int height = tree.height();

      // Get an upper bound on what the maximum height of
      // an AVL tree should be. Values were taken from:
      // https://en.wikipedia.org/wiki/AVL_tree#Comparison_to_other_structures
      double c = 1.441;
      double b = -0.329;
      double upperBound = c * (Math.log(n + 2.0) / Math.log(2)) + b;

      assertTrue(height < upperBound);
    }
  }

  @Test
  public void randomRemoveTests() {
    TreeSet<Integer> ts = new TreeSet<>();
    for (int i = 0; i < TEST_SZ; i++) {

      int size = i;
      List<Integer> lst = genRandList(size);
      for (Integer value : lst) {
        tree.insert(value);
        ts.add(value);
      }
      Collections.shuffle(lst);

      // Remove all the elements we just placed in the tree.
      for (int j = 0; j < size; j++) {

        Integer value = lst.get(j);

        assertEquals(ts.remove(value), tree.remove(value));
        assertFalse(tree.contains(value));
        assertEquals(size - j - 1, tree.size());
      }

      assertTrue(tree.isEmpty());
    }
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
