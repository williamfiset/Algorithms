package com.williamfiset.algorithms.datastructures.stack;

/**
 * Stack Interface
 *
 * Defines the core operations for a last-in, first-out (LIFO) data structure.
 * Implementations include array-based, linked-list-based, and primitive int variants.
 *
 * @author liujingkun
 */
public interface Stack<T> {

  /** Returns the number of elements in the stack. */
  public int size();

  /** Returns true if the stack has no elements. */
  public boolean isEmpty();

  /** Pushes an element onto the top of the stack. */
  public void push(T elem);

  /**
   * Removes and returns the top element of the stack.
   *
   * @throws java.util.EmptyStackException if the stack is empty
   */
  public T pop();

  /**
   * Returns the top element without removing it.
   *
   * @throws java.util.EmptyStackException if the stack is empty
   */
  public T peek();
}
