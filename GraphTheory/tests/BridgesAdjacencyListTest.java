import static com.google.common.truth.Truth.assertThat;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import org.junit.*;

public class BridgesAdjacencyListTest {

  // Initialize graph with 'n' nodes.
  public static List<List<Integer>> createGraph(int n) {
    List<List<Integer>> graph = new ArrayList<>();
    for(int i = 0; i < n; i++) graph.add(new ArrayList<>());
    return graph;
  }

  // Add undirected edge to graph.
  public static void addEdge(List<List<Integer>> graph, int from, int to) {
    graph.get(from).add(to);
    graph.get(to).add(from);
  }

  // Every edge should be a bridge if the input a tree
  @Test
  public void testTreeCase() {

    int n = 12;
    List<List<Integer>> graph = createGraph(n);
    addEdge(graph, 1, 0);
    addEdge(graph, 0, 2);
    addEdge(graph, 2, 5);
    addEdge(graph, 5, 6);
    addEdge(graph, 5, 11);
    addEdge(graph, 5, 4);
    addEdge(graph, 4, 10);
    addEdge(graph, 4, 3);
    addEdge(graph, 3, 7);
    addEdge(graph, 7, 8);
    addEdge(graph, 7, 9);

    BridgesAdjacencyList solver = new BridgesAdjacencyList(graph, n);
    List<Integer> bridges = solver.findBridges();
    List<Pair> actual = new ArrayList<>();

    for (int i = 0; i < bridges.size(); i += 2) {
      int node1 = bridges.get(i);
      int node2 = bridges.get(i+1);
      Pair pair;
      if (node1 < node2) {
        pair = Pair.of(node1, node2);
      } else {
        pair = Pair.of(node2, node1);
      }
      actual.add(pair);
    }

    List<Pair> expected = ImmutableList.of(
      Pair.of(0, 1),
      Pair.of(0, 2),
      Pair.of(2, 5),
      Pair.of(5, 6),
      Pair.of(5, 11),
      Pair.of(4, 5),
      Pair.of(4, 10),
      Pair.of(3, 4),
      Pair.of(3, 7),
      Pair.of(7, 8),
      Pair.of(7, 9)
    );

    assertThat(actual).containsExactlyElementsIn(expected);
  }

  // Every edge should be a bridge if the input a tree
  @Test
  public void graphWithCyclesTest() {

    int n = 12;
    List<List<Integer>> graph = createGraph(n);
    addEdge(graph, 1, 0);
    addEdge(graph, 0, 2);
    addEdge(graph, 3, 1);
    addEdge(graph, 2, 5);
    addEdge(graph, 5, 6);
    addEdge(graph, 5, 11);
    addEdge(graph, 5, 4);
    addEdge(graph, 4, 10);
    addEdge(graph, 4, 3);
    addEdge(graph, 3, 7);
    addEdge(graph, 7, 8);
    addEdge(graph, 7, 9);
    addEdge(graph, 11, 6);

    BridgesAdjacencyList solver = new BridgesAdjacencyList(graph, n);
    List<Integer> bridges = solver.findBridges();
    List<Pair> actual = new ArrayList<>();

    for (int i = 0; i < bridges.size(); i += 2) {
      int node1 = bridges.get(i);
      int node2 = bridges.get(i+1);
      Pair pair;
      if (node1 < node2) {
        pair = Pair.of(node1, node2);
      } else {
        pair = Pair.of(node2, node1);
      }
      actual.add(pair);
    }

    List<Pair> expected = ImmutableList.of(
      Pair.of(3, 7),
      Pair.of(7, 8),
      Pair.of(7, 9),
      Pair.of(4, 10)
    );

    assertThat(actual).containsExactlyElementsIn(expected);
  }

}