package com.williamfiset.algorithms.datastructures.hashtable;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import org.junit.jupiter.api.*;

public class HashTableSeparateChainingTest {

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
      if (this == o) return true;
      else if (o instanceof HashObject) {
        HashObject ho = (HashObject) o;
        return hashCode() == ho.hashCode() && data == ho.data;
      } else return false;
    }
  }

  static final Random RANDOM = new Random();
  static int LOOPS, MAX_SIZE, MAX_RAND_NUM;

  static {
    LOOPS = 500;
    MAX_SIZE = randInt(1, 750);
    MAX_RAND_NUM = randInt(1, 350);
  }

  HashTableSeparateChaining<Integer, Integer> map;

  @BeforeEach
  public void setup() {
    map = new HashTableSeparateChaining<>();
  }

  @Test
  public void testIllegalCreation1() {
    assertThrows(IllegalArgumentException.class, () -> new HashTableSeparateChaining<>(-3, 0.5));
  }

  @Test
  public void testIllegalCreation2() {
    assertThrows(
        IllegalArgumentException.class,
        () -> new HashTableSeparateChaining<>(5, Double.POSITIVE_INFINITY));
  }

  @Test
  public void testIllegalCreation3() {
    assertThrows(IllegalArgumentException.class, () -> new HashTableSeparateChaining<>(6, -0.5));
  }

  @Test
  public void testLegalCreation() {
    assertDoesNotThrow(() -> new HashTableSeparateChaining<>(6, 0.9));
  }

  @Test
  public void testUpdatingValue() {

    map.add(1, 1);
    assertThat(map.get(1)).isEqualTo(1);

    map.add(1, 5);
    assertThat(map.get(1)).isEqualTo(5);

    map.add(1, -7);
    assertThat(map.get(1)).isEqualTo(-7);
  }

  @Test
  public void testIterator() {

    HashMap<Integer, Integer> map2 = new HashMap<>();

    for (int loop = 0; loop < LOOPS; loop++) {

      map.clear();
      map2.clear();
      assertThat(map.isEmpty()).isTrue();

      map = new HashTableSeparateChaining<>();

      List<Integer> rand_nums = genRandList(MAX_SIZE);
      for (Integer key : rand_nums) assertThat(map.add(key, key)).isEqualTo(map2.put(key, key));

      int count = 0;
      for (Integer key : map) {
        assertThat(map.get(key)).isEqualTo(key);
        assertThat(map.get(key)).isEqualTo(map2.get(key));
        assertThat(map.hasKey(key)).isTrue();
        assertThat(rand_nums.contains(key)).isTrue();
        count++;
      }

      for (Integer key : map2.keySet()) {
        assertThat(map.get(key)).isEqualTo(key);
      }

      Set<Integer> set = new HashSet<>();
      for (int n : rand_nums) set.add(n);

      assertThat(set.size()).isEqualTo(count);
      assertThat(map2.size()).isEqualTo(count);
    }
  }

  @Test
  public void testConcurrentModificationException() {
    assertThrows(
        ConcurrentModificationException.class,
        () -> {
          map.add(1, 1);
          map.add(2, 1);
          map.add(3, 1);
          for (Integer key : map) map.add(4, 4);
        });
  }

  @Test
  public void testConcurrentModificationException2() {
    assertThrows(
        ConcurrentModificationException.class,
        () -> {
          map.add(1, 1);
          map.add(2, 1);
          map.add(3, 1);
          for (Integer key : map) map.remove(2);
        });
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

      assertThat(map.size()).isEqualTo(keys_set.size());

      List<Integer> keys = map.keys();
      for (Integer key : keys) map.remove(key);

      assertThat(map.isEmpty()).isTrue();
    }
  }

  @Test
  public void removeTest() {

    HashTableSeparateChaining<Integer, Integer> map = new HashTableSeparateChaining<>(7);

    // Add three elements
    map.put(11, 0);
    map.put(12, 0);
    map.put(13, 0);
    assertThat(map.size()).isEqualTo(3);

    // Add ten more
    for (int i = 1; i <= 10; i++) map.put(i, 0);
    assertThat(map.size()).isEqualTo(13);

    // Remove ten
    for (int i = 1; i <= 10; i++) map.remove(i);
    assertThat(map.size()).isEqualTo(3);

    // remove three
    map.remove(11);
    map.remove(12);
    map.remove(13);
    assertThat(map.size()).isEqualTo(0);
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

    assertThat(map.size()).isEqualTo(0);
  }

  @Test
  public void testRandomMapOperations() {

    HashMap<Integer, Integer> jmap = new HashMap<>();

    for (int loop = 0; loop < LOOPS; loop++) {

      map.clear();
      jmap.clear();
      assertThat(jmap.size()).isEqualTo(map.size());

      map = new HashTableSeparateChaining<>();

      final double probability1 = Math.random();
      final double probability2 = Math.random();

      List<Integer> nums = genRandList(MAX_SIZE);
      for (int i = 0; i < MAX_SIZE; i++) {

        double r = Math.random();

        int key = nums.get(i);
        int val = i;

        if (r < probability1) assertThat(jmap.put(key, val)).isEqualTo(map.put(key, val));

        assertThat(jmap.get(key)).isEqualTo(map.get(key));
        assertThat(jmap.containsKey(key)).isEqualTo(map.containsKey(key));
        assertThat(jmap.size()).isEqualTo(map.size());

        if (r > probability2) assertThat(map.remove(key)).isEqualTo(jmap.remove(key));

        assertThat(jmap.get(key)).isEqualTo(map.get(key));
        assertThat(jmap.containsKey(key)).isEqualTo(map.containsKey(key));
        assertThat(jmap.size()).isEqualTo(map.size());
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
      assertThat(m.size()).isEqualTo(hm.size());

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

        assertThat(m.size()).isEqualTo(hm.size());
        assertThat(l1).isEqualTo(l2);
      }
    }
  }

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
}
