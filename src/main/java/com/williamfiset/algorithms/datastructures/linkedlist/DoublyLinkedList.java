/**
 * A doubly linked list implementation.
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.datastructures.linkedlist;

public class DoublyLinkedList<T> implements Iterable<T> {
  private int size = 0;
  private Node<T> head = null;
  private Node<T> tail = null;

  // Internal node class to represent data
  private static class Node<T> {
    private T data;
    private Node<T> previous, next;

    public Node(T data, Node<T> previous, Node<T> next) {
      this.data = data;
      this.previous = previous;
      this.next = next;
    }

    public void clear() {
      data = null;
      previous = null;
      next = null;
    }

    @Override
    public String toString() {
      return data.toString();
    }
  }

  // Empty this linked list, O(n)
  public void clear() {
    Node<T> current = head;
    while (current != null) {
      Node<T> next = current.next;
      current.clear();
      current = next;
    }
    head = tail = current = null;
    size = 0;
  }

  // Return the size of this linked list
  public int size() {
    return size;
  }

  // Is this linked list empty?
  public boolean isEmpty() {
    return size() == 0;
  }

  // Add an element to the tail of the linked list, O(1)
  public void add(T data) {
    addLast(data);
  }

  // Add a node to the tail of the linked list, O(1)
  public void addLast(T data) {
    if (isEmpty()) {
      head = tail = new Node<T>(data, null, null);
    } else {
      tail.next = new Node<T>(data, tail, null);
      tail = tail.next;
    }
    size++;
  }

  // Add an element to the beginning of this linked list, O(1)
  public void addFirst(T data) {
    if (isEmpty()) {
      head = tail = new Node<T>(data, null, null);
    } else {
      head.previous = new Node<T>(data, null, head);
      head = head.previous;
    }
    size++;
  }

  // Add an element at a specified index, O(n)
  public void addAt(int index, T data) throws Exception {
    if (index < 0 || index > size) {
      throw new IllegalArgumentException("Illegal index");
    }
    if (index == 0) {
      addFirst(data);
      return;
    }
    if (index == size) {
      addLast(data);
      return;
    }

    Node<T> leftNode = getNodeAt(index - 1);
    Node<T> rightNode = leftNode.next;
    Node<T> newNode = new Node<>(data, leftNode, rightNode);
    leftNode.next = rightNode.previous = newNode;

    size++;
  }

  private Node<T> getNodeAt(int index) {
    Node<T> node = head;
    for (int i = 0; i < index; i++) {
      node = node.next;
    }
    return node;
  }

  private void checkEmptyListException() {
    if (isEmpty()) {
      throw new RuntimeException("Empty list");
    }
  }

  // Check the value of the first node if it exists, O(1)
  public T peekFirst() {
    checkEmptyListException();
    return head.data;
  }

  // Check the value of the last node if it exists, O(1)
  public T peekLast() {
    checkEmptyListException();
    return tail.data;
  }

  // Remove the first value at the head of the linked list, O(1)
  public T removeFirst() {
    // Can't remove data from an empty list
    checkEmptyListException();

    // Extract the data at the head and move
    // the head pointer forwards one node
    T data = head.data;
    head = head.next;
    --size;

    // If the list is empty set the tail to null
    if (isEmpty()) {
      tail = null;
    } else {
      head.previous = null;
    }

    // Return the data that was at the first node we just removed
    return data;
  }

  // Remove the last value at the tail of the linked list, O(1)
  public T removeLast() {
    // Can't remove data from an empty list
    checkEmptyListException();

    // Extract the data at the tail and move
    // the tail pointer backwards one node
    T data = tail.data;
    tail = tail.previous;
    --size;

    // If the list is now empty set the head to null
    if (isEmpty()) {
      head = null;
    } else {
      tail.next = null;
    }

    // Return the data that was in the last node we just removed
    return data;
  }

  // Remove an arbitrary node from the linked list, O(1)
  private T remove(Node<T> node) {
    // If the node to remove is somewhere either at the
    // head or the tail handle those independently
    if (node.previous == null) {
      return removeFirst();
    }
    if (node.next == null) {
      return removeLast();
    }

    // Make the pointers of adjacent nodes skip over 'node'
    Node<T> leftNode = node.previous;
    Node<T> rightNode = node.next;
    leftNode.next = rightNode;
    rightNode.previous = leftNode;

    // Temporarily store the data we want to return
    T data = node.data;

    // Memory cleanup
    node.clear();
    node = null;

    --size;

    // Return the data in the node we just removed
    return data;
  }

  // Remove a node at a particular index, O(n)
  public T removeAt(int index) {
    // Make sure the index provided is valid
    if (index < 0 || index >= size) {
      throw new IllegalArgumentException("Illegal index");
    }

    int i;
    Node<T> current;

    // Search from the front of the list
    if (index < size / 2) {
      for (i = 0, current = head; i != index; i++) {
        current = current.next;
      }
      // Search from the back of the list
    } else {
      for (i = size - 1, current = tail; i != index; i--) {
        current = current.previous;
      }
    }

    return remove(current);
  }

  // Remove a particular value in the linked list, O(n)
  public boolean remove(Object obj) {
    Node<T> current = head;

    // Support searching for null
    if (obj == null) {
      for (; current != null; current = current.next) {
        if (current.data == null) {
          remove(current);
          return true;
        }
      }
      // Search for non null object
    } else {
      for (; current != null; current = current.next) {
        if (obj.equals(current.data)) {
          remove(current);
          return true;
        }
      }
    }
    return false;
  }

  // Find the index of a particular value in the linked list, O(n)
  public int indexOf(Object obj) {
    int index = 0;
    Node<T> current = head;

    // Support searching for null
    if (obj == null) {
      for (; current != null; current = current.next, index++) {
        if (current.data == null) {
          return index;
        }
      }
      // Search for non null object
    } else {
      for (; current != null; current = current.next, index++) {
        if (obj.equals(current.data)) {
          return index;
        }
      }
    }

    return -1;
  }

  // Check is a value is contained within the linked list
  public boolean contains(Object obj) {
    return indexOf(obj) != -1;
  }

  @Override
  public java.util.Iterator<T> iterator() {
    return new java.util.Iterator<T>() {
      private Node<T> current = head;

      @Override
      public boolean hasNext() {
        return current != null;
      }

      @Override
      public T next() {
        T data = current.data;
        current = current.next;
        return data;
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[ ");
    Node<T> trav = head;
    while (trav != null) {
      sb.append(trav.data);
      if (trav.next != null) {
        sb.append(", ");
      }
      trav = trav.next;
    }
    sb.append(" ]");
    return sb.toString();
  }
}
