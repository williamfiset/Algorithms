package com.williamfiset.algorithms.datastructures.balancedtree;

import java.util.LinkedList;
import java.util.Queue;

class BTreeNode {
    int[] keys;
    BTreeNode[] children;
    int numKeys;
    boolean isLeaf;

    public BTreeNode(int t, boolean isLeaf) {
        this.keys = new int[2 * t - 1];
        this.children = new BTreeNode[2 * t];
        this.isLeaf = isLeaf;
        this.numKeys = 0;
    }

    public void traverse() {
        int i;
        for (i = 0; i < numKeys; i++) {
            if (!isLeaf) {
                children[i].traverse();
            }
            System.out.print(keys[i] + " ");
        }

        if (!isLeaf) {
            children[i].traverse();
        }
    }
}

public class Btree {
    private BTreeNode root;
    private int t;

    public Btree(int t) {
        this.root = null;
        this.t = t;
    }

    public void insert(int key) {
        if (root == null) {
            root = new BTreeNode(t, true);
            root.keys[0] = key;
            root.numKeys = 1;
        } else {
            if (root.numKeys == 2 * t - 1) {
                BTreeNode newRoot = new BTreeNode(t, false);
                newRoot.children[0] = root;
                splitChild(newRoot, 0);
                insertNonFull(newRoot, key);
                root = newRoot;
            } else {
                insertNonFull(root, key);
            }
        }
    }

    private void insertNonFull(BTreeNode node, int key) {
        int i = node.numKeys - 1;

        if (node.isLeaf) {
            while (i >= 0 && key < node.keys[i]) {
                node.keys[i + 1] = node.keys[i];
                i--;
            }
            node.keys[i + 1] = key;
            node.numKeys++;
        } else {
            while (i >= 0 && key < node.keys[i]) {
                i--;
            }

            i++;

            if (node.children[i].numKeys == 2 * t - 1) {
                splitChild(node, i);

                if (key > node.keys[i]) {
                    i++;
                }
            }

            insertNonFull(node.children[i], key);
        }
    }

    private void splitChild(BTreeNode parentNode, int childIndex) {
        BTreeNode childNode = parentNode.children[childIndex];
        BTreeNode newNode = new BTreeNode(t, childNode.isLeaf);
        parentNode.numKeys++;

        for (int j = parentNode.numKeys - 1; j > childIndex; j--) {
            parentNode.keys[j] = parentNode.keys[j - 1];
        }

        parentNode.keys[childIndex] = childNode.keys[t - 1];

        for (int j = parentNode.numKeys; j > childIndex + 1; j--) {
            parentNode.children[j] = parentNode.children[j - 1];
        }

        parentNode.children[childIndex + 1] = newNode;

        for (int j = 0; j < t - 1; j++) {
            newNode.keys[j] = childNode.keys[j + t];
        }

        if (!childNode.isLeaf) {
            for (int j = 0; j < t; j++) {
                newNode.children[j] = childNode.children[j + t];
            }
        }

        childNode.numKeys = t - 1;
    }

    public void traverse() {
        if (root != null) {
            root.traverse();
        }
    }

    public static void main(String[] args) {
        Btree bTree = new Btree(3);

        int[] keys = {3, 7, 1, 5, 9, 2, 8, 4, 6};

        for (int key : keys) {
            bTree.insert(key);
        }

        System.out.println("B-Tree traversal:");
        bTree.traverse();
    }
}
