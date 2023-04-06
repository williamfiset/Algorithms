package com.williamfiset.algorithms.datastructures.binarysearchtree;

import static com.google.common.truth.Truth.assertThat;

import com.williamfiset.algorithms.datastructures.utils.TestUtils;
import java.util.*;
import org.junit.Test;

public class SplayTreeTest {

  public static final int MAX = Integer.MAX_VALUE / 4, MIN = Integer.MIN_VALUE / 4;

  @Test
  public void getRoot() {
    SplayTree<Integer> splayTree = new SplayTree<>();
    List<Integer> data = TestUtils.randomIntegerList(100, MIN, MAX);
    for (int i : data) {
      splayTree.insertNode(i);
      assertThat(splayTree.getRoot().getData()).isEqualTo(i);
    }
  }

  @Test
  public void splayInsertDeleteSearch() {
    SplayTree<Integer> splayTree = new SplayTree<>();
    List<Integer> data =
        TestUtils.randomUniformUniqueIntegerList(
            100); // Note : we dont want duplicate values here to test "search" after "delete"
    // should assertNull
    for (int i : data) {
      splayTree.insertNode(i);
      assertThat(splayTree.getRoot().getData()).isEqualTo(i);
    }
    for (int i : data) {
      assertThat(splayTree.searchNode(i)).isNotNull();
    }
    for (int i : data) {
      splayTree.deleteNode(i);
      assertThat(splayTree.searchNode(i)).isNull();
    }
  }

  @Test
  public void insertSearch() {
    SplayTree<Integer> splayTree = new SplayTree<>();
    List<Integer> data = TestUtils.randomIntegerList(100, MIN, MAX);
    for (int i : data) {
      splayTree.insertNode(i);
      assertThat(splayTree.getRoot().getData()).isEqualTo(i);
    }
  }

  @Test
  public void findMax() {
    SplayTree<Integer> splayTree = new SplayTree<>();
    List<Integer> data = TestUtils.sortedIntegerList(-50, 50);
    for (int i : data) {
      splayTree.insertNode(i);
      assertThat(splayTree.findMaximumNode(splayTree.getRoot())).isEqualTo(i);
    }
  }

  /** Comparison With Built In Priority Queue* */
  @Test
  public void splayTreePriorityQueueConsistencyTest() {
    SplayTree<Integer> splayTree = new SplayTree<>();
    List<Integer> data = TestUtils.randomUniformUniqueIntegerList(100);
    Queue<Integer> pq = new PriorityQueue<>(100, Collections.reverseOrder());

    // insertion
    for (int i : data) {
      assertThat(pq.add(i)).isEqualTo(splayTree.insertNode(i) != null);
    }
    // searching
    for (int i : data) {
      assertThat(splayTree.searchNode(i).getData().equals(i)).isEqualTo(pq.contains(i));
    }
    // findMax & delete
    while (!pq.isEmpty()) {
      Integer splayTreeMax = splayTree.findMaximumNode();
      assertThat(pq.peek()).isEqualTo(splayTreeMax);

      splayTree.deleteNode(splayTreeMax);
      assertThat(splayTree.searchNode(splayTreeMax)).isNull();
      pq.remove(splayTreeMax);
      assertThat(pq.contains(splayTreeMax)).isFalse();
    }
  }
}
