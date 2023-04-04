// javac -d classes -sourcepath src/main/java
// src/main/java/com/williamfiset/algorithms/datastructures/binarysearchtree/SplayTree.java
// java -cp classes com.williamfiset.algorithms.datastructures.binarysearchtree.SplayTreeRun

package com.williamfiset.algorithms.datastructures.binarysearchtree;

import com.williamfiset.algorithms.datastructures.utils.TreePrinter;
import java.util.ArrayList;

/**
 * Standard Splay Tree Implementation, supports generic data(must implement Comparable)
 *
 * <p>The Basic Concept of SplayTree is to keep frequently used nodes close to the root of the tree
 * It performs basic operations such as insertion,search,delete,findMin,findMax in O(log n)
 * amortized time Having frequently-used nodes near to the root can be useful in implementing many
 * algorithms. e.g: Implementing caches, garbage collection algorithms etc Primary disadvantage of
 * the splay tree can be the fact that its height can go linear. This causes the worst case running
 * times to go O(n) However, the amortized costs of this worst case situation is logarithmic, O(log
 * n)
 *
 * @author Ashiqur Rahman,https://github.com/ashiqursuperfly
 */
public class SplayTree<T extends Comparable<T>> {

  private BinaryTree<T> root;

  public static class BinaryTree<T extends Comparable<T>> implements TreePrinter.PrintableNode {
    private T data;
    private BinaryTree<T> leftChild, rightChild;

    public BinaryTree(T data) {
      if (data == null) {
        try {
          throw new Exception("Null data not allowed into tree");
        } catch (Exception e) {
          e.printStackTrace();
        }
      } else this.data = data;
    }

    @Override
    public BinaryTree<T> getLeftChild() {
      return leftChild;
    }

    public void setLeftChild(BinaryTree<T> leftChild) {
      this.leftChild = leftChild;
    }

    @Override
    public BinaryTree<T> getRightChild() {
      return rightChild;
    }

    public void setRightChild(BinaryTree<T> rightChild) {
      this.rightChild = rightChild;
    }

    @Override
    public String getText() {
      return data.toString();
    }

    public T getData() {
      return data;
    }

    public void setData(T data) {
      if (data == null) {
        try {
          throw new Exception("Null data not allowed into tree");
        } catch (Exception e) {
          e.printStackTrace();
        }
      } else this.data = data;
    }

    @Override
    public String toString() {

      return TreePrinter.getTreeDisplay(this);
    }
  }

  /** Public Methods * */
  public SplayTree() {
    this.root = null;
  }

  public SplayTree(BinaryTree<T> root) {
    this.root = root;
  }

  public BinaryTree<T> getRoot() {
    return root;
  }

  /** Searches a node and splays it on top,returns the new root * */
  public BinaryTree<T> searchNode(T node) {
    if (root == null) return null;

    this.root = splayUtil(root, node);

    return this.root.getData().compareTo(node) == 0 ? this.root : null;
  }

  /** Inserts a node into the tree and splays it on top, returns the new root* */
  public BinaryTree<T> insertNode(T node) {
    if (root == null) {
      root = new BinaryTree<>(node);
      return root;
    }
    splay(node);

    ArrayList<BinaryTree<T>> l_r = splitChild(node);

    BinaryTree<T> left = l_r.get(0);
    BinaryTree<T> right = l_r.get(1);

    root = new BinaryTree<>(node);
    root.setLeftChild(left);
    root.setRightChild(right);

    return root;
  }

  /** Deletes a node,returns the new root * */
  public BinaryTree<T> deleteNode(T node) {
    if (root == null) return null;

    BinaryTree<T> searchResult = splay(node);

    assert searchResult != null;
    if (searchResult.getData().compareTo(node) != 0) return null;

    BinaryTree<T> leftSubtree = root.getLeftChild();
    BinaryTree<T> rightSubtree = root.getRightChild();

    // Set the 'to be deleted' key ready for garbage collection
    root.setLeftChild(null);
    root.setRightChild(null);

    root = joinTree(leftSubtree, rightSubtree);

    return root;
  }

  /** To FindMax Of Entire Tree * */
  public T findMaximumNode() {
    BinaryTree<T> temporaryNode = root;
    while (temporaryNode.getRightChild() != null) temporaryNode = temporaryNode.getRightChild();
    return temporaryNode.getData();
  }

  /** To FindMin Of Entire Tree * */
  public T findMinimumNode() {
    BinaryTree<T> temporaryNode = root;
    while (temporaryNode.getLeftChild() != null) temporaryNode = temporaryNode.getLeftChild();
    return temporaryNode.getData();
  }

  /** * To FindMax Of Tree with specified root * */
  public T findMaximumNode(BinaryTree<T> root) {
    BinaryTree<T> temporaryNode = root;
    while (temporaryNode.getRightChild() != null) temporaryNode = temporaryNode.getRightChild();
    return temporaryNode.getData();
  }

  /** * To FindMin Of Tree with specified root * */
  public T findMinimumNode(BinaryTree<T> root) {
    BinaryTree<T> temporaryNode = root;
    while (temporaryNode.getLeftChild() != null) temporaryNode = temporaryNode.getLeftChild();
    return temporaryNode.getData();
  }

  @Override
  public String toString() {

    System.out.println("Elements:" + inorder(root, new ArrayList<>()));
    return (root != null) ? root.toString() : null;
  }

  /** Private Methods * */
  private BinaryTree<T> rightRotate(BinaryTree<T> node) {
    BinaryTree<T> p = node.getLeftChild();
    node.setLeftChild(p.getRightChild());
    p.setRightChild(node);
    return p;
  }

  private BinaryTree<T> leftRotate(BinaryTree<T> node) {
    BinaryTree<T> p = node.getRightChild();
    node.setRightChild(p.getLeftChild());
    p.setLeftChild(node);
    return p;
  }

  private BinaryTree<T> splayUtil(BinaryTree<T> root, T key) {
    if (root == null || root.getData() == key) return root;

    if (root.getData().compareTo(key) > 0) {
      if (root.getLeftChild() == null) return root;

      if (root.getLeftChild().getData().compareTo(key) > 0) {

        root.getLeftChild().setLeftChild(splayUtil(root.getLeftChild().getLeftChild(), key));

        root = rightRotate(root);
      } else if (root.getLeftChild().getData().compareTo(key) < 0) {

        root.getLeftChild().setRightChild(splayUtil(root.getLeftChild().getRightChild(), key));

        if (root.getLeftChild().getRightChild() != null) root.setLeftChild(leftRotate(root.getLeftChild()));
      }
      return (root.getLeftChild() == null) ? root : rightRotate(root);
    } else {
      if (root.getRightChild() == null) return root;

      if (root.getRightChild().getData().compareTo(key) > 0) {
        root.getRightChild().setLeftChild(splayUtil(root.getRightChild().getLeftChild(), key));
        if (root.getRightChild().getLeftChild() != null) root.setRightChild(rightRotate(root.getRightChild()));
      } else if (root.getRightChild().getData().compareTo(key) < 0) // Zag-Zag (Right Right)
      {
        root.getRightChild().setRightChild(splayUtil(root.getRightChild().getRightChild(), key));
        root = leftRotate(root);
      }

      return (root.getRightChild() == null) ? root : leftRotate(root);
    }
  }

  private BinaryTree<T> splay(T node) {
    if (root == null) return null;

    this.root = splayUtil(root, node);

    return this.root;
  }

  private ArrayList<BinaryTree<T>> splitChild(T node) {
    BinaryTree<T> right;
    BinaryTree<T> left;

    if (node.compareTo(root.getData()) > 0) {

      right = root.getRightChild();
      left = root;
      left.setRightChild(null);
    } else {
      left = root.getLeftChild();
      right = root;
      right.setLeftChild(null);
    }
    ArrayList<BinaryTree<T>> l_r = new ArrayList<>();
    l_r.add(left);
    l_r.add(right);

    return l_r;
  }

  private BinaryTree<T> joinTree(BinaryTree<T> leftTree, BinaryTree<T> rightTree) {

    if (leftTree == null) {
      root = rightTree;
      return rightTree;
    }
    root = splayUtil(leftTree, findMaximumNode(leftTree));
    root.setRightChild(rightTree);
    return root;
  }

  private ArrayList<T> inorder(BinaryTree<T> root, ArrayList<T> sorted) {

    if (root == null) {
      return sorted;
    }
    inorder(root.getLeftChild(), sorted);
    sorted.add(root.getData());
    inorder(root.getRightChild(), sorted);
    return sorted;
  }
}

