/**
 * This file contain an implementation of the maximum sliding window problem. This code has been
 * tested against the judge data on:
 *
 * <p>https://leetcode.com/problems/sliding-window-maximum/description/
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.other;

import java.util.ArrayDeque;
import java.util.Deque;

public class SlidingWindowMaximum {

  int[] values;
  public int N, lo, hi;

  Deque<Integer> deque = new ArrayDeque<>();

  public SlidingWindowMaximum(int[] values) {
    if (values == null) throw new IllegalArgumentException();
    this.values = values;
    N = values.length;
  }

  // Advances the front of the window by one unit
  public void advance() {

    // Remove all the worse values in the back of the deque
    while (!deque.isEmpty() && values[deque.peekLast()] < values[hi])
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
    while (!deque.isEmpty() && deque.peekFirst() < lo) deque.removeFirst();
  }

  // Query the current maximum value in the window
  public int getMax() {
    if (lo >= hi) throw new IllegalStateException("Make sure lo < hi");
    return values[deque.peekFirst()];
  }
}
