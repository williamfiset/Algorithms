/**
 * This snippet finds the intersection of two line segments. The intersection may either be empty, a
 * single point or the intersection is a subsegment there's an overlap.
 *
 * <p>NOTE: In the code, I assume that a line segment (x1, y1), (x2, y2) with x1 = x2 and y1 = y2 is
 * a valid line segment. Mathematically speaking, a line segment consists of distinct points, but I
 * am allowing segments to be points in this implementation for completeness.
 *
 * @author: William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.geometry;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.List;

public class LineSegmentLineSegmentIntersection {

  // Small epsilon used for double value comparison.
  private static final double EPS = 1e-7;

  // 2D Point class.
  public static class Pt {
    double x, y;

    public Pt(double x, double y) {
      this.x = x;
      this.y = y;
    }

    public boolean equals(Pt pt) {
      return abs(x - pt.x) < EPS && abs(y - pt.y) < EPS;
    }
  }

  // Finds the intersection point(s) of two line segments. Unlike regular line
  // segments, segments which are points (x1 = x2 and y1 = y2) are allowed.
  public static Pt[] lineSegmentLineSegmentIntersection(Pt p1, Pt p2, Pt p3, Pt p4) {

    // No intersection.
    if (!segmentsIntersect(p1, p2, p3, p4)) return new Pt[] {};

    // Both segments are a single point.
    if (p1.equals(p2) && p2.equals(p3) && p3.equals(p4)) return new Pt[] {p1};

    List<Pt> endpoints = getCommonEndpoints(p1, p2, p3, p4);
    int n = endpoints.size();

    // One of the line segments is an intersecting single point.
    // NOTE: checking only n == 1 is insufficient to return early
    // because the solution might be a sub segment.
    boolean singleton = p1.equals(p2) || p3.equals(p4);
    if (n == 1 && singleton) return new Pt[] {endpoints.get(0)};

    // Segments are equal.
    if (n == 2) return new Pt[] {endpoints.get(0), endpoints.get(1)};

    boolean collinearSegments = (orientation(p1, p2, p3) == 0) && (orientation(p1, p2, p4) == 0);

    // The intersection will be a sub-segment of the two
    // segments since they overlap each other.
    if (collinearSegments) {

      // Segment #2 is enclosed in segment #1
      if (pointOnLine(p1, p2, p3) && pointOnLine(p1, p2, p4)) return new Pt[] {p3, p4};

      // Segment #1 is enclosed in segment #2
      if (pointOnLine(p3, p4, p1) && pointOnLine(p3, p4, p2)) return new Pt[] {p1, p2};

      // The subsegment is part of segment #1 and part of segment #2.
      // Find the middle points which correspond to this segment.
      Pt midPoint1 = pointOnLine(p1, p2, p3) ? p3 : p4;
      Pt midPoint2 = pointOnLine(p3, p4, p1) ? p1 : p2;

      // There is actually only one middle point!
      if (midPoint1.equals(midPoint2)) return new Pt[] {midPoint1};

      return new Pt[] {midPoint1, midPoint2};
    }

    /* Beyond this point there is a unique intersection point. */

    // Segment #1 is a vertical line.
    if (abs(p1.x - p2.x) < EPS) {
      double m = (p4.y - p3.y) / (p4.x - p3.x);
      double b = p3.y - m * p3.x;
      return new Pt[] {new Pt(p1.x, m * p1.x + b)};
    }

    // Segment #2 is a vertical line.
    if (abs(p3.x - p4.x) < EPS) {
      double m = (p2.y - p1.y) / (p2.x - p1.x);
      double b = p1.y - m * p1.x;
      return new Pt[] {new Pt(p3.x, m * p3.x + b)};
    }

    double m1 = (p2.y - p1.y) / (p2.x - p1.x);
    double m2 = (p4.y - p3.y) / (p4.x - p3.x);
    double b1 = p1.y - m1 * p1.x;
    double b2 = p3.y - m2 * p3.x;
    double x = (b2 - b1) / (m1 - m2);
    double y = (m1 * b2 - m2 * b1) / (m1 - m2);

    return new Pt[] {new Pt(x, y)};
  }

  // Finds the orientation of point 'c' relative to the line segment (a, b)
  // Returns  0 if all three points are collinear.
  // Returns -1 if 'c' is clockwise to segment (a, b), i.e right of line formed by the segment.
  // Returns +1 if 'c' is counter clockwise to segment (a, b), i.e left of line
  // formed by the segment.
  private static int orientation(Pt a, Pt b, Pt c) {
    double value = (b.y - a.y) * (c.x - b.x) - (b.x - a.x) * (c.y - b.y);
    if (abs(value) < EPS) return 0;
    return (value > 0) ? -1 : +1;
  }

  // Tests whether point 'c' is on the line segment (a, b).
  // Ensure first that point c is collinear to segment (a, b) and
  // then check whether c is within the rectangle formed by (a, b)
  private static boolean pointOnLine(Pt a, Pt b, Pt c) {
    return orientation(a, b, c) == 0
        && min(a.x, b.x) <= c.x
        && c.x <= max(a.x, b.x)
        && min(a.y, b.y) <= c.y
        && c.y <= max(a.y, b.y);
  }

  // Determines whether two segments intersect.
  private static boolean segmentsIntersect(Pt p1, Pt p2, Pt p3, Pt p4) {

    // Get the orientation of points p3 and p4 in relation
    // to the line segment (p1, p2)
    int o1 = orientation(p1, p2, p3);
    int o2 = orientation(p1, p2, p4);
    int o3 = orientation(p3, p4, p1);
    int o4 = orientation(p3, p4, p2);

    // If the points p1, p2 are on opposite sides of the infinite
    // line formed by (p3, p4) and conversly p3, p4 are on opposite
    // sides of the infinite line formed by (p1, p2) then there is
    // an intersection.
    if (o1 != o2 && o3 != o4) return true;

    // Collinear special cases (perhaps these if checks can be simplified?)
    if (o1 == 0 && pointOnLine(p1, p2, p3)) return true;
    if (o2 == 0 && pointOnLine(p1, p2, p4)) return true;
    if (o3 == 0 && pointOnLine(p3, p4, p1)) return true;
    if (o4 == 0 && pointOnLine(p3, p4, p2)) return true;

    return false;
  }

  private static List<Pt> getCommonEndpoints(Pt p1, Pt p2, Pt p3, Pt p4) {

    List<Pt> points = new ArrayList<>();

    if (p1.equals(p3)) {
      points.add(p1);
      if (p2.equals(p4)) points.add(p2);

    } else if (p1.equals(p4)) {
      points.add(p1);
      if (p2.equals(p3)) points.add(p2);

    } else if (p2.equals(p3)) {
      points.add(p2);
      if (p1.equals(p4)) points.add(p1);

    } else if (p2.equals(p4)) {
      points.add(p2);
      if (p1.equals(p3)) points.add(p1);
    }

    return points;
  }

  public static void main(String[] args) {

    // Segment #1 is (p1, p2), segment #2 is (p3, p4)
    Pt p1, p2, p3, p4;

    p1 = new Pt(-2, 4);
    p2 = new Pt(3, 3);
    p3 = new Pt(0, 0);
    p4 = new Pt(2, 4);
    Pt[] points = lineSegmentLineSegmentIntersection(p1, p2, p3, p4);
    Pt point = points[0];

    // Prints: (1.636, 3.273)
    System.out.printf("(%.3f, %.3f)\n", point.x, point.y);

    p1 = new Pt(-10, 0);
    p2 = new Pt(+10, 0);
    p3 = new Pt(-5, 0);
    p4 = new Pt(+5, 0);
    points = lineSegmentLineSegmentIntersection(p1, p2, p3, p4);
    Pt point1 = points[0], point2 = points[1];

    // Prints: (-5.000, 0.000) (5.000, 0.000)
    System.out.printf("(%.3f, %.3f) (%.3f, %.3f)\n", point1.x, point1.y, point2.x, point2.y);
  }
}
