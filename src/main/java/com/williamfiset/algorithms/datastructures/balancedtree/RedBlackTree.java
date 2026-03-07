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

import java.util.*;

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

    public boolean getColor() { return color; }
    public T getValue() { return value; }
    public Node getLeft() { return left; }
    public Node getRight() { return right; }
    public Node getParent() { return parent; }
  }

  public Node root;
  private int nodeCount = 0;
  public final Node NIL;

  public RedBlackTree() {
    NIL = new Node(null, BLACK, null, null, null);
    NIL.left = NIL.right = NIL.parent = NIL;
    root = NIL;
  }

  public int size() { return nodeCount; }
  public boolean isEmpty() { return nodeCount == 0; }
  public boolean contains(T value) { return search(value) != NIL; }

  public boolean insert(T val) {
    if (val == null) throw new IllegalArgumentException("Null values not allowed.");
    Node y = NIL, x = root;
    while (x != NIL) {
      y = x;
      int cmp = val.compareTo(x.value);
      if (cmp < 0) x = x.left;
      else if (cmp > 0) x = x.right;
      else return false;
    }
    Node z = new Node(val, RED, y, NIL, NIL);
    if (y == NIL) root = z;
    else if (val.compareTo(y.value) < 0) y.left = z;
    else y.right = z;
    insertFix(z);
    nodeCount++;
    return true;
  }

  private void insertFix(Node z) {
    while (z.parent.color == RED) {
      boolean isLeft = (z.parent == z.parent.parent.left);
      Node u = isLeft ? z.parent.parent.right : z.parent.parent.left;
      if (u.color == RED) {
        z.parent.color = u.color = BLACK;
        z.parent.parent.color = RED;
        z = z.parent.parent;
      } else {
        if (z == (isLeft ? z.parent.right : z.parent.left)) {
          z = z.parent;
          rotate(z, isLeft);
        }
        z.parent.color = BLACK;
        z.parent.parent.color = RED;
        rotate(z.parent.parent, !isLeft);
      }
    }
    root.color = BLACK;
  }

  public boolean delete(T key) {
    Node z = search(key);
    if (z == NIL) return false;
    Node y = z, x;
    boolean yOriginalColor = y.color;
    if (z.left == NIL) transplant(z, x = z.right);
    else if (z.right == NIL) transplant(z, x = z.left);
    else {
      y = successor(z.right);
      yOriginalColor = y.color;
      x = y.right;
      if (y.parent == z) x.parent = y;
      else {
        transplant(y, y.right);
        y.right = z.right;
        y.right.parent = y;
      }
      transplant(z, y);
      y.left = z.left;
      y.left.parent = y;
      y.color = z.color;
    }
    if (yOriginalColor == BLACK) deleteFix(x);
    nodeCount--;
    return true;
  }

  private void deleteFix(Node x) {
    while (x != root && x.color == BLACK) {
      boolean isLeft = (x == x.parent.left);
      Node w = isLeft ? x.parent.right : x.parent.left;
      if (w.color == RED) {
        w.color = BLACK;
        x.parent.color = RED;
        rotate(x.parent, isLeft);
        w = isLeft ? x.parent.right : x.parent.left;
      }
      if (w.left.color == BLACK && w.right.color == BLACK) {
        w.color = RED;
        x = x.parent;
      } else {
        if ((isLeft ? w.right : w.left).color == BLACK) {
          (isLeft ? w.left : w.right).color = BLACK;
          w.color = RED;
          rotate(w, !isLeft);
          w = isLeft ? x.parent.right : x.parent.left;
        }
        w.color = x.parent.color;
        x.parent.color = BLACK;
        (isLeft ? w.right : w.left).color = BLACK;
        rotate(x.parent, isLeft);
        x = root;
      }
    }
    x.color = BLACK;
  }

  private void rotate(Node x, boolean left) {
    Node y = left ? x.right : x.left;
    if (left) {
      x.right = y.left;
      if (y.left != NIL) y.left.parent = x;
    } else {
      x.left = y.right;
      if (y.right != NIL) y.right.parent = x;
    }
    y.parent = x.parent;
    if (x.parent == NIL) root = y;
    else if (x == x.parent.left) x.parent.left = y;
    else x.parent.right = y;
    if (left) y.left = x; else y.right = x;
    x.parent = y;
  }

  private void transplant(Node u, Node v) {
    if (u.parent == NIL) root = v;
    else if (u == u.parent.left) u.parent.left = v;
    else u.parent.right = v;
    v.parent = u.parent;
  }

  private Node successor(Node n) {
    while (n.left != NIL) n = n.left;
    return n;
  }

  private Node search(T val) {
    Node curr = root;
    while (curr != NIL) {
      int cmp = val.compareTo(curr.value);
      if (cmp < 0) curr = curr.left;
      else if (cmp > 0) curr = curr.right;
      else return curr;
    }
    return NIL;
  }

  public int height() { return height(root); }
  private int height(Node n) {
    return n == NIL ? 0 : 1 + Math.max(height(n.left), height(n.right));
  }

  @Override
  public Iterator<T> iterator() {
    int expectedCount = nodeCount;
    Stack<Node> stack = new Stack<>();
    pushLeft(root, stack);
    return new Iterator<T>() {
      public boolean hasNext() {
        if (expectedCount != nodeCount) throw new ConcurrentModificationException();
        return !stack.isEmpty();
      }
      public T next() {
        if (!hasNext()) throw new NoSuchElementException();
        Node n = stack.pop();
        pushLeft(n.right, stack);
        return n.value;
      }
    };
  }
  private void pushLeft(Node n, Stack<Node> s) {
    while (n != NIL) { s.push(n); n = n.left; }
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
