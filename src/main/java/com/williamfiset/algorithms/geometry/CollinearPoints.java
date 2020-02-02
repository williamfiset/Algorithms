/**
 * In this file I show you how to determine if three points are collinear to each other (on the same
 * line).
 *
 * <p>Time Complexity: O(1)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.geometry;

import static java.lang.Math.*;

import java.awt.geom.Point2D;

public class CollinearPoints {

  // Epsilon is a small double value used for double comparisons
  private static final double EPS = 1e-9;

  // Suppose a != b and the points a & b form an infinite line and we want
  // to determine if c is a point on that line. This method returns 0 if
  // it is on the line, -1 if c is to the right of the line and +1 if it's
  // to the left from the frame of reference of standing at point a
  // and facing point b.
  public static int collinear(Point2D a, Point2D b, Point2D c) {

    if (a.distance(b) < EPS) throw new IllegalArgumentException("a cannot equal b");

    // To determine if c is on the line we will use the determinant of
    // the two vectors formed by 'ab' and 'ac' (could also have done 'ba' and 'bc').
    // The determinant is a particularly useful property in linear algebra because
    // it can tell us the signed area of a parallelogram between two vectors.
    // Furthermore, we know that two vectors are parallel if the area of the
    // parallelogram between them is zero and we will use this fact to determine
    // if the point c is on the line formed by a & b

    // If a = (a_x, a_y), b = (b_x, b_y), c = (c_x, c_y) then our two vectors
    // 'ab' and 'ac' are v1 = <b_x-a_x, b_y-a_y> and v2 = <c_x-a_x, c_y-a_y>
    double v1_x = b.getX() - a.getX();
    double v1_y = b.getY() - a.getY();
    double v2_x = c.getX() - a.getX();
    double v2_y = c.getY() - a.getY();

    //                                                           | v1_x v2_x |
    // When we place our two vectors inside a 2x2 matrix we get: | v1_y v2_y |, and
    // hence we get that the determinant is (v1_x*v2_y)-(v2_x*v1_y)
    double determinant = (v1_x * v2_y) - (v2_x * v1_y);

    // The points a, b, c are collinear
    if (abs(determinant) < EPS) return 0;

    // Return -1 for right and +1 for left
    return (determinant < 0 ? -1 : +1);
  }

  // Compressed version of collinear code above
  public static int collinear2(Point2D a, Point2D b, Point2D c) {
    double area =
        (b.getX() - a.getX()) * (c.getY() - a.getY())
            - (b.getY() - a.getY()) * (c.getX() - a.getX());
    if (abs(area) < EPS) return 0;
    return (int) signum(area);
  }

  // Example of collinear points
  public static void main(String[] args) {

    Point2D a = new Point2D.Double(1, 1);
    Point2D b = new Point2D.Double(3, 3);
    Point2D c = new Point2D.Double(7, 7);

    // Returns 0 means that yes these points are collinear
    System.out.println(collinear(a, b, c));

    // Returns +1 meaning that c is to the left of the line
    c = new Point2D.Double(2, 7);
    System.out.println(collinear(a, b, c));

    // Returns -1 meaning that c is to the right of the line
    c = new Point2D.Double(2, -7);
    System.out.println(collinear(a, b, c));

    System.out.println(collinear2(a, b, c));
  }
}
