/**
 * Computes the Greatest Common Divisor (GCD) of two numbers using the Euclidean algorithm.
 *
 * <p>Time: ~O(log(a + b))
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.math;

public class Gcd {

  /**
   * Computes the Greatest Common Divisor (GCD) of a and b. The returned value is always
   * non-negative.
   */
  public static long gcd(long a, long b) {
    if (b == 0)
      return Math.abs(a);
    return gcd(b, a % b);
  }

  public static void main(String[] args) {
    System.out.println(gcd(12, 18));   // 6
    System.out.println(gcd(-12, 18));  // 6
    System.out.println(gcd(12, -18));  // 6
    System.out.println(gcd(-12, -18)); // 6

    System.out.println(gcd(5, 0));  // 5
    System.out.println(gcd(0, 5));  // 5
    System.out.println(gcd(-5, 0)); // 5
    System.out.println(gcd(0, -5)); // 5
    System.out.println(gcd(0, 0));  // 0
  }
}
