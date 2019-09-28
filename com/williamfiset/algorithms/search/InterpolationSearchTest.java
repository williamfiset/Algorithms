package com.williamfiset.algorithms.search;

import org.junit.Test;

import static org.junit.Assert.*;

public class InterpolationSearchTest {

    @Test
    public void findAtFirst() {
        int nums[] = {1, 3, 5, 100};
        int val = 1;

        assertEquals(0, new InterpolationSearch().find(nums, val));
    }

    @Test
    public void findKeyAtLast() {
        int nums[] = {1, 3, 5, 100};
        int val = 100;

        assertEquals(2, new InterpolationSearch().find(nums, val));
    }
	
    @Test
    public void findInNums() {
        int nums[] = {1, 3, 5, 100};
        int val = 5;

        assertEquals(1, new InterpolationSearch().find(nums, val));

    }
	//Array searches are not sorted
    @Test
    public void nonSortedNums() {
        int nums[] = {3, 1, 5, 100};
        int val = 1;

        assertEquals(-1, new InterpolationSearch().find(nums, val));
    }
    @Test
    public void emptyNums() {
        int nums[] = {};
        int val = 4;

        assertEquals(-1, new InterpolationSearch().find(nums, val));
    }

    @Test
    public void nullNums() {
        int nums[] = null;
        int val = 4;

        assertEquals(-1, new InterpolationSearch().find(nums, val));
    }
    @Test
    public void findKeyIsnotInNums() {
        int nums[] = {1, 3, 5, 100};
        int val = 50;

        assertEquals(-1, new InterpolationSearch().find(nums, val));
    }
}
