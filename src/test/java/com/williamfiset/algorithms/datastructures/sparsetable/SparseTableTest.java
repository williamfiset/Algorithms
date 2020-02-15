package com.williamfiset.algorithms.datastructures.sparsetable;

import static com.google.common.truth.Truth.assertThat;

import com.williamfiset.algorithms.datastructures.sparsetable.SparseTable;
import java.util.*;
import org.junit.*;

public class SparseTableTest {

  private void queryResultTest(int[] values, int l, int r, int actual, SparseTable.Operation op) {
    if (op == SparseTable.Operation.MIN) {
      minQuery(values, l, r, actual);
    } else if (op == SparseTable.Operation.MAX) {
      maxQuery(values, l, r, actual);
    } else if (op == SparseTable.Operation.SUM) {
      sumQuery(values, l, r, actual);
    }
  }

  private void minQuery(int[] values, int l, int r, int actual) {
    int m = Integer.MAX_VALUE;
    for (int i = l; i <= r; i++)
      m = Math.min(m, values[i]);
    assertThat(m).isEqualTo(actual);
  }

  private void maxQuery(int[] values, int l, int r, int actual) {
    int m = Integer.MIN_VALUE;
    for (int i = l; i <= r; i++)
      m = Math.max(m, values[i]);
    assertThat(m).isEqualTo(actual);
  }

  private void sumQuery(int[] values, int l, int r, int actual) {
    int m = 0;
    for (int i = l; i <= r; i++)
      m += values[i];
    assertThat(m).isEqualTo(actual);
  }

  private void testAllOperations(int[] values) {
    SparseTable min_st = new SparseTable(values, SparseTable.Operation.MIN);
    SparseTable max_st = new SparseTable(values, SparseTable.Operation.MAX);
    SparseTable sum_st = new SparseTable(values, SparseTable.Operation.SUM);

    for (int i = 0; i < values.length; i++) {
      for (int j = i; j < values.length; j++) {
        queryResultTest(values, i, j, min_st.query(i,j), SparseTable.Operation.MIN);
        queryResultTest(values, i, j, max_st.query(i,j), SparseTable.Operation.MAX);
        queryResultTest(values, i, j, sum_st.query(i,j), SparseTable.Operation.SUM);
      }
    }
  }

  @Test
  public void simple() {
    int[] values = {0, 1, -2, 3, -4, 5, -6, 7, -8, 9};
    testAllOperations(values);
  }

  @Test
  public void randomArrayTests() {
    for (int i = 1; i < 100; i++) {
      int[] values = genRandArray(i, -1000, 1000);
      testAllOperations(values);
    }
  }

  private static int[] genRandArray(int n, int lo, int hi) {
    return new Random().ints(n, lo, hi).toArray();
  }

}
