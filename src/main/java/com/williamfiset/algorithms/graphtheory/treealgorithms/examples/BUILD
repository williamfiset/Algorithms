load("@rules_java//java:defs.bzl", "java_binary", "java_library")

# Tree algorithm examples lib
java_library(
    name = "treealgorithms_examples",
    srcs = glob(["*.java"]),
    visibility = ["//visibility:public"],
    deps = ["//src/main/java/com/williamfiset/algorithms/utils/graphutils:graphutils"],
)

################################################################################
# Binary targets for tree algorithm examples
################################################################################

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory/treealgorithms/examples:TreeHeight
java_binary(
    name = "TreeHeight",
    main_class = "com.williamfiset.algorithms.graphtheory.treealgorithms.examples.TreeHeight",
    runtime_deps = [":treealgorithms_examples"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/graphtheory/treealgorithms/examples:TreeSum
java_binary(
    name = "TreeSum",
    main_class = "com.williamfiset.algorithms.graphtheory.treealgorithms.examples.TreeSum",
    runtime_deps = [":treealgorithms_examples"],
)
