package javatests.com.williamfiset.algorithms.graphtheory;

import static com.google.common.truth.Truth.assertThat;
import static com.williamfiset.algorithms.graphtheory.BreadthFirstSearchAdjacencyListIterative.Edge;

import com.williamfiset.algorithms.graphtheory.BreadthFirstSearchAdjacencyListIterative;
import java.util.*;
import org.junit.Before;
import org.junit.Test;

public class BreadthFirstSearchAdjacencyListIterativeTest {

  BreadthFirstSearchAdjacencyListIterative solver;

  @Before
  public void setup() {
    solver = null;
  }  

  @Test(expected=IllegalArgumentException.class)
  public void testNullGraphInput() {
    new BreadthFirstSearchAdjacencyListIterative(null);
  }

  @Test
  public void testSingletonGraph() {
    int n = 1;
    List<List<Edge>> graph = BreadthFirstSearchAdjacencyListIterative.createEmptyGraph(n);

    solver = new BreadthFirstSearchAdjacencyListIterative(graph);
    List<Integer> path = solver.reconstructPath(0, 0);
    List<Integer> expected = new ArrayList<>();
    expected.add(0);
    assertThat(path).isEqualTo(expected);
  }

  @Test
  public void testTwoNodeGraph() {
    // TODO(williamfiset): Write test.
  }

  @Test 
  public void testThreeNodeGraph() {
    // TODO(williamfiset): Write test.
  }

  @Test
  public void testShortestPathAgainstBellmanFord() {
    // TODO(williamfiset): Write test.
  }

  @Test
  public void testDepthAgainstFloydWarshall() {
    // TODO(williamfiset): Write test.
  }

  @Test
  public void testPathBetweenComponents() {
    // TODO(williamfiset): Write test.
  }

}














