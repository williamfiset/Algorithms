/**
 * A benchmark file to test the speed of the various hashtable implementations
 *
 * @author William Fiset
 */
package com.williamfiset.algorithms.datastructures.hashtable;

import java.util.*;

public class Benchmark {

  static final int N = 1000000;
  static final int MOD = 1000000;

  static int[] keys = new int[N];
  static int[] values = new int[N];
  static DoubleHashingTestObject[] doubleHashKeys = new DoubleHashingTestObject[N];

  static Random RANDOM = new Random();

  static {
    for (int i = 0; i < N; i++) {
      keys[i] = RANDOM.nextInt() % MOD;
      values[i] = RANDOM.nextInt() % MOD;
      doubleHashKeys[i] = new DoubleHashingTestObject(keys[i]);
    }
  }

  public static void main(String[] args) {
    testLinearProbing();
    testQuadraticProbing();
    testDoubleHashing();
    testHashMapSpeed();
  }

  public static void testLinearProbing() {

    HashTableLinearProbing<Integer, Integer> hashtable = new HashTableLinearProbing<>();

    long start = System.nanoTime();
    for (int i = 0; i < N; i++) {
      hashtable.insert(keys[i], values[i]);
      int val = hashtable.get(keys[i]);
      if (val != values[i]) System.out.println("Not good..");
    }
    long end = System.nanoTime();
    System.out.println("Linear probing: " + (end - start) / 1e9);
  }

  public static void testQuadraticProbing() {

    HashTableQuadraticProbing<Integer, Integer> hashtable = new HashTableQuadraticProbing<>();

    long start = System.nanoTime();
    for (int i = 0; i < N; i++) {
      hashtable.insert(keys[i], values[i]);
      int val = hashtable.get(keys[i]);
      if (val != values[i]) System.out.println("Not good..");
    }
    long end = System.nanoTime();
    System.out.println("Quadratic probing: " + (end - start) / 1e9);
  }

  public static void testDoubleHashing() {

    HashTableDoubleHashing<DoubleHashingTestObject, Integer> hashtable =
        new HashTableDoubleHashing<>();

    long start = System.nanoTime();
    for (int i = 0; i < N; i++) {

      hashtable.insert(doubleHashKeys[i], values[i]);
      int val = hashtable.get(doubleHashKeys[i]);
      if (val != values[i]) System.out.println("Not good..");
    }
    long end = System.nanoTime();
    System.out.println("Double hashing: " + (end - start) / 1e9);
  }

  public static void testHashMapSpeed() {

    HashMap<Integer, Integer> jmap = new HashMap<>();

    long start = System.nanoTime();
    for (int i = 0; i < N; i++) {
      jmap.put(keys[i], values[i]);
      int val = jmap.get(keys[i]);
      if (val != values[i]) System.out.println("Not good..");
    }
    long end = System.nanoTime();
    System.out.println("HashMap: " + (end - start) / 1e9);
  }
}
