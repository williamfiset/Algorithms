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

  public static void main(String[] args) {
    System.out.println(isPrime(5));    // true
    System.out.println(isPrime(31));   // true
    System.out.println(isPrime(1433)); // true
    System.out.println(isPrime(8763857775536878331L)); // true
    System.out.println(isPrime(4));    // false
    System.out.println(isPrime(15));   // false
  }
}
