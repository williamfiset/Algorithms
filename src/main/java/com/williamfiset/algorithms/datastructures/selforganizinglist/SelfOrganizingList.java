package com.williamfiset.algorithms.datastructures.selforganizinglist;

public class SelfOrganizingList {
  Node start;
  int totalNodes;

  public SelfOrganizingList() {
    start = null;
    totalNodes = 0;
  }

  public void insertNode(int value) {
    Node newNode = new Node(value);
    Node current = start;
    if (current == null) {
      start = newNode;
    } else {
      while (current.next != null) {
        current = current.next;
      }
      current.next = newNode;
      newNode.prev = current;
    }
    totalNodes++;
  }

  public boolean search(int key) {
    Node current = start;
    if (current == null) {
      System.out.println("List is empty");
    } else {
      while (current != null) {
        if (key == current.data) {
          current.count++;
          reorder();
          break;
        }
        current = current.next;
      }
      if (current == null) {
        System.out.println("Element " + key + " was not found");
        return false;
      } else {
        System.out.println(key + " was found");
        return true;
      }
    }
    return false;
  }

  public void reorder() {
    Node current = start;
    int tempdata;
    int tempcount;
    for (int i = 1; i < totalNodes; i++) {
      if (current.count < current.next.count) {
        tempdata = current.data;
        tempcount = current.count;
        current.data = current.next.data;
        current.count = current.next.count;
        current.next.data = tempdata;
        current.next.count = tempcount;
      }
      current = current.next;
    }
  }

  public void deleteNode(int position) {
    if (start == null) {
      System.out.println("List is empty");
    } else {
      if (position == 1) {
        System.out.println("Deleting first element " + start.data);
        if (start.next != null) {
          start.next.prev = null;
        }
        start = start.next;
        totalNodes--;
      } else if (position == totalNodes) {
        Node current = start;
        while (current.next != null) {
          current = current.next;
        }
        System.out.println("Deleting last element " + current.data);
        current.prev.next = null;
        current.prev = null;
        totalNodes--;
      } else if (position > 1 && position < totalNodes) {
        Node current = start;
        for (int i = 1; i < position - 1; i++) {
          current = current.next;
        }
        current.next = current.next.next;
        current.next.prev = current;
        totalNodes--;
      } else {
        System.out.println("There is no such position");
      }
    }
  }

  public int getSize() {
    return totalNodes;
  }

  public boolean isEmpty() {
    return start == null;
  }

  public void display() {
    Node current = start;
    if (current == null) {
      System.out.println("List is empty");
    } else {
      while (current.next != null) {
        System.out.print(current.data + "(count: " + current.count + ")->");
        current = current.next;
      }
      System.out.println(current.data + "(count: " + current.count + ")");
    }
  }
}
