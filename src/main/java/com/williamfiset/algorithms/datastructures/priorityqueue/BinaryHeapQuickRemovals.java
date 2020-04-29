/**
 * A min priority queue implementation using a binary heap. This implementation tracks each element
 * inside the binary heap with a hashtable for quick removals.
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.datastructures.priorityqueue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class BinaryHeapQuickRemovals<T extends Comparable<T>> {

  // A dynamic list to track the elements inside the heap
  private List<T> heap = null;

  // This map keeps track of the possible indices a particular
  // node value is found in the heap. Having this mapping lets
  // us have O(log(n)) removals and O(1) element containment check
  // at the cost of some additional space and minor overhead
  private Map<T, TreeSet<Integer>> map = new HashMap<>();

  // Construct and initially empty priority queue
  public BinaryHeapQuickRemovals() {
    this(1);
  }

  // Construct a priority queue with an initial capacity
  public BinaryHeapQuickRemovals(int sz) {
    heap = new ArrayList<>(sz);
  }

  // Construct a priority queue using heapify in O(n) time, a great explanation can be found at:
  // http://www.cs.umd.edu/~meesh/351/mount/lectures/lect14-heapsort-analysis-part.pdf
  public BinaryHeapQuickRemovals(T[] elems) {

    int heapSize = elems.length;
    heap = new ArrayList<T>(heapSize);

    // Place all element in heap
    for (int i = 0; i < heapSize; i++) {
      mapAdd(elems[i], i);
      heap.add(elems[i]);
    }

    // Heapify process, O(n)
    for (int i = Math.max(0, (heapSize / 2) - 1); i >= 0; i--) sink(i);
  }

  // Priority queue construction, O(nlog(n))
  public BinaryHeapQuickRemovals(Collection<T> elems) {
    this(elems.size());
    for (T elem : elems) add(elem);
  }

  // Returns true/false depending on if the priority queue is empty
  public boolean isEmpty() {
    return size() == 0;
  }

  // Clears everything inside the heap, O(n)
  public void clear() {
    heap.clear();
    map.clear();
  }

  // Return the size of the heap
  public int size() {
    return heap.size();
  }

  // Returns the value of the element with the lowest
  // priority in this priority queue. If the priority
  // queue is empty null is returned.
  public T peek() {
    if (isEmpty()) return null;
    return heap.get(0);
  }

  // Removes the root of the heap, O(log(n))
  public T poll() {
    return removeAt(0);
  }

  // Test if an element is in heap, O(1)
  public boolean contains(T elem) {

    // Map lookup to check containment, O(1)
    if (elem == null) return false;
    return map.containsKey(elem);

    // Linear scan to check containment, O(n)
    // for(int i = 0; i < heapSize; i++)
    //   if (heap.get(i).equals(elem))
    //     return true;
    // return false;

  }

  // Adds an element to the priority queue, the
  // element must not be null, O(log(n))
  public void add(T elem) {

    if (elem == null) throw new IllegalArgumentException();

    heap.add(elem);
    int indexOfLastElem = size() - 1;
    mapAdd(elem, indexOfLastElem);

    swim(indexOfLastElem);
  }

  // Tests if the value of node i <= node j
  // This method assumes i & j are valid indices, O(1)
  private boolean less(int i, int j) {

    T node1 = heap.get(i);
    T node2 = heap.get(j);
    return node1.compareTo(node2) <= 0;
  }

  // Perform bottom up node swim, O(log(n))
  private void swim(int k) {

    // Grab the index of the next parent node WRT to k
    int parent = (k - 1) / 2;

    // Keep swimming while we have not reached the
    // root and while we're less than our parent.
    while (k > 0 && less(k, parent)) {

      // Exchange k with the parent
      swap(parent, k);
      k = parent;

      // Grab the index of the next parent node WRT to k
      parent = (k - 1) / 2;
    }
  }

  // Top down node sink, O(log(n))
  private void sink(int k) {
    int heapSize = size();

    while (true) {

      int left = 2 * k + 1; // Left  node
      int right = 2 * k + 2; // Right node
      int smallest = left; // Assume left is the smallest node of the two children

      // Find which is smaller left or right
      // If right is smaller set smallest to be right
      if (right < heapSize && less(right, left)) smallest = right;

      // Stop if we're outside the bounds of the tree
      // or stop early if we cannot sink k anymore
      if (left >= heapSize || less(k, smallest)) break;

      // Move down the tree following the smallest node
      swap(smallest, k);
      k = smallest;
    }
  }

  // Swap two nodes. Assumes i & j are valid, O(1)
  private void swap(int i, int j) {

    T i_elem = heap.get(i);
    T j_elem = heap.get(j);

    heap.set(i, j_elem);
    heap.set(j, i_elem);

    mapSwap(i_elem, j_elem, i, j);
  }

  // Removes a particular element in the heap, O(log(n))
  public boolean remove(T element) {

    if (element == null) return false;

    // Linear removal via search, O(n)
    // for (int i = 0; i < heapSize; i++) {
    //   if (element.equals(heap.get(i))) {
    //     removeAt(i);
    //     return true;
    //   }
    // }

    // Logarithmic removal with map, O(log(n))
    Integer index = mapGet(element);
    if (index != null) removeAt(index);
    return index != null;
  }

  // Removes a node at particular index, O(log(n))
  private T removeAt(int i) {

    if (isEmpty()) return null;

    int indexOfLastElem = size() - 1;
    T removed_data = heap.get(i);
    swap(i, indexOfLastElem);

    // Obliterate the value
    heap.remove(indexOfLastElem);
    mapRemove(removed_data, indexOfLastElem);

    // Removed last element
    if (i == indexOfLastElem) return removed_data;

    T elem = heap.get(i);

    // Try sinking element
    sink(i);

    // If sinking did not work try swimming
    if (heap.get(i).equals(elem)) swim(i);

    return removed_data;
  }

  // Recursively checks if this heap is a min heap
  // This method is just for testing purposes to make
  // sure the heap invariant is still being maintained
  // Called this method with k=0 to start at the root
  public boolean isMinHeap(int k) {

    // If we are outside the bounds of the heap return true
    int heapSize = size();
    if (k >= heapSize) return true;

    int left = 2 * k + 1;
    int right = 2 * k + 2;

    // Make sure that the current node k is less than
    // both of its children left, and right if they exist
    // return false otherwise to indicate an invalid heap
    if (left < heapSize && !less(k, left)) return false;
    if (right < heapSize && !less(k, right)) return false;

    // Recurse on both children to make sure they're also valid heaps
    return isMinHeap(left) && isMinHeap(right);
  }

  // Add a node value and its index to the map
  private void mapAdd(T value, int index) {

    TreeSet<Integer> set = map.get(value);

    // New value being inserted in map
    if (set == null) {

      set = new TreeSet<>();
      set.add(index);
      map.put(value, set);

      // Value already exists in map
    } else set.add(index);
  }

  // Removes the index at a given value, O(log(n))
  private void mapRemove(T value, int index) {
    TreeSet<Integer> set = map.get(value);
    set.remove(index); // TreeSets take O(log(n)) removal time
    if (set.size() == 0) map.remove(value);
  }

  // Extract an index position for the given value
  // NOTE: If a value exists multiple times in the heap the highest
  // index is returned (this has arbitrarily been chosen)
  private Integer mapGet(T value) {
    TreeSet<Integer> set = map.get(value);
    if (set != null) return set.last();
    return null;
  }

  // Exchange the index of two nodes internally within the map
  private void mapSwap(T val1, T val2, int val1Index, int val2Index) {

    Set<Integer> set1 = map.get(val1);
    Set<Integer> set2 = map.get(val2);

    set1.remove(val1Index);
    set2.remove(val2Index);

    set1.add(val2Index);
    set2.add(val1Index);
  }

  @Override
  public String toString() {
    return heap.toString();
  }
}
