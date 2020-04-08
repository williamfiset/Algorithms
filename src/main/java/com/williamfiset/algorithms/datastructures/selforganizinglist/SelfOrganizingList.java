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

  public void search(int key) {
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
      } else {
        System.out.println(key + " was found");
      }
    }
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
}
