/**
 * If ever you need to do a binary search on discrete values you should use Java's binary search:
 * java.util.Arrays.binarySearch(int[] ar, int key) However, in the event that you need to do a
 * binary search on the real numbers you can resort to this implementation.
 *
 * <p>Time Complexity: O(log(high-low))
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.search;

import java.util.function.DoubleFunction;

public class BinarySearch {

  // Comparing double values directly is bad practice.
  // Using a small epsilon value is the preferred approach
  private static final double EPS = 0.00000001;

  public static double binarySearch(
      double lo, double hi, double target, DoubleFunction<Double> function) {

    if (hi <= lo) throw new IllegalArgumentException("hi should be greater than lo");

    double mid;
    do {

      // Find the middle point
      mid = (hi + lo) / 2.0;

      // Compute the value of our function for the middle point
      // Note that f can be any function not just the square root function
      double value = function.apply(mid);

      if (value > target) {
        hi = mid;
      } else {
        lo = mid;
      }

    } while ((hi - lo) > EPS);

    return mid;
  }

  public static void main(String[] args) {

    // EXAMPLE #1
    // Suppose we want to know what the square root of 875 is and
    // we have no knowledge of the wonderful Math.sqrt() function.
    // One approach is to use a binary search because we know that
    // the square root of 875 is bounded in the region: [0, 875].
    //
    // We can define our function to be f(x) = x*x and our target
    // value to be 875. As we binary search on f(x) approaching
    // successively closer values of 875 we get better and better
    // values of x (the square root of 875)

    double lo = 0.0;
    double hi = 875.0;
    double target = 875.0;

    DoubleFunction<Double> function = (x) -> (x * x);

    double sqrtVal = binarySearch(lo, hi, target, function);
    System.out.printf("sqrt(%.2f) = %.5f, x^2 = %.5f\n", target, sqrtVal, (sqrtVal * sqrtVal));

    // EXAMPLE #2
    // Suppose we want to find the radius of a sphere with volume 100m^3 using
    // a binary search. We know that for a sphere the volume is given by
    // V = (4/3)*pi*r^3, so all we have to do is binary search on the radius.
    //
    // Note: this is a silly example because you could just solve for r, but it
    // shows how binary search can be a powerful technique.

    double radiusLowerBound = 0;
    double radiusUpperBound = 1000;
    double volume = 100.0;
    DoubleFunction<Double> sphereVolumeFunction = (r) -> ((4.0 / 3.0) * Math.PI * r * r * r);

    double sphereRadius =
        binarySearch(radiusLowerBound, radiusUpperBound, volume, sphereVolumeFunction);

    System.out.printf("Sphere radius = %.5fm\n", sphereRadius);
  }
}
