/**
 * This file contains an implementation of a Red-Black tree. A RB tree is a special type of binary
 * tree which self balances itself to keep operations logarithmic.
 *
 * <p>Great visualization tool: https://www.cs.usfca.edu/~galles/visualization/RedBlack.html
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.datastructures.balancedtree;

public class RedBlackTree<T extends Comparable<T>> implements Iterable<T> {

  public static final boolean RED = true;
  public static final boolean BLACK = false;

  public class Node {

    // The color of this node. By default all nodes start red.
    public boolean color = RED;

    // The value/data contained within the node.
    public T value;

    // The left, right and parent references of this node.
    public Node left, right, parent;

    public Node(T value, Node parent) {
      this.value = value;
      this.parent = parent;
    }
  }

  // The root node of the RB tree.
  public Node root;

  // Tracks the number of nodes inside the tree.
  private int nodeCount = 0;

  // Returns the number of nodes in the tree.
  public int size() {
    return nodeCount;
  }

  // Returns whether or not the tree is empty.
  public boolean isEmpty() {
    return size() == 0;
  }

  public boolean contains(T value) {

    Node node = root;

    if (node == null || value == null) return false;

    while (node != null) {

      // Compare current value to the value in the node.
      int cmp = value.compareTo(node.value);

      // Dig into left subtree.
      if (cmp < 0) node = node.left;

      // Dig into right subtree.
      else if (cmp > 0) node = node.right;

      // Found value in tree.
      else return true;
    }

    return false;
  }

  public boolean insert(T value) {

    if (value == null) throw new IllegalArgumentException();

    // No root node.
    if (root == null) {
      root = new Node(value, null);
      insertionRelabel(root);
      nodeCount++;
      return true;
    }

    for (Node node = root; ; ) {

      int cmp = value.compareTo(node.value);

      // Left subtree.
      if (cmp < 0) {
        if (node.left == null) {
          node.left = new Node(value, node);
          insertionRelabel(node.left);
          nodeCount++;
          return true;
        }
        node = node.left;

        // Right subtree.
      } else if (cmp > 0) {
        if (node.right == null) {
          node.right = new Node(value, node);
          insertionRelabel(node.right);
          nodeCount++;
          return true;
        }
        node = node.right;

        // The value we're trying to insert already exists in the tree.
      } else return false;
    }
  }

  private void insertionRelabel(Node node) {

    Node parent = node.parent;

    // Root node case.
    if (parent == null) {
      node.color = BLACK;
      root = node;
      return;
    }

    Node grandParent = parent.parent;
    if (grandParent == null) return;

    // The red-black tree invariant is already satisfied.
    if (parent.color == BLACK || node.color == BLACK) return;

    boolean nodeIsLeftChild = (parent.left == node);
    boolean parentIsLeftChild = (parent == grandParent.left);
    Node uncle = parentIsLeftChild ? grandParent.right : grandParent.left;
    boolean uncleIsRedNode = (uncle == null) ? BLACK : uncle.color;

    if (uncleIsRedNode) {

      parent.color = BLACK;
      grandParent.color = RED;
      uncle.color = BLACK;

      // At this point the parent node is red and so is the new child node.
      // We need to re-balance somehow because no two red nodes can be
      // adjacent to one another.
    } else {

      // Parent node is a left child.
      if (parentIsLeftChild) {

        // Left-left case.
        if (nodeIsLeftChild) {
          grandParent = leftLeftCase(grandParent);

          // Left-right case.
        } else {
          grandParent = leftRightCase(grandParent);
        }

        // Parent node is a right child.
      } else {

        // Right-left case.
        if (nodeIsLeftChild) {
          grandParent = rightLeftCase(grandParent);

          // Right-right case.
        } else {
          grandParent = rightRightCase(grandParent);
        }
      }
    }

    insertionRelabel(grandParent);
  }

  private void swapColors(Node a, Node b) {
    boolean tmpColor = a.color;
    a.color = b.color;
    b.color = tmpColor;
  }

  private Node leftLeftCase(Node node) {
    node = rightRotate(node);
    swapColors(node, node.right);
    return node;
  }

  private Node leftRightCase(Node node) {
    node.left = leftRotate(node.left);
    return leftLeftCase(node);
  }

  private Node rightRightCase(Node node) {
    node = leftRotate(node);
    swapColors(node, node.left);
    return node;
  }

  private Node rightLeftCase(Node node) {
    node.right = rightRotate(node.right);
    return rightRightCase(node);
  }

  private Node rightRotate(Node parent) {

    Node grandParent = parent.parent;
    Node child = parent.left;

    parent.left = child.right;
    if (child.right != null) child.right.parent = parent;

    child.right = parent;
    parent.parent = child;

    child.parent = grandParent;
    updateParentChildLink(grandParent, parent, child);

    return child;
  }

  private Node leftRotate(Node parent) {

    Node grandParent = parent.parent;
    Node child = parent.right;

    parent.right = child.left;
    if (child.left != null) child.left.parent = parent;

    child.left = parent;
    parent.parent = child;

    child.parent = grandParent;
    updateParentChildLink(grandParent, parent, child);

    return child;
  }

  // Sometimes the left or right child node of a parent changes and the
  // parent's reference needs to be updated to point to the new child.
  // This is a helper method to do just that.
  private void updateParentChildLink(Node parent, Node oldChild, Node newChild) {
    if (parent != null) {
      if (parent.left == oldChild) {
        parent.left = newChild;
      } else {
        parent.right = newChild;
      }
    }
  }

  // Helper method to find the leftmost node (which has the smallest value)
  private Node findMin(Node node) {
    while (node.left != null) node = node.left;
    return node;
  }

  // Helper method to find the rightmost node (which has the largest value)
  private Node findMax(Node node) {
    while (node.right != null) node = node.right;
    return node;
  }

  // Returns as iterator to traverse the tree in order.
  @Override
  public java.util.Iterator<T> iterator() {

    final int expectedNodeCount = nodeCount;
    final java.util.Stack<Node> stack = new java.util.Stack<>();
    stack.push(root);

    return new java.util.Iterator<T>() {
      Node trav = root;

      @Override
      public boolean hasNext() {
        if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
        return root != null && !stack.isEmpty();
      }

      @Override
      public T next() {

        if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();

        while (trav != null && trav.left != null) {
          stack.push(trav.left);
          trav = trav.left;
        }

        Node node = stack.pop();

        if (node.right != null) {
          stack.push(node.right);
          trav = node.right;
        }

        return node.value;
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }

  // Example usage of RB tree:
  public static void main(String[] args) {

    int[] values = {5, 8, 1, -4, 6, -2, 0, 7};
    RedBlackTree<Integer> rbTree = new RedBlackTree<>();
    for (int v : values) rbTree.insert(v);

    System.out.printf("RB tree contains %d: %s\n", 6, rbTree.contains(6));
    System.out.printf("RB tree contains %d: %s\n", -5, rbTree.contains(-5));
    System.out.printf("RB tree contains %d: %s\n", 1, rbTree.contains(1));
    System.out.printf("RB tree contains %d: %s\n", 99, rbTree.contains(99));
  }
}
