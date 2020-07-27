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
        }
    }
    // The root node of hte Treap tree.
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

    public int size(){
        return this.nodeCount;
    }

    public boolean contains(T value){
        return null;
    }

    public boolean isEmpty(){
        return null;
    }

    public boolean insert(T val){
        return null;
    }

    public boolean delete(T key){
        return null;
    }
}
