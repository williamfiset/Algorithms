/**
 * SkipList is a data structure that is useful for dealing with dynamic sorted data. In particular
 * it gives O(log(n)) average complexity of insertion, removal, and find operations. This
 * implementation has been augmented with a method for determining the index of an element in the
 * SkipList. Finding the index of an element is also O(log(n)) on average. The complexities are
 * average complexities since this algorithm is dependent on randomisation to achieve nice balanced
 * properties. To make this efficient, instantiate the SkipList with a height equal to, or just
 * greater than, log(n) where n is the number of elements that will be in the list. On average, this
 * data structure uses O(n) space. Worst case space is O(nlog(n)), and worst case for all other
 * operations is O(n)
 *
 * @author Finn Lidbetter
 *     <p>Refactored and rewritten by:
 * @author Daniel Gustafsson
 * @author Timmy Lindholm
 * @author Anja Studic
 * @author Christian Stjernberg
 */
package com.williamfiset.algorithms.datastructures.skiplist;

import java.util.Random;

class SkipList {
  private Random rand = new Random();
  private int height;
  private Node head;
  private Node tail;

  /**
   * This function creates a new skip list with the specified height and minimum and maximum values.
   */
  public SkipList(int height, int minValue, int maxValue) {
    // Height goes from 0 to height-1
    this.height = height;
    head = new Node(minValue);
    tail = new Node(maxValue);
    Node currLeft = head;
    Node currRight = tail;
    // Setup links between all levels of head and tail nodes
    for (int i = 1; i < height; i++) {
      setHeadTail(height - i, currLeft, currRight);
      // Create and setup down node
      currLeft.down = new Node(currLeft.value);
      currLeft.down.up = currLeft;
      currRight.down = new Node(currRight.value);
      currRight.down.up = currRight;
      currLeft = currLeft.down;
      currRight = currRight.down;
    }
    // Set last level
    setHeadTail(0, currLeft, currRight);
  }

  private static void setHeadTail(int height, Node nLeft, Node nRight) {
    nLeft.right = nRight;
    nRight.left = nLeft;
    nLeft.index = 0;
    nRight.index = 1;
    nLeft.height = height;
    nRight.height = height;
  }

  public int size() {
    return this.tail.index + 1;
  }

  /** Return true if the number is in the list. False otherwise. */
  public boolean find(int num) {
    return (search(num).value == num);
  }

  // Search for closest node by number
  private Node search(int num) {
    return search(num, this.head);
  }

  // Helper method for search
  private Node search(int num, Node node) {
    // Check if the next right node is still less than num
    if (node.compareTo(num) < 0) {
      if (node.down != null) return search(num, node.down);
      else if (node.right != null && node.right.compareTo(num) <= 0) return search(num, node.right);
    }
    return node;
  }

  /** Inserts the number into the list. */
  public boolean insert(int num) {
    if (num < head.value || num > tail.value) return false;
    Node node = search(num);
    if (node.value == num) return false;
    int nodeHeight = 0;
    // 0.5 probability of having height 1, 0.25 for height 2
    while (rand.nextBoolean() && nodeHeight < (height - 1)) {
      nodeHeight++;
    }
    insert(node, new Node(num), null, nodeHeight, node.index + 1);
    return true;
  }

  /** Helper method for insert */
  private void insert(Node startNode, Node insertNode, Node lower, int insertHeight, int distance) {
    if (startNode.height <= insertHeight) {
      insertNode.left = startNode;
      insertNode.right = startNode.right;
      // If not at lowest level
      if (lower != null) {
        lower.up = insertNode;
        insertNode.down = lower;
      }
      // If at lowest level, update ranks of following
      else if (startNode.height == 0) {
        increaseRank(insertNode.right);
      }
      startNode.right.left = insertNode;
      startNode.right = insertNode;
      insertNode.height = startNode.height;
      insertNode.index = distance;
      Node curr = startNode;
      while (curr.up == null && curr.left != null) {
        curr = curr.left;
      }
      if (curr.up != null) {
        curr = curr.up;
        insert(curr, new Node(insertNode.value), insertNode, insertHeight, distance);
      }
    }
  }

  /**
   * Remove a the number from the list with the given value. Returns true if value was successfully
   * removed. False otherwise.
   */
  public boolean remove(int num) {
    if (num == head.value || num == tail.value) return false;
    // Get the node to remove
    Node node = search(num);
    if (node.value != num) return false;
    // Re-number all nodes to the right
    decreaseRank(node.right);

    // Connect the left and right nodes to each other
    while (node.up != null) {
      node.left.right = node.right;
      node.right.left = node.left;
      node = node.up;
    }
    node.left.right = node.right;
    node.right.left = node.left;

    return true;
  }

  // Decrease the index for all nodes to the right of the start node
  private void decreaseRank(Node startNode) {
    modifyRank(startNode, -1);
  }

  // Increase the index for all nodes to the right of the start node
  private void increaseRank(Node startNode) {
    modifyRank(startNode, 1);
  }

  // Modify the index for all nodes to the right of the start node
  private void modifyRank(Node startNode, int change) {
    Node node = startNode;
    // Check all nodes to the right
    while (startNode != null) {
      node = startNode;
      // Check all nodes upwards
      while (node.up != null) {
        node.index += change;
        node = node.up;
      }
      node.index += change;
      startNode = startNode.right;
    }
  }

  /** Returns the index of the number in the list or -1 if not found. */
  public int getIndex(int num) {
    Node node = search(num);
    return num == node.value ? node.index : -1;
  }

  class Node implements Comparable<Node> {
    Node left;
    Node right;
    Node up;
    Node down;
    int height;
    int index;
    int value;

    public Node(int value) {
      this.value = value;
    }

    public int compareTo(Node n2) {
      return Integer.compare(this.value, n2.value);
    }

    public int compareTo(int num) {
      return Integer.compare(this.value, num);
    }
  }
}
