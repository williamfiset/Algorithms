package com.williamfiset.algorithms.search;

import static org.junit.Assert.*;

import org.junit.Test;

public class FibonacciSearchTest {

	@Test
	  public void testCoverage1() {
	    int[] arr = {0, 1, 2, 3, 4, 5};
	    int n = 5;
	    int x = 2;
	    int index = FibonacciSearch.fibMonaccianSearch(arr, x, n);
	    assertEquals(index, 2);
	  }

	  @Test
	  public void testCoverage2() {
	    int[] arr = {0, 1, 2, 3, 4, 5};
	    int n = 5;
	    int x = 4;
	    int index = FibonacciSearch.fibMonaccianSearch(arr, x, n);
	    assertEquals(index, 4);
	  }

	  @Test
	  public void testCoverage3() {
	    int[] arr = {0, 1, 2, 3, 4, 5};
	    int n = 5;
	    int x = -2;
	    int index = FibonacciSearch.fibMonaccianSearch(arr, x, n);
	    assertEquals(index, -1);
	  }

	  @Test
	  public void testCoverage4() {
	    int[] arr = {0, 1, 2, 3, 4, 5};
	    int n = 5;
	    int x = 8;
	    int index = FibonacciSearch.fibMonaccianSearch(arr, x, n);
	    assertEquals(index, -1);
	  }

}
