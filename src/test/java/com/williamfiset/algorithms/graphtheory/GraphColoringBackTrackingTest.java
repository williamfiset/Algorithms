package com.williamfiset.algorithms.graphtheory;
import com.williamfiset.algorithms.datastructures.graph.Graph;
import com.williamfiset.algorithms.datastructures.graph.Graph.Node;
import com.williamfiset.algorithms.datastructures.graph.Graph.Edge;
import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.Before;

public class GraphColoringBackTrackingTest {
    private Graph graph = null;
	

  @Before
  public void setup() {
    graph = new Graph();
  }
	
  @Test
  public void createGraphTest() {
    graph = new Graph();
    assertThat(graph.getGraph()).isNotNull();
	assertThat(graph.getGraph()).isEmpty();
  }
  
  @Test
  public void addNodesTest() {
	
    graph.addNode(1);
	graph.addNode(3);

    assertThat(graph.getNodes().size() == 2);
  }

  private void addSimpleData(){
	graph.addNode(1);
	graph.addNode(2);
	graph.addNode(3);
	
	graph.addEdge(1,2);
	graph.addEdge(1,3);
	graph.addEdge(2,3);
	graph.addEdge(2,3); 
	  
  }

  private boolean allNodesColorDifferent(Graph g) {

    ArrayList<Edge> edgesList = g.getEdges();
    for (Edge e: edgesList) {
        if (g.getNode(e.getFrom()).color == g.getNode(e.getTo()).color)
            return false;
    }
    return true;
  }

  @Test
  public void simpleTest1() {
	
    addSimpleData();
	
    GraphColoringBackTracking bt = new GraphColoringBackTracking();
	assertTrue(bt.colorGraph(graph, 4));
	assertFalse(bt.colorGraph(graph, 2));
  }

  @Test
  public void simpleTest2() {
	
    addSimpleData();
	
    GraphColoringBackTracking bt = new GraphColoringBackTracking();
    bt.colorGraph(graph, 4);
    assertTrue(allNodesColorDifferent(graph));
    }
}
