/**
 * An implementation of canonizing a tree. 
 * Reference: http://webhome.cs.uvic.ca/~wendym/courses/582/16/notes/582_12_tree_can_form.pdf
 *
 * Time Complexity: O(V+E)
 * 
 * @author William Fiset, william.alexandre.fiset@gmail.com
 **/

import java.util.*;

class TreeNode {
  String label;
  int id, degree;
  TreeNode parent;
  List <TreeNode> children;
  public TreeNode(int id) {
    label = "()";
    this.id = id;
    children = new LinkedList<>();
  }
  public void addChild(TreeNode child) {
    children.add(child);
    degree++;
  }
  public boolean leaf() {
    return degree == 0;
  }
  @Override public String toString() {
    return String.valueOf(id);
  }
}

class TreeCanonicalForm {

  // Given a rooted tree and the number of nodes in the tree
  // return the unique canonized representation of this tree.
  public static String canonize(TreeNode root, int n) {
    
    Map <TreeNode, List<TreeNode>> leafGroups = new HashMap<>();
    List <String> labels = new ArrayList<>();
    List <TreeNode> leaves = new ArrayList<>();
    findLeafNodes(leaves, root);

    while(n > 1) {

      // Group all leaf nodes belonging to the same parent together
      for (TreeNode leaf : leaves) {
        List <TreeNode> group = leafGroups.get(leaf.parent);
        if (group == null) {
          group = new ArrayList<>();
          leafGroups.put(leaf.parent, group);
        }
        group.add(leaf);
        leaf.parent.degree--;
        n--;
      }

      // Update all parent labels
      for (TreeNode parent : leafGroups.keySet()) {
        
        String parentLabel = parent.label.substring(1, parent.label.length()-1);
        labels.add(parentLabel);

        for (TreeNode leaf : leafGroups.get(parent))
          labels.add(leaf.label);

        Collections.sort(labels);
        String newLabel = "(" + String.join("", labels) + ")";
        parent.label = newLabel;
        labels.clear();

      }

      leaves.clear();
      
      // Keep a subset of the parent nodes who are the
      // true new unambiguous leaf nodes
      for (TreeNode parent : leafGroups.keySet())
        if (parent.leaf())
          leaves.add(parent);

      leafGroups.clear();

    }

    // Unique last node
    String label1 = leaves.get(0).label;
    if (n == 1) return label1;

    // Sort last two node labels
    String label2 = leaves.get(1).label;
    return (label1.compareTo(label2) < 0) ? label1 + label2 : label2 + label1;

  }

  private static void findLeafNodes(List<TreeNode> leaves, TreeNode node) {
    if (node.leaf()) {
      leaves.add(node);
      node.degree--;
    } else {
      for (TreeNode child : node.children) {
        findLeafNodes(leaves, child);
      }
    }
  }

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

  // Create a graph as a adjacency list
  public static List<List<Integer>> createGraph(int n) {
    List<List<Integer>> graph = new ArrayList<>(n);
    for (int i = 0; i < n; i++) graph.add(new LinkedList<>());
    return graph;
  }

  // Add an undirected edge between node 'from' and 'to'
  public static void addUndirectedEdge(List<List<Integer>> graph, int node1, int node2) {
    graph.get(node1).add(node2);
    graph.get(node2).add(node1);
  }


    /********** TESTING ************/


  // Example from slides:
  private static void exampleFromSlides() {

    int n = 19;
    List<List<Integer>> graph = createGraph(n);

    addUndirectedEdge(graph,6,2);
    addUndirectedEdge(graph,6,7);
    addUndirectedEdge(graph,6,11);
    addUndirectedEdge(graph,7,8);
    addUndirectedEdge(graph,7,9);
    addUndirectedEdge(graph,7,10);
    addUndirectedEdge(graph,11,12);
    addUndirectedEdge(graph,11,13);
    addUndirectedEdge(graph,11,16);
    addUndirectedEdge(graph,13,14);
    addUndirectedEdge(graph,13,15);
    addUndirectedEdge(graph,16,17);
    addUndirectedEdge(graph,16,18);
    addUndirectedEdge(graph,2,0);
    addUndirectedEdge(graph,2,1);
    addUndirectedEdge(graph,2,3);
    addUndirectedEdge(graph,2,4);
    addUndirectedEdge(graph,4,5);

    // Try rooting the tree at all possible positions
    for (int rootID = 0; rootID < n; rootID++) {
      TreeNode root = rootTree(graph, rootID);
      String canonizedForm = canonize(root, n);
      System.out.println("Canonized form with root at " + rootID + ": " + canonizedForm);
    }

  }

  // Three node tree example
  private static void threeNodeExample() {

    int n = 3;
    List<List<Integer>> graph = createGraph(n);
    addUndirectedEdge(graph,0,1);
    addUndirectedEdge(graph,1,2);

    TreeNode root0 = rootTree(graph, 0);
    TreeNode root1 = rootTree(graph, 1);
    TreeNode root2 = rootTree(graph, 2);

    System.out.println("Canonized form with root at 0: " + canonize(root0, n));
    System.out.println("Canonized form with root at 1: " + canonize(root1, n));
    System.out.println("Canonized form with root at 2: " + canonize(root2, n));

  }

  // Four node tree example
  private static void fourNodeExample() {

    int n = 4;
    List<List<Integer>> graph = createGraph(n);
    addUndirectedEdge(graph,0,1);
    addUndirectedEdge(graph,1,2);
    addUndirectedEdge(graph,2,3);

    TreeNode root0 = rootTree(graph, 0);
    TreeNode root1 = rootTree(graph, 1);
    TreeNode root2 = rootTree(graph, 2);
    TreeNode root3 = rootTree(graph, 3);

    System.out.println("Canonized form with root at 0: " + canonize(root0, n));
    System.out.println("Canonized form with root at 1: " + canonize(root1, n));
    System.out.println("Canonized form with root at 2: " + canonize(root2, n));
    System.out.println("Canonized form with root at 3: " + canonize(root3, n));

  }

  public static void main(String[] args) {

    System.out.println("Example in email:");
    threeNodeExample();
    System.out.println();
    System.out.println("Example from slides:");
    exampleFromSlides();

  }

}












