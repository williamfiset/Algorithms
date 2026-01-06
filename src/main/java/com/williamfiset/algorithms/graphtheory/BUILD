load("@rules_java//java:defs.bzl", "java_binary", "java_library")

# Graph theory algorithms library
java_library(
    name = "graphtheory",
    srcs = glob(
        ["*.java"],
        exclude = [
            "networkflow/**",
            "treealgorithms/**",
            "examples/**",
            "analysis/**",
        ],
    ),
    visibility = ["//visibility:public"],
    deps = ["//src/main/java/com/williamfiset/algorithms/utils/graphutils:graphutils"],
)

################################################################################
# Binary targets for root graphtheory algorithms
################################################################################

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory:ArticulationPointsAdjacencyList
java_binary(
    name = "ArticulationPointsAdjacencyList",
    main_class = "com.williamfiset.algorithms.graphtheory.ArticulationPointsAdjacencyList",
    runtime_deps = [":graphtheory"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory:AStar_GridHeuristic
java_binary(
    name = "AStar_GridHeuristic",
    main_class = "com.williamfiset.algorithms.graphtheory.AStar_GridHeuristic",
    runtime_deps = [":graphtheory"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory:BellmanFordAdjacencyList
java_binary(
    name = "BellmanFordAdjacencyList",
    main_class = "com.williamfiset.algorithms.graphtheory.BellmanFordAdjacencyList",
    runtime_deps = [":graphtheory"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory:BellmanFordAdjacencyMatrix
java_binary(
    name = "BellmanFordAdjacencyMatrix",
    main_class = "com.williamfiset.algorithms.graphtheory.BellmanFordAdjacencyMatrix",
    runtime_deps = [":graphtheory"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory:BellmanFordEdgeList
java_binary(
    name = "BellmanFordEdgeList",
    main_class = "com.williamfiset.algorithms.graphtheory.BellmanFordEdgeList",
    runtime_deps = [":graphtheory"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory:Boruvkas
java_binary(
    name = "Boruvkas",
    main_class = "com.williamfiset.algorithms.graphtheory.Boruvkas",
    runtime_deps = [":graphtheory"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory:BreadthFirstSearchAdjacencyListIterative
java_binary(
    name = "BreadthFirstSearchAdjacencyListIterative",
    main_class = "com.williamfiset.algorithms.graphtheory.BreadthFirstSearchAdjacencyListIterative",
    runtime_deps = [":graphtheory"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory:BreadthFirstSearchAdjacencyListIterativeFastQueue
java_binary(
    name = "BreadthFirstSearchAdjacencyListIterativeFastQueue",
    main_class = "com.williamfiset.algorithms.graphtheory.BreadthFirstSearchAdjacencyListIterativeFastQueue",
    runtime_deps = [":graphtheory"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory:BreadthFirstSearchRecursive
java_binary(
    name = "BreadthFirstSearchRecursive",
    main_class = "com.williamfiset.algorithms.graphtheory.BreadthFirstSearchRecursive",
    runtime_deps = [":graphtheory"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory:BridgesAdjacencyList
java_binary(
    name = "BridgesAdjacencyList",
    main_class = "com.williamfiset.algorithms.graphtheory.BridgesAdjacencyList",
    runtime_deps = [":graphtheory"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory:BridgesAdjacencyListIterative
java_binary(
    name = "BridgesAdjacencyListIterative",
    main_class = "com.williamfiset.algorithms.graphtheory.BridgesAdjacencyListIterative",
    runtime_deps = [":graphtheory"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory:ChinesePostmanProblem
java_binary(
    name = "ChinesePostmanProblem",
    main_class = "com.williamfiset.algorithms.graphtheory.ChinesePostmanProblem",
    runtime_deps = [":graphtheory"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory:ConnectedComponentsAdjacencyList
java_binary(
    name = "ConnectedComponentsAdjacencyList",
    main_class = "com.williamfiset.algorithms.graphtheory.ConnectedComponentsAdjacencyList",
    runtime_deps = [":graphtheory"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory:ConnectedComponentsDfsSolverAdjacencyList
java_binary(
    name = "ConnectedComponentsDfsSolverAdjacencyList",
    main_class = "com.williamfiset.algorithms.graphtheory.ConnectedComponentsDfsSolverAdjacencyList",
    runtime_deps = [":graphtheory"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory:DepthFirstSearchAdjacencyListIterative
java_binary(
    name = "DepthFirstSearchAdjacencyListIterative",
    main_class = "com.williamfiset.algorithms.graphtheory.DepthFirstSearchAdjacencyListIterative",
    runtime_deps = [":graphtheory"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory:DepthFirstSearchAdjacencyListIterativeFastStack
java_binary(
    name = "DepthFirstSearchAdjacencyListIterativeFastStack",
    main_class = "com.williamfiset.algorithms.graphtheory.DepthFirstSearchAdjacencyListIterativeFastStack",
    runtime_deps = [":graphtheory"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory:DepthFirstSearchAdjacencyListRecursive
java_binary(
    name = "DepthFirstSearchAdjacencyListRecursive",
    main_class = "com.williamfiset.algorithms.graphtheory.DepthFirstSearchAdjacencyListRecursive",
    runtime_deps = [":graphtheory"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory:DijkstrasShortestPathAdjacencyList
java_binary(
    name = "DijkstrasShortestPathAdjacencyList",
    main_class = "com.williamfiset.algorithms.graphtheory.DijkstrasShortestPathAdjacencyList",
    runtime_deps = [":graphtheory"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory:DijkstrasShortestPathAdjacencyListWithDHeap
java_binary(
    name = "DijkstrasShortestPathAdjacencyListWithDHeap",
    main_class = "com.williamfiset.algorithms.graphtheory.DijkstrasShortestPathAdjacencyListWithDHeap",
    runtime_deps = [":graphtheory"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory:EagerPrimsAdjacencyList
java_binary(
    name = "EagerPrimsAdjacencyList",
    main_class = "com.williamfiset.algorithms.graphtheory.EagerPrimsAdjacencyList",
    runtime_deps = [":graphtheory"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory:EulerianPathDirectedEdgesAdjacencyList
java_binary(
    name = "EulerianPathDirectedEdgesAdjacencyList",
    main_class = "com.williamfiset.algorithms.graphtheory.EulerianPathDirectedEdgesAdjacencyList",
    runtime_deps = [":graphtheory"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory:EulerianPathUndirectedEdgesAdjacencyList
java_binary(
    name = "EulerianPathUndirectedEdgesAdjacencyList",
    main_class = "com.williamfiset.algorithms.graphtheory.EulerianPathUndirectedEdgesAdjacencyList",
    runtime_deps = [":graphtheory"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory:FloydWarshallSolver
java_binary(
    name = "FloydWarshallSolver",
    main_class = "com.williamfiset.algorithms.graphtheory.FloydWarshallSolver",
    runtime_deps = [":graphtheory"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory:GraphDiameter
java_binary(
    name = "GraphDiameter",
    main_class = "com.williamfiset.algorithms.graphtheory.GraphDiameter",
    runtime_deps = [":graphtheory"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory:Kahns
java_binary(
    name = "Kahns",
    main_class = "com.williamfiset.algorithms.graphtheory.Kahns",
    runtime_deps = [":graphtheory"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory:Kosaraju
java_binary(
    name = "Kosaraju",
    main_class = "com.williamfiset.algorithms.graphtheory.Kosaraju",
    runtime_deps = [":graphtheory"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory:KruskalsEdgeList
java_binary(
    name = "KruskalsEdgeList",
    main_class = "com.williamfiset.algorithms.graphtheory.KruskalsEdgeList",
    runtime_deps = [":graphtheory"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory:KruskalsEdgeListPartialSortSolver
java_binary(
    name = "KruskalsEdgeListPartialSortSolver",
    main_class = "com.williamfiset.algorithms.graphtheory.KruskalsEdgeListPartialSortSolver",
    runtime_deps = [":graphtheory"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory:LazyPrimsAdjacencyList
java_binary(
    name = "LazyPrimsAdjacencyList",
    main_class = "com.williamfiset.algorithms.graphtheory.LazyPrimsAdjacencyList",
    runtime_deps = [":graphtheory"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory:LazyPrimsAdjacencyMatrix
java_binary(
    name = "LazyPrimsAdjacencyMatrix",
    main_class = "com.williamfiset.algorithms.graphtheory.LazyPrimsAdjacencyMatrix",
    runtime_deps = [":graphtheory"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory:SteinerTree
java_binary(
    name = "SteinerTree",
    main_class = "com.williamfiset.algorithms.graphtheory.SteinerTree",
    runtime_deps = [":graphtheory"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory:TarjanAdjacencyMatrix
java_binary(
    name = "TarjanAdjacencyMatrix",
    main_class = "com.williamfiset.algorithms.graphtheory.TarjanAdjacencyMatrix",
    runtime_deps = [":graphtheory"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory:TarjanSccSolverAdjacencyList
java_binary(
    name = "TarjanSccSolverAdjacencyList",
    main_class = "com.williamfiset.algorithms.graphtheory.TarjanSccSolverAdjacencyList",
    runtime_deps = [":graphtheory"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory:TopologicalSortAdjacencyList
java_binary(
    name = "TopologicalSortAdjacencyList",
    main_class = "com.williamfiset.algorithms.graphtheory.TopologicalSortAdjacencyList",
    runtime_deps = [":graphtheory"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory:TopologicalSortAdjacencyMatrix
java_binary(
    name = "TopologicalSortAdjacencyMatrix",
    main_class = "com.williamfiset.algorithms.graphtheory.TopologicalSortAdjacencyMatrix",
    runtime_deps = [":graphtheory"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory:TspBruteForce
java_binary(
    name = "TspBruteForce",
    main_class = "com.williamfiset.algorithms.graphtheory.TspBruteForce",
    runtime_deps = [":graphtheory"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory:TspDynamicProgrammingIterative
java_binary(
    name = "TspDynamicProgrammingIterative",
    main_class = "com.williamfiset.algorithms.graphtheory.TspDynamicProgrammingIterative",
    runtime_deps = [":graphtheory"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory:TspDynamicProgrammingRecursive
java_binary(
    name = "TspDynamicProgrammingRecursive",
    main_class = "com.williamfiset.algorithms.graphtheory.TspDynamicProgrammingRecursive",
    runtime_deps = [":graphtheory"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory:TwoSatSolverAdjacencyList
java_binary(
    name = "TwoSatSolverAdjacencyList",
    main_class = "com.williamfiset.algorithms.graphtheory.TwoSatSolverAdjacencyList",
    runtime_deps = [":graphtheory"],
)
