package com.williamfiset.algorithms.datastructures.vanemdeboastree;

public class VanEmdeBoasTree {

  private final VanEmdeBoasTreeNode root;

  public VanEmdeBoasTree(int size) {
    if (size != 0 && ((size & (size - 1)) == 0)) {
      root = new VanEmdeBoasTreeNode(size);
    } else {
      throw new IllegalArgumentException("ERROR: Must create a tree with size a power of 2!");
    }
  }

  public void add(int value) {
    add(root, value);
  }

  private void add(VanEmdeBoasTreeNode node, int value) {
    if (node.min == -1) {
      node.min = value;
      node.max = value;
    }
    if (value < node.min) {
      int tempValue = value;
      value = node.min;
      node.min = tempValue;
    }
    if (value > node.min && node.size > 2) {
      int highOfX = high(node, value);
      int lowOfX = low(node, value);

      if (node.cluster[highOfX].min != -1) {
        add(node.cluster[highOfX], lowOfX);
      } else {
        add(node.summary, highOfX);
        node.cluster[highOfX].min = lowOfX;
        node.cluster[highOfX].max = lowOfX;
      }
    }
    if (value > node.max) {
      node.max = value;
    }
  }

  public void delete(int value) {
    delete(root, value);
  }

  private void delete(VanEmdeBoasTreeNode node, int value) {
    if (node.min == node.max) {
      node.min = -1;
      node.max = -1;
    } else if (2 == node.size) {
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

      int highOfX = high(node, value);
      int lowOfX = low(node, value);
      delete(node.cluster[highOfX], lowOfX);

      if (-1 == node.cluster[highOfX].min) {
        delete(node.summary, highOfX);
        if (value == node.max) {
          int summaryMax = node.summary.max;
          if (-1 == summaryMax) {
            node.max = node.min;
          } else {
            node.max = index(node, summaryMax, node.cluster[summaryMax].max);
          }
        }
      } else if (value == node.max) {
        node.max = index(node, highOfX, node.cluster[highOfX].max);
      }
    }
  }

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

  public int predecessor(int value) {
    return predecessor(root, value);
  }

  public int predecessor(VanEmdeBoasTreeNode node, int value) {
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
          if (-1 != node.min && value > node.min) {
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

  public int min() {
    return root.min;
  }

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
