/**
 * An implementation of an indexed binary heap priority queue.
 *
 * <p>This implementation supports arbitrary keys with comparable values. To use arbitrary keys
 * (such as strings or objects) first map all your keys to the integer domain [0, N) where N is the
 * number of keys you have and then use the mapping with this indexed priority queue.
 *
 * <p>As convention, I denote 'ki' as the index value in the domain [0, N) associated with key k,
 * therefore: ki = map[k]
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.datastructures.priorityqueue;

public class MinIndexedBinaryHeap<T extends Comparable<T>> extends MinIndexedDHeap<T> {
  public MinIndexedBinaryHeap(int maxSize) {
    super(2, maxSize);
  }
}
