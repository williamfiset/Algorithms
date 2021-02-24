package com.williamfiset.algorithms.datastructures.kdtree;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Before;
import org.junit.Test;

public class GeneralKDTreeTest {

    @Test(expected = Exception.class)
    public void testZeroDimensions() {
        new GeneralKDTree(0);
    }

    @Test(expected = Exception.class)
    public void testNegativeDimensions() {
        new GeneralKDTree(-5);
    }
    
    @Test
    public void testInsert() {
        GeneralKDTree kdTree = new GeneralKDTree(3);
        assertThat(kdTree.getRootPoint() == null).isTrue();
        int[] pointRoot = {3,4,3};
        int[] pointLeft = {1,7,6};
        int[] pointRight = {3,0,2};
        kdTree.insert(pointRoot);
        kdTree.insert(pointLeft);
        kdTree.insert(pointRight);
        assertThat(kdTree.getRootPoint() != null).isTrue();
        assertThat(kdTree.getRootPoint() == pointRoot).isTrue();
    }

    @Test(expected = Exception.class)
    public void testNullInsert() {
        GeneralKDTree kdTree = new GeneralKDTree(2);
        kdTree.insert(null);
    }

    @Test(expected = Exception.class)
    public void testBadInsert() {
        GeneralKDTree kdTree = new GeneralKDTree(2);
        kdTree.insert(new int[] {1, 2, 3});
    }

    @Test
    public void testSearch() {
        GeneralKDTree kdTree = new GeneralKDTree(4);
        assertThat(kdTree.search(new int[] {7,5,4,9})).isFalse();
        int[] point1 = {3,4,3,9};
        int[] point2 = {2,1,5,9};
        int[] point3 = {5,6,9,9};
        int[] point4 = {4,4,0,9};
        kdTree.insert(point1);
        kdTree.insert(point2);
        kdTree.insert(point3);
        kdTree.insert(point4);
        assertThat(kdTree.search(point1)).isTrue();
        assertThat(kdTree.search(point2)).isTrue();
        assertThat(kdTree.search(point3)).isTrue();
        assertThat(kdTree.search(point4)).isTrue();
        assertThat(kdTree.search(new int[] {7,5,4,9})).isFalse();
    }

    @Test(expected = Exception.class)
    public void testNullSearch() {
        GeneralKDTree kdTree = new GeneralKDTree(2);
        kdTree.search(null);
    }

    @Test(expected = Exception.class)
    public void testBadSearch() {
        GeneralKDTree kdTree = new GeneralKDTree(2);
        kdTree.search(new int[] {1, 2, 3});
    }
    
}
