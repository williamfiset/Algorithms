
[![Travis](https://img.shields.io/travis/williamfiset/Algorithms.svg)](https://travis-ci.org/williamfiset/Algorithms) [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

<!-- 
Uncomment when more AI algorithms are done.

# Artificial Intelligence (AI) 
* [Genetic algorithm Knapsack 0/1](AI/GeneticAlgorithm_knapsack_01.java)
* [Genetic algorithm text searching](AI/GeneticAlgorithm_textSearch.java) 
* -->

# Constructive algorithms
* [Combinations](ConstructiveAlgorithms/Combinations.java) **- O(n choose r)**
* [Unique combinations](ConstructiveAlgorithms/UniqueCombinations.java) **- O(n choose r)**
* [Combinations with Repetition](ConstructiveAlgorithms/CombinationsWithRepetition.java) **- O((n+r-1) choose r)**
* [Permutations](ConstructiveAlgorithms/Permutations.java) **- O(n!)**
* [Power set (set of all subsets)](ConstructiveAlgorithms/PowerSet.java) **- O(2<sup>n</sup>)**

# Dynamic Programming
* [Coin change](DynamicProgramming/CoinChange.java) **- O(nW)**
* [Edit distance](DynamicProgramming/EditDistance.java) **- O(nm)**
* [Knapsack 0/1](DynamicProgramming/Knapsack_01.java) **- O(nW)**
* [Knapsack unbounded (0/∞)](DynamicProgramming/KnapsackUnbounded.java) **- O(nW)**
* [Maximum subarray](DynamicProgramming/MaximumSubarray.java) **- O(n)**
* [Longest Common Subsequence (LCS)](DynamicProgramming/LongestCommonSubsequence.java) **- O(nm)**
* [Longest Increasing Subsequence (LIS)](DynamicProgramming/LongestIncreasingSubsequence.java) **- O(n<sup>2</sup>)**
* [Longest Palindrome Subsequence (LPS)](DynamicProgramming/LongestPalindromeSubsequence.java) **- O(n<sup>2</sup>)**
* [Traveling Salesman Problem](GraphTheory/TspDynamicProgramming.java) **- O(n<sup>2</sup>2<sup>n</sup>)**

# Geometry
* [Angle between 2D vectors](Geometry/AngleBetweenVectors2D.java) **- O(1)**
* [Angle between 3D vectors](Geometry/AngleBetweenVectors3D.java) **- O(1)**
* [Circle-circle intersection point(s)](Geometry/CircleCircleIntersectionPoints.js) **- O(1)**
* [Circle-line intersection point(s)](Geometry/LineCircleIntersection.js) **- O(1)**
* [Circle-line segment intersection point(s)](Geometry/LineSegmentCircleIntersection.js) **- O(1)**
* [Circle-point tangent line(s)](Geometry/PointCircleTangent.java) **- O(1)**
* [Closest pair of points (line sweeping algorithm)](Geometry/ClosestPairOfPoints.java) **- O(nlog(n))**
* [Collinear points test (are three 2D points on the same line)](Geometry/CollinearPoints.java) **- O(1)**
* [Convex hull (Graham Scan algorithm)](Geometry/ConvexHullGrahamScan.java) **- O(nlog(n))**
* [Convex hull (Monotone chain algorithm)](Geometry/ConvexHullMonotoneChainAlgorithm.java) **- O(nlog(n))**
* [Convex polygon area](Geometry/ConvexPolygonArea.java) **- O(n)**
* [Convex polygon contains points](Geometry/ConvexPolygonContainsPoint.java) **- O(log(n))**
* [Coplanar points test (are four 3D points on the same plane)](Geometry/CoplanarPointsTest.java) **- O(1)**
* [Line class (handy infinite line class)](Geometry/Line.java) **- O(1)**
* [Line-circle intersection point(s)](Geometry/LineCircleIntersection.js) **- O(1)**
* [Line segment-circle intersection point(s)](Geometry/LineSegmentCircleIntersection.js) **- O(1)**
* [Line segment to general form (ax + by = c)](Geometry/LineSegmentToGeneralForm.java) **- O(1)**
* [Line segment-line segment intersection](Geometry/LineSegmentLineSegmentIntersection.java) **- O(1)**
* [Longitude-Latitude geographic distance](Geometry/LongitudeLatitudeGeographicDistance.java) **- O(1)**
* [Point-circle tangent line(s)](Geometry/PointCircleTangent.java) **- O(1)**
* [Point is inside triangle check](Geometry/PointInsideTriangle.java) **- O(1)**
* [Point rotation about point](Geometry/PointRotation.java) **- O(1)**
* [Triangle area algorithms](Geometry/TriangleArea.java) **- O(1)**
* [[UNTESTED] Circle-circle intersection area](Geometry/CircleCircleIntersectionArea.java) **- O(1)**
* [[UNTESTED] Circular segment area](Geometry/CircularSegmentArea.java) **- O(1)**

# Graph theory

### Tree algorithms
* [Tree diameter](GraphTheory/TreeAlgorithms/TreeDiameter.java) **- O(V+E)**
* [Tree canonical form](GraphTheory/TreeAlgorithms/TreeCanonicalFormAdjacencyList.java) **- O(V+E)**

### Network flow
* [Bipartite graph verification (adjacency list)](GraphTheory/NetworkFlow/BipartiteGraphCheckAdjacencyList.java) **- O(V+E)**
* [Ford-Fulkerson method with DFS (max flow, min cut, adjacency list)](GraphTheory/NetworkFlow/FordFulkersonDFSAdjacencyList.java) **- O(fE)**
* [Ford-Fulkerson method with DFS (max flow, min cut, adjacency matrix)](GraphTheory/NetworkFlow/FordFulkersonDFSAdjacencyMatrix.java) **- O(fV<sup>2</sup>)**
* [Edmonds-Karp Algorithm (max flow, min cut, adjacency list)](GraphTheory/NetworkFlow/EdmondsKarpAdjacencyList.java) **- O(VE<sup>2</sup>)**
* [Edmonds-Karp Algorithm optimized (max flow, min cut, adjacency list)](GraphTheory/NetworkFlow/EdmondsKarpAdjacencyListOptimized.java) **- O(VE<sup>2</sup>)**
* [Maximum Cardinality Bipartite Matching (augmenting path algorithm, adjacency list)](GraphTheory/NetworkFlow/MaximumCardinalityBipartiteMatchingAugmentingPathAdjacencyList.java) **- O(VE)**

### Other graph theory
* [Bellman-Ford (edge list, negative cycles)](GraphTheory/BellmanFordEdgeList.java) **- O(VE)**
* [Bellman-Ford (adjacency list, negative cycles)](GraphTheory/BellmanFordAdjacencyList.java) **- O(VE)**
* [Breadth first search (adjacency list)](GraphTheory/BreadthFirstSearchAdjacencyListIterative.java) **- O(V+E)**
* [Breadth first search (adjacency list, fast queue)](GraphTheory/BreadthFirstSearchAdjacencyListIterativeFastQueue.java) **- O(V+E)**
* [Find connected components (adjacency list, union find)](GraphTheory/ConnectedComponentsAdjacencyList.java) **- O(Elog(E))**
* [Depth first search (adjacency list, iterative)](GraphTheory/DepthFirstSearchAdjacencyListIterative.java) **- O(V+E)**
* [Depth first search (adjacency list, iterative, fast stack)](GraphTheory/DepthFirstSearchAdjacencyListIterativeFastStack.java) **- O(V+E)**
* [Depth first search (adjacency list, recursive)](GraphTheory/DepthFirstSearchAdjacencyListRecursive.java) **- O(V+E)**
* [Dijkstra's shortest path (adjacency list)](GraphTheory/DijkstrasShortestPathAdjacencyList.java) **- O(Elog(V))**
* [Dijkstra's shortest path to all nodes (adjacency list)](GraphTheory/DijkstrasShortestPathAllNodesAdjacencyList.java) **- O(Elog(V))**
* [Floyd Warshall algorithm (adjacency matrix, negative cycle check)](GraphTheory/FloydWarshall.java) **- O(V<sup>3</sup>)**
* [Graph diameter (adjacency list)](GraphTheory/GraphDiameter.java) **- O(VE)**
* [Kruskal's min spanning tree algorithm (edge list, union find)](GraphTheory/KruskalsEdgeList.java) **- O(Elog(E))**
* [Prim's min spanning tree algorithm (lazy version, adjacency list)](GraphTheory/LazyPrimsAdjacencyList.java) **- O(Elog(E))**
* [Prim's min spanning tree  algorithm (lazy version, adjacency matrix)](GraphTheory/LazyPrimsAdjacencyMatrix.java) **- O(V<sup>2</sup>)**
* [Prim's min spanning tree  algorithm (eager version, adjacency list)](GraphTheory/EagerPrimsAdjacencyList.java) **- O(Elog(V))**
* [Steiner tree (minimum spanning tree generalization)](GraphTheory/SteinerTree.java) **- O(V<sup>3</sup> + V<sup>2</sup> * 2<sup>T</sup> + V * 3<sup>T</sup>)**
* [Tarjan's strongly connected components algorithm (adjacency list) ](GraphTheory/TarjanAdjacencyList.java) **- O(V+E)**
* [Tarjan's strongly connected components algorithm (adjacency matrix) ](GraphTheory/TarjanAdjacencyMatrix.java) **- O(V<sup>2</sup>)**
* [Topological sort (acyclic graph, adjacency list)](GraphTheory/TopologicalSortAdjacencyList.java) **- O(V+E)**
* [Topological sort (acyclic graph, adjacency matrix)](GraphTheory/TopologicalSortAdjacencyMatrix.java) **- O(V<sup>2</sup>)**
* [Traveling Salesman Problem (brute force)](GraphTheory/TspBruteForce.java) **- O(n!)**
* [Traveling Salesman Problem (dynamic programming, iterative)](GraphTheory/TspDynamicProgrammingIterative.java) **- O(n<sup>2</sup>2<sup>n</sup>)**
* [Traveling Salesman Problem (dynamic programming, recursive)](GraphTheory/TspDynamicProgrammingRecursive.java) **- O(n<sup>2</sup>2<sup>n</sup>)**

# Linear algebra
* [Freivald's algorithm (matrix multiplication verification)](LinearAlgebra/FreivaldsAlgorithm.java) **- O(kn<sup>2</sup>)**
* [Gaussian elimination (solve system of linear equations)](LinearAlgebra/GaussianElimination.java) **- O(cr<sup>2</sup>)**
* [Gaussian elimination (modular version, prime finite field)](LinearAlgebra/ModularLinearAlgebra.java) **- O(cr<sup>2</sup>)**
* [Linear recurrence solver (finds nth term in a recurrence relation)](LinearAlgebra/LinearRecurrenceSolver.java) **- O(m<sup>3</sup>log(n))**
* [Matrix determinant (Laplace/cofactor expansion)](LinearAlgebra/MatrixDeterminantLaplaceExpansion.java) **- O((n+2)!)**
* [Matrix inverse](LinearAlgebra/MatrixInverse.java) **- O(n<sup>3</sup>)**
* [Matrix multiplication](LinearAlgebra/MatrixMultiplication.java) **- O(n<sup>3</sup>)**
* [Matrix power](LinearAlgebra/MatrixPower.java) **- O(n<sup>3</sup>log(p))**
* [Square matrix rotation](LinearAlgebra/RotateSquareMatrixInplace.java) **- O(n<sup>2</sup>)**

# Mathematics
* [Chinese remainder theorem](Math/ChineseRemainderTheorem.java)
* [Prime number sieve (sieve of Eratosthenes)](Math/SieveOfEratosthenes.java) **- O(nlog(log(n)))**
* [Prime number sieve (sieve of Eratosthenes, compressed)](Math/CompressedPrimeSieve.java) **- O(nlog(log(n)))**
* [Totient function (phi function, relatively prime number count)](Math/EulerTotientFunction.java) **- O(n<sup>1/4</sup>)**
* [Totient function using sieve (phi function, relatively prime number count)](Math/EulerTotientFunctionWithSieve.java) **- O(nlog(log(n)))**
* [Extended euclidean algorithm](Math/ExtendedEuclideanAlgorithm.java) **- ~O(log(a + b))**
* [Greatest Common Divisor (GCD)](Math/GCD.java) **- ~O(log(a + b))**
* [Fast Fourier transform (quick polynomial multiplication)](Math/FastFourierTransform.java) **- O(nlog(n))**
* [Fast Fourier transform (quick polynomial multiplication, complex numbers)](Math/FastFourierTransformComplexNumbers.java) **- O(nlog(n))**
* [Primality check](Math/IsPrime.java) **- O(√n)**
* [Primality check (Rabin-Miller)](Math/RabinMillerPrimalityTest.py) **- O(k)**
* [Least Common Multiple (LCM)](Math/LCM.java) **- ~O(log(a + b))**
* [Modular inverse](Math/ModularInverse.java) **- ~O(log(a + b))**
* [Prime factorization (pollard rho)](Math/PrimeFactorization.java) **- O(n<sup>1/4</sup>)**
* [Relatively prime check (coprimality check)](Math/RelativelyPrime.java) **- ~O(log(a + b))**

# Other
* [Bit manipulations](Other/BitManipulations.java) **- O(1)**
* [Sliding Window Minimum/Maximum](Other/SlidingWindowMaximum.java) **- O(1)**
* [Square Root Decomposition](Other/SquareRootDecomposition.java) **- O(1) point updates, O(√n) range queries**

# Search algorithms
* [Binary search (real numbers)](SearchAlgorithms/BinarySearch.java) **- O(log(n))**
* [Interpolation search (discrete discrete)](SearchAlgorithms/InterpolationSearch.java) **- O(n) or O(log(log(n))) with uniform input**
* [Ternary search (real numbers)](SearchAlgorithms/TernarySearch.java) **- O(log(n))**
* [Ternary search (discrete numbers)](SearchAlgorithms/TernarySearchDiscrete.java) **- O(log(n))**

# Sorting algorithms
* [Bubble sort](SortingAlgorithms/BubbleSort.java) **- O(n<sup>2</sup>)**
* [Bucket sort](SortingAlgorithms/BucketSort.java) **- Θ(n + k)**
* [Counting sort](SortingAlgorithms/CountingSort.java) **- O(n + k)**
* [Heapsort](SortingAlgorithms/Heapsort.java) **- O(nlog(n))**
* [Insertion sort](SortingAlgorithms/InsertionSort.java) **- O(n<sup>2</sup>)**
* [Mergesort](SortingAlgorithms/Mergesort.java) **- O(nlog(n))**
* [Quicksort (in-place, Hoare partitioning)](SortingAlgorithms/Quicksort.java) **- Θ(nlog(n))**
* [Selection sort](SortingAlgorithms/SelectionSort.java) **- O(n<sup>2</sup>)**

# String algorithms
* [Booth's algorithm (finds lexicographically smallest string rotation)](StringAlgorithms/BoothsAlgorithm.java) **- O(n)**
* [Knuth-Morris-Pratt algorithm (finds pattern matches in text)](StringAlgorithms/KMP.java) **- O(n+m)**
* [Longest Common Prefix (LCP) array](StringAlgorithms/LongestCommonPrefixArray.java) **- O(nlog(n)) bounded by SA construction, otherwise O(n)**
* [Longest Common Substring (LCS)](StringAlgorithms/LongestCommonSubstring.java) **- O(nlog(n)) bounded by SA construction, otherwise O(n)**
* [Longest Repeated Substring (LRS)](StringAlgorithms/LongestRepeatedSubstring.java) **- O(nlog(n))**
* [Manacher's algorithm (finds all palindromes in text)](StringAlgorithms/ManachersAlgorithm.java) **- O(n)**
* [Rabin-Karp algorithm (finds pattern matches in text)](StringAlgorithms/RabinKarp.java) **- O(n+m)**
* [Substring verification with suffix array](StringAlgorithms/SubstringVerificationSuffixArray.java) **- O(nlog(n)) SA construction and O(mlog(n)) per query**

# Contributing

This repository is contribution friendly :smiley:. If you're an algorithms enthusiast (like me!) and want to add or improve an algorithm your contribution is welcome! Please be sure to include tests :kissing_heart:.

# For developers

This project uses [Gradle](https://gradle.org/) as a build system and for testing. To get started install the gradle command-line tool and run the build command to make sure you don't get any errors:

```bash
Algorithms$ gradle build
```

### Adding a new algorithm

The procedure to add a new algorithm named **Foo** is the following:

1) Identify the category folder your algorithm belongs to. For example a matrix multiplication snippet would belong to the LinearAlgebra/ folder. You may also create a new category folder if appropriate.
2) Add the algorithm implementation to Category/ as Category/Foo.java
3) Add tests for Foo in Category/Foo/tests/FooTest.java
4) Edit the **build.gradle** file if you added a new category to the project.
5) Test your algorithm thoroughly.
6) Send pull request for review :open_mouth:

### Testing

This repository places a large emphasis on good testing practice to ensure that published algorithms are bug free and high quality. Testing is done using a combinations of frameworks including: [JUnit](http://junit.org/junit4/), [Mockito](http://site.mockito.org/) and the [Google Truth](http://google.github.io/truth) framework. Currently very few algorithms have tests because they were (informally) tested against problems on [Kattis](https://open.kattis.com/problems) in a competitive programming setting, but we are slowly migrating to formally testing these algorithms for robustness.

When developing you likely do not want to run all tests but only a subset of them. For example, if you want to run the FloydWarshallTest.java file under [GraphTheory/tests/FloydWarshallTest.java](GraphTheory/tests/FloydWarshallTest.java) you can execute:
```bash
Algorithms$ gradle test --tests "FloydWarshallTest"
```

# License

This repository is released under the [MIT license](https://opensource.org/licenses/MIT). In short, this means you are free to use this software in any personal, open-source or commercial projects. Attribution is optional but appreciated.
