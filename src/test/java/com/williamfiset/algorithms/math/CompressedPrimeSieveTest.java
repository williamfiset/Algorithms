package com.williamfiset.algorithms.math;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.*;

public class CompressedPrimeSieveTest {

  // First 25 primes (all primes <= 100).
  private static final int[] PRIMES_UP_TO_100 = {
    2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89,
    97
  };

  @Test
  public void testPrimesUpTo100() {
    long[] sieve = CompressedPrimeSieve.primeSieve(100);
    java.util.Set<Integer> expected = new java.util.HashSet<>();
    for (int p : PRIMES_UP_TO_100)
      expected.add(p);

    for (int i = 0; i <= 100; i++) {
      if (expected.contains(i))
        assertThat(CompressedPrimeSieve.isPrime(sieve, i)).isTrue();
      else
        assertThat(CompressedPrimeSieve.isPrime(sieve, i)).isFalse();
    }
  }

  @Test
  public void testZeroAndOneAreNotPrime() {
    long[] sieve = CompressedPrimeSieve.primeSieve(10);
    assertThat(CompressedPrimeSieve.isPrime(sieve, 0)).isFalse();
    assertThat(CompressedPrimeSieve.isPrime(sieve, 1)).isFalse();
  }

  @Test
  public void testTwoIsPrime() {
    long[] sieve = CompressedPrimeSieve.primeSieve(10);
    assertThat(CompressedPrimeSieve.isPrime(sieve, 2)).isTrue();
  }

  @Test
  public void testEvenNumbersAreNotPrime() {
    long[] sieve = CompressedPrimeSieve.primeSieve(200);
    for (int i = 4; i <= 200; i += 2)
      assertThat(CompressedPrimeSieve.isPrime(sieve, i)).isFalse();
  }

  @Test
  public void testPrimeCount() {
    // There are 168 primes <= 1000.
    long[] sieve = CompressedPrimeSieve.primeSieve(1000);
    int count = 0;
    for (int i = 0; i <= 1000; i++)
      if (CompressedPrimeSieve.isPrime(sieve, i))
        count++;
    assertThat(count).isEqualTo(168);
  }

  @Test
  public void testLargePrimes() {
    long[] sieve = CompressedPrimeSieve.primeSieve(10000);
    // Some known primes near the upper range.
    assertThat(CompressedPrimeSieve.isPrime(sieve, 9973)).isTrue();
    assertThat(CompressedPrimeSieve.isPrime(sieve, 9967)).isTrue();
    // Some known composites near the upper range.
    assertThat(CompressedPrimeSieve.isPrime(sieve, 9999)).isFalse();
    assertThat(CompressedPrimeSieve.isPrime(sieve, 10000)).isFalse();
  }

  @Test
  public void testSmallLimit() {
    long[] sieve = CompressedPrimeSieve.primeSieve(2);
    assertThat(CompressedPrimeSieve.isPrime(sieve, 2)).isTrue();
  }
}
