package com.williamfiset.algorithms.datastructures.queue;

/**
 * Besides the Generics, the loss of property of size is another difference between ArrayQueue and
 * IntQueue. The size of ArrayQueue is calculated by the formula, as are empty status and full
 * status.
 *
 * <p>ArrayQueue maximum size is data.length - 1. The place of the variable rear is always in front
 * of the variable front logistically if regard the data array as circular. so the number of states
 * of the combination of rear and front is the length of the data array. And one of the total states
 * is used to be the judge if the queue is empty or full.
 *
 * @author liujingkun, liujkon@gmail.com
 */
public class ArrayQueue<T> implements Queue<T> {
  private Object[] data;
  private int front;
  private int rear;

  public ArrayQueue(int capacity) {
    // ArrayQueue maximum size is data.length - 1.
    data = new Object[capacity + 1];
    front = 0;
    rear = 0;
  }

  @Override
  public void offer(T elem) {
    if (isFull()) {
      throw new RuntimeException("Queue is full");
    }
    data[rear++] = elem;
    rear = adjustIndex(rear, data.length);
  }

  @Override
  @SuppressWarnings("unchecked")
  public T poll() {
    if (isEmpty()) {
      throw new RuntimeException("Queue is empty");
    }
    front = adjustIndex(front, data.length);
    return (T) data[front++];
  }

  @Override
  @SuppressWarnings("unchecked")
  public T peek() {
    if (isEmpty()) {
      throw new RuntimeException("Queue is empty");
    }
    front = adjustIndex(front, data.length);
    return (T) data[front];
  }

  @Override
  public int size() {
    return adjustIndex(rear + data.length - front, data.length);
  }

  @Override
  public boolean isEmpty() {
    return rear == front;
  }

  public boolean isFull() {
    return (front + data.length - rear) % data.length == 1;
  }

  private int adjustIndex(int index, int size) {
    return index >= size ? index - size : index;
  }
}
