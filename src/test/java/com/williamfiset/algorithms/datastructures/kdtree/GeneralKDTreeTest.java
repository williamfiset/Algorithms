package com.williamfiset.algorithms.datastructures.kdtree;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Before;
import org.junit.Test;

public class GeneralKDTreeTest {

    @Test
    public void testInsert() {
        GeneralKDTree kdTree = new GeneralKDTree(3);
        int[] point1 = {3,4,3};
        int[] point2 = {2,1,5};
        int[] point3 = {5,6,9};
        int[] point4 = {4,4,0};
        kdTree.insert(point1);
        kdTree.insert(point2);
        kdTree.insert(point3);
        kdTree.insert(point4);
        assertThat(kdTree.getRoot() == point1).isTrue();
    }
    
}
