/**
 * There is more than one way to take the area of a triangle, some are faster or more convenient in
 * some situations.
 *
 * <p>Time Complexity: O(1)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.geometry;

import static java.lang.Math.*;

import java.awt.geom.*;

public class TriangleArea {

  // Given three sides of a triangle use Heron's formula
  // to compute the area of the triangle.
  public static double triangleArea(double side1, double side2, double side3) {

    // Semi-perimeter
    double s = (side1 + side2 + side3) / 2.0;

    // Square root makes this method slower than the others
    return sqrt(s * (s - side1) * (s - side2) * (s - side3));
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

  // Given three points a,b,c find the area of the triangle
  public static double triangleArea(Point2D a, Point2D b, Point2D c) {
    return triangleArea(a.getX(), a.getY(), b.getX(), b.getY(), c.getX(), c.getY());
  }

  // The traditional triangle area formula
  public static double triangleArea(double base, double height) {
    return base * (height / 2.0);
  }

  // Example usage
  public static void main(String[] args) {

    Point2D a = new Point2D.Double(0, 1);
    Point2D b = new Point2D.Double(2, 0);
    Point2D c = new Point2D.Double(3, 4);

    System.out.println(triangleArea(a, b, c));

    double side1 = a.distance(b);
    double side2 = a.distance(c);
    double side3 = b.distance(c);

    System.out.println(triangleArea(side1, side2, side3));
  }
}
