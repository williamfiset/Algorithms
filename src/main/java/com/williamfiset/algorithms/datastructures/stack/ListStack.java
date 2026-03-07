package com.williamfiset.algorithms.datastructures.stack;

import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Linked List Stack
 *
 * A generic stack backed by a LinkedList. Supports iteration in LIFO order.
 * All core operations (push, pop, peek) run in O(1) time.
 *
 * Time:  O(1) for push, pop, and peek
 * Space: O(n)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
public class ListStack<T> implements Iterable<T>, Stack<T> {

  private LinkedList<T> list = new LinkedList<>();

  public ListStack() {}

  public ListStack(T firstElem) {
    push(firstElem);
  }

  @Override
  public int size() {
    return list.size();
  }

  @Override
  public boolean isEmpty() {
    return size() == 0;
  }

  @Override
  public void push(T elem) {
    list.addLast(elem);
  }

  @Override
  public T pop() {
    if (isEmpty()) throw new EmptyStackException();
    return list.removeLast();
  }

  @Override
  public T peek() {
    if (isEmpty()) throw new EmptyStackException();
    return list.peekLast();
  }

  // Searches for the element starting from top of the stack.
  // Returns -1 if the element is not present in the stack.
  public int search(T elem) {
    return list.lastIndexOf(elem);
  }

  @Override
  public Iterator<T> iterator() {
    return list.descendingIterator();
  }
}
