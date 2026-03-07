package com.williamfiset.algorithms.datastructures.unionfind;

/**
 * Union-Find (Disjoint Set)
 *
 * Tracks a set of elements partitioned into disjoint subsets. Supports
 * near-constant-time union and find operations using union by size and
 * path compression.
 *
 * Use cases:
 *   - Kruskal's minimum spanning tree algorithm
 *   - Detecting cycles in undirected graphs
 *   - Connected components in dynamic graphs
 *
 * Inspired by 'Algorithms Fourth Edition' by Sedgewick and Wayne.
 *
 * Time:  O(α(n)) amortized per find/unify, where α is the inverse Ackermann function
 * Space: O(n)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
public class UnionFind {

  // The number of elements in this union find
  private int size;

  // Used to track the size of each component
  private int[] sz;

  // id[i] points to the parent of i, if id[i] = i then i is a root node
  private int[] id;

  // Tracks the number of components in the union find
  private int numComponents;

  /**
   * Creates a Union-Find with n elements, each in its own component.
   *
   * @param size the number of elements
   */
  public UnionFind(int size) {
    if (size <= 0) throw new IllegalArgumentException("Size <= 0 is not allowed");

    this.size = numComponents = size;
    sz = new int[size];
    id = new int[size];

    for (int i = 0; i < size; i++) {
      id[i] = i; // Link to itself (self root)
      sz[i] = 1; // Each component is originally of size one
    }
  }

  /**
   * Finds the root of the component containing element p.
   * Uses path compression to flatten the tree structure.
   *
   * @param p the element to find the root of
   * @return the root of the component containing p
   */
  public int find(int p) {
    int root = p;
    while (root != id[root]) root = id[root];

    // Path compression: point every node along the path directly to root
    while (p != root) {
      int next = id[p];
      id[p] = root;
      p = next;
    }

    return root;
  }

  // Alternative recursive formulation for find with path compression:
  // public int find(int p) {
  //   if (p == id[p]) return p;
  //   return id[p] = find(id[p]);
  // }

  /** Returns true if elements p and q are in the same component. */
  public boolean connected(int p, int q) {
    return find(p) == find(q);
  }

  /** Returns the size of the component containing element p. */
  public int componentSize(int p) {
    return sz[find(p)];
  }

  /** Returns the total number of elements. */
  public int size() {
    return size;
  }

  /** Returns the number of disjoint components. */
  public int components() {
    return numComponents;
  }

  /**
   * Merges the components containing elements p and q.
   * Uses union by size to keep the tree balanced.
   *
   * @param p an element in the first component
   * @param q an element in the second component
   */
  public void unify(int p, int q) {
    int root1 = find(p);
    int root2 = find(q);

    if (root1 == root2) return;

    // Merge smaller component into the larger one
    if (sz[root1] < sz[root2]) {
      sz[root2] += sz[root1];
      id[root1] = root2;
      sz[root1] = 0;
    } else {
      sz[root1] += sz[root2];
      id[root2] = root1;
      sz[root2] = 0;
    }

    numComponents--;
  }
}
