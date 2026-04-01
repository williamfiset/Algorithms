/**
 * Prime factorization using Pollard's rho algorithm with Miller-Rabin primality testing.
 *
 * <p>Miller-Rabin with the deterministic witness set {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37}
 * is guaranteed correct for all n < 3.317 × 10^24, which covers the full range of Java's long.
 *
 * <p>Time: O(n^(1/4)) expected per factor found
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.math;

import java.util.ArrayList;
import java.util.List;

public class PrimeFactorization {

  /**
   * Returns the prime factorization of n. The returned factors are not necessarily sorted.
   *
   * @throws IllegalArgumentException if n <= 0.
   */
  public static List<Long> primeFactorization(long n) {
    if (n <= 0)
      throw new IllegalArgumentException();

    List<Long> factors = new ArrayList<>();
    factor(n, factors);
    return factors;
  }

  private static void factor(long n, List<Long> factors) {
    if (n == 1)
      return;
    if (isPrime(n)) {
      factors.add(n);
      return;
    }
    long d = pollardRho(n);
    factor(d, factors);
    factor(n / d, factors);
  }

  private static long pollardRho(long n) {
    if (n % 2 == 0)
      return 2;
    long x = 2 + (long) (999999 * Math.random());
    long c = 2 + (long) (999999 * Math.random());
    long y = x;
    long d = 1;
    while (d == 1) {
      x = mulMod(x, x, n) + c;
      if (x >= n)
        x -= n;
      y = mulMod(y, y, n) + c;
      if (y >= n)
        y -= n;
      y = mulMod(y, y, n) + c;
      if (y >= n)
        y -= n;
      d = gcd(Math.abs(x - y), n);
      if (d == n)
        break;
    }
    return d;
  }

  /**
   * Deterministic Miller-Rabin primality test, correct for all long values. Uses 12 witnesses that
   * guarantee correctness for n < 3.317 × 10^24.
   */
  private static boolean isPrime(long n) {
    if (n < 2)
      return false;
    if (n < 4)
      return true;
    if (n % 2 == 0 || n % 3 == 0)
      return false;

    // Write n-1 as 2^r * d
    long d = n - 1;
    int r = Long.numberOfTrailingZeros(d);
    d >>= r;

    for (long a : new long[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37}) {
      if (a >= n)
        continue;
      long x = powMod(a, d, n);
      if (x == 1 || x == n - 1)
        continue;
      boolean composite = true;
      for (int i = 0; i < r - 1; i++) {
        x = mulMod(x, x, n);
        if (x == n - 1) {
          composite = false;
          break;
        }
      }
      if (composite)
        return false;
    }
    return true;
  }

  /** Modular exponentiation: (base^exp) % mod, using mulMod to avoid overflow. */
  private static long powMod(long base, long exp, long mod) {
    long result = 1;
    base %= mod;
    while (exp > 0) {
      if ((exp & 1) == 1)
        result = mulMod(result, base, mod);
      exp >>= 1;
      base = mulMod(base, base, mod);
    }
    return result;
  }

  /** Overflow-safe modular multiplication: (a * b) % mod. */
  private static long mulMod(long a, long b, long mod) {
    return java.math.BigInteger.valueOf(a)
        .multiply(java.math.BigInteger.valueOf(b))
        .mod(java.math.BigInteger.valueOf(mod))
        .longValue();
  }

  private static long gcd(long a, long b) {
    return b == 0 ? a : gcd(b, a % b);
  }

  public static void main(String[] args) {
    System.out.println(primeFactorization(7));         // [7]
    System.out.println(primeFactorization(100));       // [2, 2, 5, 5]
    System.out.println(primeFactorization(666));       // [2, 3, 3, 37]
    System.out.println(primeFactorization(872342345)); // [5, 7, 7, 19, 67, 2797]
  }
}
