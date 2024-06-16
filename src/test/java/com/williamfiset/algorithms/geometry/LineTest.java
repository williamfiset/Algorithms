package com.williamfiset.algorithms.geometry;
import com.williamfiset.algorithms.CoverageTracker;
import org.junit.Test;

import java.awt.geom.Point2D;
import java.util.Objects;

import static com.williamfiset.algorithms.geometry.Line.intersection;

public class LineTest {
    @Test
    public void testCoverage() {
        CoverageTracker.setTotalBranches(10);
        testIntersectionSimple();
        testIntersectionSimple2();
        testIntersectionParallel();
        testIntersectionHorizontalL1();
        testIntersectionHorizontalL2();
        testIntersectionSkewed();
        CoverageTracker.writeCoverageToConsole();
    }

    @Test
    public void testIntersectionSimple() {
        Line l1 = new Line(0.0,-2.0,0.0,2.0);
        Line l2 = new Line(-2.0,0.0,2.0,0.0);
        assert(Objects.equals(intersection(l1, l2), new Point2D.Double(0.0, 0.0)));
    }

    @Test
    public void testIntersectionSimple2() {
        Line l2 = new Line(0.0,-2.0,0.0,2.0);
        Line l1 = new Line(-2.0,0.0,2.0,0.0);
        assert(Objects.equals(intersection(l1, l2), new Point2D.Double(0.0, 0.0)));
    }

    @Test
    public void testIntersectionParallel() {
        Line l1 = new Line(0.0,0.0,1.0,0.0);
        Line l2 = new Line(0.0,1.0,1.0,1.0);
        assert(Objects.equals(intersection(l1, l2), null));
    }

    @Test
    public void testIntersectionHorizontalL1() {
        Line l1 = new Line(0.0,0.0,1.0,0.0);
        Line l2 = new Line(0.0,0.0,1.0,1.0);
        assert(Objects.equals(intersection(l1, l2), new Point2D.Double(0.0, 0.0)));
    }

    @Test
    public void testIntersectionHorizontalL2() {
        Line l1 = new Line(0.0,0.0,1.0,1.0);
        Line l2 = new Line(0.0,0.0,1.0,0.0);
        assert(Objects.equals(intersection(l1, l2), new Point2D.Double(0.0, 0.0)));
    }

    @Test
    public void testIntersectionSkewed() {
        Line l1 = new Line(0.0,0.0,2.0,3.0);
        Line l2 = new Line(0.0,0.0,3.0,2.0);
        assert(Objects.equals(intersection(l1, l2), new Point2D.Double(0.0, 0.0)));
    }
}

