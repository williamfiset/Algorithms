/**
 * A compact array based segment tree implementation. This segment tree supports point updates and
 * range queries.
 *
 * @author Al.Cash & William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.datastructures.segmenttree;

public class CompactSegmentTree {

// Observation:
// The ST doesn't lend itself to being traverse top down easily,
// rather, bottom up is nicer in this compact representation.

// Two implementations:
// 1) top down
// 2) bottom up

  // public class Pair {
  //   // Index of the value in the original which derived the best value. The index doesn't
  //   // always make sense depending on the query type. for example the index makes sense for RMQ
  //   // but not for sum queries which aggregate values.
  //   int index;

  //   // The best value in the range.
  //   long value;
  //   public Pair(int index, long value) {
  //     this.index = index;
  //     this.value = value;
  //   }
  //   public String toString() {
  //     return "(index = " + index + ", " + value + ")";
  //   }
  // }

  // // The number of elements in the original values array.
  // private int N;

  // // Let UNIQUE be a value which does NOT and will NOT appear in the segment tree.
  // // This value is used as a replacement for null to avoid unboxing values.
  // //
  // // TODO(william): should probably replace the tree with Long[] instead of long[] to remove this...
  // private long UNIQUE = 8123572096793136074L;

  // // Segment tree range values. This array is 1 based so the first entry tree[0]
  // // is unused.
  // private long[] tree;

  // public CompactSegmentTree(int size) {
  //   tree = new long[2 * (N = size)];
  //   java.util.Arrays.fill(tree, UNIQUE);
  // }

  // public CompactSegmentTree(long[] values) {
  //   this(values.length);
  //   for (int i = 0; i < N; i++) modify(i, values[i]);
  // }

  // // This is the segment tree function we are using for queries.
  // // The function must be an associative function, meaning
  // // the following property must hold: f(f(a,b),c) = f(a,f(b,c)).
  // // Common associative functions used with segment trees
  // // include: min, max, sum, product, GCD, and etc...
  // private int function(int ai, int bi) {
  //   if (ai == -1 || tree[ai] == UNIQUE) return bi;
  //   else if (bi == -1 || tree[bi] == UNIQUE) return ai;

  //   // return a + b; // sum over a range
  //   // return (a > b) ? a : b; // maximum value over a range
  //   if (tree[ai] < tree[bi]) { // minimum value over a range
  //     return ai;
  //   } else {
  //     return bi;
  //   }
  //   // return a * b; // product over a range (watch out for overflow!)
  // }

  // // Adjust point i by a value, O(log(n))
  // public void modify(int i, long value) {
  //   // Setting tree[0] to the value and then using the function is a
  //   // small hack to see if updating the value makes an improvement.
  //   tree[0] = value;
  //   if (function(i + N, 0) == 0) {
  //     tree[i + N] = value;
  //   }
  //   // tree[i + N] = function(tree[i + N], value);
  //   for (i += N; i > 1; i >>= 1) {
  //     tree[i >> 1] = tree[function(i, i ^ 1)];
  //   }
  // }

  // // Query interval [l, r), O(log(n))
  // public Pair query(int l, int r) {
  //   long res = UNIQUE;
  //   int resIndex = -1;

  //   for (l += N, r += N; l < r; l >>= 1, r >>= 1) {
  //     int bestLeft = -1, bestRight = -1;
  //     if ((l & 1) != 0) {
  //       bestLeft = function(resIndex, l++);
  //     }
  //     if ((r & 1) != 0) {
  //       bestRight = function(resIndex, --r);
  //     }
  //     resIndex = Math.min(bestLeft, bestRight);
  //   }
  //   if (tree[resIndex] == UNIQUE) {
  //     throw new IllegalStateException("UNIQUE should not be the return value.");
  //   }
  //   // TODO(william): I don't think the result index is returning what you think it is returning.
  //   // Not convinced the leaf node indexes get propagated at all...
  //   // TODO(william): What about multiple matches, what then?
  //   return new Pair(resIndex, tree[resIndex]);
  // }

  private int N;

  // Let UNIQUE be a value which does NOT
  // and will not appear in the segment tree
  private long UNIQUE = 8123572096793136074L;

  // Segment tree values
  private long[] tree;

  public CompactSegmentTree(int size) {
    tree = new long[2 * (N = size)];
    java.util.Arrays.fill(tree, UNIQUE);
  }

  public CompactSegmentTree(long[] values) {
    this(values.length);
    // TODO(william): Implement smarter construction.
    for (int i = 0; i < N; i++) modify(i, values[i]);
  }

  // This is the segment tree function we are using for queries.
  // The function must be an associative function, meaning
  // the following property must hold: f(f(a,b),c) = f(a,f(b,c)).
  // Common associative functions used with segment trees
  // include: min, max, sum, product, GCD, and etc...
  private long function(long a, long b) {
    if (a == UNIQUE) return b;
    else if (b == UNIQUE) return a;

    // return a + b; // sum over a range
    // return (a > b) ? a : b; // maximum value over a range
    return (a < b) ? a : b; // minimum value over a range
    // return a * b; // product over a range (watch out for overflow!)
  }

  // Adjust point i by a value, O(log(n))
  public void modify(int i, long value) {
    tree[i + N] = function(tree[i + N], value);
    for (i += N; i > 1; i >>= 1) {
      tree[i >> 1] = function(tree[i], tree[i ^ 1]);
    }
  }

  // Query interval [l, r), O(log(n))
  public long query(int l, int r) {
    long res = UNIQUE;
    for (l += N, r += N; l < r; l >>= 1, r >>= 1) {
      if ((l & 1) != 0) res = function(res, tree[l++]);
      if ((r & 1) != 0) res = function(res, tree[--r]);
    }
    if (res == UNIQUE) {
      throw new IllegalStateException("UNIQUE should not be the return value.");
    }
    return res;
  }

  public static void main(String[] args) {
    long[] values = new long[]{3,0,8,9,8,2,5,3,7,1};
    CompactSegmentTree st = new CompactSegmentTree(values);
    System.out.println(java.util.Arrays.toString(st.tree));
  }
}
