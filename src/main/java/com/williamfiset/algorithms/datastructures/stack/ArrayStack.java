package com.williamfiset.algorithms.datastructures.stack;

import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * Array-based Stack
 *
 * A generic stack backed by a dynamically resizing array. Starts with
 * capacity 16 and doubles when full. Push, pop, and peek are all O(1)
 * amortized.
 *
 * Time:  O(1) amortized for push, O(1) for pop and peek
 * Space: O(n)
 *
 * @author liujingkun
 */
public class ArrayStack<T> implements Stack<T> {
  private int size;
  private int capacity;
  private Object[] data;

  public ArrayStack() {
    capacity = 16;
    data = new Object[capacity];
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  @Override
  public void push(T elem) {
    if (size == capacity) {
      increaseCapacity();
    }
    data[size++] = elem;
  }

  // Increase the capacity to store more elements.
  private void increaseCapacity() {
    capacity *= 2;
    data = Arrays.copyOf(data, capacity);
  }

  @Override
  @SuppressWarnings("unchecked")
  public T pop() {
    if (isEmpty()) throw new EmptyStackException();
    T elem = (T) data[--size];
    data[size] = null;
    return elem;
  }

  @Override
  @SuppressWarnings("unchecked")
  public T peek() {
    if (isEmpty()) throw new EmptyStackException();
    return (T) data[size - 1];
  }
}
