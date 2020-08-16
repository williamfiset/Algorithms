package com.williamfiset.algorithms.graphtheory;

import static com.google.common.truth.Truth.assertThat;

import com.williamfiset.algorithms.graphtheory.BidirectionalDijkstrasAlgorithm.Edge;
import com.williamfiset.algorithms.graphtheory.BidirectionalDijkstrasAlgorithm.Graph;
import com.williamfiset.algorithms.graphtheory.BidirectionalDijkstrasAlgorithm.Res;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.junit.Test;

public class BidirectionalDijkstrasAlgorithmTest {

  public static String[] getRandomTwoNodes(Set<String> nodeSet) {
    // using reservoir sample to choose two random nodes.
    Random random = new Random();
    String[] pair = new String[2];
    Iterator<String> iterator = nodeSet.iterator();
    pair[0] = iterator.next();
    pair[1] = iterator.next();
    int k = 2;
    int j = k;
    while (iterator.hasNext()) {
      j++;
      String node = iterator.next();
      int randomI = random.nextInt(j);
      if (randomI < k) {
        pair[randomI] = node;
      }
    }
    return pair;
  }

  private static int[] generateOneEdge(int[] nodes, Random random) {
    // using reservoir sample to choose two random node to form an edge.
    int k = 2;
    int[] nodePair = new int[k];
    System.arraycopy(nodes, 0, nodePair, 0, k);
    for (int j = k; j < nodes.length; j++) {
      int temp = nodes[j];
      int randomIdx = random.nextInt(j + 1);
      if (randomIdx < k) {
        nodePair[randomIdx] = temp;
      }
    }
    return nodePair;
  }

  private static int[] generateNodesSourceArr(int numOfNodes) {
    int[] nodes = new int[numOfNodes];
    for (int i = 0; i < nodes.length; i++) {
      nodes[i] = i;
    }
    return nodes;
  }

  public void generateDirectedGraph(
      int numOfNodes, int numOfEdges, int distanceUpBound, boolean directedGraph) {
    Random random = new Random();

    DijkstrasShortestPathAdjacencyList singleSourcedDijkstra =
        new DijkstrasShortestPathAdjacencyList(numOfNodes);

    Graph graph = new Graph(numOfNodes);

    int[] nodes = generateNodesSourceArr(numOfNodes);
    for (int i = 0; i < numOfEdges; i++) {
      int[] nodePair = generateOneEdge(nodes, random);
      int from = nodePair[0];
      int to = nodePair[1];
      int cost = random.nextInt(distanceUpBound + 1);
      singleSourcedDijkstra.addEdge(from, to, cost);
      if (!directedGraph) {
        singleSourcedDijkstra.addEdge(to, from, cost);
      }
      if (directedGraph) {
        graph.addDirectedEdge(String.valueOf(from), String.valueOf(to), cost);
      } else {
        graph.addUndirectedEdge(String.valueOf(from), String.valueOf(to), cost);
      }
    }

    BidirectionalDijkstrasAlgorithm bidirectionalDijkstras =
        new BidirectionalDijkstrasAlgorithm(graph, directedGraph);

    for (int i = 0; i < 100; i++) {
      String[] randomNodePair = getRandomTwoNodes(graph.getNodeSet());
      String start = randomNodePair[0];
      String end = randomNodePair[1];
      int intStart = Integer.parseInt(start);
      int intEnd = Integer.parseInt(end);
      double shortest = singleSourcedDijkstra.dijkstra(intStart, intEnd);
      Res res = bidirectionalDijkstras.bidirectionalDijkstra(start, end);

      double sumPath = getShortestDistanceFromThePath(graph, res.thePath);

      assertThat(sumPath).isEqualTo(shortest);
      assertThat(res.shortestDistance).isEqualTo(shortest);
    }
  }

  private double getShortestDistanceFromThePath(Graph graph, List<String> thePath) {

    if (thePath.size() == 0) {
      return Double.POSITIVE_INFINITY;
    }
    double sumPath = 0;
    Iterator<String> iterator = thePath.iterator();
    if (iterator.hasNext()) {
      String from = iterator.next();
      while (iterator.hasNext()) {
        String to = iterator.next();
        List<Edge> edges = graph.getEdges(from);
        // why we sort the edges here, because in this graph which we randomly generated, it might
        // exist parallel edges, if so, the distance might be different. So we just fetch the
        // minimum.
        Collections.sort(edges, (a, b) -> (Double.compare(a.distance, b.distance)));
        for (Edge edge : edges) {
          if (edge.to.equals(to)) {
            sumPath += edge.distance;
            break;
          }
        }
        from = to;
      }
    }

    return sumPath;
  }

  @Test
  public void testBidirectionalDijkstrasAlgorithmForDirectedGraph() {
    generateDirectedGraph(400, 4000, 400, true);
  }

  @Test
  public void testBidirectionalDijkstrasAlgorithmForUndirectedGraph() {
    generateDirectedGraph(400, 4000, 4000, false);
  }
}
