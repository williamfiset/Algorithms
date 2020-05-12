package com.williamfiset.algorithms.search;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

public class QuickSelectTest {

  @Test
  public void testQuickSelect() {
    int array[] = {1, 89, 2, 17, 42, 100};

    assertEquals(1, QuickSelect.findKthSmallest(array, 1));
    assertEquals(89, QuickSelect.findKthSmallest(array, 5));
    assertEquals(17, QuickSelect.findKthSmallest(array, 3));
  }
}
