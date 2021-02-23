package com.williamfiset.algorithms.geometry;

import org.junit.*;

import static com.google.common.truth.Truth.assertThat;

public class LineSegmentLineSegmentIntersectionTest {

    /**
     * Test when both line segments are the same single point
     */
    @Test
    public void testSinglePoint() {
        LineSegmentLineSegmentIntersection.Pt p = new LineSegmentLineSegmentIntersection.Pt(0, 0);
        LineSegmentLineSegmentIntersection.Pt[] res = LineSegmentLineSegmentIntersection.lineSegmentLineSegmentIntersection(p, p, p, p);
        assertThat(res[0]).isEqualTo(p);
    }

    /**
     * Test when the line segments don't intersect
     */
    @Test
    public void testNoIntersection() {
        LineSegmentLineSegmentIntersection.Pt p1 = new LineSegmentLineSegmentIntersection.Pt(0, 0);
        LineSegmentLineSegmentIntersection.Pt p2 = new LineSegmentLineSegmentIntersection.Pt(1, 1);
        LineSegmentLineSegmentIntersection.Pt p3 = new LineSegmentLineSegmentIntersection.Pt(0, 1);
        LineSegmentLineSegmentIntersection.Pt p4 = new LineSegmentLineSegmentIntersection.Pt(1, 2);
        LineSegmentLineSegmentIntersection.Pt[] res = LineSegmentLineSegmentIntersection.lineSegmentLineSegmentIntersection(p1,p2,p3,p4);
        assertThat(res).isEmpty();
    }

    /**
     * Test when one single point intersects line segment
     */
    @Test
    public void testOnePointOneSegment() {
        LineSegmentLineSegmentIntersection.Pt p1 = new LineSegmentLineSegmentIntersection.Pt(0.5, 0.5);
        LineSegmentLineSegmentIntersection.Pt p2 = new LineSegmentLineSegmentIntersection.Pt(0, 0);
        LineSegmentLineSegmentIntersection.Pt p3 = new LineSegmentLineSegmentIntersection.Pt(1, 1);
        LineSegmentLineSegmentIntersection.Pt[] res = LineSegmentLineSegmentIntersection.lineSegmentLineSegmentIntersection(p1,p1,p2,p3);
        assertThat(res[0]).isEqualTo(p1);
    }

    /**
     * Test when the two line segments are intersecting each other
     */
    @Test
    public void testTwoLineSegments() {
        LineSegmentLineSegmentIntersection.Pt p1 = new LineSegmentLineSegmentIntersection.Pt(0, 0);
        LineSegmentLineSegmentIntersection.Pt p2 = new LineSegmentLineSegmentIntersection.Pt(1, 1);
        LineSegmentLineSegmentIntersection.Pt p3 = new LineSegmentLineSegmentIntersection.Pt(1, 0);
        LineSegmentLineSegmentIntersection.Pt p4 = new LineSegmentLineSegmentIntersection.Pt(0, 1);
        LineSegmentLineSegmentIntersection.Pt expected = new LineSegmentLineSegmentIntersection.Pt(0.5,0.5);
        LineSegmentLineSegmentIntersection.Pt[] res = LineSegmentLineSegmentIntersection.lineSegmentLineSegmentIntersection(p1,p2,p3,p4);
        assertThat(res[0].equals(expected)).isEqualTo(true);
    }
}
