/**
 * Tests the SkipList data structure implementation.
 *
 * @author Daniel Gustafsson
 * @author Timmy Lindholm
 * @author Anja Studic
 * @author Christian Stjernberg
 */
package com.williamfiset.algorithms.datastructures.skiplist;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

public class SkipListTest {

  @Test
  // Tests the getIndex method, supposed to return the index of an element
  // in the list
  public void testIndex() {
    SkipList sl = new SkipList(4, 1, 45);

    sl.insert(10);
    sl.insert(25);
    assertEquals(2, sl.getIndex(25), "Index should be 2");

    sl.insert(13);
    sl.insert(14);
    assertEquals(3, sl.getIndex(14), "Index should be 3");
  }

  @Test
  // GetIndex shall return -1 if trying to get the index of a non existing object
  public void testIndexWithNonExistingValue() {
    SkipList sl = new SkipList(4, 1, 45);

    assertEquals(-1, sl.getIndex(44), "Index should be -1");
  }

  @Test
  // Insert shall return false if trying to insert an object with a
  // key that is greater than the initialized MAX value
  public void testInsertGreaterThanMaxValue() {
    SkipList sl = new SkipList(3, 1, 65);

    assertFalse(sl.insert(66), "Insert should return false");
    assertFalse(sl.insert(103), "Insert should return false");
    assertFalse(sl.insert(67), "Insert should return false");
  }

  @Test
  // Insert shall return false if trying to insert an object with a
  // key that is lesser than the initialized MIN value
  public void testInsertLesserThanMinValue() {
    SkipList sl = new SkipList(3, 10, 83);

    assertFalse(sl.insert(5), "Insert should return false");
    assertFalse(sl.insert(4), "Insert should return false");
    assertFalse(sl.insert(3), "Insert should return false");
    assertFalse(sl.insert(2), "Insert should return false");
  }

  @Test
  // Tests the basic functionlity of the data structure
  public void testSimpleFunctionality() {
    SkipList sl = new SkipList(1, 0, 10);
    sl.insert(5);
    sl.insert(8);

    assertEquals(4, sl.size(), "Size should be 4");
    assertTrue(sl.find(5), "Object with key 5 should be found");
    assertTrue(sl.find(8), "Object with key 8 should be found");

    sl.remove(5);

    assertEquals(3, sl.size(), "Size should be 3");
    assertFalse(sl.find(5), "Object with key 5 shouldn't be found");
  }

  @Test
  // Tests the size method, should initialy be 2 becuase of min and max.
  public void testSize() {
    SkipList sl = new SkipList(3, 1, 10);

    assertEquals(2, sl.size(), "Size should be initialized to 2");

    sl.insert(3);
    sl.insert(4);
    sl.insert(5);

    assertEquals(5, sl.size(), "Size should be 5");
    assertNotEquals(4, sl.size(), "Size shouldn't be 4");

    sl.remove(3);
    sl.remove(4);

    assertEquals(3, sl.size(), "Size should be 3");
  }

  @Test
  // Tests the find method, find should return true when given an existing
  // element key and return false when given a key of a non existing element
  public void testFind() {
    SkipList sl = new SkipList(4, 1, 45);
    sl.insert(9);
    sl.insert(18);
    sl.insert(2);
    sl.insert(6);
    sl.insert(43);
    sl.insert(36);
    sl.insert(20);
    sl.insert(30);
    sl.insert(24);

    assertEquals(11, sl.size(), "Size should be 11");
    assertTrue(sl.find(43), "Object with key 43 should be found");
    assertFalse(sl.find(44), "Object with key 44 shouldn't be found");

    sl.remove(43);

    assertFalse(sl.find(43), "Object with key 43 shouldn't be found");
  }

  @Test
  // Insert shall return false if trying to insert an object with the
  // same key as an already existing object
  public void testDuplicate() {
    SkipList sl = new SkipList(2, 2, 5);
    sl.insert(4);

    assertFalse(sl.insert(4), "Duplicate insert should return false");
    assertEquals(3, sl.size(), "Duplicate should not exist, size should be 3");

    sl.remove(4);

    assertFalse(sl.find(4), "Element exist after removal");
  }

  @Test
  // Tests removing non-existing key
  public void testRemoveNonExisting() {
    SkipList sl = new SkipList(2, 2, 5);

    assertFalse(sl.remove(4), "Remove should return false when Object does not exist");

    sl.insert(4);

    assertTrue(sl.remove(4), "Object should be removable");
    assertFalse(sl.remove(4), "Remove should return false when it has already been removed");
  }

  @Test
  // Tests removing head and tail nodes, should return false
  public void testRemoveHeadTail() {
    SkipList sl = new SkipList(3, 1, 10);

    assertFalse(sl.remove(1), "Head shouldn't be removable");
    assertFalse(sl.remove(10), "Tail shouldn't be removable");
  }
}
