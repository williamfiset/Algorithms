package com.williamfiset.algorithms.graphtheory;

import com.williamfiset.algorithms.datastructures.graph.Graph;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class BidirectionalSearchTest {

    @Test
    public void testGraphWithPath(){
        final int n = 15;
        Graph g = new Graph();
        for (int i = 0; i < n; i++) {
            g.addNode(i);
        }
        g.addEdge(0, 4);
        g.addEdge(1, 4);
        g.addEdge(2, 5);
        g.addEdge(3, 5);
        g.addEdge(4, 6);
        g.addEdge(5, 6);
        g.addEdge(6, 7);
        g.addEdge(7, 8);
        g.addEdge(8, 9);
        g.addEdge(9, 11);
        g.addEdge(9, 12);
        g.addEdge(8, 10);
        g.addEdge(10, 13);
        g.addEdge(10, 14);
        int src = 0;
        int dest = 14;

        BidirectionalSearch solver = new BidirectionalSearch(g, n);
        List<Integer> actualPath = solver.bidirectionalSearch(src, dest);
        if(actualPath == null)
            fail();
        List<Integer> expectedPath = new ArrayList<>();
        expectedPath.add(0);
        expectedPath.add(4);
        expectedPath.add(6);
        expectedPath.add(7);
        expectedPath.add(8);
        expectedPath.add(10);
        expectedPath.add(14);
        assertEquals(actualPath, expectedPath);
    }

    @Test
    public void testBFSForward(){
        final int n = 5;
        Graph g = new Graph();
        for (int i = 0; i < n; i++) {
            g.addNode(i);
        }
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(0, 3);
        g.addEdge(0, 4);
        BidirectionalSearch bds = new BidirectionalSearch(g, n);
        bds.srcQueue.offer(0);
        bds.srcVisited[0] = true;
        bds.bfs("forward");
        assertTrue(bds.srcVisited[1]);
        assertTrue(bds.srcVisited[2]);
        assertTrue(bds.srcVisited[3]);
        assertTrue(bds.srcVisited[4]);

        assertEquals(Integer.valueOf(0), Integer.valueOf(bds.srcParent[1]));
        assertEquals(Integer.valueOf(0), Integer.valueOf(bds.srcParent[2]));
        assertEquals(Integer.valueOf(0), Integer.valueOf(bds.srcParent[3]));
        assertEquals(Integer.valueOf(0), Integer.valueOf(bds.srcParent[4]));
    }

    @Test
    public void testBFSBackward(){
        final int n = 5;
        Graph g = new Graph();
        for (int i = 0; i < n; i++) {
            g.addNode(i);
        }
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(0, 3);
        g.addEdge(0, 4);
        BidirectionalSearch bds = new BidirectionalSearch(g, n);
        bds.destQueue.offer(0);
        bds.destVisited[0] = true;
        bds.bfs("backward");
        assertTrue(bds.destVisited[1]);
        assertTrue(bds.destVisited[2]);
        assertTrue(bds.destVisited[3]);
        assertTrue(bds.destVisited[4]);

        assertEquals(Integer.valueOf(0), Integer.valueOf(bds.destParent[1]));
        assertEquals(Integer.valueOf(0), Integer.valueOf(bds.destParent[2]));
        assertEquals(Integer.valueOf(0), Integer.valueOf(bds.destParent[3]));
        assertEquals(Integer.valueOf(0), Integer.valueOf(bds.destParent[4]));
    }

    @Test
    public void testIntersectIdentification(){
        final int n = 7;
        Graph g = new Graph();
        for (int i = 0; i < n; i++) {
            g.addNode(i);
        }
        g.addEdge(0, 1);
        g.addEdge(2, 1);
        g.addEdge(1, 3);
        g.addEdge(3, 4);
        g.addEdge(4, 5);
        g.addEdge(4, 6);

        int src = 0;
        int dest = 6;

        BidirectionalSearch solver = new BidirectionalSearch(g, n);
        List<Integer> actualPath = solver.bidirectionalSearch(src, dest);
        if(actualPath == null)
            fail();
        assertEquals(3, solver.isIntersecting());
    }

    @Test
    public void testGraphWithoutPath(){
        final int n = 4;
        Graph g = new Graph();
        for (int i = 0; i < n; i++) {
            g.addNode(i);
        }

        g.addEdge(0, 1);
        g.addEdge(2, 3);

        int src = 0;
        int dest = 3;

        BidirectionalSearch solver = new BidirectionalSearch(g, n);
        List<Integer> actualPath = solver.bidirectionalSearch(src, dest);
        assertNull(actualPath);
    }

}
