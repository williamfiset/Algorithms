/**
 * Base class for hash tables using open addressing collision resolution.
 *
 * Open addressing stores all entries directly in the bucket array. When a collision occurs, the
 * table probes for the next available slot using a probing function (linear, quadratic, double
 * hashing, etc.). Subclasses define the probing strategy by implementing setupProbing(), probe(),
 * and adjustCapacity().
 *
 * Deleted entries are marked with a TOMBSTONE sentinel rather than being nulled out, because
 * nulling would break probe chains — a lookup that follows the same probe sequence would
 * incorrectly conclude the key doesn't exist when it hits the null gap.
 *
 * To keep probe chains short, get() and containsKey() perform "lazy relocation": if a tombstone
 * is encountered before the target key, the key is moved into the tombstone's slot. This speeds
 * up future lookups for the same key.
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.datastructures.hashtable;

import java.util.*;

@SuppressWarnings("unchecked")
public abstract class HashTableOpenAddressingBase<K, V> implements Iterable<K> {

  protected double loadFactor;
  protected int capacity, threshold, modificationCount;

  // 'usedBuckets' counts the total number of used buckets inside the hash-table (includes cells
  // marked as deleted) and drives resize decisions. While 'keyCount' tracks the number of unique
  // keys currently inside the hash-table and is the value returned by size().
  protected int usedBuckets, keyCount;

  // These arrays store the key-value pairs.
  protected K[] keys;
  protected V[] values;

  // Sentinel object marking a deleted slot — distinct from null (empty) and any real key.
  // We use a tombstone rather than null because nulling a deleted slot would break probe chains:
  // a lookup following the same probe sequence would stop early at the null gap and incorrectly
  // conclude the key doesn't exist.
  protected final K TOMBSTONE = (K) new Object();

  private static final int DEFAULT_CAPACITY = 7;
  private static final double DEFAULT_LOAD_FACTOR = 0.65;

  protected HashTableOpenAddressingBase() {
    this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
  }

  protected HashTableOpenAddressingBase(int capacity) {
    this(capacity, DEFAULT_LOAD_FACTOR);
  }

  protected HashTableOpenAddressingBase(int capacity, double loadFactor) {
    if (capacity <= 0)
      throw new IllegalArgumentException("Illegal capacity: " + capacity);
    if (loadFactor <= 0 || Double.isNaN(loadFactor) || Double.isInfinite(loadFactor))
      throw new IllegalArgumentException("Illegal loadFactor: " + loadFactor);

    this.loadFactor = loadFactor;
    this.capacity = Math.max(DEFAULT_CAPACITY, capacity);
    adjustCapacity();
    threshold = (int) (this.capacity * loadFactor);

    keys = (K[]) new Object[this.capacity];
    values = (V[]) new Object[this.capacity];
  }

  // These three methods are used to dictate how the probing is to actually occur for whatever
  // open addressing scheme you are implementing. setupProbing() is called before probing begins
  // for a key — subclasses can cache a secondary hash here (e.g. double hashing).
  protected abstract void setupProbing(K key);

  // Returns the probe offset for the x-th step in the probe sequence. For example, linear
  // probing returns x, quadratic probing returns x*x, and double hashing returns x * hash2(key).
  protected abstract int probe(int x);

  // Adjusts the capacity of the hash table after it's been made larger. This is important to be
  // able to override because the size of the hash-table controls the functionality of the probing
  // function. For example, linear probing requires the capacity to be prime, while quadratic
  // probing works best with powers of two.
  protected abstract void adjustCapacity();

  // Increases the capacity of the hash table. Default is 2n+1; quadratic probing overrides
  // to use powers of two.
  protected void increaseCapacity() {
    capacity = (2 * capacity) + 1;
  }

  public void clear() {
    Arrays.fill(keys, null);
    Arrays.fill(values, null);
    keyCount = usedBuckets = 0;
    modificationCount++;
  }

  // Returns the number of keys currently inside the hash-table
  public int size() {
    return keyCount;
  }

  // Returns the capacity of the hash-table (used mostly for testing)
  public int getCapacity() {
    return capacity;
  }

  // Returns true/false depending on whether the hash-table is empty
  public boolean isEmpty() {
    return keyCount == 0;
  }

  // Converts a hash value to an index. Essentially, this strips the negative sign and places
  // the hash value in the domain [0, capacity).
  protected final int normalizeIndex(int keyHash) {
    return (keyHash & 0x7FFFFFFF) % capacity;
  }

  // Finds the greatest common denominator of a and b.
  protected static final int gcd(int a, int b) {
    if (b == 0)
      return a;
    return gcd(b, a % b);
  }

  /**
   * Inserts or updates a key-value pair. Returns the previous value, or null if the key is new.
   *
   * <p>During probing, if a tombstone is found before an empty slot or matching key, the index is
   * saved. If the key is later found, it is relocated to the tombstone's position to shorten
   * future probe chains. If an empty slot is reached, the new entry is placed at the tombstone
   * (if one was seen) or at the empty slot.
   */
  public V put(K key, V val) {
    if (key == null)
      throw new IllegalArgumentException("Null key");
    if (usedBuckets >= threshold)
      resizeTable();

    setupProbing(key);
    final int offset = normalizeIndex(key.hashCode());

    for (int i = offset, j = -1, x = 1; ; i = normalizeIndex(offset + probe(x++))) {

      // The current slot was previously deleted
      if (keys[i] == TOMBSTONE) {
        // Remember the first tombstone position for potential relocation
        if (j == -1)
          j = i;

      // The current cell already contains a key
      } else if (keys[i] != null) {
        // The key we're trying to insert already exists in the hash-table,
        // so update its value with the most recent value
        if (keys[i].equals(key)) {
          V oldValue = values[i];
          if (j == -1) {
            // No tombstone seen, update in place
            values[i] = val;
          } else {
            // Previously encountered a tombstone — relocate the key to that earlier
            // position so future lookups find it sooner in the probe sequence
            keys[i] = TOMBSTONE;
            values[i] = null;
            keys[j] = key;
            values[j] = val;
          }
          modificationCount++;
          return oldValue;
        }

      // Current cell is null so an insertion/update can occur
      } else {
        if (j == -1) {
          // No previously encountered deleted buckets — use this empty slot
          usedBuckets++;
          keyCount++;
          keys[i] = key;
          values[i] = val;
        } else {
          // Previously seen deleted bucket. Instead of inserting the new element at i
          // where the null element is, insert it where the deleted token was found.
          // This doesn't increase usedBuckets since we're reusing a tombstone slot.
          keyCount++;
          keys[j] = key;
          values[j] = val;
        }
        modificationCount++;
        return null;
      }
    }
  }

  /**
   * Returns true if the key exists in the table.
   *
   * <p>Performs lazy relocation: if a tombstone was encountered before the matching key, the key
   * is moved to the tombstone's slot to speed up future lookups.
   */
  public boolean containsKey(K key) {
    if (key == null)
      throw new IllegalArgumentException("Null key");

    setupProbing(key);
    final int offset = normalizeIndex(key.hashCode());

    // Start at the original hash value and probe until we find a spot where our key
    // is or hit a null element in which case our element does not exist.
    for (int i = offset, j = -1, x = 1; ; i = normalizeIndex(offset + probe(x++))) {

      // Ignore deleted cells, but record where the first index
      // of a deleted cell is found to perform lazy relocation later.
      if (keys[i] == TOMBSTONE) {
        if (j == -1)
          j = i;

      // We hit a non-null key, perhaps it's the one we're looking for.
      } else if (keys[i] != null) {
        if (keys[i].equals(key)) {
          // If j != -1 this means we previously encountered a deleted cell.
          // We can perform an optimization by swapping the entries in cells
          // i and j so that the next time we search for this key it will be
          // found faster. This is called lazy deletion/relocation.
          if (j != -1) {
            // Swap the key-value pairs of positions i and j.
            keys[j] = keys[i];
            values[j] = values[i];
            keys[i] = TOMBSTONE;
            values[i] = null;
          }
          return true;
        }

      } else {
        // Hit an empty slot — key was not found in the hash-table
        return false;
      }
    }
  }

  /**
   * Returns the value for the given key, or null if not found.
   *
   * <p>NOTE: returns null if the value is null AND also returns null if the key does not exist.
   *
   * <p>Like containsKey(), performs lazy relocation when a tombstone precedes the target key.
   */
  public V get(K key) {
    if (key == null)
      throw new IllegalArgumentException("Null key");

    setupProbing(key);
    final int offset = normalizeIndex(key.hashCode());

    // Start at the original hash value and probe until we find a spot where our key
    // is or we hit a null element in which case our element does not exist.
    for (int i = offset, j = -1, x = 1; ; i = normalizeIndex(offset + probe(x++))) {

      // Ignore deleted cells, but record where the first index
      // of a deleted cell is found to perform lazy relocation later.
      if (keys[i] == TOMBSTONE) {
        if (j == -1)
          j = i;

      // We hit a non-null key, perhaps it's the one we're looking for.
      } else if (keys[i] != null) {
        if (keys[i].equals(key)) {
          // If j != -1 this means we previously encountered a deleted cell.
          // We can perform an optimization by swapping the entries in cells
          // i and j so that the next time we search for this key it will be
          // found faster. This is called lazy deletion/relocation.
          if (j != -1) {
            // Swap key-value pairs at indexes i and j.
            keys[j] = keys[i];
            values[j] = values[i];
            keys[i] = TOMBSTONE;
            values[i] = null;
            return values[j];
          }
          return values[i];
        }

      } else {
        // Element was not found in the hash-table
        return null;
      }
    }
  }

  /**
   * Removes a key from the map and returns the value. Marks the slot as TOMBSTONE.
   *
   * <p>NOTE: returns null if the value is null AND also returns null if the key does not exist.
   */
  public V remove(K key) {
    if (key == null)
      throw new IllegalArgumentException("Null key");

    setupProbing(key);
    final int offset = normalizeIndex(key.hashCode());

    // Starting at the original hash probe until we find a spot where our key is
    // or we hit a null element in which case our element does not exist.
    for (int i = offset, x = 1; ; i = normalizeIndex(offset + probe(x++))) {

      // Ignore deleted cells
      if (keys[i] == TOMBSTONE)
        continue;

      // Key was not found in hash-table.
      if (keys[i] == null)
        return null;

      // The key we want to remove is in the hash-table!
      if (keys[i].equals(key)) {
        keyCount--;
        modificationCount++;
        V oldValue = values[i];
        keys[i] = TOMBSTONE;
        values[i] = null;
        return oldValue;
      }
    }
  }

  // Returns a list of keys found in the hash table
  public List<K> keys() {
    List<K> hashtableKeys = new ArrayList<>(keyCount);
    for (int i = 0; i < capacity; i++) {
      if (keys[i] != null && keys[i] != TOMBSTONE)
        hashtableKeys.add(keys[i]);
    }
    return hashtableKeys;
  }

  // Returns a list of non-unique values found in the hash table
  public List<V> values() {
    List<V> hashtableValues = new ArrayList<>(keyCount);
    for (int i = 0; i < capacity; i++) {
      if (keys[i] != null && keys[i] != TOMBSTONE)
        hashtableValues.add(values[i]);
    }
    return hashtableValues;
  }

  // Doubles capacity, rehashes all live entries, and discards tombstones.
  // After resizing, all tombstones are gone because we re-insert only live keys.
  protected void resizeTable() {
    increaseCapacity();
    adjustCapacity();
    threshold = (int) (capacity * loadFactor);

    K[] oldKeys = keys;
    V[] oldValues = values;

    keys = (K[]) new Object[capacity];
    values = (V[]) new Object[capacity];

    // Reset the key count and buckets used since we are about to
    // re-insert all the keys into the hash-table.
    keyCount = usedBuckets = 0;

    for (int i = 0; i < oldKeys.length; i++) {
      if (oldKeys[i] != null && oldKeys[i] != TOMBSTONE)
        put(oldKeys[i], oldValues[i]);
      oldKeys[i] = null;
      oldValues[i] = null;
    }
  }

  // Return a String view of this hash-table.
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("{");
    boolean first = true;
    for (int i = 0; i < capacity; i++) {
      if (keys[i] != null && keys[i] != TOMBSTONE) {
        if (!first)
          sb.append(", ");
        sb.append(keys[i]).append(" => ").append(values[i]);
        first = false;
      }
    }
    return sb.append("}").toString();
  }

  @Override
  public Iterator<K> iterator() {
    // Before the iteration begins record the number of modifications done to the hash-table.
    // This value should not change as we iterate otherwise a concurrent modification has occurred.
    final int expectedModCount = modificationCount;
    return new Iterator<K>() {
      int index, keysLeft = keyCount;

      @Override
      public boolean hasNext() {
        if (expectedModCount != modificationCount)
          throw new ConcurrentModificationException();
        return keysLeft != 0;
      }

      // Find the next element and return it
      @Override
      public K next() {
        if (expectedModCount != modificationCount)
          throw new ConcurrentModificationException();
        while (keys[index] == null || keys[index] == TOMBSTONE)
          index++;
        keysLeft--;
        return keys[index++];
      }
    };
  }
}
