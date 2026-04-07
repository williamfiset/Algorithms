package com.williamfiset.algorithms.graphtheory;

import org.junit.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static com.google.common.truth.Truth.assertThat;

public class HamiltonianCycleTest {

   HamiltonianCycle solver;

    @Before
    public void setUp() {
        solver = null;
    }

    // Initialize graph with 'n' nodes.
    public static int[][] initializeEmptyGraph() {
        int[][] graph = null;
        return graph;
    }

    // Add directed edge to graph.
    public static void addDirectedEdge(int[][] graph, int a, int b) {
        graph[a][b]=1;
    }

    //test empty
    @Test(expected = IllegalArgumentException.class)
    public void testEmptyGraph() {
        int[][] graph = initializeEmptyGraph();
        HamiltonianCycle solver;
        solver = new HamiltonianCycle();
        solver.setGraph(graph);
        solver.getHamiltonCircuit(graph);
    }

    //valid test 1
    @Test
    public void validHC1() {
        int[][] graph = new int[5][5];
        int[] result= new int[6];
        result[0]=0;result[1]=1;result[2]=2;result[3]=4;result[4]=3;result[5]=0;
        /**
         * the example graph
         * (0)--(1)--(2)
         *  |   / \   |
         *  |  /   \  |
         *  | /     \ |
         * (3)-------(4)
         *
         * the 2D should be
         * 0 1 0 1 0
         * 1 0 1 1 1
         * 0 1 0 0 1
         * 1 1 0 0 1
         * 0 1 1 1 0
         *
         */
        graph [0][0]=0;graph [0][1]=1;graph [0][2]=0;graph [0][3]=1;graph [0][4]=0;
        graph [1][0]=1;graph [1][1]=0;graph [1][2]=1;graph [1][3]=1;graph [1][4]=1;
        graph [2][0]=0;graph [2][1]=1;graph [2][2]=0;graph [2][3]=0;graph [2][4]=1;
        graph [3][0]=1;graph [3][1]=1;graph [3][2]=0;graph [3][3]=0;graph [3][4]=1;
        graph [4][0]=0;graph [4][1]=1;graph [4][2]=1;graph [4][3]=1;graph [4][4]=0;
        HamiltonianCycle solver;
        solver = new HamiltonianCycle();
        solver.setGraph(graph);
        solver.getHamiltonCircuit(graph);
        assertThat(solver.getPath()).isEqualTo(result);
    }

    //invalid test 1, generally same as valid 1, but change a little and make it invalid
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }
    @Test
    public void invalidHC1() {
        int[][] graph = new int[5][5];
        int[] invalidresult= new int[6];
        Arrays.fill(invalidresult, -1);
        /**
         * the example graph
         * (0)--(1)--(2)
         *  |   / \   |
         *  |  /   \  |
         *  | /     \ |
         * (3)      (4)
         *
         * the 2D should be
         * 0 1 0 1 0
         * 1 0 1 1 1
         * 0 1 0 0 1
         * 1 1 0 0 0
         * 0 1 1 0 0
         *
         */
        graph [0][0]=0;graph [0][1]=1;graph [0][2]=0;graph [0][3]=1;graph [0][4]=0;
        graph [1][0]=1;graph [1][1]=0;graph [1][2]=1;graph [1][3]=1;graph [1][4]=1;
        graph [2][0]=0;graph [2][1]=1;graph [2][2]=0;graph [2][3]=0;graph [2][4]=1;
        graph [3][0]=1;graph [3][1]=1;graph [3][2]=0;graph [3][3]=0;graph [3][4]=0;
        graph [4][0]=0;graph [4][1]=1;graph [4][2]=1;graph [4][3]=0;graph [4][4]=0;
        HamiltonianCycle solver;
        solver = new HamiltonianCycle();
        solver.setGraph(graph);
        solver.getHamiltonCircuit(graph);
        Assert.assertEquals("No solution",outContent.toString());
    }
}
