/**
 * This file shows you how to rotate a point clockwise relative to a fixed point a certain number of
 * radians.
 *
 * <p>Time Complexity: O(1)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.geometry;

import static java.lang.Math.*;

import java.awt.geom.Point2D;

public class PointRotation {

  // Rotate point 'pt' a certain number of radians clockwise
  // relative to some fixed point 'fp'. Note that the angle
  // should be specified in radians, not degrees.
  public static Point2D rotatePoint(Point2D fp, Point2D pt, double angle) {

    double fpx = fp.getX();
    double fpy = fp.getY();
    double ptx = pt.getX();
    double pty = pt.getY();

    // Compute the vector <x, y> from the fixed point
    // to the point of rotation.
    double x = ptx - fpx;
    double y = pty - fpy;

    // Apply the clockwise rotation matrix to the vector <x, y>
    // |  cosθ sinθ ||x|   |  xcosθ + ysinθ |
    // | -sinθ cosθ ||y| = | -xsinθ + ycosθ |
    double xRotated = x * cos(angle) + y * sin(angle);
    double yRotated = y * cos(angle) - x * sin(angle);

    // The rotation matrix rotated the vector about the origin, so we
    // need to offset it by the point (fpx, fpy) to get the right answer
    return new Point2D.Double(fpx + xRotated, fpy + yRotated);
  }

  public static void main(String[] args) {

    // Suppose we want to rotate the point (4,5) about the point
    // (3, 5) 45 degrees (pi/4 radians) clockwise 8 times until
    // it cycles back to it's original position:

    double angle = PI / 4.0;
    Point2D fixedPoint = new Point2D.Double(3, 5);
    Point2D point = new Point2D.Double(4, 5);

    // Prints all 8 rotations
    Point2D rotatedPoint = point;
    for (int i = 0; i < 8; i++) {
      System.out.println("Rotated point is now at: " + rotatedPoint);
      rotatedPoint = rotatePoint(fixedPoint, rotatedPoint, angle);
    }
    System.out.println("Rotated point is now at: " + rotatedPoint);
  }
}
