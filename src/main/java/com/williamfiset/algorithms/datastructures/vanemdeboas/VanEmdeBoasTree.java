package com.williamfiset.algorithms.datastructures.vanemdeboas;

/**
 * Van Emde Boas tree is a tree that implements an associative array. It has the following
 * operations:
 *
 * <p>Search: O(log log N)
 *
 * <p>Insert: O(log log N)
 *
 * <p>Delete: O(log log N)
 *
 * <p>Find Next O(log log N)
 *
 * <p>Find Previous O(log log N)
 *
 * <p>N has to be a power of 2.
 */
public class VanEmdeBoasTree {

  private final VanEmdeBoasTreeNode root;

  /**
   * Initializes a Van Emde Boas Tree with a given size. The size has to be a power of 2.
   *
   * @param size Size of the Van Emde Boas tree.
   */
  public VanEmdeBoasTree(int size) {
    if (size != 0 && ((size & (size - 1)) == 0)) { // Check if size is a power of 2.
      root = new VanEmdeBoasTreeNode(size);
    } else {
      throw new IllegalArgumentException("ERROR: Must create a tree with size a power of 2!");
    }
  }

  /**
   * Adds a value to the tree.
   *
   * @param value The value to add.
   */
  public void add(int value) {
    add(root, value);
  }

  private void add(VanEmdeBoasTreeNode node, int value) {
    if (node.min == -1) { // Tree is currently empty
      node.min = value;
      node.max = value;
    }
    if (value < node.min) {
      int tempValue = value;
      value = node.min;
      node.min = tempValue;
    }
    if (value > node.min && node.size > 2) {
      int high = high(node, value);
      int low = low(node, value);

      if (node.cluster[high].min != -1) { // Cluster is not empty
        add(node.cluster[high], low);
      } else { // Cluster is empty
        add(node.summary, high);
        node.cluster[high].min = low;
        node.cluster[high].max = low;
      }
    }
    if (value > node.max) { // Update maximum
      node.max = value;
    }
  }

  /**
   * Deletes a value from this tree
   *
   * @param value Value to delete.
   */
  public void delete(int value) {
    delete(root, value);
  }

  private void delete(VanEmdeBoasTreeNode node, int value) {
    if (node.min == node.max) { // There is only one value in this tree
      node.min = -1;
      node.max = -1;
    } else if (node.size == 2) { // The current node only has 2 values
      if (0 == value) {
        node.min = 1;
      } else {
        node.min = 0;
      }
      node.max = node.min;
    } else if (value == node.min) {
      int summaryMin = node.summary.min;
      value = index(node, summaryMin, node.cluster[summaryMin].min);
      node.min = value;

      int high = high(node, value);
      int low = low(node, value);
      delete(node.cluster[high], low); // Recursively go lower

      if (-1 == node.cluster[high].min) {
        delete(node.summary, high);
        if (value == node.max) {
          int summaryMax = node.summary.max;
          if (-1 == summaryMax) {
            node.max = node.min;
          } else {
            node.max = index(node, summaryMax, node.cluster[summaryMax].max);
          }
        }
      } else if (value == node.max) {
        node.max = index(node, high, node.cluster[high].max);
      }
    }
  }

  /**
   * Check if a value is present in this tree.
   *
   * @param value The value to check for.
   * @return True if the value exists, false otherwise.
   */
  public boolean contains(int value) {
    return contains(root, value);
  }

  private boolean contains(VanEmdeBoasTreeNode node, int value) {
    if (value == node.min || value == node.max) {
      return true;
    } else if (2 == node.size) {
      return false;
    } else {
      return contains(node.cluster[high(node, value)], low(node, value));
    }
  }

  /**
   * Get the next smallest value.
   *
   * @param value The value to look behind.
   * @return The next smallest value.
   */
  public int predecessor(int value) {
    return predecessor(root, value);
  }

  private int predecessor(VanEmdeBoasTreeNode node, int value) {
    if (node.size == 2) {
      if (value == 1 && node.min == 0) {
        return 0;
      } else {
        return -1;
      }
    } else if (node.max != 1 && value > node.max) {
      return node.max;
    } else {
      int high = high(node, value);
      int low = low(node, value);

      int minCluster = node.cluster[high].min;
      if (minCluster != -1 && low > minCluster) {
        return index(node, high, predecessor(node.cluster[high], low));
      } else {
        int clusterPred = predecessor(node.summary, high);
        if (clusterPred == -1) {
          if (node.min != -1 && value > node.min) {
            return node.min;
          } else {
            return -1;
          }
        } else {
          return index(node, clusterPred, node.cluster[clusterPred].max);
        }
      }
    }
  }

  /**
   * Returns the min value.
   *
   * @return The min value.
   */
  public int min() {
    return root.min;
  }

  /**
   * Returns the max value.
   *
   * @return The max value.
   */
  public int max() {
    return root.max;
  }

  private int high(VanEmdeBoasTreeNode node, int value) {
    return (int) Math.floor(value / lowerSquareRoot(node));
  }

  private int low(VanEmdeBoasTreeNode node, int value) {
    return value % (int) lowerSquareRoot(node);
  }

  private double lowerSquareRoot(VanEmdeBoasTreeNode node) {
    return Math.pow(2, Math.floor((Math.log10(node.size) / Math.log10(2)) / 2.0));
  }

  private int index(VanEmdeBoasTreeNode node, int value1, int value2) {
    return (int) (value1 * lowerSquareRoot(node) + value2);
  }
}
