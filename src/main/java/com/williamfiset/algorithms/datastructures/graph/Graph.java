/**
 * 
 *
 * @author Sebaztian Öjebrant
 */

package com.williamfiset.algorithms.datastructures.graph;

import java.util.ArrayList;
import java.util.List;

public class Graph {
  
  private List<Node> graph;
  
  /** 
  * An edge class that represents a directed edge between two nodes
  */
  public static class Edge {
	int from;
	int to;
	
	/**
    * Initiates a single edge between the two nodes specified
	*
	* @param from - if of the node the edge comes from
	* @param to - The id of the the edge comes to
    */
	public Edge(int from, int to) {
      this.from = from;
      this.to = to;
    }
	  
  }
  
  /**
  * A node class that represents a node with a specified id
  * Holds a list of all edges connected to the node
  *
  */
  public static class Node {
    int id;
    List<Edge> edges;

	/**
	* Initiates a single node with a specified id
	*
	* @param id - The id of the node initiated
    */
    public Node(int id) {
      this.id = id;
    }
	
	/**
	* Creates a new instance of Edge towards the node specified
	* Also creates an additional instance of Edge going from the specified node
	* which gets added to the specified node.
	*
	* Two Edge instances are added to create a two way path between the nodes
	*
	* @param to - The Node instance of the edge should come to
    */
	public void addNewEdge(Node to) {
		Edge edgeTo = new Edge(this.id, to.id);
		Edge edgeFrom = new Edge(to.id, this.id);
		
		addEdge(edgeTo);
		to.addEdge(edgeFrom);
	}
	
	/**
	* Adds a specified edge to the list of edges
	*
	* @param edge - The instance of Edge to add to list of edges
    */
	private void addEdge(Edge edge) {
		this.edges.add(edge);
	}
	
	/**
	* Returns the list of edges
    */
	private List<Edge> getEdges() {
		return edges;
	}
  }
  
  /**
  * Creates a new Graph instance
  */
  public Graph(){
	  
	  
  }
  
  /**
  * Returns the list of nodes
  */
  public List<Node> getGraph(){  
	return graph;
	  
  }
  
  /**
  * Returns the list of edges for a specific node
  * 
  * @param id - The id of the node to get the edges for
  */
  public List<Edge> getEdges(int id){  
	return graph.get(id).getEdges();
	  
  }
  
  /**
  * Adds a node to the graph
  * 
  * @param id - The id of the node to add
  */
  public void addNode(int id){  
	Node node = new Node(id);
	
	// Add the new node to the list at the index of the id of the node
	graph.add(id, node);
	  
  }
  
  /**
  * Adds an edge between the two nodes specified
  * 
  * @param id1 - The id of the first node
  * @param id2 - The id of the second node
  */
  public void addEdge(int id1, int id2){  
	Node node1 = graph.get(id1);
	Node node2 = graph.get(id2);
	
	// Add the new edge between the two nodes
	node1.addNewEdge(node2);
	  
  }

}