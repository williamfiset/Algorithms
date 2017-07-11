/**
* This file shows you how to cut a polygon by a specific line (infinite line).
* Both polygons resulting from the cut can be found by running the cut method twice.
*
* Time Complexity: O(n)
*
* @author Thomas Finn Lidbetter
**/
import java.util.*;

public class ConvexPolygonCut {

  // Small epsilon value
  static double EPS = 1e-9;

  // Custom point class
  static class PT {
    double x, y;
    public PT(double x, double y) {
      this.x = x;
      this.y = y;
    }
    @Override public String toString() {
      return "(" + x + "," + y + ")";
    }
  }

  // Cuts a polygon by a specified infinite line (represented as a finite 
  // line segment) and returns one part of the polygon. By swapping the
  // endpoints of the line you can find the other part of the polygon.
  public static PT[] cut(PT[] poly, PT p1, PT p2) {
    int n = poly.length;
    List<PT> res = new ArrayList<>();
    for (int i = 0, j = n - 1; i < n; j = i++) {
      int d1 = orientation(p1.x, p1.y, p2.x, p2.y, poly[j].x, poly[j].y);
      int d2 = orientation(p1.x, p1.y, p2.x, p2.y, poly[i].x, poly[i].y);
      if (d1 >= 0) res.add(poly[j]);
      if (d1 * d2 < 0) res.add(intersect(p1.x, p1.y, p2.x, p2.y, poly[j].x, poly[j].y, poly[i].x, poly[i].y));
    }
    return res.toArray(new PT[res.size()]);
  }
  
  private static int orientation(double ax, double ay, double bx, double by, double cx, double cy) {
    bx -= ax; by -= ay;
    cx -= ax; cy -= ay;
    double cross = bx * cy - by * cx;
    return cross < -EPS ? -1 : cross > EPS ? 1 : 0;
  }

  private static PT intersect(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
    double a1 = y2 - y1, a2 = y4 - y3, b1 = x1 - x2, b2 = x3 - x4;
    double c1 = -(x1 * y2 - x2 * y1), c2 = -(x3 * y4 - x4 * y3);
    double det = a1 * b2 - a2 * b1;
    double x = -(c1 * b2 - c2 * b1) / det, y = -(a1 * c2 - a2 * c1) / det;
    return new PT(x, y);
  }

  public static void main(String[] args) {
    
    int n = 6;
    PT[] polygon = new PT[n];
    polygon[0] = new PT(-1, 1);
    polygon[1] = new PT(-2, 0);
    polygon[2] = new PT(-1, -1);
    polygon[3] = new PT(1, -1);
    polygon[4] = new PT(2, 0);
    polygon[5] = new PT(1, 1);

    // Line segment from (0,4) to (0,-4) represents an infinite line on x = 0
    PT pt1 = new PT(0, +4);
    PT pt2 = new PT(0, -4);

    PT[] rightPolygon = cut(polygon, pt1, pt2);
    PT[] leftPolygon = cut(polygon, pt2, pt1);

    // Prints: [(1.0,1.0), (0.0,1.0), (-0.0,-1.0), (1.0,-1.0), (2.0,0.0)]
    System.out.println(Arrays.toString(leftPolygon));

    // Print:  [(-0.0,1.0), (-1.0,1.0), (-2.0,0.0), (-1.0,-1.0), (0.0,-1.0)]
    System.out.println(Arrays.toString(rightPolygon));

  }

}








