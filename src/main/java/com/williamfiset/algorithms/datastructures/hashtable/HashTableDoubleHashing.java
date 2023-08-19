/**
 * An implementation of a hashtable using double hashing as a collision resolution technique.
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.datastructures.hashtable;

import java.math.BigInteger;

public class HashTableDoubleHashing<K extends SecondaryHash, V>
    extends HashTableOpenAddressingBase<K, V> {

  private int hash;

  public HashTableDoubleHashing() {
    super();
  }

  public HashTableDoubleHashing(int capacity) {
    super(capacity);
  }

  // Designated constructor
  public HashTableDoubleHashing(int capacity, double loadFactor) {
    super(capacity, loadFactor);
  }

  @Override
  protected void setupProbing(K key) {
    // Cache second hash value.
    hash = normalizeIndex(key.hashCode2());

    // Fail safe to avoid infinite loop.
    if (hash == 0) hash = 1;
  }

  @Override
  protected int probe(int x) {
    return x * hash;
  }

  // Adjust the capacity until it is a prime number. The reason for
  // doing this is to help ensure that the GCD(hash, capacity) = 1 when
  // probing so that all the cells can be reached.
  @Override
  protected void adjustCapacity() {
    while (!(BigInteger.valueOf(capacity).isProbablePrime(20))) {
      capacity++;
    }
  }
}
