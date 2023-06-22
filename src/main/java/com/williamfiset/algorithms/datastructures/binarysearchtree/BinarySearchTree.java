/**
 * This file contains an implementation of a Binary Search Tree (BST) Any comparable data is allowed
 * within this tree (numbers, strings, comparable Objects, etc...). Supported operations include
 * adding, removing, height, and containment checks. Furthermore, multiple tree traversal Iterators
 * are provided including: 1) Preorder traversal 2) Inorder traversal 3) Postorder traversal 4)
 * Level Order traversal
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.datastructures.binarysearchtree;

public class BinarySearchTree<T extends Comparable<T>> {

    // Tracks the number of nodes in this BST
    private int nodeCount = 0;

    // This BST is a rooted tree, so we maintain a handle on the root node
    private Node root = null;

    // Check if this binary tree is empty
    public boolean isEmpty() {
        return sizeOfTree() == 0;
    }

    // Get the number of nodes in this binary tree
    public int sizeOfTree() {
        return nodeCount;
    }

    // Add an element to this binary tree. Returns true
    // if we successfully perform an insertion
    public boolean addElement(T element) {

        // Check if the value already exists in this
        // binary tree, if it does ignore adding it
        if (contains(element)) {
            return false;

            // Otherwise add this element to the binary tree
        } else {
            root = addElement(root, element);
            nodeCount++;
            return true;
        }
    }

    // Private method to recursively add a value in the binary tree
    private Node addElement(Node node, T element) {

        // Base case: found a leaf node
        if (node == null) {
            node = new Node(null, null, element);

        } else {
            // Pick a subtree to insert element
            if (element.compareTo(node.data) < 0) {
                node.left = addElement(node.left, element);
            } else {
                node.right = addElement(node.right, element);
            }
        }

        return node;
    }

    // Remove a value from this binary tree if it exists, O(n)
    public boolean removeDuplicateElement(T element) {
        if (contains(element)) {
            BinarySearchTreeMethods binarySearchTreeMethods = new BinarySearchTreeMethods();
            root = binarySearchTreeMethods.removeElement(root, element);
            nodeCount--;
            return true;
        }
        return false;
    }

    private Node removeDuplicateElement(Node node, T element) {

        if (node == null) return null;

        int elementToCompare = element.compareTo(node.data);

        // Dig into left subtree, the value we're looking
        // for is smaller than the current value
        if (elementToCompare < 0) {
            node.left = removeDuplicateElement(node.left, element);

            // Dig into right subtree, the value we're looking
            // for is greater than the current value
        } else if (elementToCompare > 0) {
            node.right = removeDuplicateElement(node.right, element);

            // Found the node we wish to remove
        } else {

            // This is the case with only a right subtree or
            // no subtree at all. In this situation just
            // swap the node we wish to remove with its right child.
            if (node.left == null) {
                return node.right;

                // This is the case with only a left subtree or
                // no subtree at all. In this situation just
                // swap the node we wish to remove with its left child.
            } else if (node.right == null) {

                return node.left;

                // When removing a node from a binary tree with two links the
                // successor of the node being removed can either be the largest
                // value in the left subtree or the smallest value in the right
                // subtree. In this implementation I have decided to find the
                // smallest value in the right subtree which can be found by
                // traversing as far left as possible in the right subtree.
            } else {

                // Find the leftmost node in the right subtree
                Node temporaryNode = findLeftmostNode(node.right);

                // Swap the data
                node.data = temporaryNode.data;

                // Go into the right subtree and remove the leftmost node we
                // found and swapped data with. This prevents us from having
                // two nodes in our tree with the same value.
                node.right = removeDuplicateElement(node.right, temporaryNode.data);

                // If instead we wanted to find the largest node in the left
                // subtree as opposed to the smallest node in the right subtree
                // here is what we would do:
                // Node tmp = findMax(node.left);
                // node.data = tmp.data;
                // node.left = remove(node.left, tmp.data);

            }
        }

        return node;
    }

    // Helper method to find the leftmost node (which has the smallest value)
    private Node findLeftmostNode(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    // Helper method to find the rightmost node (which has the largest value)
    private Node findRightmostNode(Node node) {
        while (node.right != null) node = node.right;
        return node;
    }

    // returns true is the element exists in the tree
    public boolean contains(T element) {
        return contains(root, element);
    }

    // private recursive method to find an element in the tree
    private boolean contains(Node node, T element) {

        // Base case: reached bottom, value not found
        if (node == null) return false;

        int elementToCompare = element.compareTo(node.data);

        // Dig into the left subtree because the value we're
        // looking for is smaller than the current value
        if (elementToCompare < 0) return contains(node.left, element);

            // Dig into the right subtree because the value we're
            // looking for is greater than the current value
        else if (elementToCompare > 0) return contains(node.right, element);

            // We found the value we were looking for
        else return true;
    }

   public class BinarySearchTreeMethods{
       // New method added to encapsulate remove functionality.
       // We only call this method if we know the element is in the tree.
       public Node removeElement(Node node, T element) {
           if (node == null) {
               return null;
           }

           int elementToCompare = element.compareTo(node.data);
           if (elementToCompare < 0) {
               node.left = removeElement(node.left, element);
           } else if (elementToCompare > 0) {
               node.right = removeElement(node.right, element);
           } else {
               if (node.left == null) {
                   return node.right;
               } else if (node.right == null) {
                   return node.left;
               } else {
                   Node temporaryNode = findLeftmostNode(node.right);
                   node.data = temporaryNode.data;
                   node.right = removeElement(node.right, temporaryNode.data);
               }
           }
           return node;
       }
   }
    // Computes the height of the tree, O(n)
    public int heightOfTree() {
        return heightOfTree(root);
    }

    // Recursive helper method to compute the height of the tree
    private int heightOfTree(Node node) {
        if (node == null) return 0;
        return Math.max(heightOfTree(node.left), heightOfTree(node.right)) + 1;
    }

    // This method returns an iterator for a given TreeTraversalOrder.
    // The ways in which you can traverse the tree are in four different ways:
    // preorder, inorder, postorder and level order.
    public java.util.Iterator<T> traverse(TreeTraversalOrder order) {
        switch (order) {
            case PRE_ORDER:
                return preOrderTraversal();
            case IN_ORDER:
                return inOrderTraversal();
            case POST_ORDER:
                return postOrderTraversal();
            case LEVEL_ORDER:
                return levelOrderTraversal();
            default:
                return null;
        }
    }

    // Returns as iterator to traverse the tree in pre-order
    private java.util.Iterator<T> preOrderTraversal() {

        final int expectedNodeCount = nodeCount;
        final java.util.Stack<Node> stack = new java.util.Stack<>();
        stack.push(root);

        return new java.util.Iterator<T>() {
            @Override
            public boolean hasNext() {
                if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
                return root != null && !stack.isEmpty();
            }

            @Override
            public T next() {
                if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
                Node node = stack.pop();
                if (node.right != null) stack.push(node.right);
                if (node.left != null) stack.push(node.left);
                return node.data;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    // Returns as iterator to traverse the tree in order
    private java.util.Iterator<T> inOrderTraversal() {

        final int expectedNodeCount = nodeCount;
        final java.util.Stack<Node> stack = new java.util.Stack<>();
        stack.push(root);

        return new java.util.Iterator<T>() {
            Node traversalNode = root;

            @Override
            public boolean hasNext() {
                if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
                return root != null && !stack.isEmpty();
            }

            @Override
            public T next() {

                if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();

                // Dig left
                while (traversalNode != null && traversalNode.left != null) {
                    stack.push(traversalNode.left);
                    traversalNode = traversalNode.left;
                }

                Node node = stack.pop();

                // Try moving down right once
                if (node.right != null) {
                    stack.push(node.right);
                    traversalNode = node.right;
                }

                return node.data;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    // Returns as iterator to traverse the tree in post order
    private java.util.Iterator<T> postOrderTraversal() {
        final int expectedNodeCount = nodeCount;
        final java.util.Stack<Node> firstStack = new java.util.Stack<>();
        final java.util.Stack<Node> secondStack = new java.util.Stack<>();
        firstStack.push(root);
        while (!firstStack.isEmpty()) {
            Node node = firstStack.pop();
            if (node != null) {
                secondStack.push(node);
                if (node.left != null) firstStack.push(node.left);
                if (node.right != null) firstStack.push(node.right);
            }
        }
        return new java.util.Iterator<T>() {
            @Override
            public boolean hasNext() {
                if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
                return root != null && !secondStack.isEmpty();
            }

            @Override
            public T next() {
                if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
                return secondStack.pop().data;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    // Returns as iterator to traverse the tree in level order
    private java.util.Iterator<T> levelOrderTraversal() {

        final int expectedNodeCount = nodeCount;
        final java.util.Queue<Node> queue = new java.util.LinkedList<>();
        queue.offer(root);

        return new java.util.Iterator<T>() {
            @Override
            public boolean hasNext() {
                if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
                return root != null && !queue.isEmpty();
            }

            @Override
            public T next() {
                if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
                Node node = queue.poll();
                assert node != null;
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
                return node.data;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    // Internal node containing node references
    // and the actual node data
    private class Node {
        T data;
        Node left, right;

        public Node(Node left, Node right, T element) {
            this.data = element;
            this.left = left;
            this.right = right;
        }
    }
}
