package com.williamfiset.algorithms.graphtheory.networkflow.examples;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EdmondsKarpExampleTest {

    /**
     * Test case that asserts that breath-first search finds the shortest
     * path that has available capacity. The following is a simple path in which the flow
     * for the first shortest path should be 11.
     */
    @Test
    void bfsOneIterationTest() {
        int n = 6;

        int s = n-2;
        int t = n-1;

        EdmondsKarpExample.EdmondsKarpSolver solver = new EdmondsKarpExample.EdmondsKarpSolver(n,s,t);

        // Edges from source
        solver.addEdge(s, 0, 11);
        solver.addEdge(s, 1, 12);

        // Middle edges
        solver.addEdge(0,2, 12);
        solver.addEdge(1,0,1);
        solver.addEdge(1,3,11);
        solver.addEdge(3,2, 7);

        // Edges to sink
        solver.addEdge(2, t, 19);
        solver.addEdge(3,t, 4);

        long flow = solver.bfs();
        Assertions.assertEquals(flow, 11);
    }

    /**
     * Test case that asserts that if sink is not reachable bfs returns 0.
     * In the following graph there are no edges to the sink which means that
     * it won't be reachable and thus bfs should return 0.
     */
    @Test
    void bfsSinkNotReachableTest() {
        int n = 6;

        int s = n-2;
        int t = n-1;

        EdmondsKarpExample.EdmondsKarpSolver solver = new EdmondsKarpExample.EdmondsKarpSolver(n,s,t);

        // Edges from source
        solver.addEdge(s, 0, 11);
        solver.addEdge(s, 1, 12);

        // Middle edges
        solver.addEdge(0,2, 12);
        solver.addEdge(1,0,1);
        solver.addEdge(1,3,11);
        solver.addEdge(3,2, 7);

        // No edges to sink to make it unreachable

        long flow = solver.bfs();
        Assertions.assertEquals(flow, 0);
    }


}
