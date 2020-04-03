/**
 * This algorithm cuts a ordered convex polygon with a line segment and returns the two resulting
 * pieces.
 *
 * <p>Time Complexity: O(nlogn)
 *
 * @author Finn Lidbetter
 */
package com.williamfiset.algorithms.geometry;

import java.util.*;

public class ConvexPolygonCutWithLineSegment {

  private static final double EPS = 1e-9;

  // Simple 2D point class.
  public static class Pt {
    double x, y;

    public Pt(double x, double y) {
      this.x = x;
      this.y = y;
    }

    @Override
    public String toString() {
      return "(" + x + "," + y + ")";
    }
  }

  // sorts the points in CW direction.
  public static List<Pt> sortCW(List<Pt> poly) {

    int l = poly.size();
    double centroidX = 0;
    double centroidY = 0;
    for (int i = 0; i < l; i++) {
      centroidX += poly.get(i).x;
      centroidY += poly.get(i).y;
    }
    centroidX = centroidX / l;
    centroidY = centroidY / l;
    Pt center = new Pt(centroidX, centroidY);

    Collections.sort(
        poly,
        (a, b) -> {
          double a1 = (Math.toDegrees(Math.atan2(a.x - center.x, a.y - center.y)) + 360) % 360;
          double a2 = (Math.toDegrees(Math.atan2(b.x - center.x, b.y - center.y)) + 360) % 360;
          return (int) (a1 - a2);
        });
    return poly;
  }

  // Cuts a convex polygon by a specified line and returns one part
  // of the polygon (swapping the endpoints p1 and p2 of the line
  // will return the other part of the polygon).
  public static Pt[] cut(Pt[] poly, Pt p1, Pt p2) {
    int n = poly.length;
    List<Pt> res = new ArrayList<>();
    for (int i = 0, j = n - 1; i < n; j = i++) {
      int d1 = orientation(p1.x, p1.y, p2.x, p2.y, poly[j].x, poly[j].y);
      int d2 = orientation(p1.x, p1.y, p2.x, p2.y, poly[i].x, poly[i].y);
      if (d1 >= 0) res.add(poly[j]);
      if (d1 * d2 < 0)
        res.add(intersect(p1.x, p1.y, p2.x, p2.y, poly[j].x, poly[j].y, poly[i].x, poly[i].y));
    }
    return res.toArray(new Pt[res.size()]);
  }

  private static Pt intersect(
      double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
    double a1 = y2 - y1, a2 = y4 - y3, b1 = x1 - x2, b2 = x3 - x4;
    double c1 = -(x1 * y2 - x2 * y1), c2 = -(x3 * y4 - x4 * y3);
    double det = a1 * b2 - a2 * b1;
    double x = -(c1 * b2 - c2 * b1) / det, y = -(a1 * c2 - a2 * c1) / det;
    return new Pt(x, y);
  }

  private static int orientation(double ax, double ay, double bx, double by, double cx, double cy) {
    bx -= ax;
    by -= ay;
    cx -= ax;
    cy -= ay;
    double cross = bx * cy - by * cx;
    return cross < -EPS ? -1 : cross > EPS ? 1 : 0;
  }

  // takes Pt[] as an argument and returns List<Pt>
  public static List<Pt> makeList(Pt[] squarePolygon) {
    List<Pt> list = new ArrayList<Pt>();
    for (int i = 0; i < squarePolygon.length; i++) {
      list.add(squarePolygon[i]);
    }
    return list;
  }

  // takes List<Pt> as an argument and returns Pt[]
  public static Pt[] makeArray(List<Pt> list) {
    int l = list.size();
    Pt[] temp = new Pt[l];
    for (int i = 0; i < l; i++) {
      temp[i] = list.get(i);
    }
    return temp;
  }

  // Example usage
  public static void main(String[] args) {

    Pt[] squarePolygon = {new Pt(0, 0), new Pt(0, 4), new Pt(4, 0), new Pt(4, 4), new Pt(0, 2)};
    Pt p1 = new Pt(-1, -1);
    Pt p2 = new Pt(5, 5);

    List<Pt> list = makeList(squarePolygon);
    list = sortCW(list);
    squarePolygon = makeArray(list);

    Pt[] poly1 = cut(squarePolygon, p1, p2);
    Pt[] poly2 = cut(squarePolygon, p2, p1);

    System.out.println("First polygon:");
    for (Pt pt : poly1) System.out.println(pt);
    // Prints:
    // First polygon:
    // (0.0,4.0)
    // (4.0,4.0)
    // (0.0,0.0)
    // (0.0,2.0)

    System.out.println("\nSecond polygon:");
    for (Pt pt : poly2) System.out.println(pt);
    // Prints:
    // Second polygon:
    // (4.0,4.0)
    // (4.0,0.0)
    // (0.0,0.0)

  }
}
