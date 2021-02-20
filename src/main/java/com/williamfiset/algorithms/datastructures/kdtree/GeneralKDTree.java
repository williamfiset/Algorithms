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

    public int[] getRoot() {
        return root.point;
    }

    /* Tree Methods */
    public void insert(int[] toAdd) {
        KDNode newNode = new KDNode(toAdd);
        int currentAxis = 0;
        if(root == null) root = newNode;
        else insertRecursive(newNode, root, currentAxis);
    }

    public void insertRecursive(KDNode toAdd, KDNode curr, int axis) {
        if(toAdd.point[axis] < curr.point[axis]) {
            if(curr.left == null) curr.left = toAdd;
            else insertRecursive(toAdd, curr.left, (++axis)%k);
        }
        else {
            if(curr.right == null) curr.right = toAdd;
            else insertRecursive(toAdd, curr.right, (++axis)%k);
        }
    }

    public void search(int[] element) {
        //?
    }

    public int[] findMin(int dim) {
        return null;
    }

    public int[] remove(int[] toDelete) {
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
