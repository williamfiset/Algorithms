/**
 * This file contains an implementation of a Red-Black tree. A RB tree is a special type of binary
 * tree which self balances itself to keep operations logarithmic.
 *
 * <p>Great visualization tool: https://www.cs.usfca.edu/~galles/visualization/RedBlack.html
 *
 * @author nishantc1527
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.datastructures.balancedtree;

import java.awt.*;

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

    public Node(boolean color, T value) {
      this.color = color;
      this.value = value;
    }

    Node(T key, boolean color, Node parent, Node left, Node right) {
      this.value = key;
      this.color = color;

      if (parent == null && left == null && right == null) {
        parent = this;
        left = this;
        right = this;
      }

      this.parent = parent;
      this.left = left;
      this.right = right;
    }

    public boolean getColor() {
      return color;
    }

    public void setColor(boolean color) {
      this.color = color;
    }

    public T getValue() {
      return value;
    }

    public void setValue(T value) {
      this.value = value;
    }

    public Node getLeft() {
      return left;
    }

    public void setLeft(Node left) {
      this.left = left;
    }

    public Node getRight() {
      return right;
    }

    public void setRight(Node right) {
      this.right = right;
    }

    public Node getParent() {
      return parent;
    }

    public void setParent(Node parent) {
      this.parent = parent;
    }
  }

  // The root node of the RB tree.
  public Node root;

  // Tracks the number of nodes inside the tree.
  private int nodeCount = 0;

  public final Node NIL;

  public RedBlackTree() {
    NIL = new Node(BLACK, null);
    NIL.left = NIL;
    NIL.right = NIL;
    NIL.parent = NIL;

    root = NIL;
  }

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

    while (node != NIL) {

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

  public boolean insert(T val) {
    if (val == null) {
      throw new IllegalArgumentException("Red-Black tree does not allow null values.");
    }

    Node x = root, y = NIL;

    while (x != NIL) {
      y = x;

      if (x.getValue().compareTo(val) > 0) {
        x = x.left;
      } else if (x.getValue().compareTo(val) < 0) {
        x = x.right;
      } else {
        return false;
      }
    }

    Node z = new Node(val, RED, y, NIL, NIL);

    if (y == NIL) {
      root = z;
    } else if (z.getValue().compareTo(y.getValue()) < 0) {
      y.left = z;
    } else {
      y.right = z;
    }
    insertFix(z);

    nodeCount++;
    return true;
  }

  private void insertFix(Node z) {
    Node y;
    while (z.parent.color == RED) {
      if (z.parent == z.parent.parent.left) {
        y = z.parent.parent.right;
        if (y.color == RED) {
          z.parent.color = BLACK;
          y.color = BLACK;
          z.parent.parent.color = RED;
          z = z.parent.parent;
        } else {
          if (z == z.parent.right) {
            z = z.parent;
            leftRotate(z);
          }
          z.parent.color = BLACK;
          z.parent.parent.color = RED;
          rightRotate(z.parent.parent);
        }
      } else {
        y = z.parent.parent.left;
        if (y.color == RED) {
          z.parent.color = BLACK;
          y.color = BLACK;
          z.parent.parent.color = RED;
          z = z.parent.parent;
        } else {
          if (z == z.parent.left) {
            z = z.parent;
            rightRotate(z);
          }
          z.parent.color = BLACK;
          z.parent.parent.color = RED;
          leftRotate(z.parent.parent);
        }
      }
    }
    root.setColor(BLACK);
    NIL.setParent(null);
  }

  private void leftRotate(Node x) {
    Node y = x.right;
    x.setRight(y.getLeft());
    if (y.getLeft() != NIL) y.getLeft().setParent(x);
    y.setParent(x.getParent());
    if (x.getParent() == NIL) root = y;
    if (x == x.getParent().getLeft()) x.getParent().setLeft(y);
    else x.getParent().setRight(y);
    y.setLeft(x);
    x.setParent(y);
  }

  private void rightRotate(Node y) {
    Node x = y.left;
    y.left = x.right;
    if (x.right != NIL) x.right.parent = y;
    x.parent = y.parent;
    if (y.parent == NIL) root = x;
    if (y == y.parent.left) y.parent.left = x;
    else y.parent.right = x;
    x.right = y;
    y.parent = x;
  }

  public boolean delete(T key) {
    Node z;
    if (key == null || (z = (search(key, root))) == NIL) return false;
    Node x;
    Node y = z; // temporary reference y
    boolean y_original_color = y.getColor();

    if (z.getLeft() == NIL) {
      x = z.getRight();
      transplant(z, z.getRight());
    } else if (z.getRight() == NIL) {
      x = z.getLeft();
      transplant(z, z.getLeft());
    } else {
      y = successor(z.getRight());
      y_original_color = y.getColor();
      x = y.getRight();
      if (y.getParent() == z) x.setParent(y);
      else {
        transplant(y, y.getRight());
        y.setRight(z.getRight());
        y.getRight().setParent(y);
      }
      transplant(z, y);
      y.setLeft(z.getLeft());
      y.getLeft().setParent(y);
      y.setColor(z.getColor());
    }
    if (y_original_color == BLACK) deleteFix(x);
    nodeCount--;
    return true;
  }

  private void deleteFix(Node x) {
    while (x != root && x.getColor() == BLACK) {
      if (x == x.getParent().getLeft()) {
        Node w = x.getParent().getRight();
        if (w.getColor() == RED) {
          w.setColor(BLACK);
          x.getParent().setColor(RED);
          leftRotate(x.parent);
          w = x.getParent().getRight();
        }
        if (w.getLeft().getColor() == BLACK && w.getRight().getColor() == BLACK) {
          w.setColor(RED);
          x = x.getParent();
          continue;
        } else if (w.getRight().getColor() == BLACK) {
          w.getLeft().setColor(BLACK);
          w.setColor(RED);
          rightRotate(w);
          w = x.getParent().getRight();
        }
        if (w.getRight().getColor() == RED) {
          w.setColor(x.getParent().getColor());
          x.getParent().setColor(BLACK);
          w.getRight().setColor(BLACK);
          leftRotate(x.getParent());
          x = root;
        }
      } else {
        Node w = (x.getParent().getLeft());
        if (w.color == RED) {
          w.color = BLACK;
          x.getParent().setColor(RED);
          rightRotate(x.getParent());
          w = (x.getParent()).getLeft();
        }
        if (w.right.color == BLACK && w.left.color == BLACK) {
          w.color = RED;
          x = x.getParent();
          continue;
        } else if (w.left.color == BLACK) {
          w.right.color = BLACK;
          w.color = RED;
          leftRotate(w);
          w = (x.getParent().getLeft());
        }
        if (w.left.color == RED) {
          w.color = x.getParent().getColor();
          x.getParent().setColor(BLACK);
          w.left.color = BLACK;
          rightRotate(x.getParent());
          x = root;
        }
      }
    }
    x.setColor(BLACK);
  }

  private Node successor(Node root) {
    if (root == NIL || root.left == NIL) return root;
    else return successor(root.left);
  }

  private void transplant(Node u, Node v) {
    if (u.parent == NIL) {
      root = v;
    } else if (u == u.parent.left) {
      u.parent.left = v;
    } else u.parent.right = v;
    v.parent = u.parent;
  }

  private Node search(T val, Node curr) {
    if (curr == NIL) return NIL;
    else if (curr.value.equals(val)) return curr;
    else if (curr.value.compareTo(val) < 0) return search(val, curr.right);
    else return search(val, curr.left);
  }

  public int height() {
    return height(root);
  }

  private int height(Node curr) {
    if (curr == NIL) {
      return 0;
    }
    if (curr.left == NIL && curr.right == NIL) {
      return 1;
    }

    return 1 + Math.max(height(curr.left), height(curr.right));
  }

  private void swapColors(Node a, Node b) {
    boolean tmpColor = a.color;
    a.color = b.color;
    b.color = tmpColor;
  }

  // Sometimes the left or right child node of a parent changes and the
  // parent's reference needs to be updated to point to the new child.
  // This is a helper method to do just that.
  private void updateParentChildLink(Node parent, Node oldChild, Node newChild) {
    if (parent != NIL) {
      if (parent.left == oldChild) {
        parent.left = newChild;
      } else {
        parent.right = newChild;
      }
    }
  }

  // Helper method to find the leftmost node (which has the smallest value)
  private Node findMin(Node node) {
    while (node.left != NIL) node = node.left;
    return node;
  }

  // Helper method to find the rightmost node (which has the largest value)
  private Node findMax(Node node) {
    while (node.right != NIL) node = node.right;
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
        return root != NIL && !stack.isEmpty();
      }

      @Override
      public T next() {

        if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();

        while (trav != NIL && trav.left != NIL) {
          stack.push(trav.left);
          trav = trav.left;
        }

        Node node = stack.pop();

        if (node.right != NIL) {
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
