/**
 * This file shows you how to determine if four 3D points lie in the same plane as each other.
 *
 * <p>Time Complexity: O(1)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.geometry;

import static java.lang.Math.*;

public class CoplanarPoints {

  private static final double EPS = 1e-7;

  // A simple 3D vector class
  public static class Vector {
    double x, y, z;

    public Vector(double xx, double yy, double zz) {
      x = xx;
      y = yy;
      z = zz;
    }
  }

  // Determine is four points (ax,ay,az), (bx,by,bz), (cx,cy,cz),
  // (dx,dy,dz) all lie in the same plane in 3D space
  public static boolean coplanar(
      double ax,
      double ay,
      double az,
      double bx,
      double by,
      double bz,
      double cx,
      double cy,
      double cz,
      double dx,
      double dy,
      double dz) {

    // The problem of testing if four points are coplanar can be converted
    // into a problem a checking if two vectors are orthogonal which is something
    // the dot product is excellent at (if the dot product of two vectors is zero
    // then they are orthogonal!)
    //
    // First, consider the three vectors spanning outwards from say point 'a':
    // v1 = b-a, v2 = c-a, and v3 = d-a. If we take the cross product of any
    // of these vectors (say v1 and v2) then we can obtain a fourth vector (v4)
    // normal to the plane in which v1 and v2 live, so if the point 'd' lies in
    // the same plane then the dot product of v3 and v4 should be 0

    Vector v1 = new Vector(bx - ax, by - ay, bz - az);
    Vector v2 = new Vector(cx - ax, cy - ay, cz - az);
    Vector v3 = new Vector(dx - ax, dy - ay, dz - az);

    // Find the vector normal (perpendicular) to v1 and v2
    Vector v4 = cross(v1, v2);

    // Check whether to dot product of v3 and v4 is zero
    return abs(dot(v3, v4)) < EPS;
  }

  // Cross product of two vectors
  private static Vector cross(Vector v1, Vector v2) {
    double v3x = v1.y * v2.z - v1.z * v2.y;
    double v3y = v1.z * v2.x - v1.x * v2.z;
    double v3z = v1.x * v2.y - v1.y * v2.x;
    return new Vector(v3x, v3y, v3z);
  }

  // 3D vector dot product
  private static double dot(Vector v1, Vector v2) {
    return (v1.x * v2.x) + (v1.y * v2.y) + (v1.z * v2.z);
  }

  // Examples
  public static void main(String[] args) {

    System.out.println(
        "The points (0,0,0), (1,0,1), (0,1,1), (1,1,2) are coplanar: "
            + coplanar(0, 0, 0, 1, 0, 1, 0, 1, 1, 1, 1, 2));

    System.out.println(
        "The points (0,0,0), (3,3,3), (3,0,0), (0,4,0) are coplanar: "
            + coplanar(0, 0, 0, 3, 3, 3, 3, 0, 0, 0, 4, 0));

    System.out.println(
        "The points (0,0,0), (1,1,1), (2,2,2), (3,3,3) are coplanar: "
            + coplanar(0, 0, 0, 1, 1, 1, 2, 2, 2, 3, 3, 3));
  }
}
