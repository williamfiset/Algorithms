package com.williamfiset.algorithms.datastructures.graph;

import static com.google.common.truth.Truth.assertThat;
import org.junit.Test;
import org.junit.Before;

import java.util.*;

public class GraphTest {
	
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
	assertThat(graph.getGraph().get(1).id).isEqualTo(1);
	assertThat(graph.getGraph().size()).isEqualTo(1);
	
	graph.addNode(3);
	assertThat(graph.getGraph().get(3).id).isEqualTo(3);
	assertThat(graph.getGraph().size()).isEqualTo(2);
	
  }
  /**
  *	A function to create a simple graph used for testing
  */
  private void addSimpleData(){
	graph.addNode(1);
	graph.addNode(2);
	graph.addNode(3);
	
	graph.addEdge(1,2);
	graph.addEdge(1,3);
	graph.addEdge(2,3);
	graph.addEdge(2,3); 
	  
  }
  
  @Test
  public void removeNodeTest() {
	
    addSimpleData();
	
	graph.removeNode(1);
	assertThat(graph.getGraph().size()).isEqualTo(2);
	assertThat(graph.getGraph().get(2).getEdges().size()).isEqualTo(2);
	assertThat(graph.getGraph().get(3).getEdges().size()).isEqualTo(2);
	
	graph.removeNode(2);
	assertThat(graph.getGraph().size()).isEqualTo(1);
	assertThat(graph.getGraph().get(3).getEdges()).isEmpty();
	
  }
  
  @Test
  public void addEdgesTest() {
	
    addSimpleData();
	
	assertThat(graph.getGraph().get(1).getEdges().size()).isEqualTo(2);
	assertThat(graph.getGraph().get(2).getEdges().size()).isEqualTo(3);
	assertThat(graph.getGraph().get(3).getEdges().size()).isEqualTo(3);
	
  }
  
  @Test
  public void removeEdgesTest() {
	
    addSimpleData();
	
	graph.removeEdge(1,2);
	assertThat(graph.getGraph().get(1).getEdges().size()).isEqualTo(1);
	assertThat(graph.getGraph().get(2).getEdges().size()).isEqualTo(2);
	assertThat(graph.getGraph().get(3).getEdges().size()).isEqualTo(3);
	
	graph.removeEdge(1,3);
	assertThat(graph.getGraph().get(1).getEdges()).isEmpty();
	assertThat(graph.getGraph().get(2).getEdges().size()).isEqualTo(2);
	assertThat(graph.getGraph().get(3).getEdges().size()).isEqualTo(2);
	
	graph.removeEdge(2,3); // Should remove both duplicate edges
	assertThat(graph.getGraph().get(1).getEdges()).isEmpty();
	assertThat(graph.getGraph().get(2).getEdges()).isEmpty();
	assertThat(graph.getGraph().get(3).getEdges()).isEmpty();
	
  }
 

}