/**
 *
 * "[Andrew, 1979] discovered an alternative to the Graham scan that uses a linear
 *  lexographic sort of the point set by the x and y-coordinates. This is an advantage
 *  if this ordering is already known for a set, which is sometimes the case. But
 *  even if sorting is required, this is a faster sort than the angular Graham-scan
 *  sort with its more complicated comparison function. The "Monotone Chain" algorithm
 *  computes the upper and lower hulls of a monotone chain of points, which is why we
 *  refer to it as the "Monotone Chain" algorithm. Like the Graham scan, it runs
 *  in O(nlog-n) time due to the sort time. After that, it only takes O(n) time to
 *  compute the hull." - Dan Sunday
 *
 *  This code is a modification of the monotone chain algorithm found on wikibooks.
 *  https://en.wikibooks.org/wiki/Algorithm_Implementation/Geometry/Convex_hull/Monotone_chain
 *
 * Time Complexity: O(nlogn)
 * 
 * @author A. M. Andrew, Dan Sunday, William Fiset
 **/

import java.util.*;
import java.awt.geom.*;

public class ConvexHullMonotoneChainAlgorithm {

  // Use the monotone chain algorithm to find the 
  // convex hull of a set of points in O(nlogn) time.
  static Point2D[] convexHull(Point2D[] pts) {
    
    int n = pts.length, k = 0, count = 0;
    Point2D[] arr = new Point2D[2 * n];
    Arrays.sort(pts, new PointComparator());
    
    for (int i = 0; i < n; ++i) {
      while (k >= 2 && cross(arr[k-2], arr[k-1], pts[i]) <= 0) k--;
      arr[k++] = pts[i];
    }

    for (int i = n - 2, t = k + 1; i >= 0; i--) {
      while (k >= t && cross(arr[k-2], arr[k-1], pts[i]) <= 0) k--;
      arr[k++] = pts[i];
    }

    // Filter duplicate points
    Set <Point2D> pt_set = new HashSet<>();
    Point2D[] hull = new Point2D[n];
    for (int i = 0; i < k; i++) {
      if (!pt_set.contains(arr[i])) {
        hull[count++] = arr[i];
        pt_set.add(arr[i]);
      }
    }
    
    return Arrays.copyOfRange(hull, 0, count);

  }

  static class PointComparator implements Comparator <Point2D> {
    static final double EPS = 0.0000001;
    public int compare(Point2D p1, Point2D p2) {
      if (Math.abs(p1.getX() - p2.getX()) < EPS) {
        if (Math.abs(p1.getY() - p2.getY()) < EPS) return 0;
        else if (p1.getY() > p2.getY()) return 1;
      } else if (p1.getX() > p2.getX()) return 1;
      return -1;
    }
  }
  
  static double cross(Point2D a, Point2D b, Point2D c) {
    return ((b.getX() - a.getX()) * (c.getY() - a.getY()) -
            (b.getY() - a.getY()) * (c.getX() - a.getX()));
  }


  public static void main(String[] args) {

    Point2D[] pts = new Point2D[13];

    pts[0] = new Point2D.Double(0,5);
    pts[1] = new Point2D.Double(-1,1);
    pts[2] = new Point2D.Double(0,1);
    pts[3] = new Point2D.Double(1,1);
    pts[4] = new Point2D.Double(-5,0);
    pts[5] = new Point2D.Double(-1,0);
    pts[6] = new Point2D.Double(0,0);
    pts[7] = new Point2D.Double(1,0);
    pts[8] = new Point2D.Double(5,0);
    pts[9] = new Point2D.Double(-1,-1);
    pts[10] = new Point2D.Double(0,-1);
    pts[11] = new Point2D.Double(1,-1);
    pts[12] = new Point2D.Double(0,-5);

    Point2D[] hull = convexHull(pts);

    // Print the points in the hull
    for (Point2D pt : hull) {
      System.out.println(pt);
    }
    
  }
  
}