/**
 * A working implementation of the GrahamScan convex hull algorithm
 *
 * <p>Time Complexity: O(nlogn)
 *
 * @author Micah Stairs, William Fiset
 */
package com.williamfiset.algorithms.geometry;

import java.awt.geom.Point2D;
import java.util.*;

public class ConvexHullGrahamScan {

  // Construct a convex hull and return it as a stack of points
  public static Stack<Point2D> createConvexHull(Point2D[] pts) {
    int k1, k2, N = pts.length;
    Stack<Point2D> hull = new Stack<Point2D>();
    Arrays.sort(pts, new PointOrder());
    Arrays.sort(pts, 1, N, new PolarOrder(pts[0]));
    hull.push(pts[0]);
    for (k1 = 1; k1 < N; k1++) {
      if (!pts[0].equals(pts[k1])) {
        break;
      }
    }
    if (k1 == N) return null;
    for (k2 = k1 + 1; k2 < N; k2++) {
      if (collinear(pts[0], pts[k1], pts[k2]) != 0) {
        break;
      }
    }
    hull.push(pts[k2 - 1]);
    for (int i = k2; i < N; i++) {
      Point2D top = hull.pop();
      while (collinear(hull.peek(), top, pts[i]) <= 0) {
        top = hull.pop();
      }
      hull.push(top);
      hull.push(pts[i]);
    }
    return hull;
  }

  // Compare other points relative to polar angle (between 0 and 2*PI) they make with this point
  private static class PolarOrder implements Comparator<Point2D> {
    Point2D pt;

    public PolarOrder(Point2D pt) {
      this.pt = pt;
    }

    @Override
    public int compare(Point2D q1, Point2D q2) {
      double dx1 = q1.getX() - pt.getX(), dy1 = q1.getY() - pt.getY();
      double dx2 = q2.getX() - pt.getX(), dy2 = q2.getY() - pt.getY();
      if (dy1 >= 0 && dy2 < 0) return -1;
      else if (dy2 >= 0 && dy1 < 0) return +1;
      else if (dy1 == 0 && dy2 == 0) {
        if (dx1 >= 0 && dx2 < 0) return -1;
        else if (dx2 >= 0 && dx1 < 0) return +1;
        else return 0;
      } else return -collinear(pt, q1, q2);
    }
  }

  // Put lower Y co-ordinates first, with a lower X value in the case of ties
  private static class PointOrder implements Comparator<Point2D> {
    @Override
    public int compare(Point2D q1, Point2D q2) {
      if (q1.getY() < q2.getY()) return -1;
      if (q1.getY() == q2.getY()) {
        if (q1.getX() < q2.getX()) return -1;
        else if (q1.getX() > q2.getX()) return 1;
        else return 0;
      }
      return 1;
    }
  }

  // Check to see whether the points are ordered clockwise or counter-clockwise (0 indicates that
  // they are collinear)
  private static int collinear(Point2D a, Point2D b, Point2D c) {
    double area =
        (b.getX() - a.getX()) * (c.getY() - a.getY())
            - (b.getY() - a.getY()) * (c.getX() - a.getX());
    return (int) Math.signum(area);
  }

  public static void main(String[] args) {

    Point2D[] pts = new Point2D[13];

    pts[0] = new Point2D.Double(0, 5);
    pts[1] = new Point2D.Double(-1, 1);
    pts[2] = new Point2D.Double(0, 1);
    pts[3] = new Point2D.Double(1, 1);
    pts[4] = new Point2D.Double(-5, 0);
    pts[5] = new Point2D.Double(-1, 0);
    pts[6] = new Point2D.Double(0, 0);
    pts[7] = new Point2D.Double(1, 0);
    pts[8] = new Point2D.Double(5, 0);
    pts[9] = new Point2D.Double(-1, -1);
    pts[10] = new Point2D.Double(0, -1);
    pts[11] = new Point2D.Double(1, -1);
    pts[12] = new Point2D.Double(0, -5);

    Stack<Point2D> hull = createConvexHull(pts);

    // Print the points in the hull
    while (!hull.isEmpty()) System.out.println(hull.pop());
  }
}
