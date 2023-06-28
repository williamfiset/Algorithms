package com.williamfiset.algorithms.datastructures.binarysearchtree;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import org.junit.jupiter.api.*;

class TestTreeNode {

  Integer data;
  TestTreeNode left, right;

  public TestTreeNode(Integer data, TestTreeNode l, TestTreeNode r) {
    this.data = data;
    this.right = r;
    this.left = l;
  }

  static TestTreeNode add(TestTreeNode node, int data) {

    if (node == null) {
      node = new TestTreeNode(data, null, null);
    } else {
      // Place lower elem values on left
      if (data < node.data) {
        node.left = add(node.left, data);
      } else {
        node.right = add(node.right, data);
      }
    }
    return node;
  }

  static void preOrder(List<Integer> lst, TestTreeNode node) {

    if (node == null) return;

    lst.add(node.data);
    if (node.left != null) preOrder(lst, node.left);
    if (node.right != null) preOrder(lst, node.right);
  }

  static void inOrder(List<Integer> lst, TestTreeNode node) {

    if (node == null) return;

    if (node.left != null) inOrder(lst, node.left);
    lst.add(node.data);
    if (node.right != null) inOrder(lst, node.right);
  }

  static void postOrder(List<Integer> lst, TestTreeNode node) {

    if (node == null) return;

    if (node.left != null) postOrder(lst, node.left);
    if (node.right != null) postOrder(lst, node.right);
    lst.add(node.data);
  }

  static void levelOrder(List<Integer> lst, TestTreeNode node) {

    Deque<TestTreeNode> q = new ArrayDeque<>();
    if (node != null) q.offer(node);

    while (!q.isEmpty()) {

      node = q.poll();
      lst.add(node.data);
      if (node.left != null) q.offer(node.left);
      if (node.right != null) q.offer(node.right);
    }
  }
}

public class BinarySearchTreeTest {

  static final int LOOPS = 100;

  @BeforeEach
  public void setup() {}

  @Test
  public void testIsEmpty() {

    BinarySearchTree<String> tree = new BinarySearchTree<>();
    assertThat(tree.isEmpty()).isTrue();

    tree.add("Hello World!");
    assertThat(tree.isEmpty()).isFalse();
  }

  @Test
  public void testSize() {
    BinarySearchTree<String> tree = new BinarySearchTree<>();
    assertThat(tree.size()).isEqualTo(0);

    tree.add("Hello World!");
    assertThat(tree.size()).isEqualTo(1);
  }

  @Test
  public void testHeight() {
    BinarySearchTree<String> tree = new BinarySearchTree<>();

    // Tree should look like:
    //        M
    //      J  S
    //    B   N Z
    //  A

    // No tree
    assertThat(tree.height()).isEqualTo(0);

    // Layer One
    tree.add("M");
    assertThat(tree.height()).isEqualTo(1);

    // Layer Two
    tree.add("J");
    assertThat(tree.height()).isEqualTo(2);
    tree.add("S");
    assertThat(tree.height()).isEqualTo(2);

    // Layer Three
    tree.add("B");
    assertThat(tree.height()).isEqualTo(3);
    tree.add("N");
    assertThat(tree.height()).isEqualTo(3);
    tree.add("Z");
    assertThat(tree.height()).isEqualTo(3);

    // Layer 4
    tree.add("A");
    assertThat(tree.height()).isEqualTo(4);
  }

  @Test
  public void testAdd() {

    // Add element which does not yet exist
    BinarySearchTree<Character> tree = new BinarySearchTree<>();
    assertThat(tree.add('A')).isTrue();

    // Add duplicate element
    assertThat(tree.add('A')).isFalse();

    // Add a second element which is not a duplicate
    assertThat(tree.add('B')).isTrue();
  }

  @Test
  public void testRemove() {

    // Try removing an element which doesn't exist
    BinarySearchTree<Character> tree = new BinarySearchTree<>();
    tree.add('A');
    assertThat(tree.size()).isEqualTo(1);
    assertThat(tree.remove('B')).isFalse();
    assertThat(tree.size()).isEqualTo(1);

    // Try removing an element which does exist
    tree.add('B');
    assertThat(tree.size()).isEqualTo(2);
    assertThat(tree.remove('B')).isTrue();
    assertThat(tree.size()).isEqualTo(1);
    assertThat(tree.height()).isEqualTo(1);

    // Try removing the root
    assertThat(tree.remove('A')).isTrue();
    assertThat(tree.size()).isEqualTo(0);
    assertThat(tree.height()).isEqualTo(0);
  }

  @Test
  public void testContains() {

    // Setup tree
    BinarySearchTree<Character> tree = new BinarySearchTree<>();

    tree.add('B');
    tree.add('A');
    tree.add('C');

    // Try looking for an element which doesn't exist
    assertThat(tree.contains('D')).isFalse();

    // Try looking for an element which exists in the root
    assertThat(tree.contains('B')).isTrue();

    // Try looking for an element which exists as the left child of the root
    assertThat(tree.contains('A')).isTrue();

    // Try looking for an element which exists as the right child of the root
    assertThat(tree.contains('C')).isTrue();
  }

  @Test
  public void concurrentModificationErrorPreOrder() {

    BinarySearchTree<Integer> bst = new BinarySearchTree<>();

    bst.add(1);
    bst.add(2);
    bst.add(3);

    Iterator<Integer> iter = bst.traverse(TreeTraversalOrder.PRE_ORDER);

    assertThrows(
        ConcurrentModificationException.class,
        () -> {
          while (iter.hasNext()) {
            bst.add(0);
            iter.next();
          }
        });
  }

  @Test
  public void concurrentModificationErrorInOrderOrder() {

    BinarySearchTree<Integer> bst = new BinarySearchTree<>();

    bst.add(1);
    bst.add(2);
    bst.add(3);

    Iterator<Integer> iter = bst.traverse(TreeTraversalOrder.IN_ORDER);

    assertThrows(
        ConcurrentModificationException.class,
        () -> {
          while (iter.hasNext()) {
            bst.add(0);
            iter.next();
          }
        });
  }

  @Test
  public void concurrentModificationErrorPostOrder() {

    BinarySearchTree<Integer> bst = new BinarySearchTree<>();

    bst.add(1);
    bst.add(2);
    bst.add(3);

    Iterator<Integer> iter = bst.traverse(TreeTraversalOrder.POST_ORDER);

    assertThrows(
        ConcurrentModificationException.class,
        () -> {
          while (iter.hasNext()) {
            bst.add(0);
            iter.next();
          }
        });
  }

  @Test
  public void concurrentModificationErrorLevelOrder() {

    BinarySearchTree<Integer> bst = new BinarySearchTree<>();

    bst.add(1);
    bst.add(2);
    bst.add(3);

    Iterator<Integer> iter = bst.traverse(TreeTraversalOrder.LEVEL_ORDER);

    assertThrows(
        ConcurrentModificationException.class,
        () -> {
          while (iter.hasNext()) {
            bst.add(0);
            iter.next();
          }
        });
  }

  @Test
  public void concurrentModificationErrorRemovingPreOrder() {

    BinarySearchTree<Integer> bst = new BinarySearchTree<>();

    bst.add(1);
    bst.add(2);
    bst.add(3);

    Iterator<Integer> iter = bst.traverse(TreeTraversalOrder.PRE_ORDER);

    assertThrows(
        ConcurrentModificationException.class,
        () -> {
          while (iter.hasNext()) {
            bst.remove(2);
            iter.next();
          }
        });
  }

  @Test
  public void concurrentModificationErrorRemovingInOrderOrder() {

    BinarySearchTree<Integer> bst = new BinarySearchTree<>();

    bst.add(1);
    bst.add(2);
    bst.add(3);

    Iterator<Integer> iter = bst.traverse(TreeTraversalOrder.IN_ORDER);

    assertThrows(
        ConcurrentModificationException.class,
        () -> {
          while (iter.hasNext()) {
            bst.remove(2);
            iter.next();
          }
        });
  }

  @Test
  public void concurrentModificationErrorRemovingPostOrder() {

    BinarySearchTree<Integer> bst = new BinarySearchTree<>();

    bst.add(1);
    bst.add(2);
    bst.add(3);

    Iterator<Integer> iter = bst.traverse(TreeTraversalOrder.POST_ORDER);

    assertThrows(
        ConcurrentModificationException.class,
        () -> {
          while (iter.hasNext()) {
            bst.remove(2);
            iter.next();
          }
        });
  }

  @Test
  public void concurrentModificationErrorRemovingLevelOrder() {

    BinarySearchTree<Integer> bst = new BinarySearchTree<>();

    bst.add(1);
    bst.add(2);
    bst.add(3);

    Iterator<Integer> iter = bst.traverse(TreeTraversalOrder.LEVEL_ORDER);

    assertThrows(
        ConcurrentModificationException.class,
        () -> {
          while (iter.hasNext()) {
            bst.remove(2);
            iter.next();
          }
        });
  }

  @Test
  public void randomRemoveTests() {

    for (int i = 0; i < LOOPS; i++) {

      int size = i;
      BinarySearchTree<Integer> tree = new BinarySearchTree<>();
      List<Integer> lst = genRandList(size);
      for (Integer value : lst) tree.add(value);

      Collections.shuffle(lst);
      // Remove all the elements we just placed in the tree
      for (int j = 0; j < size; j++) {

        Integer value = lst.get(j);

        assertThat(tree.remove(value)).isTrue();
        assertThat(tree.contains(value)).isFalse();
        assertThat(tree.size()).isEqualTo(size - j - 1);
      }

      assertThat(tree.isEmpty()).isTrue();
    }
  }

  static List<Integer> genRandList(int sz) {
    List<Integer> lst = new ArrayList<>(sz);
    for (int i = 0; i < sz; i++) lst.add(i);
    Collections.shuffle(lst);
    return lst;
  }

  public boolean validateTreeTraversal(TreeTraversalOrder trav_order, List<Integer> input) {

    List<Integer> out = new ArrayList<>();
    List<Integer> expected = new ArrayList<>();

    TestTreeNode testTree = null;
    BinarySearchTree<Integer> tree = new BinarySearchTree<>();

    // Construct Binary Tree and test tree
    for (Integer value : input) {
      testTree = TestTreeNode.add(testTree, value);
      tree.add(value);
    }

    // Generate the expected output for the particular traversal
    switch (trav_order) {
      case PRE_ORDER:
        TestTreeNode.preOrder(expected, testTree);
        break;
      case IN_ORDER:
        TestTreeNode.inOrder(expected, testTree);
        break;
      case POST_ORDER:
        TestTreeNode.postOrder(expected, testTree);
        break;
      case LEVEL_ORDER:
        TestTreeNode.levelOrder(expected, testTree);
        break;
    }

    // Get traversal output
    Iterator<Integer> iter = tree.traverse(trav_order);
    while (iter.hasNext()) out.add(iter.next());

    // The output and the expected size better be the same size
    if (out.size() != expected.size()) return false;

    // Compare output to expected
    for (int i = 0; i < out.size(); i++) if (!expected.get(i).equals(out.get(i))) return false;

    return true;
  }

  @Test
  public void testPreOrderTraversal() {

    for (int i = 0; i < LOOPS; i++) {
      List<Integer> input = genRandList(i);
      assertThat(validateTreeTraversal(TreeTraversalOrder.PRE_ORDER, input)).isTrue();
    }
  }

  @Test
  public void testInOrderTraversal() {

    for (int i = 0; i < LOOPS; i++) {
      List<Integer> input = genRandList(i);
      assertThat(validateTreeTraversal(TreeTraversalOrder.IN_ORDER, input)).isTrue();
    }
  }

  @Test
  public void testPostOrderTraversal() {

    for (int i = 0; i < LOOPS; i++) {
      List<Integer> input = genRandList(i);
      assertThat(validateTreeTraversal(TreeTraversalOrder.POST_ORDER, input)).isTrue();
    }
  }

  @Test
  public void testLevelOrderTraversal() {

    for (int i = 0; i < LOOPS; i++) {
      List<Integer> input = genRandList(i);
      assertThat(validateTreeTraversal(TreeTraversalOrder.LEVEL_ORDER, input)).isTrue();
    }
  }
}
