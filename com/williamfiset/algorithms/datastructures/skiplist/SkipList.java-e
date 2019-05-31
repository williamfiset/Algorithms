/**
 * SkipList is a data structure that is useful for dealing with dynamic sorted data. In particular
 * it gives O(log(n)) average complexity of insertion, removal, and find operations. This
 * implementation has been augmented with a method for determining the rank of an element in the
 * SkipList. Finding the rank of an element is also O(log(n)) on average. The complexities are
 * average complexities since this algorithm is dependent on randomisation to achieve nice balanced
 * properties. To make this efficient, instantiate the SkipList with a height equal to, or just
 * greater than, log(n) where n is the number of elements that will be in the list. On average, this
 * data structure uses O(n) space. Worst case space is O(nlog(n)), and worst case for all other
 * operations is O(n)
 *
 * @author Finn Lidbetter
 */
package com.williamfiset.datastructures.skiplist;

import java.util.Random;

class SkipList {
  Random rand = new Random(0);
  int height;
  Node head;
  Node tail;
  // Initialise with the height of the SkipList and Keys smaller and larger
  // than all other keys that will be inserted in the SkipList
  public SkipList(int height, Key minKey, Key maxKey, int h) {
    this.height = height;
    head = new Node(minKey);
    tail = new Node(maxKey);
    Node currLeft = head;
    Node currRight = tail;
    for (int i = 0; i <= h; i++) {
      currLeft.right = currRight;
      currRight.left = currLeft;
      currLeft.down = new Node(currLeft.k);
      currLeft.down.up = currLeft;
      currRight.down = new Node(currRight.k);
      currRight.down.up = currRight;
      currLeft.leftDist = 0;
      currRight.leftDist = 1;
      currLeft.height = height - i;
      currRight.height = height - i;
      currLeft = currLeft.down;
      currRight = currRight.down;
    }
    currLeft.up.down = null;
    currRight.up.down = null;
  }

  public void insert(Node n2) {
    int r = rand.nextInt();
    if (r < 0) r *= -1;
    r %= (1 << (height - 1));
    int nodeHeight = Integer.numberOfLeadingZeros(r) - (32 - (height - 1));
    head.find(n2).insert(n2, null, nodeHeight, 1);
  }

  public void remove(Node n2) {
    head.find(n2).remove(n2);
  }

  public int getRank(Node n2) {
    Node curr = head.find(n2);
    if (curr.compareTo(n2) != 0) return -1;
    int distSum = 0;
    while (curr.left != null) {
      while (curr.up != null) {
        curr = curr.up;
      }
      distSum += curr.leftDist;
      curr = curr.left;
    }
    return distSum;
  }

  class Node implements Comparable<Node> {
    Node left;
    Node right;
    Node up;
    Node down;
    int height;
    int leftDist;
    Key k;

    public Node(Key kk) {
      k = kk;
    }

    public int compareTo(Node n2) {
      return k.compareTo(n2.k);
    }

    public Node find(Node f) {
      if (f.compareTo(right) >= 0) {
        return right.find(f);
      } else if (down != null) {
        return down.find(f);
      }
      return this;
    }

    public void insert(Node n2, Node lower, int insertHeight, int distance) {
      if (height <= insertHeight) {
        n2.left = this;
        n2.right = right;
        n2.down = lower;
        right.left = n2;
        right = n2;
        if (lower != null) lower.up = n2;
        n2.height = height;
        n2.leftDist = distance;
        n2.right.leftDist -= n2.leftDist - 1;
        Node curr = this;
        while (curr.up == null) {
          distance += curr.leftDist;
          curr = curr.left;
        }
        curr = curr.up;
        curr.insert(new Node(n2.k), n2, insertHeight, distance);
      } else {
        Node curr = this;
        curr.right.leftDist++;
        while (curr.left != null || curr.up != null) {
          while (curr.up == null) {
            curr = curr.left;
          }
          curr = curr.up;
          curr.right.leftDist++;
        }
      }
    }

    public void remove(Node n2) {
      Node curr = this;
      if (curr.compareTo(n2) != 0) return;
      while (curr.up != null) {
        curr.left.right = curr.right;
        curr.right.left = curr.left;
        curr.right.leftDist += curr.leftDist - 1;
        curr = curr.up;
      }
      curr.left.right = curr.right;
      curr.right.left = curr.left;
      curr.right.leftDist += curr.leftDist - 1;
      while (curr.left != null || curr.up != null) {
        while (curr.up == null) {
          curr = curr.left;
        }
        curr = curr.up;
        curr.right.leftDist--;
      }
    }
  }

  class Key implements Comparable<Key> {
    int k;

    public Key(int kk) {
      k = kk;
    }

    public int compareTo(Key k2) {
      return k - k2.k;
    }
  }
}
