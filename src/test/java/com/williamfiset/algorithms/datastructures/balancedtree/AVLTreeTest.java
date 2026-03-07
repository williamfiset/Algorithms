package com.williamfiset.algorithms.datastructures.balancedtree;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import org.junit.jupiter.api.*;

public class AVLTreeTest {

  static final int MAX_RAND_NUM = +100000;
  static final int MIN_RAND_NUM = -100000;

  static final int TEST_SZ = 2500;

  private AVLTreeRecursive<Integer> tree;

  @BeforeEach
  public void setup() {
    tree = new AVLTreeRecursive<>();
  }

  @Test
  public void testNullInsertion() {
    assertThat(tree.insert(null)).isFalse();
  }

  @Test
  public void testNullRemoval() {
    assertThat(tree.remove(null)).isFalse();
  }

  @Test
  public void testTreeContainsNull() {
    assertThat(tree.contains(null)).isFalse();
  }

  @Test
  public void testEmptyTree() {
    assertThat(tree.isEmpty()).isTrue();
    assertThat(tree.size()).isEqualTo(0);
    assertThat(tree.height()).isEqualTo(0);
    assertThat(tree.contains(1)).isFalse();
  }

  @Test
  public void testSingleElement() {
    tree.insert(5);
    assertThat(tree.isEmpty()).isFalse();
    assertThat(tree.size()).isEqualTo(1);
    assertThat(tree.height()).isEqualTo(0);
    assertThat(tree.contains(5)).isTrue();
    assertThat(tree.contains(4)).isFalse();
  }

  @Test
  public void testDuplicateInsertion() {
    assertThat(tree.insert(5)).isTrue();
    assertThat(tree.insert(5)).isFalse();
    assertThat(tree.size()).isEqualTo(1);
  }

  @Test
  public void testRemoveNonExistentElement() {
    tree.insert(5);
    assertThat(tree.remove(99)).isFalse();
    assertThat(tree.size()).isEqualTo(1);
  }

  @Test
  public void testRemoveFromEmptyTree() {
    assertThat(tree.remove(1)).isFalse();
  }

  @Test
  public void testLeftLeftCase() {

    tree.insert(3);
    tree.insert(2);
    tree.insert(1);

    assertThat(tree.root.value.intValue()).isEqualTo(2);
    assertThat(tree.root.left.value.intValue()).isEqualTo(1);
    assertThat(tree.root.right.value.intValue()).isEqualTo(3);

    assertThat(tree.root.left.left).isNull();
    assertThat(tree.root.left.right).isNull();
    assertThat(tree.root.right.left).isNull();
    assertThat(tree.root.right.right).isNull();
  }

  @Test
  public void testLeftRightCase() {

    tree.insert(3);
    tree.insert(1);
    tree.insert(2);

    assertThat(tree.root.value.intValue()).isEqualTo(2);
    assertThat(tree.root.left.value.intValue()).isEqualTo(1);
    assertThat(tree.root.right.value.intValue()).isEqualTo(3);

    assertThat(tree.root.left.left).isNull();
    assertThat(tree.root.left.right).isNull();
    assertThat(tree.root.right.left).isNull();
    assertThat(tree.root.right.right).isNull();
  }

  @Test
  public void testRightRightCase() {

    tree.insert(1);
    tree.insert(2);
    tree.insert(3);

    assertThat(tree.root.value.intValue()).isEqualTo(2);
    assertThat(tree.root.left.value.intValue()).isEqualTo(1);
    assertThat(tree.root.right.value.intValue()).isEqualTo(3);

    assertThat(tree.root.left.left).isNull();
    assertThat(tree.root.left.right).isNull();
    assertThat(tree.root.right.left).isNull();
    assertThat(tree.root.right.right).isNull();
  }

  @Test
  public void testRightLeftCase() {

    tree.insert(1);
    tree.insert(3);
    tree.insert(2);

    assertThat(tree.root.value.intValue()).isEqualTo(2);
    assertThat(tree.root.left.value.intValue()).isEqualTo(1);
    assertThat(tree.root.right.value.intValue()).isEqualTo(3);

    assertThat(tree.root.left.left).isNull();
    assertThat(tree.root.left.right).isNull();
    assertThat(tree.root.right.left).isNull();
    assertThat(tree.root.right.right).isNull();
  }

  @Test
  public void testRemoveLeafNode() {
    tree.insert(2);
    tree.insert(1);
    tree.insert(3);

    assertThat(tree.remove(1)).isTrue();
    assertThat(tree.size()).isEqualTo(2);
    assertThat(tree.contains(1)).isFalse();
    assertThat(tree.validateBSTInvariant(tree.root)).isTrue();
  }

  @Test
  public void testRemoveNodeWithOneChild() {
    tree.insert(2);
    tree.insert(1);
    tree.insert(3);
    tree.insert(4);

    assertThat(tree.remove(3)).isTrue();
    assertThat(tree.size()).isEqualTo(3);
    assertThat(tree.contains(3)).isFalse();
    assertThat(tree.contains(4)).isTrue();
    assertThat(tree.validateBSTInvariant(tree.root)).isTrue();
  }

  @Test
  public void testRemoveNodeWithTwoChildren() {
    tree.insert(3);
    tree.insert(1);
    tree.insert(5);
    tree.insert(2);
    tree.insert(4);

    assertThat(tree.remove(3)).isTrue();
    assertThat(tree.size()).isEqualTo(4);
    assertThat(tree.contains(3)).isFalse();
    assertThat(tree.validateBSTInvariant(tree.root)).isTrue();
    assertThat(validateBalanceFactorValues(tree.root)).isTrue();
  }

  @Test
  public void testRemoveRoot() {
    tree.insert(5);
    assertThat(tree.remove(5)).isTrue();
    assertThat(tree.isEmpty()).isTrue();
    assertThat(tree.root).isNull();
  }

  @Test
  public void testInsertAndRemoveAll() {
    for (int i = 0; i < 10; i++) tree.insert(i);
    for (int i = 0; i < 10; i++) {
      assertThat(tree.remove(i)).isTrue();
      assertThat(tree.contains(i)).isFalse();
    }
    assertThat(tree.isEmpty()).isTrue();
  }

  @Test
  public void testRandomizedBalanceFactorTest() {
    for (int i = 0; i < TEST_SZ; i++) {
      tree.insert(randValue());
      assertThat(validateBalanceFactorValues(tree.root)).isTrue();
    }
  }

  @Test
  public void testBalanceFactorAfterRemovals() {
    List<Integer> values = genRandList(500);
    for (int v : values) tree.insert(v);
    Collections.shuffle(values);
    for (int v : values) {
      tree.remove(v);
      assertThat(validateBalanceFactorValues(tree.root)).isTrue();
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

      assertThat(tree.insert(v)).isEqualTo(set.add(v));
      assertThat(tree.size()).isEqualTo(set.size());
      assertThat(tree.validateBSTInvariant(tree.root)).isTrue();
    }
  }

  @Test
  public void testBSTInvariantAfterRemovals() {
    List<Integer> values = genRandList(500);
    for (int v : values) tree.insert(v);
    Collections.shuffle(values);
    for (int v : values) {
      tree.remove(v);
      assertThat(tree.validateBSTInvariant(tree.root)).isTrue();
    }
  }

  @Test
  public void testTreeHeight() {
    for (int n = 1; n <= TEST_SZ; n++) {

      tree.insert(randValue());
      double height = tree.height();

      // Get an upper bound on what the maximum height of
      // an AVL tree should be. Values were taken from:
      // https://en.wikipedia.org/wiki/AVL_tree#Comparison_to_other_structures
      double c = 1.441;
      double b = -0.329;
      double upperBound = c * (Math.log(n + 2.0) / Math.log(2)) + b;

      assertThat(height).isLessThan(upperBound);
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

        assertThat(tree.remove(value)).isEqualTo(ts.remove(value));
        assertThat(tree.contains(value)).isFalse();
        assertThat(tree.size()).isEqualTo(size - j - 1);
      }

      assertThat(tree.isEmpty()).isTrue();
    }
  }

  @Test
  public void testIteratorInOrder() {
    int[] values = {5, 3, 7, 1, 4, 6, 8};
    for (int v : values) tree.insert(v);

    List<Integer> result = new ArrayList<>();
    for (int v : tree) result.add(v);

    assertThat(result).containsExactly(1, 3, 4, 5, 6, 7, 8).inOrder();
  }

  @Test
  public void testIteratorOnEmptyTree() {
    Iterator<Integer> it = tree.iterator();
    assertThat(it.hasNext()).isFalse();
  }

  @Test
  public void testIteratorOnSingleElement() {
    tree.insert(42);
    Iterator<Integer> it = tree.iterator();
    assertThat(it.hasNext()).isTrue();
    assertThat(it.next()).isEqualTo(42);
    assertThat(it.hasNext()).isFalse();
  }

  @Test
  public void testIteratorConcurrentModificationOnInsert() {
    tree.insert(1);
    tree.insert(2);
    tree.insert(3);

    Iterator<Integer> it = tree.iterator();
    tree.insert(4);

    assertThrows(ConcurrentModificationException.class, it::hasNext);
  }

  @Test
  public void testIteratorConcurrentModificationOnRemove() {
    tree.insert(1);
    tree.insert(2);
    tree.insert(3);

    Iterator<Integer> it = tree.iterator();
    tree.remove(2);

    assertThrows(ConcurrentModificationException.class, it::next);
  }

  @Test
  public void testIteratorRemoveUnsupported() {
    tree.insert(1);
    Iterator<Integer> it = tree.iterator();
    assertThrows(UnsupportedOperationException.class, it::remove);
  }

  @Test
  public void testToString() {
    tree.insert(2);
    tree.insert(1);
    tree.insert(3);
    // Just verify it doesn't throw and returns non-empty output.
    assertThat(tree.toString()).isNotEmpty();
  }

  @Test
  public void testContainsAfterRemoval() {
    tree.insert(1);
    tree.insert(2);
    tree.insert(3);

    assertThat(tree.contains(2)).isTrue();
    tree.remove(2);
    assertThat(tree.contains(2)).isFalse();
    assertThat(tree.contains(1)).isTrue();
    assertThat(tree.contains(3)).isTrue();
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
