package com.williamfiset.algorithms.search;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.williamfiset.algorithms.search.JumpSearch;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class JumpSearchingTest {
	
	JumpSearch js = new JumpSearch();
	
	int[] integers = {23,25,36,38,48,52,55,60};

	/**
	 * The testing is doing for:
	 * 1. Negative search
	 * 2. Less than smallest value in array
	 * 3. Greater than largest value in array
	 * 4. Successful searching
	 */
	@Test
	@Parameters({"-10,-1","20,-1","65,-1","52,5"})
	public void testJumpSearch(int search, int expectedResult) {
		
		assertEquals(expectedResult, js.jumpSearch(integers,search));
	}

	// Test when array is empty
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidJumpSearch() {
		
		int[] emptyIntegers = {};
		js.jumpSearch(emptyIntegers, -1);
	}
}
