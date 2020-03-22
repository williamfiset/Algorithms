/**
 * Min sparse table example
 *
 * <p>Download the code: <br>
 * $ git clone https://github.com/williamfiset/algorithms
 *
 * <p>Run: <br>
 * $ ./gradlew run -Palgorithm=datastructures.sparsetable.examples.MinSparseTable
 *
 * <p>Construction complexity: O(nlogn), query complexity: O(1)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.datastructures.sparsetable.examples;

// Sparse table for efficient minimum range queries in O(1) with O(nlogn) space
public class MinSparseTable {

  // Example usage:
  public static void main(String[] args) {
    // index values: 0, 1,  2, 3, 4,  5, 6
    long[] values = {1, 2, -3, 2, 4, -1, 5};
    MinSparseTable sparseTable = new MinSparseTable(values);

    System.out.println(sparseTable.queryMin(1, 5)); // prints -3
    System.out.println(sparseTable.queryMinIndex(1, 5)); // prints 2

    System.out.println(sparseTable.queryMin(3, 3)); // prints 2
    System.out.println(sparseTable.queryMinIndex(3, 3)); // prints 3

    System.out.println(sparseTable.queryMin(3, 6)); // prints -1
    System.out.println(sparseTable.queryMinIndex(3, 6)); // prints 5
  }

  // The number of elements in the original input array.
  private int n;

  // The maximum power of 2 needed. This value is floor(log2(n))
  private int P;

  // Fast log base 2 logarithm lookup table, 1 <= i <= n
  private int[] log2;

  // The sparse table values.
  private long[][] dp;

  // Index Table (IT) associated with the values in the sparse table. This table
  // is only useful when we want to query the index of the min (or max) element
  // in the range [l, r] rather than the value itself. The index table doesn’t
  // make sense for most other range query types like gcd or sum.
  private int[][] it;

  public MinSparseTable(long[] values) {
    n = values.length;
    P = (int) (Math.log(n) / Math.log(2));
    dp = new long[P + 1][n];
    it = new int[P + 1][n];

    for (int i = 0; i < n; i++) {
      dp[0][i] = values[i];
      it[0][i] = i;
    }

    log2 = new int[n + 1];
    for (int i = 2; i <= n; i++) {
      log2[i] = log2[i / 2] + 1;
    }

    // Build sparse table combining the values of the previous intervals.
    for (int p = 1; p <= P; p++) {
      for (int i = 0; i + (1 << p) <= n; i++) {
        long leftInterval = dp[p - 1][i];
        long rightInterval = dp[p - 1][i + (1 << (p - 1))];
        dp[p][i] = Math.min(leftInterval, rightInterval);

        // Propagate the index of the best value
        if (leftInterval <= rightInterval) {
          it[p][i] = it[p - 1][i];
        } else {
          it[p][i] = it[p - 1][i + (1 << (p - 1))];
        }
      }
    }
  }

  // Do a min query on the interval [l, r] in O(1).
  //
  // We can get O(1) query by finding the smallest power of 2 that fits within
  // the interval length which we'll call k. Then we can query the intervals
  // [l, l+k] and [r-k+1, r] (which likely overlap) and apply the function
  // again. Some functions (like min and max) don't care about overlapping
  // intervals so this trick works, but for a function like sum this would
  // return the wrong result since it is not an idempotent binary function
  // (aka an overlap friendly function).
  private long queryMin(int l, int r) {
    int length = r - l + 1;
    int p = log2[length];
    int k = 1 << p; // 2 to the power of p
    return Math.min(dp[p][l], dp[p][r - k + 1]);
  }

  // Returns the index of the minimum element in the range [l, r].
  public int queryMinIndex(int l, int r) {
    int length = r - l + 1;
    int p = log2[length];
    int k = 1 << p; // 2 to the power of p
    long leftInterval = dp[p][l];
    long rightInterval = dp[p][r - k + 1];
    if (leftInterval <= rightInterval) {
      return it[p][l];
    } else {
      return it[p][r - k + 1];
    }
  }
}
