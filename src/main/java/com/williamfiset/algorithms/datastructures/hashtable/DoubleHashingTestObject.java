/**
 * The DoubleHashingTestObject is an object to test the functionality of the hash-table implemented
 * with double hashing.
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.datastructures.hashtable;

import java.util.Arrays;
import java.util.Random;

public class DoubleHashingTestObject implements SecondaryHash {

  private int hash, hash2;
  private final Integer intData;
  private final int[] vectorData;
  private final String stringData;

  private static final long[] RANDOM_VECTOR;
  private static final int MAX_VECTOR_SIZE = 10000;

  static {
    RANDOM_VECTOR = new long[MAX_VECTOR_SIZE];
    Random r = new Random();
    for (int i = 0; i < MAX_VECTOR_SIZE; i++) {
      long val = r.nextLong();
      while (val % 2 == 0) val = r.nextLong();
      RANDOM_VECTOR[i] = val;
    }
  }

  public DoubleHashingTestObject(int data) {
    this.intData = data;
    this.vectorData = null;
    this.stringData = null;
    computeHashes();
  }

  public DoubleHashingTestObject(int[] data) {
    if (data == null) throw new IllegalArgumentException("Cannot be null");
    this.intData = null;
    this.vectorData = data;
    this.stringData = null;
    computeHashes();
  }

  public DoubleHashingTestObject(String data) {
    if (data == null) throw new IllegalArgumentException("Cannot be null");
    this.intData = null;
    this.vectorData = null;
    this.stringData = data;
    computeHashes();
  }

  private void computeHashes() {
    if (intData != null) {
      hash = intData.hashCode();
      hash2 = intData;
    } else if (stringData != null) {
      hash = stringData.hashCode();
      // Multiplicative hash function for hash2
      int prime = 17;
      int power = 1;
      hash2 = 0;
      for (int i = 0; i < stringData.length(); i++) {
        hash2 += power * stringData.charAt(i);
        power *= prime;
      }
    } else {
      hash = Arrays.hashCode(vectorData);
      hash2 = 0;
      for (int i = 0; i < vectorData.length; i++) {
        hash2 += RANDOM_VECTOR[i] * vectorData[i];
      }
    }
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
    if (this == o) return true;
    if (!(o instanceof DoubleHashingTestObject)) return false;
    DoubleHashingTestObject obj = (DoubleHashingTestObject) o;
    if (hash != obj.hash) return false;
    if (intData != null) return intData.equals(obj.intData);
    if (vectorData != null) return Arrays.equals(vectorData, obj.vectorData);
    return stringData.equals(obj.stringData);
  }
}
