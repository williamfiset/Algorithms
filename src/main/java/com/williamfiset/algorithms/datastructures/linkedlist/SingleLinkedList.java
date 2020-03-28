
package com.williamfiset.algorithms.datastructures.linkedlist;

public class SingleLinkedList<T> {
	private Node<T> first = null;  //First Element
	
	/**
	 * Insert new element at head
	 * New node is head of list
	 * 
	 */
	
	public void insert(Node<T> node) {
		node.setNext(first);
		first = node;
	}
	
	/**
	 * Remove head element
	 */
	
	public void remove() {
		if (first.getNext() != null) {
			first = first.getNext();
		} else {
			first = null;
		}
	}
	
	public Node<T> getHead() {
		return first;
	}
}
