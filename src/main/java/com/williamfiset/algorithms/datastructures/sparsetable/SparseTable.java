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


  // Supply an array of values and an associative binary function. The function
  // is usually min, max sum, gcd, etc... see pre-made ones below.
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

  public int query(int l, int r) {
    if (op == Operation.MIN) {
      return minQuery(l, r);
    } else if (op == Operation.MAX) {
      return maxQuery(l, r);
    }
    return sumQuery(l, r);
  }

  // Do sum query [l, r] in O(lg(n))
  public int sumQuery(int l, int r) {
    int sum = 0;
    for (int p = P; p >= 0; p--) {
      // System.out.println(l + " " + r);
      int rangeLength = r - l + 1;
      if ((1 << p) <= rangeLength) {
        sum += t[p][l];
        l += (1 << p);
      }
    }
    return sum;
  }

  // Do min query [l, r] in O(1)
  private int minQuery(int l, int r) {
    int p = log2[r - l + 1];
    return Math.min(t[p][l], t[p][r - (1 << p) + 1]);
  }

  // Do max query [l, r] in O(1)
  private int maxQuery(int l, int r) {
    int p = log2[r - l + 1];
    return Math.max(t[p][l], t[p][r - (1 << p) + 1]);
  }

  private void print(int[][] t) {
    for (int[] r : t) {
      System.out.println(java.util.Arrays.toString(r));
    }
    System.out.println();
  }

  public static void main(String[] args) {
    int[] v = {1,8,5,6,7,2,7,1,8,5,6,7,2,4,3};
    System.out.println(v.length);
    SparseTable st = new SparseTable(v, Operation.MIN);
  }

}

