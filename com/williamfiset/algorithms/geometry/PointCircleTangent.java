/**
 * Given a circle and a point around or inside the circle we wish to find place(s) of intersection
 * of the lines from the point which are tangent to the circle. For an animation see here:
 * http://jsfiddle.net/zxqCw/1/
 *
 * <p>Time Complexity: O(1)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.geometry;

import static java.lang.Math.*;

import java.awt.geom.Point2D;

public class PointCircleTangent {

  // A very same epsilon value used as a threshold
  public static final double EPS = 0.0000001;

  // Due to double rounding precision the value passed into the asin
  // function may be outside its domain of [-1, +1] which would return
  // the value Double.NaN which we do not want.
  private static double arcsinSafe(double x) {
    if (x <= -1.0) return -PI / 2.0;
    if (x >= +1.0) return +PI / 2.0;
    return asin(x);
  }

  // Finds the point(s) of intersection of the lines tangent to the circle centered
  // at 'center' from the point 'point'.
  public static Point2D[] pointCircleTangentPoints(Point2D center, double radius, Point2D pt) {

    double px = pt.getX(), py = pt.getY();
    double cx = center.getX(), cy = center.getY();

    // Compute the distance to the circle center
    double dx = cx - px;
    double dy = cy - py;
    double dist = sqrt(dx * dx + dy * dy);

    // Point is strictly contained within the circle
    if (dist < radius) return new Point2D[] {};

    double angle, angle1, angle2;

    angle1 = arcsinSafe(radius / dist);
    angle2 = atan2(dy, dx);

    angle = angle2 - angle1;
    Point2D p1 = new Point2D.Double(cx + radius * sin(angle), cy + radius * -cos(angle));

    angle = angle1 + angle2;
    Point2D p2 = new Point2D.Double(cx + radius * -sin(angle), cy + radius * cos(angle));

    // Points are sufficiently close to be considered the same point
    // (i.e the original point is on the circle circumference)
    if (p1.distance(p2) < EPS) return new Point2D[] {pt};
    return new Point2D[] {p1, p2};
  }

  // Example usage
  public static void main(String[] args) {

    // Suppose there's a circle centered at (5, 0) with a radius
    // of two and a point at the origin (0,0) and we wish to determine
    // the intersection of the tangents when draw two lines extending from
    // the point at the origin onto the circumference of the circle

    // Circle
    double radius = 2.0;
    Point2D circleCenter = new Point2D.Double(5, 0);

    // Point
    Point2D origin = new Point2D.Double(0, 0);
    Point2D[] points = pointCircleTangentPoints(circleCenter, radius, origin);
    if (points.length == 2) {
      Point2D pt1 = points[0];
      Point2D pt2 = points[1];
      System.out.println("Points found: " + pt1 + " " + pt2);
    }
  }
}
