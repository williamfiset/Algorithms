/**
 * 
 *
 * @author Sebaztian Öjebrant
 */

package com.williamfiset.algorithms.datastructures.graph;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.List;
import java.util.HashMap;

public class Graph {
  
  private HashMap<Integer, Node> graph;

  private ArrayList<Node> nodes;
  private ArrayList<Edge> edges;

  /** 
  * An edge class that represents a directed edge between two nodes
  */
  public static class Edge {

	private Integer from;
	private Integer to;

	
	/**
    * Initiates a single edge between the two nodes specified
	*
	* @param from - if of the node the edge comes from
	* @param to - The id of the the edge comes to
    */
	public Edge(Integer from, Integer to) {
      this.from = from;
      this.to = to;
    }
    
	public Integer getFrom() {
		return from;
	}
	public Integer getTo() {
		return to;
	}
  
  /**
  * A node class that represents a node with a specified id
  * Holds a list of all edges connected to the node
  *
  */
  public static class Node {
    public Integer id;
    private List<Edge> edges;
    public int color;


	/**
	* Initiates a single node with a specified id
	*
	* @param id - The id of the node initiated
    */
    public Node(Integer id) {
      this.id = id;
	    edges = new ArrayList<Edge>();
	    this.color = -1;
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
	* Returns the list of edges
    */
	public List<Edge> getEdges() {
		return edges;
	}
	
	/**
	* Adds a specified edge to the list of edges
	*
	* @param edge - The instance of Edge to add to list of edges
    */
	private void addEdge(Edge edge) {
		edges.add(edge);
	}
	
	/**
	* Removes all edges connecting to a specified node
	* If there are multiple edges towards the same node they will all be removed
	*
	* @param id - The id of the node the edge connects to
    */
	public void removeEdge(Integer idTo) {
      ListIterator<Edge> iterator = edges.listIterator();
	  
	  while(iterator.hasNext()){
		if(iterator.next().to == idTo){
		  iterator.remove();
		}
	  }
	}
	
	/**
	* Removes all edges from the node
    */
	public void removeAllEdges(){
	  edges.clear();
	}
	
  }
  
  /**
  * Creates a new Graph instance
  */
  public Graph(){
	graph = new HashMap<>();
	nodes = new ArrayList<Node>();
	edges = new ArrayList<Edge>();
  }
  
  /**
  * Returns the nodes of the graph as a hashmap
  */
  public HashMap<Integer, Node> getGraph(){ 
	return this.graph;
	  
  }
  
  /**
  * Returns the node of the specified id
  * 
  * @param id - The id of the node to return
  */
  public Node getNode(Integer id){ 
    // Check if the node exists, if not throw an exception
	if(!graph.containsKey(id)){
		throw new IndexOutOfBoundsException();
	}
	
	return graph.get(id);
	  
  }
  
  /**
  * Adds a node to the graph
  * 
  * @param id - The id of the node to add
  */
  public void addNode(Integer id){  
	Node node = new Node(id);
	
	// Add the new node to the list
	graph.putIfAbsent(id, node);
	nodes.add(node);
	  
  }
  
  /**
  * Removes the node specified by the id
  * Alo removes all edges connected to the node
  * 
  * @param id - The id of the node to remove
  */
  public void removeNode(Integer id){
	// Check if the node exists, if not throw an exception
	if(!graph.containsKey(id)){
		throw new IndexOutOfBoundsException();
	}
	  
	Node node = getNode(id);
	List<Edge> edges = node.getEdges();
	
	// Itterate over all edges and remove the duplicate ones going to the node being removed
	for(Edge edge : edges){
	  Node tempNode = getNode(edge.to);
	  tempNode.removeEdge(id);
	}
	
	// Remove all edges from the node
	node.removeAllEdges();
	
	// Remove the node once done
	graph.remove(id);
	nodes.remove(node);
	  
  }
  
  /**
  * Adds an edge between the two nodes specified
  * 
  * @param id1 - The id of the first node
  * @param id2 - The id of the second node
  */
  public void addEdge(Integer id1, Integer id2){
    // Check if both nodes exists, if not throw an exception
	if(!graph.containsKey(id1) && !graph.containsKey(id2)){
		throw new IndexOutOfBoundsException();
	}
	
	Node node1 = graph.get(id1);
	Node node2 = graph.get(id2);
	
	// Add the new edge between the two nodes
	node1.addNewEdge(node2);
	edges.add(new Edge(id1, id2));
	edges.add(new Edge(id2, id1));

  }
  
  /**
  * Removes the edge from a specified node that connects the node to another specified one
  * 
  * @param idFrom - The id of the node the edge starts at
  * @param idTo - The id of the node the edge connects to
  */
  public void removeEdge(Integer idFrom, Integer idTo){
	// Check if both nodes exists, if not throw an exception
	if(!graph.containsKey(idFrom) && !graph.containsKey(idTo)){
		throw new IndexOutOfBoundsException();
	}
	
	Node nodeFrom = getNode(idFrom);
	Node nodeTo = getNode(idTo);
	
	//Removes both directions of the edge
	nodeFrom.removeEdge(idTo);
	nodeTo.removeEdge(idFrom);
	edges.remove(new Edge(idFrom, idTo));
	edges.remove(new Edge(idTo, idFrom));
	  
  }

  public ArrayList<Node> getNodes() {
	  return nodes;
  }

  public ArrayList<Edge> getEdges() {
	return edges;
  }
}