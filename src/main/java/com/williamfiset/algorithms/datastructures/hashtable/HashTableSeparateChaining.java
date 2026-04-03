/**
 * A hash table implementation using separate chaining with linked lists.
 *
 * Each bucket holds a linked list of entries. On collision, new entries are appended to the list.
 * When the load factor threshold is exceeded, the table doubles in capacity and all entries are
 * rehashed.
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.datastructures.hashtable;

import java.util.*;

@SuppressWarnings("unchecked")
public class HashTableSeparateChaining<K, V> implements Iterable<K> {

  private static class Entry<K, V> {
    int hash;
    K key;
    V value;

    // Cache the hash code so we don't recompute it on every resize
    Entry(K key, V value) {
      this.key = key;
      this.value = value;
      this.hash = key.hashCode();
    }

    boolean keyEquals(K other) {
      return key.equals(other);
    }

    @Override
    public String toString() {
      return key + " => " + value;
    }
  }

  private static final int DEFAULT_CAPACITY = 3;
  private static final double DEFAULT_LOAD_FACTOR = 0.75;

  private double maxLoadFactor;
  private int capacity, threshold, size;
  // Tracks structural modifications for fail-fast iteration
  private int modCount;
  private LinkedList<Entry<K, V>>[] table;

  public HashTableSeparateChaining() {
    this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
  }

  public HashTableSeparateChaining(int capacity) {
    this(capacity, DEFAULT_LOAD_FACTOR);
  }

  public HashTableSeparateChaining(int capacity, double maxLoadFactor) {
    if (capacity < 0)
      throw new IllegalArgumentException("Illegal capacity");
    if (maxLoadFactor <= 0 || Double.isNaN(maxLoadFactor) || Double.isInfinite(maxLoadFactor))
      throw new IllegalArgumentException("Illegal maxLoadFactor");
    this.maxLoadFactor = maxLoadFactor;
    this.capacity = Math.max(DEFAULT_CAPACITY, capacity);
    threshold = (int) (this.capacity * maxLoadFactor);
    table = new LinkedList[this.capacity];
  }

  public int size() {
    return size;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  // Strips the negative sign and maps the hash into [0, capacity)
  private int normalizeIndex(int keyHash) {
    return (keyHash & 0x7FFFFFFF) % capacity;
  }

  public void clear() {
    Arrays.fill(table, null);
    size = 0;
    modCount++;
  }

  public boolean containsKey(K key) {
    int bucketIndex = normalizeIndex(key.hashCode());
    return seekEntry(bucketIndex, key) != null;
  }

  public V put(K key, V value) {
    if (key == null)
      throw new IllegalArgumentException("Null key");
    Entry<K, V> newEntry = new Entry<>(key, value);
    int bucketIndex = normalizeIndex(newEntry.hash);
    return insertEntry(bucketIndex, newEntry);
  }

  public V get(K key) {
    if (key == null)
      return null;
    int bucketIndex = normalizeIndex(key.hashCode());
    Entry<K, V> entry = seekEntry(bucketIndex, key);
    return entry != null ? entry.value : null;
  }

  public V remove(K key) {
    if (key == null)
      return null;
    int bucketIndex = normalizeIndex(key.hashCode());
    return removeEntry(bucketIndex, key);
  }

  // Single-pass find-and-remove using an iterator to avoid scanning the bucket twice
  private V removeEntry(int bucketIndex, K key) {
    LinkedList<Entry<K, V>> bucket = table[bucketIndex];
    if (bucket == null)
      return null;
    java.util.Iterator<Entry<K, V>> iter = bucket.iterator();
    while (iter.hasNext()) {
      Entry<K, V> entry = iter.next();
      if (entry.keyEquals(key)) {
        iter.remove();
        size--;
        modCount++;
        return entry.value;
      }
    }
    return null;
  }

  // If the key already exists, update its value and return the old value.
  // Otherwise, insert a new entry and return null.
  private V insertEntry(int bucketIndex, Entry<K, V> entry) {
    LinkedList<Entry<K, V>> bucket = table[bucketIndex];
    if (bucket == null)
      table[bucketIndex] = bucket = new LinkedList<>();

    Entry<K, V> existing = seekEntry(bucketIndex, entry.key);
    if (existing == null) {
      bucket.add(entry);
      if (++size > threshold)
        resizeTable();
      modCount++;
      return null;
    }
    V oldVal = existing.value;
    existing.value = entry.value;
    return oldVal;
  }

  // Linearly scans the bucket's chain looking for a matching key
  private Entry<K, V> seekEntry(int bucketIndex, K key) {
    if (key == null)
      return null;
    LinkedList<Entry<K, V>> bucket = table[bucketIndex];
    if (bucket == null)
      return null;
    for (Entry<K, V> entry : bucket) {
      if (entry.keyEquals(key))
        return entry;
    }
    return null;
  }

  // Doubles the table capacity and rehashes all entries into new buckets
  private void resizeTable() {
    capacity *= 2;
    threshold = (int) (capacity * maxLoadFactor);
    LinkedList<Entry<K, V>>[] newTable = new LinkedList[capacity];

    for (LinkedList<Entry<K, V>> bucket : table) {
      if (bucket == null)
        continue;
      for (Entry<K, V> entry : bucket) {
        int i = normalizeIndex(entry.hash);
        if (newTable[i] == null)
          newTable[i] = new LinkedList<>();
        newTable[i].add(entry);
      }
    }
    table = newTable;
  }

  public List<K> keys() {
    List<K> keys = new ArrayList<>(size);
    for (LinkedList<Entry<K, V>> bucket : table) {
      if (bucket != null)
        for (Entry<K, V> entry : bucket)
          keys.add(entry.key);
    }
    return keys;
  }

  public List<V> values() {
    List<V> values = new ArrayList<>(size);
    for (LinkedList<Entry<K, V>> bucket : table) {
      if (bucket != null)
        for (Entry<K, V> entry : bucket)
          values.add(entry.value);
    }
    return values;
  }

  @Override
  public Iterator<K> iterator() {
    return new Iterator<K>() {
      final int expectedModCount = modCount;
      int bucketIndex = 0;
      Iterator<Entry<K, V>> bucketIter = advanceToNextBucket();

      private Iterator<Entry<K, V>> advanceToNextBucket() {
        while (bucketIndex < capacity) {
          if (table[bucketIndex] != null && !table[bucketIndex].isEmpty())
            return table[bucketIndex].iterator();
          bucketIndex++;
        }
        return null;
      }

      @Override
      public boolean hasNext() {
        if (expectedModCount != modCount)
          throw new ConcurrentModificationException();
        return bucketIter != null && bucketIter.hasNext();
      }

      @Override
      public K next() {
        if (expectedModCount != modCount)
          throw new ConcurrentModificationException();
        if (bucketIter == null || !bucketIter.hasNext())
          throw new NoSuchElementException();
        K key = bucketIter.next().key;
        if (!bucketIter.hasNext()) {
          bucketIndex++;
          bucketIter = advanceToNextBucket();
        }
        return key;
      }
    };
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("{");
    boolean first = true;
    for (LinkedList<Entry<K, V>> bucket : table) {
      if (bucket == null)
        continue;
      for (Entry<K, V> entry : bucket) {
        if (!first)
          sb.append(", ");
        sb.append(entry);
        first = false;
      }
    }
    return sb.append("}").toString();
  }
}
