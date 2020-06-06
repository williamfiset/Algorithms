package com.williamfiset.algorithms.datastructures.queue;

/**
 * Besides the Generics, the loss of property of size is another difference between ArrayQueue and
 * IntQueue. The size of ArrayQueue is calculated by the formula, as are empty status and full
 * status.
 *
 * @author liujingkun, liujkon@gmail.com
 */
public class ArrayQueue<T> implements Queue<T> {
  private Object[] data;
  private int front;
  private int rear;

  public ArrayQueue(int capacity) {
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
    rear = rear % data.length;
  }

  @Override
  @SuppressWarnings("unchecked")
  public T poll() {
    if (isEmpty()) {
      throw new RuntimeException("Queue is empty");
    }
    front = front % data.length;
    return (T) data[front++];
  }

  @Override
  @SuppressWarnings("unchecked")
  public T peek() {
    if (isEmpty()) {
      throw new RuntimeException("Queue is empty");
    }
    front = front % data.length;
    return (T) data[front];
  }

  @Override
  public int size() {
    return (rear + data.length - front) % data.length;
  }

  @Override
  public boolean isEmpty() {
    return rear == front;
  }

  public boolean isFull() {
    return (front + data.length - rear) % data.length == 1;
  }
}
