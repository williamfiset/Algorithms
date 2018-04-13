/**
 * Generate a compressed prime sieve using bit manipulation. 
 * The idea is that each bit represents a boolean value indicating whether
 * a number is prime or not. This saves a lot of room when creating the sieve. 
 * In this implementation I store all odd numbers in individual longs meaning 
 * that for each long I use I can represent a range of 128 numbers 
 * (even numbers are omitted because they are not prime, with the exception of 
 * 2 which is handled as a special case).
 *
 * Time Complexity: ~O(nloglogn)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 **/
package com.williamfiset.algorithms.math;

public class CompressedPrimeSieve {

  static final int NUM_BITS = 128;
  static final int NUM_BITS_SHIFT = 7; // 2^7 = 128

  // Sets the bit repesenting n to 1 indicating this number is not prime
  static void setBit(long[] arr, int n) {
    if ((n & 1) == 0) return; // n is even
    arr[n >> NUM_BITS_SHIFT] |= 1L << ((n - 1) >> 1);
  }

  // Returns true if a bit if turned on for the number n (AKA n is a prime)
  // Make sure you DO NOT use this method to access numbers outside your prime sieve range!
  static boolean isSet(long[] arr, int n) {
    if (n == 2) return true; // two is prime
    if ((n & 1) == 0) return false; // n is even
    long chunk = arr[n >> NUM_BITS_SHIFT];
    long mask = 1L << ((n - 1) >> 1);
    return (chunk & mask) != mask;
  }

  // Returns an array of longs with each bit indicating whether a number
  // is prime or not. Use the isSet and setBit methods to toggle to bits for each number.
  static long[] primeSieve(int limit) {

    final int num_chunks = (int) Math.ceil(limit / ((double)NUM_BITS) );
    final int sqrt_limit = (int) Math.sqrt(limit);
    // if (limit < 2) return 0; // uncomment for primeCount purposes
    // int primeCount = (int) Math.ceil(limit / 2.0); // Counts number of primes <= limit
    long[] chunks = new long[num_chunks];
    chunks[0] = 1; // 1 as not prime
    for (int i = 3; i <= sqrt_limit; i += 2)
      if (isSet(chunks, i))
        for (int j = i * i; j <= limit; j += i)
          if (isSet(chunks, j)) {
            setBit(chunks, j);
            // primeCount--;
          }
    return chunks;
  }

  public static void main(String[] args) {
    
    final int LIMIT = 101;
    long[] sieve = primeSieve(LIMIT);
    
    for (int i = 1; i <= LIMIT; i++ )
      if (isSet(sieve, i))
        System.out.printf("%d is prime!\n", i);

  }

}