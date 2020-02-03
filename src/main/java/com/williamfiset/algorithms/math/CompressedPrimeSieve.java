/**
 * Generate a compressed prime sieve using bit manipulation. The idea is that each bit represents a
 * boolean value indicating whether a number is prime or not. This saves a lot of room when creating
 * the sieve. In this implementation I store all odd numbers in individual longs meaning that for
 * each long I use I can represent a range of 128 numbers (even numbers are omitted because they are
 * not prime, with the exception of 2 which is handled as a special case).
 *
 * <p>Time Complexity: ~O(nloglogn)
 *
 * <p>Compile: javac -d src/main/java
 * src/main/java/com/williamfiset/algorithms/math/CompressedPrimeSieve.java
 *
 * <p>Run: java -cp src/main/java com/williamfiset/algorithms/math/CompressedPrimeSieve
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.math;

public class CompressedPrimeSieve {
  private static final double NUM_BITS = 128.0;
  private static final int NUM_BITS_SHIFT = 7; // 2^7 = 128

  // Sets the bit representing n to 1 indicating this number is not prime
  private static void setBit(long[] arr, int n) {
    if ((n & 1) == 0) return; // n is even
    arr[n >> NUM_BITS_SHIFT] |= 1L << ((n - 1) >> 1);
  }

  // Returns true if the bit for n is off (meaning n is a prime).
  // Note: do use this method to access numbers outside your prime sieve range!
  private static boolean isNotSet(long[] arr, int n) {
    if (n < 2) return false; // n is not prime
    if (n == 2) return true; // two is prime
    if ((n & 1) == 0) return false; // n is even
    long chunk = arr[n >> NUM_BITS_SHIFT];
    long mask = 1L << ((n - 1) >> 1);
    return (chunk & mask) != mask;
  }

  // Returns true/false depending on whether n is prime.
  public static boolean isPrime(long[] sieve, int n) {
    return isNotSet(sieve, n);
  }

  // Returns an array of longs with each bit indicating whether a number
  // is prime or not. Use the isNotSet and setBit methods to toggle to bits for each number.
  public static long[] primeSieve(int limit) {
    final int numChunks = (int) Math.ceil(limit / NUM_BITS);
    final int sqrtLimit = (int) Math.sqrt(limit);
    // if (limit < 2) return 0; // uncomment for primeCount purposes
    // int primeCount = (int) Math.ceil(limit / 2.0); // Counts number of primes <= limit
    long[] chunks = new long[numChunks];
    chunks[0] = 1; // 1 as not prime
    for (int i = 3; i <= sqrtLimit; i += 2)
      if (isNotSet(chunks, i))
        for (int j = i * i; j <= limit; j += i)
          if (isNotSet(chunks, j)) {
            setBit(chunks, j);
            // primeCount--;
          }
    return chunks;
  }

  /* Example usage. */

  public static void main(String[] args) {
    final int limit = 200;
    long[] sieve = CompressedPrimeSieve.primeSieve(limit);

    for (int i = 0; i <= limit; i++) {
      if (CompressedPrimeSieve.isPrime(sieve, i)) {
        System.out.printf("%d is prime!\n", i);
      }
    }
  }
}
