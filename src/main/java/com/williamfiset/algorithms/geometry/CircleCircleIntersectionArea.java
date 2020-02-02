/**
 * This file shows you how to find the area of intersection of two circles
 *
 * <p>Time Complexity: O(1)
 *
 * <p>NOTE: This file could use some formal testing.
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.geometry;

import static java.lang.Math.*;

import java.awt.geom.*;

public class CircleCircleIntersectionArea {

  private static final double EPS = 1e-6;

  // Due to double rounding precision the value passed into the acos
  // function may be outside its domain of [-1, +1] which would return
  // the value Double.NaN which we do not want.
  private static double arccosSafe(double x) {
    if (x >= +1.0) return 0;
    if (x <= -1.0) return PI;
    return acos(x);
  }

  public static double circleCircleIntersectionArea(Point2D c1, double r1, Point2D c2, double r2) {

    double r = r1, R = r2;
    Point2D c = c1, C = c2;

    if (r1 > r2) {
      r = r2;
      R = r1;
      c = c2;
      C = c1;
    }

    double dist = c1.distance(c2);
    Point2D[] intersections = circleCircleIntersection(c1, r1, c2, r2);

    // Smaller circle is contained within larger circle
    if (intersections == null) {
      return PI * r * r;

      // Circles do not intersect
    } else if (intersections.length == 0) {
      return 0;

      // One intersection point
    } else if (intersections.length == 1) {

      // Check if the smaller circle is inside larger circle
      if (dist < R) {
        return PI * r * r;
      } else {
        return 0;
      }

      // Circles intersect at two distinct points
    } else {

      // http://stackoverflow.com/questions/4247889/area-of-intersection-between-two-circles
      double d = dist;
      Double part1 = r * r * arccosSafe((d * d + r * r - R * R) / (2 * d * r));
      Double part2 = R * R * arccosSafe((d * d + R * R - r * r) / (2 * d * R));
      Double part3 = 0.5 * sqrt((-d + r + R) * (d + r - R) * (d - r + R) * (d + r + R));

      return part1 + part2 - part3;
    }
  }

  // Finds the intersection points of two circles. If the smaller circle is contained
  // within the larger one this method returns null. If there is no intersection point
  // an empty array is returned. If there is a unique intersection point that point alone
  // is returned, otherwise two distinct points are returned where the intersection is.
  public static Point2D[] circleCircleIntersection(Point2D c1, double r1, Point2D c2, double r2) {

    // r is the smaller radius and R is bigger radius
    double r, R;

    // c is the center of the small circle
    // C is the center of the big circle
    Point2D c, C;

    // Determine which is the bigger/smaller circle
    if (r1 < r2) {
      r = r1;
      R = r2;
      c = c1;
      C = c2;
    } else {
      r = r2;
      R = r1;
      c = c2;
      C = c1;
    }

    double dist = c1.distance(c2);

    // There are an infinite number of solutions
    if (dist < EPS && abs(r - R) < EPS) return null;

    // No intersection (small circle contained within big circle)
    if (r + dist < R) return null;

    // No intersection (circles are disjoint)
    if (r + R < dist) return new Point2D[] {};

    // Let (cx, cy) be the center of the small circle
    // Let (Cx, Cy) be the center of the larger circle
    double cx = c.getX();
    double Cx = C.getX();
    double cy = c.getY();
    double Cy = C.getY();

    // Compute the vector from the big circle to the little circle
    double vx = cx - Cx;
    double vy = cy - Cy;

    // Scale the vector by R and offset the vector so that the head of the vector
    // is positioned on the circumference of the big circle ready to be rotated
    double x = (vx / dist) * R + Cx;
    double y = (vy / dist) * R + Cy;
    Point2D point = new Point2D.Double(x, y);

    // Unique intersection point on circumference of both circles
    if (abs(r + R - dist) < EPS || abs(R - (r + dist)) < EPS) return new Point2D[] {point};

    // Find the angle via cos law
    double angle = arccosSafe((r * r - dist * dist - R * R) / (-2.0 * dist * R));

    // Two unique intersection points
    Point2D pt1 = rotatePoint(C, point, angle);
    Point2D pt2 = rotatePoint(C, point, -angle);

    return new Point2D[] {pt1, pt2};
  }

  // Rotate point 'pt' a certain number of radians clockwise
  // relative to some fixed point 'fp'. Note that the angle
  // should be specified in radians, not degrees.
  private static Point2D rotatePoint(Point2D fp, Point2D pt, double angle) {

    double fpx = fp.getX();
    double fpy = fp.getY();
    double ptx = pt.getX();
    double pty = pt.getY();

    // Compute the vector <x, y> from the fixed point
    // to the point of rotation.
    double x = ptx - fpx;
    double y = pty - fpy;

    // Apply the clockwise rotation matrix to the vector <x, y>
    // |  cos(theta) sin(theta) ||x|   |  xcos(theta) + ysin(theta) |
    // | -sin(theta) cos(theta) ||y| = | -xsin(theta) + ycos(theta) |
    double xRotated = x * cos(angle) + y * sin(angle);
    double yRotated = y * cos(angle) - x * sin(angle);

    // The rotation matrix rotated the vector about the origin, so we
    // need to offset it by the point (fpx, fpy) to get the right answer
    return new Point2D.Double(fpx + xRotated, fpy + yRotated);
  }
}
