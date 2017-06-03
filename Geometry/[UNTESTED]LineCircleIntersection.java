/**
 *
 * General formula for line:
 * ax + by = c, a != 0
 * y = (c - ax)/b
 *
 * Time Complexity: O(1)
 * 
 * @author William Fiset, william.alexandre.fiset@gmail.com
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

    // Use quadratic formula X = -b +- sqrt(a^2 - 4ac)/2a to find the 
    // roots of the equation (if they exist).
      
    double D = B*B - 4*A*C;
    double x1,y1,x2,y2;

    // Line is tangent to circle
    if (abs(D) < EPS) {
      
      x1 = -B/(2*A);
      
      // Vertical tangent line has y coordinate equal to that of y coordinate of circle
      if (b == 0) y1 = y; 
      else y1 = (c - a*x1)/b;

      return new Point2D[]{ new Point2D.Double(x1,y1) };

    // No intersection point
    } else if (D < 0) {
      return new Point2D[]{};

    // Two unique intersection points
    } else {
    
      D = sqrt(D);
      
      x1 = (-B+D)/(2*A);
      y1 = (c - a*x1)/b;

      x2 = (-B-D)/(2*A);
      y2 = (c - a*x2)/b;

      return new Point2D[]{ new Point2D.Double(x1,y1),
                            new Point2D.Double(x2,y2) };
    }
  
  }

  public static void main(String[] args) {

    // Vertical Line on passing through (1,0)
    display(lineCircleIntersection(1,0,1, 0,0,1));
    
    // Horizontal line passing through (0,1)
    display(lineCircleIntersection(0,1,1, 0,0,1));

    // Vertical line passing through (-1,0)
    display(lineCircleIntersection(1,0,-1, 0,0,1));

    

  }

  private static void display(Point2D[] pts) {
    if (pts == null) System.out.println("null");
    else {
      if (pts.length == 0) System.out.println("[]");
      else for(Point2D p : pts) System.out.println(p);
    }
  }

}
















