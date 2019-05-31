/**
 * A generic implementation of a D-ary heap inspired by the work of David Brink.
 *
 * @author David Brink, William Fiset
 */
package com.williamfiset.algorithms.datastructures.priorityqueue;

@SuppressWarnings("unchecked")
public class MinDHeap<T extends Comparable<T>> {

  private T[] heap;
  private int d, n, sz;
  private int[] child, parent;

  // Initializes a D-ary heap with a maximum capacity of n
  public MinDHeap(int degree, int maxNodes) {
    d = Math.max(2, degree);
    n = Math.max(d, maxNodes);

    heap = (T[]) new Comparable[n];
    child = new int[n];
    parent = new int[n];
    for (int i = 0; i < n; i++) {
      parent[i] = (i - 1) / d;
      child[i] = i * d + 1;
    }
  }

  // Returns the number of elements currently present inside the PQ
  public int size() {
    return sz;
  }

  // Returns true/false depending on whether the PQ is empty
  public boolean isEmpty() {
    return sz == 0;
  }

  // Clears all the elements inside the PQ
  public void clear() {
    java.util.Arrays.fill(heap, null);
    sz = 0;
  }

  // Returns the element at the top of the PQ or null if the PQ is empty
  public T peek() {
    if (isEmpty()) return null;
    return heap[0];
  }

  // Polls an element from the priority queue.
  // Make sure the queue is not empty before calling.
  public T poll() {
    if (isEmpty()) return null;
    T root = heap[0];
    heap[0] = heap[--sz];
    heap[sz] = null;
    sink(0);
    return root;
  }

  // Adds a none null element to the priority queue
  public void add(T elem) {
    if (elem == null) throw new IllegalArgumentException("No null elements please :)");
    heap[sz] = elem;
    swim(sz);
    sz++;
  }

  private void sink(int i) {
    for (int j = minChild(i); j != -1; ) {
      swap(i, j);
      i = j;
      j = minChild(i);
    }
  }

  private void swim(int i) {
    while (less(i, parent[i])) {
      swap(i, parent[i]);
      i = parent[i];
    }
  }

  // From the parent node at index i find the minimum child below it
  private int minChild(int i) {
    int index = -1, from = child[i], to = Math.min(sz, from + d);
    for (int j = from; j < to; j++) if (less(j, i)) index = i = j;
    return index;
  }

  private boolean less(int i, int j) {
    return heap[i].compareTo(heap[j]) < 0;
  }

  private void swap(int i, int j) {
    T tmp = heap[i];
    heap[i] = heap[j];
    heap[j] = tmp;
  }
}
