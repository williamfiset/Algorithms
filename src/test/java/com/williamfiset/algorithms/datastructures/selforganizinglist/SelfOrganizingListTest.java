package com.williamfiset.algorithms.datastructures.selforganizinglist;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;

public class SelfOrganizingListTest {
  SelfOrganizingList list;

  @Before
  public void setUp() {
    list = new SelfOrganizingList();
  }

  @Test
  public void testSelfOrganizingList() {
    assertTrue(list != null);
    assertTrue(list.totalNodes == 0);
    assertTrue(list.start == null);
  }

  @Test
  public void testInsertNode() {
    list.insertNode(17);
    list.insertNode(42);
    list.insertNode(6);
    assertTrue(list.totalNodes == 3);
    assertTrue(list.start.next.data == 42);
  }

  @Test
  public void testReorder() {
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

  @Test
  public void testSearch() {
    assertFalse(list.search(8));
    list.insertNode(59);
    assertTrue(list.search(59));
  }

  @Test
  public void testGetSize() {
    assertTrue(list.getSize() == 0);
    list.insertNode(98);
    list.insertNode(741);
    assertTrue(list.getSize() == 2);
  }

  @Test
  public void testIsEmpty() {
    assertTrue(list.isEmpty());
    list.insertNode(23);
    assertFalse(list.isEmpty());
  }

  @Test
  public void testDeleteNode() {
    list.insertNode(78);
    list.insertNode(12);
    list.insertNode(56);
    list.deleteNode(2);
    assertEquals(56, list.start.next.data);
    list.deleteNode(1);
    assertEquals(56, list.start.data);
  }
}
