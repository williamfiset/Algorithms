/**
 * This file shows you how to find the smaller of the two angles between two vectors in R3
 *
 * <p>Time Complexity: O(1)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.geometry;

import static java.lang.Math.*;

public class AngleBetweenVectors3D {

  // Return the smaller of the two angles between two 3D vectors in radians
  public static double angleBetweenVectors(
      double v1x, double v1y, double v1z, double v2x, double v2y, double v2z) {
    // To determine the angle between two vectors v1 and v2 we can use
    // the following formula: dot(v1,v2) = len(v1)*len(v2)*cosθ and solve
    // for θ where dot(a,b) is the dot product and len(c) is the length of c.
    double dotproduct = (v1x * v2x) + (v1y * v2y) + (v1z * v2z);
    double v1Length = sqrt(v1x * v1x + v1y * v1y + v1z * v1z);
    double v2Length = sqrt(v2x * v2x + v2y * v2y + v2z * v2z);

    double value = dotproduct / (v1Length * v2Length);

    // Double precision may lead to a slightly invalid domain for the
    // arccos function, so make sure to check boundary conditions
    if (value <= -1.0) return PI;
    if (value >= +1.0) return 0;
    return acos(value);
  }

  public static void main(String[] args) {
    System.out.println(
        "Angle between  (1,1,0),  (1,1,1): " + angleBetweenVectors(1, 1, 0, 1, 1, 1));
    System.out.println(
        "Angle between  (5,5,5),  (5,5,5): " + angleBetweenVectors(5, 5, 5, 5, 5, 5));
    System.out.println(
        "Angle between  (-5,-5,-5),  (-5,-5,-5): " + angleBetweenVectors(-5, -5, -5, -5, -5, -5));
    System.out.println(
        "Angle between  (2,2,2),  (-1,-1,-1): " + angleBetweenVectors(2, 2, 2, -1, -1, -1));
    System.out.println(
        "Angle between  (4,-6,5), (-3,7,12): " + angleBetweenVectors(4, -6, 5, -3, 7, 12));
  }
}
