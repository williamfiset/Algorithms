/**
 * This file shows you how to determine if a graph is bipartite or not.
 * This can be achieved in linear time by coloring the visited nodes.
 *
 * Time Complexity: O(V + E)
 * 
 * @author William Fiset, william.alexandre.fiset@gmail.com
 **/

import java.util.*;

class Edge {
  int from, to;
  public Edge(int from, int to) {
    this.from = from;
    this.to = to;
  }
}

public class BipartiteGraphCheckAdjacencyList {

  private static final int BLACK = 0, RED = 1, UNVISITED = 2;

  // Checks whether a certain graph represented as a adjacency list can
  // be setup as a bipartite graph. If it is bipartite, this method returns
  // an array of colors indicating to which group each node belongs to or
  // null if the graph is not bipartite. Note that because the entire graph 
  // is not bipartite does not mean that the individual connected components
  // of the graph cannot be disjointly bipartite themselves.
  public static int[] isBipartite(Map <Integer,List<Edge>> graph, int n) {
    
    // Graph cannot be bipartite if there is only one node
    if (n <= 1) return null;

    // The graph must be connected and undirected otherwise it is not bipartite
    if (graph.size() != n) return null;

    int[] colors = new int[n];
    java.util.Arrays.fill(colors, UNVISITED);
    
    int nodesVisited = colorGraph(0, BLACK, colors, graph);

    // This graph is not bipartite
    if (nodesVisited == -1) return null;

    // Make sure we were able to visit all the nodes in
    // the graph, otherwise it is not bipartite
    if (nodesVisited != n) return null;
    return colors;
    
  }

  // Do a depth first search coloring the nodes of the graph as we go.
  // This method returns the count of the number of nodes visited while
  // coloring the graph or -1 if this graph is not bipartite.
  private static int colorGraph(int i, int color, int[] colors, Map<Integer,List<Edge>> graph) {
    
    // This node is already colored the correct color
    if (colors[i] == color) return 0;

    // Color this node
    colors[i] = color;

    // Swap colors. A less explicit way of swapping colors
    // is to say: nextColor = 1 - color; to flip the parity
    int nextColor = (color == BLACK) ? RED : BLACK;

    List <Edge> edges = graph.get(i);
    int visitCount = 1;

    // Do a depth first search to explore all nodes which have
    // not already been colored with the next color. This includes
    // all unvisited nodes and also nodes of the current color because
    // we want to check for a contradiction if one exists.
    for(Edge edge : edges) {

      // Contradiction found. In a bipartite graph no two
      // nodes of the same color can be next to each other!
      if (colors[edge.to] == color) return -1;
      
      // Attempt to color the rest of the graph.
      int count = colorGraph(edge.to, nextColor, colors, graph);

      // If a contradiction is found propagate return -1
      // otherwise keep track of the number of visited nodes
      if (count == -1) return -1;
      else visitCount += count;
      
    }

    return visitCount;

  }

  // Examples
  public static void main(String[] args) {
    
    // Singleton (not bipartite)
    Map<Integer,List<Edge>> graph = new HashMap<>();
    addUndirectedEdge(graph,0,0);
    displayGraph(graph, 1);

    // Prints:
    // Graph has 1 node(s) and the following edges:
    // 0 -> 0
    // 0 -> 0
    // This graph is bipartite: false

    // Two nodes one edge between them (bipartite)
    graph.clear();
    addUndirectedEdge(graph, 0, 1);
    displayGraph(graph, 2);

    // Prints:
    // Graph has 2 node(s) and the following edges:
    // 0 -> 1
    // 1 -> 0
    // This graph is bipartite: true

    // Triangle graph (not bipartite)
    graph.clear();
    addUndirectedEdge(graph, 0, 1);
    addUndirectedEdge(graph, 1, 2);
    addUndirectedEdge(graph, 2, 0);
    displayGraph(graph, 3);

    // Prints:
    // Graph has 3 node(s) and the following edges:
    // 0 -> 1
    // 0 -> 2
    // 1 -> 0
    // 1 -> 2
    // 2 -> 1
    // 2 -> 0
    // This graph is bipartite: false

    // Disjoint graph is bipartite connected components (altogether not bipartite)
    graph.clear();
    addUndirectedEdge(graph, 0, 1);
    addUndirectedEdge(graph, 2, 3);
    displayGraph(graph, 4);

    // Prints:
    // Graph has 4 node(s) and the following edges:
    // 0 -> 1
    // 1 -> 0
    // 2 -> 3
    // 3 -> 2
    // This graph is bipartite: false

    // Square graph (bipartite)
    graph.clear();
    addUndirectedEdge(graph,0,1);
    addUndirectedEdge(graph,1,2);
    addUndirectedEdge(graph,2,3);
    addUndirectedEdge(graph,3,0);
    displayGraph(graph, 4);

    // Prints:
    // Graph has 4 node(s) and the following edges:
    // 0 -> 1
    // 0 -> 3
    // 1 -> 0
    // 1 -> 2
    // 2 -> 1
    // 2 -> 3
    // 3 -> 2
    // 3 -> 0
    // This graph is bipartite: true

    // Square graph with additional edge (not bipartite)
    graph.clear();
    addUndirectedEdge(graph,0,1);
    addUndirectedEdge(graph,1,2);
    addUndirectedEdge(graph,2,3);
    addUndirectedEdge(graph,3,0);
    addUndirectedEdge(graph,0,2);
    displayGraph(graph, 4);

    // Prints:
    // Graph has 4 node(s) and the following edges:
    // 0 -> 1
    // 0 -> 3
    // 0 -> 2
    // 1 -> 0
    // 1 -> 2
    // 2 -> 1
    // 2 -> 3
    // 2 -> 0
    // 3 -> 2
    // 3 -> 0
    // This graph is bipartite: false

  }

  // Helper method to setup graph
  private static void addUndirectedEdge( Map <Integer, List <Edge>> graph, int from, int to) {
    List <Edge> list = graph.get(from);
    if (list == null) {
      list = new ArrayList <Edge>();
      graph.put(from, list);
    }
    list.add(new Edge(from, to));
    list = graph.get(to);
    if (list == null) {
      list = new ArrayList <Edge>();
      graph.put(to, list);
    }
    list.add(new Edge(to, from));
  }

  private static void displayGraph(Map<Integer,List<Edge>> graph, int n) {
    System.out.println("Graph has " + n + " node(s) and the following edges:");
    for (int i = 0; i < n; i++) {
      List<Edge> edges = graph.get(i);
      if (edges != null) {
        for (Edge e : edges ) {
          System.out.println(e.from + " -> " + e.to);
        }
      }
    }
    System.out.println("This graph is bipartite: " + (isBipartite(graph, n)!=null) );
    System.out.println();
  }

}






