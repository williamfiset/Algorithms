// See live demo:
// http://www.williamfiset.com/circlecircleintersection

// Let EPS (epsilon) be a small value
var EPS = 0.0000001;

// Let a point be a pair: (x, y)
function Point(x, y) {
  this.x = x;
  this.y = y;
}

// Define a circle centered at (x,y) with radius r
function Circle(x,y,r) {
  this.x = x;
  this.y = y;
  this.r = r;
}

// Due to double rounding precision the value passed into the Math.acos
// function may be outside its domain of [-1, +1] which would return
// the value NaN which we do not want.
function acossafe(x) {
  if (x >= +1.0) return 0;
  if (x <= -1.0) return Math.PI;
  return Math.acos(x);
}

// Rotates a point about a fixed point at some angle 'a'
function rotatePoint(fp, pt, a) {
  var x = pt.x - fp.x;
  var y = pt.y - fp.y;
  var xRot = x * Math.cos(a) + y * Math.sin(a);
  var yRot = y * Math.cos(a) - x * Math.sin(a);
  return new Point(fp.x+xRot,fp.y+yRot);
}

// Given two circles this method finds the intersection
// point(s) of the two circles (if any exists)
function circleCircleIntersectionPoints(c1, c2) {
  
  var r, R, d, dx, dy, cx, cy, Cx, Cy;
  
  if (c1.r < c2.r) {
    r  = c1.r;  R = c2.r;
    cx = c1.x; cy = c1.y;
    Cx = c2.x; Cy = c2.y;
  } else {
    r  = c2.r; R  = c1.r;
    Cx = c1.x; Cy = c1.y;
    cx = c2.x; cy = c2.y;
  }
  
  // Compute the vector <dx, dy>
  dx = cx - Cx;
  dy = cy - Cy;

  // Find the distance between two points.
  d = Math.sqrt( dx*dx + dy*dy );
  
  // There are an infinite number of solutions
  // Seems appropriate to also return null
  if (d < EPS && Math.abs(R-r) < EPS) return [];
  
  // No intersection (circles centered at the 
  // same place with different size)
  else if (d < EPS) return [];
  
  var x = (dx / d) * R + Cx;
  var y = (dy / d) * R + Cy;
  var P = new Point(x, y);
  
  // Single intersection (kissing circles)
  if (Math.abs((R+r)-d) < EPS || Math.abs(R-(r+d)) < EPS) return [P];
  
  // No intersection. Either the small circle contained within 
  // big circle or circles are simply disjoint.
  if ( (d+r) < R || (R+r < d) ) return [];
  
  var C = new Point(Cx, Cy);
  var angle = acossafe((r*r-d*d-R*R)/(-2.0*d*R));
  var pt1 = rotatePoint(C, P, +angle);
  var pt2 = rotatePoint(C, P, -angle);
  return [pt1, pt2];
  
}





