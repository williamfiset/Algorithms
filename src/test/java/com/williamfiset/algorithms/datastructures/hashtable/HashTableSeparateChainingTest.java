package com.williamfiset.algorithms.datastructures.hashtable;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HashTableSeparateChainingTest {

  static final Random RANDOM = new Random();
  static int LOOPS, MAX_SIZE, MAX_RAND_NUM;

  static {
    LOOPS = 500;
    MAX_SIZE = randInt(1, 750);
    MAX_RAND_NUM = randInt(1, 350);
  }

  HashTableSeparateChaining<Integer, Integer> map;

  static int randInt(int min, int max) {
    return RANDOM.nextInt((max - min) + 1) + min;
  }

  // Generate a list of random numbers
  static List<Integer> genRandList(int sz) {

    List<Integer> lst = new ArrayList<>(sz);
    for (int i = 0; i < sz; i++) lst.add(randInt(-MAX_RAND_NUM, MAX_RAND_NUM));
    Collections.shuffle(lst);
    return lst;
  }

  // Generate a list of unique random numbers
  static List<Integer> genUniqueRandList(int sz) {
    List<Integer> lst = new ArrayList<>(sz);
    for (int i = 0; i < sz; i++) lst.add(i);
    Collections.shuffle(lst);
    return lst;
  }

  @Before
  public void setup() {
    map = new HashTableSeparateChaining<>();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullKey() {
    map.put(null, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalCreation1() {
    new HashTableSeparateChaining<>(-3, 0.5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalCreation2() {
    new HashTableSeparateChaining<>(5, Double.POSITIVE_INFINITY);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalCreation3() {
    new HashTableSeparateChaining<>(6, -0.5);
  }

  @Test
  public void testLegalCreation() {
    new HashTableSeparateChaining<>(6, 0.9);
  }

  @Test
  public void testUpdatingValue() {

    map.add(1, 1);
    assertTrue(1 == map.get(1));

    map.add(1, 5);
    assertTrue(5 == map.get(1));

    map.add(1, -7);
    assertTrue(-7 == map.get(1));
  }

  @Test
  public void testIterator() {

    HashMap<Integer, Integer> map2 = new HashMap<>();

    for (int loop = 0; loop < LOOPS; loop++) {

      map.clear();
      map2.clear();
      assertTrue(map.isEmpty());

      map = new HashTableSeparateChaining<>();

      List<Integer> rand_nums = genRandList(MAX_SIZE);
      for (Integer key : rand_nums) assertEquals(map.add(key, key), map2.put(key, key));

      int count = 0;
      for (Integer key : map) {
        assertEquals(key, map.get(key));
        assertEquals(map.get(key), map2.get(key));
        assertTrue(map.hasKey(key));
        assertTrue(rand_nums.contains(key));
        count++;
      }

      for (Integer key : map2.keySet()) {
        assertEquals(key, map.get(key));
      }

      Set<Integer> set = new HashSet<>();
      for (int n : rand_nums) set.add(n);

      assertEquals(set.size(), count);
      assertEquals(map2.size(), count);
    }
  }

  @Test(expected = java.util.ConcurrentModificationException.class)
  public void testConcurrentModificationException() {
    map.add(1, 1);
    map.add(2, 1);
    map.add(3, 1);
    for (Integer key : map) map.add(4, 4);
  }

  @Test(expected = java.util.ConcurrentModificationException.class)
  public void testConcurrentModificationException2() {
    map.add(1, 1);
    map.add(2, 1);
    map.add(3, 1);
    for (Integer key : map) map.remove(2);
  }

  @Test
  public void randomRemove() {

    HashTableSeparateChaining<Integer, Integer> map;

    for (int loop = 0; loop < LOOPS; loop++) {

      map = new HashTableSeparateChaining<>();
      map.clear();

      // Add some random values
      Set<Integer> keys_set = new HashSet<>();
      for (int i = 0; i < MAX_SIZE; i++) {
        int randomVal = randInt(-MAX_RAND_NUM, MAX_RAND_NUM);
        keys_set.add(randomVal);
        map.put(randomVal, 5);
      }

      assertEquals(map.size(), keys_set.size());

      List<Integer> keys = map.keys();
      for (Integer key : keys) map.remove(key);

      assertTrue(map.isEmpty());
    }
  }

  @Test
  public void removeTest() {

    HashTableSeparateChaining<Integer, Integer> map = new HashTableSeparateChaining<>(7);

    // Add three elements
    map.put(11, 0);
    map.put(12, 0);
    map.put(13, 0);
    assertEquals(3, map.size());

    // Add ten more
    for (int i = 1; i <= 10; i++) map.put(i, 0);
    assertEquals(13, map.size());

    // Remove ten
    for (int i = 1; i <= 10; i++) map.remove(i);
    assertEquals(3, map.size());

    // remove three
    map.remove(11);
    map.remove(12);
    map.remove(13);
    assertEquals(0, map.size());
  }

  @Test
  public void removeTestComplex1() {

    HashTableSeparateChaining<HashObject, Integer> map = new HashTableSeparateChaining<>();

    HashObject o1 = new HashObject(88, 1);
    HashObject o2 = new HashObject(88, 2);
    HashObject o3 = new HashObject(88, 3);
    HashObject o4 = new HashObject(88, 4);

    map.add(o1, 111);
    map.add(o2, 111);
    map.add(o3, 111);
    map.add(o4, 111);

    map.remove(o2);
    map.remove(o3);
    map.remove(o1);
    map.remove(o4);

    assertEquals(0, map.size());
  }

  @Test
  public void testRandomMapOperations() {

    HashMap<Integer, Integer> jmap = new HashMap<>();

    for (int loop = 0; loop < LOOPS; loop++) {

      map.clear();
      jmap.clear();
      assertEquals(jmap.size(), map.size());

      map = new HashTableSeparateChaining<>();

      final double probability1 = Math.random();
      final double probability2 = Math.random();

      List<Integer> nums = genRandList(MAX_SIZE);
      for (int i = 0; i < MAX_SIZE; i++) {

        double r = Math.random();

        int key = nums.get(i);
        int val = i;

        if (r < probability1) assertEquals(jmap.put(key, val), map.put(key, val));

        assertEquals(jmap.get(key), map.get(key));
        assertEquals(jmap.containsKey(key), map.containsKey(key));
        assertEquals(jmap.size(), map.size());

        if (r > probability2) assertEquals(map.remove(key), jmap.remove(key));

        assertEquals(jmap.get(key), map.get(key));
        assertEquals(jmap.containsKey(key), map.containsKey(key));
        assertEquals(jmap.size(), map.size());
      }
    }
  }

  @Test
  public void randomIteratorTests() {

    HashTableSeparateChaining<Integer, LinkedList<Integer>> m = new HashTableSeparateChaining<>();
    HashMap<Integer, LinkedList<Integer>> hm = new HashMap<>();

    for (int loop = 0; loop < LOOPS; loop++) {

      m.clear();
      hm.clear();
      assertEquals(m.size(), hm.size());

      int sz = randInt(1, MAX_SIZE);
      m = new HashTableSeparateChaining<>(sz);
      hm = new HashMap<>(sz);

      final double probability = Math.random();

      for (int i = 0; i < MAX_SIZE; i++) {

        int index = randInt(0, MAX_SIZE - 1);
        LinkedList<Integer> l1 = m.get(index);
        LinkedList<Integer> l2 = hm.get(index);

        if (l2 == null) {
          l1 = new LinkedList<Integer>();
          l2 = new LinkedList<Integer>();
          m.put(index, l1);
          hm.put(index, l2);
        }

        int rand_val = randInt(-MAX_SIZE, MAX_SIZE);

        if (Math.random() < probability) {

          l1.removeFirstOccurrence(rand_val);
          l2.removeFirstOccurrence(rand_val);

        } else {

          l1.add(rand_val);
          l2.add(rand_val);
        }

        assertEquals(m.size(), hm.size());
        assertEquals(l1, l2);
      }
    }
  }

  // You can set the hash value of this object to be whatever you want
  // This makes it great for testing special cases.
  static class HashObject {
    final int hash, data;

    public HashObject(int hash, int data) {
      this.hash = hash;
      this.data = data;
    }

    @Override
    public int hashCode() {
      return hash;
    }

    @Override
    public boolean equals(Object o) {
      HashObject ho = (HashObject) o;
      return hashCode() == ho.hashCode() && data == ho.data;
    }
  }
}
