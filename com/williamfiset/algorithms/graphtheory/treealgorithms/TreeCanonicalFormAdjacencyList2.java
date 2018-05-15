/**
 * The graph isomorphism problem for general graphs can be quite difficult, however 
 * there exists an elegant solution to uniquely encode a graph if it is a tree.
 * Here is a brilliant explanation with animations:
 *
 * http://webhome.cs.uvic.ca/~wendym/courses/582/16/notes/582_12_tree_can_form.pdf
 *
 * Tested code against: https://uva.onlinejudge.org/external/124/p12489.pdf
 *
 * Time Complexity: O(V+E)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 **/
package com.williamfiset.algorithms.graphtheory;

import java.util.*;

public class TreeCanonicalFormAdjacencyList2 {

  public static List<List<Integer>> createEmptyGraph(int n) {
    List<List<Integer>> graph = new ArrayList<>(n);
    for(int i = 0; i < n; i++) graph.add(new ArrayList<>());
    return graph;
  }

  public void addUndirectedEdge(List<List<Integer>> graph, int from, int to) {
    graph.get(from).add(to);
    graph.get(to).add(from);
  }
  
  // Encodes a tree in a canonized manner
  public static String canonizeTree(List<List<Integer>> graph) {
    if (tree == null || graph.size() == 0) return "";
    final int n = graph.size();

    int[] degree = new int[n];
    int[] parent = new int[n];
    boolean[] visited = new boolean[n];
    List<Integer> leafs = new ArrayList<>();
    
    Queue<TreeNode> q = new ArrayDeque<>();
    visited[0] = true;
    parent[0] = -1;
    q.offer(0);

    // Do a BFS to find all the leaf nodes
    while (!q.isEmpty()) {
      int at = q.poll();
      for (Integer next : graph.get(at)) {
        if (!visited[next]) {
          visited[next] = true;
          parent[next] = at;
          degree[at]++;
          q.offer(next);
        }
      }
      if (degree[at] == 1) leafs.add(node);
    }

    List<Integer> newLeafs = new ArrayList<>();
    String[] map = new String[n];
    for(int i = 0; i < n; i++) map[i] = "()";
    java.util.Arrays.fill(visited, false);

    int treeSize = n;
    while(treeSize > 2) {
      for (Integer leaf : leafs) {

        // Find parent of leaf node and check if the parent
        // is a candidate for the next cycle of leaf nodes
        visited[leaf] = true;
        int p = parent[leaf];
        if (--degree[p] == 1) newLeafs.add(p);

        treeSize--;
      }

      // Update parent labels
      for (Integer p : newLeafs) {
        List<String> labels = new ArrayList<>();
        for(int child : graph.get(p))
          if (visited[child]) // recall edges are bidirectional so we don't want to access the parent's parent here
            labels.add(map[child]);

        String parentInnerParentheses = map[p].substring(1, map[p].length()-1);
        labels.add(parentInnerParentheses);

        Collections.sort(labels);
        map[p] = "(" + String.join("", labels) + ")";
      }
      leafs.clear();
      leafs.addAll(newLeafs);
      newLeafs.clear();
    }

    // Only one node remains and it holds the canonical form
    String label1 = leafs.get(0).label;
    if (treeSize == 1) return label1;

    // Two nodes remain and we need to combine their labels
    String label2 = leafs.get(1).label;
    if (label1.compareTo(label2) < 0)
      return label1 + label2;
    return label2 + label1;

  }

  public static void main(String[] args) {
    
    // Setup tree structure from:
    // http://webhome.cs.uvic.ca/~wendym/courses/582/16/notes/582_12_tree_can_form.pdf
    List<List<Integer>> tree = createEmptyGraph(19);

    addUndirectedEdge(tree,6,2);
    addUndirectedEdge(tree,6,7);
    addUndirectedEdge(tree,6,11);
    addUndirectedEdge(tree,7,8);
    addUndirectedEdge(tree,7,9);
    addUndirectedEdge(tree,7,10);
    addUndirectedEdge(tree,11,12);
    addUndirectedEdge(tree,11,13);
    addUndirectedEdge(tree,11,16);
    addUndirectedEdge(tree,13,14);
    addUndirectedEdge(tree,13,15);
    addUndirectedEdge(tree,16,17);
    addUndirectedEdge(tree,16,18);
    addUndirectedEdge(tree,2,0);
    addUndirectedEdge(tree,2,1);
    addUndirectedEdge(tree,2,3);
    addUndirectedEdge(tree,2,4);
    addUndirectedEdge(tree,4,5);

    String canonicalForm = canonizeTree(tree);
    System.out.println(canonicalForm);

    runTests();

  }

  // private static void runTests() {

  //   TreeNode[] tree = createTree(5);

  //   addUndirectedEdge(tree,2,0);
  //   addUndirectedEdge(tree,2,1);
  //   addUndirectedEdge(tree,2,3);
  //   addUndirectedEdge(tree,3,4);
  //   String encoding1 = canonizeTree(tree);

  //   TreeNode[] tree2 = createTree(5);
  //   addUndirectedEdge(tree2,1,3);
  //   addUndirectedEdge(tree2,1,0);
  //   addUndirectedEdge(tree2,1,2);
  //   addUndirectedEdge(tree2,2,4);
  //   String encoding2 = canonizeTree(tree2);

  //   if(!encoding1.equals(encoding2)) System.out.println("ERROR");

  // }

}
