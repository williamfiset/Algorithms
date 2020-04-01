package com.williamfiset.algorithms.datastructures.segmenttree;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SegmentTreeWithPointersTest {

  static final int LOOPS = 50;
  static final int TEST_SZ = 1000;
  static final int MIN_RAND_NUM = 0;
  static final int MAX_RAND_NUM = +2000;

  @Before
  public void setup() {}

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalSegmentTreeCreation1() {
    Node tree = new Node(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalSegmentTreeCreation2() {
    int size = -10;
    Node tree = new Node(size);
  }

  @Test
  public void testSumQuery() {

    int[] values = {1, 2, 3, 4, 5};
    Node tree = new Node(values);

    assertEquals(1, tree.sum(0, 1));
    assertEquals(2, tree.sum(1, 2));
    assertEquals(3, tree.sum(2, 3));
    assertEquals(4, tree.sum(3, 4));
    assertEquals(5, tree.sum(4, 5));
  }

  // Select a lower bound index for the Fenwick tree
  public static int lowBound(int N) {
    return (int) (Math.random() * N);
  }

  // Select an upper bound index for the Fenwick tree
  public static int highBound(int low, int N) {
    return Math.min(N, low + (int) (Math.random() * N));
  }

  public static long randValue() {
    return (long) (Math.random() * MAX_RAND_NUM * 2) + MIN_RAND_NUM;
  }
}
