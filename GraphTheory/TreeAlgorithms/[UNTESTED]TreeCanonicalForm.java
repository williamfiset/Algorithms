/**
 * The graph isomorphism problem for general graphs can be quite difficult, however 
 * there exists an elegant solution to uniquely encode a graph if it is a tree.
 * Here is a brilliant explanation with animations:
 *
 * http://webhome.cs.uvic.ca/~wendym/courses/582/16/notes/582_12_tree_can_form.pdf
 *
 * Time Complexity: O(V^2)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 **/

import java.util.*;

class TreeNode {
  
  static int ID = 0;
  
  int id = ID++;
  String label = "()";
  List <TreeNode> neighbors = new ArrayList<>();

  // Add an undirected link between two nodes
  public void connect(TreeNode node) {
    neighbors.add(node);
    node.neighbors.add(this);
  }

  @Override public int hashCode() { return id; }
  @Override public String toString() { return "NODE: " + id + " LABEL: " + label; }

}

public class TreeCanonicalForm {

  // Encodes a tree in a canonized manner in O(V^2) time
  // A faster variant without using strings can do O(V) time
  public static String canonizeTree(TreeNode node) {
    
    if (node == null) return "";

    int treeSize = 0;
    List <TreeNode> leafs = new ArrayList<>();
    
    // A node has been visited if its value in the map 
    // is equal to the current value of the VISITED_TOKEN
    int VISITED_TOKEN = 1;
    Map <Integer, Integer> visited = new HashMap<>();
    visited.put(node.id, VISITED_TOKEN);
    
    // Do a BFS to find all the leaf nodes
    ArrayDeque <TreeNode> q = new ArrayDeque<>();
    q.offer(node);

    while (!q.isEmpty()) {
      
      treeSize++;
      node = q.poll();
      List <TreeNode> neighbors = node.neighbors;

      // The current node is a leaf node
      if (neighbors.size() == 1) leafs.add(node);

      for (TreeNode neighbor : neighbors) {
        if (!visited.containsKey(neighbor.id)) {
          visited.put(neighbor.id, VISITED_TOKEN);
          q.offer(neighbor);
        }
      }

    }

    VISITED_TOKEN++;
    List <TreeNode> newLeafs = new ArrayList<>();
    Map <TreeNode, List<String>> map = new HashMap<>();

    while(treeSize > 2) {

      for (TreeNode leaf : leafs) {

        // Find parent of leaf node and check if the parent
        // is a candidate for the next cycle of leaf nodes
        TreeNode parent = findParent(leaf, visited, VISITED_TOKEN);
        visited.put(leaf.id, VISITED_TOKEN);
        boolean parentWillBecomeLeaf = findParent(parent, visited, VISITED_TOKEN) != null;

        // This parent node is the next level leaf
        if (parentWillBecomeLeaf) newLeafs.add(parent);

        // Update labels associated with parent
        List <String> labels = map.get(parent);
        if (labels == null) { labels = new ArrayList<>(); map.put(parent, labels); }
        labels.add(leaf.label);

        treeSize--;

      }

      // Update parent labels
      for (TreeNode parent : map.keySet()) {

        List <String> labels = map.get(parent);
        String parentInnerParentheses = parent.label.substring(1,parent.label.length()-1);
        labels.add(parentInnerParentheses);

        Collections.sort(labels);
        String newLabel = "(" + String.join("", labels) + ")";
        parent.label = newLabel;

      }

      // Update the new set of leaf nodes
      leafs.clear();
      leafs.addAll(newLeafs);
      newLeafs.clear();
      map.clear();

    }

    // Only one node remains and it holds the canonical form
    String label1 = leafs.get(0).label;
    if (treeSize == 1) return label1;

    // Two nodes remain and we need to combine their labels
    String label2 = leafs.get(1).label;
    if (label1.compareTo(label2) < 0)
      return label1 + "" + label2;
    return label2 + "" + label1;

  }

  // Searches the unvisited neighbors of a node to find its unambiguous
  // parent. If no parent or more than one parent exists null is returned.
  private static TreeNode findParent( TreeNode node, Map <Integer, Integer> visited, int VISITED_TOKEN) {

    if (node == null) return null;
    TreeNode parent = null;

    for (TreeNode neighbor : node.neighbors) {
      boolean visitedNode = (visited.get(neighbor.id) == VISITED_TOKEN);
      if (!visitedNode) {
        if (parent != null) return null;
        parent = neighbor;
      }
    }

    return parent;

  }

  public static void main(String[] args) {
    

    // Setup tree structure from:
    // http://webhome.cs.uvic.ca/~wendym/courses/582/16/notes/582_12_tree_can_form.pdf
    TreeNode[] tree = new TreeNode[19];
    for ( int i = 0; i < tree.length; i++)
      tree[i] = new TreeNode();

    tree[6].connect(tree[2]);
    tree[6].connect(tree[7]);
    tree[6].connect(tree[11]);

    tree[7].connect(tree[8]);
    tree[7].connect(tree[9]);
    tree[7].connect(tree[10]);

    tree[11].connect(tree[12]);
    tree[11].connect(tree[13]);
    tree[11].connect(tree[16]);

    tree[13].connect(tree[14]);
    tree[13].connect(tree[15]);

    tree[16].connect(tree[17]);
    tree[16].connect(tree[18]);

    tree[2].connect(tree[0]);
    tree[2].connect(tree[1]);
    tree[2].connect(tree[3]);
    tree[2].connect(tree[4]);

    tree[4].connect(tree[5]);

    String canonicalForm = canonizeTree(tree[0]);
    System.out.println(canonicalForm);

    runTests();

  }

  private static void runTests() {

    TreeNode[] tree = new TreeNode[5];
    
    for ( int i = 0; i < tree.length; i++) tree[i] = new TreeNode();
    tree[2].connect(tree[0]);
    tree[2].connect(tree[1]);
    tree[2].connect(tree[3]);
    tree[3].connect(tree[4]);
    String encoding1 = canonizeTree(tree[0]);

    for ( int i = 0; i < tree.length; i++) tree[i] = new TreeNode();
    tree[1].connect(tree[3]);
    tree[1].connect(tree[0]);
    tree[1].connect(tree[2]);
    tree[2].connect(tree[4]);
    String encoding2 = canonizeTree(tree[2]);

    if(!encoding1.equals(encoding2)) System.out.println("ERROR");

  }

}




















