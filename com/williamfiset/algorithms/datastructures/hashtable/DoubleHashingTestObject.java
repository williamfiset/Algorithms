/**
 * The DoubleHashingTestObject is an object to test the functionality of the hash-table implemented
 * with double hashing.
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.datastructures.hashtable;

import java.util.Random;

public class DoubleHashingTestObject implements SecondaryHash {

  private int hash, hash2;
  Integer intData = null;
  int[] vectorData = null;
  String stringData = null;

  static long[] randomVector;
  static Random R = new Random();
  static int MAX_VECTOR_SIZE = 100000;

  static {
    randomVector = new long[MAX_VECTOR_SIZE];
    for (int i = 0; i < MAX_VECTOR_SIZE; i++) {
      long val = R.nextLong();
      while (val % 2 == 0) val = R.nextLong();
      randomVector[i] = val;
    }
  }

  public DoubleHashingTestObject(int data) {
    intData = data;
    intHash();
    computeHash();
  }

  public DoubleHashingTestObject(int[] data) {
    if (data == null) throw new IllegalArgumentException("Cannot be null");
    vectorData = data;
    vectorHash();
    computeHash();
  }

  public DoubleHashingTestObject(String data) {
    if (data == null) throw new IllegalArgumentException("Cannot be null");
    stringData = data;
    stringHash();
    computeHash();
  }

  private void intHash() {
    hash2 = intData;
  }

  private void vectorHash() {
    for (int i = 0; i < vectorData.length; i++) hash2 += randomVector[i] * vectorData[i];
  }

  private void stringHash() {

    // Multipicative hash function:
    final int INITIAL_VALUE = 0;
    int prime = 17;
    int power = 1;
    hash = INITIAL_VALUE;
    for (int i = 0; i < stringData.length(); i++) {
      int ch = stringData.charAt(i);
      hash2 += power * ch;
      power *= prime;
    }
  }

  private void computeHash() {
    if (intData != null) hash = intData.hashCode();
    else if (stringData != null) hash = stringData.hashCode();
    else hash = java.util.Arrays.hashCode(vectorData);
  }

  @Override
  public int hashCode() {
    return hash;
  }

  @Override
  public int hashCode2() {
    return hash2;
  }

  @Override
  public boolean equals(Object o) {
    DoubleHashingTestObject obj = (DoubleHashingTestObject) o;
    if (hash != obj.hash) return false;
    if (intData != null) return intData.equals(obj.intData);
    if (vectorData != null) return java.util.Arrays.equals(vectorData, obj.vectorData);
    return stringData.equals(obj.stringData);
  }
}
