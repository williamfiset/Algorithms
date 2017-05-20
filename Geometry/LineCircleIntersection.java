/**
 *
 * General formula for line:
 * ax + by = c, a != 0
 * y = (c - ax)/b
 *  
 * Use formula for circle and plug in solved value for y from line equation
 * (X-x)^2 + (Y-y)^2 = r^2
 * (X^2 - 2Xx + x^2) + (Y^2 - 2Yy + y^2) = r^2
 * (X^2 - 2Xx + x^2) + (Y^2 - 2Y(c-ax)/b + ((c-ax)/b)^2) = r^2
 * (X^2 - 2Xx + x^2) + (Y^2 - 2cY/b + 2axY/b + (c-ax)^2(1/b^2)) = r^2
 * (X^2 - 2Xx + x^2) + (Y^2 - 2cY/b + 2axY/b + (c-ax)(c-ax)(1/b^2)) = r^2
 * (X^2 - 2Xx + x^2) + (Y^2 - 2cY/b + 2axY/b + (c^2 -2acx + a^2x^2)(1/b^2) = r^2
 * (b^2X^2 - 2b^2Xx + b^2x^2) + b^2Y^2 - 2bcY + 2axY + c^2 - 2acx + a^2x^2 = b^2r^2
 * (b^2x^2 + a^2x^2) + (2axY + -2acx - 2b^2Xx) + (b^2X^2 + c^2 + b^2Y^2 - 2bcY - b^2r^2) = 0
 * (b^2 + a^2)(x^2) + (2aY + -2ac - 2b^2X)(x) + (b^2X^2 + c^2 + b^2Y^2 - 2bcY - b^2r^2) = 0
 *

 **/

import java.awt.geom.Point2D;
import static java.lang.Math.*;

public class LineCircleIntersection {

  private static final double EPS = 1e-9;
  
  // Given a line in the form ax + by = c (NOTE: this is not ax + by + c = 0)
  // Given a circle with a center at (x,y) of radius r
  // This method finds the intersection of this line and this circle
  public static Point2D[] lineCircleIntersection(double a, double b, double c, double x, double y, double r) {
    
    // (a^2 + b^2)x^2 + (2abY - 2ac + - 2b^2X)x + b^2X^2 + b^2Y^2 - 2bcY + c^2 - b^2r^2 = 0
    double A = a*a + b*b;
    double B = 2*a*b*y - 2*a*c - 2*b*b*x;
    double C = b*b*x*x + b*b*y*y - 2*b*c*y + c*c - b*b*r*r;

    System.out.printf("%.3f %.3f %.3f\n", a,b,c);
    System.out.printf("%.3f %.3f %.3f\n", A,B,C);

    // Use quadratic formula X = -b +- sqrt(a^2 - 4ac)/2a to find the 
    // roots of the equation (if they exist).
      
    double D = B*B - 4*A*C;

//    System.out.printf("%.3f\n", D);

    // Line is tangent to circle
    if (abs(D) < EPS) {
      double X1 = -B/(2*A);
      double Y1 = (c - a*X1)/b;
      return new Point2D[]{ new Point2D.Double(X1,Y1) };

    // No intersection point
    } else if (D < 0) {
      return new Point2D[]{};

    // Two unique intersection points
    } else 
    
      D = sqrt(D);
      
      double X1 = (-B+D)/(2*A);
      double Y1 = (c - a*X1)/b;

      double X2 = (-B-D)/(2*A);
      double Y2 = (c - a*X2)/b;

      return new Point2D[]{ new Point2D.Double(X1,Y1),
                            new Point2D.Double(X2,Y2) };
  }

  public static void main(String[] args) {

    
    // y = x
    // ax + by = c
    Point2D[] pts = lineCircleIntersection(-1,1,0,0,0,1);
    System.out.println(pts);
    if (pts != null) {
      System.out.println(pts.length);
      for(Point2D p : pts) System.out.println(p);
    }

  }

}
















