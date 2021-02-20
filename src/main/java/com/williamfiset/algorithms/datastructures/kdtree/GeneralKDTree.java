/**
 * A generic k-dimensional tree implementation.
 * 
 * @author David Jagnow
 */

package com.williamfiset.algorithms.datastructures.kdtree;

public class GeneralKDTree {
    
    private int k;
    private KDNode root;
    
    /* Definition of the tree */
    public GeneralKDTree(int dimensions) {
        k = dimensions;
        root = null;
    }

    /* Tree Methods */
    public void insert(int[] toAdd) {
        //?
    }

    public void search(int[] element) {
        //?
    }

    public int[] findMin(int dim) {
        return null;
    }

    public int[] delete(int[] toRemove) {
        return null;
    }

    /* Definition of the tree nodes */
    private class KDNode {

        private int[] point;
        private KDNode left;
        private KDNode right;

        public KDNode(int[] coords) {
            if(coords.length != k) throw new IllegalArgumentException("Error: Expected " + k + "dimensions, but given " + coords.length);
            point = coords;
            left = null;
            right = null;
        }
    }
}
