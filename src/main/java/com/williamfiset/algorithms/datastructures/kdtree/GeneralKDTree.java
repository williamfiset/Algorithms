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
        if(k <= 0) throw new IllegalArgumentException("Error: GeneralKDTree must have positive dimensions");
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
        KDNode elemNode = new KDNode(element);
        return searchRecursive(elemNode, root, 0);
    }

    private boolean searchRecursive(KDNode toSearch, KDNode curr, int axis) {
        if(curr == null) return false;
        int[] currCoords = curr.point;
        int[] searchCoords = toSearch.point;
        if(currCoords.equals(searchCoords)) return true;
        return (searchCoords[axis] < currCoords[axis]) ? searchRecursive(toSearch, curr.left, axis+1) : searchRecursive(toSearch, curr.right, axis+1);
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
            if(coords == null) throw new IllegalArgumentException("Error: Null coordinate set passed");
            if(coords.length != k) throw new IllegalArgumentException("Error: Expected " + k + "dimensions, but given " + coords.length);
            point = coords;
            left = null;
            right = null;
        }
    }
}
