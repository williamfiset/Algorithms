
public class InterpolationSearch {

  // nums - an ordered list containing uniformly distributed values
  // val  - the value we're looking for
  static int interpolationSearch(int[] nums, int val) {
    int lo = 0, mid = 0, hi = nums.length - 1;
    while (nums[lo] <= val && nums[hi] >= val) {
      mid = lo + ((val - nums[lo]) * (hi - lo)) / (nums[hi] - nums[lo]);
      if (nums[mid] < val) {
        lo = mid + 1;
      } else if (nums[mid] > val) {
        hi = mid - 1;
      } else return mid;
    }
    if (nums[lo] == val)
      return lo;
    return -1;
  }
  
}