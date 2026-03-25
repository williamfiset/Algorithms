/**
 * Generates a compressed prime sieve using bit manipulation. Each bit represents whether an odd
 * number is prime or not. Even numbers are omitted (except 2, handled as a special case), so each
 * long covers a range of 128 numbers.
 *
 * <p>Time: ~O(n log(log(n)))
 *
 * <p>Space: O(n / 128) longs
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.math;

public class CompressedPrimeSieve {

  private static final double NUM_BITS = 128.0;
  private static final int NUM_BITS_SHIFT = 7; // 2^7 = 128

  // Marks n as not prime by setting its bit to 1.
  private static void setBit(long[] arr, int n) {
    if ((n & 1) == 0)
      return;
    arr[n >> NUM_BITS_SHIFT] |= 1L << ((n - 1) >> 1);
  }

  // Returns true if n's bit is unset (meaning n is prime).
  private static boolean isNotSet(long[] arr, int n) {
    if (n < 2)
      return false;
    if (n == 2)
      return true;
    if ((n & 1) == 0)
      return false;
    long chunk = arr[n >> NUM_BITS_SHIFT];
    long mask = 1L << ((n - 1) >> 1);
    return (chunk & mask) != mask;
  }

  /** Returns true if n is prime according to the given sieve. */
  public static boolean isPrime(long[] sieve, int n) {
    return isNotSet(sieve, n);
  }

  /**
   * Builds a compressed prime sieve for all numbers up to {@code limit}.
   *
   * @param limit the upper bound (inclusive) for the sieve.
   * @return a bit-packed array where each bit indicates whether an odd number is composite.
   */
  public static long[] primeSieve(int limit) {
    int numChunks = (int) Math.ceil(limit / NUM_BITS);
    int sqrtLimit = (int) Math.sqrt(limit);
    long[] chunks = new long[numChunks];
    chunks[0] = 1; // Mark 1 as not prime.
    for (int i = 3; i <= sqrtLimit; i += 2)
      if (isNotSet(chunks, i))
        for (int j = i * i; j <= limit; j += i)
          if (isNotSet(chunks, j))
            setBit(chunks, j);
    return chunks;
  }

  public static void main(String[] args) {
    int limit = 200;
    long[] sieve = primeSieve(limit);
    for (int i = 0; i <= limit; i++)
      if (isPrime(sieve, i))
        System.out.printf("%d is prime!\n", i);
  }
}
