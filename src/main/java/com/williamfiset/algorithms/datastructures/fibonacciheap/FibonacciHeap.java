/**
 * FibonacciHeap data structure implementation.
 *
 * <p>Disclaimer: implementation based on:
 * http://staff.ustc.edu.cn/~csli/graduate/algorithms/book6/chap21.htm Implementation credits to the
 * respective code owners.
 */
package com.williamfiset.algorithms.datastructures.fibonacciheap;

import static java.lang.Math.floor;
import static java.lang.Math.log;
import static java.lang.Math.sqrt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public final class FibonacciHeap<E> implements Queue<E> {

  private static final double LOG_PHI = log((1 + sqrt(5)) / 2);

  private final Set<E> elementsIndex = new HashSet<E>();

  private final Comparator<? super E> comparator;

  private int size = 0;

  private int trees = 0;

  private int markedNodes = 0;

  private FibonacciHeapNode<E> minimumNode;

  public FibonacciHeap() {
    this(null);
  }

  public FibonacciHeap(/* @Nullable */ Comparator<? super E> comparator) {
    this.comparator = comparator;
  }

  private void moveToRoot(FibonacciHeapNode<E> node) {
    // 8'  if min[H] = NIL
    if (isEmpty()) {
      // then min[H] <- x
      minimumNode = node;
    } else {
      // 7 concatenate the root list containing x with root list H
      node.getLeft().setRight(node.getRight());
      node.getRight().setLeft(node.getLeft());

      node.setLeft(minimumNode);
      node.setRight(minimumNode.getRight());
      minimumNode.setRight(node);
      node.getRight().setLeft(node);

      // 8''  if key[x] < key[min[H]]
      if (compare(node, minimumNode) < 0) {
        // 9     then min[H] <- x
        minimumNode = node;
      }
    }
  }

  public boolean add(E e) {
    if (e == null) {
      throw new IllegalArgumentException(
          "Null elements not allowed in this FibonacciHeap implementation.");
    }

    // 1-6 performed in the node initialization
    FibonacciHeapNode<E> node = new FibonacciHeapNode<E>(e);

    // 7-9 performed in the #moveToRoot( FibonacciHeapNode<E> ) method
    moveToRoot(node);

    // 10  n[H] <- n[H] + 1
    size++;

    elementsIndex.add(e);

    return true;
  }

  /** {@inheritDoc} */
  public boolean addAll(Collection<? extends E> c) {
    for (E element : c) {
      add(element);
    }

    return true;
  }

  /** {@inheritDoc} */
  public void clear() {
    minimumNode = null;
    size = 0;
    trees = 0;
    markedNodes = 0;
    elementsIndex.clear();
  }

  /** {@inheritDoc} */
  public boolean contains(Object o) {
    if (o == null) {
      return false;
    }

    return elementsIndex.contains(o);
  }

  /** {@inheritDoc} */
  public boolean containsAll(Collection<?> c) {
    if (c == null) {
      return false;
    }

    for (Object o : c) {
      if (!contains(o)) {
        return false;
      }
    }

    return true;
  }

  /** {@inheritDoc} */
  public boolean isEmpty() {
    return minimumNode == null;
  }

  /** {@inheritDoc} */
  public Iterator<E> iterator() {
    throw new UnsupportedOperationException();
  }

  /** {@inheritDoc} */
  public boolean remove(Object o) {
    throw new UnsupportedOperationException();
  }

  /** {@inheritDoc} */
  public boolean removeAll(Collection<?> c) {
    throw new UnsupportedOperationException();
  }

  /** {@inheritDoc} */
  public boolean retainAll(Collection<?> c) {
    throw new UnsupportedOperationException();
  }

  /** {@inheritDoc} */
  public int size() {
    return size;
  }

  /** {@inheritDoc} */
  public Object[] toArray() {
    throw new UnsupportedOperationException();
  }

  /** {@inheritDoc} */
  public <T> T[] toArray(T[] a) {
    throw new UnsupportedOperationException();
  }

  /** {@inheritDoc} */
  public E element() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
    return peek();
  }

  /** {@inheritDoc} */
  public boolean offer(E e) {
    return add(e);
  }

  /** {@inheritDoc} */
  public E peek() {
    if (isEmpty()) {
      return null;
    }

    return minimumNode.getElement();
  }

  public E poll() {
    // 2  if z &ne; NIL
    if (isEmpty()) {
      return null;
    }

    // 1  z <- min[H]
    FibonacciHeapNode<E> z = minimumNode;
    int numOfKids = z.getDegree();

    FibonacciHeapNode<E> x = z.getChild();
    FibonacciHeapNode<E> tempRight;

    while (numOfKids > 0) {
      // 3  for each child x of z
      tempRight = x.getRight();

      // 4  do add x to the root list of H
      moveToRoot(x);

      // 5  p[x] <- NIL
      x.setParent(null);

      x = tempRight;
      numOfKids--;
    }

    // 6  remove z from the root list of H
    z.getLeft().setRight(z.getRight());
    z.getRight().setLeft(z.getLeft());

    // 7  if z = right[z]
    if (z == z.getRight()) {
      // 8  min[H] <- NIL
      minimumNode = null;
    } else {
      // 9  min[H] <- right[z]
      minimumNode = z.getRight();
      // 10  CONSOLIDATE(H)
      consolidate();
    }

    // 11  n[H] <- n[H] - 1
    size--;

    E minimum = z.getElement();
    elementsIndex.remove(minimum);
    // 12  return z
    return minimum;
  }

  /** {@inheritDoc} */
  public E remove() {
    // FIB-HEAP-EXTRACT-MIN(H)

    if (isEmpty()) {
      throw new NoSuchElementException();
    }

    return poll();
  }

  private void consolidate() {
    if (isEmpty()) {
      return;
    }

    // D( n[H] ) <= log_phi( n[H] )
    // -> log_phi( n[H] ) = log( n[H] ) / log( phi )
    // -> D( n[H] ) = log( n[H] ) / log( phi )
    int arraySize = ((int) floor(log(size) / LOG_PHI));

    // 1  for i <- 0 to D(n[H])
    List<FibonacciHeapNode<E>> nodeSequence = new ArrayList<FibonacciHeapNode<E>>(arraySize);
    for (int i = 0; i < arraySize; i++) {
      // 2      do A[i] <- NIL
      nodeSequence.add(i, null);
    }

    int numRoots = 0;

    // 3  for each node x in the root list of H
    // 4  do x &larr; w
    FibonacciHeapNode<E> x = minimumNode;

    if (x != null) {
      numRoots++;
      x = x.getRight();

      while (x != minimumNode) {
        numRoots++;
        x = x.getRight();
      }
    }

    while (numRoots > 0) {
      // 5  d <- degree[x]
      int degree = x.getDegree();
      FibonacciHeapNode<E> next = x.getRight();

      // 6  while A[d] != NIL
      while (nodeSequence.get(degree) != null) {
        // 7  do y <- A[d]
        FibonacciHeapNode<E> y = nodeSequence.get(degree);

        // 8  if key[x] > key[y]
        if (compare(x, y) > 0) {
          // 9  exchange x <-> y
          FibonacciHeapNode<E> pointer = y;
          y = x;
          x = pointer;
        }

        // 10  FIB-HEAP-LINK(H,y,x)
        link(y, x);

        // 11  A[d] <- NIL
        nodeSequence.set(degree, null);

        // 12  d <- d + 1
        degree++;
      }

      // 13  A[d] <- x
      nodeSequence.set(degree, x);

      x = next;
      numRoots--;
    }

    // 14  min[H] <- NIL
    minimumNode = null;

    // 15  for i <- 0 to D(n[H])
    for (FibonacciHeapNode<E> pointer : nodeSequence) {
      if (pointer == null) {
        continue;
      }
      if (minimumNode == null) {
        minimumNode = pointer;
      }

      // 16 if A[i] != NIL
      // We've got a live one, add it to root list.
      if (minimumNode != null) {
        //  First remove node from root list.
        moveToRoot(pointer);
      }
    }
  }

  private void link(FibonacciHeapNode<E> y, FibonacciHeapNode<E> x) {
    // 1 remove y from the root list of H
    y.getLeft().setRight(y.getRight());
    y.getRight().setLeft(y.getLeft());

    y.setParent(x);

    if (x.getChild() == null) {
      // 2 make y a child of x, incrementing degree[x]
      x.setChild(y);
      y.setRight(y);
      y.setLeft(y);
    } else {
      y.setLeft(x.getChild());
      y.setRight(x.getChild().getRight());
      x.getChild().setRight(y);
      y.getRight().setLeft(y);
    }

    x.incraeseDegree();

    // 3 mark[y] <- FALSE
    y.setMarked(false);
    markedNodes++;
  }

  private void cut(FibonacciHeapNode<E> x, FibonacciHeapNode<E> y) {
    // add x to the root list of H
    moveToRoot(x);

    // remove x from the child list of y, decrementing degree[y]
    y.decraeseDegree();
    // p[x] <- NIL
    x.setParent(null);

    // mark[x] <- FALSE
    x.setMarked(false);
    markedNodes--;
  }

  private void cascadingCut(FibonacciHeapNode<E> y) {
    // z <- p[y]
    FibonacciHeapNode<E> z = y.getParent();

    // if z != NIL
    if (z != null) {
      // if mark[y] = FALSE
      if (!y.isMarked()) {
        // then mark[y]  TRUE
        y.setMarked(true);
        markedNodes++;
      } else {
        // else CUT(H,y,z)
        cut(y, z);
        // CASCADING-CUT(H,z)
        cascadingCut(z);
      }
    }
  }

  public int potential() {
    return trees + 2 * markedNodes;
  }

  private int compare(FibonacciHeapNode<E> o1, FibonacciHeapNode<E> o2) {
    if (comparator != null) {
      return comparator.compare(o1.getElement(), o2.getElement());
    }
    @SuppressWarnings("unchecked") // it will throw a ClassCastException at runtime
    Comparable<? super E> o1Comparable = (Comparable<? super E>) o1.getElement();
    return o1Comparable.compareTo(o2.getElement());
  }

  /**
   * Creates a String representation of this Fibonacci heap.
   *
   * @return String of this.
   */
  public String toString() {
    if (minimumNode == null) {
      return "FibonacciHeap=[]";
    }

    // create a new stack and put root on it
    Stack<FibonacciHeapNode<E>> stack = new Stack<FibonacciHeapNode<E>>();
    stack.push(minimumNode);

    StringBuilder buf = new StringBuilder("FibonacciHeap=[");

    // do a simple breadth-first traversal on the tree
    while (!stack.empty()) {
      FibonacciHeapNode<E> curr = stack.pop();
      buf.append(curr);
      buf.append(", ");

      if (curr.getChild() != null) {
        stack.push(curr.getChild());
      }

      FibonacciHeapNode<E> start = curr;
      curr = curr.getRight();

      while (curr != start) {
        buf.append(curr);
        buf.append(", ");

        if (curr.getChild() != null) {
          stack.push(curr.getChild());
        }

        curr = curr.getRight();
      }
    }

    buf.append(']');

    return buf.toString();
  }
}
