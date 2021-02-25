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
        //Create the new node and make it the root if the root is null
        KDNode<T> newNode = new KDNode<T>(toAdd);
        if(root == null) root = newNode;
        //Otherwise, insert the node recursively
        else insertRecursive(newNode, root, 0);
    }

    private void insertRecursive(KDNode<T> toAdd, KDNode<T> curr, int axis) {
        //If the new point should go to the left, go left and insert where a spot is available
        if((toAdd.point[axis]).compareTo(curr.point[axis]) < 0) {
            if(curr.left == null) curr.left = toAdd;
            else insertRecursive(toAdd, curr.left, (++axis)%k);
        }
        //Otherwise, go right and insert where a spot is available
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
        //If the search fails, the point is not in the tree
        if(curr == null) return false;
        //If the search succeeds, the point is in the tree
        if((curr.point).equals(toSearch.point)) return true;
        //Otherwise, go where the point would go if it was inserted into the tree
        KDNode<T> nextNode = ((toSearch.point[axis]).compareTo(curr.point[axis]) < 0) ? curr.left : curr.right;
        return searchRecursive(toSearch, nextNode, (++axis)%k);
    }

    //FindMin Method
    public T[] findMin(int dim) {
        if(dim < 0 || dim >= k) throw new IllegalArgumentException("Error: Dimension out of bounds");
        return findMinRecursive(dim, root, 0);
    }

    public T[] findMinRecursive(int dim, KDNode<T> curr, int axis) {
        //If nothing is found, return nothing
        if(curr == null) return null;
        //If the axis and dimension match, follow typical search procedure
        if(dim == axis) {
            if(curr.left == null) return curr.point;
            return findMinRecursive(dim, curr.left, (axis+1)%k);
        }
        //If there are no children, return what you have
        if(curr.left == null && curr.right == null) return curr.point;
        //If there is at least one child, search the children of the current node
        T[] leftSubTree = findMinRecursive(dim, curr.left, (axis+1)%k);
        T[] rightSubTree = findMinRecursive(dim, curr.right, (axis+1)%k);
        T[] minSubTree;
        //If a child is null, pick the non-null child
        if(leftSubTree == null || rightSubTree == null) {
            minSubTree = (rightSubTree == null) ? leftSubTree : rightSubTree;
        }
        //Otherwise, compare them
        else {
            minSubTree = ((leftSubTree[dim]).compareTo(rightSubTree[dim]) < 0) ? leftSubTree : rightSubTree;
        }
        //Compare with the value in the current node and return the point with the smallest value in the given dimension
        T[] min = ((curr.point[dim]).compareTo(minSubTree[dim]) < 0) ? curr.point : minSubTree;
        return min;
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
