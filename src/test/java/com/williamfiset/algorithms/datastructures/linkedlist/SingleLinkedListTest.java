
package com.williamfiset.algorithms.datastructures.linkedlist;

import static org.junit.Assert.*;

import org.junit.Test;

public class SingleLinkedListTest {
	
	@Test
	public void testInsert() {
		SingleLinkedList<Integer> integerList = new SingleLinkedList<>();
		integerList.insert(new Node<Integer>(42));
		assertTrue(integerList.getHead().getValue() == 42);
		assertNull(integerList.getHead().getNext());
		
	}
	
	@Test
	public void testRemove() {
		SingleLinkedList<Integer> integerList = new SingleLinkedList<>();
		integerList.insert(new Node<Integer>(42));
		integerList.insert(new Node<Integer>(17));
		assertTrue(integerList.getHead().getValue() == 17);
		assertTrue(integerList.getHead().getNext().getValue() == 42);
		integerList.remove();
		assertTrue(integerList.getHead().getValue() == 42);
		assertNull(integerList.getHead().getNext());
		
	}

}
