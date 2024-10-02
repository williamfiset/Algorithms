/**
 * An implementation of interpolation search
 *
 * <p>Time Complexity: O(log(log(n))) if data is uniform O(n) in worst case
 */
package com.williamfiset.algorithms.search;

public class InterpolationSearch {

  /**
   * Searches for the given value in an ordered list containing uniformly distributed values using
   * interpolation search.
   *
   * @param nums - an ordered list containing uniformly distributed values.
   * @param val - the value we're looking for in 'nums'
   * @return the index of the value if it is found, otherwise -1.
   */
  public static int interpolationSearch(int[] nums, int val) {
    int lo = 0, hi = nums.length - 1;
    while (lo <= hi && val >= nums[lo] && val <= nums[hi]) {
      // Interpolate the index of the mid-point element to be closer to our target value
      int mid = lo + ((val - nums[lo]) * (hi - lo)) / (nums[hi] - nums[lo]);
      if (nums[mid] == val) {
        return mid;
      }
      if (nums[mid] < val) {
        lo = mid + 1;
      } else {
        hi = mid - 1;
      }
    }
    return -1;
  }

  public static void main(String[] args) {

    int[] values = {10, 20, 25, 35, 50, 70, 85, 100, 110, 120, 125};

    // Since 25 exists in the values array the interpolation search
    // returns that it has found 25 at the index 2
    System.out.println(interpolationSearch(values, 25));

    // 111 does not exist so we get -1 as an index value
    System.out.println(interpolationSearch(values, 111));
  }
}
