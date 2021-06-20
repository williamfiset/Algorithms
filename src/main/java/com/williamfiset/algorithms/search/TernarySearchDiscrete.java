/**
 * Ternary search is similar to binary search except that it works on a function which decreases and
 * then increases. This implementation of ternary search works on discrete values and returns the
 * input value corresponding with the minimum output value of the function you're searching on.
 *
 * <p>Time Complexity: O(log(high - low)).
 *
 * <p>NOTE: You can also work with a function which increases and then decreases, simply negate your
 * function :)
 *
 * @author Thomas Finn Lidbetter
 */
package com.williamfiset.algorithms.search;

public class TernarySearchDiscrete {

  // TODO(williamfiset): refactor for better support of custom functions.

  // Define a very small epsilon value to compare double values.
  static final double EPS = 0.000000001;

  // A discrete function is just a set of data points.
  static final double[] function = {16, 12, 10, 3, 6, 7, 9, 10, 11, 12, 13, 17};

  // Define your own function on whatever you're attempting to ternary
  // search. Remember that your function must be a discrete and a unimodal
  // function, this means a function which decreases then increases (U shape)
  static double f(int i) {
    return function[i];
  }

  static double discreteTernarySearch(int lo, int hi) {
    while (lo != hi) {
      if (hi - lo == 1) return Math.min(f(lo), f(hi));
      if (hi - lo == 2) return Math.min(f(lo), Math.min(f(lo + 1), f(hi)));
      int mid1 = (2 * lo + hi) / 3, mid2 = (lo + 2 * hi) / 3;
      double res1 = f(mid1), res2 = f(mid2);
      if (Math.abs(res1 - res2) < 0.000000001) {
        lo = mid1;
        hi = mid2;
      } else if (res1 > res2) lo = mid1;
      else hi = mid2;
    }
    return f(lo);
  }

  public static void main(String[] args) {

    int lo = 0;
    int hi = function.length - 1;

    // Use ternary search to find the minimum value on the
    // whole interval of out function.
    double minValue = discreteTernarySearch(lo, hi);
    System.out.printf("%.4f\n", minValue);
  }
}
