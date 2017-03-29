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

import java.util.function.DoubleFunction;

public class TernarySearch {

  // Define a very small epsilon value to compare double values
  static final double EPS = 0.000000001;

  // Perform a ternary search on the interval low to high.
  // Remember that your function must be a continuous unimodal
  // function, this means a function which decreases then increases (U shape)
  static double ternarySearch(double low, double high, DoubleFunction <Double> function) {
    Double best = null;
    while (true) {
      double mid1 = (2*low + high) / 3, mid2 = (low + 2*high) / 3;
      double res1 = function.apply(mid1), res2 = function.apply(mid2);
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
    DoubleFunction <Double> function = (x) -> (x*x + 3*x + 5);
    double root = ternarySearch(-100.0, +100.0, function);
    System.out.printf("%.4f\n", root);

  }

}
