/**
 *
 *
 * https://cp-algorithms.com/data_structures/sparse-table.html
 */
package com.williamfiset.algorithms.datastructures.sparsetable;

import java.util.function.BinaryOperator;

public class SparseTable {

  // The number of elements in the original input array.
  private int n;

  // The maximum power of 2 needed. This value of floor(log2(n))
  private int P;
  
  // Fast log2(i) lookup table, 1 <= i <= n
  private int[] log2;

  // The sprase table.
  private int[][] t;

  // TODO(william): add GCD after more testing.
  public enum Operation {
    MIN,
    MAX,
    SUM
  };

  private Operation op;

  private BinaryOperator<Integer> sumFn = (a, b) -> a + b;
  private BinaryOperator<Integer> minFn = (a, b) -> Math.min(a, b);
  private BinaryOperator<Integer> maxFn = (a, b) -> Math.max(a, b);
  private BinaryOperator<Integer> gcdFn = (a, b) -> {
    int gcd = b;
    while (b != 0) {
      gcd = b;
      b = a % b;
      a = gcd;
    }
    return Math.abs(gcd);
  };

  public SparseTable(int[] v, Operation op) {
    this.op = op;
    init(v);
  }

  private void init(int[] v) {
    n = v.length;
    P = (int) (Math.log(n)/Math.log(2));
    t = new int[P+1][n];

    for (int i = 0; i < n; i++) {
      t[0][i] = v[i];
    }

    log2 = new int[n+1];
    for (int i = 2; i <= n; i++) {
      log2[i] = log2[i/2] + 1;
    }

    System.out.println("Log table: ");
    System.out.println(java.util.Arrays.toString(log2));

    // O(nlog(n))
    for (int p = 1; p <= P; p++) {
      for (int i = 0; i + (1 << p) <= n; i++) {
        if (op == Operation.SUM) {
          t[p][i] = sumFn.apply(t[p-1][i], t[p-1][i + (1 << (p-1))]);
        } else if (op == Operation.MIN) {
          t[p][i] = minFn.apply(t[p-1][i], t[p-1][i + (1 << (p-1))]);
        } else if (op == Operation.MAX) {
          t[p][i] = maxFn.apply(t[p-1][i], t[p-1][i + (1 << (p-1))]);
        }
      }
    }

    System.out.println();
    print(t);
  }

  // Queries [l, r] for the operation set on this Sparse table.
  public int query(int l, int r) {
    if (op == Operation.MIN) {
      return query(l, r, minFn);
    } else if (op == Operation.MAX) {
      return query(l, r, maxFn);
    }
    // TODO(william): add query for gcd
    return sumQuery(l, r);
  }

  // Do sum query [l, r] in O(log2(n)).
  // 
  // Perform a cascading query which shrinks the left endpoint while summing over all the intervals
  // which are powers of 2 between [l, r].
  // NOTE: You can  achieve a faster time complexity with less memory using a simple prefix array...
  // 
  // TODO(william): use longs?
  public int sumQuery(int l, int r) {
    int sum = 0;
    for (int p = P; p >= 0; p--) {
      int rangeLength = r - l + 1;
      if ((1 << p) <= rangeLength) {
        // System.out.printf("power = 2^%d = %d, l = %d, value = %d\n", p, 1<<p, l, t[p][l]);
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
  private int query(int l, int r, BinaryOperator<Integer> fn) {
    int len = r - l + 1;
    int p = log2[r - l + 1];
    return fn.apply(t[p][l], t[p][r - (1 << p) + 1]);
  }

  private static void print(int[][] t) {
    for (int i = 0; i < t.length; i++) {
      for (int j = 0; j < t[0].length; j++) {
        System.out.printf("%3d,", t[i][j]);
      }
      System.out.println();
    }
    System.out.println();
  }

  public static void main(String[] args) {
    int[] v = {1,2,3,4,5,6,7,8};
    SparseTable st = new SparseTable(v, Operation.SUM);
    System.out.println(st.query(1, 7));
    simpleTest();
  }

  private static void simpleTest() {
    int[] v = {1,2,3,4,5,6,7,8};
    SparseTable st = new SparseTable(v, Operation.SUM);
    for (int i = 0; i < v.length; i++) {
      for (int j = i; j < v.length; j++) {
        int trueSum = 0;
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








