/**
 * This file contains an implementation of an integer only stack which is extremely quick and
 * lightweight. In terms of performance it can outperform java.util.ArrayDeque (Java's fastest stack
 * implementation) by a factor of 50! See the benchmark test below for proof. However, the downside
 * is you need to know an upper bound on the number of elements that will be inside the stack at any
 * given time for it to work correctly.
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 * @contribuitor César Miranda Meza, cmirandameza3@hotmail.com
 */
package com.williamfiset.algorithms.datastructures.stack;

public class IntStack implements Stack<Integer> {

  private int[] ar;
  private int pos = 0;
  private int maxSize;

  // maxSize is the maximum number of items
  // that can be in the queue at any given time
  public IntStack(int maxSize) {
	ar = new int[maxSize];
	this.maxSize = maxSize;
  }

  // Returns the number of elements insize the stack
  public int size() {
    return pos;
  }

  // Returns true/false on whether the stack is empty
  public boolean isEmpty() {
    return pos == 0;
  }

  // Returns the element at the top of the stack
  @Override
  public Integer peek() {
	if (isEmpty()) throw new IllegalArgumentException("Illegal use of \"peek()\" method. There is no data stored in the current Stack.");
    return ar[pos - 1];
  }

  // Add an element to the top of the stack
  @Override
  public void push(Integer value) {
	if (this.maxSize == size()) throw new IllegalArgumentException("Illegal use of \"push()\" method. The data stored/pushed into the current Stack has exceeded the size limit.");
    ar[pos++] = value;
  }

  // Make sure you check that the stack is not empty before calling pop!
  @Override
  public Integer pop() {
	if (isEmpty()) throw new IllegalArgumentException("Illegal use of \"pop()\" method. There is no data stored in the current Stack.");
    return ar[--pos];
  }

  // Example usage
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

  // BenchMark IntStack vs ArrayDeque.
  private static void benchMarkTest() {

    int n = 10000000;
    IntStack intStack = new IntStack(n);

    // IntStack times at around 0.0324 seconds
    long start = System.nanoTime();
    for (int i = 0; i < n; i++) intStack.push(i);
    for (int i = 0; i < n; i++) intStack.pop();
    long end = System.nanoTime();
    System.out.println("IntStack Time: " + (end - start) / 1e9);

    // ArrayDeque times at around 1.438 seconds
    //    java.util.ArrayDeque<Integer> arrayDeque = new java.util.ArrayDeque<>();
    //    java.util.Stack<Integer> arrayDeque = new java.util.Stack<>();
    java.util.ArrayDeque<Integer> arrayDeque = new java.util.ArrayDeque<>(n); // strangely the
    // ArrayQueue is slower when you give it an initial capacity.
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
