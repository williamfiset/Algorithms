import static com.google.common.truth.Truth.assertThat;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import org.junit.*;

public class TwoSatSolverAdjacencyListTest {

  // Initialize graph with 'n' nodes.
  public static List<List<Integer>> createGraph(int n) {
    List<List<Integer>> graph = new ArrayList<>(2*n);
    for(int i = 0; i < 2*n; i++) graph.add(new ArrayList<>());
    return graph;
  }

  @Test
  public void testSingleClauseSatisfiableCase() {
    int n = 1;
    List<List<Integer>> graph = createGraph(n);
    // Add clause: (p or ~p)
    TwoSatSolverAdjacencyList.addOrClause(graph, 0, 0 ^ 1);
    TwoSatSolverAdjacencyList solver = new TwoSatSolverAdjacencyList(graph);
    assertThat(solver.isSatisfiable()).isTrue();
  }

}









