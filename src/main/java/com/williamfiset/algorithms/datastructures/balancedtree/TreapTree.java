/**
 * This file contains an implementation of a Treap Class. Any comparable data
 * is allowed within this tree(numbers, strings, comparable Objects, etc...)
 * Supported operations include:
 * insert(x)
 * remove(x)
 * contains(x)
 *
 * @author JZ Chang, jzisheng@gmail.com
 */

package com.williamfiset.algorithms.datastructures.balancedtree;

import java.util.Random;
import java.awt.*;
public class TreapTree<T extends Comparable<T>> {
    private Random random;

    public class Node {
        // The value/data contained within the node
        public T value;

        // The int priority of this node for Treap
        public int priority;

        // The left right and parent references of this node
        public Node left, right;

        public Node(T value) {
            this.value = value;
            this.priority = random.nextInt(100);
            this.left = this.right = null;
        }
        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public TreapTree.Node getLeft() {
            return left;
        }

        public void setLeft(TreapTree.Node left) {
            this.left = left;
        }

        public TreapTree.Node getRight() {
            return right;
        }

        public void setRight(TreapTree.Node right) {
            this.right = right;
        }
    }
    // The root node of the Treap tree.
    public Node root;

    // Tracks the number of nodes inside the tree
    private int nodeCount = 0;

    public final Node NIL;

    public TreapTree(){
        NIL = new Node(null);
        NIL.left = null;
        NIL.right = null;

        root = NIL;
    }
    // returns the number of nodes in the tree
    public int size(){
        return this.nodeCount;
    }

    public boolean contains(T value){
        return contains(root, value);
    }

    private boolean contains(Node node, T elem) {
        if (node == null) return false;

        int cmp = elem.compareTo(node.getValue());

        if (cmp < 0) return contains(node.left, elem);

        else if (cmp > 0) return contains(node.right, elem);

        else return true;
    }

    public boolean isEmpty(){
        return (nodeCount == 0);
    }

    public boolean insert(T val){
        if (val == null){
            throw new IllegalArgumentException("TreapTree does not allow null values");
        }
    }

    private Node insert(Node node, T value) {
        // base case
        if (node == null) return new Node(value);

        // compare curr val to the val in the node
        int compareResult = value.compareTo(node.value);

        if (compareResult < 0){
            node.left = insert(node.left, value);
            if (node.left.priority < node.priority){
                // rotate with left child (node)
            }
        }
    }

    public boolean delete(T key){
        return true;
    }
}
