/**
 * Tests the SkipList data structure implementation.
 *
 * @author Daniel Gustafsson
 * @author Timmy Lindholm
 * @author Anja Studic
 * @author Christian Stjernberg
 */
package com.williamfiset.algorithms.datastructures.skiplist;

import static org.junit.Assert.*;

import org.junit.*;

public class SkipListTest {

  @Test
  // Tests the getIndex method, supposed to return the index of an element
  // in the list
  public void testIndex() {
    SkipList sl = new SkipList(4, 1, 45);

    sl.insert(10);
    sl.insert(25);
    assertTrue("Index should be 2", sl.getIndex(25) == 2);

    sl.insert(13);
    sl.insert(14);
    assertTrue("Index should be 3", sl.getIndex(14) == 3);
  }

  @Test
  // GetIndex shall return -1 if trying to get the index of a non existing object
  public void testIndexWithNonExistingValue() {
    SkipList sl = new SkipList(4, 1, 45);

    assertTrue("Index should be -1", sl.getIndex(44) == -1);
  }

  @Test
  // Insert shall return false if trying to insert an object with a
  // key that is greater than the initialized MAX value
  public void testInsertGreaterThanMaxValue() {
    SkipList sl = new SkipList(3, 1, 65);

    assertTrue("Insert should return false", sl.insert(66) == false);
    assertTrue("Insert should return false", sl.insert(103) == false);
    assertTrue("Insert should return false", sl.insert(67) == false);
  }

  @Test
  // Insert shall return false if trying to insert an object with a
  // key that is lesser than the initialized MIN value
  public void testInsertLesserThanMinValue() {
    SkipList sl = new SkipList(3, 10, 83);

    assertTrue("Insert should return false", sl.insert(5) == false);
    assertTrue("Insert should return false", sl.insert(4) == false);
    assertTrue("Insert should return false", sl.insert(3) == false);
    assertTrue("Insert should return false", sl.insert(2) == false);
  }

  @Test
  // Tests the basic functionlity of the data structure
  public void testSimpleFunctionality() {
    SkipList sl = new SkipList(1, 0, 10);
    sl.insert(5);
    sl.insert(8);

    assertTrue("Size should be 4", sl.size() == 4);
    assertTrue("Object with key 5 should be found", sl.find(5));
    assertTrue("Object with key 8 should be found", sl.find(8));

    sl.remove(5);

    assertTrue("Size should be 3", sl.size() == 3);
    assertFalse("Object with key 5 shouldn't be found", sl.find(5));
  }

  @Test
  // Tests the size method, should initialy be 2 becuase of min and max.
  public void testSize() {
    SkipList sl = new SkipList(3, 1, 10);

    assertTrue("Size should be initialized to 2", sl.size() == 2);

    sl.insert(3);
    sl.insert(4);
    sl.insert(5);

    assertTrue("Size should be 5", sl.size() == 5);
    assertFalse("Size shouldn't be 4", sl.size() == 4);

    sl.remove(3);
    sl.remove(4);

    assertTrue("Size should be 3", sl.size() == 3);
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

    assertTrue("Size should be 11", sl.size() == 11);
    assertTrue("Object with key 43 should be found", sl.find(43));
    assertFalse("Object with key 44 shouldn't be found", sl.find(44));

    sl.remove(43);

    assertFalse("Object with key 43 shouldn't be found", sl.find(43));
  }

  @Test
  // Insert shall return false if trying to insert an object with the
  // same key as an already existing object
  public void testDuplicate() {
    SkipList sl = new SkipList(2, 2, 5);
    sl.insert(4);

    assertFalse("Duplicate insert should return false", sl.insert(4));
    assertTrue("Duplicate should not exist, size should be 3", sl.size() == 3);

    sl.remove(4);

    assertFalse("Element exist after removal", sl.find(4));
  }

  @Test
  // Tests removing non-existing key
  public void testRemoveNonExisting() {
    SkipList sl = new SkipList(2, 2, 5);

    assertFalse("Remove should return false when Object does not exist", sl.remove(4));

    sl.insert(4);

    assertTrue("Object should be removable", sl.remove(4));
    assertFalse("Remove should return false when it has already been removed", sl.remove(4));
  }

  @Test
  // Tests removing head and tail nodes, should return false
  public void testRemoveHeadTail() {
    SkipList sl = new SkipList(3, 1, 10);

    assertFalse("Head shouldn't be removable", sl.remove(1));
    assertFalse("Tail shouldn't be removable", sl.remove(10));
  }
}
