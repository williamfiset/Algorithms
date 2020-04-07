package com.williamfiset.algorithms.datastructures.selforganizinglist;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class NodeTest {
	Node node;

	@Before
	public void setUp() {
		node = new Node (5);
	}

	@Test
	public void testNode() {
		assertTrue(node != null);
		assertEquals(0, node.count);
		assertEquals(5, node.data);
	}
}
