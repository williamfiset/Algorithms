/** NOTE: This file is still in development! */
package com.williamfiset.algorithms.graphtheory;

import java.util.*;

public class TwoSatSolverAdjacencyList {

  private int n;
  private List<List<Integer>> graph;

  private boolean solved;
  private boolean isSatisfiable;
  private TarjanSccSolverAdjacencyList sccSolver;

  public TwoSatSolverAdjacencyList(List<List<Integer>> graph) {
    n = graph.size() / 2;
    sccSolver = new TarjanSccSolverAdjacencyList(graph);
  }

  // Returns true/false depending on whether this 2SAT problem is satisfiable
  public boolean isSatisfiable() {
    if (!solved) solve();
    return isSatisfiable;
  }

  public void solve() {
    if (solved) return;

    int[] sccs = sccSolver.getSccs();
    // System.out.println(Arrays.toString(sccs));

    // Assume that this 2SAT problem is satisfiable and try to
    // disprove it by looking at which SCCs p and ~p belong to.
    isSatisfiable = true;
    for (int i = 0; i < sccs.length; i += 2) {
      if (sccs[i] == sccs[i ^ 1]) {
        isSatisfiable = false;
        break;
      }
    }

    // Find a truth assignment.
    if (isSatisfiable) {
      // get topsort ordering
    }

    solved = true;
  }

  // Creates an implication graph.
  //
  // NOTE: In the implication graph node i should be stored in position 2i
  // and its negation in position 2i + 1. This is done so that we can quickly
  // access the negation of a node by doing an xor operation. For example:
  // Node 4's negation is 5 since 4 ⊕ 1 = 5 and 5's negation is 4 since 5 ⊕ 1 = 4.
  public static List<List<Integer>> createImplicationGraph(int n) {
    List<List<Integer>> graph = new ArrayList<>(2 * n);
    for (int i = 0; i < 2 * n; i++) graph.add(new ArrayList<>());
    return graph;
  }

  // Make sure 0 <= p, q < 2n
  public static void addXorClause(List<List<Integer>> graph, int p, int q) {
    // xor(p, q) = (p or q) and (~p or ~q)
    addOrClause(graph, p, q);
    addOrClause(graph, p ^ 1, q ^ 1);
  }

  // Add p or q to the graph where p, q come from the CNF
  // Make sure 0 <= p, q < 2n
  public static void addOrClause(List<List<Integer>> graph, int p, int q) {
    // Add the edges ~p -> q and ~q -> p to implication graph.
    // System.out.printf("p = %d, ~p = %d, q = %d, ~q = %d\n", p, p ^ 1, q, q ^ 1);
    // System.out.printf("%d -> %d\n", p^1, q);
    // System.out.printf("%d -> %d\n", q^1, p);
    graph.get(p ^ 1).add(q);
    graph.get(q ^ 1).add(p);
  }

  public static void main(String[] args) {

    int n = 2;
    List<List<Integer>> graph = createImplicationGraph(n);

    // addOrClause(graph, n, 0, negate(1, n));
    // addOrClause(graph, n, negate(0, n), 2);
    // addOrClause(graph, n, negate(1, n), negate(3, n));
    // addOrClause(graph, n, 3, 0);
    // addOrClause(graph, n, negate(2, n), 4);
    // addOrClause(graph, n, negate(1, n), 4);

    // addOrClause(graph, n, 0, 1^1);
    // addOrClause(graph, n, 0, 1);
    // addOrClause(graph, n, 0^1, 1);
    // System.out.println(graph);

    // TwoSatSolverAdjacencyList solver = new TwoSatSolverAdjacencyList(graph);
    // int[] sccs = solver.sccSolver.getSccs();
    // System.out.println(Arrays.toString(sccs));

  }
}
