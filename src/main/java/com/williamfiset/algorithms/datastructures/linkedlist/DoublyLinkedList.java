/**
 * A doubly linked list implementation.
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.datastructures.linkedlist;


class Node<T> {
  private T data;
  private Node<T> prev;
  private Node<T> next;

  public Node(T data, Node<T> prev, Node<T> next) {
    this.data = data;
    this.prev = prev;
    this.next = next;
  }

  public T getData() {
    return this.data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public Node<T> getPrev() {
    return this.prev;
  }
  
  public void setPrev(Node<T> node) {
    this.prev = node;
  }

  public Node<T> getNext() {
    return this.next;
  }

  public void setNext(Node<T> node) {
    this.next = node;
  }

  public String toString() {
    return this.data.toString();
  }
}

public class DoublyLinkedList<T> implements Iterable<T> {
  private int size = 0;
  private Node<T> head = null;
  private Node<T> tail = null;

  public void clear() {
    Node<T> trav = this.head;

    while (trav != null) {
      Node<T> next = trav.getNext();

      trav.setData(null);
      trav.setNext(null);
      trav.setPrev(null);

      trav = next;
    }

    this.size = 0;
    this.head = null;
    this.tail = null;
    trav = null;
  }

  public int size() {
    return this.size;
  }

  public boolean isEmpty() {
    return this.size() == 0;
  }

  public void add(T element) {
    this.addLast(element);
  }

  public void addLast(T element) {
    if (this.isEmpty()) {
      this.head = this.tail = new Node(element, null, null);
    }
    else {
      Node<T> node = new Node(element, this.tail, null);
      this.tail.setNext(node);
      this.tail = node;
    }
    ++this.size;
  }

  public void addFirst(T element) {
    if (this.isEmpty()) {
      this.head = this.tail = new Node(element, null, null);
    }
    else {
      Node<T> node = new Node(element, null, this.head);
      this.head.setPrev(node);
      this.head = node;
    }
  }

  public void addAt(int index, T element) throws Exception {
    if (index < 0 || index > this.size()) {
      throw new Exception("Illegal Index");
    }
    else if (index == 0) {
      this.addFirst(element);
    }
    else if (index == this.size) {
      this.addLast(element);
    }
    else {
      Node<T> trav = null;
      for (int i = 0; i < index; ++i) {
        trav = (i == 0) ? this.head : trav.getNext();
      }

      Node<T> node = new Node(element, trav, trav.getNext());
      trav.getNext().setPrev(node);
      trav.setNext(node);
      ++this.size;
    }
  }

  public T peekFirst() {
    if (this.isEmpty()) {
      throw new RuntimeException("Empty list");
    }
    else {
      return this.head.getData();
    }
  }

  public T peekLast() {
    if (this.isEmpty()) {
      throw new RuntimeException("Empty list");
    }
    else {
      return this.tail.getData();
    }
  }

  public T removeFirst() {
    if (this.isEmpty()) {
      throw new RuntimeException("Empty list");
    }
    else {
      T data = this.head.getData();
      this.head = this.head.getNext();
      --this.size;

      if (this.isEmpty()) {
        this.tail = null;
      }
      else {
        this.head.setPrev(null);
      }

      return data;
    }
  }

  public T removeLast() {
    if (this.isEmpty()) {
      throw new RuntimeException("Empty list");
    }
    else {
      T data = this.tail.getData();
      this.tail = this.tail.getPrev();
      --this.size;

      if (this.isEmpty()) {
        this.head = null;
      }
      else {
        this.tail.setNext(null);
      }

      return data;
    }
  }

  private T remove(Node<T> node) {
    /* if the node is the head */
    if (node.getPrev() == null) {
      return this.removeFirst();
    }
    /* if the node is the tail */
    else if (node.getNext() == null) {
      return this.removeLast();
    }
    /* otherwise */
    else {
      T data = node.getData();
      --this.size;

      node.getNext().setPrev(node.getPrev());
      node.getPrev().setNext(node.getNext());

      /* clean up */
      node.setData(null);
      node.setPrev(null);
      node.setNext(null);
      node = null;

      return data;
    }
  }

  public T removeAt(int index) {
    if (index < 0 || index >= this.size()) {
      throw new IllegalArgumentException();
    }
    else {
      int midIndex = 0 + this.size() >>> 1;
      Node<T> node = null;

      if (index < midIndex) {
        for (int i = 0; i < midIndex; ++i) {
          node = (i == 0) ? this.head : node.getNext();
          if (i == index) {
            break;
          }
        }
      }
      else {
        for (int i = this.size() - 1; i >= midIndex; --i) {
          node = (i == this.size() - 1) ? this.tail : node.getPrev();
          if (i == index) {
            break;
          }
        }
      }
      T data = node.getData();
      this.remove(node);        
      return data;
    }
  }
  
  public boolean remove(Object obj) {
    Node<T> node = null;

    for (int i = 0; i < this.size(); ++i) {
      node = (i == 0) ? this.head : node.getNext();
      if (obj == null && node.getData() == null) {
        this.remove(node);
        return true;
      }
      else {
        if (obj.equals(node.getData())) {
          this.remove(node);
        return true;
        }
      }
    }

    /* if you can't find the value in the linkedlist */
    return false;
  }

  public int indexOf(Object obj) {
    int index = -1;
    Node<T> node = null;

    for (int i = 0; i < this.size(); ++i) {
      node = (i == 0) ? this.head : node.getNext();
      if (obj == null && node.getData() == null) {
        index = i;
        break;
      }
      else {
        if (obj.equals(node.getData())) {
          index = i;
          break;
        }
      }
    }

    return index;
  }

  public boolean contains(Object obj) {
    return this.indexOf(obj) > -1;
  }

  @Override
  public java.util.Iterator<T> iterator() {
    return new java.util.Iterator<T>() {
      private Node<T> trav = head;

      @Override
      public boolean hasNext() {
        return trav != null;
      }

      @Override
      public T next() {
        T data = trav.getData();
        trav = trav.getNext();
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
    String string = "[";
    Node<T> node = null;
  
    for (int i = 0; i < this.size(); ++i) {
      node = (i == 0) ? this.head : node.getNext();
      string += (i < this.size() - 1) ? node.getData() + ", " : node.getData();
    }

    string += "]";
    return string;
  }
}
