package com.williamfiset.algorithms.datastructures.linkedlist;

/**
 * 
 * Node class for SingleLinkedList
 */
public class Node<T> {
	
	/**
	 * Properties
	 */
	private T value; 		//Node's value
	private Node<T> next;   //Next Node
	
	/**
	 * Methods
	 */
	
	//Constructor
	public Node(T value) {
		this.value = value;
	}
	
	//Getters and Setters
	public void setNext(Node<T> next) {
		this.next = next;
	}
	
	public Node<T> getNext() {
		return next;
	}
	
	public T getValue() {
		return value;
	}

}