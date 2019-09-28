/**
 * This file shows you how to determine if a point is inside or on the boundary of a triangle formed
 * by three points.
 *
 * <p>Time Complexity: O(1)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.geometry;

import static java.lang.Math.*;

import java.awt.geom.Point2D;

public class PointInsideTriangle {

  private static final double EPS = 1e-8;

  // Determine if the point 'p' lies inside or exactly on the border of the
  // triangle formed by the three points a,b,c. If you do want to exclude
  // the border modify this method so that instead of checking against '<=' and
  // '>=' it checks against strictly less than and strictly greater than.
  public static boolean pointInsideTriangle(Point2D a, Point2D b, Point2D c, Point2D p) {

    // Points a,b,c form a degenerate triangle
    if (collinear(a, b, c) == 0) {
      throw new IllegalArgumentException("points a,b,c do not form a triangle!");
    }

    // Compute the directions the point 'p' is relative to
    // the three half planes formed by the points of the triangle
    int dir1 = collinear(a, b, p);
    int dir2 = collinear(b, c, p);
    int dir3 = collinear(c, a, p);

    // Check if two of the half planes overlap in
    // the area where the triangle is found.
    return (dir1 <= 0 && dir2 <= 0 && dir3 <= 0) || (dir1 >= 0 && dir2 >= 0 && dir3 >= 0);
  }

  // A slightly more optimized version which does less comparisons.
  public static boolean pointInsideTriangle2(Point2D a, Point2D b, Point2D c, Point2D p) {

    // Points a,b,c form a degenerate triangle
    if (collinear(a, b, c) == 0) {
      throw new IllegalArgumentException("points a,b,c do not form a triangle!");
    }

    // Change '<' to '<=' to exclude points on the boundary
    boolean dir1 = collinear(a, b, p) < 0;
    boolean dir2 = collinear(b, c, p) < 0;
    boolean dir3 = collinear(c, a, p) < 0;

    return (dir1 == dir2) && (dir2 == dir3);
  }

  // Suppose a != b and the points a & b form an infinite line and we want
  // to determine if c is a point on that line. This method returns 0 if
  // it is on the line, -1 if c is to the right of the line and +1 if it's
  // to the left from the frame of reference of standing at point a
  // and facing point b.
  private static int collinear(Point2D a, Point2D b, Point2D c) {
    double ax = a.getX(), ay = a.getY();
    double bx = b.getX(), by = b.getY();
    double cx = c.getX(), cy = c.getY();
    double area = (bx - ax) * (cy - ay) - (by - ay) * (cx - ax);
    if (abs(area) < EPS) return 0;
    return (int) signum(area);
  }

  public static void main(String[] args) {

    Point2D a = new Point2D.Double(0, 5);
    Point2D b = new Point2D.Double(0, 0);
    Point2D c = new Point2D.Double(5, 0);

    for (int i = -2; i < 7; i++) {
      for (int j = -2; j < 7; j++) {
        Point2D p = new Point2D.Double(i, j);
        if (pointInsideTriangle(a, b, c, p)) {
          System.out.printf("(%.3f,%.3f) is inside the triangle\n", p.getX(), p.getY());
        }
      }
    }

    System.out.println();

    for (int i = -2; i < 7; i++) {
      for (int j = -2; j < 7; j++) {
        Point2D p = new Point2D.Double(i, j);
        if (pointInsideTriangle2(a, b, c, p)) {
          System.out.printf("(%.3f,%.3f) is inside the triangle\n", p.getX(), p.getY());
        }
      }
    }
  }
}
