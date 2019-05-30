/**
 * "[Andrew, 1979] discovered an alternative to the Graham scan that uses a linear lexicographic
 * sort of the point set by the x and y-coordinates. This is an advantage if this ordering is
 * already known for a set, which is sometimes the case. But even if sorting is required, this is a
 * faster sort than the angular Graham-scan sort with its more complicated comparison function. The
 * "Monotone Chain" algorithm computes the upper and lower hulls of a monotone chain of points,
 * which is why we refer to it as the "Monotone Chain" algorithm. Like the Graham scan, it runs in
 * O(nlog-n) time due to the sort time. After that, it only takes O(n) time to compute the hull." -
 * Dan Sunday
 *
 * <p>This code is a modification of the monotone chains algorithm found on wikibooks.
 * https://en.wikibooks.org/wiki/Algorithm_Implementation/Geometry/Convex_hull/Monotone_chain
 *
 * <p>Time Complexity: O(nlogn)
 *
 * @author A. M. Andrew, Dan Sunday, William Fiset
 */
package com.williamfiset.algorithms.geometry;

import static java.lang.Math.abs;

import java.awt.geom.*;
import java.util.*;

public class ConvexHullMonotoneChainsAlgorithm {

  // Small epsilon used for double value comparison.
  private static final double EPS = 1e-5;

  // Sorts points by first x coordinate and then y coordinate.
  private static class PointComparator implements Comparator<Point2D> {
    public int compare(Point2D p1, Point2D p2) {
      if (abs(p1.getX() - p2.getX()) < EPS) {
        if (abs(p1.getY() - p2.getY()) < EPS) return 0;
        else if (p1.getY() > p2.getY()) return 1;
      } else if (p1.getX() > p2.getX()) return 1;
      return -1;
    }
  }

  // Use the monotone chains algorithm to find the
  // convex hull of a set of points in O(nlogn) time.
  public static Point2D[] convexHull(Point2D[] pts) {

    int n = pts.length, k = 0;
    if (n <= 1) return pts;

    Point2D[] hull = new Point2D[2 * n];
    Arrays.sort(pts, new PointComparator());

    // Build upper chain.
    for (int i = 0; i < n; i++) {
      while (k >= 2 && orientation(hull[k - 2], hull[k - 1], pts[i]) <= 0) k--;
      hull[k++] = pts[i];
    }

    int lastUpperChainIndex = k;

    // Build lower chain.
    for (int i = n - 2; i >= 0; i--) {
      while (k > lastUpperChainIndex && orientation(hull[k - 2], hull[k - 1], pts[i]) <= 0) k--;
      hull[k++] = pts[i];
    }

    // Conserve only unique points.
    int index = 1;
    Point2D lastPt = hull[0];
    for (int i = 1; i < k - 1; i++) {
      if (!hull[i].equals(lastPt)) {
        hull[index++] = lastPt = hull[i];
      }
    }

    return Arrays.copyOfRange(hull, 0, index);
  }

  // To find orientation of point 'c' relative to the line segment (a, b).
  // Imagine yourself standing at point 'a' looking out towards point 'b'.
  // Returns  0 if all three points are collinear.
  // Returns -1 if 'c' is clockwise to segment (a, b), i.e right of line formed by the segment.
  // Returns +1 if 'c' is counter clockwise to segment (a, b), i.e left of line
  // formed by the segment.
  private static int orientation(Point2D a, Point2D b, Point2D c) {
    double value =
        (b.getY() - a.getY()) * (c.getX() - b.getX())
            - (b.getX() - a.getX()) * (c.getY() - b.getY());
    if (abs(value) < EPS) return 0;
    return (value > 0) ? -1 : +1;
  }
}
