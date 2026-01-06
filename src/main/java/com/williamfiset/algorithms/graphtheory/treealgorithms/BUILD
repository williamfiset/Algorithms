load("@rules_java//java:defs.bzl", "java_binary", "java_library")

# Tree algorithms
java_library(
    name = "treealgorithms",
    srcs = glob(["*.java"]),
    visibility = ["//visibility:public"],
    deps = ["//src/main/java/com/williamfiset/algorithms/utils/graphutils:graphutils"],
)

################################################################################
# Binary targets for tree algorithms
################################################################################

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory/treealgorithms:LowestCommonAncestor
java_binary(
    name = "LowestCommonAncestor",
    main_class = "com.williamfiset.algorithms.graphtheory.treealgorithms.LowestCommonAncestor",
    runtime_deps = [":treealgorithms"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory/treealgorithms:LowestCommonAncestorEulerTour
java_binary(
    name = "LowestCommonAncestorEulerTour",
    main_class = "com.williamfiset.algorithms.graphtheory.treealgorithms.LowestCommonAncestorEulerTour",
    runtime_deps = [":treealgorithms"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory/treealgorithms:RootingTree
java_binary(
    name = "RootingTree",
    main_class = "com.williamfiset.algorithms.graphtheory.treealgorithms.RootingTree",
    runtime_deps = [":treealgorithms"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory/treealgorithms:TreeCenter
java_binary(
    name = "TreeCenter",
    main_class = "com.williamfiset.algorithms.graphtheory.treealgorithms.TreeCenter",
    runtime_deps = [":treealgorithms"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory/treealgorithms:TreeCenterLongestPathImpl
java_binary(
    name = "TreeCenterLongestPathImpl",
    main_class = "com.williamfiset.algorithms.graphtheory.treealgorithms.TreeCenterLongestPathImpl",
    runtime_deps = [":treealgorithms"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory/treealgorithms:TreeDiameter
java_binary(
    name = "TreeDiameter",
    main_class = "com.williamfiset.algorithms.graphtheory.treealgorithms.TreeDiameter",
    runtime_deps = [":treealgorithms"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory/treealgorithms:TreeIsomorphism
java_binary(
    name = "TreeIsomorphism",
    main_class = "com.williamfiset.algorithms.graphtheory.treealgorithms.TreeIsomorphism",
    runtime_deps = [":treealgorithms"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory/treealgorithms:TreeIsomorphismWithBfs
java_binary(
    name = "TreeIsomorphismWithBfs",
    main_class = "com.williamfiset.algorithms.graphtheory.treealgorithms.TreeIsomorphismWithBfs",
    runtime_deps = [":treealgorithms"],
)
