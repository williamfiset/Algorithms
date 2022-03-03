package com.williamfiset.algorithms.graphtheory;

import static com.google.common.truth.Truth.assertThat;
import static com.williamfiset.algorithms.graphtheory.BidirectionialSearch.Edge;
import static com.williamfiset.algorithms.graphtheory.BidirectionialSearch.addUnweightedUndirectedEdge;
import static com.williamfiset.algorithms.graphtheory.BidirectionialSearch.createEmptyGraph;
import static java.lang.Math.max;
import static java.lang.Math.random;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import org.junit.Before;
import org.junit.Test;

public class BidirectionalSearchTest {

    @Test
    public void testGraphWithPath(){
        final int n = 15;
        List<List<Edge>> graph = createEmptyGraph(n);

        addUnweightedUndirectedEdge(graph, 0, 4);
        addUnweightedUndirectedEdge(graph, 1, 4);
        addUnweightedUndirectedEdge(graph, 2, 5);
        addUnweightedUndirectedEdge(graph, 3, 5);
        addUnweightedUndirectedEdge(graph, 4, 6);
        addUnweightedUndirectedEdge(graph, 5, 6);
        addUnweightedUndirectedEdge(graph, 6, 7);
        addUnweightedUndirectedEdge(graph, 7, 8);
        addUnweightedUndirectedEdge(graph, 8, 9);
        addUnweightedUndirectedEdge(graph, 9, 11);
        addUnweightedUndirectedEdge(graph, 9, 12);
        addUnweightedUndirectedEdge(graph, 8, 10);
        addUnweightedUndirectedEdge(graph, 10, 13);
        addUnweightedUndirectedEdge(graph, 10, 14);
        int src = 0;
        int dest = 14;

        BidirectionialSearch solver = new BidirectionialSearch(graph, n);
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
        List<List<Edge>> graph = createEmptyGraph(n);
        addUnweightedUndirectedEdge(graph, 0, 1);
        addUnweightedUndirectedEdge(graph, 0, 2);
        addUnweightedUndirectedEdge(graph, 0, 3);
        addUnweightedUndirectedEdge(graph, 0, 4);
        BidirectionialSearch bds = new BidirectionialSearch(graph, n);
        bds.srcQueue.offer(0);
        bds.srcVisited[0] = true;
        bds.bfs("forward");
        assertTrue(bds.srcVisited[1]);
        assertTrue(bds.srcVisited[2]);
        assertTrue(bds.srcVisited[3]);
        assertTrue(bds.srcVisited[4]);

        assertEquals(Integer.valueOf(0), bds.srcParent.get(1));
        assertEquals(Integer.valueOf(0), bds.srcParent.get(2));
        assertEquals(Integer.valueOf(0), bds.srcParent.get(3));
        assertEquals(Integer.valueOf(0), bds.srcParent.get(4));
    }

    @Test
    public void testBFSBackward(){
        final int n = 5;
        List<List<Edge>> graph = createEmptyGraph(n);
        addUnweightedUndirectedEdge(graph, 0, 1);
        addUnweightedUndirectedEdge(graph, 0, 2);
        addUnweightedUndirectedEdge(graph, 0, 3);
        addUnweightedUndirectedEdge(graph, 0, 4);
        BidirectionialSearch bds = new BidirectionialSearch(graph, n);
        bds.destQueue.offer(0);
        bds.destVisited[0] = true;
        bds.bfs("backward");
        assertTrue(bds.destVisited[1]);
        assertTrue(bds.destVisited[2]);
        assertTrue(bds.destVisited[3]);
        assertTrue(bds.destVisited[4]);

        assertEquals(Integer.valueOf(0), bds.destParent.get(1));
        assertEquals(Integer.valueOf(0), bds.destParent.get(2));
        assertEquals(Integer.valueOf(0), bds.destParent.get(3));
        assertEquals(Integer.valueOf(0), bds.destParent.get(4));
    }

    @Test
    public void testIntersectIdentification(){
        final int n = 6;
        List<List<Edge>> graph = createEmptyGraph(n);

        addUnweightedUndirectedEdge(graph, 0, 1);
        addUnweightedUndirectedEdge(graph, 2, 1);
        addUnweightedUndirectedEdge(graph, 1, 3);
        addUnweightedUndirectedEdge(graph, 3, 4);
        addUnweightedUndirectedEdge(graph, 4, 5);
        addUnweightedUndirectedEdge(graph, 4, 6);

        int src = 0;
        int dest = 6;

        BidirectionialSearch solver = new BidirectionialSearch(graph, n);
        List<Integer> actualPath = solver.bidirectionalSearch(src, dest);
        if(actualPath == null)
            fail();
        assertEquals(3, solver.isIntersecting());
    }

    @Test
    public void testGraphWithoutPath(){
        final int n = 2;
        List<List<Edge>> graph = createEmptyGraph(n);

        addUnweightedUndirectedEdge(graph, 0, 1);
        addUnweightedUndirectedEdge(graph, 2, 3);

        int src = 0;
        int dest = 3;

        BidirectionialSearch solver = new BidirectionialSearch(graph, n);
        List<Integer> actualPath = solver.bidirectionalSearch(src, dest);
        assertNull(actualPath);
    }

}
