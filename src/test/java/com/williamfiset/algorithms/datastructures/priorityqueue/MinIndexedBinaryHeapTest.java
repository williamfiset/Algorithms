package com.williamfiset.algorithms.datastructures.priorityqueue;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import org.junit.jupiter.api.*;

public class MinIndexedBinaryHeapTest {

  @BeforeEach
  public void setup() {}

  @Test
  public void testIllegalSizeOfNegativeOne() {
    assertThrows(IllegalArgumentException.class, () -> new MinIndexedBinaryHeap<String>(-1));
  }

  @Test
  public void testIllegalSizeOfZero() {
    assertThrows(IllegalArgumentException.class, () -> new MinIndexedBinaryHeap<String>(0));
  }

  @Test
  public void testLegalSize() {
    new MinIndexedBinaryHeap<String>(1);
  }

  @Test
  public void testContainsValidKey() {
    MinIndexedBinaryHeap<String> pq = new MinIndexedBinaryHeap<String>(10);
    pq.insert(5, "abcdef");
    assertThat(pq.contains(5)).isTrue();
  }

  @Test
  public void testContainsInvalidKey() {
    MinIndexedBinaryHeap<String> pq = new MinIndexedBinaryHeap<String>(10);
    pq.insert(5, "abcdef");
    assertThat(pq.contains(3)).isFalse();
  }

  @Test
  public void testDuplicateKeys() {
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          MinIndexedBinaryHeap<String> pq = new MinIndexedBinaryHeap<String>(10);
          pq.insert(5, "abcdef");
          pq.insert(5, "xyz");
        });
  }

  @Test
  public void testUpdateKeyValue() {
    MinIndexedBinaryHeap<String> pq = new MinIndexedBinaryHeap<String>(10);
    pq.insert(5, "abcdef");
    pq.update(5, "xyz");
    assertThat(pq.valueOf(5)).isEqualTo("xyz");
  }

  @Test
  public void testTestDecreaseKey() {
    MinIndexedBinaryHeap<Integer> pq = new MinIndexedBinaryHeap<Integer>(10);
    pq.insert(3, 5);
    pq.decrease(3, 4);
    assertThat(pq.valueOf(3)).isEqualTo(4);
  }

  @Test
  public void testTestDecreaseKeyNoUpdate() {
    MinIndexedBinaryHeap<Integer> pq = new MinIndexedBinaryHeap<Integer>(10);
    pq.insert(3, 5);
    pq.decrease(3, 6);
    assertThat(pq.valueOf(3)).isEqualTo(5);
  }

  @Test
  public void testTestIncreaseKey() {
    MinIndexedBinaryHeap<Integer> pq = new MinIndexedBinaryHeap<Integer>(10);
    pq.insert(3, 5);
    pq.increase(3, 6);
    assertThat(pq.valueOf(3)).isEqualTo(6);
  }

  @Test
  public void testTestIncreaseKeyNoUpdate() {
    MinIndexedBinaryHeap<Integer> pq = new MinIndexedBinaryHeap<Integer>(10);
    pq.insert(3, 5);
    pq.increase(3, 4);
    assertThat(pq.valueOf(3)).isEqualTo(5);
  }

  @Test
  public void testPeekAndPollMinIndex() {
    // pairs[i][0] is the index
    // pairs[i][1] is the value
    Integer[][] pairs = {
      {4, 1},
      {7, 5},
      {1, 6},
      {5, 8},
      {3, 7},
      {6, 9},
      {8, 0},
      {2, 4},
      {9, 3},
      {0, 2}
    };
    sortPairsByValue(pairs);

    int n = pairs.length;
    MinIndexedBinaryHeap<Integer> pq = new MinIndexedBinaryHeap<Integer>(n);
    for (int i = 0; i < n; i++) pq.insert(pairs[i][0], pairs[i][1]);

    Integer minIndex;
    for (int i = 0; i < n; i++) {
      minIndex = pq.peekMinKeyIndex();
      assertThat(minIndex).isEqualTo(pairs[i][0]);
      minIndex = pq.pollMinKeyIndex();
      assertThat(minIndex).isEqualTo(pairs[i][0]);
    }
  }

  @Test
  public void testPeekAndPollMinValue() {
    // pairs[i][0] is the index
    // pairs[i][1] is the value
    Integer[][] pairs = {
      {4, 1},
      {7, 5},
      {1, 6},
      {5, 8},
      {3, 7},
      {6, 9},
      {8, 0},
      {2, 4},
      {9, 3},
      {0, 2}
    };
    sortPairsByValue(pairs);

    int n = pairs.length;
    MinIndexedBinaryHeap<Integer> pq = new MinIndexedBinaryHeap<Integer>(n);
    for (int i = 0; i < n; i++) pq.insert(pairs[i][0], pairs[i][1]);

    Integer minValue;
    for (int i = 0; i < n; i++) {
      assertThat(pq.valueOf(pairs[i][0])).isEqualTo(pairs[i][1]);
      minValue = pq.peekMinValue();
      assertThat(minValue).isEqualTo(pairs[i][1]);
      minValue = pq.pollMinValue();
      assertThat(minValue).isEqualTo(pairs[i][1]);
    }
  }

  @Test
  public void testInsertionAndValueOf() {
    String[] names = {"jackie", "wilson", "catherine", "jason", "bobby", "sia"};
    MinIndexedBinaryHeap<String> pq = new MinIndexedBinaryHeap<String>(names.length);
    for (int i = 0; i < names.length; i++) pq.insert(i, names[i]);
    for (int i = 0; i < names.length; i++) assertThat(pq.valueOf(i)).isEqualTo(names[i]);
  }

  @Test
  public void testOperations() {
    int n = 7;
    MinIndexedBinaryHeap<Integer> pq = new MinIndexedBinaryHeap<Integer>(n);

    pq.insert(4, 4);
    assertThat(pq.contains(4)).isTrue();
    assertThat(pq.peekMinValue()).isEqualTo(4);
    assertThat(pq.peekMinKeyIndex()).isEqualTo(4);
    pq.update(4, 8);
    assertThat(pq.peekMinValue()).isEqualTo(8);
    assertThat(pq.pollMinKeyIndex()).isEqualTo(4);
    assertThat(pq.contains(4)).isFalse();
    pq.insert(3, 99);
    pq.insert(1, 101);
    pq.insert(2, 60);
    assertThat(pq.peekMinValue()).isEqualTo(60);
    assertThat(pq.peekMinKeyIndex()).isEqualTo(2);
    pq.increase(2, 150);
    assertThat(pq.peekMinValue()).isEqualTo(99);
    assertThat(pq.peekMinKeyIndex()).isEqualTo(3);
    pq.increase(3, 250);
    assertThat(pq.peekMinValue()).isEqualTo(101);
    assertThat(pq.peekMinKeyIndex()).isEqualTo(1);
    pq.decrease(3, -500);
    assertThat(pq.peekMinValue()).isEqualTo(-500);
    assertThat(pq.peekMinKeyIndex()).isEqualTo(3);
    assertThat(pq.contains(3)).isTrue();
    pq.delete(3);
    assertThat(pq.contains(3)).isFalse();
    assertThat(pq.peekMinValue()).isEqualTo(101);
    assertThat(pq.peekMinKeyIndex()).isEqualTo(1);
    assertThat(pq.valueOf(1)).isEqualTo(101);
  }

  @Test
  public void testRandomInsertionsAndPolls() {
    for (int n = 1; n < 1500; n++) {
      int bound = 100000;
      int[] randomValues = genRandArray(n, -bound, +bound);
      MinIndexedBinaryHeap<Integer> pq1 = new MinIndexedBinaryHeap<Integer>(n);
      PriorityQueue<Integer> pq2 = new PriorityQueue<Integer>(n);

      final double p = Math.random();

      for (int i = 0; i < n; i++) {
        pq1.insert(i, randomValues[i]);
        pq2.add(randomValues[i]);

        if (Math.random() < p) {
          if (!pq2.isEmpty()) {
            assertThat(pq1.pollMinValue()).isEqualTo(pq2.poll());
          }
        }

        assertThat(pq1.size()).isEqualTo(pq2.size());
        assertThat(pq1.isEmpty()).isEqualTo(pq2.isEmpty());
        if (!pq2.isEmpty()) assertThat(pq1.peekMinValue()).isEqualTo(pq2.peek());
      }
    }
  }

  @Test
  public void testRandomInsertionsAndRemovals() {
    for (int n = 1; n < 500; n++) {
      List<Integer> indexes = genUniqueRandList(n);
      MinIndexedBinaryHeap<Integer> pq1 = new MinIndexedBinaryHeap<Integer>(n);
      PriorityQueue<Integer> pq2 = new PriorityQueue<Integer>(n);
      List<Integer> indexesToRemove = new ArrayList<>();

      final double p = Math.random();
      for (int i = 0; i < n; i++) {
        int ii = indexes.get(i);
        pq1.insert(ii, ii);
        pq2.add(ii);
        indexesToRemove.add(ii);
        assertThat(pq1.isMinHeap()).isTrue();

        if (Math.random() < p) {
          int itemsToRemove = (int) (Math.random() * 10);
          while (itemsToRemove-- > 0 && indexesToRemove.size() > 0) {
            int iii = (int) (Math.random() * indexesToRemove.size());
            int indexToRemove = indexesToRemove.get(iii);
            boolean contains1 = pq1.contains(indexToRemove);
            boolean contains2 = pq2.contains(indexToRemove);
            assertThat(contains1).isEqualTo(contains2);
            assertThat(pq1.isMinHeap()).isTrue();
            if (contains2) {
              pq1.delete(indexToRemove);
              pq2.remove(indexToRemove);
              indexesToRemove.remove(iii);
            }
            if (!pq2.isEmpty()) assertThat(pq1.peekMinValue()).isEqualTo(pq2.peek());
          }
        }

        for (int index : indexesToRemove) {
          assertThat(pq2.contains(index)).isTrue(); // Sanity check.
          assertThat(pq1.contains(index)).isTrue();
        }

        assertThat(pq1.size()).isEqualTo(pq2.size());
        assertThat(pq1.isEmpty()).isEqualTo(pq2.isEmpty());
        if (!pq2.isEmpty()) assertThat(pq1.peekMinValue()).isEqualTo(pq2.peek());
      }
    }
  }

  static int[] genRandArray(int n, int lo, int hi) {
    return new Random().ints(n, lo, hi).toArray();
  }

  static void sortPairsByValue(Integer[][] pairs) {
    Arrays.sort(
        pairs,
        new Comparator<Integer[]>() {
          @Override
          public int compare(Integer[] pair1, Integer[] pair2) {
            return pair1[1] - pair2[1];
          }
        });
  }

  // Generate a list of unique random numbers
  static List<Integer> genUniqueRandList(int sz) {
    List<Integer> lst = new ArrayList<>(sz);
    for (int i = 0; i < sz; i++) lst.add(i);
    Collections.shuffle(lst);
    return lst;
  }
}
