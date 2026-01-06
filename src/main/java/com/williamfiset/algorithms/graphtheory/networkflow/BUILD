load("@rules_java//java:defs.bzl", "java_binary", "java_library")

# Network flow algorithms lib
java_library(
    name = "networkflow",
    srcs = glob(["*.java"]),
    visibility = ["//visibility:public"],
    deps = [
        "//src/main/java/com/williamfiset/algorithms/utils/graphutils:graphutils",
    ],
)

################################################################################
# Binary targets for network flow algorithms
################################################################################

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory/networkflow:BipartiteGraphCheckAdjacencyList
java_binary(
    name = "BipartiteGraphCheckAdjacencyList",
    main_class = "com.williamfiset.algorithms.graphtheory.networkflow.BipartiteGraphCheckAdjacencyList",
    runtime_deps = [":networkflow"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory/networkflow:CapacityScalingSolverAdjacencyList
java_binary(
    name = "CapacityScalingSolverAdjacencyList",
    main_class = "com.williamfiset.algorithms.graphtheory.networkflow.CapacityScalingSolverAdjacencyList",
    runtime_deps = [":networkflow"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory/networkflow:Dinics
java_binary(
    name = "Dinics",
    main_class = "com.williamfiset.algorithms.graphtheory.networkflow.Dinics",
    runtime_deps = [":networkflow"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory/networkflow:EdmondsKarpAdjacencyList
java_binary(
    name = "EdmondsKarpAdjacencyList",
    main_class = "com.williamfiset.algorithms.graphtheory.networkflow.EdmondsKarpAdjacencyList",
    runtime_deps = [":networkflow"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory/networkflow:FordFulkersonDfsSolverAdjacencyList
java_binary(
    name = "FordFulkersonDfsSolverAdjacencyList",
    main_class = "com.williamfiset.algorithms.graphtheory.networkflow.FordFulkersonDfsSolverAdjacencyList",
    runtime_deps = [":networkflow"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory/networkflow:MaximumCardinalityBipartiteMatchingAugmentingPathAdjacencyList
java_binary(
    name = "MaximumCardinalityBipartiteMatchingAugmentingPathAdjacencyList",
    main_class = "com.williamfiset.algorithms.graphtheory.networkflow.MaximumCardinalityBipartiteMatchingAugmentingPathAdjacencyList",
    runtime_deps = [":networkflow"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory/networkflow:MinCostMaxFlowWithBellmanFord
java_binary(
    name = "MinCostMaxFlowWithBellmanFord",
    main_class = "com.williamfiset.algorithms.graphtheory.networkflow.MinCostMaxFlowWithBellmanFord",
    runtime_deps = [":networkflow"],
)
