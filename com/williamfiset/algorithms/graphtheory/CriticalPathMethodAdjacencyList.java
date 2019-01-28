package com.williamfiset.algorithms.graphtheory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.williamfiset.algorithms.graphtheory.TopologicalSortAdjacencyList.Edge;

/**
 * 
 * Critical Path Method uses Topological Sort and DAG Longest path method to
 * find the Critical Path.
 * 
 * Two new nodes, source and target are introduced such that there are directed
 * edges from source node to each nodes in the graph. Likewise, all nodes has an
 * edge that connects to target node.
 * 
 * The weights are negated so to find the longest path in DAG.
 * 
 *
 */
public class CriticalPathMethodAdjacencyList {

	public static Integer[] dists;

	public static class Task {
		int node;
		int weight;

		public Task(int n, int w) {
			this.node = n;
			this.weight = w;
		}
	}

	public static Integer[] criticalPathMathod(Map<Task, List<Integer>> graph, int start, int nodeCount) {

		Map<Integer, List<Edge>> cmpGraph = new HashMap<>();

		int source = 2 * nodeCount;
		int target = 2 * nodeCount + 1;

		graph.keySet().stream().forEach(task -> {
			List<Integer> successors = graph.get(task);

			// can't create edge to itself, so creating new node which shows the end of
			// duration for the node and negating the weight for longest path
			// For example, Task (0, 41) translates to edge 0 to 10 and -41 as weight.
			addGraphEdge(cmpGraph, new Edge(task.node, task.node + nodeCount, -task.weight));
			// Adding edge from source node to the node
			addGraphEdge(cmpGraph, new Edge(source, task.node, 0));
			// Adding edge from node to target node
			addGraphEdge(cmpGraph, new Edge(task.node + nodeCount, target, 0));

			successors.stream().forEach(to -> {
				// Since we created end node for each node, we have to add edge from end node to
				// it's successors
				addGraphEdge(cmpGraph, new Edge(task.node + nodeCount, to, 0));
			});

		});

		dists = TopologicalSortAdjacencyList.dagShortestPath(cmpGraph, start, cmpGraph.size() + 1);

		// Negate the weights now that we have calculated the shortest path
		for (int i = 0; i < dists.length; i++) {
			if (dists[i] != null) {
				dists[i] = -1 * dists[i];
			}
		}

		return Arrays.copyOfRange(dists, start, nodeCount);
	}

	private static void addGraphEdge(Map<Integer, List<Edge>> graph, Edge edge) {
		List<Edge> edges = graph.getOrDefault(edge.from, new ArrayList<>());
		edges.add(edge);
		graph.put(edge.from, edges);
	}

	public static void main(String[] args) {
		int nodeCount = 10;
		Map<Task, List<Integer>> graph = new HashMap<>();
		graph.put(new Task(0, 41), Arrays.asList(1, 7, 9));
		graph.put(new Task(1, 51), Arrays.asList(2));
		graph.put(new Task(2, 50), Arrays.asList());
		graph.put(new Task(3, 36), Arrays.asList());
		graph.put(new Task(4, 38), Arrays.asList());
		graph.put(new Task(5, 45), Arrays.asList());
		graph.put(new Task(6, 21), Arrays.asList(3, 8));
		graph.put(new Task(7, 32), Arrays.asList(3, 8));
		graph.put(new Task(8, 32), Arrays.asList(2));
		graph.put(new Task(9, 29), Arrays.asList(4, 6));

		Integer[] longestPaths = criticalPathMathod(graph, 0, nodeCount);

		System.out.println(java.util.Arrays.toString(longestPaths));

		System.out.println("CPM time : " + dists[2 * graph.size() + 1]);
	}
}
