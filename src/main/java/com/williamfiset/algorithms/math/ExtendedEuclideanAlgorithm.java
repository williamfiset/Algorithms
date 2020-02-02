/** Time Complexity ~O(log(a + b)) */
package com.williamfiset.algorithms.math;

public class ExtendedEuclideanAlgorithm {

  // This function performs the extended euclidean algorithm on two numbers a and b.
  // The function returns the gcd(a,b) as well as the numbers x and y such
  // that ax + by = gcd(a,b). This calculation is important in number theory
  // and can be used for several things such as finding modular inverses and
  // solutions to linear Diophantine equations.
  public static long[] egcd(long a, long b) {
    if (b == 0) return new long[] {a, 1, 0};
    else {
      long[] ret = egcd(b, a % b);
      long tmp = ret[1] - ret[2] * (a / b);
      ret[1] = ret[2];
      ret[2] = tmp;
      return ret;
    }
  }
}
