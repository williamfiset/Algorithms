package com.williamfiset.algorithms.datastructures.priorityqueue;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import org.junit.*;

public class BinaryHeapQuickRemovalsTest {

  static final int LOOPS = 100;
  static final int MAX_SZ = 100;

  @Before
  public void setup() {}

  @Test
  public void testEmpty() {
    BinaryHeapQuickRemovals<Integer> q = new BinaryHeapQuickRemovals<>();
    assertEquals(q.size(), 0);
    assertTrue(q.isEmpty());
    assertEquals(q.poll(), null);
    assertEquals(q.peek(), null);
  }

  @Test
  public void testHeapProperty() {

    BinaryHeapQuickRemovals<Integer> q = new BinaryHeapQuickRemovals<>();
    Integer[] nums = {3, 2, 5, 6, 7, 9, 4, 8, 1};

    // Try manually creating heap
    for (int n : nums) q.add(n);
    for (int i = 1; i <= 9; i++) assertTrue(i == q.poll());

    q.clear();

    // Try heapify constructor
    q = new BinaryHeapQuickRemovals<>(nums);
    for (int i = 1; i <= 9; i++) assertTrue(i == q.poll());
  }

  @Test
  public void testHeapify() {

    for (int i = 1; i < LOOPS; i++) {

      Integer[] lst = genRandArray(i);
      BinaryHeapQuickRemovals<Integer> pq = new BinaryHeapQuickRemovals<>(lst);

      PriorityQueue<Integer> pq2 = new PriorityQueue<>(i);
      for (int x : lst) pq2.add(x);

      assertTrue(pq.isMinHeap(0));
      while (!pq2.isEmpty()) {
        assertEquals(pq.poll(), pq2.poll());
      }
    }
  }

  @Test
  public void testClear() {

    BinaryHeapQuickRemovals<String> q;
    String[] strs = {"aa", "bb", "cc", "dd", "ee"};
    q = new BinaryHeapQuickRemovals<>(strs);
    q.clear();
    assertEquals(q.size(), 0);
    assertTrue(q.isEmpty());
  }

  @Test
  public void testContainment() {

    String[] strs = {"aa", "bb", "cc", "dd", "ee"};
    BinaryHeapQuickRemovals<String> q = new BinaryHeapQuickRemovals<>(strs);
    q.remove("aa");
    assertFalse(q.contains("aa"));
    q.remove("bb");
    assertFalse(q.contains("bb"));
    q.remove("cc");
    assertFalse(q.contains("cc"));
    q.remove("dd");
    assertFalse(q.contains("dd"));
    q.clear();
    assertFalse(q.contains("ee"));
  }

  @Test
  public void testContainmentRandomized() {

    for (int i = 0; i < LOOPS; i++) {

      List<Integer> randNums = genRandList(100);
      PriorityQueue<Integer> PQ = new PriorityQueue<>();
      BinaryHeapQuickRemovals<Integer> pq = new BinaryHeapQuickRemovals<>();
      for (int j = 0; j < randNums.size(); j++) {
        pq.add(randNums.get(j));
        PQ.add(randNums.get(j));
      }

      for (int j = 0; j < randNums.size(); j++) {

        int randVal = randNums.get(j);
        assertEquals(pq.contains(randVal), PQ.contains(randVal));
        pq.remove(randVal);
        PQ.remove(randVal);
        assertEquals(pq.contains(randVal), PQ.contains(randVal));
      }
    }
  }

  public void sequentialRemoving(Integer[] in, Integer[] removeOrder) {

    assertEquals(in.length, removeOrder.length);

    BinaryHeapQuickRemovals<Integer> pq = new BinaryHeapQuickRemovals<>(in);
    PriorityQueue<Integer> PQ = new PriorityQueue<>();
    for (int value : in) PQ.offer(value);

    assertTrue(pq.isMinHeap(0));

    for (int i = 0; i < removeOrder.length; i++) {

      int elem = removeOrder[i];

      assertTrue(pq.peek() == PQ.peek());
      assertEquals(pq.remove(elem), PQ.remove(elem));
      assertTrue(pq.size() == PQ.size());
      assertTrue(pq.isMinHeap(0));
    }

    assertTrue(pq.isEmpty());
  }

  @Test
  public void testRemoving() {

    Integer[] in = {1, 2, 3, 4, 5, 6, 7};
    Integer[] removeOrder = {1, 3, 6, 4, 5, 7, 2};
    sequentialRemoving(in, removeOrder);

    in = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
    removeOrder = new Integer[] {7, 4, 6, 10, 2, 5, 11, 3, 1, 8, 9};
    sequentialRemoving(in, removeOrder);

    in = new Integer[] {8, 1, 3, 3, 5, 3};
    removeOrder = new Integer[] {3, 3, 5, 8, 1, 3};
    sequentialRemoving(in, removeOrder);

    in = new Integer[] {7, 7, 3, 1, 1, 2};
    removeOrder = new Integer[] {2, 7, 1, 3, 7, 1};
    sequentialRemoving(in, removeOrder);

    in = new Integer[] {32, 66, 93, 42, 41, 91, 54, 64, 9, 35};
    removeOrder = new Integer[] {64, 93, 54, 41, 35, 9, 66, 42, 32, 91};
    sequentialRemoving(in, removeOrder);
  }

  @Test
  public void testRemovingDuplicates() {

    Integer[] in = new Integer[] {2, 7, 2, 11, 7, 13, 2};
    BinaryHeapQuickRemovals<Integer> pq = new BinaryHeapQuickRemovals<>(in);

    assertTrue(pq.peek() == 2);
    pq.add(3);

    assertTrue(pq.poll() == 2);
    assertTrue(pq.poll() == 2);
    assertTrue(pq.poll() == 2);
    assertTrue(pq.poll() == 3);
    assertTrue(pq.poll() == 7);
    assertTrue(pq.poll() == 7);
    assertTrue(pq.poll() == 11);
    assertTrue(pq.poll() == 13);
  }

  @Test
  public void testRandomizedPolling() {

    for (int i = 0; i < LOOPS; i++) {

      int sz = i;
      List<Integer> randNums = genRandList(sz);
      PriorityQueue<Integer> pq1 = new PriorityQueue<>();
      BinaryHeapQuickRemovals<Integer> pq2 = new BinaryHeapQuickRemovals<>();

      // Add all the elements to both priority queues
      for (Integer value : randNums) {
        pq1.offer(value);
        pq2.add(value);
      }

      while (!pq1.isEmpty()) {

        assertTrue(pq2.isMinHeap(0));
        assertEquals(pq1.size(), pq2.size());
        assertEquals(pq1.peek(), pq2.peek());
        assertEquals(pq1.contains(pq1.peek()), pq2.contains(pq2.peek()));

        Integer v1 = pq1.poll();
        Integer v2 = pq2.poll();

        assertEquals(v1, v2);
        assertEquals(pq1.peek(), pq2.peek());
        assertEquals(pq1.size(), pq2.size());
        assertTrue(pq2.isMinHeap(0));
      }
    }
  }

  @Test
  public void testRandomizedRemoving() {

    for (int i = 0; i < LOOPS; i++) {

      int sz = i;
      List<Integer> randNums = genRandList(sz);
      PriorityQueue<Integer> pq1 = new PriorityQueue<>();
      BinaryHeapQuickRemovals<Integer> pq2 = new BinaryHeapQuickRemovals<>();

      // Add all the elements to both priority queues
      for (Integer value : randNums) {
        pq1.offer(value);
        pq2.add(value);
      }

      Collections.shuffle(randNums);
      int index = 0;

      while (!pq1.isEmpty()) {

        int removeNum = randNums.get(index++);

        assertTrue(pq2.isMinHeap(0));
        assertEquals(pq1.size(), pq2.size());
        assertEquals(pq1.peek(), pq2.peek());
        pq1.remove(removeNum);
        pq2.remove(removeNum);
        assertEquals(pq1.peek(), pq2.peek());
        assertEquals(pq1.size(), pq2.size());
        assertTrue(pq2.isMinHeap(0));
      }
    }
  }

  @Test
  public void testPQReusability() {

    List<Integer> SZs = genUniqueRandList(LOOPS);

    PriorityQueue<Integer> PQ = new PriorityQueue<>();
    BinaryHeapQuickRemovals<Integer> pq = new BinaryHeapQuickRemovals<>();

    for (int sz : SZs) {

      pq.clear();
      PQ.clear();

      List<Integer> nums = genRandList(sz);
      for (int n : nums) {
        pq.add(n);
        PQ.add(n);
      }

      Collections.shuffle(nums);

      for (int i = 0; i < sz / 2; i++) {

        // Sometimes add a new number into the BinaryHeapQuickRemovals
        if (0.25 < Math.random()) {
          int randNum = (int) (Math.random() * 10000);
          PQ.add(randNum);
          pq.add(randNum);
        }

        int removeNum = nums.get(i);

        assertTrue(pq.isMinHeap(0));
        assertEquals(PQ.size(), pq.size());
        assertEquals(PQ.peek(), pq.peek());

        PQ.remove(removeNum);
        pq.remove(removeNum);

        assertEquals(PQ.peek(), pq.peek());
        assertEquals(PQ.size(), pq.size());
        assertTrue(pq.isMinHeap(0));
      }
    }
  }

  static Integer[] genRandArray(int sz) {
    Integer[] lst = new Integer[sz];
    for (int i = 0; i < sz; i++) lst[i] = (int) (Math.random() * MAX_SZ);
    return lst;
  }

  // Generate a list of random numbers
  static List<Integer> genRandList(int sz) {
    List<Integer> lst = new ArrayList<>(sz);
    for (int i = 0; i < sz; i++) lst.add((int) (Math.random() * MAX_SZ));
    return lst;
  }

  // Generate a list of unique random numbers
  static List<Integer> genUniqueRandList(int sz) {
    List<Integer> lst = new ArrayList<>(sz);
    for (int i = 0; i < sz; i++) lst.add(i);
    Collections.shuffle(lst);
    return lst;
  }
}
