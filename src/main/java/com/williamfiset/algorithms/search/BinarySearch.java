package com.example;

import java.util.function.DoubleFunction;

public class BinarySearch {

  private static final double EPSILON = 0.00000001;

  /**
   * Performs binary search on the given function to find a value that is close to the target.
   *
   * @param lo       The lower bound of the search interval.
   * @param hi       The upper bound of the search interval.
   * @param target   The target value to find.
   * @param function The function to evaluate.
   * @return A value close to the target.
   * @throws IllegalArgumentException If the upper bound is not greater than the lower bound.
   */
  public static double binarySearch(double lo, double hi, double target, DoubleFunction<Double> function) {
    if (hi <= lo) {
      throw new IllegalArgumentException("The upper bound must be greater than the lower bound.");
    }

    double mid;
    do {
      mid = (hi + lo) / 2.0;
      double value = function.apply(mid);
      if (value > target) {
        hi = mid;
      } else {
        lo = mid;
      }
    } while ((hi - lo) > EPSILON);

    return mid;
  }

  public static void main(String[] args) {
    // EXAMPLE #1: Finding the square root of a number using binary search.
    double lo = 0.0;
    double hi = 875.0;
    double target = 875.0;
    DoubleFunction<Double> squareFunction = (x) -> (x * x);
    double sqrtVal = binarySearch(lo, hi, target, squareFunction);
    System.out.printf("sqrt(%.2f) = %.5f, x^2 = %.5f\n", target, sqrtVal, (sqrtVal * sqrtVal));

    // EXAMPLE #2: Finding the radius of a sphere with a given volume using binary search.
    double radiusLowerBound = 0;
    double radiusUpperBound = 1000;
    double volume = 100.0;
    DoubleFunction<Double> sphereVolumeFunction = (r) -> ((4.0 / 3.0) * Math.PI * r * r * r);
    double sphereRadius = binarySearch(radiusLowerBound, radiusUpperBound, volume, sphereVolumeFunction);
    System.out.printf("Sphere radius = %.5fm\n", sphereRadius);
  }
}
