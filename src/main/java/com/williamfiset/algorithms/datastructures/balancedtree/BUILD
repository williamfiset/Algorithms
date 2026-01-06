load("@rules_java//java:defs.bzl", "java_binary", "java_library")

# Balanced tree implementations library
java_library(
    name = "balancedtree",
    srcs = glob(["*.java"]),
    visibility = ["//visibility:public"],
    deps = ["//src/main/java/com/williamfiset/algorithms/datastructures/utils"],
)

# bazel run //src/main/java/com/williamfiset/algorithms/datastructures/balancedtree:RedBlackTree
java_binary(
    name = "RedBlackTree",
    main_class = "com.williamfiset.algorithms.datastructures.balancedtree.RedBlackTree",
    runtime_deps = [":balancedtree"],
)
