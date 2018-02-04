import static com.google.common.truth.Truth.assertThat;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import org.junit.*;

public class TarjanSccSolverAdjacencyListTest {

  // Initialize graph with 'n' nodes.
  public static List<List<Integer>> createGraph(int n) {
    List<List<Integer>> graph = new ArrayList<>();
    for(int i = 0; i < n; i++) graph.add(new ArrayList<>());
    return graph;
  }

  // Add directed edge to graph.
  public static void addEdge(List<List<Integer>> graph, int from, int to) {
    graph.get(from).add(to);
  }

  @Test
  public void singletonCase() {
    int n = 1;
    List<List<Integer>> g = createGraph(n);
    
    TarjanSccSolverAdjacencyList solver = new TarjanSccSolverAdjacencyList(g);
    solver.solve();

    int[] actual = solver.getSccs();
    int[] expected = new int[n];
    assertThat(actual).isEqualTo(expected);
    assertThat(solver.sccCount()).isEqualTo(1);
  }

}
