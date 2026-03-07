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

import java.util.Iterator;
import java.util.Stack;

/**
 * This file contains an implementation of a Red-Black tree. A RB tree is a special type of binary
 * tree which self balances itself to keep operations logarithmic.
 *
 * <p>Great visualization tool: https://www.cs.usfca.edu/~galles/visualization/RedBlack.html
 *
 * @author nishantc1527
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
public class RedBlackTree<T extends Comparable<T>> implements Iterable<T> {

  public static final boolean RED = true;
  public static final boolean BLACK = false;

  public class Node {
    private boolean color = RED;
    private T value;
    private Node left, right, parent;

    public Node(T value, boolean color, Node parent, Node left, Node right) {
      this.value = value;
      this.color = color;
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

  public Node root;
  private int nodeCount = 0;
  public final Node NIL;

  public RedBlackTree() {
    NIL = new Node(null, BLACK, null, null, null);
    NIL.left = NIL;
    NIL.right = NIL;
    NIL.parent = NIL;
    root = NIL;
  }

  public int size() {
    return nodeCount;
  }

  public boolean isEmpty() {
    return size() == 0;
  }

  public boolean contains(T value) {
    return search(value, root) != NIL;
  }

  public boolean insert(T val) {
    if (val == null) {
      throw new IllegalArgumentException("Red-Black tree does not allow null values.");
    }

    Node y = NIL;
    Node x = root;

    while (x != NIL) {
      y = x;
      int cmp = val.compareTo(x.value);
      if (cmp < 0) {
        x = x.left;
      } else if (cmp > 0) {
        x = x.right;
      } else {
        return false; // Value already exists
      }
    }

    Node z = new Node(val, RED, y, NIL, NIL);

    if (y == NIL) {
      root = z;
    } else if (val.compareTo(y.value) < 0) {
      y.left = z;
    } else {
      y.right = z;
    }

    insertFix(z);
    nodeCount++;
    return true;
  }

  private void insertFix(Node z) {
    while (z.parent.color == RED) {
      if (z.parent == z.parent.parent.left) {
        Node y = z.parent.parent.right;
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
        Node y = z.parent.parent.left;
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
    root.color = BLACK;
  }

  private void leftRotate(Node x) {
    Node y = x.right;
    x.right = y.left;
    if (y.left != NIL) {
      y.left.parent = x;
    }
    y.parent = x.parent;
    if (x.parent == NIL) {
      root = y;
    } else if (x == x.parent.left) {
      x.parent.left = y;
    } else {
      x.parent.right = y;
    }
    y.left = x;
    x.parent = y;
  }

  private void rightRotate(Node y) {
    Node x = y.left;
    y.left = x.right;
    if (x.right != NIL) {
      x.right.parent = y;
    }
    x.parent = y.parent;
    if (y.parent == NIL) {
      root = x;
    } else if (y == y.parent.left) {
      y.parent.left = x;
    } else {
      y.parent.right = x;
    }
    x.right = y;
    y.parent = x;
  }

  public boolean delete(T key) {
    if (key == null) return false;
    Node z = search(key, root);
    if (z == NIL) return false;

    Node x;
    Node y = z;
    boolean yOriginalColor = y.color;

    if (z.left == NIL) {
      x = z.right;
      transplant(z, z.right);
    } else if (z.right == NIL) {
      x = z.left;
      transplant(z, z.left);
    } else {
      y = successor(z.right);
      yOriginalColor = y.color;
      x = y.right;
      if (y.parent == z) {
        x.parent = y;
      } else {
        transplant(y, y.right);
        y.right = z.right;
        y.right.parent = y;
      }
      transplant(z, y);
      y.left = z.left;
      y.left.parent = y;
      y.color = z.color;
    }

    if (yOriginalColor == BLACK) {
      deleteFix(x);
    }

    nodeCount--;
    return true;
  }

  private void deleteFix(Node x) {
    while (x != root && x.color == BLACK) {
      if (x == x.parent.left) {
        Node w = x.parent.right;
        if (w.color == RED) {
          w.color = BLACK;
          x.parent.color = RED;
          leftRotate(x.parent);
          w = x.parent.right;
        }
        if (w.left.color == BLACK && w.right.color == BLACK) {
          w.color = RED;
          x = x.parent;
        } else {
          if (w.right.color == BLACK) {
            w.left.color = BLACK;
            w.color = RED;
            rightRotate(w);
            w = x.parent.right;
          }
          w.color = x.parent.color;
          x.parent.color = BLACK;
          w.right.color = BLACK;
          leftRotate(x.parent);
          x = root;
        }
      } else {
        Node w = x.parent.left;
        if (w.color == RED) {
          w.color = BLACK;
          x.parent.color = RED;
          rightRotate(x.parent);
          w = x.parent.left;
        }
        if (w.right.color == BLACK && w.left.color == BLACK) {
          w.color = RED;
          x = x.parent;
        } else {
          if (w.left.color == BLACK) {
            w.right.color = BLACK;
            w.color = RED;
            leftRotate(w);
            w = x.parent.left;
          }
          w.color = x.parent.color;
          x.parent.color = BLACK;
          w.left.color = BLACK;
          rightRotate(x.parent);
          x = root;
        }
      }
    }
    x.color = BLACK;
  }

  private Node successor(Node node) {
    while (node.left != NIL) {
      node = node.left;
    }
    return node;
  }

  private void transplant(Node u, Node v) {
    if (u.parent == NIL) {
      root = v;
    } else if (u == u.parent.left) {
      u.parent.left = v;
    } else {
      u.parent.right = v;
    }
    v.parent = u.parent;
  }

  private Node search(T val, Node curr) {
    while (curr != NIL) {
      int cmp = val.compareTo(curr.value);
      if (cmp < 0) {
        curr = curr.left;
      } else if (cmp > 0) {
        curr = curr.right;
      } else {
        return curr;
      }
    }
    return NIL;
  }

  public int height() {
    return height(root);
  }

  private int height(Node curr) {
    if (curr == NIL) {
      return 0;
    }
    return 1 + Math.max(height(curr.left), height(curr.right));
  }

  @Override
  public Iterator<T> iterator() {
    final int expectedNodeCount = nodeCount;
    final Stack<Node> stack = new Stack<>();
    pushLeftmost(root, stack);

    return new Iterator<T>() {
      @Override
      public boolean hasNext() {
        if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
        return !stack.isEmpty();
      }

      @Override
      public T next() {
        if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
        Node node = stack.pop();
        pushLeftmost(node.right, stack);
        return node.value;
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }

  private void pushLeftmost(Node node, Stack<Node> stack) {
    while (node != NIL) {
      stack.push(node);
      node = node.left;
    }
  }

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
