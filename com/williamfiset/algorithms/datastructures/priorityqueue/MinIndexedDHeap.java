/**
 * An implementation of an indexed min D-ary heap priority queue.
 *
 * <p>This implementation supports arbitrary keys with comparable values. To use arbitrary keys
 * (such as strings or objects) first map all your keys to the integer domain [0, N) where N is the
 * number of keys you have and then use the mapping with this indexed priority queue.
 *
 * <p>As convention, I denote 'ki' as the index value in the domain [0, N) associated with a key k,
 * therefore: ki = map[k]
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.datastructures.priorityqueue;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class MinIndexedDHeap<T extends Comparable<T>> {

  // Current number of elements in the heap.
  private int sz;

  // Maximum number of elements in the heap.
  private final int N;

  // The degree of every node in the heap.
  private final int D;

  // Lookup arrays to track the child/parent indexes of each node.
  private final int[] child, parent;

  // The Position Map (pm) maps Key Indexes (ki) to where the position of that
  // key is represented in the priority queue in the domain [0, sz).
  public final int[] pm;

  // The Inverse Map (im) stores the indexes of the keys in the range
  // [0, sz) which make up the priority queue. It should be noted that
  // 'im' and 'pm' are inverses of each other, so: pm[im[i]] = im[pm[i]] = i
  public final int[] im;

  // The values associated with the keys. It is very important  to note
  // that this array is indexed by the key indexes (aka 'ki').
  public final Object[] values;

  // Initializes a D-ary heap with a maximum capacity of maxSize.
  public MinIndexedDHeap(int degree, int maxSize) {
    if (maxSize <= 0) throw new IllegalArgumentException("maxSize <= 0");

    D = max(2, degree);
    N = max(D + 1, maxSize);

    im = new int[N];
    pm = new int[N];
    child = new int[N];
    parent = new int[N];
    values = new Object[N];

    for (int i = 0; i < N; i++) {
      parent[i] = (i - 1) / D;
      child[i] = i * D + 1;
      pm[i] = im[i] = -1;
    }
  }

  public int size() {
    return sz;
  }

  public boolean isEmpty() {
    return sz == 0;
  }

  public boolean contains(int ki) {
    keyInBoundsOrThrow(ki);
    return pm[ki] != -1;
  }

  public int peekMinKeyIndex() {
    isNotEmptyOrThrow();
    return im[0];
  }

  public int pollMinKeyIndex() {
    int minki = peekMinKeyIndex();
    delete(minki);
    return minki;
  }

  @SuppressWarnings("unchecked")
  public T peekMinValue() {
    isNotEmptyOrThrow();
    return (T) values[im[0]];
  }

  public T pollMinValue() {
    T minValue = peekMinValue();
    delete(peekMinKeyIndex());
    return minValue;
  }

  public void insert(int ki, T value) {
    if (contains(ki)) throw new IllegalArgumentException("index already exists; received: " + ki);
    valueNotNullOrThrow(value);
    pm[ki] = sz;
    im[sz] = ki;
    values[ki] = value;
    swim(sz++);
  }

  @SuppressWarnings("unchecked")
  public T valueOf(int ki) {
    keyExistsOrThrow(ki);
    return (T) values[ki];
  }

  @SuppressWarnings("unchecked")
  public T delete(int ki) {
    keyExistsOrThrow(ki);
    final int i = pm[ki];
    swap(i, --sz);
    sink(i);
    swim(i);
    T value = (T) values[ki];
    values[ki] = null;
    pm[ki] = -1;
    im[sz] = -1;
    return value;
  }

  @SuppressWarnings("unchecked")
  public T update(int ki, T value) {
    keyExistsAndValueNotNullOrThrow(ki, value);
    final int i = pm[ki];
    T oldValue = (T) values[ki];
    values[ki] = value;
    sink(i);
    swim(i);
    return oldValue;
  }

  // Strictly decreases the value associated with 'ki' to 'value'
  public void decrease(int ki, T value) {
    keyExistsAndValueNotNullOrThrow(ki, value);
    if (less(value, values[ki])) {
      values[ki] = value;
      swim(pm[ki]);
    }
  }

  // Strictly increases the value associated with 'ki' to 'value'
  public void increase(int ki, T value) {
    keyExistsAndValueNotNullOrThrow(ki, value);
    if (less(values[ki], value)) {
      values[ki] = value;
      sink(pm[ki]);
    }
  }

  /* Helper functions */

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
    int index = -1, from = child[i], to = min(sz, from + D);
    for (int j = from; j < to; j++) if (less(j, i)) index = i = j;
    return index;
  }

  private void swap(int i, int j) {
    pm[im[j]] = i;
    pm[im[i]] = j;
    int tmp = im[i];
    im[i] = im[j];
    im[j] = tmp;
  }

  // Tests if the value of node i < node j
  @SuppressWarnings("unchecked")
  private boolean less(int i, int j) {
    return ((Comparable<? super T>) values[im[i]]).compareTo((T) values[im[j]]) < 0;
  }

  @SuppressWarnings("unchecked")
  private boolean less(Object obj1, Object obj2) {
    return ((Comparable<? super T>) obj1).compareTo((T) obj2) < 0;
  }

  @Override
  public String toString() {
    List<Integer> lst = new ArrayList<>(sz);
    for (int i = 0; i < sz; i++) lst.add(im[i]);
    return lst.toString();
  }

  /* Helper functions to make the code more readable. */

  private void isNotEmptyOrThrow() {
    if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
  }

  private void keyExistsAndValueNotNullOrThrow(int ki, Object value) {
    keyExistsOrThrow(ki);
    valueNotNullOrThrow(value);
  }

  private void keyExistsOrThrow(int ki) {
    if (!contains(ki)) throw new NoSuchElementException("Index does not exist; received: " + ki);
  }

  private void valueNotNullOrThrow(Object value) {
    if (value == null) throw new IllegalArgumentException("value cannot be null");
  }

  private void keyInBoundsOrThrow(int ki) {
    if (ki < 0 || ki >= N)
      throw new IllegalArgumentException("Key index out of bounds; received: " + ki);
  }

  /* Test functions */

  // Recursively checks if this heap is a min heap. This method is used
  // for testing purposes to validate the heap invariant.
  public boolean isMinHeap() {
    return isMinHeap(0);
  }

  private boolean isMinHeap(int i) {
    int from = child[i], to = min(sz, from + D);
    for (int j = from; j < to; j++) {
      if (!less(i, j)) return false;
      if (!isMinHeap(j)) return false;
    }
    return true;
  }
}
