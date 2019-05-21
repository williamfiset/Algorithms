package com.williamfiset.algorithms.graphtheory.analysis;

import static java.lang.Math.*;
import java.util.*;

public class PrimsGraphRepresentationAnaylsis {

  private static class Edge implements Comparable<Edge> {
    int from, to, cost;
    public Edge(int from, int to, int cost) {
      this.from = from;
      this.to = to;
      this.cost = cost;
    }
    @Override public int compareTo(Edge other) {
      return cost - other.cost;
    }
  }

  private static class PrimsAdjList {

    // Inputs
    private final int n;
    private final List<List<Edge>> graph;

    // Internal
    private boolean solved;
    private boolean mstExists;
    private boolean[] visited;
    private MinIndexedDHeap<Edge> ipq;

    // Outputs
    private long minCostSum;
    private Edge[] mstEdges;

    public PrimsAdjList(List<List<Edge>> graph) {
      if (graph == null || graph.isEmpty()) throw new IllegalArgumentException();
      this.n = graph.size();
      this.graph = graph;
    }

    // Returns the edges used in finding the minimum spanning tree,
    // or returns null if no MST exists.
    public Edge[] getMst() {
      solve();
      return mstExists ? mstEdges : null;
    }

    public Long getMstCost() {
      solve();
      return mstExists ? minCostSum : null;
    }

    private void relaxEdgesAtNode(int currentNodeIndex) {
      visited[currentNodeIndex] = true;

      // edges will never be null if the createEmptyGraph method was used to build the graph.
      List<Edge> edges = graph.get(currentNodeIndex);

      for (Edge edge : edges) {
        int destNodeIndex = edge.to;

        // Skip edges pointing to already visited nodes.
        if (visited[destNodeIndex])
          continue;

        if (ipq.contains(destNodeIndex)) {
          // Try and improve the cheapest edge at destNodeIndex with the current edge in the IPQ.
          ipq.decrease(destNodeIndex, edge);
        } else {
          // Insert edge for the first time.
          ipq.insert(destNodeIndex, edge);
        }
      }
    }

    // Computes the minimum spanning tree and minimum spanning tree cost.
    private void solve() {
      if (solved) return;
      solved = true;

      int m = n-1, edgeCount = 0;
      visited = new boolean[n];
      mstEdges = new Edge[m];

      // The degree of the d-ary heap supporting the IPQ can greatly impact performance, especially
      // on dense graphs. The base 2 logarithm of n is a decent value based on my quick experiments 
      // (even better than E/V in many cases).
      int degree = (int) Math.ceil(Math.log(n)/Math.log(2));
      ipq = new MinIndexedDHeap<>(max(2, degree), n);

      // Add initial set of edges to the priority queue starting at node 0.
      relaxEdgesAtNode(0);

      while (!ipq.isEmpty() && edgeCount != m) {
        int destNodeIndex = ipq.peekMinKeyIndex(); // equivalently: edge.to
        Edge edge = ipq.pollMinValue();

        mstEdges[edgeCount++] = edge;
        minCostSum += edge.cost;

        relaxEdgesAtNode(destNodeIndex);
      }

      // Verify MST spans entire graph.
      mstExists = (edgeCount == m);
    }

      /* Graph construction helpers. */

    // Creates an empty adjacency list graph with n nodes.
    static List<List<Edge>> createEmptyGraph(int n) {
      List<List<Edge>> g = new ArrayList<>();
      for(int i = 0; i < n; i++) g.add(new ArrayList<>());
      return g;
    }

    static void addDirectedEdge(List<List<Edge>> g, int from, int to, int cost) {
      g.get(from).add(new Edge(from, to, cost));
    }

    static void addUndirectedEdge(List<List<Edge>> g, int from, int to, int cost) {
      addDirectedEdge(g, from, to, cost);
      addDirectedEdge(g, to, from, cost);
    }
  }

  private static class PrimsAdjMatrix {

    // Inputs
    private final int n;
    private final Integer[][] graph;

    // Internal
    private boolean solved;
    private boolean mstExists;
    private boolean[] visited;
    private MinIndexedDHeap<Integer> ipq;

    // Outputs
    private long minCostSum;
    private Edge[] mstEdges;

    public PrimsAdjMatrix(Integer[][] graph) {
      if (graph == null || graph.length == 0 || graph[0].length != graph.length) throw new IllegalArgumentException();
      this.n = graph.length;
      this.graph = graph;
    }

    // Returns the edges used in finding the minimum spanning tree,
    // or returns null if no MST exists.
    public Edge[] getMst() {
      // Unimplemented.
      return null;
    }

    public Long getMstCost() {
      solve();
      return mstExists ? minCostSum : null;
    }

    private void relaxEdgesAtNode(int currentNodeIndex) {
      visited[currentNodeIndex] = true;

      for (int to = 0; to < n; to++) {
        Integer cost = graph[currentNodeIndex][to];
        // Edge doesn't exist.
        if (cost == null)
          continue;

        // Skip edges pointing to already visited nodes.
        if (visited[to])
          continue;

        if (ipq.contains(to)) {
          // Try and improve the cheapest edge at to with the current edge in the IPQ.
          ipq.decrease(to, cost);
        } else {
          // Insert edge for the first time.
          ipq.insert(to, cost);
        }
      }
    }

    // Computes the minimum spanning tree and minimum spanning tree cost.
    private void solve() {
      if (solved) return;
      solved = true;

      int m = n-1, edgeCount = 0;
      visited = new boolean[n];

      // The degree of the d-ary heap supporting the IPQ can greatly impact performance, especially
      // on dense graphs. The base 2 logarithm of n is a decent value based on my quick experiments 
      // (even better than E/V in many cases).
      int degree = (int) Math.ceil(Math.log(n)/Math.log(2));
      ipq = new MinIndexedDHeap<>(max(2, degree), n);

      // Add initial set of edges to the priority queue starting at node 0.
      relaxEdgesAtNode(0);

      while (!ipq.isEmpty() && edgeCount != m) {
        int destNodeIndex = ipq.peekMinKeyIndex();
        int edgeCost = ipq.pollMinValue();

        minCostSum += edgeCost;
        edgeCount++;

        relaxEdgesAtNode(destNodeIndex);
      }

      // Verify MST spans entire graph.
      mstExists = (edgeCount == m);
    }

      /* Graph construction helpers. */

    // Creates an empty adjacency matrix graph with n nodes.
    static Integer[][] createEmptyGraph(int n) {
      return new Integer[n][n];
    }

    static void addDirectedEdge(Integer[][] g, int from, int to, int cost) {
      g[from][to] = cost;
    }

    static void addUndirectedEdge(Integer[][] g, int from, int to, int cost) {
      addDirectedEdge(g, from, to, cost);
      addDirectedEdge(g, to, from, cost);
    }
  }


    /* Example usage. */

  public static void main(String[] args) {
    densityTest();
  }

  static Random random = new Random(); 
  private static void densityTest() {
    
    for (int percentage = 0; percentage <= 100; percentage += 10) {
      
      int n = 5000;
      List<List<Edge>> g1 = PrimsAdjList.createEmptyGraph(n);
      Integer[][] g2 = PrimsAdjMatrix.createEmptyGraph(n);

      int numEdgesIncluded = 0;
      for (int i = 0; i < n; i++) {
        for (int j = i + 1; j < n; j++) {
          int r = Math.abs(random.nextInt()) % 100;
          if (r > percentage) continue;
          PrimsAdjList.addUndirectedEdge(g1, i, j, r);
          PrimsAdjMatrix.addUndirectedEdge(g2, i, j, r);
          numEdgesIncluded += 2;
        }
      }

      PrimsAdjList adjListSolver = new PrimsAdjList(g1);
      PrimsAdjMatrix matrixSolver = new PrimsAdjMatrix(g2);

      System.out.println("\nPercentage full: ~" + percentage + "%, Edges included: " + numEdgesIncluded);

      long startTime = System.nanoTime();
      Long listCost = adjListSolver.getMstCost();
      long endTime = System.nanoTime();
      System.out.println("List:   " + (endTime - startTime));

      startTime = System.nanoTime();
      Long matrixCost = matrixSolver.getMstCost();
      endTime = System.nanoTime();
      System.out.println("Matrix: " + (endTime - startTime));

      if (listCost != null && listCost.longValue() != matrixCost.longValue()) {
        System.out.println("Oh dear. " + listCost + " != " + matrixCost);
      }

    }

  }

    /* Supporting indexed priority queue implementation. */

  private static class MinIndexedDHeap <T extends Comparable<T>> {

    // Current number of elements in the heap.
    private int sz;

    // Maximum number of elements in the heap.
    private final int N;

    // The degree of every node in the heap.
    private final int D;

    // Lookup arrays to track the child/parent indexes of each node.
    private final int[] child, parent;

    // The Position Map (pm) maps Key Indexes (ki) to where the position of that 
    // key is represented in the priority queue in the domain [0, sz).
    public final int[] pm;

    // The Inverse Map (im) stores the indexes of the keys in the range
    // [0, sz) which make up the priority queue. It should be noted that
    // 'im' and 'pm' are inverses of each other, so: pm[im[i]] = im[pm[i]] = i
    public final int[] im;

    // The values associated with the keys. It is very important  to note 
    // that this array is indexed by the key indexes (aka 'ki').
    public final Object[] values;

    // Initializes a D-ary heap with a maximum capacity of maxSize.
    public MinIndexedDHeap(int degree, int maxSize) {
      if (maxSize <= 0) throw new IllegalArgumentException("maxSize <= 0");

      D = max(2, degree);
      N = max(D+1, maxSize);

      im = new int[N];
      pm = new int[N];
      child = new int[N];
      parent = new int[N];
      values = new Object[N];

      for (int i = 0; i < N; i++) {
        parent[i] = (i-1) / D;
        child[i] = i*D + 1;
        pm[i] = im[i] = -1;
      }
    }

    public int size() {
      return sz;
    }

    public boolean isEmpty() {
      return sz == 0;
    }

    public boolean contains(int ki) {
      keyInBoundsOrThrow(ki);
      return pm[ki] != -1;
    }

    public int peekMinKeyIndex() {
      isNotEmptyOrThrow();
      return im[0];
    }

    public int pollMinKeyIndex() {
      int minki = peekMinKeyIndex();
      delete(minki);
      return minki;
    }

    @SuppressWarnings("unchecked")
    public T peekMinValue() {
      isNotEmptyOrThrow();
      return (T) values[im[0]];
    }

    public T pollMinValue() {
      T minValue = peekMinValue();
      delete(peekMinKeyIndex());
      return minValue;
    }

    public void insert(int ki, T value) {
      if (contains(ki))
        throw new IllegalArgumentException("index already exists; received: " + ki);
      valueNotNullOrThrow(value);
      pm[ki] = sz;
      im[sz] = ki;
      values[ki] = value;
      swim(sz++);
    }

    @SuppressWarnings("unchecked")
    public T valueOf(int ki) {
      keyExistsOrThrow(ki);
      return (T) values[ki];
    }

    @SuppressWarnings("unchecked")
    public T delete(int ki) {
      keyExistsOrThrow(ki);
      final int i = pm[ki];
      swap(i, --sz);
      sink(i);
      swim(i);
      T value = (T) values[ki];
      values[ki] = null;
      pm[ki] = -1;
      im[sz] = -1;
      return value;
    }

    @SuppressWarnings("unchecked")
    public T update(int ki, T value) {
      keyExistsAndValueNotNullOrThrow(ki, value);
      final int i = pm[ki];
      T oldValue = (T) values[ki];
      values[ki] = value;
      sink(i);
      swim(i);
      return oldValue;
    }

    // Strictly decreases the value associated with 'ki' to 'value'
    public void decrease(int ki, T value) {
      keyExistsAndValueNotNullOrThrow(ki, value);
      if (less(value, values[ki])) {
        values[ki] = value;
        swim(pm[ki]);
      }
    }

    // Strictly increases the value associated with 'ki' to 'value'
    public void increase(int ki, T value) {
      keyExistsAndValueNotNullOrThrow(ki, value);
      if (less(values[ki], value)) {
        values[ki] = value;
        sink(pm[ki]);
      }
    }

      /* Helper functions */

    private void sink(int i) {
      for(int j = minChild(i); j != -1;) {
        swap(i, j);
        i = j;
        j = minChild(i);
      }
    }

    private void swim(int i) {
      while(less(i, parent[i])) {
        swap(i, parent[i]);
        i = parent[i];
      }
    }

    // From the parent node at index i find the minimum child below it
    private int minChild(int i) {
      int index = -1, from = child[i], to = min(sz, from + D);
      for(int j = from; j < to; j++)
        if (less(j, i))
          index = i = j;
      return index;
    }

    private void swap(int i, int j) {
      pm[im[j]] = i;
      pm[im[i]] = j;
      int tmp = im[i];
      im[i] = im[j];
      im[j] = tmp;
    }

    // Tests if the value of node i < node j
    @SuppressWarnings("unchecked")
    private boolean less(int i, int j) {
      return ((Comparable<? super T>) values[im[i]]).compareTo((T) values[im[j]]) < 0;
    }

    @SuppressWarnings("unchecked")
    private boolean less(Object obj1, Object obj2) {
      return ((Comparable<? super T>) obj1).compareTo((T) obj2) < 0;
    }

    @Override
    public String toString() {
      List<Integer> lst = new ArrayList<>(sz);
      for(int i = 0; i < sz; i++) lst.add(im[i]);
      return lst.toString();
    }

      /* Helper functions to make the code more readable. */

    private void isNotEmptyOrThrow() {
      if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
    }

    private void keyExistsAndValueNotNullOrThrow(int ki, Object value) {
      keyExistsOrThrow(ki);
      valueNotNullOrThrow(value);
    }

    private void keyExistsOrThrow(int ki) {
      if (!contains(ki)) 
        throw new NoSuchElementException("Index does not exist; received: " + ki);
    }

    private void valueNotNullOrThrow(Object value) {
      if (value == null) 
        throw new IllegalArgumentException("value cannot be null");
    }

    private void keyInBoundsOrThrow(int ki) {
      if (ki < 0 || ki >= N) 
        throw new IllegalArgumentException("Key index out of bounds; received: " + ki);
    }

      /* Test functions */

    // Recursively checks if this heap is a min heap. This method is used
    // for testing purposes to validate the heap invariant.
    public boolean isMinHeap() {
      return isMinHeap(0);
    }

    private boolean isMinHeap(int i) {
      int from = child[i], to = min(sz, from + D);
      for(int j = from; j < to; j++) {
        if (!less(i, j)) return false;
        if (!isMinHeap(j)) return false;
      }
      return true;
    }

  }

}


