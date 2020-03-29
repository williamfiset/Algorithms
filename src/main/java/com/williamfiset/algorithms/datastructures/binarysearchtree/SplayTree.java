// javac -d classes -sourcepath src/main/java
// src/main/java/com/williamfiset/algorithms/datastructures/binarysearchtree/SplayTree.java
// java -cp classes com.williamfiset.algorithms.datastructures.binarysearchtree.SplayTreeRun

package com.williamfiset.algorithms.datastructures.binarysearchtree;

import com.williamfiset.algorithms.datastructures.utils.TreePrinter;
import java.util.ArrayList;
import java.util.Scanner;

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
    public BinaryTree<T> getLeft() {
      return leftChild;
    }

    public void setLeft(BinaryTree<T> leftChild) {
      this.leftChild = leftChild;
    }

    @Override
    public BinaryTree<T> getRight() {
      return rightChild;
    }

    public void setRight(BinaryTree<T> rightChild) {
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
  public BinaryTree<T> search(T node) {
    if (root == null) return null;

    this.root = splayUtil(root, node);

    return this.root.getData().compareTo(node) == 0 ? this.root : null;
  }

  /** Inserts a node into the tree and splays it on top, returns the new root* */
  public BinaryTree<T> insert(T node) {
    if (root == null) {
      root = new BinaryTree<>(node);
      return root;
    }
    splay(node);

    ArrayList<BinaryTree<T>> l_r = split(node);

    BinaryTree<T> left = l_r.get(0);
    BinaryTree<T> right = l_r.get(1);

    root = new BinaryTree<>(node);
    root.setLeft(left);
    root.setRight(right);

    return root;
  }

  /** Deletes a node,returns the new root * */
  public BinaryTree<T> delete(T node) {
    if (root == null) return null;

    BinaryTree<T> searchResult = splay(node);

    if (searchResult.getData().compareTo(node) != 0) return null;

    BinaryTree<T> leftSubtree = root.getLeft();
    BinaryTree<T> rightSubtree = root.getRight();

    // Set the 'to be deleted' key ready for garbage collection
    root.setLeft(null);
    root.setRight(null);

    root = join(leftSubtree, rightSubtree);

    return root;
  }

  /** To FindMax Of Entire Tree * */
  public T findMax() {
    BinaryTree<T> temp = root;
    while (temp.getRight() != null) temp = temp.getRight();
    return temp.getData();
  }

  /** To FindMin Of Entire Tree * */
  public T findMin() {
    BinaryTree<T> temp = root;
    while (temp.getLeft() != null) temp = temp.getLeft();
    return temp.getData();
  }

  /** * To FindMax Of Tree with specified root * */
  public T findMax(BinaryTree<T> root) {
    BinaryTree<T> temp = root;
    while (temp.getRight() != null) temp = temp.getRight();
    return temp.getData();
  }

  /** * To FindMin Of Tree with specified root * */
  public T findMin(BinaryTree<T> root) {
    BinaryTree<T> temp = root;
    while (temp.getLeft() != null) temp = temp.getLeft();
    return temp.getData();
  }

  @Override
  public String toString() {

    System.out.println("Elements:" + inorder(root, new ArrayList<>()));
    return (root != null) ? root.toString() : null;
  }

  /** Private Methods * */
  private BinaryTree<T> rightRotate(BinaryTree<T> node) {
    BinaryTree<T> p = node.getLeft();
    node.setLeft(p.getRight());
    p.setRight(node);
    return p;
  }

  private BinaryTree<T> leftRotate(BinaryTree<T> node) {
    BinaryTree<T> p = node.getRight();
    node.setRight(p.getLeft());
    p.setLeft(node);
    return p;
  }

  private BinaryTree<T> splayUtil(BinaryTree<T> root, T key) {
    if (root == null || root.getData() == key) return root;

    if (root.getData().compareTo(key) > 0) {
      if (root.getLeft() == null) return root;

      if (root.getLeft().getData().compareTo(key) > 0) {

        root.getLeft().setLeft(splayUtil(root.getLeft().getLeft(), key));

        root = rightRotate(root);
      } else if (root.getLeft().getData().compareTo(key) < 0) {

        root.getLeft().setRight(splayUtil(root.getLeft().getRight(), key));

        if (root.getLeft().getRight() != null) root.setLeft(leftRotate(root.getLeft()));
      }
      return (root.getLeft() == null) ? root : rightRotate(root);
    } else {
      if (root.getRight() == null) return root;

      if (root.getRight().getData().compareTo(key) > 0) {
        root.getRight().setLeft(splayUtil(root.getRight().getLeft(), key));
        if (root.getRight().getLeft() != null) root.setRight(rightRotate(root.getRight()));
      } else if (root.getRight().getData().compareTo(key) < 0) // Zag-Zag (Right Right)
      {
        root.getRight().setRight(splayUtil(root.getRight().getRight(), key));
        root = leftRotate(root);
      }

      return (root.getRight() == null) ? root : leftRotate(root);
    }
  }

  private BinaryTree<T> splay(T node) {
    if (root == null) return null;

    this.root = splayUtil(root, node);

    return this.root;
  }

  private ArrayList<BinaryTree<T>> split(T node) {
    BinaryTree<T> right;
    BinaryTree<T> left;

    if (node.compareTo(root.getData()) > 0) {
      right = root.getRight();
      left = root;
      left.setRight(null);
    } else {
      left = root.getLeft();
      right = root;
      right.setLeft(null);
    }
    ArrayList<BinaryTree<T>> l_r = new ArrayList<>();
    l_r.add(left);
    l_r.add(right);

    return l_r;
  }

  private BinaryTree<T> join(BinaryTree<T> L, BinaryTree<T> R) {

    if (L == null) {
      root = R;
      return R;
    }
    root = splayUtil(L, findMax(L));
    root.setRight(R);
    return root;
  }

  private ArrayList<T> inorder(BinaryTree<T> root, ArrayList<T> sorted) {

    if (root == null) {
      return sorted;
    }
    inorder(root.getLeft(), sorted);
    sorted.add(root.getData());
    inorder(root.getRight(), sorted);
    return sorted;
  }
}

class SplayTreeRun {

  public static void main(String[] args) {

    SplayTree<Integer> splayTree = new SplayTree<>();
    Scanner sc = new Scanner(System.in);
    int[] data = {2, 29, 26, -1, 10, 0, 2, 11};
    int c = 0;
    for (int i : data) {
      splayTree.insert(i);
    }

    while (c != 7) {
      System.out.println("1. Insert 2. Delete 3. Search 4.FindMin 5.FindMax 6. PrintTree 7. Exit");
      c = sc.nextInt();
      switch (c) {
        case 1:
          System.out.println("Enter Data :");
          splayTree.insert(sc.nextInt());
          break;
        case 2:
          System.out.println("Enter Element to be Deleted:");
          splayTree.delete(sc.nextInt());
          break;
        case 3:
          System.out.println("Enter Element to be Searched and Splayed:");
          splayTree.search(sc.nextInt());
          break;
        case 4:
          System.out.println("Min: " + splayTree.findMin());
          break;
        case 5:
          System.out.println("Max: " + splayTree.findMax());
          break;
        case 6:
          System.out.println(splayTree);
          break;
        case 7:
          sc.close();
          break;
      }
    }
  }
}
