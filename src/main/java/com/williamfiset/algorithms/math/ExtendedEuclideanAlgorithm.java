/**
 * Extended Euclidean Algorithm. Given two integers a and b, computes gcd(a, b) and finds integers x
 * and y such that ax + by = gcd(a, b). Useful for finding modular inverses and solving linear
 * Diophantine equations.
 *
 * <p>Time: ~O(log(a + b))
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.math;

public class ExtendedEuclideanAlgorithm {

  /**
   * Performs the extended Euclidean algorithm on a and b.
   *
   * @return an array [gcd(a, b), x, y] such that ax + by = gcd(a, b).
   */
  public static long[] egcd(long a, long b) {
    if (b == 0)
      return new long[] {a, 1, 0};
    long[] ret = egcd(b, a % b);
    long tmp = ret[1] - ret[2] * (a / b);
    ret[1] = ret[2];
    ret[2] = tmp;
    return ret;
  }

  public static void main(String[] args) {
    // egcd(35, 15) = [5, 1, -2] because gcd(35,15)=5 and 35*1 + 15*(-2) = 5.
    long[] result = egcd(35, 15);
    System.out.printf("gcd(%d, %d) = %d, x = %d, y = %d\n", 35, 15, result[0], result[1], result[2]);

    // Finding modular inverse: 7^(-1) mod 11 = 8 because 7*8 mod 11 = 1.
    long[] inv = egcd(7, 11);
    long modInverse = ((inv[1] % 11) + 11) % 11;
    System.out.printf("7^(-1) mod 11 = %d\n", modInverse);
  }
}
