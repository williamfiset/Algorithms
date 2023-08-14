/**
 * The StringSet is a probabilistic string set data structure implemented using a bloom filter and
 * positional rolling hashing techniques. This data structure can be very fast (outperforming Java
 * HashSet) if you're doing queries involving all substrings of a particular string. Despite being
 * probabilistic, this DS is very safe to use because the probability of a false positive can be set
 * to as low as you wish it to be.
 *
 * @author William Alexandre Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.datastructures.bloomfilter;

public class StringSet {

  // Our alphabet size is 95 because there are only 95 printable ASCII characters
  // which are in the range between [32, 127). We also need to add +1 to our alphabet
  // size because we're going to redefine the first ASCII character (the space character)
  // to be 1 instead of 0 to avoid collisions where the string ' ' hashes to the
  // same value as '   ' since 0*95^0 = 0*95^0 + 0*95^1 + 0*95^2
  private static final int ALPHABET_SZ = 95 + 1;
  private static final int[] ALPHABET = new int[127];

  private final int N_HASHES;
  private final long POWERS[][];
  private final int[] MODS, MOD_INVERSES;
  private final long[] rollingHashes;
  private final BloomFilter bloomFilter;

  // More primes: 1009, 1013, 1019, 10007, 10009, 10037, 100003, 100019, 100043, 1000003, 1000033,
  // 1000037,
  // 10000019, 10000079, 10000103, 100000007, 100000009, 100000023, 1000000007, 1000000009,
  // 1000000021, 1000000033
  private static final int[] DEFAULT_MODS = {10009, 100003, 1000003};

  // Assign a mapping from the printable ASCII characters to the natural numbers
  static {
    for (int i = 32, n = 1; i < ALPHABET.length; i++, n++) {
      ALPHABET[i] = n;
    }
  }

  public StringSet(int maxLen) {
    this(DEFAULT_MODS, maxLen);
  }

  // mods - The mod values to use for the bloom filter, they should probably be prime numbers
  // maxLen - The maximum length string we will need to deal with
  public StringSet(int[] mods, int maxLen) {

    MODS = mods.clone();
    N_HASHES = mods.length;
    MOD_INVERSES = new int[N_HASHES];
    POWERS = new long[N_HASHES][maxLen];
    rollingHashes = new long[N_HASHES];
    bloomFilter = new BloomFilter(mods);

    java.math.BigInteger bigAlpha = java.math.BigInteger.valueOf(ALPHABET_SZ);

    // Assuming all mods are primes each mod value will have a modular inverse
    for (int i = 0; i < N_HASHES; i++) {
      java.math.BigInteger mod = java.math.BigInteger.valueOf(MODS[i]);
      MOD_INVERSES[i] = bigAlpha.modInverse(mod).intValue();
    }

    // Precompute powers of the alphabet size mod all the mod values
    for (int i = 0; i < N_HASHES; i++) {
      POWERS[i][0] = 1L;
      for (int j = 1; j < maxLen; j++) {
        POWERS[i][j] = (POWERS[i][j - 1] * ALPHABET_SZ) % mods[i];
      }
    }
  }

  // Returns a shallow copy of the current rolling hash value.
  // Make sure you clone the array if you want to cache it.
  public long[] computeHash(String str) {

    java.util.Arrays.fill(rollingHashes, 0L);

    for (int i = 0; i < str.length(); i++) {
      for (int k = 0; k < N_HASHES; k++) {
        int rightChar = ALPHABET[str.charAt(i)];
        rollingHashes[k] = addRight(rollingHashes[k], rightChar, k);
      }
    }

    return rollingHashes; // rollingHashes.clone();
  }

  // This method adds a string to the bloom filter set. If you're adding a lot
  // of similar strings of the same length use the overloaded version
  // of this method to take advantage of rolling hashes, or if you're
  // simply adding all substrings call 'addAllSubstrings' to also take
  // advantage of rolling hashing technique.
  public void add(String str) {
    bloomFilter.add(computeHash(str));
  }

  // Add all sequences of length 'sz' to the bloom filter
  public void add(String str, int sz) {

    add(str);

    for (int i = sz; i < str.length() - sz; i++) {
      for (int k = 0; k < N_HASHES; k++) {

        int rightChar = ALPHABET[str.charAt(i)];
        int leftChar = ALPHABET[str.charAt(i - sz)];

        // Add the right character
        rollingHashes[k] = addRight(rollingHashes[k], rightChar, k);

        // Remove the leftmost character
        rollingHashes[k] = removeLeft(rollingHashes[k], leftChar, k, sz);
      }
      bloomFilter.add(rollingHashes);
    }
  }

  // Adds all the substrings of 'data' into the
  // bloom filter using positional hashing
  public void addAllSubstrings(String str) {

    int N = str.length();
    int[] values = new int[N];

    for (int i = 0; i < N; i++) values[i] = ALPHABET[str.charAt(i)];

    for (int i = 0; i < N; i++) {

      // Reset all rolling hashes
      java.util.Arrays.fill(rollingHashes, 0L);

      for (int j = i; j < N; j++) {

        // Compute the next rolling hash value for each hash
        // function with a different modulus value
        for (int k = 0; k < N_HASHES; k++) {
          rollingHashes[k] = addRight(rollingHashes[k], values[j], k);
        }

        // Add this substring to the bloom filter
        bloomFilter.add(rollingHashes);
      }
    }
  }

  // This function adds a character to the end of the rolling hash
  public long addRight(long rollingHash, int lastValue, int modIndex) {
    rollingHash = (rollingHash * ALPHABET_SZ + lastValue) % MODS[modIndex];
    return (rollingHash + MODS[modIndex]) % MODS[modIndex];
  }

  public long addRight(long rollingHash, char lastValue, int modIndex) {
    return addRight(rollingHash, ALPHABET[lastValue], modIndex);
  }

  // This function adds a character to the beginning of the rolling hash
  public long addLeft(long rollingHash, int firstValue, int modIndex, int len) {
    rollingHash = (firstValue * POWERS[modIndex][len] + rollingHash) % MODS[modIndex];
    return (rollingHash + MODS[modIndex]) % MODS[modIndex];
  }

  public long addLeft(long rollingHash, char firstValue, int modIndex, int len) {
    return addLeft(rollingHash, ALPHABET[firstValue], modIndex, len);
  }

  // Given a rolling hash x_n*A^n + x_n-1*A^(n-1) + ... + x_2*A^2 + x_1*A^1 + x_0*A^0
  // where x_i is a string character value and 'A' is the alphabet size we
  // want to remove the first term 'x_n*A^n' from our rolling hash.
  //
  // firstValue - This is x_n, the first character of this string
  public long removeLeft(long rollingHash, int firstValue, int modIndex, int len) {
    rollingHash = (rollingHash - firstValue * POWERS[modIndex][len - 1]) % MODS[modIndex];
    return (rollingHash + MODS[modIndex]) % MODS[modIndex];
  }

  public long removeLeft(long rollingHash, char firstValue, int modIndex, int len) {
    return removeLeft(rollingHash, ALPHABET[firstValue], modIndex, len);
  }

  public long removeRight(long rollingHash, int lastValue, int modIndex) {
    rollingHash = (((rollingHash - lastValue) % MODS[modIndex]) + MODS[modIndex]) % MODS[modIndex];
    return (rollingHash * MOD_INVERSES[modIndex]) % MODS[modIndex];
  }

  public long removeRight(long rollingHash, char lastValue, int modIndex) {
    return removeRight(rollingHash, ALPHABET[lastValue], modIndex);
  }

  // Given the hash of a string this method returns whether or not
  // that string is found within the bloom filter.
  public boolean contains(long[] hashes) {
    return bloomFilter.contains(hashes);
  }

  // Dynamically compute this string's hash value and check containment
  public boolean contains(String str) {
    return bloomFilter.contains(computeHash(str));
  }

  @Override
  public String toString() {
    return bloomFilter.toString();
  }
}
