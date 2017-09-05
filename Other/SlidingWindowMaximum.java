/**
 * This file contain an implementation of the maximum sliding window problem.
 * This code has been tested against the judge data on:
 *
 * https://leetcode.com/problems/sliding-window-maximum/description/
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 **/

import java.util.Deque;
import java.util.ArrayDeque;

public class SlidingWindowMaximum {

  int[] values;
  int N, lo, hi;

  Deque<Integer> deque = new ArrayDeque<>();

  public SlidingWindowMaximum(int[] values) {
    if (values == null) throw new IllegalArgumentException();
    this.values = values;
    N = values.length;
  }

  // Advances the front of the window by one unit
  public void advance() {

    // Remove all the worse values in the back of the deque
    while(!deque.isEmpty() && values[deque.peekLast()] < values[hi])
      deque.removeLast(); // Change the '<' sign here ^^^ to '>' for minimum sliding window

    // Add the next index to the back of the deque
    deque.addLast(hi);

    // Increase the window size
    hi++;

  }

  // Retracks the back of the window by one unit
  public void shrink() {

    // Decrease window size by pushing it forward
    lo++;

    // Remove elements in the front of the queue whom are no longer
    // valid in the reduced window.
    while(!deque.isEmpty() && deque.peekFirst() < lo)
      deque.removeFirst();

  }

  // Query the current maximum value in the window
  public int getMax() {
    if (lo >= hi) throw new IllegalStateException("Make sure lo < hi");
    return values[deque.peekFirst()];
  }

  public static void main(String[] args) {
    MaxSlidingWindowTester.runTests();
  }

}

class MaxSlidingWindowTester {

  public static void runTests() {
    for(int sz = 1; sz <= 1000; sz++) {
      randomizedTest(sz);
    }
  }

  private static void fillRandom(int[] ar) {
    for(int i = 0; i < ar.length; i++) {
      if (Math.random() < 0.5) {
        ar[i] = (int) (Math.random() * +25);
      } else {
        ar[i] = (int) (Math.random() * -25);
      }
    }
  }

  public static void randomizedTest(int n) {

    double r = Math.max(0.1, Math.random());
    int[] ar = new int[n];
    fillRandom(ar);

    SlidingWindowMaximum window = new SlidingWindowMaximum(ar);
    int lo = 0, hi = 0;
    while(hi < n) {

      // increase hi
      if (Math.random() < r) {
        window.advance();
        hi++;

      // increase lo if we can
      } else {
        if (lo+1 < hi) {
          lo++;
          window.shrink();
        }
      }
      
      // Ignore invalid queries
      if (window.lo == window.hi) continue;
      
      // Manually find the window maximum
      int max = Integer.MIN_VALUE;
      for (int i = lo; i < hi; i++) 
        max = Math.max(max, ar[i]);
      
      // Output error if any
      if (max != window.getMax()) {
        System.err.println("Oops..");
        break;
      }

    }
    

  }
}













