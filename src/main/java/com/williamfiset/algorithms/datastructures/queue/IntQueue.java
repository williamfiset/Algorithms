/**
 * An integer-only queue backed by a fixed-size circular buffer. It is extremely quick and
 * lightweight, outperforming java.util.ArrayDeque (Java's fastest queue implementation) by ~7x.
 * See the benchmark test below for details.
 *
 * Design notes:
 * - The internal array capacity is rounded up to the next power of 2 so that index wrapping
 *   uses a cheap bitwise AND (& mask) instead of the costly modulo (%) operator.
 * - front and end pointers are always kept in the range [0, capacity-1] after every operation,
 *   avoiding lazy normalisation scattered across methods.
 * - A separate size counter tracks occupancy so full/empty states are unambiguous without
 *   reserving a sentinel slot.
 *
 * Limitation: you must know an upper bound on the number of elements in the queue at any given
 * time. Actual allocated capacity may be up to 2x that bound due to power-of-2 rounding.
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com, liujingkun, liujkon@gmail.com
 */
package com.williamfiset.algorithms.datastructures.queue;

public class IntQueue implements Queue<Integer> {

  private int[] data;
  private int front, end;
  private int size;
  private int mask; // capacity - 1, for fast modulo via bitwise AND (requires power-of-2 capacity)

  // maxSize is the maximum number of items that can be in the queue at any given time.
  // Actual capacity is rounded up to the next power of 2 for fast wrapping.
  public IntQueue(int maxSize) {
    int capacity = 1;
    while (capacity < maxSize) capacity <<= 1;
    data = new int[capacity];
    mask = capacity - 1;
    front = end = size = 0;
  }

  // Return true/false on whether the queue is empty
  public boolean isEmpty() {
    return size == 0;
  }

  // Return the number of elements inside the queue
  public int size() {
    return size;
  }

  @Override
  public Integer peek() {
    if (isEmpty()) {
      throw new RuntimeException("Queue is empty");
    }
    return data[front];
  }

  public boolean isFull() {
    return size == data.length;
  }

  // Add an element to the queue
  @Override
  public void offer(Integer value) {
    if (isFull()) {
      throw new RuntimeException("Queue too small!");
    }
    data[end] = value;
    end = (end + 1) & mask;
    size++;
  }

  // Make sure you check is the queue is not empty before calling poll!
  @Override
  public Integer poll() {
    if (size == 0) {
      throw new RuntimeException("Queue is empty");
    }
    int val = data[front];
    front = (front + 1) & mask;
    size--;
    return val;
  }

  // Example usage
  public static void main(String[] args) {

    IntQueue q = new IntQueue(5);

    q.offer(1);
    q.offer(2);
    q.offer(3);
    q.offer(4);
    q.offer(5);

    System.out.println(q.poll()); // 1
    System.out.println(q.poll()); // 2
    System.out.println(q.poll()); // 3
    System.out.println(q.poll()); // 4

    System.out.println(q.isEmpty()); // false

    q.offer(1);
    q.offer(2);
    q.offer(3);

    System.out.println(q.poll()); // 5
    System.out.println(q.poll()); // 1
    System.out.println(q.poll()); // 2
    System.out.println(q.poll()); // 3

    System.out.println(q.isEmpty()); // true

    benchMarkTest();
  }

  // BenchMark IntQueue vs ArrayDeque.
  private static void benchMarkTest() {
    int n = 50000000;
    System.out.println("IntQueue Time:   " + timeIntQueue(n));
    System.out.println("ArrayDeque Time: " + timeArrayDeque(n));
  }

  private static double timeIntQueue(int n) {
    IntQueue q = new IntQueue(n);
    long start = System.nanoTime();
    for (int i = 0; i < n; i++) q.offer(i);
    for (int i = 0; i < n; i++) q.poll();
    return (System.nanoTime() - start) / 1e9;
  }

  private static double timeArrayDeque(int n) {
    java.util.ArrayDeque<Integer> q = new java.util.ArrayDeque<>();
    long start = System.nanoTime();
    for (int i = 0; i < n; i++) q.offer(i);
    for (int i = 0; i < n; i++) q.poll();
    return (System.nanoTime() - start) / 1e9;
  }
}
