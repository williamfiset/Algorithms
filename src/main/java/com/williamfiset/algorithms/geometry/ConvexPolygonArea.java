/**
 * This file contains code that can find the area of a convex polygon if the points are given in CW
 * or CCW order.
 *
 * <p>Time Complexity: O(n)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.geometry;

import java.awt.geom.Point2D;

public class ConvexPolygonArea {

  // Given a set of points in CW of CCW order this method finds the area
  // of the convex polygon defined by those points. The input to this
  // method is an array of N+1 points where points[0] = points[N]
  public static double convexPolygonArea(Point2D[] points) {

    int N = points.length - 1;

    if (N < 3 || points[0] != points[N])
      throw new IllegalArgumentException(
          "Make sure N >= 3 and that the points array has n+1 points and points[0] = points[N]");

    // Let the first point be the fixed point
    Point2D fp = points[0];
    double area = 0, fpx = fp.getX(), fpy = fp.getY();

    for (int i = 1; i < N; i++) {

      Point2D pt1 = points[i];
      Point2D pt2 = points[i + 1];

      // Find the components of the vectors <a, c>
      // and <b, d> which are the vectors from the
      // fixed point to the points 'pt1' and 'pt2'
      double a = pt1.getX() - fpx;
      double b = pt2.getX() - fpx;
      double c = pt1.getY() - fpy;
      double d = pt2.getY() - fpy;

      // Sum the signed area of the parallelograms formed by
      // the vectors using formula for the determinant of
      // two vectors in R2 which is: ad - bc.
      area += a * d - b * c;
    }

    // Return half the area (since we were summing parallelograms)
    return Math.abs(area) / 2.0;
  }

  // Example of finding polygon area
  public static void main(String[] args) {

    int N = 5;

    // Make sure you allocate n+1 elements!
    Point2D[] points = new Point2D[N + 1];

    // Clockwise order
    points[0] = new Point2D.Double(0, 0);
    points[1] = new Point2D.Double(0, 2);
    points[2] = new Point2D.Double(1, 3);
    points[3] = new Point2D.Double(2, 2);
    points[4] = new Point2D.Double(2, 0);
    points[N] = points[0];

    System.out.println(convexPolygonArea(points));

    // Counterclockwise
    points[0] = new Point2D.Double(0, 0);
    points[1] = new Point2D.Double(2, 0);
    points[2] = new Point2D.Double(2, 2);
    points[3] = new Point2D.Double(1, 3);
    points[4] = new Point2D.Double(0, 2);
    points[N] = points[0];

    System.out.println(convexPolygonArea(points));
  }
}
