package com.williamfiset.algorithms.datastructures.linkedlist.selfOrganizingList;

/*
 * Class representing an element from the list
 */


public class Node {
    int data;
    int count;
    Node prev;
    Node next;
    
    public Node(int value) {
        this.data = value;
        this.count = 0;
        this.prev = null;
        this.next = null;
    }
}