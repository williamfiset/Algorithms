package com.williamfiset.algorithms.datastructures.balancedtree;

import static com.google.common.truth.Truth.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;
import org.junit.Before;
import org.junit.Test;

public class TreapTreeTest {

  static final int MAX_RAND_NUM = +100000;
  static final int MIN_RAND_NUM = -100000;

  static final int TEST_SZ = 500;

  private TreapTree<Integer> tree;

  @Before
  public void setup() {
    tree = new TreapTree<>();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullInsertion() {
    tree.insert(null);
  }

  @Test
  public void testTreeContainsNull() {
    assertThat(tree.contains(null)).isFalse();
  }

  @Test
  public void LeftLeftCase() {
    tree.insert(15, 15);
    tree.insert(10, 8);
    tree.insert(20, 10);
    tree.insert(30, 9);

    assertThat(tree.root.left.getValue()).isEqualTo(10);
    assertThat(tree.root.getValue()).isEqualTo(15);
    assertThat(tree.root.right.getValue()).isEqualTo(20);
    assertThat(tree.root.right.right.getValue()).isEqualTo(30);

    tree.insert(32, 14);

    assertThat(tree.root.left.getValue()).isEqualTo(10);
    assertThat(tree.root.getValue()).isEqualTo(15);
    assertThat(tree.root.right.getValue()).isEqualTo(32);
    assertThat(tree.root.right.left.getValue()).isEqualTo(20);
    assertThat(tree.root.right.left.right.getValue()).isEqualTo(30);

    assertThat(tree.root.right.left.right.left).isNull();
    assertThat(tree.root.right.left.right.right).isNull();
    assertThat(tree.root.right.left.left).isNull();
    assertThat(tree.root.right.right).isNull();
    assertThat(tree.root.left.left).isNull();
    assertThat(tree.root.left.right).isNull();
  }

  @Test
  public void testLeftRightCase() {
    tree.insert(20, 10);
    tree.insert(17, 5);
    tree.insert(26, 7);

    assertThat(tree.root.getValue()).isEqualTo(20);
    assertThat(tree.root.left.getValue()).isEqualTo(17);
    assertThat(tree.root.right.getValue()).isEqualTo(26);

    tree.insert(18, 15);
    assertThat(tree.root.getValue()).isEqualTo(18);
    assertThat(tree.root.left.getValue()).isEqualTo(17);
    assertThat(tree.root.right.getValue()).isEqualTo(20);
    assertThat(tree.root.right.right.getValue()).isEqualTo(26);

    assertThat(tree.root.left.left).isNull();
    assertThat(tree.root.left.right).isNull();
    assertThat(tree.root.right.left).isNull();
    assertThat(tree.root.right.right.left).isNull();
    assertThat(tree.root.right.right.right).isNull();
  }

  @Test
  public void testRightRightCase() {
    tree.insert(10, 2);
    tree.insert(8, 1);

    assertThat(tree.root.getValue()).isEqualTo(10);
    assertThat(tree.root.left.getValue()).isEqualTo(8);

    tree.insert(7, 3);

    assertThat(tree.root.getValue()).isEqualTo(7);
    assertThat(tree.root.right.getValue()).isEqualTo(10);
    assertThat(tree.root.right.left.getValue()).isEqualTo(8);

    assertThat(tree.root.left).isNull();
    assertThat(tree.root.right.right).isNull();
    assertThat(tree.root.right.left.right).isNull();
    assertThat(tree.root.right.left.left).isNull();
  }

  @Test
  public void testRightLeftCase() {
    tree.insert(15, 10);
    tree.insert(16, 8);

    assertThat(tree.root.getValue()).isEqualTo(15);
    assertThat(tree.root.right.getValue()).isEqualTo(16);

    tree.insert(13, 11);

    assertThat(tree.root.getValue()).isEqualTo(13);
    assertThat(tree.root.right.getValue()).isEqualTo(15);
    assertThat(tree.root.right.right.getValue()).isEqualTo(16);

    assertThat(tree.root.left).isNull();
    assertThat(tree.root.right.left).isNull();
    assertThat(tree.root.right.right.left).isNull();
    assertThat(tree.root.right.right.right).isNull();
  }

  @Test
  public void randomTreapOperations() {
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
