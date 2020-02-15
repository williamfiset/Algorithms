/**
 * Implementation of a sprase table which is a data structure that can very quickly query a range on
 * a static array in O(1) for operations like min, max and gcd using O(n*logn) memory.
 *
 * <p>Main inspiration: https://cp-algorithms.com/data_structures/sparse-table.html
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.datastructures.sparsetable;

import java.util.function.BinaryOperator;

public class SparseTable {

  // The number of elements in the original input array.
  private int n;

  // The maximum power of 2 needed. This value is floor(log2(n))
  private int P;

  // Fast log base 2 logarithm lookup table, 1 <= i <= n
  private int[] log2;

  // The sprase table values.
  private long[][] t;

  // Index Table (IT) associated with the values in the sparse table.
  private int[][] it;

  // The various supported query operations on this sprase table.
  public enum Operation {
    MIN,
    MAX,
    SUM,
    GCD
  };

  private Operation op;

  // All functions must be associative, e.g: a * (b * c) = (a * b) * c for some operation '*'
  private BinaryOperator<Long> sumFn = (a, b) -> a + b;
  private BinaryOperator<Long> minFn = (a, b) -> Math.min(a, b);
  private BinaryOperator<Long> maxFn = (a, b) -> Math.max(a, b);
  private BinaryOperator<Long> gcdFn =
      (a, b) -> {
        long gcd = a;
        while (b != 0) {
          gcd = b;
          b = a % b;
          a = gcd;
        }
        return Math.abs(gcd);
      };

  public SparseTable(long[] values, Operation op) {
    this.op = op;
    init(values);
  }

  private void init(long[] v) {
    n = v.length;
    P = (int) (Math.log(n) / Math.log(2));
    t = new long[P + 1][n];
    it = new int[P + 1][n];

    for (int i = 0; i < n; i++) {
      t[0][i] = v[i];
      it[0][i] = i;
    }

    log2 = new int[n + 1];
    for (int i = 2; i <= n; i++) {
      log2[i] = log2[i / 2] + 1;
    }

    // Build sparse table combining the values of the previous intervals.
    for (int p = 1; p <= P; p++) {
      for (int i = 0; i + (1 << p) <= n; i++) {
        long leftInterval = t[p - 1][i], rightInterval = t[p - 1][i + (1 << (p - 1))];
        if (op == Operation.MIN) {
          t[p][i] = minFn.apply(leftInterval, rightInterval);
          // Propagate the index of the best value
          if (leftInterval <= rightInterval) {
            it[p][i] = it[p - 1][i];
          } else {
            it[p][i] = it[p - 1][i + (1 << (p - 1))];
          }
        } else if (op == Operation.MAX) {
          t[p][i] = maxFn.apply(leftInterval, rightInterval);
          // Propagate the index of the best value
          if (leftInterval >= rightInterval) {
            it[p][i] = it[p - 1][i];
          } else {
            it[p][i] = it[p - 1][i + (1 << (p - 1))];
          }
        } else if (op == Operation.SUM) {
          t[p][i] = sumFn.apply(leftInterval, rightInterval);
        } else if (op == Operation.GCD) {
          t[p][i] = gcdFn.apply(leftInterval, rightInterval);
        }
      }
    }
  }

  // Queries [l, r] for the operation set on this sparse table.
  public long query(int l, int r) {
    if (op == Operation.MIN) {
      return query(l, r, minFn);
    } else if (op == Operation.MAX) {
      return query(l, r, maxFn);
    } else if (op == Operation.GCD) {
      return query(l, r, gcdFn);
    }
    return sumQuery(l, r);
  }

  public int queryIndex(int l, int r) {
    if (op == Operation.MIN) {
      return minQueryIndex(l, r);
    } else if (op == Operation.MAX) {
      return maxQueryIndex(l, r);
    }
    throw new UnsupportedOperationException(
        "Operation type: " + op + " doesn't support index queries :/");
  }

  private int minQueryIndex(int l, int r) {
    int len = r - l + 1;
    int p = log2[r - l + 1];
    long leftInterval = t[p][l];
    long rightInterval = t[p][r - (1 << p) + 1];
    if (leftInterval <= rightInterval) {
      return it[p][l];
    } else {
      return it[p][r - (1 << p) + 1];
    }
  }

  private int maxQueryIndex(int l, int r) {
    int len = r - l + 1;
    int p = log2[r - l + 1];
    long leftInterval = t[p][l];
    long rightInterval = t[p][r - (1 << p) + 1];
    if (leftInterval >= rightInterval) {
      return it[p][l];
    } else {
      return it[p][r - (1 << p) + 1];
    }
  }

  // Do sum query [l, r] in O(log2(n)).
  //
  // Perform a cascading query which shrinks the left endpoint while summing over all the intervals
  // which are powers of 2 between [l, r].
  //
  // NOTE: You can  achieve a faster time complexity with less memory using a simple prefix array...
  // WARNING: This method can easily produces values that overflow.
  private long sumQuery(int l, int r) {
    long sum = 0;
    for (int p = P; p >= 0; p--) {
      int rangeLength = r - l + 1;
      if ((1 << p) <= rangeLength) {
        sum += t[p][l];
        l += (1 << p);
      }
    }
    return sum;
  }

  // Do either a min, max or gcd query on the interval [l, r] in O(1).
  //
  // We can get O(1) query by finding the smallest power of 2 that fits within the interval length
  // which we'll call k. Then we can query the intervals [l, l+k] and [r-k+1, r] (which likely
  // overlap) and apply the function again. Some functions (like min and max) don't care about
  // overlapping intervals so this trick works, but for a function like sum this would return the
  // wrong result.
  private long query(int l, int r, BinaryOperator<Long> fn) {
    int len = r - l + 1;
    int p = log2[r - l + 1];
    return fn.apply(t[p][l], t[p][r - (1 << p) + 1]);
  }

  public static void main(String[] args) {
    long[] v = {2, -3, 4, 1, 0, -1, 5, 6};
    SparseTable st = new SparseTable(v, Operation.MIN);
    System.out.println(st.query(2, 7));
    System.out.println(st.queryIndex(2, 7));

    simpleTest();
    simpleMinQueryTest();
  }

  private static void simpleMinQueryTest() {
    long[] v = {2, -3, 4, 1, 0, -1, 5, 6};
    SparseTable st = new SparseTable(v, Operation.MIN);
    for (int i = 0; i < v.length; i++) {
      for (int j = i; j < v.length; j++) {
        long m = Long.MAX_VALUE;
        for (int k = i; k <= j; k++) {
          m = Math.min(m, v[k]);
        }
        if (st.query(i, j) != m) {
          System.out.println("Sparse table value incorrect.");
        }
        if (v[st.queryIndex(i, j)] != m) {
          System.out.println("SparseTable index incorrect. Got index: " + st.queryIndex(i, j));
        }
      }
    }
  }

  private static void simpleTest() {
    long[] v = {2, -3, 4, 1, 0, -1, 5, 6};
    SparseTable st = new SparseTable(v, Operation.SUM);
    for (int i = 0; i < v.length; i++) {
      for (int j = i; j < v.length; j++) {
        long trueSum = 0;
        for (int k = i; k <= j; k++) {
          trueSum += v[k];
        }
        if (st.query(i, j) != trueSum) {
          System.out.printf("Ooopse, got %d instead of %d!\n", st.query(i, j), trueSum);
        }
      }
    }
  }
}
