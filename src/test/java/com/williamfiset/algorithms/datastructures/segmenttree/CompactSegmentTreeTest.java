package com.williamfiset.algorithms.datastructures.segmenttree;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class CompactSegmentTreeTest {

  @Test
  public void testSumQueryBasic() {
    long[] values = {1, 2, 3, 4, 5};
    CompactSegmentTree st = new CompactSegmentTree(values);
    assertThat(st.query(0, 5)).isEqualTo(15);
    assertThat(st.query(0, 3)).isEqualTo(6);
    assertThat(st.query(2, 5)).isEqualTo(12);
  }

  @Test
  public void testSingleElement() {
    long[] values = {42};
    CompactSegmentTree st = new CompactSegmentTree(values);
    assertThat(st.query(0, 1)).isEqualTo(42);
  }

  @Test
  public void testTwoElements() {
    long[] values = {3, 7};
    CompactSegmentTree st = new CompactSegmentTree(values);
    assertThat(st.query(0, 2)).isEqualTo(10);
    assertThat(st.query(0, 1)).isEqualTo(3);
    assertThat(st.query(1, 2)).isEqualTo(7);
  }

  @Test
  public void testPointUpdate() {
    long[] values = {1, 1, 1, 1, 1, 1};
    CompactSegmentTree st = new CompactSegmentTree(values);
    assertThat(st.query(0, 6)).isEqualTo(6);

    // modify combines with existing value (sum), so adding 5 to index 2
    // changes value from 1 to 6
    st.modify(2, 5);
    assertThat(st.query(0, 6)).isEqualTo(11);
    assertThat(st.query(2, 3)).isEqualTo(6);
  }

  @Test
  public void testQuerySingleElementInRange() {
    long[] values = {10, 20, 30, 40, 50};
    CompactSegmentTree st = new CompactSegmentTree(values);
    for (int i = 0; i < values.length; i++) {
      assertThat(st.query(i, i + 1)).isEqualTo(values[i]);
    }
  }

  @Test
  public void testAllZeros() {
    long[] values = {0, 0, 0, 0};
    CompactSegmentTree st = new CompactSegmentTree(values);
    assertThat(st.query(0, 4)).isEqualTo(0);
    assertThat(st.query(1, 3)).isEqualTo(0);
  }

  @Test
  public void testNegativeValues() {
    long[] values = {-5, 3, -2, 7, -1};
    CompactSegmentTree st = new CompactSegmentTree(values);
    assertThat(st.query(0, 5)).isEqualTo(2);
    assertThat(st.query(0, 2)).isEqualTo(-2);
    assertThat(st.query(3, 5)).isEqualTo(6);
  }

  @Test
  public void testMultipleUpdates() {
    long[] values = {1, 2, 3};
    CompactSegmentTree st = new CompactSegmentTree(values);
    assertThat(st.query(0, 3)).isEqualTo(6);

    st.modify(0, 10);  // 1 + 10 = 11
    st.modify(1, 20);  // 2 + 20 = 22
    st.modify(2, 30);  // 3 + 30 = 33
    assertThat(st.query(0, 3)).isEqualTo(66);
  }

  @Test
  public void testSizeConstructor() {
    // Empty tree created with size constructor, then populated with modify
    CompactSegmentTree st = new CompactSegmentTree(4);
    st.modify(0, 5);
    st.modify(1, 10);
    st.modify(2, 15);
    st.modify(3, 20);
    assertThat(st.query(0, 4)).isEqualTo(50);
    assertThat(st.query(1, 3)).isEqualTo(25);
  }

  // Query with equal l and r is an empty range — should throw since
  // the result would be the UNIQUE sentinel value.
  @Test
  public void testEmptyRangeQueryThrows() {
    long[] values = {1, 2, 3};
    CompactSegmentTree st = new CompactSegmentTree(values);
    assertThrows(IllegalStateException.class, () -> st.query(1, 1));
  }

  @Test
  public void testLargerArray() {
    int n = 100;
    long[] values = new long[n];
    long total = 0;
    for (int i = 0; i < n; i++) {
      values[i] = i + 1;
      total += values[i];
    }
    CompactSegmentTree st = new CompactSegmentTree(values);
    assertThat(st.query(0, n)).isEqualTo(total);

    // Query first half: sum of 1..50 = 1275
    assertThat(st.query(0, 50)).isEqualTo(1275);
    // Query second half: sum of 51..100 = 3775
    assertThat(st.query(50, 100)).isEqualTo(3775);
  }
}
