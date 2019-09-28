/**
 * Given a convex polygon with integer coordinates in CCW order, return whether or not the specified
 * point is contained within the polygon or on the boundary. Time Complexity: O(log(n))
 *
 * @author Thomas Finn Lidbetter
 */
package com.williamfiset.algorithms.geometry;

import static java.lang.Math.*;

import java.awt.geom.Point2D;

public class ConvexPolygonContainsPoint {

  // Epsilon is a small double value used for double comparisons
  private static final double EPS = 1e-9;

  // Given a convex polygon with integer coordinates in CCW order, return whether
  // or not the specified point 'p' is contained within the polygon or on the boundary.
  // Time Complexity: O(log(n))
  //
  // NOTE: The last line of this snippet can be modified to include
  // the boundary of the polygon or not. "boundSign >= 0" includes it,
  // "boundSign > 0" does not include it.
  public static boolean containsPoint(Point2D[] hull, Point2D p) {

    Point2D p0 = hull[0];
    int lo = 1, hi = hull.length - 2, mid = (lo + hi) >>> 1;

    while (lo != hi) {
      Point2D p1 = hull[mid];
      double sign = collinear(p0, p1, p);
      if (sign >= 0) lo = mid;
      else if (sign < 0) hi = mid;
      mid = (lo + hi) >>> 1;
      if (hi - lo == 1) {
        if (collinear(p0, hull[hi], p) >= 0) lo = hi;
        else hi = lo;
      }
    }

    Point2D p1 = hull[lo], p2 = hull[lo + 1];
    double boundSign = collinear(p1, p2, p);
    double segSign1 = collinear(p0, p1, p);
    double segSign2 = collinear(p0, p2, p);

    return (boundSign >= 0 && segSign1 >= 0 && segSign2 <= 0);
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
}
