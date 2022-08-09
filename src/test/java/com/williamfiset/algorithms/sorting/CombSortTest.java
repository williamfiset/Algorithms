package com.williamfiset.algorithms.sorting;

import static org.junit.Assert.*;
import org.junit.Test;
	
public class CombSortTest {
	
	// 5 testing condition/test case
	// ==============================
	// test all positive value
	// test all negative value
	// test combination of positive and negative value
	// test different length and content of result
	// test empty array
	
	CombSort comb = new CombSort();
	
	@Test
	public void testAllPositiveInteger (){
		
		int [] intArray = {10, 35, 57, 17, 83, 14, 91, 88};
		comb.sort(intArray);
		
		int [] expectedwords = {10, 14, 17, 35, 57, 83, 88, 91}; 
		int [] actualwords = intArray;
		
		//Array have same content and length, return true
		assertArrayEquals("Arrays are not equal!!!", expectedwords, actualwords);
	}
	
	@Test
	public void testAllNegativeInteger (){
		
		int [] intArray = {-16, -98, -44, -68, -71, -6, -7, -39, -100, -55};
		comb.sort(intArray);
		
		int [] expectedwords = {-100, -98, -71, -68, -55, -44, -39, -16, -7, -6}; 
		int [] actualwords = intArray;
		
		//Array have same content and length, return true
		assertArrayEquals("Arrays are not equal!!!", expectedwords, actualwords);
	}
	
	@Test
	public void testWithNegativeInteger(){
		
		int [] intArray = {15, 25, -45, -43, -13, 17, 0, -31, 23, -24};
		comb.sort(intArray);
		
		int [] expectedwords = {-45, -43, -31, -24, -13, 0, 15, 17, 23, 25}; 
		int [] actualwords = intArray;
		
		//Array have same content and length, return true
		assertArrayEquals("Arrays are not equal!!!", expectedwords, actualwords);
	}
	
	@Test
	public void testNotSameResult(){
		
		int [] intArray = {-40, -42, -32, 6, -8};
		comb.sort(intArray);
		
		int [] expectedwords = {-32, 8, -40}; 
		int [] actualwords = intArray;
		
		//Array have same content and length, return true
		assertNotSame("Arrays are not equal!!!", expectedwords, actualwords);
	}
	
	@Test
	public void testEmptyArray() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,() -> CombSort.combsort(null));
		String expectedMsg = "Empty array cannot be sorted !!!";
		assertEquals(exception.getMessage(), expectedMsg);
	}
}
