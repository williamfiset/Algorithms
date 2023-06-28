package com.williamfiset.algorithms.geometry;

import static com.google.common.truth.Truth.assertThat;

import java.awt.geom.*;
import org.junit.jupiter.api.*;

public class MinimumCostConvexPolygonTriangulationTest {

  private static final double TOLERANCE = 1e-3;

  @Test
  public void MinimumCostConvexPolygonTriangulationBasicTest() {
    Point2D[] pts = new Point2D[5];

    pts[0] = new Point2D.Double(0, 0);
    pts[1] = new Point2D.Double(1, 0);
    pts[2] = new Point2D.Double(2, 1);
    pts[3] = new Point2D.Double(1, 2);
    pts[4] = new Point2D.Double(0, 2);

    double cost = MinimumCostConvexPolygonTriangulation.minimumCostTriangulation(pts);
    assertThat(cost).isWithin(TOLERANCE).of(15.3);
  }

  @Test
  public void MinimumCostConvexPolygonTriangulationInvalidTest() {
    Point2D[] pts = new Point2D[2];

    pts[0] = new Point2D.Double(0, 0);
    pts[1] = new Point2D.Double(1, 0);

    double cost = MinimumCostConvexPolygonTriangulation.minimumCostTriangulation(pts);
    assertThat(cost).isEqualTo(0);
  }

  @Test
  public void MinimumCostConvexPolygonTriangulationConvex() {
    Point2D[] pts = new Point2D[6];

    pts[0] = new Point2D.Double(0, 0);
    pts[1] = new Point2D.Double(4, 0);
    pts[2] = new Point2D.Double(4, 2);
    pts[3] = new Point2D.Double(1, 3);
    pts[4] = new Point2D.Double(0, 2);
    pts[5] = new Point2D.Double(0, 1);

    double cost = MinimumCostConvexPolygonTriangulation.minimumCostTriangulation(pts);
    assertThat(cost).isWithin(TOLERANCE).of(31.386);
  }
}
