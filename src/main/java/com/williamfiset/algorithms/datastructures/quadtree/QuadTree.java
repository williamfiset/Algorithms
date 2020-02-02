/**
 * A QuadTree implementation with integer coordinates.
 *
 * <p>NOTE: THIS FILE IS STILL UNDER DEVELOPMENT!
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.datastructures.quadtree;

import static java.lang.Double.POSITIVE_INFINITY;

import java.util.*;

public class QuadTree {

  private static int NORTH_EAST = 1;
  private static int NORTH_WEST = 2;
  private static int SOUTH_EAST = 3;
  private static int SOUTH_WEST = 4;

  private static boolean isNorth(int dir) {
    return dir == NORTH_EAST || dir == NORTH_WEST;
  }

  class Pt {
    long x, y;

    public Pt(long xx, long yy) {
      y = yy;
      x = xx;
    }

    @Override
    public String toString() {
      return "(" + x + "," + y + ")";
    }
  }

  static class SortedPt implements Comparable<SortedPt> {
    Pt pt;
    double dist;

    public SortedPt(double dist, Pt pt) {
      this.dist = dist;
      this.pt = pt;
    }

    @Override
    public int compareTo(SortedPt other) {
      return Double.compare(dist, other.dist);
    }

    @Override
    public String toString() {
      return dist + " - " + pt;
    }
  }

  // Node that represents a regions with points inside this region.
  class Node {

    // Keeps track of how many points are currently
    // contained within this quad tree node.
    private int ptCount = 0;

    // Tracks the (x,y) coordinates of points within this quad tree node.
    private long[] X, Y;

    // Define four Quad Tree nodes to subdivide the region we're
    // considering into four parts: north west (nw), north east (ne),
    // south west(sw) and south east(se).
    private Node nw, ne, sw, se;

    // The region this node encompasses
    private Rect region;

    // Construct a quad tree for a particular region.
    public Node(Rect region) {
      if (region == null) throw new IllegalArgumentException("Illegal argument");
      this.region = region;
      X = new long[NUM_POINTS];
      Y = new long[NUM_POINTS];
    }

    // Try adding a point to the current region and if the
    // region is already full subdivide and recurse until
    // you are able to place the point inside a smaller region
    private boolean add(long x, long y) {

      // Point is not within this region.
      if (!region.contains(x, y)) return false;

      // The point is within this region and there is room for it.
      if (ptCount < NUM_POINTS) {

        X[ptCount] = x;
        Y[ptCount] = y;
        ptCount++;

        return true;

        // This region is full, so subdivide the region into four
        // quadrants and try adding the point to these new regions
      } else {

        // Find the center of this region at (cx, cy)
        long cx = (region.x1 + region.x2) / 2;
        long cy = (region.y1 + region.y2) / 2;

        // Lazily subdivide each of the regions into four parts
        // one by one as needed to save memory.

        if (sw == null) sw = new Node(new Rect(region.x1, region.y1, cx, cy));
        if (sw.add(x, y)) return true;

        if (nw == null) nw = new Node(new Rect(region.x1, cy, cx, region.y2));
        if (nw.add(x, y)) return true;

        if (ne == null) ne = new Node(new Rect(cx, cy, region.x2, region.y2));
        if (ne.add(x, y)) return true;

        if (se == null) se = new Node(new Rect(cx, region.y1, region.x2, cy));
        return se.add(x, y);
      }
    }

    // Count how many points are found within a certain rectangular region
    private int count(Rect area) {

      if (area == null || !region.intersects(area)) return 0;

      int count = 0;

      // The area we're considering fully contains
      // the region of this node, so simply add the
      // number of points within this region to the count
      if (area.contains(region)) {
        count = ptCount;

        // Our regions overlap, so some points in this
        // region may intersect with the area we're considering
      } else {
        for (int i = 0; i < ptCount; i++) if (area.contains(X[i], Y[i])) count++;
      }

      // Dig into each of the quadrants and count all points
      // which overlap with the area and sum their count
      if (nw != null) count += nw.count(area);
      if (ne != null) count += ne.count(area);
      if (sw != null) count += sw.count(area);
      if (se != null) count += se.count(area);

      return count;
    }

    private List<Pt> kNearestNeighbors(int k, long x, long y) {
      PriorityQueue<SortedPt> heap = new PriorityQueue<>(k, Collections.reverseOrder());
      knn(k, x, y, heap);

      List<Pt> neighbors = new ArrayList<>();
      for (SortedPt n : heap) neighbors.add(n.pt);
      return neighbors;
    }

    // Find the k-nearest neighbors.
    private void knn(int k, long x, long y, PriorityQueue<SortedPt> heap) {

      for (int i = 0; i < ptCount; i++) {
        long xx = X[i], yy = Y[i];

        // Get largest radius.
        double radius = heap.isEmpty() ? POSITIVE_INFINITY : heap.peek().dist;

        // Get distance from point to this point.
        double distance = Math.sqrt((xx - x) * (xx - x) + (yy - y) * (yy - y));

        // Add node to heap.
        if (heap.size() < k) {
          heap.add(new SortedPt(distance, new Pt(xx, yy)));
        } else if (distance < radius) {
          heap.poll();
          // System.out.println("POLLED: " + heap.poll());
          heap.add(new SortedPt(distance, new Pt(xx, yy)));
        }
      }

      int pointQuadrant = 0;

      // Dig to find the quadrant (x, y) belongs to.
      if (nw != null && region.contains(x, y)) {
        nw.knn(k, x, y, heap);
        pointQuadrant = NORTH_WEST;
      } else if (ne != null && region.contains(x, y)) {
        ne.knn(k, x, y, heap);
        pointQuadrant = NORTH_EAST;
      } else if (sw != null && region.contains(x, y)) {
        sw.knn(k, x, y, heap);
        pointQuadrant = SOUTH_WEST;
      } else if (se != null && region.contains(x, y)) { // Use else clause?
        se.knn(k, x, y, heap);
        pointQuadrant = SOUTH_EAST;
      }

      if (pointQuadrant == 0) {
        // System.out.println("UNDEFINED QUADRANT?");
        // return;
      }

      // Get largest radius.
      double radius = heap.isEmpty() ? POSITIVE_INFINITY : heap.peek().dist;

      // Find the center of this region at (cx, cy)
      long cx = (region.x1 + region.x2) / 2;
      long cy = (region.y1 + region.y2) / 2;

      // Compute the horizontal (dx) and vertical (dy) distance from the
      // point (x, y) to the nearest cell.
      long dx = Math.abs(x - cx);
      long dy = Math.abs(y - cy);

      boolean checkHorizontalCell = radius >= dx;
      boolean checkVerticalCell = radius >= dy;
      boolean checkDiagonalCell = checkHorizontalCell && checkVerticalCell;

      // TODO(williamfiset): Refactor.
      if (heap.size() == k) {

        if (isNorth(pointQuadrant)) {
          if (pointQuadrant == NORTH_WEST) {
            if (checkHorizontalCell) if (ne != null) ne.knn(k, x, y, heap);
            if (checkVerticalCell) if (sw != null) sw.knn(k, x, y, heap);
            if (checkDiagonalCell) if (se != null) se.knn(k, x, y, heap);
          } else {
            if (checkHorizontalCell) if (nw != null) nw.knn(k, x, y, heap);
            if (checkVerticalCell) if (se != null) se.knn(k, x, y, heap);
            if (checkDiagonalCell) if (nw != null) nw.knn(k, x, y, heap);
          }
        } else {
          if (pointQuadrant == SOUTH_WEST) {
            if (checkHorizontalCell) if (se != null) se.knn(k, x, y, heap);
            if (checkVerticalCell) if (nw != null) nw.knn(k, x, y, heap);
            if (checkDiagonalCell) if (ne != null) ne.knn(k, x, y, heap);
          } else {
            if (checkHorizontalCell) if (sw != null) sw.knn(k, x, y, heap);
            if (checkVerticalCell) if (ne != null) ne.knn(k, x, y, heap);
            if (checkDiagonalCell) if (nw != null) nw.knn(k, x, y, heap);
          }
        }

        // Still need to find k - heap.size() nodes!
      } else {

        // explore all quadrants ?
        // Do it lazy? Inspect return val after each call?

        for (int quadrant = 1; quadrant <= 4; quadrant++) {

          if (quadrant == pointQuadrant) continue;
          radius = heap.isEmpty() ? POSITIVE_INFINITY : heap.peek().dist;
          checkHorizontalCell = radius >= dx;
          checkVerticalCell = radius >= dy;
          checkDiagonalCell = checkHorizontalCell && checkVerticalCell;

          // No validation
          if (heap.size() != k) {
            if (isNorth(pointQuadrant)) {
              if (pointQuadrant == NORTH_WEST) {
                if (ne != null) ne.knn(k, x, y, heap);
                if (sw != null) sw.knn(k, x, y, heap);
                if (se != null) se.knn(k, x, y, heap);
              } else {
                if (nw != null) nw.knn(k, x, y, heap);
                if (se != null) se.knn(k, x, y, heap);
                if (nw != null) nw.knn(k, x, y, heap);
              }
            } else {
              if (pointQuadrant == SOUTH_WEST) {
                if (se != null) se.knn(k, x, y, heap);
                if (nw != null) nw.knn(k, x, y, heap);
                if (ne != null) ne.knn(k, x, y, heap);
              } else {
                if (sw != null) sw.knn(k, x, y, heap);
                if (ne != null) ne.knn(k, x, y, heap);
                if (nw != null) nw.knn(k, x, y, heap);
              }
            }

            // must intersect
          } else {

            if (isNorth(pointQuadrant)) {
              if (pointQuadrant == NORTH_WEST) {
                if (checkHorizontalCell) if (ne != null) ne.knn(k, x, y, heap);
                if (checkVerticalCell) if (sw != null) sw.knn(k, x, y, heap);
                if (checkDiagonalCell) if (se != null) se.knn(k, x, y, heap);
              } else {
                if (checkHorizontalCell) if (nw != null) nw.knn(k, x, y, heap);
                if (checkVerticalCell) if (se != null) se.knn(k, x, y, heap);
                if (checkDiagonalCell) if (nw != null) nw.knn(k, x, y, heap);
              }
            } else {
              if (pointQuadrant == SOUTH_WEST) {
                if (checkHorizontalCell) if (se != null) se.knn(k, x, y, heap);
                if (checkVerticalCell) if (nw != null) nw.knn(k, x, y, heap);
                if (checkDiagonalCell) if (ne != null) ne.knn(k, x, y, heap);
              } else {
                if (checkHorizontalCell) if (sw != null) sw.knn(k, x, y, heap);
                if (checkVerticalCell) if (ne != null) ne.knn(k, x, y, heap);
                if (checkDiagonalCell) if (nw != null) nw.knn(k, x, y, heap);
              }
            }
          }
        } // for
      } // if
    } // method
  } // node

  public static class Rect {

    long x1, y1, x2, y2;

    // Define a rectangle as a pair of points (x1, y1) in the bottom left corner
    // and (x2, y2) in the top right corner of the rectangle.
    public Rect(long x1, long y1, long x2, long y2) {
      if (x1 > x2 || y1 > y2) throw new IllegalArgumentException("Illegal rectangle coordinates");
      this.x1 = x1;
      this.y1 = y1;
      this.x2 = x2;
      this.y2 = y2;
    }

    // Check for an intersection between two rectangles. The easiest way to do this is to
    // check if the two rectangles do not intersect and negate the logic afterwards.
    public boolean intersects(Rect r) {
      return r != null && !(r.x2 < x1 || r.x1 > x2 || r.y1 > y2 || r.y2 < y1);
    }

    // Check if a point (x, y) is within this rectangle, this
    // includes the boundary of the rectangle.
    public boolean contains(long x, long y) {
      return (x1 <= x && x <= x2) && (y1 <= y && y <= y2);
    }

    // Check if another rectangle is strictly contained within this rectangle.
    public boolean contains(Rect r) {
      return r != null && contains(r.x1, r.y1) && contains(r.x2, r.y2);
    }
  }

  // This is the maximum number of points each quad tree node can
  // sustain before it has to subdivide into four more regions.
  // This variable can have a significant impact on performance.
  final int NUM_POINTS;

  public static final int DEFAULT_NUM_POINTS = 16;

  // Root node of the quad tree. Public for testing.
  public Node root;

  public QuadTree(Rect region) {
    this.NUM_POINTS = DEFAULT_NUM_POINTS;
    root = new Node(region);
  }

  public QuadTree(Rect region, int pointsPerNode) {
    this.NUM_POINTS = pointsPerNode;
    root = new Node(region);
  }

  public boolean add(long x, long y) {
    return root.add(x, y);
  }

  public int count(Rect region) {
    return root.count(region);
  }

  public List<Pt> kNearestNeighbors(int k, long x, long y) {
    return root.kNearestNeighbors(k, x, y);
  }

  public List<Pt> getPoints() {
    List<Pt> points = new ArrayList<>();
    getPoints(root, points);
    return points;
  }

  private void getPoints(Node node, List<Pt> points) {
    if (node == null) return;
    for (int i = 0; i < node.ptCount; i++) points.add(new Pt(node.X[i], node.Y[i]));
    getPoints(node.nw, points);
    getPoints(node.ne, points);
    getPoints(node.sw, points);
    getPoints(node.se, points);
  }
}
