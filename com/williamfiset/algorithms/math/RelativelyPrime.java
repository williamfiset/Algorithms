/**
 * Test to see whether two numbers are relatively prime (coprime)
 *
 * <p>Time Complexity: O(log(a+b))
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.math;

public class RelativelyPrime {

  // Find the greatest common factor between two numbers
  private static long gcf(long a, long b) {
    return b == 0 ? a : gcf(b, a % b);
  }

  // Test to see whether two numbers are relatively prime (coprime)
  public static boolean areCoprime(long a, long b) {
    return gcf(a, b) == 1;
  }

  public static void main(String[] args) {
    System.out.println(areCoprime(5, 7)); // true
    System.out.println(areCoprime(12, 18)); // false
  }
}
