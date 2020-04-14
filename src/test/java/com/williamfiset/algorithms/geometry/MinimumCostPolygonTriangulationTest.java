package com.williamfiset.algorithms.geometry;

import java.awt.geom.*;
import org.junit.*;

public class MinimumCostPolygonTriangulationTest {

  @Test
  public void MinimumCostPolygonTriangulationBasicTest() {
    Point2D[] pts = new Point2D[5];

    pts[0] = new Point2D.Double(0, 0);
    pts[1] = new Point2D.Double(1, 0);
    pts[2] = new Point2D.Double(2, 1);
    pts[3] = new Point2D.Double(1, 2);
    pts[4] = new Point2D.Double(0, 2);

    double cost = MinimumCostPolygonTriangulation.minimumCostTriangulation(pts);
    assert (Math.abs(cost - 15.3) < .001);
  }

  @Test
  public void MinimumCostPolygonTriangulationInvalidTest() {
    Point2D[] pts = new Point2D[2];

    pts[0] = new Point2D.Double(0, 0);
    pts[1] = new Point2D.Double(1, 0);

    double cost = MinimumCostPolygonTriangulation.minimumCostTriangulation(pts);
    assert (cost == 0);
  }

  @Test
  public void MinimumCostPolygonTriangulationConvex() {
    Point2D[] pts = new Point2D[7];

    pts[0] = new Point2D.Double(0, 0);
    pts[1] = new Point2D.Double(4, 0);
    pts[2] = new Point2D.Double(2, 1);
    pts[3] = new Point2D.Double(4, 2);
    pts[4] = new Point2D.Double(1, 3);
    pts[5] = new Point2D.Double(0, 2);
    pts[6] = new Point2D.Double(0, 1);

    double cost = MinimumCostPolygonTriangulation.minimumCostTriangulation(pts);
    assert (Math.abs(cost - 32.465) < .001);
  }
}
