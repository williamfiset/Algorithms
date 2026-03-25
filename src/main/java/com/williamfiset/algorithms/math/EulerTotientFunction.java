/**
 * Computes Euler's totient function phi(n), which counts the number of integers in [1, n] that are
 * relatively prime to n.
 *
 * <p>Uses trial division to find prime factors and applies the product formula:
 * phi(n) = n * product of (1 - 1/p) for each distinct prime factor p of n.
 *
 * <p>Time: O(sqrt(n))
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.math;

public class EulerTotientFunction {

  /**
   * Computes Euler's totient phi(n).
   *
   * @param n a positive integer.
   * @return the number of integers in [1, n] that are coprime to n.
   * @throws IllegalArgumentException if n is not positive.
   */
  public static long eulersTotient(long n) {
    if (n <= 0)
      throw new IllegalArgumentException("n must be positive.");
    long result = n;
    for (long p = 2; p * p <= n; p++) {
      if (n % p == 0) {
        while (n % p == 0)
          n /= p;
        result -= result / p;
      }
    }
    // If n still has a prime factor greater than sqrt(original n).
    if (n > 1)
      result -= result / n;
    return result;
  }

  public static void main(String[] args) {
    // phi(15) = 8 because 1,2,4,7,8,11,13,14 are coprime with 15.
    System.out.printf("phi(15) = %d\n", eulersTotient(15));

    System.out.println();

    for (int x = 1; x <= 11; x++)
      System.out.printf("phi(%d) = %d\n", x, eulersTotient(x));
  }
}
