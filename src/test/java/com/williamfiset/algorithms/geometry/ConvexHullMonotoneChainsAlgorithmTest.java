package com.williamfiset.algorithms.geometry;

import static com.google.common.truth.Truth.assertThat;

import com.google.common.collect.ImmutableList;
import java.awt.geom.*;
import org.junit.*;

public class ConvexHullMonotoneChainsAlgorithmTest {

  @Test
  public void testEmptyCase() {
    assertThat(ConvexHullMonotoneChainsAlgorithm.convexHull(new Point2D[0])).isEmpty();
  }

  @Test
  public void convexHullRedundantPoints() {
    Point2D[] pts = new Point2D[14];

    pts[0] = new Point2D.Double(0, 5); // In hull
    pts[1] = new Point2D.Double(-1, 1);
    pts[2] = new Point2D.Double(0, 1);
    pts[3] = new Point2D.Double(1, 1);
    pts[4] = new Point2D.Double(-5, 0); // In hull
    pts[5] = new Point2D.Double(-1, 0);
    pts[6] = new Point2D.Double(0, 0);
    pts[7] = new Point2D.Double(1, 0);
    pts[8] = new Point2D.Double(5, 0); // In hull
    pts[9] = new Point2D.Double(-1, -1);
    pts[10] = new Point2D.Double(0, -1);
    pts[11] = new Point2D.Double(1, -1);
    pts[12] = new Point2D.Double(0, -5); // In hull

    // Duplicate point on hull.
    pts[13] = new Point2D.Double(-5, 0);
    ImmutableList<Point2D> expected = ImmutableList.of(pts[4], pts[12], pts[0], pts[8]);
    Point2D[] hull = ConvexHullMonotoneChainsAlgorithm.convexHull(pts);
    assertThat(hull).asList().containsExactlyElementsIn(expected);
  }

  @Test
  public void uniquePointsOnHull() {
    Point2D[] pts = new Point2D[5];
    pts[0] = new Point2D.Double(5, 4);
    pts[1] = new Point2D.Double(0, 0);
    pts[2] = new Point2D.Double(5, 0);
    pts[3] = new Point2D.Double(4, 5);
    pts[4] = new Point2D.Double(0, 5);

    ImmutableList<Point2D> expected = ImmutableList.of(pts[1], pts[4], pts[3], pts[2], pts[0]);
    Point2D[] hull = ConvexHullMonotoneChainsAlgorithm.convexHull(pts);
    assertThat(hull).asList().containsExactlyElementsIn(expected);
  }

  @Test
  public void test3Points() {
    Point2D[] pts = new Point2D[3];
    pts[1] = new Point2D.Double(0, 0);
    pts[2] = new Point2D.Double(5, 0);
    pts[0] = new Point2D.Double(0, 5);

    ImmutableList<Point2D> expected = ImmutableList.of(pts[0], pts[1], pts[2]);
    Point2D[] hull = ConvexHullMonotoneChainsAlgorithm.convexHull(pts);
    assertThat(hull).asList().containsExactlyElementsIn(expected);
  }

  @Test
  public void test2Points() {
    Point2D[] pts = new Point2D[2];
    pts[1] = new Point2D.Double(0, 0);
    pts[0] = new Point2D.Double(0, 5);

    ImmutableList<Point2D> expected = ImmutableList.of(pts[0], pts[1]);
    Point2D[] hull = ConvexHullMonotoneChainsAlgorithm.convexHull(pts);
    assertThat(hull).asList().containsExactlyElementsIn(expected);
  }

  @Test
  public void test1Point() {
    Point2D[] pts = new Point2D[1];
    pts[0] = new Point2D.Double(0, 5);

    ImmutableList<Point2D> expected = ImmutableList.of(pts[0]);
    Point2D[] hull = ConvexHullMonotoneChainsAlgorithm.convexHull(pts);
    assertThat(hull).asList().containsExactlyElementsIn(expected);
  }

  @Test
  public void repeatedSinglePoint() {
    Point2D[] pts = new Point2D[5];
    pts[0] = new Point2D.Double(0, 5);
    pts[1] = pts[0];
    pts[2] = pts[0];
    pts[3] = pts[0];
    pts[4] = pts[0];

    ImmutableList<Point2D> expected = ImmutableList.of(pts[0]);
    Point2D[] hull = ConvexHullMonotoneChainsAlgorithm.convexHull(pts);
    assertThat(hull).asList().containsExactlyElementsIn(expected);
  }
}
