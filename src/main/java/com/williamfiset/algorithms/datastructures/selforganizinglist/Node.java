package com.williamfiset.algorithms.datastructures.selforganizinglist;

/*
 * Class representing the elements of the list
 */
public class Node {
	int data;
	int count;
	Node prev;
	Node next;
	
	public Node(int data) {
		this.data = data;
		this.count = 0;
		this.prev = null;
		this.next = null;
	}

}
