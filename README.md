
[![Travis](https://img.shields.io/travis/williamfiset/Algorithms.svg)](https://travis-ci.org/williamfiset/Algorithms) [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

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
    * [Avl Tree (recursive)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/datastructures/balancedtree/AVLTreeRecursive.java)
    * [Avl Tree (recursive, mildly optimized)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/datastructures/balancedtree/AVLTreeRecursiveOptimized.java)
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

### Other graph theory
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
* [Rabin-Karp algorithm (finds pattern matches in text)](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/strings/RabinKarp.java) **- O(n+m)**
* [Substring verification with suffix array](https://github.com/williamfiset/algorithms/tree/master/src/main/java/com/williamfiset/algorithms/strings/SubstringVerificationSuffixArray.java) **- O(nlog(n)) SA construction and O(mlog(n)) per query**

# License


                                 Apache License
                           Version 2.0, January 2004
                        https://www.apache.org/licenses/

   TERMS AND CONDITIONS FOR USE, REPRODUCTION, AND DISTRIBUTION

   1. Definitions.

      "License" shall mean the terms and conditions for use, reproduction,
      and distribution as defined by Sections 1 through 9 of this document.

      "Licensor" shall mean the copyright owner or entity authorized by
      the copyright owner that is granting the License.

      "Legal Entity" shall mean the union of the acting entity and all
      other entities that control, are controlled by, or are under common
      control with that entity. For the purposes of this definition,
      "control" means (i) the power, direct or indirect, to cause the
      direction or management of such entity, whether by contract or
      otherwise, or (ii) ownership of fifty percent (50%) or more of the
      outstanding shares, or (iii) beneficial ownership of such entity.

      "You" (or "Your") shall mean an individual or Legal Entity
      exercising permissions granted by this License.

      "Source" form shall mean the preferred form for making modifications,
      including but not limited to software source code, documentation
      source, and configuration files.

      "Object" form shall mean any form resulting from mechanical
      transformation or translation of a Source form, including but
      not limited to compiled object code, generated documentation,
      and conversions to other media types.

      "Work" shall mean the work of authorship, whether in Source or
      Object form, made available under the License, as indicated by a
      copyright notice that is included in or attached to the work
      (an example is provided in the Appendix below).

      "Derivative Works" shall mean any work, whether in Source or Object
      form, that is based on (or derived from) the Work and for which the
      editorial revisions, annotations, elaborations, or other modifications
      represent, as a whole, an original work of authorship. For the purposes
      of this License, Derivative Works shall not include works that remain
      separable from, or merely link (or bind by name) to the interfaces of,
      the Work and Derivative Works thereof.

      "Contribution" shall mean any work of authorship, including
      the original version of the Work and any modifications or additions
      to that Work or Derivative Works thereof, that is intentionally
      submitted to Licensor for inclusion in the Work by the copyright owner
      or by an individual or Legal Entity authorized to submit on behalf of
      the copyright owner. For the purposes of this definition, "submitted"
      means any form of electronic, verbal, or written communication sent
      to the Licensor or its representatives, including but not limited to
      communication on electronic mailing lists, source code control systems,
      and issue tracking systems that are managed by, or on behalf of, the
      Licensor for the purpose of discussing and improving the Work, but
      excluding communication that is conspicuously marked or otherwise
      designated in writing by the copyright owner as "Not a Contribution."

      "Contributor" shall mean Licensor and any individual or Legal Entity
      on behalf of whom a Contribution has been received by Licensor and
      subsequently incorporated within the Work.

   2. Grant of Copyright License. Subject to the terms and conditions of
      this License, each Contributor hereby grants to You a perpetual,
      worldwide, non-exclusive, no-charge, royalty-free, irrevocable
      copyright license to reproduce, prepare Derivative Works of,
      publicly display, publicly perform, sublicense, and distribute the
      Work and such Derivative Works in Source or Object form.

   3. Grant of Patent License. Subject to the terms and conditions of
      this License, each Contributor hereby grants to You a perpetual,
      worldwide, non-exclusive, no-charge, royalty-free, irrevocable
      (except as stated in this section) patent license to make, have made,
      use, offer to sell, sell, import, and otherwise transfer the Work,
      where such license applies only to those patent claims licensable
      by such Contributor that are necessarily infringed by their
      Contribution(s) alone or by combination of their Contribution(s)
      with the Work to which such Contribution(s) was submitted. If You
      institute patent litigation against any entity (including a
      cross-claim or counterclaim in a lawsuit) alleging that the Work
      or a Contribution incorporated within the Work constitutes direct
      or contributory patent infringement, then any patent licenses
      granted to You under this License for that Work shall terminate
      as of the date such litigation is filed.

   4. Redistribution. You may reproduce and distribute copies of the
      Work or Derivative Works thereof in any medium, with or without
      modifications, and in Source or Object form, provided that You
      meet the following conditions:

      (a) You must give any other recipients of the Work or
          Derivative Works a copy of this License; and

      (b) You must cause any modified files to carry prominent notices
          stating that You changed the files; and

      (c) You must retain, in the Source form of any Derivative Works
          that You distribute, all copyright, patent, trademark, and
          attribution notices from the Source form of the Work,
          excluding those notices that do not pertain to any part of
          the Derivative Works; and

      (d) If the Work includes a "NOTICE" text file as part of its
          distribution, then any Derivative Works that You distribute must
          include a readable copy of the attribution notices contained
          within such NOTICE file, excluding those notices that do not
          pertain to any part of the Derivative Works, in at least one
          of the following places: within a NOTICE text file distributed
          as part of the Derivative Works; within the Source form or
          documentation, if provided along with the Derivative Works; or,
          within a display generated by the Derivative Works, if and
          wherever such third-party notices normally appear. The contents
          of the NOTICE file are for informational purposes only and
          do not modify the License. You may add Your own attribution
          notices within Derivative Works that You distribute, alongside
          or as an addendum to the NOTICE text from the Work, provided
          that such additional attribution notices cannot be construed
          as modifying the License.

      You may add Your own copyright statement to Your modifications and
      may provide additional or different license terms and conditions
      for use, reproduction, or distribution of Your modifications, or
      for any such Derivative Works as a whole, provided Your use,
      reproduction, and distribution of the Work otherwise complies with
      the conditions stated in this License.

   5. Submission of Contributions. Unless You explicitly state otherwise,
      any Contribution intentionally submitted for inclusion in the Work
      by You to the Licensor shall be under the terms and conditions of
      this License, without any additional terms or conditions.
      Notwithstanding the above, nothing herein shall supersede or modify
      the terms of any separate license agreement you may have executed
      with Licensor regarding such Contributions.

   6. Trademarks. This License does not grant permission to use the trade
      names, trademarks, service marks, or product names of the Licensor,
      except as required for reasonable and customary use in describing the
      origin of the Work and reproducing the content of the NOTICE file.

   7. Disclaimer of Warranty. Unless required by applicable law or
      agreed to in writing, Licensor provides the Work (and each
      Contributor provides its Contributions) on an "AS IS" BASIS,
      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
      implied, including, without limitation, any warranties or conditions
      of TITLE, NON-INFRINGEMENT, MERCHANTABILITY, or FITNESS FOR A
      PARTICULAR PURPOSE. You are solely responsible for determining the
      appropriateness of using or redistributing the Work and assume any
      risks associated with Your exercise of permissions under this License.

   8. Limitation of Liability. In no event and under no legal theory,
      whether in tort (including negligence), contract, or otherwise,
      unless required by applicable law (such as deliberate and grossly
      negligent acts) or agreed to in writing, shall any Contributor be
      liable to You for damages, including any direct, indirect, special,
      incidental, or consequential damages of any character arising as a
      result of this License or out of the use or inability to use the
      Work (including but not limited to damages for loss of goodwill,
      work stoppage, computer failure or malfunction, or any and all
      other commercial damages or losses), even if such Contributor
      has been advised of the possibility of such damages.

   9. Accepting Warranty or Additional Liability. While redistributing
      the Work or Derivative Works thereof, You may choose to offer,
      and charge a fee for, acceptance of support, warranty, indemnity,
      or other liability obligations and/or rights consistent with this
      License. However, in accepting such obligations, You may act only
      on Your own behalf and on Your sole responsibility, not on behalf
      of any other Contributor, and only if You agree to indemnify,
      defend, and hold each Contributor harmless for any liability
      incurred by, or claims asserted against, such Contributor by reason
      of your accepting any such warranty or additional liability.

   END OF TERMS AND CONDITIONS

   APPENDIX: How to apply the Apache License to your work.

      To apply the Apache License to your work, attach the following
      boilerplate notice, with the fields enclosed by brackets "[]"
      replaced with your own identifying information. (Don't include
      the brackets!)  The text should be enclosed in the appropriate
      comment syntax for the file format. We also recommend that a
      file or class name and description of purpose be included on the
      same "printed page" as the copyright notice for easier
      identification within third-party archives.

   Copyright 2020 Rolando Gopez Lacuata.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       https://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

