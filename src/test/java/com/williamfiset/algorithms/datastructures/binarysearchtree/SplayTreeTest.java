package com.williamfiset.algorithms.datastructures.binarysearchtree;

import static org.junit.jupiter.api.Assertions.*;

import com.williamfiset.algorithms.datastructures.utils.TestUtils;
import java.util.*;
import org.junit.jupiter.api.Test;

class SplayTreeTest {

  public static final int MAX = Integer.MAX_VALUE / 4, MIN = Integer.MIN_VALUE / 4;

  @Test
  void getRoot() {
    SplayTree<Integer> splayTree = new SplayTree<>();
    List<Integer> data = TestUtils.randomIntegerList(100, MIN, MAX);
    for (int i : data) {
      splayTree.insert(i);
      assertEquals(i, splayTree.getRoot().getData());
    }
  }

  @Test
  void splayInsertDeleteSearch() {
    SplayTree<Integer> splayTree = new SplayTree<>();
    List<Integer> data =
        TestUtils.randomUniformUniqueIntegerList(
            100); // Note : we dont want duplicate values here to test "search" after "delete"
    // should assertNull
    for (int i : data) {
      splayTree.insert(i);
      assertEquals(i, splayTree.getRoot().getData());
    }
    for (int i : data) {
      assertNotNull(splayTree.search(i));
    }
    for (int i : data) {
      splayTree.delete(i);
      assertNull(splayTree.search(i));
    }
  }

  @Test
  void insertSearch() {
    SplayTree<Integer> splayTree = new SplayTree<>();
    List<Integer> data = TestUtils.randomIntegerList(100, MIN, MAX);
    for (int i : data) {
      splayTree.insert(i);
      assertEquals(i, splayTree.getRoot().getData());
    }
  }

  @Test
  void findMax() {
    SplayTree<Integer> splayTree = new SplayTree<>();
    List<Integer> data = TestUtils.sortedIntegerList(-50, 50);
    for (int i : data) {
      splayTree.insert(i);
      assertEquals(i, splayTree.findMax(splayTree.getRoot()));
    }
  }

  /** Comparison With Built In Priority Queue* */
  @Test
  void splayTreePriorityQueueConsistencyTest() {
    SplayTree<Integer> splayTree = new SplayTree<>();
    List<Integer> data = TestUtils.randomUniformUniqueIntegerList(100);
    Queue<Integer> pq = new PriorityQueue<>(100, Collections.reverseOrder());

    // insertion
    for (int i : data) {
      assertEquals(pq.add(i), splayTree.insert(i) != null);
    }
    // searching
    for (int i : data) {
      assertEquals(splayTree.search(i).getData().equals(i), pq.contains(i));
    }
    // findMax & delete
    while (!pq.isEmpty()) {
      Integer splayTreeMax = splayTree.findMax();
      assertEquals(pq.peek(), splayTreeMax);

      splayTree.delete(splayTreeMax);
      assertNull(splayTree.search(splayTreeMax));
      pq.remove(splayTreeMax);
      assertFalse(pq.contains(splayTreeMax));
    }
  }
}
