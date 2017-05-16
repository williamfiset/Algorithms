/**
 * This file shows you how to find the intersection of two circles.
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 **/

import static java.lang.Math.*;
import java.awt.geom.Point2D;

public class CircleCircleIntersection {

  private static final double EPS = 1e-6;

  // Due to double rounding precision the value passed into the acos
  // function may be outside its domain of [-1, +1] which would return
  // the value Double.NaN which we do not want.
  static double arccosSafe(double x) {
    if (x >= +1.0) return 0;
    if (x <= -1.0) return PI;
    return acos(x);
  }

  public static Point2D[] circleCircleIntersection(Point2D c1, double r1, Point2D c2, double r2) {
    
    // r is the smaller radius and R is bigger radius
    double r, R;
    
    // c is the center of the small circle
    // C is the center of the big circle
    Point2D c, C;
    
    // Determine which is the bigger/smaller circle
    if (r1 < r2) { r = r1; R = r2; c = c1; C = c2; } 
    else         { r = r2; R = r1; c = c2; C = c1; }
    
    double dist = c1.distance(c2);

    // There are an infinite number of solutions
    if (dist < EPS && abs(r-R) < EPS)
      throw new IllegalStateException("Infinite number of solutions (circles are equal)");

    // No intersection (circles are disjoint)
    if (r+R < dist) return new Point2D[]{};
    
    // No intersection (small circle contained within big circle)
    if (r+dist < R) return new Point2D[]{};
    
    // Let (cx, cy) be the center of the small circle
    // Let (Cx, Cy) be the center of the larger circle
    double cx = c.getX(); double Cx = C.getX();
    double cy = c.getY(); double Cy = C.getY();

    // Compute the vector from the big circle to the little circle
    double vx = cx - Cx;
    double vy = cy - Cy;

    // Scale the vector by R and offset the vector so that the head of the vector 
    // is positioned on the circumference of the big circle ready to be rotated
    double x = ( vx / dist) * R + Cx;
    double y = ( vy / dist) * R + Cy;
    Point2D point = new Point2D.Double(x, y);

    // Unique intersection point on circumference of both circles
    if ( abs(r+R - dist) < EPS || abs(R-(r+dist)) < EPS )
      return new Point2D[]{point};

    // Find the angle via cos law
    double angle = arccosSafe((r*r-dist*dist-R*R)/(-2*dist*R));

    // Two unique intersection points      
    Point2D pt1 = rotatePoint( C, point,  angle );
    Point2D pt2 = rotatePoint( C, point, -angle );

    return new Point2D[]{pt1, pt2};

  }

  // Rotate point 'pt' a certain number of radians clockwise 
  // relative to some fixed point 'fp'. Note that the angle
  // should be specified in radians, not degrees.
  public static Point2D rotatePoint(Point2D fp, Point2D pt, double angle) {
    
    double fpx = fp.getX();
    double fpy = fp.getY();
    double ptx = pt.getX();
    double pty = pt.getY();
    
    // Compute the vector <x, y> from the fixed point
    // to the point of rotation.
    double x = ptx - fpx;
    double y = pty - fpy;

    // Apply the clockwise rotation matrix to the vector <x, y>
    // |  cosθ sinθ ||x|   |  xcosθ + ysinθ |
    // | -sinθ cosθ ||y| = | -xsinθ + ycosθ |
    double xRotated = x*cos(angle) + y*sin(angle);
    double yRotated = y*cos(angle) - x*sin(angle);

    // The rotation matrix rotated the vector about the origin, so we 
    // need to offset it by the point (fpx, fpy) to get the right answer
    return new Point2D.Double(fpx + xRotated, fpy + yRotated);

  }

  public static void main(String[] args) {

    Point2D center1 = new Point2D.Double(3,-5);
    Point2D center2 = new Point2D.Double(2,-5);
    Point2D center3 = new Point2D.Double(6,-5);
    Point2D center4 = new Point2D.Double(3,-7);
    Point2D center5 = new Point2D.Double(3,-10);
    
    double radius1 = 2;
    double radius2 = 1;
    double radius3 = 1;
    double radius4 = 1;
    double radius5 = 0.5;

    Point2D[] pts = circleCircleIntersection(center1,radius1,center2,radius2);
    displayIntersectionPoints(pts);

    pts = circleCircleIntersection(center1,radius1,center3,radius3);
    displayIntersectionPoints(pts);

    pts = circleCircleIntersection(center1,radius1,center4,radius4);
    displayIntersectionPoints(pts);

    pts = circleCircleIntersection(center1,radius1,center5,radius5);
    displayIntersectionPoints(pts);

    Point2D center6 = new Point2D.Double(3,1);
    Point2D center7 = new Point2D.Double(4,1);

    double radius6 = 2.0;
    double radius7 = sqrt(2);

    pts = circleCircleIntersection(center6,radius6,center7,radius7);
    displayIntersectionPoints(pts);

    Point2D center8 = new Point2D.Double(9,1);
    Point2D center9 = new Point2D.Double(12,1);
    Point2D center10 = new Point2D.Double(9,1);

    double radius8 = 2;
    double radius9 = sqrt(1.1);
    double radius10 = 0.25;
    
    pts = circleCircleIntersection(center8,radius8,center9,radius9);
    displayIntersectionPoints(pts);

    pts = circleCircleIntersection(center8,radius8,center10,radius10);
    displayIntersectionPoints(pts);

  }

  private static void displayIntersectionPoints(Point2D[] pts) {
    System.out.println("Circle intersections: ");
    for (Point2D p: pts) System.out.println(p);
  }

}




