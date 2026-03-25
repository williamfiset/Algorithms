/**
 * Computes the Least Common Multiple (LCM) of two numbers using the relation LCM(a, b) = |a /
 * gcd(a, b) * b|.
 *
 * <p>Time: ~O(log(a + b))
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.math;

public class Lcm {

  /** Returns the least common multiple of a and b. The result is always non-negative. */
  public static long lcm(long a, long b) {
    return Math.abs(a / gcd(a, b) * b);
  }

  private static long gcd(long a, long b) {
    if (b == 0)
      return Math.abs(a);
    return gcd(b, a % b);
  }

  public static void main(String[] args) {
    System.out.println(lcm(12, 18));   // 36
    System.out.println(lcm(-12, 18));  // 36
    System.out.println(lcm(12, -18));  // 36
    System.out.println(lcm(-12, -18)); // 36
  }
}
