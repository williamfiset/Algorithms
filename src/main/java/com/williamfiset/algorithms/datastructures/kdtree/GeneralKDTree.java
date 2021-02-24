/**
 * A generic k-dimensional tree implementation.
 * 
 * @author David Jagnow
 */

package com.williamfiset.algorithms.datastructures.kdtree;

public class GeneralKDTree {
    
    private int k;
    private KDNode root;
    
    /* KDTREE DEFINITION */
    public GeneralKDTree(int dimensions) {
        k = dimensions;
        root = null;
    }

    /* ATTRIBUTE METHODS */
    public int getDimensions() {
        return k;
    }

    public int[] getRootPoint() {
        return (root == null) ? null : root.point;
    }

    /* TREE METHODS */
    //Insert Method
    public void insert(int[] toAdd) {
        KDNode newNode = new KDNode(toAdd);
        if(root == null) root = newNode;
        else insertRecursive(newNode, root, 0);
    }

    private void insertRecursive(KDNode toAdd, KDNode curr, int axis) {
        if(toAdd.point[axis] < curr.point[axis]) {
            if(curr.left == null) curr.left = toAdd;
            else insertRecursive(toAdd, curr.left, (++axis)%k);
        }
        else {
            if(curr.right == null) curr.right = toAdd;
            else insertRecursive(toAdd, curr.right, (++axis)%k);
        }
    }

    //Search Method
    public boolean search(int[] element) {
        return searchRecursive(element, root, 0);
    }

    private boolean searchRecursive(int[] toSearch, KDNode curr, int axis) {
        if(curr == null) return false;
        int[] currCoords = curr.point;
        if(currCoords.equals(toSearch)) return true;
        return (toSearch[axis] < currCoords[axis]) ? searchRecursive(toSearch, curr.left, axis+1) : searchRecursive(toSearch, curr.right, axis+1) ;
    }

    //FindMin Method
    public int[] findMin(int dim) {
        return null;
    }

    //Remove Method
    public int[] remove(int[] toDelete) {
        return null;
    }

    /* KDTREE NODE DEFINITION */
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
