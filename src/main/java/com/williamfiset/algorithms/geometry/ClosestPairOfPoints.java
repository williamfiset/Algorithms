/**
 * This file contains code to find the closest pair of points given a list of points.
 *
 * <p>Time Complexity: O(nlog(n))
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.geometry;

import static java.lang.Math.*;

import java.util.*;

public class ClosestPairOfPoints {

  private static final double EPS = 1e-9;

  public class PT {
    double x, y;

    public PT(double xx, double yy) {
      x = xx;
      y = yy;
    }

    public double dist(PT pt) {
      double dx = x - pt.x, dy = y - pt.y;
      return sqrt(dx * dx + dy * dy);
    }
  }

  // Sorts points by X coordinate
  private static class X_Sort implements Comparator<PT> {
    @Override
    public int compare(PT pt1, PT pt2) {
      if (abs(pt1.x - pt2.x) < EPS) return 0;
      return (pt1.x < pt2.x) ? -1 : +1;
    }
  }

  // Sorts points by Y coordinate first then X coordinate
  private static class YX_Sort implements Comparator<PT> {
    @Override
    public int compare(PT pt1, PT pt2) {
      if (abs(pt1.y - pt2.y) < EPS) {
        if (abs(pt1.x - pt2.x) < EPS) return 0;
        return (pt1.x < pt2.x) ? -1 : +1;
      }
      return (pt1.y < pt2.y) ? -1 : +1;
    }
  }

  // Finds the closest pair of points in a list of points
  public static PT[] closestPair(PT[] points) {

    if (points == null || points.length < 2) return new PT[] {};

    final int n = points.length;
    int xQueueFront = 0, xQueueBack = 0;

    // Sort all point by x-coordinate
    Arrays.sort(points, new X_Sort());
    TreeSet<PT> yWorkingSet = new TreeSet<>(new YX_Sort());

    PT pt1 = null, pt2 = null;
    double d = Double.POSITIVE_INFINITY;

    for (int i = 0; i < n; i++) {

      PT nextPoint = points[i];

      // Remove all points (from both sets) where the distance to
      // the new point is greater than d (the smallest window size yet)
      while (xQueueFront != xQueueBack && nextPoint.x - points[xQueueFront].x > d) {
        PT pt = points[xQueueFront++];
        yWorkingSet.remove(pt);
      }

      // Look at all the points in our working set with a y-coordinate
      // above nextPoint.y but within a window of nextPoint.y + d
      double upperBound = nextPoint.y + d;
      PT next = yWorkingSet.higher(nextPoint);
      while (next != null && next.y <= upperBound) {
        double dist = nextPoint.dist(next);
        if (dist < d) {
          pt1 = nextPoint;
          pt2 = next;
          d = dist;
        }
        next = yWorkingSet.higher(next);
      }

      // Look at all the points in our working set with a y-coordinate
      // below nextPoint.y but within a window of nextPoint.y - d
      double lowerBound = nextPoint.y - d;
      next = yWorkingSet.lower(nextPoint);
      while (next != null && next.y > lowerBound) {
        double dist = nextPoint.dist(next);
        if (dist < d) {
          pt1 = nextPoint;
          pt2 = next;
          d = dist;
        }
        next = yWorkingSet.lower(next);
      }

      // Duplicate/stacked points
      if (yWorkingSet.contains(nextPoint)) {
        pt1 = pt2 = nextPoint;
        d = 0;
        break;
      }

      // Add the next point to the working set
      yWorkingSet.add(nextPoint);
      xQueueBack++;
    }

    return new PT[] {pt1, pt2};
  }
}
