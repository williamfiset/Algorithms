/**
 * This file shows you how to find the minimum cost convex polygon triangulation of a set of points.
 * Points must be in either clockwise or counterclockwise order.
 *
 * <p>Time Complexity: O(n^3)
 *
 * @author Bryan Bowles
 */
package com.williamfiset.algorithms.geometry;

import java.awt.geom.Point2D;

// Problem explanation: https://www.geeksforgeeks.org/minimum-cost-polygon-triangulation/
public class MinimumCostConvexPolygonTriangulation {

  // Returns the perimeter (cost) of the triangle
  private static double cost(Point2D i, Point2D j, Point2D k) {
    return i.distance(j) + i.distance(k) + j.distance(k);
  }

  // Input must be a convex polygon with points in CW or CCW order.
  public static double minimumCostTriangulation(Point2D[] polygon) {
    int len = polygon.length;
    if (len < 3) return 0;

    double[][] dp = new double[len][len];
    for (int i = 2; i < len; i++) {
      for (int j = 0; j + i < len; j++) {
        dp[j][j + i] = Integer.MAX_VALUE;
        for (int k = j + 1; k < j + i; k++) {
          dp[j][j + i] =
              Math.min(
                  dp[j][j + i],
                  dp[j][k] + dp[k][j + i] + cost(polygon[j], polygon[j + i], polygon[k]));
        }
      }
    }
    return dp[0][len - 1];
  }
}
