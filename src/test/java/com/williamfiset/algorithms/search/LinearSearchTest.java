package com.williamfiset.algorithms.search;

import static org.junit.Assert.*;

import org.junit.Test;

public class LinearSearchTest {
	
	//Testing for the linear search

	int[] array = {100,34,2,78,1,53,17,3};
	LinearSearch ls = new LinearSearch(array);

	@Test
	public void testLinearSearch1(){
	  assertTrue(ls.linearSearch(17));
	}
	
	@Test
	public void testLinearSearch2(){
	  assertTrue(ls.linearSearch(1));
	}
	
	@Test
	public void testLinearSearch3(){
	  assertFalse(ls.linearSearch(33));
	}
	
	@Test
	public void testLinearSearch4(){
	  assertTrue(ls.linearSearch(100));
	}
	
	@Test
	public void testLinearSearch5(){
	  assertFalse(ls.linearSearch(7));
	}
	
}
