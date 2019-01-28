package javatests.com.williamfiset.algorithms.graphtheory;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.williamfiset.algorithms.graphtheory.CriticalPathMethodAdjacencyList;
import com.williamfiset.algorithms.graphtheory.CriticalPathMethodAdjacencyList.Task;

public class CriticalPathMethodAdjacencyListTest {

	@Test
	public void testCriticalPathMathod() {

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

		CriticalPathMethodAdjacencyList.criticalPathMathod(graph, 0, nodeCount);

		int result = CriticalPathMethodAdjacencyList.dists[2 * graph.size() + 1];
		assertEquals(173, result);
	}

	@Test
	public void testCriticalPathMathodLessNodes() {

		int nodeCount = 10;
		Map<Task, List<Integer>> graph = new HashMap<>();
		graph.put(new Task(0, 41), Arrays.asList(1, 7, 9));
		graph.put(new Task(1, 83), Arrays.asList(2));
		graph.put(new Task(2, 50), Arrays.asList());
		graph.put(new Task(3, 36), Arrays.asList());
		graph.put(new Task(4, 38), Arrays.asList());
		graph.put(new Task(5, 45), Arrays.asList());
		graph.put(new Task(6, 21), Arrays.asList(3, 8));
		graph.put(new Task(7, 32), Arrays.asList(3, 8));
		graph.put(new Task(8, 32), Arrays.asList(2));
		graph.put(new Task(9, 29), Arrays.asList(4, 6));

		CriticalPathMethodAdjacencyList.criticalPathMathod(graph, 0, nodeCount);

		int result = CriticalPathMethodAdjacencyList.dists[2 * graph.size() + 1];
		assertEquals(174, result);
	}

	@Test
	public void testCriticalPathMathodWithIndividualNode() {

		int nodeCount = 10;
		Map<Task, List<Integer>> graph = new HashMap<>();
		graph.put(new Task(0, 41), Arrays.asList());
		graph.put(new Task(1, 11), Arrays.asList(2));
		graph.put(new Task(2, 50), Arrays.asList());
		graph.put(new Task(3, 36), Arrays.asList());
		graph.put(new Task(4, 38), Arrays.asList());
		graph.put(new Task(5, 45), Arrays.asList());
		graph.put(new Task(6, 21), Arrays.asList());
		graph.put(new Task(7, 101), Arrays.asList());
		graph.put(new Task(8, 32), Arrays.asList());
		graph.put(new Task(9, 29), Arrays.asList());

		CriticalPathMethodAdjacencyList.criticalPathMathod(graph, 7, nodeCount);

		int result = CriticalPathMethodAdjacencyList.dists[2 * graph.size() + 1];
		assertEquals(101, result);
	}

	@Test
	public void testCriticalPathMathodWithTwoNodes() {

		int nodeCount = 10;
		Map<Task, List<Integer>> graph = new HashMap<>();
		graph.put(new Task(0, 41), Arrays.asList());
		graph.put(new Task(1, 11), Arrays.asList(2));
		graph.put(new Task(2, 50), Arrays.asList());
		graph.put(new Task(3, 36), Arrays.asList());
		graph.put(new Task(4, 38), Arrays.asList());
		graph.put(new Task(5, 45), Arrays.asList());
		graph.put(new Task(6, 21), Arrays.asList());
		graph.put(new Task(7, 101), Arrays.asList());
		graph.put(new Task(8, 32), Arrays.asList());
		graph.put(new Task(9, 29), Arrays.asList());

		CriticalPathMethodAdjacencyList.criticalPathMathod(graph, 1, nodeCount);

		int result = CriticalPathMethodAdjacencyList.dists[2 * graph.size() + 1];
		assertEquals(61, result);
	}

	@Test
	public void testCriticalPathMathod1Node() {

		int nodeCount = 1;
		Map<Task, List<Integer>> graph = new HashMap<>();
		graph.put(new Task(0, 41), Arrays.asList());

		CriticalPathMethodAdjacencyList.criticalPathMathod(graph, 0, nodeCount);

		int result = CriticalPathMethodAdjacencyList.dists[2 * graph.size() + 1];
		assertEquals(41, result);
	}

	@Test
	public void testCriticalPathMathod0Node() {

		int nodeCount = 0;
		Map<Task, List<Integer>> graph = new HashMap<>();

		CriticalPathMethodAdjacencyList.criticalPathMathod(graph, 0, nodeCount);

		Integer[] result = CriticalPathMethodAdjacencyList.dists;
		Integer[] expected = { 0 };
		assertArrayEquals(expected, result);
	}

}
