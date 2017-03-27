/**
 *
 * Ternary search is similar to binary search except that it 
 * works on a function which decreases and then increases. 
 * This implementation of ternary search returns the input value
 * corresponding with the minimum output value of the 
 * function you're searching on.
 * 
 * Time Complexity: O(log(high - low)). 
 *
 * NOTE: You can also work with a function which increases
 * and then decreases, simply negate your function :)
 *
 * @author Micah Stairs
 *
 **/

public class TernarySearch {

  // Define a very small epsilon value to compare double values
  static final double EPS = 0.000000001;

  // Define your own function on whatever you're attempting to ternary
  // search. Remember that your function must be a continuous unimodal
  // function, this means a function which decreases then increases (U shape)
  static double f(double x) { return x*x + 3*x + 5; }

  static double ternarySearch(double low, double high) {
    Double best = null;
    while (true) {
      double mid1 = (2*low + high) / 3, mid2 = (low + 2*high) / 3;
      double res1 = f(mid1), res2 = f(mid2);
      if (res1 > res2) low = mid1;
      else high = mid2;
      if (best != null && Math.abs(best - mid1) < EPS) break;
      best = mid1;
    }
    return best;
  }

  public static void main(String[] args) {

    // Search for the lowest point on the function x^2 + 3x + 5 
    // using a ternary search on the interval [-100, +100]
    double root = ternarySearch(-100.0, +100.0);
    System.out.printf("%.4f\n", root);

  }

}
