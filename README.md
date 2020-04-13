
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
![Java CI with Gradle](https://github.com/williamfiset/Algorithms/workflows/Java%20CI%20with%20Gradle/badge.svg)
![README Checker](https://github.com/williamfiset/Algorithms/workflows/README%20URL%20Checker/badge.svg)

# Algorithms & data structures project

Algorithms and data structures are fundamental to efficient code and good software design. Creating and designing excellent algorithms is required for being an exemplary programmer. This repository's goal is to demonstrate how to correctly implement common data structures and algorithms in the simplest and most elegant ways.

# Contributing

This repository is contribution friendly :smiley:. If you'd like to add or improve an algorithm, your contribution is welcome! Please be sure to checkout the [Wiki](https://github.com/williamfiset/Algorithms/wiki) for instructions.

# Running an algorithm implementation

To compile and run any of the algorithms here, you need at least JDK version 8. Gradle can make things more convenient for you, but it is not required. 

## Compiling and running with only a JDK


### Create a classes folder
```
cd Algorithms
mkdir classes
```

### Compile the algorithm 
```
javac -sourcepath src/main/java -d classes src/main/java/ <relative-path-to-java-source-file>
```

### Run the algorithm 
```
java -cp classes <class-fully-qualified-name>
```

### Example
```
$ javac -d classes -sourcepath src/main/java src/main/java/com/williamfiset/algorithms/search/BinarySearch.java
$ java -cp classes com.williamfiset.algorithms.search.BinarySearch
```

## Running with Gradle

This project supports the [Gradle Wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html). The Gradle wrapper automatically downloads Gradle at the first time it runs, so expect a delay when running the first command below.

If you are on Windows, use `gradlew.bat` instead of `./gradlew` below.


Run a single algorithm like this:

```
./gradlew run -Palgorithm=<algorithm-subpackage>.<algorithm-class>
```


Alternatively, you can run a single algorithm specifying the full class name
```
./gradlew run -Pmain=<algorithm-fully-qualified-class-name>

```

For instance:

```
./gradlew run -Palgorithm=search.BinarySearch
```

or 

```
./gradlew run -Pmain=com.williamfiset.algorithms.search.BinarySearch
```

# Data Structures
* [:movie_camera:](https://www.youtube.com/watch?v=q4fnJZr8ztY)[Balanced Trees](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/datastructures/balancedtree)
    * [AVL Tree (recursive)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/datastructures/balancedtree/AVLTreeRecursive.java)
    * [Red Black Tree (recursive)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/datastructures/balancedtree/RedBlackTree.java)
* [:movie_camera:](https://www.youtube.com/watch?v=JfSdGQdAzq8)[Binary Search Tree](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/datastructures/binarysearchtree/BinarySearchTree.java)
* [Splay Tree](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/datastructures/binarysearchtree/SplayTree.java)
* [Bloom Filter](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/datastructures/bloomfilter)
* [:movie_camera:](https://www.youtube.com/watch?v=PEnFFiQe1pM)[Dynamic Array](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/datastructures/dynamicarray)
    * [Dynamic array (integer only, fast)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/datastructures/dynamicarray/IntArray.java)
    * [Dynamic array (generic)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/datastructures/dynamicarray/DynamicArray.java)
* [:movie_camera:](https://www.youtube.com/watch?v=RgITNht_f4Q)[Fenwick Tree](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/datastructures/fenwicktree)
    * [Fenwick Tree (range query, point updates)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/datastructures/fenwicktree/FenwickTreeRangeQueryPointUpdate.java)
    * [Fenwick Tree (range update, point query)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/datastructures/fenwicktree/FenwickTreeRangeUpdatePointQuery.java)
* [Fibonacci Heap](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/datastructures/fibonacciheap)
* [Set](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/datastructures/set)
* [:movie_camera:](https://www.youtube.com/watch?v=2E54GqF0H4s)[Hashtable](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/datastructures/hashtable)
    * [Hashtable (double hashing)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/datastructures/hashtable/HashTableDoubleHashing.java)
    * [Hashtable (linear probing)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/datastructures/hashtable/HashTableLinearProbing.java)
    * [Hashtable (quadratic probing)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/datastructures/hashtable/HashTableQuadraticProbing.java)
    * [Hashtable (separate chaining)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/datastructures/hashtable/HashTableSeparateChaining.java)
* [:movie_camera:](https://www.youtube.com/watch?v=-Yn5DU0_-lw)[Linked List](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/datastructures/linkedlist/DoublyLinkedList.java)
* [:movie_camera:](https://www.youtube.com/watch?v=wptevk0bshY)[Priority Queue](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/datastructures/priorityqueue)
    * [Min Binary Heap](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/datastructures/priorityqueue/BinaryHeap.java)
    * [Min Indexed Binary Heap (sorted key-value pairs, similar to hash-table)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/datastructures/priorityqueue/MinIndexedBinaryHeap.java)
    * [Min D-Heap](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/datastructures/priorityqueue/MinDHeap.java)
    * [:movie_camera:](https://www.youtube.com/watch?v=DT8xZ0Uf8wo)[Min Indexed D-Heap (sorted key-value pairs, similar to hash-table)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/datastructures/priorityqueue/MinIndexedDHeap.java)
* [Quad Tree [WIP]](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/datastructures/quadtree/QuadTree.java)
* [:movie_camera:](https://www.youtube.com/watch?v=KxzhEQ-zpDc)[Queue](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/datastructures/queue)
    * [Queue (integer only, fixed size, fast)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/datastructures/queue/IntQueue.java)
    * [Queue (linked list, generic)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/datastructures/queue/Queue.java)
* [Segment Tree](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/datastructures/segmenttree)
    * [Segment tree (array based, compact)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/datastructures/segmenttree/CompactSegmentTree.java)
    * [Segment tree (pointer implementation)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/datastructures/segmenttree/Node.java)
* [Skip List [UNTESTED]](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/datastructures/skiplist/SkipList.java)
* [:movie_camera:](https://youtu.be/uUatD9AudXo)[Sparse Table](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/datastructures/sparsetable/SparseTable.java)
* [:movie_camera:](https://www.youtube.com/watch?v=L3ud3rXpIxA)[Stack](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/datastructures/stack)
    * [Stack (integer only, fixed size, fast)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/datastructures/stack/IntStack.java)
    * [Stack (linked list, generic)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/datastructures/stack/Stack.java)
* [:movie_camera:](https://www.youtube.com/watch?v=zqKlL3ZpTqs)[Suffix Array](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/datastructures/suffixarray)
    * [Suffix Array (O(n²logn) construction)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/datastructures/suffixarray/SuffixArraySlow.java)
    * [Suffix Array (O(nlog²(n)) construction)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/datastructures/suffixarray/SuffixArrayMed.java)
    * [Suffix Array (O(nlog(n)) construction)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/datastructures/suffixarray/SuffixArrayFast.java)
* [Trie](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/datastructures/trie/Trie.java)
* [:movie_camera:](https://www.youtube.com/watch?v=ibjEGG7ylHk)[Union Find](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/datastructures/unionfind/UnionFind.java)

# Dynamic Programming
* [Coin change problem](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/dp/CoinChange.java) **- O(nW)**
* [Edit distance](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/dp/EditDistance.java) **- O(nm)**
* [:movie_camera:](https://www.youtube.com/watch?v=cJ21moQpofY)[Knapsack 0/1](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/dp/Knapsack_01.java) **- O(nW)**
* [Knapsack unbounded (0/∞)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/dp/KnapsackUnbounded.java) **- O(nW)**
* [Maximum contiguous subarray](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/dp/MaximumSubarray.java) **- O(n)**
* [Longest Common Subsequence (LCS)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/dp/LongestCommonSubsequence.java) **- O(nm)**
* [Longest Increasing Subsequence (LIS)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/dp/LongestIncreasingSubsequence.java) **- O(n<sup>2</sup>)**
* [Longest Palindrome Subsequence (LPS)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/dp/LongestPalindromeSubsequence.java) **- O(n<sup>2</sup>)**
* [:movie_camera:](https://www.youtube.com/watch?v=cY4HiiFHO1o)[Traveling Salesman Problem (dynamic programming, iterative)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/graphtheory/TspDynamicProgrammingIterative.java) **- O(n<sup>2</sup>2<sup>n</sup>)**
* [Traveling Salesman Problem (dynamic programming, recursive)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/graphtheory/TspDynamicProgrammingRecursive.java) **- O(n<sup>2</sup>2<sup>n</sup>)**
* [Minimum Weight Perfect Matching (iterative, complete graph)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/dp/MinimumWeightPerfectMatching.java) **- O(n<sup>2</sup>2<sup>n</sup>)**

# Geometry
* [Angle between 2D vectors](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/geometry/AngleBetweenVectors2D.java) **- O(1)**
* [Angle between 3D vectors](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/geometry/AngleBetweenVectors3D.java) **- O(1)**
* [Circle-circle intersection point(s)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/geometry/CircleCircleIntersectionPoints.js) **- O(1)**
* [Circle-line intersection point(s)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/geometry/LineCircleIntersection.js) **- O(1)**
* [Circle-line segment intersection point(s)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/geometry/LineSegmentCircleIntersection.js) **- O(1)**
* [Circle-point tangent line(s)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/geometry/PointCircleTangent.java) **- O(1)**
* [Closest pair of points (line sweeping algorithm)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/geometry/ClosestPairOfPoints.java) **- O(nlog(n))**
* [Collinear points test (are three 2D points on the same line)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/geometry/CollinearPoints.java) **- O(1)**
* [Convex hull (Graham Scan algorithm)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/geometry/ConvexHullGrahamScan.java) **- O(nlog(n))**
* [Convex hull (Monotone chain algorithm)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/geometry/ConvexHullMonotoneChainsAlgorithm.java) **- O(nlog(n))**
* [Convex polygon area](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/geometry/ConvexPolygonArea.java) **- O(n)**
* [Convex polygon cut](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/geometry/ConvexPolygonCutWithLineSegment.java) **- O(n)**
* [Convex polygon contains points](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/geometry/ConvexPolygonContainsPoint.java) **- O(log(n))**
* [Coplanar points test (are four 3D points on the same plane)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/geometry/CoplanarPoints.java) **- O(1)**
* [Line class (handy infinite line class)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/geometry/Line.java) **- O(1)**
* [Line-circle intersection point(s)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/geometry/LineCircleIntersection.js) **- O(1)**
* [Line segment-circle intersection point(s)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/geometry/LineSegmentCircleIntersection.js) **- O(1)**
* [Line segment to general form (ax + by = c)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/geometry/LineSegmentToGeneralForm.java) **- O(1)**
* [Line segment-line segment intersection](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/geometry/LineSegmentLineSegmentIntersection.java) **- O(1)**
* [Longitude-Latitude geographic distance](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/geometry/LongitudeLatitudeGeographicDistance.java) **- O(1)**
* [Point is inside triangle check](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/geometry/PointInsideTriangle.java) **- O(1)**
* [Point rotation about point](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/geometry/PointRotation.java) **- O(1)**
* [Triangle area algorithms](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/geometry/TriangleArea.java) **- O(1)**
* [[UNTESTED] Circle-circle intersection area](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/geometry/CircleCircleIntersectionArea.java) **- O(1)**
* [[UNTESTED] Circular segment area](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/geometry/CircularSegmentArea.java) **- O(1)**

# Graph theory

### Tree algorithms
* [:movie_camera:](https://www.youtube.com/watch?v=2FFq2_je7Lg)[Rooting an undirected tree](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/graphtheory/treealgorithms/RootingTree.java) **- O(V+E)**
* [:movie_camera:](https://www.youtube.com/watch?v=OCKvEMF0Xac)[Identifying isomorphic trees](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/graphtheory/treealgorithms/TreeIsomorphism.java) **- O(?)**
* [:movie_camera:](https://www.youtube.com/watch?v=Fa3VYhQPTOI)[Tree center(s)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/graphtheory/treealgorithms/TreeCenter.java) **- O(V+E)**
* [Tree diameter](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/graphtheory/treealgorithms/TreeDiameter.java) **- O(V+E)**
* [:movie_camera:](https://www.youtube.com/watch?v=sD1IoalFomA)[Lowest Common Ancestor (LCA, Euler tour)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/graphtheory/treealgorithms/LowestCommonAncestorEulerTour.java) **- O(1) queries, O(nlogn) preprocessing**

### Network flow
* [Bipartite graph verification (adjacency list)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/graphtheory/networkflow/BipartiteGraphCheckAdjacencyList.java) **- O(V+E)**
* [:movie_camera:](https://www.youtube.com/watch?v=LdOnanfc5TM)[Max flow & Min cut (Ford-Fulkerson with DFS, adjacency list)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/graphtheory/networkflow/FordFulkersonDfsSolverAdjacencyList.java) **- O(fE)**
* [Max flow & Min cut (Ford-Fulkerson with DFS, adjacency matrix)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/graphtheory/networkflow/FordFulkersonDFSAdjacencyMatrix.java) **- O(fV<sup>2</sup>)**
* [:movie_camera:](https://www.youtube.com/watch?v=RppuJYwlcI8)[Max flow & Min cut (Edmonds-Karp, adjacency list)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/graphtheory/networkflow/EdmondsKarpAdjacencyList.java) **- O(VE<sup>2</sup>)**
* [:movie_camera:](https://youtu.be/1ewLrXUz4kk)[Max flow & Min cut (Capacity scaling, adjacency list)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/graphtheory/networkflow/CapacityScalingSolverAdjacencyList.java) **- O(E<sup>2</sup>log<sub>2</sub>(U))**
* [:movie_camera:](https://youtu.be/M6cm8UeeziI)[Max flow & Min cut (Dinic's, adjacency list)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/graphtheory/networkflow/Dinics.java) **- O(EV<sup>2</sup>) or O(E√V) for bipartite graphs**
* [Maximum Cardinality Bipartite Matching (augmenting path algorithm, adjacency list)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/graphtheory/networkflow/MaximumCardinalityBipartiteMatchingAugmentingPathAdjacencyList.java) **- O(VE)**
* [Min Cost Max Flow (Bellman-Ford, adjacency list)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/graphtheory/networkflow/MinCostMaxFlowWithBellmanFord.java) **- O(E<sup>2</sup>V<sup>2</sup>)**
* [Min Cost Max Flow (Johnson's algorithm, adjacency list)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/graphtheory/networkflow/MinCostMaxFlowJohnsons.java) **- O(E<sup>2</sup>Vlog(V))**

### Main graph theory algorithms
*  [:movie_camera:](https://www.youtube.com/watch?v=aZXi1unBdJA)[Articulation points/cut vertices (adjacency list)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/graphtheory/ArticulationPointsAdjacencyList.java) **- O(V+E)**
* [Bellman-Ford (edge list, negative cycles, fast & optimized)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/graphtheory/BellmanFordEdgeList.java) **- O(VE)**
* [:movie_camera:](https://www.youtube.com/watch?v=lyw4FaxrwHg)[Bellman-Ford (adjacency list, negative cycles)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/graphtheory/BellmanFordAdjacencyList.java) **- O(VE)**
* [Bellman-Ford (adjacency matrix, negative cycles)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/graphtheory/BellmanFordAdjacencyMatrix.java) **- O(V<sup>3</sup>)**
* [:movie_camera:](https://www.youtube.com/watch?v=oDqjPvD54Ss)[Breadth first search (adjacency list)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/graphtheory/BreadthFirstSearchAdjacencyListIterative.java) **- O(V+E)**
* [Breadth first search (adjacency list, fast queue)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/graphtheory/BreadthFirstSearchAdjacencyListIterativeFastQueue.java) **- O(V+E)**
* [:movie_camera:](https://www.youtube.com/watch?v=aZXi1unBdJA)[Bridges/cut edges (adjacency list)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/graphtheory/BridgesAdjacencyList.java) **- O(V+E)**
* [Find connected components (adjacency list, union find)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/graphtheory/ConnectedComponentsAdjacencyList.java) **- O(Elog(E))**
* [Find connected components (adjacency list, DFS)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/graphtheory/ConnectedComponentsDfsSolverAdjacencyList.java) **- O(V+E)**
* [Depth first search (adjacency list, iterative)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/graphtheory/DepthFirstSearchAdjacencyListIterative.java) **- O(V+E)**
* [Depth first search (adjacency list, iterative, fast stack)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/graphtheory/DepthFirstSearchAdjacencyListIterativeFastStack.java) **- O(V+E)**
* [:movie_camera:](https://www.youtube.com/watch?v=7fujbpJ0LB4)[Depth first search (adjacency list, recursive)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/graphtheory/DepthFirstSearchAdjacencyListRecursive.java) **- O(V+E)**
* [:movie_camera:](https://www.youtube.com/watch?v=pSqmAO-m7Lk)[Dijkstra's shortest path (adjacency list, lazy implementation)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/graphtheory/DijkstrasShortestPathAdjacencyList.java) **- O(Elog(V))**
* [:movie_camera:](https://www.youtube.com/watch?v=pSqmAO-m7Lk)[Dijkstra's shortest path (adjacency list, eager implementation + D-ary heap)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/graphtheory/DijkstrasShortestPathAdjacencyListWithDHeap.java) **- O(Elog<sub>E/V</sub>(V))**
* [:movie_camera:](https://www.youtube.com/watch?v=8MpoO2zA2l4)[Eulerian Path (directed edges)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/graphtheory/EulerianPathDirectedEdgesAdjacencyList.java) **- O(E+V)**
* [:movie_camera:](https://www.youtube.com/watch?v=4NQ3HnhyNfQ)[Floyd Warshall algorithm (adjacency matrix, negative cycle check)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/graphtheory/FloydWarshallSolver.java) **- O(V<sup>3</sup>)**
* [Graph diameter (adjacency list)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/graphtheory/GraphDiameter.java) **- O(VE)**
* [Kahn's algorithm (topological sort, adjacency list)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/graphtheory/Kahns.java) **- O(E+V)**
* [Kruskal's min spanning tree algorithm (edge list, union find)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/graphtheory/KruskalsEdgeList.java) **- O(Elog(E))**
* [:movie_camera:](https://www.youtube.com/watch?v=JZBQLXgSGfs)[Kruskal's min spanning tree algorithm (edge list, union find, lazy sorting)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/graphtheory/KruskalsEdgeListPartialSortSolver.java) **- O(Elog(E))**
* [:movie_camera:](https://www.youtube.com/watch?v=jsmMtJpPnhU)[Prim's min spanning tree algorithm (lazy version, adjacency list)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/graphtheory/LazyPrimsAdjacencyList.java) **- O(Elog(E))**
* [Prim's min spanning tree algorithm (lazy version, adjacency matrix)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/graphtheory/LazyPrimsAdjacencyMatrix.java) **- O(V<sup>2</sup>)**
* [:movie_camera:](https://www.youtube.com/watch?v=xq3ABa-px_g)[Prim's min spanning tree algorithm (eager version, adjacency list)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/graphtheory/EagerPrimsAdjacencyList.java) **- O(Elog(V))**
* [Steiner tree (minimum spanning tree generalization)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/graphtheory/SteinerTree.java) **- O(V<sup>3</sup> + V<sup>2</sup> * 2<sup>T</sup> + V * 3<sup>T</sup>)**
* [:movie_camera:](https://www.youtube.com/watch?v=TyWtx7q2D7Y)[Tarjan's strongly connected components algorithm (adjacency list) ](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/graphtheory/TarjanSccSolverAdjacencyList.java) **- O(V+E)**
* [Tarjan's strongly connected components algorithm (adjacency matrix) ](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/graphtheory/TarjanAdjacencyMatrix.java) **- O(V<sup>2</sup>)**
* [:movie_camera:](https://www.youtube.com/watch?v=eL-KzMXSXXI)[Topological sort (acyclic graph, adjacency list)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/graphtheory/TopologicalSortAdjacencyList.java) **- O(V+E)**
* [Topological sort (acyclic graph, adjacency matrix)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/graphtheory/TopologicalSortAdjacencyMatrix.java) **- O(V<sup>2</sup>)**
* [Traveling Salesman Problem (brute force)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/graphtheory/TspBruteForce.java) **- O(n!)**
* [:movie_camera:](https://www.youtube.com/watch?v=cY4HiiFHO1o)[Traveling Salesman Problem (dynamic programming, iterative)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/graphtheory/TspDynamicProgrammingIterative.java) **- O(n<sup>2</sup>2<sup>n</sup>)**
* [Traveling Salesman Problem (dynamic programming, recursive)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/graphtheory/TspDynamicProgrammingRecursive.java) **- O(n<sup>2</sup>2<sup>n</sup>)**

# Linear algebra
* [Freivald's algorithm (matrix multiplication verification)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/linearalgebra/FreivaldsAlgorithm.java) **- O(kn<sup>2</sup>)**
* [Gaussian elimination (solve system of linear equations)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/linearalgebra/GaussianElimination.java) **- O(cr<sup>2</sup>)**
* [Gaussian elimination (modular version, prime finite field)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/linearalgebra/ModularLinearAlgebra.java) **- O(cr<sup>2</sup>)**
* [Linear recurrence solver (finds nth term in a recurrence relation)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/linearalgebra/LinearRecurrenceSolver.java) **- O(m<sup>3</sup>log(n))**
* [Matrix determinant (Laplace/cofactor expansion)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/linearalgebra/MatrixDeterminantLaplaceExpansion.java) **- O((n+2)!)**
* [Matrix inverse](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/linearalgebra/MatrixInverse.java) **- O(n<sup>3</sup>)**
* [Matrix multiplication](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/linearalgebra/MatrixMultiplication.java) **- O(n<sup>3</sup>)**
* [Matrix power](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/linearalgebra/MatrixPower.java) **- O(n<sup>3</sup>log(p))**
* [Square matrix rotation](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/linearalgebra/RotateSquareMatrixInplace.java) **- O(n<sup>2</sup>)**

# Mathematics
* [[UNTESTED] Chinese remainder theorem](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/math/ChineseRemainderTheorem.java)
* [Prime number sieve (sieve of Eratosthenes)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/math/SieveOfEratosthenes.java) **- O(nlog(log(n)))**
* [Prime number sieve (sieve of Eratosthenes, compressed)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/math/CompressedPrimeSieve.java) **- O(nlog(log(n)))**
* [Totient function (phi function, relatively prime number count)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/math/EulerTotientFunction.java) **- O(n<sup>1/4</sup>)**
* [Totient function using sieve (phi function, relatively prime number count)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/math/EulerTotientFunctionWithSieve.java) **- O(nlog(log(n)))**
* [Extended euclidean algorithm](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/math/ExtendedEuclideanAlgorithm.java) **- ~O(log(a + b))**
* [Greatest Common Divisor (GCD)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/math/GCD.java) **- ~O(log(a + b))**
* [Fast Fourier transform (quick polynomial multiplication)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/math/FastFourierTransform.java) **- O(nlog(n))**
* [Fast Fourier transform (quick polynomial multiplication, complex numbers)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/math/FastFourierTransformComplexNumbers.java) **- O(nlog(n))**
* [Primality check](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/math/IsPrime.java) **- O(√n)**
* [Primality check (Rabin-Miller)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/math/RabinMillerPrimalityTest.py) **- O(k)**
* [Least Common Multiple (LCM)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/math/LCM.java) **- ~O(log(a + b))**
* [Modular inverse](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/math/ModularInverse.java) **- ~O(log(a + b))**
* [Prime factorization (pollard rho)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/math/PrimeFactorization.java) **- O(n<sup>1/4</sup>)**
* [Relatively prime check (coprimality check)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/math/RelativelyPrime.java) **- ~O(log(a + b))**

# Other
* [Bit manipulations](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/other/BitManipulations.java) **- O(1)**
* [List permutations](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/other/Permutations.java) **- O(n!)**
* [:movie_camera:](https://www.youtube.com/watch?v=RnlHPR0lyOE)[Power set (set of all subsets)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/other/PowerSet.java) **- O(2<sup>n</sup>)**
* [Set combinations](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/other/Combinations.java) **- O(n choose r)**
* [Set combinations with repetition](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/other/CombinationsWithRepetition.java) **- O((n+r-1) choose r)**
* [Sliding Window Minimum/Maximum](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/other/SlidingWindowMaximum.java) **- O(1)**
* [Square Root Decomposition](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/other/SquareRootDecomposition.java) **- O(1) point updates, O(√n) range queries**
* [Unique set combinations](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/other/UniqueCombinations.java) **- O(n choose r)**
* [Lazy Range Adder](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/other/LazyRangeAdder.java) **- O(1) range updates, O(n) to finalize all updates**

# Search algorithms
* [Binary search (real numbers)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/search/BinarySearch.java) **- O(log(n))**
* [Interpolation search (discrete discrete)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/search/InterpolationSearch.java) **- O(n) or O(log(log(n))) with uniform input**
* [Ternary search (real numbers)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/search/TernarySearch.java) **- O(log(n))**
* [Ternary search (discrete numbers)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/search/TernarySearchDiscrete.java) **- O(log(n))**

# Sorting algorithms
* [Bubble sort](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/sorting/BubbleSort.java) **- O(n<sup>2</sup>)**
* [Bucket sort](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/sorting/BucketSort.java) **- Θ(n + k)**
* [Counting sort](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/sorting/CountingSort.java) **- O(n + k)**
* [Heapsort](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/sorting/Heapsort.java) **- O(nlog(n))**
* [Insertion sort](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/sorting/InsertionSort.java) **- O(n<sup>2</sup>)**
* [Mergesort](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/sorting/Mergesort.java) **- O(nlog(n))**
* [Quicksort (in-place, Hoare partitioning)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/sorting/Quicksort.java) **- Θ(nlog(n))**
* [Selection sort](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/sorting/SelectionSort.java) **- O(n<sup>2</sup>)**

# String algorithms
* [Booth's algorithm (finds lexicographically smallest string rotation)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/strings/BoothsAlgorithm.java) **- O(n)**
* [Knuth-Morris-Pratt algorithm (finds pattern matches in text)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/strings/KMP.java) **- O(n+m)**
* [Longest Common Prefix (LCP) array](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/strings/LongestCommonPrefixArray.java) **- O(nlog(n)) bounded by SA construction, otherwise O(n)**
* [:movie_camera:](https://www.youtube.com/watch?v=Ic80xQFWevc)[Longest Common Substring (LCS)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/strings/LongestCommonSubstring.java) **- O(nlog(n)) bounded by SA construction, otherwise O(n)**
* [:movie_camera:](https://www.youtube.com/watch?v=OptoHwC3D-Y)[Longest Repeated Substring (LRS)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/strings/LongestRepeatedSubstring.java) **- O(nlog(n))**
* [Manacher's algorithm (finds all palindromes in text)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/strings/ManachersAlgorithm.java) **- O(n)**
* [Rabin-Karp algorithm (finds pattern match positions in text)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/strings/RabinKarp.java) **- O(n+m)**
* [Boyer-Moore (finds pattern match positions in text)[UNTESTED]](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/strings/BoyerMooreStringSearch.java) **- O(n+m)**
* [Substring verification with suffix array](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/strings/SubstringVerificationSuffixArray.java) **- O(nlog(n)) SA construction and O(mlog(n)) per query**

# License

This repository is released under the [MIT license](https://opensource.org/licenses/MIT). In short, this means you are free to use this software in any personal, open-source or commercial projects. Attribution is optional but appreciated.
