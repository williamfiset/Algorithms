/**
 * This file shows you how to find the area of a circular segment which lies on the circumference of
 * a circle of radius r.
 *
 * <p>Time Complexity: O(1)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.geometry;

import static java.lang.Math.*;

import java.awt.geom.Point2D;

public class CircularSegmentArea {

  public static double circularSegmentArea(Point2D center, double r, Point2D p1, Point2D p2) {

    double cx = center.getX(), cy = center.getY();
    double x1 = p1.getX(), y1 = p1.getY();
    double x2 = p2.getX(), y2 = p2.getY();

    double circleArea = PI * r * r;

    // To find the area of the circular segment cap we will find the area not occupied
    // by the cap and compute pi*r^2 minus that area. The area not occupied by the circular
    // cap can be divided into three parts:
    // 1) A section pertaining to half the circle's area
    // 2) The area of the triangle formed by (cx,cy), (x1,y1), (x2,y2)
    // 3) The remaining slice which looks like a slice of pizza

    double halfCircleArea = circleArea / 2.0;
    double threePointsArea = triangleArea(cx, cy, x1, y1, x2, y2);

    // Compute both vectors from the center of the circle
    // to the points on the circumference of the circle.
    double v1x = x1 - cx, v1y = y1 - cy;
    double v2x = x2 - cx, v2y = y2 - cy;

    // Turn one of the vector around to point in the opposite direction
    v1x = -v1x;
    v1y = -v1y;

    // Compute the angle between the vectors and find the slice area
    double angle = angleBetweenVectors(v1x, v1y, v2x, v2y);
    double pizzaSliceArea = circleArea * (angle / (2.0 * PI));

    return circleArea - (halfCircleArea + threePointsArea + pizzaSliceArea);
  }

  // Given three points a, b, c find the area of the triangle
  public static double triangleArea(
      double ax, double ay, double bx, double by, double cx, double cy) {

    // Let v1 = <bx-ax, by-ay> and v2 = <cx-ax, cy-ay> be vectors
    // Take the determinant of v1 and v2 to find the area of the
    // parallelogram of the two vectors.

    double v1_x = bx - ax;
    double v1_y = by - ay;
    double v2_x = cx - ax;
    double v2_y = cy - ay;

    //                | v1_x v2_x |
    // Determinant of | v1_y v2_y | is v1_x*v2_y - v2_x*v1_y
    double determinant = v1_x * v2_y - v2_x * v1_y;

    // Divide by two because we computed the area of the parallelogram
    return abs(determinant) / 2.0;
  }

  // Return the smaller of the two angles between two 2D vectors in radians
  private static double angleBetweenVectors(double v1x, double v1y, double v2x, double v2y) {

    // To determine the angle between two vectors v1 and v2 we can use
    // the following formula: dot(v1,v2) = len(v1)*len(v2)*cosθ and solve
    // for θ where dot(a,b) is the dot product and len(c) is the length of c.
    double dotproduct = (v1x * v2x) + (v1y * v2y);
    double v1Length = sqrt(v1x * v1x + v1y * v1y);
    double v2Length = sqrt(v2x * v2x + v2y * v2y);

    double value = dotproduct / (v1Length * v2Length);

    // Double value rounding precision may lead to the value we're about to pass into
    // the arccos function to be slightly outside its domain, so do a safety check.
    if (value <= -1.0) return PI;
    if (value >= +1.0) return 0;
    return acos(value);
  }

  public static void main(String[] args) {

    Point2D center = new Point2D.Double(0, 0);
    Point2D p1 = new Point2D.Double(2, 0);
    Point2D p2 = new Point2D.Double(-2, 0);
    System.out.println(circularSegmentArea(center, 2.0, p1, p2));

    p1 = new Point2D.Double(0, 3);
    p2 = new Point2D.Double(3, 0);
    System.out.println(circularSegmentArea(center, 3.0, p1, p2));
  }
}
