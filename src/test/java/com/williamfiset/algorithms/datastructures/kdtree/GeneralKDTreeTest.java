package com.williamfiset.algorithms.datastructures.kdtree;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Before;
import org.junit.Test;

public class GeneralKDTreeTest {

    @Test(expected = Exception.class)
    public void testZeroDimensions() {
        new GeneralKDTree<>(0);
    }

    @Test(expected = Exception.class)
    public void testNegativeDimensions() {
        new GeneralKDTree<>(-5);
    }
    
    @Test
    public void testInsert() {
        GeneralKDTree<Integer> kdTree = new GeneralKDTree<Integer>(3);
        assertThat(kdTree.getRootPoint() == null).isTrue();
        Integer[] pointRoot = {3,4,3};
        Integer[] pointLeft = {1,7,6};
        Integer[] pointRight = {3,0,2};
        kdTree.insert(pointRoot);
        kdTree.insert(pointLeft);
        kdTree.insert(pointRight);
        assertThat(kdTree.getRootPoint() != null).isTrue();
        assertThat(kdTree.getRootPoint() == pointRoot).isTrue();
    }

    @Test(expected = Exception.class)
    public void testNullInsert() {
        GeneralKDTree<Integer> kdTree = new GeneralKDTree<Integer>(2);
        kdTree.insert(null);
    }

    @Test(expected = Exception.class)
    public void testBadInsert() {
        GeneralKDTree<Integer> kdTree = new GeneralKDTree<Integer>(2);
        kdTree.insert(new Integer[] {1, 2, 3});
    }

    @Test
    public void testSearch() {
        GeneralKDTree<Integer> kdTree = new GeneralKDTree<Integer>(4);
        assertThat(kdTree.search(new Integer[] {7,5,4,9})).isFalse();
        Integer[] point1 = {3,4,3,9};
        Integer[] point2 = {2,1,5,9};
        Integer[] point3 = {5,6,9,9};
        Integer[] point4 = {4,4,0,9};
        kdTree.insert(point1);
        kdTree.insert(point2);
        kdTree.insert(point3);
        kdTree.insert(point4);
        assertThat(kdTree.search(point1)).isTrue();
        assertThat(kdTree.search(point2)).isTrue();
        assertThat(kdTree.search(point3)).isTrue();
        assertThat(kdTree.search(point4)).isTrue();
        assertThat(kdTree.search(new Integer[] {7,5,4,9})).isFalse();
    }

    @Test(expected = Exception.class)
    public void testNullSearch() {
        GeneralKDTree<Integer> kdTree = new GeneralKDTree<Integer>(2);
        kdTree.search(null);
    }

    @Test(expected = Exception.class)
    public void testBadSearch() {
        GeneralKDTree<Integer> kdTree = new GeneralKDTree<Integer>(2);
        kdTree.search(new Integer[] {1, 2, 3});
    }
    
}
