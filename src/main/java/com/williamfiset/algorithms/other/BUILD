load("@rules_java//java:defs.bzl", "java_binary", "java_library")

# Miscellaneous algorithms library
java_library(
    name = "other",
    srcs = glob(["*.java"]),
    visibility = ["//visibility:public"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/other:Combinations
java_binary(
    name = "Combinations",
    main_class = "com.williamfiset.algorithms.other.Combinations",
    runtime_deps = [":other"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/other:CombinationsWithRepetition
java_binary(
    name = "CombinationsWithRepetition",
    main_class = "com.williamfiset.algorithms.other.CombinationsWithRepetition",
    runtime_deps = [":other"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/other:LazyRangeAdder
java_binary(
    name = "LazyRangeAdder",
    main_class = "com.williamfiset.algorithms.other.LazyRangeAdder",
    runtime_deps = [":other"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/other:Permutations
java_binary(
    name = "Permutations",
    main_class = "com.williamfiset.algorithms.other.Permutations",
    runtime_deps = [":other"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/other:PowerSet
java_binary(
    name = "PowerSet",
    main_class = "com.williamfiset.algorithms.other.PowerSet",
    runtime_deps = [":other"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/other:SquareRootDecomposition
java_binary(
    name = "SquareRootDecomposition",
    main_class = "com.williamfiset.algorithms.other.SquareRootDecomposition",
    runtime_deps = [":other"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/other:UniqueCombinations
java_binary(
    name = "UniqueCombinations",
    main_class = "com.williamfiset.algorithms.other.UniqueCombinations",
    runtime_deps = [":other"],
)
