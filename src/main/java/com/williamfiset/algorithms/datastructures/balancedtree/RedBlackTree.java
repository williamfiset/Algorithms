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
    public boolean color = RED;
    public T value;
    public Node left, right, parent;

    public Node(T value, boolean color, Node parent, Node left, Node right) {
      this.value = value;
      this.color = color;
      this.parent = parent;
      this.left = left;
      this.right = right;
    }
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
    Node parent = NIL, current = root;
    while (current != NIL) {
      parent = current;
      int cmp = val.compareTo(current.value);
      if (cmp < 0) current = current.left;
      else if (cmp > 0) current = current.right;
      else return false;
    }
    Node newNode = new Node(val, RED, parent, NIL, NIL);
    if (parent == NIL) root = newNode;
    else if (val.compareTo(parent.value) < 0) parent.left = newNode;
    else parent.right = newNode;
    insertFix(newNode);
    nodeCount++;
    return true;
  }

  private void insertFix(Node node) {
    while (node.parent.color == RED) {
      boolean isLeft = (node.parent == node.parent.parent.left);
      Node uncle = isLeft ? node.parent.parent.right : node.parent.parent.left;
      if (uncle.color == RED) {
        node.parent.color = uncle.color = BLACK;
        node.parent.parent.color = RED;
        node = node.parent.parent;
      } else {
        if (node == (isLeft ? node.parent.right : node.parent.left)) {
          node = node.parent;
          rotate(node, isLeft);
        }
        node.parent.color = BLACK;
        node.parent.parent.color = RED;
        rotate(node.parent.parent, !isLeft);
      }
    }
    root.color = BLACK;
  }

  public boolean delete(T key) {
    Node nodeToDelete = search(key);
    if (nodeToDelete == NIL) return false;
    Node successor = nodeToDelete, replacement;
    boolean successorOriginalColor = successor.color;
    if (nodeToDelete.left == NIL) transplant(nodeToDelete, replacement = nodeToDelete.right);
    else if (nodeToDelete.right == NIL) transplant(nodeToDelete, replacement = nodeToDelete.left);
    else {
      successor = findMin(nodeToDelete.right);
      successorOriginalColor = successor.color;
      replacement = successor.right;
      if (successor.parent == nodeToDelete) replacement.parent = successor;
      else {
        transplant(successor, successor.right);
        successor.right = nodeToDelete.right;
        successor.right.parent = successor;
      }
      transplant(nodeToDelete, successor);
      successor.left = nodeToDelete.left;
      successor.left.parent = successor;
      successor.color = nodeToDelete.color;
    }
    if (successorOriginalColor == BLACK) deleteFix(replacement);
    nodeCount--;
    return true;
  }

  private void deleteFix(Node node) {
    while (node != root && node.color == BLACK) {
      boolean isLeft = (node == node.parent.left);
      Node sibling = isLeft ? node.parent.right : node.parent.left;
      if (sibling.color == RED) {
        sibling.color = BLACK;
        node.parent.color = RED;
        rotate(node.parent, isLeft);
        sibling = isLeft ? node.parent.right : node.parent.left;
      }
      if (sibling.left.color == BLACK && sibling.right.color == BLACK) {
        sibling.color = RED;
        node = node.parent;
      } else {
        if ((isLeft ? sibling.right : sibling.left).color == BLACK) {
          (isLeft ? sibling.left : sibling.right).color = BLACK;
          sibling.color = RED;
          rotate(sibling, !isLeft);
          sibling = isLeft ? node.parent.right : node.parent.left;
        }
        sibling.color = node.parent.color;
        node.parent.color = BLACK;
        (isLeft ? sibling.right : sibling.left).color = BLACK;
        rotate(node.parent, isLeft);
        node = root;
      }
    }
    node.color = BLACK;
  }

  private void rotate(Node node, boolean left) {
    Node child = left ? node.right : node.left;
    if (left) {
      node.right = child.left;
      if (child.left != NIL) child.left.parent = node;
    } else {
      node.left = child.right;
      if (child.right != NIL) child.right.parent = node;
    }
    child.parent = node.parent;
    if (node.parent == NIL) root = child;
    else if (node == node.parent.left) node.parent.left = child;
    else node.parent.right = child;
    if (left) child.left = node; else child.right = node;
    node.parent = child;
  }

  private void transplant(Node target, Node source) {
    if (target.parent == NIL) root = source;
    else if (target == target.parent.left) target.parent.left = source;
    else target.parent.right = source;
    source.parent = target.parent;
  }

  private Node findMin(Node node) {
    while (node.left != NIL) node = node.left;
    return node;
  }

  private Node search(T val) {
    Node current = root;
    while (current != NIL) {
      int cmp = val.compareTo(current.value);
      if (cmp < 0) current = current.left;
      else if (cmp > 0) current = current.right;
      else return current;
    }
    return NIL;
  }

  public int height() { return height(root); }
  private int height(Node node) {
    return node == NIL ? 0 : 1 + Math.max(height(node.left), height(node.right));
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
        Node node = stack.pop();
        pushLeft(node.right, stack);
        return node.value;
      }
    };
  }
  private void pushLeft(Node node, Stack<Node> stack) {
    while (node != NIL) { stack.push(node); node = node.left; }
  }
}
