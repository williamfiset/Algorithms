/**
 * NOTE: The code in this file is incomplete and still under development.
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.datastructures.bloomfilter;

public class AnagramSet {

  private final int N_HASHES;
  private final int[] MODS;
  private final long[] rollingHashes;
  private final int[][] MOD_INVERSES;
  private final BloomFilter bloomFilter;

  // Alphabet table lookup
  private static final int[] ALPHABET = new int[127];

  // There are only 95 printable ASCII characters, hence we only
  // need the first 95 prime numbers to uniquely represent every string
  private static final int[] PRIMES = {
    2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97,
    101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193,
    197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307,
    311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419, 421,
    431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499
  };

  // More primes: 1009, 1013, 1019, 10007, 10009, 10037, 100003, 100019, 100043, 1000003, 1000033,
  // 1000037,
  // 10000019, 10000079, 10000103, 100000007, 100000009, 100000023, 1000000007, 1000000009,
  // 1000000021, 1000000033
  private static final int[] DEFAULT_MODS = {10_000_019, 10_000_079, 10_000_103};

  static {
    for (int i = 32, j = 0; i < ALPHABET.length; i++, j++) {
      ALPHABET[i] = PRIMES[j];
    }
  }

  public AnagramSet() {
    this(DEFAULT_MODS);
  }

  public AnagramSet(int[] mods) {

    MODS = mods.clone();
    N_HASHES = mods.length;
    MOD_INVERSES = new int[N_HASHES][PRIMES.length];

    rollingHashes = new long[N_HASHES];
    bloomFilter = new BloomFilter(mods);

    // // Assuming all mods are primes each mod value will have a modular inverse
    for (int i = 0; i < N_HASHES; i++) {
      java.math.BigInteger mod = new java.math.BigInteger(String.valueOf(MODS[i]));
      for (int j = 0; j < PRIMES.length; j++) {
        java.math.BigInteger prime = new java.math.BigInteger(String.valueOf(PRIMES[j]));
        MOD_INVERSES[i][j] = prime.modInverse(mod).intValue();
      }
    }
  }

  // Compute and return a deep copy of the current rolling hash value.
  public long[] computeHash(String str) {

    java.util.Arrays.fill(rollingHashes, 1L);

    for (int j = 0; j < str.length(); j++) add(rollingHashes, str.charAt(j));

    return rollingHashes.clone();
  }

  // Compute and return a shallow copy of the current rolling hash value.
  // NOTE: Make sure you clone the returned array if you want to cache it.
  private long[] computeHashShallow(String str) {

    java.util.Arrays.fill(rollingHashes, 1L);

    for (int j = 0; j < str.length(); j++) add(rollingHashes, str.charAt(j));

    return rollingHashes;
  }

  // Adds this string to the anagram set, O(N_HASHES)
  public void add(String str) {
    bloomFilter.add(computeHashShallow(str));
  }

  // Add the character 'c' to the anagram rolling hash, O(N_HASHES)
  public void add(long[] hashes, char c) {
    for (int i = 0; i < N_HASHES; i++) {
      hashes[i] = (hashes[i] * ALPHABET[c]) % MODS[i];
    }
  }

  // Removes the character 'c' from the rolling anagram hash stored in 'hashes', O(N_HASHES)
  public void remove(long[] hashes, char c) {
    for (int i = 0; i < N_HASHES; i++) {
      hashes[i] = (hashes[i] * MOD_INVERSES[i][c - ' ']) % MODS[i];
    }
  }

  // Checks if any anagram of the string 'str' exists in the set, O(N_HASHES)
  public boolean contains(String str) {
    return contains(computeHashShallow(str));
  }

  // Given the hashes for a string this method checks
  // if any anagram of the string 'str' exists in the set, O(N_HASHES)
  public boolean contains(long[] hashes) {
    return bloomFilter.contains(hashes);
  }
}
