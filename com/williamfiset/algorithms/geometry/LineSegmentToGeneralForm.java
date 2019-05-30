/**
 * This file shows you how to convert a line segment (x1,y1), (x2,y2) into the general form for a
 * line: ax + by + c = 0
 *
 * <p>Time Complexity: O(1)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.geometry;

public class LineSegmentToGeneralForm {

  // Given a line segment as (x1,y1), (x2,y2) this method
  // returns the general form of a line ax + by + c = 0
  // NOTE: this is not the same as: ax + by = c
  public static double[] segmentToGeneralForm(double x1, double y1, double x2, double y2) {

    // Starting with y = mx we can replace y with (y-y1), m with (y2-y1)/(x2-x1)
    // and x with (x-x1), so we obtain (y-y1) = [(y2-y1)/(x2-x1)](x-x1) and simplify to:
    // x(y1-y2) + y(x2-x1) + (x1y2-y1x2) = 0 and hence we conclude that a = (y1-y2)
    // b = (x2-x1) and c = (x1y2-y1x2)

    // a = abc[0], b = abc[1], c = abc[2]
    double[] abc = new double[3];

    abc[0] = y1 - y2;
    abc[1] = x2 - x1;
    abc[2] = x1 * y2 - y1 * x2; // or y1*x2 - x1*y2; for ax + by = c

    return abc;
  }

  // Example converting to general form
  public static void main(String[] args) {

    // The line segment (1, 1), (3, -4) gives the
    // line _x + _y + _ = 0 in general form
    double[] abc = segmentToGeneralForm(1, 1, 3, -4);
    System.out.printf("%.2fx + %.2fy + %.2f = 0\n", abc[0], abc[1], abc[2]);
  }
}
