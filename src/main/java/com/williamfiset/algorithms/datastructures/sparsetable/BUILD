load("@rules_java//java:defs.bzl", "java_binary", "java_library")

# Sparse table implementations library
java_library(
    name = "sparsetable",
    srcs = glob(
        ["*.java"],
        exclude = ["examples/**"],
    ),
    visibility = ["//visibility:public"],
)

# Sparse table examples library
java_library(
    name = "sparsetable_examples",
    srcs = glob(["examples/**/*.java"]),
    visibility = ["//visibility:public"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/datastructures/sparsetable:SparseTable
java_binary(
    name = "SparseTable",
    main_class = "com.williamfiset.algorithms.datastructures.sparsetable.SparseTable",
    runtime_deps = [":sparsetable"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/datastructures/sparsetable:MinSparseTable
java_binary(
    name = "MinSparseTable",
    main_class = "com.williamfiset.algorithms.datastructures.sparsetable.examples.MinSparseTable",
    runtime_deps = [":sparsetable_examples"],
)
