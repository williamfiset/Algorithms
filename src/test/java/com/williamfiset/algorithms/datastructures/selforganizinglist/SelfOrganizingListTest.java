package com.williamfiset.algorithms.datastructures.selforganizinglist;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SelfOrganizingListTest {
  SelfOrganizingList list;

  @BeforeEach
  void setUp() {
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

  @Test
  void testReorder() {
    list.insertNode(12);
    list.insertNode(7);
    list.insertNode(24);
    list.search(7);
    assertTrue(list.start.data == 7);
    assertTrue(list.start.count == 1);
    list.search(24);
    list.search(24);
    assertTrue(list.start.data == 24);
    assertTrue(list.start.count == 2);
  }

  void testSearch() {
    assertFalse(list.search(8));
    list.insertNode(59);
    assertTrue(list.search(59));
  }
}
