/**
 * Often when working with trees we are given them as a 
 * graph with undirected edges, however sometimes a better
 * representation is a rooted tree.
 *
 * Time Complexity: O(V+E)
 *  
 * @author William Fiset, william.alexandre.fiset@gmail.com
 **/
package com.williamfiset.algorithms.graphtheory.treealgorithms;

import java.util.*;

class TreeNode {
  int id;
  TreeNode parent;
  List <TreeNode> children;
  public TreeNode(int id) {
    this.id = id;
    children = new LinkedList<>();
  }
  public void addChild(TreeNode child) {
    children.add(child);
  }
  public boolean leaf() {
    return children.size() == 0;
  }
  @Override public String toString() {
    return "" + id;
  }
}

class RootingTree {

  // Given an undirected graph representing a tree, we wish to root
  // the tree at a specified id and return a reference to the root
  public static TreeNode rootTree(List<List<Integer>> graph, int rootID)  {

    TreeNode root = new TreeNode(rootID);
    root.parent = root;

    boolean[] visited = new boolean[graph.size()];
    return buildRootedTree(root, visited, graph, rootID);

  }

  // Do a DFS to build the tree recursively
  private static TreeNode buildRootedTree(TreeNode root, boolean[] visited, List<List<Integer>> graph, int id) {
    
    if (visited[id]) return root;

    List <Integer> children = graph.get(id);
    visited[id] = true;

    for (Integer childID : children) {
      if (visited[childID]) continue;
      TreeNode childNode = new TreeNode(childID);
      root.addChild(childNode);
      childNode.parent = root;
      childNode = buildRootedTree(childNode, visited, graph, childID);
    }

    return root;

  }


    /************ TESTING **********/

  // Create a graph as a adjacency list
  public static List<List<Integer>> createGraph(int n) {
    List<List<Integer>> graph = new ArrayList<>(n);
    for (int i = 0; i < n; i++) graph.add(new LinkedList<>());
    return graph;
  }

  public static void addUndirectedEdge(List<List<Integer>> graph, int from, int to) {
    graph.get(from).add(to);
    graph.get(to).add(from);
  }

  public static void main(String[] args) {

    List<List<Integer>> graph = createGraph(9);
    addUndirectedEdge(graph, 0, 1);
    addUndirectedEdge(graph, 2, 1);
    addUndirectedEdge(graph, 2, 3);
    addUndirectedEdge(graph, 3, 4);
    addUndirectedEdge(graph, 5, 3);
    addUndirectedEdge(graph, 2, 6);
    addUndirectedEdge(graph, 6, 7);
    addUndirectedEdge(graph, 6, 8);

    
    // Rooted at 6 the tree should look like:
    //           6
    //      2    7     8
    //    1   3
    //  0    4 5

    // Layer 0: [6]
    TreeNode root = rootTree(graph, 6);

    // Layer 1: [2, 7, 8]
    System.out.println(root.children);

    // Layer 2: [1, 3]
    System.out.println(root.children.get(0).children);

    // Layer 3: [0, 4, 5]
    System.out.println(root.children.get(0).children.get(0).children);
    System.out.println(root.children.get(0).children.get(1).children);

    // Rooted at 3 the tree should look like:
    //               3
    //     2         4        5
    //  6     1
    // 7 8    0

    // Layer 0: [3]
    root = rootTree(graph, 3);
    System.out.println();
    System.out.println(root);

    // Layer 1: [2, 4, 5]
    System.out.println(root.children);

    // Layer 2: [1, 6]
    System.out.println(root.children.get(0).children);

    // Layer 3: [0, 7, 8]
    System.out.println(root.children.get(0).children.get(0).children);
    System.out.println(root.children.get(0).children.get(1).children);
    
  }

}



