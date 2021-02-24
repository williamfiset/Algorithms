/**
 * A generic k-dimensional tree implementation.
 * 
 * @author David Jagnow
 */

package com.williamfiset.algorithms.datastructures.kdtree;

public class GeneralKDTree<T extends Comparable<T>> {
    
    private int k;
    private KDNode<T> root;
    
    /* KDTREE DEFINITION */
    public GeneralKDTree(int dimensions) {
        if(dimensions <= 0) throw new IllegalArgumentException("Error: GeneralKDTree must have positive dimensions");
        k = dimensions;
        root = null;
    }

    /* ATTRIBUTE METHODS */
    public int getDimensions() {
        return k;
    }

    public T[] getRootPoint() {
        return (root == null) ? null : root.point;
    }

    /* TREE METHODS */
    //Insert Method
    public void insert(T[] toAdd) {
        KDNode<T> newNode = new KDNode<T>(toAdd);
        if(root == null) root = newNode;
        else insertRecursive(newNode, root, 0);
    }

    private void insertRecursive(KDNode<T> toAdd, KDNode<T> curr, int axis) {
        if((toAdd.point[axis]).compareTo(curr.point[axis]) < 0) {
            if(curr.left == null) curr.left = toAdd;
            else insertRecursive(toAdd, curr.left, (++axis)%k);
        }
        else {
            if(curr.right == null) curr.right = toAdd;
            else insertRecursive(toAdd, curr.right, (++axis)%k);
        }
    }

    //Search Method
    public boolean search(T[] element) {
        KDNode<T> elemNode = new KDNode<T>(element);
        return searchRecursive(elemNode, root, 0);
    }

    private boolean searchRecursive(KDNode<T> toSearch, KDNode<T> curr, int axis) {
        if(curr == null) return false;
        if((curr.point).equals(toSearch.point)) return true;
        KDNode<T> nextNode = ((toSearch.point[axis]).compareTo(curr.point[axis]) < 0) ? curr.left : curr.right;
        return searchRecursive(toSearch, nextNode, (++axis)%k);
    }

    //FindMin Method
    public T[] findMin(int dim) {
        if(dim < 0 || dim >= k) throw new IllegalArgumentException("Error: Dimension out of bounds");
        return findMinRecursive(dim, root, 0);
    }

    public T[] findMinRecursive(int dim, KDNode<T> curr, int axis) {
        return null;
    }

    //Remove Method
    public T[] remove(int[] toDelete) {
        return null;
    }

    /* KDTREE NODE DEFINITION */
    private class KDNode<E extends Comparable<E>> {

        private E[] point;
        private KDNode<E> left;
        private KDNode<E> right;

        public KDNode(E[] coords) {
            if(coords == null) throw new IllegalArgumentException("Error: Null coordinate set passed");
            if(coords.length != k) throw new IllegalArgumentException("Error: Expected " + k + "dimensions, but given " + coords.length);
            point = coords;
            left = null;
            right = null;
        }
    }
}
