package com.williamfiset.algorithms.datastructures.stack;

import java.util.ArrayDeque;
import java.util.EmptyStackException;

/**
 * Integer-only Stack (fixed capacity)
 *
 * An extremely fast and lightweight stack for primitive ints. Can outperform
 * java.util.ArrayDeque by a large factor due to avoiding boxing/unboxing.
 * The trade-off is you must know an upper bound on the number of elements
 * at construction time.
 *
 * Time:  O(1) for push, pop, and peek
 * Space: O(maxSize)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
public class IntStack implements Stack<Integer> {

  private int[] ar;
  private int pos = 0;

  /**
   * Creates a stack with the given maximum capacity.
   *
   * @param maxSize the maximum number of elements the stack can hold
   */
  public IntStack(int maxSize) {
    ar = new int[maxSize];
  }

  @Override
  public int size() {
    return pos;
  }

  @Override
  public boolean isEmpty() {
    return pos == 0;
  }

  @Override
  public Integer peek() {
    if (isEmpty()) throw new EmptyStackException();
    return ar[pos - 1];
  }

  @Override
  public void push(Integer value) {
    if (pos == ar.length) throw new RuntimeException("Stack overflow: capacity exceeded");
    ar[pos++] = value;
  }

  @Override
  public Integer pop() {
    if (isEmpty()) throw new EmptyStackException();
    return ar[--pos];
  }

  // Example usage and benchmark
  public static void main(String[] args) {
    IntStack s = new IntStack(5);

    s.push(1);
    s.push(2);
    s.push(3);
    s.push(4);
    s.push(5);

    System.out.println(s.pop()); // 5
    System.out.println(s.pop()); // 4
    System.out.println(s.pop()); // 3

    s.push(3);
    s.push(4);
    s.push(5);

    while (!s.isEmpty()) System.out.println(s.pop());

    benchMarkTest();
  }

  private static void benchMarkTest() {
    int n = 10000000;
    IntStack intStack = new IntStack(n);

    long start = System.nanoTime();
    for (int i = 0; i < n; i++) intStack.push(i);
    for (int i = 0; i < n; i++) intStack.pop();
    long end = System.nanoTime();
    System.out.println("IntStack Time: " + (end - start) / 1e9);

    ArrayDeque<Integer> arrayDeque = new ArrayDeque<>(n);
    start = System.nanoTime();
    for (int i = 0; i < n; i++) arrayDeque.push(i);
    for (int i = 0; i < n; i++) arrayDeque.pop();
    end = System.nanoTime();
    System.out.println("ArrayDeque Time: " + (end - start) / 1e9);

    Stack<Integer> listStack = new ListStack<>();
    start = System.nanoTime();
    for (int i = 0; i < n; i++) listStack.push(i);
    for (int i = 0; i < n; i++) listStack.pop();
    end = System.nanoTime();
    System.out.println("ListStack Time: " + (end - start) / 1e9);

    Stack<Integer> arrayStack = new ArrayStack<>();
    start = System.nanoTime();
    for (int i = 0; i < n; i++) arrayStack.push(i);
    for (int i = 0; i < n; i++) arrayStack.pop();
    end = System.nanoTime();
    System.out.println("ArrayStack Time: " + (end - start) / 1e9);
  }
}
