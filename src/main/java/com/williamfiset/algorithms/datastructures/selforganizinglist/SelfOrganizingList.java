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
}
