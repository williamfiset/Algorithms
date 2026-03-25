/**
 * Tests whether a number is prime using trial division. Skips multiples of 2 and 3 by iterating
 * over numbers of the form 6k ± 1.
 *
 * <p>Why 6k ± 1? Every integer falls into one of six residue classes mod 6:
 *
 * <pre>
 *   6k + 0  →  divisible by 6 (= 2·3)     → not prime
 *   6k + 1  →  not divisible by 2 or 3     → candidate ✓
 *   6k + 2  →  divisible by 2              → not prime
 *   6k + 3  →  divisible by 3              → not prime
 *   6k + 4  →  divisible by 2              → not prime
 *   6k + 5  →  not divisible by 2 or 3     → candidate ✓  (same as 6k - 1)
 * </pre>
 *
 * After checking 2 and 3 directly, all remaining primes must be of the form 6k ± 1, so we only
 * need to test divisibility by those candidates.
 *
 * <p>Time: O(√n)
 *
 * @author Micah Stairs, William Fiset
 */
package com.williamfiset.algorithms.math;

public class PrimalityCheck {

  /** Returns true if n is a prime number. */
  public static boolean isPrime(long n) {
    if (n < 2)
      return false;
    if (n == 2 || n == 3)
      return true;
    if (n % 2 == 0 || n % 3 == 0)
      return false;
    long limit = (long) Math.sqrt(n);
    for (long i = 5; i <= limit; i += 6)
      if (n % i == 0 || n % (i + 2) == 0)
        return false;
    return true;
  }

  /**
   * Optimized primality check using a mod 30 wheel. This is a balanced generalization of the mod 6
   * approach above — by also eliminating multiples of 5, we only test 8 out of every 30 candidates
   * (27%) instead of 2 out of every 6 (33%).
   *
   * <p>The 8 residues coprime to 30 (= 2·3·5) are: 1, 7, 11, 13, 17, 19, 23, 29. Larger wheels
   * (mod 210 = 2·3·5·7 with 48 offsets) exist but offer diminishing returns for the added code
   * complexity.
   */
  public static boolean isPrimeWheel30(long n) {
    if (n < 2)
      return false;
    if (n == 2 || n == 3 || n == 5)
      return true;
    if (n % 2 == 0 || n % 3 == 0 || n % 5 == 0)
      return false;
    // Gaps between consecutive residues coprime to 30: 7,11,13,17,19,23,29,31 (i.e. +4,+2,+4,+2,+4,+6,+2,+6).
    int[] gaps = {4, 2, 4, 2, 4, 6, 2, 6};
    long limit = (long) Math.sqrt(n);
    int gi = 0;
    for (long d = 7; d <= limit; d += gaps[gi++ % 8])
      if (n % d == 0)
        return false;
    return true;
  }

  public static void main(String[] args) {
    System.out.println(isPrime(5));    // true
    System.out.println(isPrime(31));   // true
    System.out.println(isPrime(1433)); // true
    System.out.println(isPrime(8763857775536878331L)); // true
    System.out.println(isPrime(4));    // false
    System.out.println(isPrime(15));   // false

    System.out.println();

    System.out.println(isPrimeWheel30(5));    // true
    System.out.println(isPrimeWheel30(31));   // true
    System.out.println(isPrimeWheel30(1433)); // true
    System.out.println(isPrimeWheel30(8763857775536878331L)); // true
    System.out.println(isPrimeWheel30(4));    // false
    System.out.println(isPrimeWheel30(15));   // false
  }
}
