package com.williamfiset.algorithms.graphtheory;

import static com.google.common.truth.Truth.assertThat;

import java.util.*;
import org.junit.Before;
import org.junit.Test;

public class FloydWarshallSolverTest {

  static final double INF = Double.POSITIVE_INFINITY;
  static final double NEG_INF = Double.NEGATIVE_INFINITY;

  static double[][] matrix1, matrix2, matrix3;

  @Before
  public void setup() {
    matrix1 =
        new double[][] {
          {0, INF, INF, INF, INF},
          {1, 0, 7, INF, INF},
          {INF, 3, 0, INF, INF},
          {13, INF, 4, 0, INF},
          {INF, INF, 3, 0, 0}
        };
    matrix2 =
        new double[][] {
          {0, 3, 1, 8, INF},
          {2, 0, 9, 4, INF},
          {INF, INF, 0, INF, -2},
          {INF, INF, 1, 0, INF},
          {INF, INF, INF, 0, 0}
        };
    matrix3 =
        new double[][] {
          {0, 6, INF, 25, 3},
          {1, 0, 6, 1, 3},
          {INF, 1, 0, 2, 3},
          {4, 4, 4, 0, INF},
          {4, 3, 5, INF, 0}
        };
  }

  private static double[][] createMatrix(int n) {
    double[][] m = new double[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        m[i][j] = Double.POSITIVE_INFINITY;
        m[i][i] = 0;
      }
    }
    return m;
  }

  private static void addRandomEdges(double[][] matrix, int count, boolean allowNegativeEdges) {
    int n = matrix.length;
    while (count-- > 0) {
      int i = (int) (Math.random() * n);
      int j = (int) (Math.random() * n);
      if (i == j) continue;
      int v = (int) (Math.random() * 100);
      // Allow negative edges but only very rarely since even one
      // negative edge can start an avalanche of negative cycles.
      if (allowNegativeEdges) v = (Math.random() > 0.005) ? v : -v;
      matrix[i][j] = v;
    }
  }

  @Test
  public void testDirectedGraph() {
    FloydWarshallSolver solver = new FloydWarshallSolver(matrix1);
    double[][] soln = solver.getApspMatrix();

    assertThat(soln[0][0]).isEqualTo(0.0);
    assertThat(soln[1][0]).isEqualTo(1.0);
    assertThat(soln[1][1]).isEqualTo(0.0);
    assertThat(soln[1][2]).isEqualTo(7.0);
    assertThat(soln[2][0]).isEqualTo(4.0);
    assertThat(soln[2][1]).isEqualTo(3.0);
    assertThat(soln[2][2]).isEqualTo(0.0);
    assertThat(soln[3][0]).isEqualTo(8.0);
    assertThat(soln[3][1]).isEqualTo(7.0);
    assertThat(soln[3][2]).isEqualTo(4.0);
    assertThat(soln[3][3]).isEqualTo(0.0);
    assertThat(soln[4][0]).isEqualTo(7.0);
    assertThat(soln[4][1]).isEqualTo(6.0);
    assertThat(soln[4][2]).isEqualTo(3.0);
    assertThat(soln[4][3]).isEqualTo(0.0);
    assertThat(soln[4][4]).isEqualTo(0.0);
  }

  @Test
  public void testNegativeCycleGraph() {
    FloydWarshallSolver solver = new FloydWarshallSolver(matrix2);
    double[][] soln = solver.getApspMatrix();

    assertThat(soln[0][0]).isEqualTo(0.0);
    assertThat(soln[0][1]).isEqualTo(3.0);
    assertThat(soln[0][2]).isEqualTo(NEG_INF);
    assertThat(soln[0][3]).isEqualTo(NEG_INF);
    assertThat(soln[0][4]).isEqualTo(NEG_INF);
    assertThat(soln[1][0]).isEqualTo(2.0);
    assertThat(soln[1][1]).isEqualTo(0.0);
    assertThat(soln[1][2]).isEqualTo(NEG_INF);
    assertThat(soln[1][3]).isEqualTo(NEG_INF);
    assertThat(soln[1][4]).isEqualTo(NEG_INF);
    assertThat(soln[2][2]).isEqualTo(NEG_INF);
    assertThat(soln[2][3]).isEqualTo(NEG_INF);
    assertThat(soln[2][4]).isEqualTo(NEG_INF);
    assertThat(soln[3][2]).isEqualTo(NEG_INF);
    assertThat(soln[3][3]).isEqualTo(NEG_INF);
    assertThat(soln[3][4]).isEqualTo(NEG_INF);
    assertThat(soln[4][2]).isEqualTo(NEG_INF);
    assertThat(soln[4][3]).isEqualTo(NEG_INF);
    assertThat(soln[4][4]).isEqualTo(NEG_INF);
  }

  @Test
  public void testApspAgainstBellmanFord_nonNegativeEdgeWeights() {
    final int TRAILS = 10;
    for (int n = 2; n <= 25; n++) {
      for (int trail = 1; trail <= TRAILS; trail++) {
        double[][] m = createMatrix(n);
        int numRandomEdges = Math.max(1, (int) (Math.random() * n * n));
        addRandomEdges(m, numRandomEdges, false);
        double[][] fw = new FloydWarshallSolver(m).getApspMatrix();

        for (int s = 0; s < n; s++) {
          double[] bf = new BellmanFordAdjacencyMatrix(s, m).getShortestPaths();
          assertThat(bf).isEqualTo(fw[s]);
        }
      }
    }
  }

  @Test
  public void testApspAgainstBellmanFord_withNegativeEdgeWeights() {
    final int TRAILS = 10;
    for (int n = 2; n <= 25; n++) {
      for (int trail = 1; trail <= TRAILS; trail++) {

        double[][] m = createMatrix(n);
        int numRandomEdges = Math.max(1, (int) (Math.random() * n * n));
        addRandomEdges(m, numRandomEdges, true);
        double[][] fw = new FloydWarshallSolver(m).getApspMatrix();

        for (int s = 0; s < n; s++) {
          double[] bf = new BellmanFordAdjacencyMatrix(s, m).getShortestPaths();
          assertThat(bf).isEqualTo(fw[s]);
        }
      }
    }
  }

  // Tests for a mismatch in how both algorithms detect the existence of
  // a negative cycle on the shortest path from s -> e.
  @Test
  public void testPathReconstructionBellmanFord_nonNegativeEdgeWeights() {
    final int TRAILS = 50;
    for (int n = 2; n <= 25; n++) {
      for (int trail = 1; trail <= TRAILS; trail++) {

        double[][] m = createMatrix(n);
        int numRandomEdges = Math.max(1, (int) (Math.random() * n * n));
        addRandomEdges(m, numRandomEdges, true);
        FloydWarshallSolver fwSolver = new FloydWarshallSolver(m);
        fwSolver.solve();

        for (int s = 0; s < n; s++) {
          BellmanFordAdjacencyMatrix bfSolver = new BellmanFordAdjacencyMatrix(s, m);
          for (int e = 0; e < n; e++) {

            // Make sure that if 'fwp' returns null that 'bfp' also returns null or
            // that if 'fwp' is not null that 'bfp' is also not null.
            List<Integer> fwp = fwSolver.reconstructShortestPath(s, e);
            List<Integer> bfp = bfSolver.reconstructShortestPath(e);
            if ((fwp == null) ^ (bfp == null)) {
              org.junit.Assert.fail("Mismatch.");
            }
          }
        }
      }
    }
  }

  @Test
  public void testSimpleNegativeCycleDetection() {
    int n = 3, s = 0, e = 2;
    double[][] m = createMatrix(n);
    m[0][1] = 100;
    m[0][2] = 5;
    m[1][2] = 0;
    m[1][1] = -1; // negative self loop.
    FloydWarshallSolver fw = new FloydWarshallSolver(m);
    List<Integer> fwPath = fw.reconstructShortestPath(s, e);
    assertThat(fwPath).isNull();
  }

  @Test
  public void testNegativeCyclePropagation() {
    int n = 100, s = 0, e = n - 1;
    double[][] m = createMatrix(n);
    for (int i = 1; i < n; i++) m[i - 1][i] = 10;
    m[1][0] = -11;
    FloydWarshallSolver fw = new FloydWarshallSolver(m);
    List<Integer> fwPath = fw.reconstructShortestPath(s, e);
    assertThat(fwPath).isNull();
  }

  @Test
  public void testSingleNodeNegativeCycleDetection() {
    int n = 3, s = 0, e = n - 1;
    double[][] m = createMatrix(n);
    m[1][2] = 1000;
    m[2][2] = -1;
    m[1][0] = 1;
    m[2][0] = 1;

    FloydWarshallSolver solver = new FloydWarshallSolver(m);
    double[][] soln = solver.getApspMatrix();

    // 1 reaches 2 with cost 1000 and then it can go through the edge from 2 to 2 (which is -1) as
    // many times as wanted and
    // thus reach 2 with arbitrarily little cost
    assertThat(soln[1][2]).isEqualTo(NEG_INF);
    assertThat(soln[1][0]).isEqualTo(NEG_INF);
  }
}
