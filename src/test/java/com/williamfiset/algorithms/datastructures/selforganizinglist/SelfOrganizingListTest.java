package com.williamfiset.algorithms.datastructures.selforganizinglist;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SelfOrganizingListTest {
	SelfOrganizingList list;

  @BeforeEach
  void setUp(){
	  list = new SelfOrganizingList();
  }

  @Test
  void testSelfOrganizingList() {
    assertTrue(list != null);
    assertTrue(list.totalNodes == 0);
    assertTrue(list.start == null);
  }
  
  @Test
  void testInsertNode() {
	  list.insertNode(17);
	  list.insertNode(42);
	  list.insertNode(6);
	  assertTrue(list.totalNodes == 3);
	  assertTrue(list.start.next.next.data == 42);
	  	  
  }
}
